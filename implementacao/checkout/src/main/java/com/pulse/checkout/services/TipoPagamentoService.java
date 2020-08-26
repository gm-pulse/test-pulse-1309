package com.pulse.checkout.services;

import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.TipoPagamento;
import com.pulse.checkout.repository.PagamentoRepository;
import com.pulse.checkout.repository.TipoPagamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TipoPagamentoService {

    private final TipoPagamentoRepository tipoPagamentoRepository;

    private final PagamentoRepository pagamentoRepository;

    public TipoPagamento salvar(TipoPagamento tipoPagamento){
        validaJaExisteTipoPagamento(tipoPagamento.getDescricao());
        return tipoPagamentoRepository.save(tipoPagamento);
    }

    private void validaJaExisteTipoPagamento(String descricao){
        if(tipoPagamentoRepository.findByDescricao(descricao).isPresent()){
            throw new CheckoutCustomException("Tipo de pagamento já cadastrado");
        }

    }

    public TipoPagamento alterar(TipoPagamento tipoPagamento){
        if (tipoPagamento.getId() == null) {
            throw new CheckoutCustomException("Tipo de Pagamento com ID não informado!");
        }
        return tipoPagamentoRepository.findById(tipoPagamento.getId())
                .map(tipoPagamentoAlterado -> {
                            if (!tipoPagamento.getDescricao().equals(tipoPagamentoAlterado.getDescricao()))
                                validaJaExisteTipoPagamento(tipoPagamento.getDescricao());

                            return tipoPagamentoRepository.save(tipoPagamento);
                        }
                )
                .orElseThrow(
                        () -> new CheckoutCustomException("Tipo de Pagamento não existe e não pode ser alterado")
                );
    }

    public TipoPagamento buscaPorId(Long id) {
        return tipoPagamentoRepository.findById(id)
                .orElseThrow(() -> new CheckoutCustomException("Tipo de Pagamento com " + id + " inexistente no banco"));
    }

    public void deletaTipoPagamentoPorId(Long id) {
        TipoPagamento tipoPagamento = buscaPorId(id);
        verificaEmPagamento(tipoPagamento);

        tipoPagamentoRepository.delete(tipoPagamento);

    }

    private void verificaEmPagamento(TipoPagamento tipoPagamento){
        if(pagamentoRepository.findAllByTipoPagamento(tipoPagamento).isPresent()){
            throw new CheckoutCustomException("O tipo de Pagamento está incluso em um pagamento e não pode ser excluído");
        }
    }
}
