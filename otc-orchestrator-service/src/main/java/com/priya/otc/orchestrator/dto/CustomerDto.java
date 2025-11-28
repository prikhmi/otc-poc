package com.priya.otc.orchestrator.dto;

import lombok.Data;

@Data
public class CustomerDto {
    private String customerId;
    private String country;
    private String segment;
    private String riskCategory;
    private boolean blocked;

    // getters & setters
}
