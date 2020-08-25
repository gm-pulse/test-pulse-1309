package com.pulse.checkout.repository;

import com.pulse.checkout.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompraRepository extends JpaRepository<Compra, Long> {

    Optional<Compra> findByNumPedido(String numPedido);

    Optional<Compra> findByCodRastreio(String codRastreio);

}
