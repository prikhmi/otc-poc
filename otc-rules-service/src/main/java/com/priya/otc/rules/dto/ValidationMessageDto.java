package com.priya.otc.rules.dto;

import lombok.Data;

@Data
public class ValidationMessageDto {

    private String code;
    private String text;

    public ValidationMessageDto() {}

    public ValidationMessageDto(String code, String text) {
        this.code = code;
        this.text = text;
    }

    // getters & setters
}
