package com.priya.otc.orchestrator.domain;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "customer_finance_summary")
public class CustomerFinanceSummary {

    @Id
    private String customerId;

    private java.math.BigDecimal totalOutstanding;
    private java.math.BigDecimal creditLimit;
    private int overdueInvoicesCount;
    private int oldestInvoiceAgeDays;

    // getters & setters
}
