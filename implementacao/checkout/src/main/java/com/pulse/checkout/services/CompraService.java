package com.pulse.checkout.services;

import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.Compra;
import com.pulse.checkout.model.Pagamento;
import com.pulse.checkout.repository.CompraRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CompraService {

    private final CompraRepository compraRepository;

    private final StatusCompraService statusCompraService;

    private final PagamentoService pagamentoService;

    public Compra salvar(Compra compra){
        verificaPagamento(compra.getPagamento());
        return criaCompra(compra);

    }

    private void verificaPagamento(Pagamento pagamento){
        if(pagamento!=null){
            if (pagamentoService.buscaPorId(pagamento.getId())==null) {
                throw new CheckoutCustomException("O tipo de pagamento é inexistente");
            }
        }else {
            throw new CheckoutCustomException("O tipo de pagamento não foi informado");

        }
    }

    private Compra criaCompra(Compra compra){
        if (compra.getId() != null) {
            throw new CheckoutCustomException("Carrinho já existe e não pode ser criado");

        }
        compra.setStatusCompra(statusCompraService.buscaPorId(3L));
        compra.setNumPedido(geraNumeroPedido());
        compra.setCodRastreio(geraNumeroRastreio());

        return compraRepository.save(compra);
    }

    private String geraNumeroPedido(){
        Random random = new Random();

        Integer valorAleatorio = geraValorAleatorio(random, 10000, 99999);


        return String.valueOf(valorAleatorio)
                .concat("-")
                .concat(String.valueOf(random.nextInt(9)*10));
    }

    private int geraValorAleatorio(Random random, int min, int max) {
        return random.ints(1, min, max).limit(1).findFirst().getAsInt();
    }

    private String geraNumeroRastreio(){
        Random random = new Random();

        Integer valorAleatorio = geraValorAleatorio(random, 100000000, 999999999);

        return "AA".concat(String.valueOf(valorAleatorio))
                .concat(String.valueOf(random.nextInt(9)*10)).concat("BR");
    }

    public Compra buscaPorId(Long id){
        return compraRepository.findById(id)
                .orElseThrow(() -> new CheckoutCustomException("Compra com " + id + " inexistente no banco"));
    }

    public Compra buscaPorNumeroPedido(String numeroPedido){
        return compraRepository.findByNumPedido(numeroPedido)
                .orElseThrow(() -> new CheckoutCustomException("Compra com " + numeroPedido + " inexistente no banco"));
    }

    public Compra buscaPorCodigoRastreio(String codigoRastreio){
        return compraRepository.findByCodRastreio(codigoRastreio)
                .orElseThrow(() -> new CheckoutCustomException("Compra com " + codigoRastreio + " inexistente no banco"));
    }
    public Compra alterar(Compra compra){
        if(compra.getId() == null){
            throw new CheckoutCustomException("Compra com ID não informado");
        }
        return compraRepository.findById(compra.getId())
                .map(compraAlterada ->{
                    if(!compraAlterada.getPagamento().equals(compra.getPagamento())) {
                        verificaPagamento(compraAlterada.getPagamento());
                    }
                    return compraRepository.save(compra);

                })
                .orElseThrow(
                        () -> new CheckoutCustomException("Compra não existe e não pode ser alterada")
                );
    }


}
