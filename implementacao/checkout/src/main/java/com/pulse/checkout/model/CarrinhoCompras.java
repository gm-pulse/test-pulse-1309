package com.pulse.checkout.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CK07_CARRINHO_COMPRAS")
@SequenceGenerator(name = "CK07_CARRINHO_COMPRAS_CK07_COD_CARRINHO_COMPRAS_SEQ", sequenceName = "CK07_CARRINHO_COMPRAS_CK07_COD_CARRINHO_COMPRAS_SEQ", allocationSize = 1)
public class CarrinhoCompras {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CK07_CARRINHO_COMPRAS_CK07_COD_CARRINHO_COMPRAS_SEQ")
    @Column(name = "CK07_COD_CARRINHO_COMPRAS")
    private Long id;

    @Min(value = 0, message = "O carrinho de compras deve ter no minímo 0 itens")
    @Column(name = "CK07_TOTAL_ITENS")
    private Integer totalItens;

    @NotNull(message = "O valor total não pode ser nulo")
    @Column(name = "CK07_VALOR_TOTAL")
    private BigDecimal valorTotal;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CK07CK02_COD_CLIENTE")
    private Cliente cliente;
}
