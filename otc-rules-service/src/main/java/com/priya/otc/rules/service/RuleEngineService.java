package com.priya.otc.rules.service;

import com.priya.otc.rules.dto.*;
import com.priya.otc.rules.model.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RuleEngineService {

    private final KieContainer kieContainer;

    public RuleEngineService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    public RuleResponseDto validate(RuleRequestDto request) {

        StatelessKieSession session = kieContainer.newStatelessKieSession("otcValidationKSession");

        // 1. Map DTO → facts
        PaymentOrderFact poFact = mapPayment(request);
        CustomerFact custFact = mapCustomer(request.getCustomer());
        CustomerFinanceSummaryFact financeFact = mapFinance(request.getCustomerFinance());

        ValidationResultFact resultFact = new ValidationResultFact();
        resultFact.setPaymentId(request.getPaymentId());

        List<Object> facts = List.of(
                poFact,
                custFact,
                financeFact,
                resultFact
        );

        // 2. Fire rules
        session.execute(facts);

        // 3. Map resultFact → RuleResponseDto
        RuleResponseDto resp = new RuleResponseDto();
        resp.setPaymentId(resultFact.getPaymentId());
        resp.setStatus(resultFact.getStatus());
        resp.setMessages(
                resultFact.getMessages().stream()
                        .map(msg -> {
                            // split [REJECT] / [REVIEW] just to put some code
                            String code = msg.startsWith("[REJECT]") ? "REJECT" :
                                          msg.startsWith("[REVIEW]") ? "REVIEW" : "INFO";
                            String text = msg.replace("[REJECT] ", "").replace("[REVIEW] ", "");
                            return new ValidationMessageDto(code, text);
                        })
                        .collect(Collectors.toList())
        );

        return resp;
    }

    private PaymentOrderFact mapPayment(RuleRequestDto req) {
        PaymentOrderFact f = new PaymentOrderFact();
        f.setPaymentId(req.getPaymentId());
        f.setAmount(req.getAmount());
        f.setCurrency(req.getCurrency());
        f.setOriginCountry(req.getOriginCountry());
        f.setDestinationCountry(req.getDestinationCountry());
        f.setPaymentMethod(req.getPaymentMethod());
        f.setTerms(req.getTerms());
        f.setOriginHighRisk(req.isOriginHighRisk());
        f.setOriginSanctioned(req.isOriginSanctioned());
        f.setDestHighRisk(req.isDestHighRisk());
        f.setDestSanctioned(req.isDestSanctioned());
        return f;
    }

    private CustomerFact mapCustomer(CustomerDto dto) {
        CustomerFact f = new CustomerFact();
        f.setCustomerId(dto.getCustomerId());
        f.setCountry(dto.getCountry());
        f.setSegment(dto.getSegment());
        f.setRiskCategory(dto.getRiskCategory());
        f.setBlocked(dto.isBlocked());
        return f;
    }

    private CustomerFinanceSummaryFact mapFinance(CustomerFinanceSummaryDto dto) {
        CustomerFinanceSummaryFact f = new CustomerFinanceSummaryFact();
        // we don't have customerId in DTO, but we don't need it in rules either
        f.setTotalOutstanding(dto.getTotalOutstanding());
        f.setCreditLimit(dto.getCreditLimit());
        f.setOverdueInvoicesCount(dto.getOverdueInvoicesCount());
        f.setOldestInvoiceAgeDays(dto.getOldestInvoiceAgeDays());
        return f;
    }
}
