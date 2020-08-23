package com.pulse.checkout.repository;

import com.pulse.checkout.model.ProdutoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoPedidoRepository extends JpaRepository<ProdutoPedido, Long> {
}
