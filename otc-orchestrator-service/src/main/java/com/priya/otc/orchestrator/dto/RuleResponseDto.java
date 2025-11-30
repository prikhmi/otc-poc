package com.priya.otc.orchestrator.dto;

import java.util.List;

public class RuleResponseDto {

    private String paymentId;
    private String status; // APPROVED / REJECTED / MANUAL_REVIEW
    private List<ValidationMessageDto> messages;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ValidationMessageDto> getMessages() {
        return messages;
    }

    public void setMessages(List<ValidationMessageDto> messages) {
        this.messages = messages;
    }
}
