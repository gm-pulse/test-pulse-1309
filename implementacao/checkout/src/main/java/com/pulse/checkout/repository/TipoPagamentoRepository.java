package com.pulse.checkout.repository;

import com.pulse.checkout.model.TipoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TipoPagamentoRepository extends JpaRepository<TipoPagamento, Long> {

    @Query("select tp from TipoPagamento tp where trim(upper(tp.descricao)) = trim(upper(:descricao) ) ")
    Optional<TipoPagamento> findByDescricao(String descricao);

}
