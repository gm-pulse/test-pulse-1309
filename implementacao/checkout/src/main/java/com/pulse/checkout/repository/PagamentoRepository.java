package com.pulse.checkout.repository;

import com.pulse.checkout.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    Optional<List<Pagamento>> findAllByEnderecoEntrega(Endereco endereco);

    Optional<List<Pagamento>> findAllByTransportadora(Transportadora transportadora);

    Optional<List<Pagamento>> findAllByTipoPagamento(TipoPagamento tipoPagamento);

    Optional<List<Pagamento>> findAllByCarrinhoCompras(CarrinhoCompras carrinhoCompras);
}
