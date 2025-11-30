package com.priya.otc.orchestrator.controller;

import com.priya.otc.orchestrator.dto.OtcValidationRequestDto;
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

    @PostMapping("/validate")
    public OtcValidationResponseDto validate(@RequestBody OtcValidationRequestDto request) {
        return service.validatePayment(request.getPaymentId());
    }
}
