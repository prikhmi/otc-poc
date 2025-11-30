package com.priya.otc.orchestrator.service;

import com.priya.otc.orchestrator.domain.*;
import com.priya.otc.orchestrator.dto.*;
import com.priya.otc.orchestrator.exception.PaymentNotFoundException;
import com.priya.otc.orchestrator.repository.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OtcValidationService {

        private final PaymentOrderRepository paymentOrderRepo;
        private final CustomerRepository customerRepo;
        private final CustomerFinanceSummaryRepository financeRepo;
        private final CountryRiskRepository countryRiskRepo;
        private final RestTemplate restTemplate;
        @Value("${otc.rules-service.base-url}")
        private String rulesServiceBaseUrl;

        public OtcValidationService(PaymentOrderRepository paymentOrderRepo,
                        CustomerRepository customerRepo,
                        CustomerFinanceSummaryRepository financeRepo,
                        CountryRiskRepository countryRiskRepo,
                        RestTemplate restTemplate) {
                this.paymentOrderRepo = paymentOrderRepo;
                this.customerRepo = customerRepo;
                this.financeRepo = financeRepo;
                this.countryRiskRepo = countryRiskRepo;
                this.restTemplate = restTemplate;
        }

        public OtcValidationResponseDto validatePayment(String paymentId) {
                // 1. Load payment order
                PaymentOrder po = paymentOrderRepo.findById(paymentId)
                                .orElseThrow(() -> new PaymentNotFoundException(paymentId));

                // 2. Load related data
                Customer customer = customerRepo.findById(po.getCustomerId())
                                .orElseThrow(() -> new RuntimeException("Customer not found: " + po.getCustomerId()));

                CustomerFinanceSummary finance = financeRepo.findById(po.getCustomerId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Finance summary not found for: " + po.getCustomerId()));

                CountryRisk originRisk = countryRiskRepo.findById(po.getOriginCountry())
                                .orElse(null);
                CountryRisk destRisk = countryRiskRepo.findById(po.getDestinationCountry())
                                .orElse(null);

                // 3. Build RuleRequestDto
                RuleRequestDto ruleReq = buildRuleRequest(po, customer, finance, originRisk, destRisk);

                // 4. Call rules service
                String url = rulesServiceBaseUrl + "/api/rules/validate";

                RuleResponseDto ruleResp = restTemplate.postForObject(
                                url,
                                ruleReq,
                                RuleResponseDto.class);

                if (ruleResp == null) {
                        throw new RuntimeException("Rule service returned null response");
                }

                // 5. Map RuleResponseDto -> OtcValidationResponseDto
                OtcValidationResponseDto response = new OtcValidationResponseDto();
                response.setPaymentId(ruleResp.getPaymentId());
                response.setStatus(ruleResp.getStatus());
                response.setMessages(ruleResp.getMessages());

                // (Later we can also publish Kafka event here.)

                return response;
        }

        private RuleRequestDto buildRuleRequest(PaymentOrder po,
                        Customer customer,
                        CustomerFinanceSummary finance,
                        CountryRisk originRisk,
                        CountryRisk destRisk) {

                // Customer DTO
                CustomerDto c = new CustomerDto();
                c.setCustomerId(customer.getCustomerId());
                c.setCountry(customer.getCountry());
                c.setSegment(customer.getSegment());
                c.setRiskCategory(customer.getRiskCategory());
                c.setBlocked(customer.isBlocked());

                // Finance DTO
                CustomerFinanceSummaryDto f = new CustomerFinanceSummaryDto();
                f.setTotalOutstanding(finance.getTotalOutstanding());
                f.setCreditLimit(finance.getCreditLimit());
                f.setOverdueInvoicesCount(finance.getOverdueInvoicesCount());
                f.setOldestInvoiceAgeDays(finance.getOldestInvoiceAgeDays());

                // Risk flags: if originRisk/destRisk not found, treat as false
                boolean originHighRisk = originRisk != null && originRisk.isHighRisk();
                boolean originSanctioned = originRisk != null && originRisk.isSanctioned();
                boolean destHighRisk = destRisk != null && destRisk.isHighRisk();
                boolean destSanctioned = destRisk != null && destRisk.isSanctioned();

                // Rule request DTO
                RuleRequestDto dto = new RuleRequestDto();
                dto.setPaymentId(po.getPaymentId());
                dto.setAmount(po.getAmount());
                dto.setCurrency(po.getCurrency());
                dto.setOriginCountry(po.getOriginCountry());
                dto.setDestinationCountry(po.getDestinationCountry());
                dto.setPaymentMethod(po.getPaymentMethod());
                dto.setTerms(po.getTerms());
                dto.setOriginHighRisk(originHighRisk);
                dto.setOriginSanctioned(originSanctioned);
                dto.setDestHighRisk(destHighRisk);
                dto.setDestSanctioned(destSanctioned);
                dto.setCustomer(c);
                dto.setCustomerFinance(f);

                return dto;
        }

}
