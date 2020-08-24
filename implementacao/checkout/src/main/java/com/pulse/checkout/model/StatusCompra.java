package com.pulse.checkout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CK10_STATUS_COMPRA")
@SequenceGenerator(name = "CK10_STATUS_COMPRA_CK10_COD_STATUS_COMPRA_SEQ", sequenceName = "CK10_STATUS_COMPRA_CK10_COD_STATUS_COMPRA_SEQ", allocationSize = 1)
public class StatusCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CK10_STATUS_COMPRA_CK10_COD_STATUS_COMPRA_SEQ")
    @Column(name = "CK10_COD_STATUS_COMPRA")
    private Long id;

    @NotBlank(message = "A descrição da compra não pode ser nula")
    @Column(name = "CK10_DESCRICAO")
    private String descricao;
}
