package com.pulse.checkout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CK03_ENDERECO")
@SequenceGenerator(name = "CK03_ENDERECO_CK03_COD_ENDERECO_SEQ", sequenceName = "CK03_ENDERECO_CK03_COD_ENDERECO_SEQ", allocationSize = 1)
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CK03_ENDERECO_CK03_COD_ENDERECO_SEQ")
    @Column(name = "CK03_COD_ENDERECO")
    private Long id;

    @NotBlank(message = "A rua não deve ser nula")
    @Column(name = "CK03_RUA")
    private String rua;

    @NotNull(message = "O numero não deve ser nulo")
    @Column(name = "CK03_NUMERO")
    private Integer numero;

    @NotBlank(message = "O complemento não deve ser nulo")
    @Column(name = "CK03_COMPLEMENTO")
    private String complemento;

    @NotBlank(message = "O bairro não deve ser nulo")
    @Column(name = "CK03_BAIRRO")
    private String bairro;

    @NotBlank(message = "O CEP não deve ser nulo")
    @Column(name = "CK03_CEP")
    private String cep;

}
