package com.pulse.checkout.repository;

import com.pulse.checkout.model.Compra;
import com.pulse.checkout.model.StatusCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompraRepository extends JpaRepository<Compra, Long> {

    @Query("select c from Compra c where trim(upper(c.numPedido)) = trim(upper(:numPedido) ) ")
    Optional<Compra> findByNumPedido(String numPedido);

    @Query("select c from Compra c where trim(upper(c.codRastreio)) = trim(upper(:codRastreio) ) ")
    Optional<Compra> findByCodRastreio(String codRastreio);

    Optional<List<Compra>> findAllByStatusCompra(StatusCompra statusCompra);

}
