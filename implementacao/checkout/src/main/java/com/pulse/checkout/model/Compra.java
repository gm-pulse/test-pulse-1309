package com.pulse.checkout.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CK09_COMPRA")
@SequenceGenerator(name = "CK09_COMPRA_CK09_COD_COMPRA_SEQ", sequenceName = "CK09_COMPRA_CK09_COD_COMPRA_SEQ", allocationSize = 1)
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CK09_COMPRA_CK09_COD_COMPRA_SEQ")
    @Column(name = "CK09_COD_COMPRA")
    private Long id;

    @NotBlank(message = "O código de rastreio da compra não pode ser nulo")
    @Column(name = "CK09_COD_RASTREIO")
    private String codRastreio;

    @NotBlank(message = "O número do pedido da compra não pode ser nulo")
    @Column(name = "CK09_NUMERO_PEDIDO")
    private String numPedido;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CK09CK10_COD_STATUS")
    private StatusCompra statusCompra;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CK09CK05_COD_PAGAMENTO")
    private Pagamento pagamento;
}
