package com.priya.otc.orchestrator.dto;

import java.util.List;

import lombok.Data;

@Data
public class OtcValidationResponseDto {

    private String paymentId;
    private String status;  // APPROVED / REJECTED / MANUAL_REVIEW / PENDING_RULES etc.
    private List<ValidationMessageDto> messages;

    // getters & setters
}
