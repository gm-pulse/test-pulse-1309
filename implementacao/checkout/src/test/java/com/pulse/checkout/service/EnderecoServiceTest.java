package com.pulse.checkout.service;

import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.Endereco;
import com.pulse.checkout.repository.EnderecoRepository;
import com.pulse.checkout.services.EnderecoService;
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
public class EnderecoServiceTest {

    @Mock
    EnderecoRepository enderecoRepository;

    @InjectMocks
    EnderecoService enderecoService;

    private Endereco endereco;

    @BeforeEach
    public void criaEndereco(){
        endereco = Endereco.builder().id(1L).bairro("Cohatrac VI").complemento("Quadra 1").cep("65050-000").numero(12).rua("Rua São João").build();
    }

    @Test
    public void retornaEnderecoSalvo_AoSalvarEndereco(){
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);

        Endereco novoEndereco = enderecoService.salvar(Endereco.builder().id(1L).bairro("Cohatrac VI").complemento("Quadra 1").cep("65050-000").numero(12).rua("Rua São João").build());

        assertEquals(endereco.getId(), novoEndereco.getId());

    }

    @Test
    public void retornaEnderecoAlterado_AoAlterarEndereco(){
        Endereco enderecoAAlterar = Endereco.builder().id(1L).bairro("Cohatrac VI").complemento("Quadra 1").cep("65050-000").numero(12).rua("Rua São João").build();

        when(enderecoRepository.findById(any(Long.class))).thenReturn(Optional.of(endereco));
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);

        enderecoService.alterar(enderecoAAlterar);

        assertEquals(endereco.getId(), enderecoAAlterar.getId());

    }

    @Test
    public void lancaExcecao_AoAlterarEnderecoSemId(){
        Endereco enderecoAAlterar = Endereco.builder().bairro("Cohatrac VI").complemento("Quadra 1").cep("65050-000").numero(12).rua("Rua São João").build();

        when(enderecoRepository.findById(any(Long.class))).thenReturn(Optional.of(endereco));
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);

        assertThrows(CheckoutCustomException.class, () -> enderecoService.alterar(enderecoAAlterar));
    }

    @Test
    public void lancaExcecao_AoAlterarEnderecoInexistenteNoBanco(){

        Endereco enderecoAAlterar = Endereco.builder().id(1L).bairro("Cohatrac VI").complemento("Quadra 1").cep("65050-000").numero(12).rua("Rua São João").build();

        when(enderecoRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);

        assertThrows(CheckoutCustomException.class, () -> enderecoService.alterar(enderecoAAlterar));
    }

    @Test
    public void retornaEndereco_AoBuscarEnderecoPorId(){

        Endereco enderecoABuscar = Endereco.builder().id(1L).bairro("Cohatrac VI").complemento("Quadra 1").cep("65050-000").numero(12).rua("Rua São João").build();

        when(enderecoRepository.findById(any(Long.class))).thenReturn(Optional.of(endereco));

        assertEquals(endereco.getId(), enderecoService.buscaPorId(enderecoABuscar.getId()).getId());

    }

    @Test
    public void lancaExcecao_AoBuscarEnderecoPorIdInexistenteNoBanco(){

        Endereco enderecoABuscar = Endereco.builder().id(1L).bairro("Cohatrac VI").complemento("Quadra 1").cep("65050-000").numero(12).rua("Rua São João").build();

        when(enderecoRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(CheckoutCustomException.class, () ->enderecoService.buscaPorId(enderecoABuscar.getId()));

    }
}
