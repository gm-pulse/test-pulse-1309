package com.pulse.checkout.services;

import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.Cliente;
import com.pulse.checkout.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente) {
        verificaCpfJaCadastrado(cliente.getCpf());
        return clienteRepository.save(cliente);
    }

    public Cliente alterar(Cliente cliente) {
        if (cliente.getId() == null) {
            throw new CheckoutCustomException("Cliente com ID não informado!");
        }
        return clienteRepository.findById(cliente.getId())
                .map(clienteAlterado -> {
                            if (!cliente.getCpf().equals(clienteAlterado.getCpf()))
                                verificaCpfJaCadastrado(cliente.getCpf());

                            return clienteRepository.save(cliente);
                        }
                )
                .orElseThrow(
                        () -> new CheckoutCustomException("Cliente não se encontra salvo e não pode ser alterado")
                );
    }

    public Cliente buscaPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new CheckoutCustomException("Cliente com " + id + " inexistente no banco"));
    }

    public Cliente buscaPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new CheckoutCustomException("Cliente com " + cpf + " inexistente no banco"));
    }

    private void verificaCpfJaCadastrado(String cpf) {
        if (clienteRepository.findByCpf(cpf).isPresent()) {
            throw new CheckoutCustomException("Cliente com o mesmo CPF já se encontra cadastrado");
        }
    }
}
