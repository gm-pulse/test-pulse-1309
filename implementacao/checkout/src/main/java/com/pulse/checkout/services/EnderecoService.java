package com.pulse.checkout.services;

import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.Endereco;
import com.pulse.checkout.repository.ClienteRepository;
import com.pulse.checkout.repository.EnderecoRepository;
import com.pulse.checkout.repository.PagamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    private final PagamentoRepository pagamentoRepository;

    private final ClienteRepository clienteRepository;

    public Endereco salvar(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    public Endereco alterar(Endereco endereco) {
        if (endereco.getId() == null) {
            throw new CheckoutCustomException("Endereco com ID não informado!");
        }
        return enderecoRepository.findById(endereco.getId())
                .map(enderecoAlterado -> enderecoRepository.save(endereco)
                )
                .orElseThrow(
                        () -> new CheckoutCustomException("Endereco não se encontra salvo e não pode ser alterado")
                );
    }

    public Endereco buscaPorId(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new CheckoutCustomException("Endereco com ID " + id + " inexiste no banco"));
    }

    public void deletaEnderecoPorId(Long id){
        Endereco endereco = buscaPorId(id);
        verificaEnderecoPagamento(endereco);
        verificaEnderecoCliente(endereco);

        enderecoRepository.delete(endereco);
    }

    private void verificaEnderecoPagamento(Endereco endereco){
        if(pagamentoRepository.findAllByEnderecoEntrega(endereco).isPresent()){
            throw new CheckoutCustomException("O endereço está cadastrado como endereço de entrega de determinada compra e não pode ser excluído");
        }
    }

    private void verificaEnderecoCliente(Endereco endereco){
        if(clienteRepository.findAllByEndereco(endereco).isPresent()){
            throw new CheckoutCustomException("O endereço está associado como a um cliente e não pode ser excluído");
        }
    }
}
