package com.pulse.checkout.repository;

import com.pulse.checkout.model.CarrinhoCompras;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CarrinhoComprasRepository extends JpaRepository<CarrinhoCompras, Long> {

    List<CarrinhoCompras> findAllByCliente_Id(Long clienteId);
}
