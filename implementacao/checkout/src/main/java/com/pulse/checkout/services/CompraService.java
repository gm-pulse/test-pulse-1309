package com.pulse.checkout.services;

import com.pulse.checkout.exception.CheckoutCustomException;
import com.pulse.checkout.model.Compra;
import com.pulse.checkout.model.Pagamento;
import com.pulse.checkout.model.StatusCompra;
import com.pulse.checkout.repository.CompraRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
@AllArgsConstructor
public class CompraService {

    private final CompraRepository compraRepository;

    private final StatusCompraService statusCompraService;


    public Compra salvar(Compra compra){
        return criaCompra(compra);

    }

    private Compra criaCompra(Compra compra){
        if (compra.getId() != null) {
            throw new CheckoutCustomException("Compra já existe e não pode ser criado");

        }
        compra.setStatusCompra(statusCompraService.buscaPorId(3L));
        compra.setNumPedido(geraNumeroPedido());
        compra.setCodRastreio(geraCodigoRastreio());

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

    private String geraCodigoRastreio(){
        Random random = new Random();

        Integer valorAleatorio = geraValorAleatorio(random, 100000000, 999999999);

        return "AA".concat(String.valueOf(valorAleatorio)).concat("BR");
    }

    public Compra buscaPorId(Long id){
        return compraRepository.findById(id)
                .orElseThrow(() -> new CheckoutCustomException("Compra com ID" + id + " inexistente no banco"));
    }

    public Compra buscaPorNumeroPedido(String numeroPedido){
        return compraRepository.findByNumPedido(numeroPedido)
                .orElseThrow(() -> new CheckoutCustomException("Compra com número de pedido " + numeroPedido + " inexistente no banco"));
    }

    public Compra buscaPorCodigoRastreio(String codigoRastreio){
        return compraRepository.findByCodRastreio(codigoRastreio)
                .orElseThrow(() -> new CheckoutCustomException("Compra com código de rastreio" + codigoRastreio + " inexistente no banco"));
    }
    public Compra alterar(Compra compra){
        if(compra.getId() == null){
            throw new CheckoutCustomException("Compra com ID não informado");
        }
        return compraRepository.findById(compra.getId())
                .map(compraAlterada ->compraRepository.save(compra)
                )
                .orElseThrow(
                        () -> new CheckoutCustomException("Compra não existe e não pode ser alterada")
                );
    }

    public List<Compra> listaComprasPorStatusCompraId(Long statusCompraId){
        StatusCompra statusCompra = statusCompraService.buscaPorId(statusCompraId);
        Optional<List<Compra>> compras = compraRepository.findAllByStatusCompra(statusCompra);
        if(!compras.isPresent()){
            throw new CheckoutCustomException("Não existem compras com o status "+ statusCompra.getDescricao());
        }
        return compras.get();
    }

}
