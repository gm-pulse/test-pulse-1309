package com.pulse.checkout.repository;

import com.pulse.checkout.model.Transportadora;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransportadoraRepository extends JpaRepository<Transportadora, Long> {

    Optional<Transportadora> findByCnpj(String cnpj);
}
