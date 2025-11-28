package com.priya.otc.orchestrator.repository;

import com.priya.otc.orchestrator.domain.CustomerFinanceSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerFinanceSummaryRepository extends JpaRepository<CustomerFinanceSummary, String> {
}
