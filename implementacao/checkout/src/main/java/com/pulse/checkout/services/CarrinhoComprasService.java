package com.pulse.checkout.services;

import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.CarrinhoCompras;
import com.pulse.checkout.model.Produto;
import com.pulse.checkout.model.ProdutoPedido;
import com.pulse.checkout.repository.CarrinhoComprasRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class CarrinhoComprasService {

    private final CarrinhoComprasRepository carrinhoComprasRepository;

    private final ProdutoPedidoService produtoPedidoService;

    public CarrinhoCompras salvar(CarrinhoCompras carrinhoCompras) {
        return criar(carrinhoCompras);
    }

    private CarrinhoCompras criar(CarrinhoCompras carrinhoCompras) {

        if (carrinhoCompras.getId() != null) {
            throw new CheckoutCustomException("Carrinho já existe e não pode ser criado");

        }
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
                .orElseThrow(() -> new CheckoutCustomException("Carrinho de compras com " + id + " inexistente no banco"));
    }

    public CarrinhoCompras adicionaProdutosCarrinho(Produto produto, Integer qtdItens, CarrinhoCompras carrinhoCompras) {
        if (carrinhoCompras.getId() == null) {
            throw new CheckoutCustomException("Carrinho de compras não existe");
        }
        produtoPedidoService.salvar(ProdutoPedido.builder().produto(produto).qtdItens(qtdItens).carrinhoCompras(carrinhoCompras).build());

        return atualizaCarrinho(carrinhoCompras);
    }

    public CarrinhoCompras removeProdutosCarrinho(ProdutoPedido produtoPedido, Integer qtdItens, CarrinhoCompras carrinhoCompras) {
        if (carrinhoCompras.getId() == null) {
            throw new CheckoutCustomException("Carrinho de compras não existe");
        }
        produtoPedidoService.removeProdutos(ProdutoPedido.builder().produto(produtoPedido.getProduto()).qtdItens(qtdItens).carrinhoCompras(carrinhoCompras).build(), qtdItens);
        atualizaCarrinho(carrinhoCompras);

        return atualizaCarrinho(carrinhoCompras);
    }

    private CarrinhoCompras atualizaCarrinho(CarrinhoCompras carrinhoCompras) {
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

}
