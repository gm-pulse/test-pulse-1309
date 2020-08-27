package com.pulse.checkout.services;

import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.CarrinhoCompras;
import com.pulse.checkout.model.ProdutoPedido;
import com.pulse.checkout.repository.ProdutoPedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        return valorUnitario.multiply(new BigDecimal(qtdItens).setScale(1, RoundingMode.HALF_UP));
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

    public List<ProdutoPedido> listarProdutosPedidosPorProdutoId(Long produtoId){
        Optional<List<ProdutoPedido>> produtoPedidos =  produtoPedidoRepository.findAllByProduto_Id(produtoId);
        if(!produtoPedidos.isPresent()){
            throw new CheckoutCustomException("Não existem pedidos para o produto de ID " + produtoId);
        }
        return produtoPedidos.get();
    }

    public List<ProdutoPedido> listarProdutosPedidosPorCarrinhoId(Long carrinhoId){

        Optional<List<ProdutoPedido>> produtoPedidos =  produtoPedidoRepository.findAllByCarrinhoCompras_Id(carrinhoId);
        if(!produtoPedidos.isPresent()){
            throw new CheckoutCustomException("Não existem produtos pedidos para o carrinho de compras de ID " + carrinhoId);
        }
        return produtoPedidos.get();
    }

    public List<ProdutoPedido> listarProdutosPedidosPorClienteId(Long clienteId){
        Optional<List<ProdutoPedido>> produtoPedidos =  produtoPedidoRepository.findAllByCarrinhoCompras_Cliente_Id(clienteId);
        if(!produtoPedidos.isPresent()){
            throw new CheckoutCustomException("Não existem produtos pedidos para o cliente de ID " + clienteId);
        }
        return produtoPedidos.get();
    }

    public ProdutoPedido buscaPorId(Long id) {
        return produtoPedidoRepository.findById(id)
                .orElseThrow(() -> new CheckoutCustomException("Produto Pedido com ID " + id + " inexiste no banco"));
    }

}
