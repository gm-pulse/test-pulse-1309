package com.pulse.checkout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CK01_PRODUTO")
@SequenceGenerator(name = "CK01_PRODUTO_CK01_COD_PRODUTO_SEQ", sequenceName = "CK01_PRODUTO_CK01_COD_PRODUTO_SEQ", allocationSize = 1)
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CK01_PRODUTO_CK01_COD_PRODUTO_SEQ")
    @Column(name = "CK01_COD_PRODUTO")
    private Long id;

    @NotBlank(message = "A descrição do produto não pode ser nula")
    @Column(name = "CK01_DESCRICAO")
    private String descricao;

    @NotBlank(message = "O valor unitário do produto não pode ser nulo")
    @Column(name = "CK01_VALOR_UNITARIO")
    private BigDecimal valorUnitario;

}
