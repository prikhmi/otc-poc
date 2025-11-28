package com.priya.otc.rules.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CustomerFinanceSummaryDto {

    private BigDecimal totalOutstanding;
    private BigDecimal creditLimit;
    private int overdueInvoicesCount;
    private int oldestInvoiceAgeDays;

    // getters & setters
}
