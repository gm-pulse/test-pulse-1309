package com.pulse.checkout.repository;

import com.pulse.checkout.model.Cliente;
import com.pulse.checkout.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCpf(String cpf);

    Optional<List<Cliente>> findAllByEndereco(Endereco endereco);
}
