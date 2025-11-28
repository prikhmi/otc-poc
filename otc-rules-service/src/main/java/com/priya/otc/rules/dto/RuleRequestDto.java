package com.priya.otc.rules.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RuleRequestDto {

    private String paymentId;
    private BigDecimal amount;
    private String currency;
    private String originCountry;
    private String destinationCountry;

    private String paymentMethod;  // INVOICE/CARD/CASH
    private String terms;          // NET30/PREPAID

    // risk flags, pre-computed in orchestrator
    private boolean originHighRisk;
    private boolean originSanctioned;
    private boolean destHighRisk;
    private boolean destSanctioned;

    private CustomerDto customer;
    private CustomerFinanceSummaryDto customerFinance;

    // getters & setters
}
