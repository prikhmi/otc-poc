package com.priya.otc.orchestrator.domain;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    private String customerId;

    private String name;
    private String country;
    private String segment;
    private String riskCategory;
    private boolean blocked;

    // getters & setters
}
