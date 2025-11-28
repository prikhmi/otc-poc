package com.priya.otc.orchestrator.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RuleRequestDto {

    private String paymentId;
    private BigDecimal amount;
    private String currency;
    private String originCountry;
    private String destinationCountry;

    private CustomerDto customer;
    private CustomerFinanceSummaryDto customerFinance;

    // getters & setters
}
