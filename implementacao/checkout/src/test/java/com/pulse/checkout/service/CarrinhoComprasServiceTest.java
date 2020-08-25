package com.pulse.checkout.service;

import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.CarrinhoCompras;
import com.pulse.checkout.model.Cliente;
import com.pulse.checkout.model.Produto;
import com.pulse.checkout.model.ProdutoPedido;
import com.pulse.checkout.repository.CarrinhoComprasRepository;
import com.pulse.checkout.services.CarrinhoComprasService;
import com.pulse.checkout.services.ProdutoPedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class CarrinhoComprasServiceTest {

    @Mock
    CarrinhoComprasRepository carrinhoComprasRepository;

    @InjectMocks
    CarrinhoComprasService carrinhoComprasService;

    private CarrinhoCompras carrinhoCompras;

    @Mock
    Cliente cliente;

    @Mock
    ProdutoPedido produtoPedido;

    @Mock
    ProdutoPedidoService produtoPedidoService;

    @Mock
    Produto produto;

    @BeforeEach
    public void criaCarrinhoCompra(){
        carrinhoCompras = CarrinhoCompras.builder().id(1L).valorTotal(new BigDecimal("150.0")).totalItens(3).cliente(cliente).build();
    }

    @Test
    public void retornaCarrinhoCompra_aoCriarCarrinho(){
        when(carrinhoComprasRepository.save(any(CarrinhoCompras.class))).thenReturn(carrinhoCompras);

        CarrinhoCompras novoCarrinhoCompras = carrinhoComprasService.salvar(CarrinhoCompras.builder().valorTotal(new BigDecimal("150.0")).totalItens(3).cliente(cliente).build());

        assertEquals(carrinhoCompras.getId(), novoCarrinhoCompras.getId());

    }

    @Test
    public void lancaExcecao_AoCriarCarrinhoDeComprasJaExistente(){
        when(carrinhoComprasRepository.save(any(CarrinhoCompras.class))).thenReturn(carrinhoCompras);

        CarrinhoCompras novoCarrinhoCompras = CarrinhoCompras.builder().id(1L).valorTotal(new BigDecimal("150.0")).totalItens(3).cliente(cliente).build();

        assertThrows(CheckoutCustomException.class, () -> carrinhoComprasService.salvar(novoCarrinhoCompras));
    }

    @Test
    public void retornaCarrinhoComprasAlterado_AoAlterarCarrinhoCompras(){
        CarrinhoCompras carrinhoComprasAAlterar = CarrinhoCompras.builder().id(1L).valorTotal(new BigDecimal("120.0")).totalItens(3).cliente(cliente).build();

        when(carrinhoComprasRepository.findById(any(Long.class))).thenReturn(Optional.of(carrinhoCompras));
        when(carrinhoComprasRepository.save(any(CarrinhoCompras.class))).thenReturn(carrinhoCompras);

        carrinhoComprasService.alterar(carrinhoComprasAAlterar);

        assertEquals(carrinhoCompras.getId(), carrinhoComprasAAlterar.getId());

    }

    @Test
    public void lancaExcecao_AoAlterarCarrinhoComprasSemId(){
        CarrinhoCompras carrinhoComprasAAlterar = CarrinhoCompras.builder().valorTotal(new BigDecimal("150.0")).totalItens(3).cliente(cliente).build();

        when(carrinhoComprasRepository.findById(any(Long.class))).thenReturn(Optional.of(carrinhoCompras));
        when(carrinhoComprasRepository.save(any(CarrinhoCompras.class))).thenReturn(carrinhoCompras);

        assertThrows(CheckoutCustomException.class, () -> carrinhoComprasService.alterar(carrinhoComprasAAlterar));
    }

    @Test
    public void lancaExcecao_AoAlterarCarrinhoComprasInexistenteNoBanco(){

        CarrinhoCompras carrinhoComprasAAlterar = CarrinhoCompras.builder().id(1L).valorTotal(new BigDecimal("150.0")).totalItens(3).cliente(cliente).build();

        when(carrinhoComprasRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        when(carrinhoComprasRepository.save(any(CarrinhoCompras.class))).thenReturn(carrinhoCompras);

        assertThrows(CheckoutCustomException.class, () -> carrinhoComprasService.alterar(carrinhoComprasAAlterar));
    }

    @Test
    public void retornaCarrinhoCompras_AoBuscarCarrinhoComprasPorId(){

        CarrinhoCompras carrinhoComprasABuscar = CarrinhoCompras.builder().id(1L).valorTotal(new BigDecimal("150.0")).totalItens(3).cliente(cliente).build();

        when(carrinhoComprasRepository.findById(any(Long.class))).thenReturn(Optional.of(carrinhoCompras));

        assertEquals(carrinhoCompras.getId(), carrinhoComprasService.buscaPorId(carrinhoComprasABuscar.getId()).getId());

    }

    @Test
    public void lancaExcecao_AoBuscarCarrinhoComprasPorIdInexistenteNoBanco(){

        CarrinhoCompras carrinhoComprasABuscar = CarrinhoCompras.builder().id(1L).valorTotal(new BigDecimal("150.0")).totalItens(3).cliente(cliente).build();

        when(carrinhoComprasRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(CheckoutCustomException.class, () ->carrinhoComprasService.buscaPorId(carrinhoComprasABuscar.getId()));

    }

    @Test
    public void retornaCarrinhoCompras_AoAdicionarProdutos(){

        when(carrinhoComprasRepository.save(any(CarrinhoCompras.class))).thenReturn(carrinhoCompras);
        when(produtoPedidoService.salvar(any(ProdutoPedido.class))).thenReturn(produtoPedido);
        when(produtoPedidoService.listarProdutosPedidosDeCarrinho(any(CarrinhoCompras.class))).thenReturn(Arrays.asList(produtoPedido, produtoPedido, produtoPedido));
        when(produtoPedido.getQtdItens()).thenReturn(3);
        when(produtoPedido.getValorTotal()).thenReturn(new BigDecimal("150.0"));

        CarrinhoCompras carrinhoComprasAAdicionar =  carrinhoComprasService.adicionaProdutosCarrinho(produto,1, carrinhoCompras);

        assertEquals(carrinhoCompras.getId(), carrinhoComprasAAdicionar.getId());
        assertEquals(9, carrinhoComprasAAdicionar.getTotalItens());
        assertEquals(new BigDecimal("450"), carrinhoComprasAAdicionar.getValorTotal());

    }

    @Test void lancaExcecao_AoAdicionarProdutosACarrinhoInexistente(){
        CarrinhoCompras carrinhoComprasAAdicionar = CarrinhoCompras.builder().valorTotal(new BigDecimal("150.0")).totalItens(3).cliente(cliente).build();

        when(carrinhoComprasRepository.findById(any(Long.class))).thenReturn(Optional.of(carrinhoCompras));
        when(carrinhoComprasRepository.save(any(CarrinhoCompras.class))).thenReturn(carrinhoCompras);

        assertThrows(CheckoutCustomException.class, () -> carrinhoComprasService.adicionaProdutosCarrinho(produto, 1, carrinhoComprasAAdicionar));

    }

    @Test
    public void retornaCarrinhoCompras_AoRemoverProdutos(){

        when(carrinhoComprasRepository.save(any(CarrinhoCompras.class))).thenReturn(carrinhoCompras);
        when(produtoPedidoService.removeProdutos(any(ProdutoPedido.class), any(Integer.class))).thenReturn(produtoPedido);
        when(produtoPedidoService.listarProdutosPedidosDeCarrinho(any(CarrinhoCompras.class))).thenReturn(Arrays.asList(produtoPedido, produtoPedido, produtoPedido));
        when(produtoPedido.getQtdItens()).thenReturn(3);
        when(produtoPedido.getValorTotal()).thenReturn(new BigDecimal("150.0"));

        CarrinhoCompras carrinhoComprasAAdicionar =  carrinhoComprasService.removeProdutosCarrinho(produtoPedido, 1, carrinhoCompras);

        assertEquals(carrinhoCompras.getId(), carrinhoComprasAAdicionar.getId());
        assertEquals(9, carrinhoComprasAAdicionar.getTotalItens());
        assertEquals(new BigDecimal("450"), carrinhoComprasAAdicionar.getValorTotal());

    }

    @Test void lancaExcecao_AoRemoverProdutosACarrinhoInexistente(){
        CarrinhoCompras carrinhoComprasAAdicionar = CarrinhoCompras.builder().valorTotal(new BigDecimal("150.0")).totalItens(3).cliente(cliente).build();

        when(carrinhoComprasRepository.findById(any(Long.class))).thenReturn(Optional.of(carrinhoCompras));
        when(carrinhoComprasRepository.save(any(CarrinhoCompras.class))).thenReturn(carrinhoCompras);

        assertThrows(CheckoutCustomException.class, () -> carrinhoComprasService.removeProdutosCarrinho(produtoPedido, 1, carrinhoComprasAAdicionar));

    }
}
