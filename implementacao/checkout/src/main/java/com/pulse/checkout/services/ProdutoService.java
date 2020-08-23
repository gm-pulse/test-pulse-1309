package com.pulse.checkout.services;


import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.Produto;
import com.pulse.checkout.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto alterar(Produto produto) {
        if (produto.getId() == null) {
            throw new CheckoutCustomException("Produto com ID não informado!");
        }
        return produtoRepository.findById(produto.getId())
                .map(produtoAlterado -> produtoRepository.save(produto)
                )
                .orElseThrow(
                        () -> new CheckoutCustomException("Produto não se encontra salvo e não pode ser alterado")
                );
    }

    public Produto buscaPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new CheckoutCustomException("Produto com " + id + " inexiste no banco"));
    }
}
