package com.pulse.checkout.services;

import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.Transportadora;
import com.pulse.checkout.repository.PagamentoRepository;
import com.pulse.checkout.repository.TransportadoraRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransportadoraService {

    private final TransportadoraRepository transportadoraRepository;

    private final PagamentoRepository pagamentoRepository;

    public Transportadora salvar(Transportadora transportadora) {
        verificaCnpjJaCadastrado(transportadora.getCnpj());
        return transportadoraRepository.save(transportadora);
    }

    public Transportadora alterar(Transportadora transportadora) {
        if (transportadora.getId() == null) {
            throw new CheckoutCustomException("Transportadora com ID não informado!");
        }
        return transportadoraRepository.findById(transportadora.getId())
                .map(transportadoraAlterada -> {
                            if (!transportadora.getCnpj().equals(transportadoraAlterada.getCnpj()))
                                verificaCnpjJaCadastrado(transportadora.getCnpj());
                            return transportadoraRepository.save(transportadora);
                        }
                )
                .orElseThrow(
                        () -> new CheckoutCustomException("Transportadora não se encontra salva e não pode ser alterada")
                );
    }

    public Transportadora buscaPorId(Long id) {
        return transportadoraRepository.findById(id)
                .orElseThrow(() -> new CheckoutCustomException("Transportadora com " + id + " inexistente no banco"));
    }

    public Transportadora buscaPorCnpj(String cnpj) {
        return transportadoraRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new CheckoutCustomException("Transportadora com " + cnpj + " inexistente no banco"));
    }

    private void verificaCnpjJaCadastrado(String cnpj) {
        if (transportadoraRepository.findByCnpj(cnpj).isPresent()) {
            throw new CheckoutCustomException("Transportadora com o mesmo CNPJ já se encontra cadastrada");
        }
    }

    public void deletaTransportadoraPorId(Long id) {
        Transportadora transportadora = buscaPorId(id);

        verificaTransportadora(transportadora);

        transportadoraRepository.delete(transportadora);
    }

    private void verificaTransportadora(Transportadora transportadora){
        if(pagamentoRepository.findAllByTransportadora(transportadora).isPresent()){
            throw new CheckoutCustomException("A transportadora está inclusa em um pagamento e não pode ser excluída");
        }
    }

}
