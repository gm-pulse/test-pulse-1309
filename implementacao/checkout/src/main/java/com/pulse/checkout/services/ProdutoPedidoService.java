package com.pulse.checkout.services;

import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.CarrinhoCompras;
import com.pulse.checkout.model.ProdutoPedido;
import com.pulse.checkout.repository.ProdutoPedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProdutoPedidoService {

    private final ProdutoPedidoRepository produtoPedidoRepository;

    public ProdutoPedido salvar(ProdutoPedido produtoPedido) {
        produtoPedido = verificaTotalMesmoProdutoNoCarrinho(produtoPedido);
        validaValorTotalDoProduto(produtoPedido);
        return produtoPedidoRepository.save(produtoPedido);
    }

    public ProdutoPedido removeProdutos(ProdutoPedido produtoPedido, Integer qtdItens){
        if(qtdItens>produtoPedido.getQtdItens()){
            throw new CheckoutCustomException("A quantidade de produto(s) a se remover não pode ser maior que a presente no carrinho");
        }
        produtoPedido.setQtdItens(produtoPedido.getQtdItens()-qtdItens);
        produtoPedido.setValorTotal(produtoPedido.getValorTotal().subtract(
                calculaValorTotalProdutoPedido(qtdItens, produtoPedido.getProduto().getValorUnitario())
        ));
        validaValorTotalDoProduto(produtoPedido);
        return produtoPedidoRepository.save(produtoPedido);
    }

    private ProdutoPedido verificaTotalMesmoProdutoNoCarrinho(ProdutoPedido produtoPedido) {
        Optional<ProdutoPedido> produtoPedidoOptional = produtoPedidoRepository.findByProdutoAndCarrinhoCompras(produtoPedido.getProduto(), produtoPedido.getCarrinhoCompras());
        return produtoPedidoOptional.map(pedido -> adicionarItensAMaisAProdutoPedido(pedido, produtoPedido.getQtdItens(), produtoPedido.getProduto().getValorUnitario())).orElseGet(() -> criarProdutoPedido(produtoPedido));
    }

    private ProdutoPedido criarProdutoPedido(ProdutoPedido produtoPedido) {
        if (produtoPedido.getValorTotal() != null) {
            return produtoPedido;
        }
        produtoPedido.setValorTotal(calculaValorTotalProdutoPedido(produtoPedido.getQtdItens(), produtoPedido.getProduto().getValorUnitario()));
        return produtoPedido;
    }

    private BigDecimal calculaValorTotalProdutoPedido(Integer qtdItens, BigDecimal valorUnitario) {
        return BigDecimal.valueOf(qtdItens).multiply(valorUnitario);
    }

    private ProdutoPedido adicionarItensAMaisAProdutoPedido(ProdutoPedido produtoPedido, Integer qtdItens, BigDecimal valorunitarioProduto) {
        produtoPedido.setQtdItens(produtoPedido.getQtdItens() + qtdItens);
        produtoPedido.setValorTotal(produtoPedido.getValorTotal().add(
                calculaValorTotalProdutoPedido(qtdItens, valorunitarioProduto)
        ));

        return produtoPedido;
    }

    private void validaValorTotalDoProduto(ProdutoPedido produtoPedido) {
        BigDecimal valorCorreto = calculaValorTotalProdutoPedido(produtoPedido.getQtdItens(), produtoPedido.getProduto().getValorUnitario());
        if (!produtoPedido.getValorTotal().equals(valorCorreto)) {
            throw new CheckoutCustomException("O valor total do produto não corresponde com o valor correto");
        }
    }

    public List<ProdutoPedido> listarProdutosPedidosDeCarrinho(CarrinhoCompras carrinhoCompras){
        return produtoPedidoRepository.findAllByCarrinhoCompras(carrinhoCompras).get();
    }

    public ProdutoPedido buscaPorId(Long id) {
        return produtoPedidoRepository.findById(id)
                .orElseThrow(() -> new CheckoutCustomException("Produto Pedido com ID " + id + " inexiste no banco"));
    }

}
