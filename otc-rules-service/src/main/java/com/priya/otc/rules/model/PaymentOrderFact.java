package com.priya.otc.rules.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PaymentOrderFact {

    private String paymentId;
    private BigDecimal amount;
    private String currency;
    private String originCountry;
    private String destinationCountry;

    private String paymentMethod;  // INVOICE/CARD/CASH
    private String terms;          // NET30/PREPAID

    private boolean originHighRisk;
    private boolean originSanctioned;
    private boolean destHighRisk;
    private boolean destSanctioned;

    // getters & setters
}
