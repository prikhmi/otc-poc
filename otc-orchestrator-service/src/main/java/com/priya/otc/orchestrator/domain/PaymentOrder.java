package com.priya.otc.orchestrator.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "payment_order")
public class PaymentOrder {

    @Id
    
    private String paymentId;
    private String customerId;
    private String shipmentId;

    private BigDecimal amount;
    private String currency;

    private String originCountry;
    private String destinationCountry;

    private String paymentMethod;
    private String terms;

    private LocalDate requestedDate;

    // getters & setters
}
