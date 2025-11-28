package com.priya.otc.rules.model;

import lombok.Data;

@Data
public class CustomerFact {

    private String customerId;
    private String country;
    private String segment;
    private String riskCategory;
    private boolean blocked;

    // getters & setters
}
