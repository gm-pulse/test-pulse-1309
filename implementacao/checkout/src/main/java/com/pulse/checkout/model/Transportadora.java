package com.pulse.checkout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CK04_TRANSPORTADORA")
@SequenceGenerator(name = "CK04_TRANSPORTADORA_CK04_COD_TRANSPORTADORA_SEQ", sequenceName = "CK04_TRANSPORTADORA_CK04_COD_TRANSPORTADORA_SEQ", allocationSize = 1)
public class Transportadora {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CK04_TRANSPORTADORA_CK04_COD_TRANSPORTADORA_SEQ")
    @Column(name = "CK04_COD_TRANSPORTADORA")
    private Long id;

    @NotBlank(message = "O nome da transportadora não pode ser nulo")
    @Column(name = "CK04_NOME")
    private String nome;

    @NotBlank(message = "O CNPJ da transportadora não pode ser nulo")
    @CNPJ(message = "Informe um CNPJ válido")
    @Column(name = "CK04_CNPJ")
    private String cnpj;

    @NotNull(message = "O valor do frete da transportadora não pode ser nulo")
    @Column(name = "CK04_VALOR_FRETE")
    private BigDecimal valorFrete;

}
