package com.pulse.checkout.repository;

import com.pulse.checkout.model.CarrinhoCompras;
import com.pulse.checkout.model.Produto;
import com.pulse.checkout.model.ProdutoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProdutoPedidoRepository extends JpaRepository<ProdutoPedido, Long> {

    Optional<ProdutoPedido> findByProdutoAndCarrinhoCompras(Produto produto, CarrinhoCompras carrinho);

    Optional<List<ProdutoPedido>> findAllByCarrinhoCompras(CarrinhoCompras carrinho);

    Optional<List<ProdutoPedido>> findAllByProduto(Produto produto);

}
