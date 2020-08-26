package com.pulse.checkout.services;


import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.Produto;
import com.pulse.checkout.repository.ProdutoPedidoRepository;
import com.pulse.checkout.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    private final ProdutoPedidoRepository produtoPedidoRepository;

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
                .orElseThrow(() -> new CheckoutCustomException("Produto com ID " + id + " inexiste no banco"));
    }

    public void deletaProdutoPorId(Long id) {
        Produto produto = buscaPorId(id);
        verificaProdutoEmPedido(produto);

        produtoRepository.delete(produto);
    }

    private void verificaProdutoEmPedido(Produto produto){
        if(produtoPedidoRepository.findAllByProduto(produto).isPresent()){
            throw new CheckoutCustomException("O produto está incluso em um pedido e não pode ser excluído");
        }
    }
}
