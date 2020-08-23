package com.pulse.checkout.service;

import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.Produto;
import com.pulse.checkout.repository.ProdutoRepository;
import com.pulse.checkout.services.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
public class ProdutoServiceTest {

    @Mock
    ProdutoRepository produtoRepository;

    @InjectMocks
    ProdutoService produtoService;

    private Produto produto;

    @BeforeEach
    public void criaProduto(){
        produto = new Produto().builder().id(1L).descricao("Caderno Tilibra").valorUnitario(new BigDecimal("15.0")).build();
    }

    @Test
    public void retornaProdutoSalvo_AoSalvarProduto(){
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto novoProduto = produtoService.salvar(new Produto().builder().descricao("Caderno Tilibra").valorUnitario(new BigDecimal("15.0")).build());

        assertEquals(produto.getDescricao(), novoProduto.getDescricao());

    }

    @Test
    public void retornaProdutoAlterado_AoAlterarProduto(){
        Produto produtoAAlterar = new Produto().builder().id(1L).descricao("Caderno Tilibras 12 Matérias").valorUnitario(new BigDecimal("15.0")).build();

        when(produtoRepository.findById(any(Long.class))).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        produtoService.alterar(produtoAAlterar);

        assertEquals(produto.getId(), produtoAAlterar.getId());

    }

    @Test
    public void lancaExcecao_AoAlterarProdutoSemId(){
        Produto produtoAAlterar = new Produto().builder().descricao("Caderno Tilibras 12 Matérias").valorUnitario(new BigDecimal("15.0")).build();

        when(produtoRepository.findById(any(Long.class))).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        assertThrows(CheckoutCustomException.class, () -> produtoService.alterar(produtoAAlterar));
    }

    @Test
    public void lancaExcecao_AoAlterarProdutoInexistenteNoBanco(){

        Produto produtoAAlterar = new Produto().builder().id(10L).descricao("Caderno Tilibras 12 Matérias").valorUnitario(new BigDecimal("15.0")).build();

        when(produtoRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        assertThrows(CheckoutCustomException.class, () -> produtoService.alterar(produtoAAlterar));
    }

    @Test
    public void retornaProduto_AoBuscarProdutoPorId(){

        Produto produtoABuscar = new Produto().builder().id(10L).descricao("Caderno Tilibras 12 Matérias").valorUnitario(new BigDecimal("15.0")).build();

        when(produtoRepository.findById(any(Long.class))).thenReturn(Optional.of(produto));

        assertEquals(produto.getId(), produtoService.buscaPorId(produtoABuscar.getId()).getId());

    }

    @Test
    public void lancaExcecao_AoBuscarProdutoPorIdInexistenteNoBanco(){

        Produto produtoABuscar = new Produto().builder().id(10L).descricao("Caderno Tilibras 12 Matérias").valorUnitario(new BigDecimal("15.0")).build();

        when(produtoRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(CheckoutCustomException.class, () ->produtoService.buscaPorId(produtoABuscar.getId()));

    }
}
