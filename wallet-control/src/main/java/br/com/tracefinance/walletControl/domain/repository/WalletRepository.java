package br.com.tracefinance.walletControl.domain.repository;

import br.com.tracefinance.walletControl.domain.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
