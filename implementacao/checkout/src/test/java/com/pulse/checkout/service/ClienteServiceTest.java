package com.pulse.checkout.service;

import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.Cliente;
import com.pulse.checkout.model.Endereco;
import com.pulse.checkout.repository.ClienteRepository;
import com.pulse.checkout.services.ClienteService;
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
public class ClienteServiceTest {

    @Mock
    ClienteRepository clienteRepository;

    @InjectMocks
    ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    public void criaCliente(){
        cliente = Cliente.builder().id(1L).nome("Milena Vitória").cpf("58123893060").endereco(new Endereco()).build();
    }

    @Test
    public void retornaClienteSalvo_AoSalvarCliente(){
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente novoCliente = clienteService.salvar(new Cliente());

        assertEquals(cliente.getId(), novoCliente.getId());

    }

    @Test
    public void lancaExcecao_AoSalvarClienteComCpfJaCadastradoNaBase(){
        when(clienteRepository.findByCpf(any(String.class))).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente novoCliente = Cliente.builder().nome("Milena Vitória").cpf("58123893060").endereco(new Endereco()).build();

        assertThrows(CheckoutCustomException.class, () -> clienteService.salvar(novoCliente));
    }

    @Test
    public void retornaClienteAlterado_AoAlterarCliente(){
        Cliente clienteAAlterar = Cliente.builder().id(1L).nome("Milena Vitória Borges").cpf("58123893060").endereco(new Endereco()).build();

        when(clienteRepository.findById(any(Long.class))).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        clienteService.alterar(clienteAAlterar);

        assertEquals(cliente.getId(), clienteAAlterar.getId());

    }

    @Test
    public void lancaExcecao_AoAlterarClienteSemId() {
        Cliente clienteAAlterar = Cliente.builder().nome("Milena Vitória Borges").cpf("58123893060").endereco(new Endereco()).build();

        when(clienteRepository.findById(any(Long.class))).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        assertThrows(CheckoutCustomException.class, () -> clienteService.alterar(clienteAAlterar));
    }

    @Test
    public void lancaExcecao_AoAlterarClienteInexistenteNoBanco() {
        Cliente clienteAAlterar = Cliente.builder().id(1L).nome("Milena Vitória Borges").cpf("58123893060").endereco(new Endereco()).build();

        when(clienteRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        assertThrows(CheckoutCustomException.class, () -> clienteService.alterar(clienteAAlterar));
    }

    @Test
    public void lancaExcecao_AoAlterarClienteComCpfJaCadastradoNaBase(){
        Cliente clienteAAlterar = Cliente.builder().id(1L).nome("Milena Vitória Borges").cpf("61685677045").endereco(new Endereco()).build();

        when(clienteRepository.findById(any(Long.class))).thenReturn(Optional.of(cliente));
        when(clienteRepository.findByCpf(any(String.class))).thenReturn(Optional.of(clienteAAlterar));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        assertThrows(CheckoutCustomException.class, () -> clienteService.alterar(clienteAAlterar));
    }

    @Test
    public void retornaCliente_AoBuscarPorId(){
        Cliente clienteABuscar = Cliente.builder().id(1L).nome("Milena Vitória Borges").cpf("61685677045").endereco(new Endereco()).build();

        when(clienteRepository.findById(any(Long.class))).thenReturn(Optional.of(cliente));

        assertEquals(cliente.getId(), clienteService.buscaPorId(clienteABuscar.getId()).getId());
    }

    @Test
    public void lancaExcecao_AoBuscarPorIdInexistenteNoBanco(){
        Cliente clienteABuscar = Cliente.builder().id(1L).nome("Milena Vitória Borges").cpf("61685677045").endereco(new Endereco()).build();

        when(clienteRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(CheckoutCustomException.class, () -> clienteService.buscaPorId(clienteABuscar.getId()));

    }
    @Test
    public void retornaCliente_AoBuscarPorCpf(){
        Cliente clienteABuscar = Cliente.builder().id(1L).nome("Milena Vitória Borges").cpf("61685677045").endereco(new Endereco()).build();

        when(clienteRepository.findByCpf(any(String.class))).thenReturn(Optional.of(cliente));

        assertEquals(cliente.getId(), clienteService.buscaPorCpf(clienteABuscar.getCpf()).getId());
    }

    @Test
    public void lancaExcecao_AoBuscarPorCpfInexistenteNoBanco(){
        Cliente clienteABuscar = Cliente.builder().id(1L).nome("Milena Vitória Borges").cpf("61685677045").endereco(new Endereco()).build();

        when(clienteRepository.findByCpf(any(String.class))).thenReturn(Optional.empty());

        assertThrows(CheckoutCustomException.class, () -> clienteService.buscaPorCpf(clienteABuscar.getCpf()));

    }

}
