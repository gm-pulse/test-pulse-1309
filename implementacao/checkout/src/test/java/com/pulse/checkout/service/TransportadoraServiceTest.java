package com.pulse.checkout.service;

import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.Transportadora;
import com.pulse.checkout.model.Endereco;
import com.pulse.checkout.model.Transportadora;
import com.pulse.checkout.repository.TransportadoraRepository;
import com.pulse.checkout.services.TransportadoraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransportadoraServiceTest {

    @Mock
    TransportadoraRepository transportadoraRepository;

    @InjectMocks
    TransportadoraService transportadoraService;

    private Transportadora transportadora;

    @BeforeEach
    public void criaTransportadora(){
        transportadora = Transportadora.builder().id(1L).nome("Tex").cnpj("51212568000107").valorFrete(new BigDecimal("15.8")).build();
    }

    @Test
    public void retornaTransportadoraSalva_AoSalvarTransportadora(){
        when(transportadoraRepository.save(any(Transportadora.class))).thenReturn(transportadora);

        Transportadora novoTransportadora = transportadoraService.salvar(new Transportadora());

        assertEquals(transportadora.getId(), novoTransportadora.getId());

    }

    @Test
    public void lancaExcecao_AoSalvarTransportadoraComCnpjJaCadastradoNaBase(){
        when(transportadoraRepository.findByCnpj(any(String.class))).thenReturn(Optional.of(transportadora));
        when(transportadoraRepository.save(any(Transportadora.class))).thenReturn(transportadora);

        Transportadora novoTransportadora = Transportadora.builder().id(1L).nome("Tex").cnpj("51212568000107").valorFrete(new BigDecimal("15.8")).build();

        assertThrows(CheckoutCustomException.class, () -> transportadoraService.salvar(novoTransportadora));
    }

    @Test
    public void retornaTransportadoraAlterada_AoAlterarTransportadora(){
        Transportadora transportadoraAAlterar = Transportadora.builder().id(1L).nome("Tex").cnpj("51212568000107").valorFrete(new BigDecimal("15.8")).build();

        when(transportadoraRepository.findById(any(Long.class))).thenReturn(Optional.of(transportadora));
        when(transportadoraRepository.save(any(Transportadora.class))).thenReturn(transportadora);

        transportadoraService.alterar(transportadoraAAlterar);

        assertEquals(transportadora.getId(), transportadoraAAlterar.getId());

    }

    @Test
    public void lancaExcecao_AoAlterarTransportadoraSemId() {
        Transportadora transportadoraAAlterar = Transportadora.builder().nome("Tex").cnpj("51212568000107").valorFrete(new BigDecimal("15.8")).build();

        when(transportadoraRepository.findById(any(Long.class))).thenReturn(Optional.of(transportadora));
        when(transportadoraRepository.save(any(Transportadora.class))).thenReturn(transportadora);

        assertThrows(CheckoutCustomException.class, () -> transportadoraService.alterar(transportadoraAAlterar));
    }

    @Test
    public void lancaExcecao_AoAlterarTransportadoraInexistenteNoBanco() {
        Transportadora transportadoraAAlterar = Transportadora.builder().id(1L).nome("Tex").cnpj("51212568000107").valorFrete(new BigDecimal("15.8")).build();

        when(transportadoraRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        when(transportadoraRepository.save(any(Transportadora.class))).thenReturn(transportadora);

        assertThrows(CheckoutCustomException.class, () -> transportadoraService.alterar(transportadoraAAlterar));
    }

    @Test
    public void lancaExcecao_AoAlterarTransportadoraComCnpjJaCadastradoNaBase(){
        Transportadora transportadoraAAlterar = Transportadora.builder().id(1L).nome("Tex").cnpj("72126990000110").valorFrete(new BigDecimal("15.8")).build();

        when(transportadoraRepository.findById(any(Long.class))).thenReturn(Optional.of(transportadora));
        when(transportadoraRepository.findByCnpj(any(String.class))).thenReturn(Optional.of(transportadoraAAlterar));
        when(transportadoraRepository.save(any(Transportadora.class))).thenReturn(transportadora);

        assertThrows(CheckoutCustomException.class, () -> transportadoraService.alterar(transportadoraAAlterar));
    }

    @Test
    public void retornaTransportadora_AoBuscarPorId(){
        Transportadora transportadoraABuscar = Transportadora.builder().id(1L).nome("Tex").cnpj("51212568000107").valorFrete(new BigDecimal("15.8")).build();

        when(transportadoraRepository.findById(any(Long.class))).thenReturn(Optional.of(transportadora));

        assertEquals(transportadora.getId(), transportadoraService.buscaPorId(transportadoraABuscar.getId()).getId());
    }

    @Test
    public void lancaExcecao_AoBuscarPorIdInexistenteNoBanco(){
        Transportadora transportadoraABuscar = Transportadora.builder().id(1L).nome("Tex").cnpj("51212568000107").valorFrete(new BigDecimal("15.8")).build();

        when(transportadoraRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(CheckoutCustomException.class, () -> transportadoraService.buscaPorId(transportadoraABuscar.getId()));

    }
    @Test
    public void retornaTransportadora_AoBuscarPorCnpj(){
        Transportadora transportadoraABuscar = Transportadora.builder().id(1L).nome("Tex").cnpj("51212568000107").valorFrete(new BigDecimal("15.8")).build();

        when(transportadoraRepository.findByCnpj(any(String.class))).thenReturn(Optional.of(transportadora));

        assertEquals(transportadora.getId(), transportadoraService.buscaPorCnpj(transportadoraABuscar.getCnpj()).getId());
    }

    @Test
    public void lancaExcecao_AoBuscarPorCnpjInexistenteNoBanco(){
        Transportadora transportadoraABuscar = Transportadora.builder().id(1L).nome("Tex").cnpj("51212568000107").valorFrete(new BigDecimal("15.8")).build();

        when(transportadoraRepository.findByCnpj(any(String.class))).thenReturn(Optional.empty());

        assertThrows(CheckoutCustomException.class, () -> transportadoraService.buscaPorCnpj(transportadoraABuscar.getCnpj()));

    }



}
