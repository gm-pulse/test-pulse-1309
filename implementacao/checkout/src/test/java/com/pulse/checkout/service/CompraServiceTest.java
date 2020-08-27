package com.pulse.checkout.service;


import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.Compra;
import com.pulse.checkout.model.Pagamento;
import com.pulse.checkout.model.StatusCompra;
import com.pulse.checkout.repository.CompraRepository;
import com.pulse.checkout.services.CompraService;
import com.pulse.checkout.services.PagamentoService;
import com.pulse.checkout.services.StatusCompraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
public class CompraServiceTest {

    @Mock
    CompraRepository compraRepository;

    @InjectMocks
    CompraService compraService;

    private Compra compra;

    @Mock
    Pagamento pagamento;

    @Mock
    PagamentoService pagamentoService;

    @Mock
    StatusCompraService statusCompraService;

    @Mock
    StatusCompra statusCompra;

    @BeforeEach
    public void criaCompra(){
        compra = Compra.builder().id(1L).statusCompra(statusCompra).numPedido("11111-21").codRastreio("AA123456789BR").build();
    }

    @Test
    public void retornaCompra_AoSalvarCompra(){
        when(compraRepository.save(any(Compra.class))).thenReturn(compra);
        when(pagamentoService.buscaPorId(any(Long.class))).thenReturn(pagamento);
        when(statusCompraService.buscaPorId(any(Long.class))).thenReturn(statusCompra);

        Compra novaCompra = compraService.salvar(Compra.builder().build());

        assertEquals(compra.getId(), novaCompra.getId());
    }

    @Test
    public void retornaCompraAlterada_AoAlterarCompra(){
        Compra compraAAlterar =Compra.builder().id(1L).statusCompra(statusCompra).numPedido("11111-21").codRastreio("AA123456789BR").build();

        when(compraRepository.findById(any(Long.class))).thenReturn(Optional.of(compra));
        when(compraRepository.save(any(Compra.class))).thenReturn(compra);

        compraService.alterar(compraAAlterar);

        assertEquals(compra.getId(), compraAAlterar.getId());

    }

    @Test
    public void lancaExcecao_AoAlterarCompraSemId(){
        Compra compraAAlterar = Compra.builder().statusCompra(statusCompra).numPedido("11111-21").codRastreio("AA123456789BR").build();

        when(compraRepository.findById(any(Long.class))).thenReturn(Optional.of(compra));
        when(compraRepository.save(any(Compra.class))).thenReturn(compra);

        assertThrows(CheckoutCustomException.class, () -> compraService.alterar(compraAAlterar));
    }

    @Test
    public void lancaExcecao_AoAlterarCompraInexistenteNoBanco(){

        Compra compraAAlterar = Compra.builder().id(1L).statusCompra(statusCompra).numPedido("11111-21").codRastreio("AA123456789BR").build();

        when(compraRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        when(compraRepository.save(any(Compra.class))).thenReturn(compra);

        assertThrows(CheckoutCustomException.class, () -> compraService.alterar(compraAAlterar));
    }

    @Test
    public void retornaCompra_AoBuscarCompraPorId(){

        Compra compraABuscar = Compra.builder().id(1L).statusCompra(statusCompra).numPedido("11111-21").codRastreio("AA123456789BR").build();

        when(compraRepository.findById(any(Long.class))).thenReturn(Optional.of(compra));
        when(pagamentoService.buscaPorId(any(Long.class))).thenReturn(pagamento);

        assertEquals(compra.getId(), compraService.buscaPorId(compraABuscar.getId()).getId());

    }

    @Test
    public void lancaExcecao_AoBuscarCompraPorIdInexistenteNoBanco(){

        Compra compraABuscar = Compra.builder().id(1L).statusCompra(statusCompra).numPedido("11111-21").codRastreio("AA123456789BR").build();

        when(compraRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(CheckoutCustomException.class, () ->compraService.buscaPorId(compraABuscar.getId()));

    }
    @Test
    public void retornaCompra_AoBuscarCompraPorNumeroPedido(){

        Compra compraABuscar = Compra.builder().id(1L).statusCompra(statusCompra).numPedido("11111-21").codRastreio("AA123456789BR").build();

        when(compraRepository.findByNumPedido(any(String.class))).thenReturn(Optional.of(compra));
        when(pagamentoService.buscaPorId(any(Long.class))).thenReturn(pagamento);

        assertEquals(compra.getId(), compraService.buscaPorNumeroPedido(compraABuscar.getNumPedido()).getId());

    }

    @Test
    public void lancaExcecao_AoBuscarCompraPorNumeroPedidoInexistenteNoBanco(){

        Compra compraABuscar = Compra.builder().id(1L).statusCompra(statusCompra).numPedido("11111-21").codRastreio("AA123456789BR").build();

        when(compraRepository.findByNumPedido(any(String.class))).thenReturn(Optional.empty());

        assertThrows(CheckoutCustomException.class, () ->compraService.buscaPorNumeroPedido(compraABuscar.getNumPedido()));

    }
    @Test
    public void retornaCompra_AoBuscarCompraPorCodigoRastreio(){

        Compra compraABuscar = Compra.builder().id(1L).statusCompra(statusCompra).numPedido("11111-21").codRastreio("AA123456789BR").build();

        when(compraRepository.findByCodRastreio(any(String.class))).thenReturn(Optional.of(compra));
        when(pagamentoService.buscaPorId(any(Long.class))).thenReturn(pagamento);

        assertEquals(compra.getId(), compraService.buscaPorCodigoRastreio(compraABuscar.getNumPedido()).getId());

    }

    @Test
    public void lancaExcecao_AoBuscarCompraPorcodigoRastreioInexistenteNoBanco(){

        Compra compraABuscar = Compra.builder().id(1L).statusCompra(statusCompra).numPedido("11111-21").codRastreio("AA123456789BR").build();

        when(compraRepository.findByCodRastreio(any(String.class))).thenReturn(Optional.empty());

        assertThrows(CheckoutCustomException.class, () ->compraService.buscaPorCodigoRastreio(compraABuscar.getCodRastreio()));

    }

}
