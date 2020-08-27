package com.pulse.checkout.service;

import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.*;
import com.pulse.checkout.repository.CarrinhoComprasRepository;
import com.pulse.checkout.repository.PagamentoRepository;
import com.pulse.checkout.repository.TipoPagamentoRepository;
import com.pulse.checkout.repository.TransportadoraRepository;
import com.pulse.checkout.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
public class PagamentoServiceTest {

    @Mock
    PagamentoRepository pagamentoRepository;

    @Mock
    CarrinhoComprasService carrinhoComprasService;

    @Mock
    TipoPagamentoService tipoPagamentoService;

    @Mock
    TransportadoraService transportadoraService;

    @Mock
    EnderecoService enderecoService;

    @InjectMocks
    PagamentoService pagamentoService;

    private Pagamento pagamento;

    @Mock
    CarrinhoCompras carrinhoCompras;

    @Mock
    Endereco endereco;

    @Mock
    Transportadora transportadora;

    @Mock
    TipoPagamento tipoPagamento;

    @BeforeEach
    public void criaPagamento(){
        pagamento = Pagamento.builder().id(1L).valorTotal(new BigDecimal("150.0")).carrinhoCompras(carrinhoCompras).tipoPagamento(tipoPagamento).transportadora(transportadora).enderecoEntrega(endereco).build();
    }

    @Test
    public void retornaPagamento_AoSalvarPagamento(){
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamento);
        when(carrinhoComprasService.buscaPorId(any(Long.class))).thenReturn(carrinhoCompras);
        when(tipoPagamentoService.buscaPorId(any(Long.class))).thenReturn(tipoPagamento);
        when(transportadoraService.buscaPorId(any(Long.class))).thenReturn(transportadora);
        when(enderecoService.buscaPorId(any(Long.class))).thenReturn(endereco);
        when(carrinhoCompras.getValorTotal()).thenReturn(new BigDecimal("150.0"));
        when(carrinhoCompras.getValorTotal()).thenReturn(new BigDecimal("130.0"));
        when(transportadora.getValorFrete()).thenReturn(new BigDecimal("20.0"));

        Pagamento novoPagamento = pagamentoService.salvar(Pagamento.builder().id(1L).carrinhoCompras(carrinhoCompras).tipoPagamento(tipoPagamento).transportadora(transportadora).enderecoEntrega(endereco).build());

        assertEquals(pagamento.getId(),novoPagamento.getId());
        assertEquals(new BigDecimal("150.0"), pagamento.getValorTotal());
    }

    @Test
    public void lancaExcecao_AoSalvarPagamentoComCarrinhoInexistente(){
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamento);
        when(tipoPagamentoService.buscaPorId(any(Long.class))).thenReturn(tipoPagamento);
        when(transportadoraService.buscaPorId(any(Long.class))).thenReturn(transportadora);
        when(enderecoService.buscaPorId(any(Long.class))).thenReturn(endereco);


        when(carrinhoCompras.getValorTotal()).thenReturn(new BigDecimal("150.0"));

        Pagamento novoPagamento = Pagamento.builder().id(1L).carrinhoCompras(carrinhoCompras).tipoPagamento(tipoPagamento).transportadora(transportadora).enderecoEntrega(endereco).build();

        assertThrows(CheckoutCustomException.class, () ->pagamentoService.salvar(novoPagamento) );
    }

    @Test
    public void lancaExcecao_AoSalvarPagamentoComTipoPagamentoInexistente(){
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamento);
        when(carrinhoComprasService.buscaPorId(any(Long.class))).thenReturn(carrinhoCompras);
        when(transportadoraService.buscaPorId(any(Long.class))).thenReturn(transportadora);
        when(enderecoService.buscaPorId(any(Long.class))).thenReturn(endereco);


        when(carrinhoCompras.getValorTotal()).thenReturn(new BigDecimal("150.0"));

        Pagamento novoPagamento = Pagamento.builder().id(1L).carrinhoCompras(carrinhoCompras).tipoPagamento(tipoPagamento).transportadora(transportadora).enderecoEntrega(endereco).build();

        assertThrows(CheckoutCustomException.class, () ->pagamentoService.salvar(novoPagamento) );
    }

    @Test
    public void lancaExcecao_AoSalvarPagamentoComTransportadoraInexistente(){
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamento);
        when(tipoPagamentoService.buscaPorId(any(Long.class))).thenReturn(tipoPagamento);
        when(carrinhoComprasService.buscaPorId(any(Long.class))).thenReturn(carrinhoCompras);
        when(enderecoService.buscaPorId(any(Long.class))).thenReturn(endereco);


        when(carrinhoCompras.getValorTotal()).thenReturn(new BigDecimal("150.0"));

        Pagamento novoPagamento =Pagamento.builder().id(1L).carrinhoCompras(carrinhoCompras).tipoPagamento(tipoPagamento).transportadora(transportadora).enderecoEntrega(endereco).build();

        assertThrows(CheckoutCustomException.class, () ->pagamentoService.salvar(novoPagamento) );
    }

    @Test
    public void lancaExcecao_AoSalvarPagamentoComEnderecoInexistente(){
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamento);
        when(tipoPagamentoService.buscaPorId(any(Long.class))).thenReturn(tipoPagamento);
        when(carrinhoComprasService.buscaPorId(any(Long.class))).thenReturn(carrinhoCompras);
        when(transportadoraService.buscaPorId(any(Long.class))).thenReturn(transportadora);


        when(carrinhoCompras.getValorTotal()).thenReturn(new BigDecimal("150.0"));

        Pagamento novoPagamento = Pagamento.builder().id(1L).carrinhoCompras(carrinhoCompras).tipoPagamento(tipoPagamento).transportadora(transportadora).enderecoEntrega(endereco).build();

        assertThrows(CheckoutCustomException.class, () ->pagamentoService.salvar(novoPagamento) );
    }

    @Test
    public void retornaPagamentoAlterado_AoAlterarPagamento(){
        Pagamento pagamentoAAlterar =Pagamento.builder().id(1L).carrinhoCompras(carrinhoCompras).valorTotal(new BigDecimal("150.0")).tipoPagamento(tipoPagamento).transportadora(transportadora).enderecoEntrega(endereco).build();

        when(pagamentoRepository.findById(any(Long.class))).thenReturn(Optional.of(pagamento));
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamento);

        pagamentoService.alterar(pagamentoAAlterar);

        assertEquals(pagamento.getId(), pagamentoAAlterar.getId());

    }

    @Test
    public void lancaExcecao_AoAlterarPagamentoSemId(){
        Pagamento pagamentoAAlterar = Pagamento.builder().carrinhoCompras(carrinhoCompras).valorTotal(new BigDecimal("200.0")).tipoPagamento(tipoPagamento).transportadora(transportadora).enderecoEntrega(endereco).build();

        when(pagamentoRepository.findById(any(Long.class))).thenReturn(Optional.of(pagamento));
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamento);
        when(carrinhoCompras.getValorTotal()).thenReturn(new BigDecimal("130.0"));
        when(transportadora.getValorFrete()).thenReturn(new BigDecimal("20.0"));

        assertThrows(CheckoutCustomException.class, () -> pagamentoService.alterar(pagamentoAAlterar));
    }

    @Test
    public void lancaExcecao_AoAlterarPagamentoInexistenteNoBanco(){

        Pagamento pagamentoAAlterar = Pagamento.builder().id(1L).carrinhoCompras(carrinhoCompras).valorTotal(new BigDecimal("200.0")).tipoPagamento(tipoPagamento).transportadora(transportadora).enderecoEntrega(endereco).build();

        when(pagamentoRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamento);

        assertThrows(CheckoutCustomException.class, () -> pagamentoService.alterar(pagamentoAAlterar));
    }

    @Test
    public void retornaPagamento_AoBuscarPagamentoPorId(){

        Pagamento pagamentoABuscar = Pagamento.builder().id(1L).carrinhoCompras(carrinhoCompras).valorTotal(new BigDecimal("200.0")).tipoPagamento(tipoPagamento).transportadora(transportadora).enderecoEntrega(endereco).build();

        when(pagamentoRepository.findById(any(Long.class))).thenReturn(Optional.of(pagamento));

        assertEquals(pagamento.getId(), pagamentoService.buscaPorId(pagamentoABuscar.getId()).getId());

    }

    @Test
    public void lancaExcecao_AoBuscarPagamentoPorIdInexistenteNoBanco(){

        Pagamento pagamentoABuscar = Pagamento.builder().id(1L).carrinhoCompras(carrinhoCompras).valorTotal(new BigDecimal("200.0")).tipoPagamento(tipoPagamento).transportadora(transportadora).enderecoEntrega(endereco).build();

        when(pagamentoRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(CheckoutCustomException.class, () ->pagamentoService.buscaPorId(pagamentoABuscar.getId()));

    }
}
