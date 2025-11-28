package com.priya.otc.orchestrator.dto;

import lombok.Data;

@Data
public class ValidationMessageDto {

    private String code;
    private String text;

    // constructors
    public ValidationMessageDto() {
    }

    public ValidationMessageDto(String code, String text) {
        this.code = code;
        this.text = text;
    }

    // getters & setters
}
