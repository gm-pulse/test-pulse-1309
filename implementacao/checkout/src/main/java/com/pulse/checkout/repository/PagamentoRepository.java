package com.pulse.checkout.repository;

import com.pulse.checkout.model.Endereco;
import com.pulse.checkout.model.Pagamento;
import com.pulse.checkout.model.Transportadora;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    Optional<List<Pagamento>> findAllByEnderecoEntrega(Endereco endereco);

    Optional<List<Pagamento>> findAllByTransportadora(Transportadora transportadora);
}
