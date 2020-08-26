package com.pulse.checkout.services;

import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.StatusCompra;
import com.pulse.checkout.repository.CompraRepository;
import com.pulse.checkout.repository.StatusCompraRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatusCompraService {

    private final StatusCompraRepository statusCompraRepository;

    private final CompraRepository compraRepository;

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
                .orElseThrow(() -> new CheckoutCustomException("Status de Compra com ID " + id + " inexistente no banco"));
    }

    public void deletaStatusCompraPorId(Long id) {
        StatusCompra statusCompra = buscaPorId(id);

        verificaStatusCompraEmCompra(statusCompra);
        statusCompraRepository.delete(statusCompra);
    }

    private void verificaStatusCompraEmCompra(StatusCompra statusCompra){
        if(compraRepository.findAllByStatusCompra(statusCompra).isPresent()){
            throw new CheckoutCustomException("O status compra está associado a uma compra e não pode ser excluído");
        }
    }
}
