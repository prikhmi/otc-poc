package com.priya.otc.orchestrator.service;

import com.priya.otc.orchestrator.domain.*;
import com.priya.otc.orchestrator.dto.*;
import com.priya.otc.orchestrator.exception.PaymentNotFoundException;
import com.priya.otc.orchestrator.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OtcValidationService {

    private final PaymentOrderRepository paymentOrderRepo;
    private final CustomerRepository customerRepo;
    private final CustomerFinanceSummaryRepository financeRepo;
    private final CountryRiskRepository countryRiskRepo;

    public OtcValidationService(PaymentOrderRepository paymentOrderRepo,
                                CustomerRepository customerRepo,
                                CustomerFinanceSummaryRepository financeRepo,
                                CountryRiskRepository countryRiskRepo) {
        this.paymentOrderRepo = paymentOrderRepo;
        this.customerRepo = customerRepo;
        this.financeRepo = financeRepo;
        this.countryRiskRepo = countryRiskRepo;
    }

    public OtcValidationResponseDto validatePayment(String paymentId) {
        // 1. Load payment order
        PaymentOrder po = paymentOrderRepo.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));

        // 2. Load related data
        Customer customer = customerRepo.findById(po.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found: " + po.getCustomerId()));

        CustomerFinanceSummary finance = financeRepo.findById(po.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Finance summary not found for: " + po.getCustomerId()));

        CountryRisk originRisk = countryRiskRepo.findById(po.getOriginCountry())
                .orElse(null);
        CountryRisk destRisk = countryRiskRepo.findById(po.getDestinationCountry())
                .orElse(null);

        // 3. Build RuleRequestDto (for now just to show mapping)
        RuleRequestDto ruleReq = buildRuleRequest(po, customer, finance);

        // TODO: call rules service here later and map its response.
        // For now we will just return a dummy response so we can test DB + service.

        OtcValidationResponseDto response = new OtcValidationResponseDto();
        response.setPaymentId(po.getPaymentId());
        response.setStatus("PENDING_RULES");

        ValidationMessageDto msg = new ValidationMessageDto(
                "INFO",
                "Data loaded, rule engine not yet integrated."
        );
        response.setMessages(List.of(msg));

        return response;
    }

    private RuleRequestDto buildRuleRequest(PaymentOrder po,
                                            Customer customer,
                                            CustomerFinanceSummary finance) {

        CustomerDto c = new CustomerDto();
        c.setCustomerId(customer.getCustomerId());
        c.setCountry(customer.getCountry());
        c.setSegment(customer.getSegment());
        c.setRiskCategory(customer.getRiskCategory());
        c.setBlocked(customer.isBlocked());

        CustomerFinanceSummaryDto f = new CustomerFinanceSummaryDto();
        f.setTotalOutstanding(finance.getTotalOutstanding());
        f.setCreditLimit(finance.getCreditLimit());
        f.setOverdueInvoicesCount(finance.getOverdueInvoicesCount());
        f.setOldestInvoiceAgeDays(finance.getOldestInvoiceAgeDays());

        RuleRequestDto dto = new RuleRequestDto();
        dto.setPaymentId(po.getPaymentId());
        dto.setAmount(po.getAmount());
        dto.setCurrency(po.getCurrency());
        dto.setOriginCountry(po.getOriginCountry());
        dto.setDestinationCountry(po.getDestinationCountry());
        dto.setCustomer(c);
        dto.setCustomerFinance(f);

        return dto;
    }
}
