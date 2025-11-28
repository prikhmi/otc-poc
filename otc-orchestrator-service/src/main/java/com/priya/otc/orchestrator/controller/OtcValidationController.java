package com.priya.otc.orchestrator.controller;

import com.priya.otc.orchestrator.dto.OtcValidationResponseDto;
import com.priya.otc.orchestrator.service.OtcValidationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otc")
public class OtcValidationController {

    private final OtcValidationService service;

    public OtcValidationController(OtcValidationService service) {
        this.service = service;
    }

    @GetMapping("/validate/{paymentId}")
    public OtcValidationResponseDto validate(@PathVariable String paymentId) {
        return service.validatePayment(paymentId);
    }
}
