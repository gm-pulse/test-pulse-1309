package com.pulse.checkout.services;

import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.StatusCompra;
import com.pulse.checkout.repository.StatusCompraRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatusCompraService {

    private final StatusCompraRepository statusCompraRepository;

    public StatusCompra salvar(StatusCompra statusCompra){
        validaJaExisteStatusCompra(statusCompra.getDescricao());
        return statusCompraRepository.save(statusCompra);
    }

    public void validaJaExisteStatusCompra(String descricao){
        if(statusCompraRepository.findByDescricao(descricao).isPresent()){
            throw new CheckoutCustomException("Status de Compra já cadastrado");
        }
    }

    public StatusCompra alterar(StatusCompra statusCompra){
        if(statusCompra.getId() == null){
            throw new CheckoutCustomException("Status de Compra com ID não informado!");
        }
        return statusCompraRepository.findById(statusCompra.getId())
                .map(statusCompraAlterado -> {
                            if (!statusCompra.getDescricao().equals(statusCompraAlterado.getDescricao()))
                                validaJaExisteStatusCompra(statusCompra.getDescricao());

                            return statusCompraRepository.save(statusCompra);
                        }
                )
                .orElseThrow(
                        () -> new CheckoutCustomException("Status de Compra não existe e não pode ser alterado")
                );
    }

    public StatusCompra buscaPorId(Long id){
        return statusCompraRepository.findById(id)
                .orElseThrow(() -> new CheckoutCustomException("Status de Compra com " + id + " inexistente no banco"));
    }

}
