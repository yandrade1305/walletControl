package br.com.tracefinance.walletControl.domain.repository;

import br.com.tracefinance.walletControl.domain.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet>  findByOwnerName(String ownerName);
}
