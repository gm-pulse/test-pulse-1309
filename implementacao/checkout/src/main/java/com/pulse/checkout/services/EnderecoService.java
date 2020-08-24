package com.pulse.checkout.services;

import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.Cliente;
import com.pulse.checkout.model.Endereco;
import com.pulse.checkout.repository.EnderecoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public Endereco salvar(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    public Endereco alterar(Endereco endereco) {
        if (endereco.getId() == null) {
            throw new CheckoutCustomException("Endereco com ID não informado!");
        }
        return enderecoRepository.findById(endereco.getId())
                .map(enderecoAlterado -> enderecoRepository.save(endereco)
                )
                .orElseThrow(
                        () -> new CheckoutCustomException("Endereco não se encontra salvo e não pode ser alterado")
                );
    }

    public Endereco buscaPorId(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new CheckoutCustomException("Endereco com " + id + " inexiste no banco"));
    }
}
