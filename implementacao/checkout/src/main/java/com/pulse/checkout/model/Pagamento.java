package com.pulse.checkout.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CK05_PAGAMENTO")
@SequenceGenerator(name = "CK05_PAGAMENTO_CK05_COD_PAGAMENTO_SEQ", sequenceName = "CK05_PAGAMENTO_CK05_COD_PAGAMENTO_SEQ", allocationSize = 1)
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CK05_PAGAMENTO_CK05_COD_PAGAMENTO_SEQ")
    @Column(name = "CK05_COD_PAGAMENTO")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CK05CK06_COD_TIPO_PAG")
    private TipoPagamento tipoPagamento;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CK05CK07_COD_CARRINHO")
    private CarrinhoCompras carrinhoCompras;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CK05CK04_COD_TRANSPORTADORA")
    private Transportadora transportadora;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CK05CK03_COD_ENDERECO")
    private Endereco enderecoEntrega;

    @NotNull(message = "O valor total do pagamento não pode ser nulo")
    @Column(name = "CK05_VALOR_TOTAL")
    private BigDecimal valorTotal;

    @OneToOne
    @JoinColumn(name="CK05CK09_COD_COMPRA")
    private Compra compra;

}
