package com.pulse.checkout.services;

import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.CarrinhoCompras;
import com.pulse.checkout.model.Pagamento;
import com.pulse.checkout.model.TipoPagamento;
import com.pulse.checkout.model.Transportadora;
import com.pulse.checkout.repository.CarrinhoComprasRepository;
import com.pulse.checkout.repository.PagamentoRepository;
import com.pulse.checkout.repository.TipoPagamentoRepository;
import com.pulse.checkout.repository.TransportadoraRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final CarrinhoComprasRepository carrinhoComprasRepository;
    private final TipoPagamentoRepository tipoPagamentoRepository;
    private final TransportadoraRepository transportadoraRepository;

    public Pagamento salvar(Pagamento pagamento) {
        verificaCarrinhoCompras(pagamento.getCarrinhoCompras());
        verificaTipoPagamento(pagamento.getTipoPagamento());
        verificaTransportadora(pagamento.getTransportadora());
        pagamento = calculaValorPagamento(pagamento);

        return pagamentoRepository.save(pagamento);
    }

    public Pagamento alterar(Pagamento pagamento){
        if(pagamento.getId() == null){
            throw new CheckoutCustomException("Pagamento com ID não informado");
        }
        return pagamentoRepository.findById(pagamento.getId())
                .map(pagamentoAlterado ->{
                    if(!pagamentoAlterado.getValorTotal().equals(pagamento.getValorTotal())) {
                        return pagamentoRepository.save(verificaValorPagamento(pagamento));
                    }
                    return pagamentoRepository.save(pagamento);

                })
                .orElseThrow(
                        () -> new CheckoutCustomException("Pagmento não existe e não pode ser alterado")
                );
    }

    public Pagamento buscaPorId(Long id) {
        return pagamentoRepository.findById(id)
                .orElseThrow(() -> new CheckoutCustomException("Pagamento com " + id + " inexistente no banco"));
    }

    private void verificaCarrinhoCompras(CarrinhoCompras carrinhoCompras) {
        if (carrinhoCompras.getId() == null || !carrinhoComprasRepository.findById(carrinhoCompras.getId()).isPresent()) {
            throw new CheckoutCustomException("O carrinho de compras é inexistente");
        }
    }

    private void verificaTipoPagamento(TipoPagamento tipoPagamento) {
        if (tipoPagamento.getId() == null || !tipoPagamentoRepository.findById(tipoPagamento.getId()).isPresent()) {
            throw new CheckoutCustomException("O tipo de pagamento nao foi informado corretamente");
        }
    }

    private void verificaTransportadora(Transportadora transportadora) {
        if (transportadora.getId() == null || !transportadoraRepository.findById(transportadora.getId()).isPresent()) {
            throw new CheckoutCustomException("A transportadora não foi informada corretamente");
        }
    }

    private Pagamento calculaValorPagamento(Pagamento pagamento) {
        if (pagamento.getValorTotal() != null) {
            return verificaValorPagamento(pagamento);
        }

        pagamento.setValorTotal(pagamento.getCarrinhoCompras().getValorTotal());

        return pagamento;
    }

    private Pagamento verificaValorPagamento(Pagamento pagamento) {
        if (!pagamento.getValorTotal().equals(pagamento.getCarrinhoCompras().getValorTotal())) {
            throw new CheckoutCustomException("O valor total a pagar não corresponde com o valor total encontrado no carrinho de compras");
        }
        return pagamento;

    }


}
