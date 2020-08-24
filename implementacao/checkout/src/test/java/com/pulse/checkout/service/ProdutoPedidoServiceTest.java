package com.pulse.checkout.service;

import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.CarrinhoCompras;
import com.pulse.checkout.model.Produto;
import com.pulse.checkout.model.ProdutoPedido;
import com.pulse.checkout.repository.ProdutoPedidoRepository;
import com.pulse.checkout.services.ProdutoPedidoService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProdutoPedidoServiceTest {

    @Mock
    ProdutoPedidoRepository produtoPedidoRepository;

    @InjectMocks
    ProdutoPedidoService produtoPedidoService;

    private ProdutoPedido produtoPedido;

    @Mock
    private CarrinhoCompras carrinhoCompras;

    @Mock
    private Produto produto;

    @BeforeEach
    public void criaProdutoPedido(){
        produtoPedido = ProdutoPedido.builder().id(1L).produto(produto).carrinhoCompras(carrinhoCompras).qtdItens(2).valorTotal(new BigDecimal("30.0")).build();
    }

    @Test
    public void retornaProdutoPedido_AoSalvarProdutoPedido(){
        when(produtoPedidoRepository.save(any(ProdutoPedido.class))).thenReturn(produtoPedido);
        when(produtoPedidoRepository.findByProdutoAndCarrinhoCompras(produto, carrinhoCompras)).thenReturn(Optional.empty());
        when(produto.getValorUnitario()).thenReturn(new BigDecimal("15.0"));

        ProdutoPedido novoProdutoPedido =  produtoPedidoService.salvar(ProdutoPedido.builder().id(1L).produto(produto).carrinhoCompras(carrinhoCompras).qtdItens(2).valorTotal(null).build());

        assertEquals(produtoPedido.getId(), novoProdutoPedido.getId());
        assertEquals(2, novoProdutoPedido.getQtdItens());
        assertEquals(new BigDecimal("30.0"),novoProdutoPedido.getValorTotal());
    }

    @Test
    public void retornaProdutoPedidoSalvo_AoSalvarProdutoPedidoDeProdutoJaExistenteNoCarrinho(){
        when(produtoPedidoRepository.save(any(ProdutoPedido.class))).thenReturn(produtoPedido);
        when(produtoPedidoRepository.findByProdutoAndCarrinhoCompras(produto, carrinhoCompras)).thenReturn(Optional.of(produtoPedido));
        when(produto.getValorUnitario()).thenReturn(new BigDecimal("15.0"));

        ProdutoPedido novoProdutoPedido =  produtoPedidoService.salvar(ProdutoPedido.builder().id(1L).produto(produto).carrinhoCompras(carrinhoCompras).qtdItens(2).valorTotal(null).build());

        assertEquals(novoProdutoPedido.getId(), produtoPedido.getId());
        assertEquals(4 ,novoProdutoPedido.getQtdItens());
        assertEquals( new BigDecimal("60.0"),novoProdutoPedido.getValorTotal());

    }

    @Test
    public void lancaExcecao_AoSalvarProdutoPedidoComValorTotalNaoCorrespondente(){
        when(produtoPedidoRepository.save(any(ProdutoPedido.class))).thenReturn(produtoPedido);
        when(produtoPedidoRepository.findByProdutoAndCarrinhoCompras(produto, carrinhoCompras)).thenReturn(Optional.empty());
        when(produto.getValorUnitario()).thenReturn(new BigDecimal("15.0"));

        ProdutoPedido novoProdutoPedido =  ProdutoPedido.builder().id(1L).produto(produto).carrinhoCompras(carrinhoCompras).qtdItens(2).valorTotal(new BigDecimal("20.0")).build();

        assertThrows(CheckoutCustomException.class, () -> produtoPedidoService.salvar(novoProdutoPedido));
    }

    @Test
    public void retornaProdutoPedidoSalvo_AoRemoverProdutos(){
        when(produtoPedidoRepository.save(any(ProdutoPedido.class))).thenReturn(produtoPedido);
        when(produto.getValorUnitario()).thenReturn(new BigDecimal("15.0"));

        ProdutoPedido novoProdutoPedido =  produtoPedidoService.removeProdutos(produtoPedido, 1);
        assertEquals(produtoPedido.getId(), novoProdutoPedido.getId());
        assertEquals(1, novoProdutoPedido.getQtdItens());
        assertEquals(new BigDecimal("15.0"),novoProdutoPedido.getValorTotal());
    }

    @Test
    public void lancaExcecao_AoRemoverNumeroMaiorDeItensDoQueOsExistentes(){
        when(produtoPedidoRepository.save(any(ProdutoPedido.class))).thenReturn(produtoPedido);
        when(produto.getValorUnitario()).thenReturn(new BigDecimal("15.0"));

        assertThrows(CheckoutCustomException.class, () -> produtoPedidoService.removeProdutos(produtoPedido, 3));

    }
}
