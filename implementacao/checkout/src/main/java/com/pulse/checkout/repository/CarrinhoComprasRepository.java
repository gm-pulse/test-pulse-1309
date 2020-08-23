package com.pulse.checkout.repository;

import com.pulse.checkout.model.CarrinhoCompras;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrinhoComprasRepository extends JpaRepository<CarrinhoCompras, Long> {
}
