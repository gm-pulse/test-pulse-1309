package com.pulse.checkout.services;

import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.CarrinhoCompras;
import com.pulse.checkout.model.Produto;
import com.pulse.checkout.model.ProdutoPedido;
import com.pulse.checkout.repository.CarrinhoComprasRepository;
import com.pulse.checkout.repository.PagamentoRepository;
import com.pulse.checkout.repository.ProdutoPedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarrinhoComprasService {

    private final CarrinhoComprasRepository carrinhoComprasRepository;

    private final ProdutoPedidoService produtoPedidoService;

    private final PagamentoRepository pagamentoRepository;

    private final ProdutoService produtoService;

    private final ProdutoPedidoRepository produtoPedidoRepository;

    public CarrinhoCompras salvar(CarrinhoCompras carrinhoCompras) {
        return criar(carrinhoCompras);
    }

    private CarrinhoCompras criar(CarrinhoCompras carrinhoCompras) {

        carrinhoCompras.setTotalItens(0);
        carrinhoCompras.setValorTotal(new BigDecimal("0.0"));

        return carrinhoComprasRepository.save(carrinhoCompras);
    }

    public CarrinhoCompras alterar(CarrinhoCompras carrinhoCompras) {
        if (carrinhoCompras.getId() == null) {
            throw new CheckoutCustomException("Carrinho de Compras com ID não informado");
        }
        return carrinhoComprasRepository.findById(carrinhoCompras.getId())
                .map(carrinhoComprasAlterado -> {
                    if(!carrinhoComprasAlterado.getValorTotal().equals(carrinhoCompras.getValorTotal())){
                        return carrinhoComprasRepository.save(atualizaCarrinho(carrinhoCompras));
                    }
                    return carrinhoComprasRepository.save(carrinhoCompras);
                })
                .orElseThrow(
                        () -> new CheckoutCustomException("Carrinho de compras não existe e não pode ser alterado ")
                );
    }

    public CarrinhoCompras buscaPorId(Long id) {
        return carrinhoComprasRepository.findById(id)
                .orElseThrow(() -> new CheckoutCustomException("Carrinho de compras com ID " + id + " inexistente no banco"));
    }

    public CarrinhoCompras adicionaProdutosCarrinho(Long produtoId, Integer qtdItens, Long carrinhoComprasId) {
        if (carrinhoComprasId == null) {
            throw new CheckoutCustomException("Carrinho de compras não existe");
        }
        CarrinhoCompras carrinhoCompras = buscaPorId(carrinhoComprasId);
        verificaCarrinhoEmPagamento(carrinhoCompras);

        Produto produto = produtoService.buscaPorId(produtoId);

        produtoPedidoService.salvar(ProdutoPedido.builder().produto(produto).qtdItens(qtdItens).carrinhoCompras(carrinhoCompras).build());

        return atualizaCarrinho(carrinhoCompras);
    }

    public CarrinhoCompras removeProdutosCarrinho(Long idProduto, Integer qtdItens, Long idCarrinho) {
        if (idCarrinho == null) {
            throw new CheckoutCustomException("Carrinho de compras não existe");
        }
        CarrinhoCompras carrinhoCompras = buscaPorId(idCarrinho);
        verificaCarrinhoEmPagamento(carrinhoCompras);
        Produto produto = produtoService.buscaPorId(idProduto);
        Optional<ProdutoPedido> produtoPedido = produtoPedidoRepository.findByProdutoAndCarrinhoCompras(produto, carrinhoCompras);
        if(!produtoPedido.isPresent()){
            throw new CheckoutCustomException("Não existe o produto especificado no carrinho de compras");
        }

        produtoPedidoService.removeProdutos(produtoPedido.get(), qtdItens);

        return atualizaCarrinho(carrinhoCompras);
    }

    private CarrinhoCompras atualizaCarrinho(CarrinhoCompras carrinhoCompras) {

        verificaCarrinhoEmPagamento(carrinhoCompras);
        List<ProdutoPedido> produtosCarrinho = produtoPedidoService.listarProdutosPedidosDeCarrinho(carrinhoCompras);

        carrinhoCompras.setTotalItens(
                produtosCarrinho.stream().
                        mapToInt(ProdutoPedido::getQtdItens).sum()
        );
        carrinhoCompras.setValorTotal(
                BigDecimal.valueOf(
                        produtosCarrinho.stream().
                                mapToInt(produtoPedido -> produtoPedido.getValorTotal().intValue()).sum())

        );

        return carrinhoComprasRepository.save(carrinhoCompras);
    }

    public List<CarrinhoCompras> buscaPorClienteId(Long clienteId){
        return carrinhoComprasRepository.findAllByCliente_Id(clienteId);

    }

    public void deletaCarrinhoPorId(Long id) {
        CarrinhoCompras carrinho = buscaPorId(id);

        verificaCarrinhoEmPagamento(carrinho);
        verificaCarrinhoEmPedido(carrinho);

        carrinhoComprasRepository.delete(carrinho);

    }

    private void verificaCarrinhoEmPagamento(CarrinhoCompras carrinhoCompras){
        if(pagamentoRepository.findAllByCarrinhoCompras(carrinhoCompras).isPresent()){
            throw new CheckoutCustomException("Já foi realizado o pagamento para o carrinho e ele não pode ser excluído ou alterado");
        }
    }

    private void verificaCarrinhoEmPedido(CarrinhoCompras carrinhoCompras){
        if(produtoPedidoRepository.findAllByCarrinhoCompras(carrinhoCompras).isPresent()){
            produtoPedidoRepository.findAllByCarrinhoCompras(carrinhoCompras)
                    .get()
                    .forEach(produtoPedidoRepository::delete);
        }
    }
}
