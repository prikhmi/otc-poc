package com.priya.otc.orchestrator.repository;

import com.priya.otc.orchestrator.domain.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, String> {
}
