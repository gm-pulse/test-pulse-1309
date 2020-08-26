package com.pulse.checkout.services;

import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.Cliente;
import com.pulse.checkout.model.Endereco;
import com.pulse.checkout.repository.ClienteRepository;
import com.pulse.checkout.repository.EnderecoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    private final CarrinhoComprasService carrinhoComprasService;

    private final EnderecoRepository enderecoRepository;

    public Cliente salvar(Cliente cliente) {
        verificarEndereco(cliente.getEndereco());
        verificaCpfJaCadastrado(cliente.getCpf());
        return clienteRepository.save(cliente);
    }

    public Cliente alterar(Cliente cliente) {
        if (cliente.getId() == null) {
            throw new CheckoutCustomException("Cliente com ID não informado!");
        }
        return clienteRepository.findById(cliente.getId())
                .map(clienteAlterado -> {
                            if (!cliente.getCpf().equals(clienteAlterado.getCpf()))
                                verificaCpfJaCadastrado(cliente.getCpf());

                            return clienteRepository.save(cliente);
                        }
                )
                .orElseThrow(
                        () -> new CheckoutCustomException("Cliente não se encontra salvo e não pode ser alterado")
                );
    }

    public Cliente buscaPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new CheckoutCustomException("Cliente com ID " + id + " inexistente no banco"));
    }

    public Cliente buscaPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new CheckoutCustomException("Cliente com CPF " + cpf + " inexistente no banco"));
    }

    private void verificaCpfJaCadastrado(String cpf) {
        if (clienteRepository.findByCpf(cpf).isPresent()) {
            throw new CheckoutCustomException("Cliente com o mesmo CPF já se encontra cadastrado");
        }
    }

    public void deletaClientePorId(Long id){
        Cliente cliente = buscaPorId(id);
        verificaPossuiCarrinhodeCompras(id);

        clienteRepository.delete(cliente);
    }

    private void verificaPossuiCarrinhodeCompras(Long id){
        if(!carrinhoComprasService.buscaPorClienteId(id).isEmpty()){
            throw new CheckoutCustomException("Cliente possui carrinho de compras e não pode ser excluído");
        }
    }
    private void verificarEndereco(Endereco endereco){
        if(endereco.getId()==null){
            throw new CheckoutCustomException("Endereço não informado!");
        }
        enderecoRepository.findById(endereco.getId()).orElseThrow(() ->new CheckoutCustomException("Endereco informado não está cadastrado no banco"));
    }

}
