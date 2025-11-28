package com.priya.otc.rules.controller;

import com.priya.otc.rules.dto.RuleRequestDto;
import com.priya.otc.rules.dto.RuleResponseDto;
import com.priya.otc.rules.service.RuleEngineService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rules")
public class RuleValidationController {

    private final RuleEngineService ruleEngineService;

    public RuleValidationController(RuleEngineService ruleEngineService) {
        this.ruleEngineService = ruleEngineService;
    }

    @PostMapping("/validate")
    public RuleResponseDto validate(@Valid @RequestBody RuleRequestDto request) {
        return ruleEngineService.validate(request);
    }
}
