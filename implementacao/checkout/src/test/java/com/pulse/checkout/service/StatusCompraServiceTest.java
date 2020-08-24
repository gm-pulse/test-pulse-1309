package com.pulse.checkout.service;

import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.StatusCompra;
import com.pulse.checkout.repository.StatusCompraRepository;
import com.pulse.checkout.services.StatusCompraService;
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
public class StatusCompraServiceTest {

    @Mock
    StatusCompraRepository statusCompraRepository;

    @InjectMocks
    StatusCompraService statusCompraService;

    private StatusCompra statusCompra;

    @BeforeEach
    public void criaStatusCompra(){
        statusCompra = StatusCompra.builder().id(1L).descricao("Em Andamento").build();
    }

    @Test
    public void retornaStatusCompraSalvo_AoSalvarStatusCompra(){
        when(statusCompraRepository.save(any(StatusCompra.class))).thenReturn(statusCompra);

        StatusCompra novoStatusCompra =  StatusCompra.builder().id(1L).descricao("Em Andamento").build();

        assertEquals(statusCompra.getId(), novoStatusCompra.getId());

    }

    @Test
    public void lancaExcecao_AoSalvarStatusCompraJaCadastradoNaBase(){
        when(statusCompraRepository.findByDescricao(any(String.class))).thenReturn(Optional.of(statusCompra));
        when(statusCompraRepository.save(any(StatusCompra.class))).thenReturn(statusCompra);

        StatusCompra novoStatusCompra = StatusCompra.builder().id(1L).descricao("Em Andamento").build();

        assertThrows(CheckoutCustomException.class, () -> statusCompraService.salvar(novoStatusCompra));

    }

    @Test
    public void retornaStatusCompraAlterado_AoAlterarTipoPagemnto(){
        StatusCompra statusCompraAAlterar = StatusCompra.builder().id(1L).descricao("Concluido").build();


        when(statusCompraRepository.findById(any(Long.class))).thenReturn(Optional.of(statusCompra));
        when(statusCompraRepository.save(any(StatusCompra.class))).thenReturn(statusCompra);

        statusCompraService.alterar(statusCompraAAlterar);

        assertEquals(statusCompra.getId(), statusCompraAAlterar.getId());

    }

    @Test
    public void lancaExcecao_AoAlterarStatusCompraSemId(){
        StatusCompra statusCompraAAlterar = StatusCompra.builder().descricao("Concluido").build();

        when(statusCompraRepository.findById(any(Long.class))).thenReturn(Optional.of(statusCompra));
        when(statusCompraRepository.save(any(StatusCompra.class))).thenReturn(statusCompra);

        assertThrows(CheckoutCustomException.class, () -> statusCompraService.alterar(statusCompraAAlterar));
    }

    @Test
    public void lancaExcecao_AoAlterarStatusCompraInexistenteNoBanco(){

        StatusCompra statusCompraAAlterar = StatusCompra.builder().id(1L).descricao("Concluido").build();

        when(statusCompraRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        when(statusCompraRepository.save(any(StatusCompra.class))).thenReturn(statusCompra);

        assertThrows(CheckoutCustomException.class, () -> statusCompraService.alterar(statusCompraAAlterar));
    }

    @Test
    public void lancaExcecao_AoAlterarStatusCompraParaOutroJaCadastradoNaBase(){

        StatusCompra statusCompraAAlterar = StatusCompra.builder().id(1L).descricao("Concluido").build();

        when(statusCompraRepository.findById(any(Long.class))).thenReturn(Optional.of(statusCompra));
        when(statusCompraRepository.findByDescricao(any(String.class))).thenReturn(Optional.of(statusCompraAAlterar));
        when(statusCompraRepository.save(any(StatusCompra.class))).thenReturn(statusCompra);

        assertThrows(CheckoutCustomException.class, () -> statusCompraService.alterar(statusCompraAAlterar));
    }

    @Test
    public void retornaStatusCompra_AoBuscarStatusCompraPorId(){

        StatusCompra statusCompraABuscar = StatusCompra.builder().id(1L).descricao("Concluido").build();

        when(statusCompraRepository.findById(any(Long.class))).thenReturn(Optional.of(statusCompra));

        assertEquals(statusCompra.getId(), statusCompraService.buscaPorId(statusCompraABuscar.getId()).getId());

    }

    @Test
    public void lancaExcecao_AoBuscarStatusCompraPorIdInexistenteNoBanco(){

        StatusCompra statusCompraABuscar = StatusCompra.builder().id(1L).descricao("Concluido").build();

        when(statusCompraRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(CheckoutCustomException.class, () ->statusCompraService.buscaPorId(statusCompraABuscar.getId()));

    }
}
