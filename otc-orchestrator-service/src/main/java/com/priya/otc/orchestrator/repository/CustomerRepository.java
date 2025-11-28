package com.priya.otc.orchestrator.repository;

import com.priya.otc.orchestrator.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
