package br.com.tracefinance.walletControl.domain.repository;

import br.com.tracefinance.walletControl.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
