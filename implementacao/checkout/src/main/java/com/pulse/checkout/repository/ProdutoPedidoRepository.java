package com.pulse.checkout.repository;

import com.pulse.checkout.model.CarrinhoCompras;
import com.pulse.checkout.model.Produto;
import com.pulse.checkout.model.ProdutoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoPedidoRepository extends JpaRepository<ProdutoPedido, Long> {

    Optional<ProdutoPedido> findByProdutoAndCarrinhoCompras(Produto produto, CarrinhoCompras carrinho);

    Optional<List<ProdutoPedido>> findAllByCarrinhoCompras(CarrinhoCompras carrinho);

    Optional<List<ProdutoPedido>> findAllByCarrinhoCompras_Id(Long carrinhoId);

    Optional<List<ProdutoPedido>> findAllByProduto(Produto produto);

    Optional<List<ProdutoPedido>> findAllByProduto_Id(Long produtoId);

    Optional<List<ProdutoPedido>> findAllByCarrinhoCompras_Cliente_Id(Long clienteId);

}
