## Documentação da sua Solução

Foram desenvolvidas várias rotas para demonstração.

No entanto, para seguir os passos conforme solicitado, seguem as seguintes:

1. O cliente cria um carrinho de compras ;
    
        em carrinho-compras-controller /api/carrinhoCompras/{idCliente}
       
2. O cliente coloca produtos nesse carrinho de compras;

   *Adiciona produtos:*
   
        em carrinho-compras-controller /api/carrinhoCompras/adicionarItens/{idProduto}/{qtdProdutos}/{idCarrinho}/
      
   *Remove produtos:*
   
        em carrinho-compras-controller /api/carrinhoCompras/removerItens/{idProduto}/{idCarrinho}/{qtdProdutos}
       
3. O cliente decide fazer o checkout do carrinho de compras;
4. O cliente seleciona o endereço de entrega e o frete (Obs: considerar frete fixo por cada
transportadora);
5. O cliente seleciona a forma de pagamento e confirma;

   *Todos os itens acima são executados pela rota:*
   
        em pagamento-controller /api/pagamento/{idTipoPagamento}/{idCarrinho}/{idEnderecoEntrega}/{idTransportadora}

6. Caso a venda seja concluída retornar mensagem de sucesso com o número do pedido e
código de rastreio.
   
   *Ao executar a rota anterior, é gerada uma Compra, possuindo número pedido e código de rastreio como atributos, que será retornada associada a Pagamento*
   

## Classes criadas

As seguintes classes foram criadas para operar o sistema:

- Produto;
- Cliente;
- Endereco;
- Transportadora;
- Pagamento;
- TipoPagamento;
- CarrinhoCompras;
- ProdutoPedido;
- Compra;
- StatusCompra.
