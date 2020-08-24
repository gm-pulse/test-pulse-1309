package com.pulse.checkout.repository;

import com.pulse.checkout.model.StatusCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StatusCompraRepository extends JpaRepository<StatusCompra, Long> {

    @Query("select sc from StatusCompra sc where trim(upper(sc.descricao)) = trim(upper(:descricao) ) ")
    Optional<StatusCompra> findByDescricao(String descricao);

}
