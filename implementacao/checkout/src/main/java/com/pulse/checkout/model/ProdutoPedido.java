package com.pulse.checkout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CK08_PRODUTO_PEDIDO")
@SequenceGenerator(name = "CK08_PRODUTO_PEDIDO_CK08_COD_PRODUTO_PEDIDO_SEQ", sequenceName = "CK08_PRODUTO_PEDIDO_CK08_COD_PRODUTO_PEDIDO_SEQ", allocationSize = 1)
public class ProdutoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CK08_PRODUTO_PEDIDO_CK08_COD_PRODUTO_PEDIDO_SEQ")
    @Column(name = "CK08_COD_PRODUTO_PEDIDO")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CK08CK01_COD_PRODUTO")
    Produto produto;

    @Min(value = 1, message = "O pedido de produto deve ter no minímo 1 item")
    @Column(name = "CK08_QTD_ITENS")
    private Integer qtdItens;

    @NotNull(message = "O valor total do pedido não pode ser nulo")
    @Column(name = "CK08_VALOR_TOTAL")
    private BigDecimal valorTotal;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CK08CK07_COD_CARRINHO_COMPRAS")
    private CarrinhoCompras carrinhoCompras;

}
