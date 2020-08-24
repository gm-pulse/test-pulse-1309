package com.pulse.checkout.service;

import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.TipoPagamento;
import com.pulse.checkout.repository.TipoPagamentoRepository;
import com.pulse.checkout.services.TipoPagamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TipoPagamentoServiceTest {

    @Mock
    TipoPagamentoRepository tipoPagamentoRepository;

    @InjectMocks
    TipoPagamentoService tipoPagamentoService;

    private  TipoPagamento tipoPagamento;

    @BeforeEach
    public void criarTipoPagamento(){
        tipoPagamento = TipoPagamento.builder().id(1L).descricao("A Vista").build();
    }

    @Test
    public void retornaTipoPagamentoSalvo_AoSalvarTipoPagamento(){
        when(tipoPagamentoRepository.save(any(TipoPagamento.class))).thenReturn(tipoPagamento);

        TipoPagamento novoTipoPagamento =  TipoPagamento.builder().id(1L).descricao("A Vista").build();

        assertEquals(tipoPagamento.getId(), novoTipoPagamento.getId());

    }

    @Test
    public void lancaExcecao_AoSalvarTipoPagamentoJaCadastradoNaBase(){
        when(tipoPagamentoRepository.findByDescricao(any(String.class))).thenReturn(Optional.of(tipoPagamento));
        when(tipoPagamentoRepository.save(any(TipoPagamento.class))).thenReturn(tipoPagamento);

        TipoPagamento novoTipoPagamento = TipoPagamento.builder().id(1L).descricao("A Vista").build();

        assertThrows(CheckoutCustomException.class, () -> tipoPagamentoService.salvar(novoTipoPagamento));

    }

    @Test
    public void retornaTipoPagamentoAlterado_AoAlterarTipoPagemnto(){
        TipoPagamento tipoPagamentoAAlterar = TipoPagamento.builder().id(1L).descricao("Boleto").build();


        when(tipoPagamentoRepository.findById(any(Long.class))).thenReturn(Optional.of(tipoPagamento));
        when(tipoPagamentoRepository.save(any(TipoPagamento.class))).thenReturn(tipoPagamento);

        tipoPagamentoService.alterar(tipoPagamentoAAlterar);

        assertEquals(tipoPagamento.getId(), tipoPagamentoAAlterar.getId());

    }

    @Test
    public void lancaExcecao_AoAlterarTipoPagamentoSemId(){
        TipoPagamento tipoPagamentoAAlterar = TipoPagamento.builder().descricao("Boleto").build();

        when(tipoPagamentoRepository.findById(any(Long.class))).thenReturn(Optional.of(tipoPagamento));
        when(tipoPagamentoRepository.save(any(TipoPagamento.class))).thenReturn(tipoPagamento);

        assertThrows(CheckoutCustomException.class, () -> tipoPagamentoService.alterar(tipoPagamentoAAlterar));
    }

    @Test
    public void lancaExcecao_AoAlterarTipoPagamentoInexistenteNoBanco(){

        TipoPagamento tipoPagamentoAAlterar = TipoPagamento.builder().id(1L).descricao("Boleto").build();

        when(tipoPagamentoRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        when(tipoPagamentoRepository.save(any(TipoPagamento.class))).thenReturn(tipoPagamento);

        assertThrows(CheckoutCustomException.class, () -> tipoPagamentoService.alterar(tipoPagamentoAAlterar));
    }

    @Test
    public void lancaExcecao_AoAlterarTipoPagamentoParaOutroJaCadastradoNaBase(){

        TipoPagamento tipoPagamentoAAlterar = TipoPagamento.builder().id(1L).descricao("Boleto").build();

        when(tipoPagamentoRepository.findById(any(Long.class))).thenReturn(Optional.of(tipoPagamento));
        when(tipoPagamentoRepository.findByDescricao(any(String.class))).thenReturn(Optional.of(tipoPagamentoAAlterar));
        when(tipoPagamentoRepository.save(any(TipoPagamento.class))).thenReturn(tipoPagamento);

        assertThrows(CheckoutCustomException.class, () -> tipoPagamentoService.alterar(tipoPagamentoAAlterar));
    }

    @Test
    public void retornaTipoPagamento_AoBuscarTipoPagamentoPorId(){

        TipoPagamento tipoPagamentoABuscar = TipoPagamento.builder().id(1L).descricao("Boleto").build();

        when(tipoPagamentoRepository.findById(any(Long.class))).thenReturn(Optional.of(tipoPagamento));

        assertEquals(tipoPagamento.getId(), tipoPagamentoService.buscaPorId(tipoPagamentoABuscar.getId()).getId());

    }

    @Test
    public void lancaExcecao_AoBuscarTipoPagamentoPorIdInexistenteNoBanco(){

        TipoPagamento tipoPagamentoABuscar = TipoPagamento.builder().id(1L).descricao("Boleto").build();

        when(tipoPagamentoRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(CheckoutCustomException.class, () ->tipoPagamentoService.buscaPorId(tipoPagamentoABuscar.getId()));

    }
}
