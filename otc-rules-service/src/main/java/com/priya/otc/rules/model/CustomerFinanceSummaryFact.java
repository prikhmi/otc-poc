package com.priya.otc.rules.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CustomerFinanceSummaryFact {

    private String customerId;
    private BigDecimal totalOutstanding;
    private BigDecimal creditLimit;
    private int overdueInvoicesCount;
    private int oldestInvoiceAgeDays;

    // getters & setters
}
