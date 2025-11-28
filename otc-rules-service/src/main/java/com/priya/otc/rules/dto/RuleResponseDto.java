package com.priya.otc.rules.dto;

import java.util.List;

import lombok.Data;

@Data
public class RuleResponseDto {

    private String paymentId;
    private String status; // APPROVED / REJECTED / MANUAL_REVIEW
    private List<ValidationMessageDto> messages;

    // getters & setters
}
