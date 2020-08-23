package com.pulse.checkout.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CK06_TIPO_PAGAMENTO")
@SequenceGenerator(name = "CK06_TIPO_PAGAMENTO_CK06_COD_TIPO_PAG_SEQ", sequenceName = "CK06_TIPO_PAGAMENTO_CK06_COD_TIPO_PAG_SEQ", allocationSize = 1)
public class TipoPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CK06_TIPO_PAGAMENTO_CK06_COD_TIPO_PAG_SEQ")
    @Column(name = "CK06_COD_TIPO_PAG")
    private Long id;

    @NotBlank(message = "A descrição do tipo de pagamento não pode ser nula")
    @Column(name = "CK06_DESCRICAO")
    private String descricao;
}
