package com.pulse.checkout.repository;

import com.pulse.checkout.model.TipoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoPagamentoRepository extends JpaRepository<TipoPagamento, Long> {
}
