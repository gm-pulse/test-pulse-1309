package com.pulse.checkout.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CK02_CLIENTE")
@SequenceGenerator(name = "CK02_CLIENTE_CK02_COD_CLIENTE_SEQ", sequenceName = "CK02_CLIENTE_CK02_COD_CLIENTE_SEQ", allocationSize = 1)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CK02_CLIENTE_CK02_COD_CLIENTE_SEQ")
    @Column(name = "CK02_COD_CLIENTE")
    private Long id;

    @NotBlank(message = "O nome do cliente não pode ser nulo")
    @Column(name = "CK02_NOME_CLIENTE")
    private String nome;

    @NotBlank(message = "O cpf do cliente não pode ser nulo")
    @CPF(message = "Informe um CPF válido")
    @Column(name = "CK02_CPF_CLIENTE")
    private String cpf;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CK02CK03_COD_ENDERECO")
    private Endereco endereco;


}
