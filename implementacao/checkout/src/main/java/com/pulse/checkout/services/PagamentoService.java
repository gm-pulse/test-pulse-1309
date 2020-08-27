package com.pulse.checkout.services;

import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.*;
import com.pulse.checkout.repository.PagamentoRepository;
import com.pulse.checkout.repository.TipoPagamentoRepository;
import com.pulse.checkout.repository.TransportadoraRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    private final CarrinhoComprasService carrinhoComprasService;
    private final TipoPagamentoService tipoPagamentoService;
    private final TransportadoraService transportadoraService;
    private final EnderecoService enderecoService;
    private final CompraService compraService;
    private final ClienteService clienteService;

    public Pagamento salvar(Pagamento pagamento) {
        verificaCarrinhoCompras(pagamento.getCarrinhoCompras());
        verificaTipoPagamento(pagamento.getTipoPagamento());
        verificaTransportadora(pagamento.getTransportadora());
        verificaEnderecoEntrega(pagamento.getEnderecoEntrega());

        return pagamentoRepository.save(verificaValorPagamento(pagamento));
    }

    public Pagamento fazCheckout(Long idTipoPagamento, Long idCarrinho, Long idTransportadora, Long idEnderecoEntrega){
        TipoPagamento tipoPagamento = tipoPagamentoService.buscaPorId(idTipoPagamento);
        CarrinhoCompras carrinhoCompras = carrinhoComprasService.buscaPorId(idCarrinho);
        Endereco enderecoEntrega = enderecoService.buscaPorId(idEnderecoEntrega);
        Transportadora transportadora = transportadoraService.buscaPorId(idTransportadora);

        verificaCarrinhoComprasJaPago(carrinhoCompras);

        Pagamento novoPagamento = new Pagamento(null, tipoPagamento, carrinhoCompras, transportadora,enderecoEntrega,null,null);

        novoPagamento.setCompra(compraService.salvar(new Compra()));

        pagamentoRepository.save(verificaValorPagamento(novoPagamento));

        return novoPagamento;

    }

    private void verificaCarrinhoComprasJaPago(CarrinhoCompras carrinhoCompras) {
        if(pagamentoRepository.findAllByCarrinhoCompras(carrinhoCompras).isPresent()){
            throw new CheckoutCustomException("Já existe um pagamento para o carrinho de compras selecionado");
        }
    }

    public Pagamento alterar(Pagamento pagamento) {
        if (pagamento.getId() == null) {
            throw new CheckoutCustomException("Pagamento com ID não informado");
        }
        return pagamentoRepository.findById(pagamento.getId())
                .map(pagamentoAlterado -> {
                    if (!pagamentoAlterado.getValorTotal().equals(pagamento.getValorTotal())) {
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
        if (carrinhoCompras != null) {
            if (carrinhoComprasService.buscaPorId(carrinhoCompras.getId()) == null) {
                throw new CheckoutCustomException("O carrinho de compras é inexistente");
            }
        } else {
            throw new CheckoutCustomException("O carrinho de compras não foi informado");
        }

    }

    private void verificaTipoPagamento(TipoPagamento tipoPagamento) {
        if (tipoPagamento != null) {
            if (tipoPagamentoService.buscaPorId(tipoPagamento.getId()) == null) {
                throw new CheckoutCustomException("O tipo de pagamento é inexistente");
            }
        } else {
            throw new CheckoutCustomException("O tipo de pagamento não foi informado");

        }

    }

    private void verificaTransportadora(Transportadora transportadora) {
        if (transportadora != null) {
            if (transportadoraService.buscaPorId(transportadora.getId()) == null) {
                throw new CheckoutCustomException("A transportadora é inexistente");
            }
        } else {
            throw new CheckoutCustomException("A transportadora não foi informada");
        }

    }

    private void verificaEnderecoEntrega(Endereco endereco) {
        if (endereco != null) {
            if (enderecoService.buscaPorId(endereco.getId()) == null) {
                throw new CheckoutCustomException("O endereco de entrega é inxistente");
            }
        } else {
            throw new CheckoutCustomException("O endereco de entrega não foi informado");
        }
    }

    private Pagamento verificaValorPagamento(Pagamento pagamento) {

        CarrinhoCompras carrinhoCompras = carrinhoComprasService.buscaPorId(pagamento.getCarrinhoCompras().getId());
        Transportadora transportadora = transportadoraService.buscaPorId(pagamento.getTransportadora().getId());
        pagamento.setValorTotal(calculaValorPagamento(carrinhoCompras, transportadora));

        return pagamento;

    }

    private BigDecimal calculaValorPagamento(CarrinhoCompras carrinhoCompras, Transportadora transportadora) {
        return carrinhoCompras.getValorTotal().add(transportadora.getValorFrete());
    }

    public List<Pagamento> listaPorClienteId(Long clienteId){
        Cliente cliente = clienteService.buscaPorId(clienteId);
        Optional<List<Pagamento>> pagamentos = pagamentoRepository.findAllByCarrinhoCompras_Cliente(cliente);

        if(!pagamentos.isPresent()){
            throw new CheckoutCustomException("O cliente "+cliente.getNome() + " não possui nenhum pagamento");
        }
        return pagamentos.get();

    }

    public Pagamento alteraTipoPagamento(Long idPagamento, Long idTipoPagamento)
    {
        Pagamento pagamento = buscaPorId(idPagamento);
        if(pagamento.getCompra().getStatusCompra().getId().equals(3L)){
            throw new CheckoutCustomException("A compra já foi concluída e não pode ser alterado seu tipo pagamento");
        }
        TipoPagamento tipoPagamento = tipoPagamentoService.buscaPorId(idTipoPagamento);
        pagamento.setTipoPagamento(tipoPagamento);

        return pagamentoRepository.save(pagamento);
    }



}
