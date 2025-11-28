package com.priya.otc.orchestrator.repository;

import com.priya.otc.orchestrator.domain.CountryRisk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRiskRepository extends JpaRepository<CountryRisk, String> {
}
