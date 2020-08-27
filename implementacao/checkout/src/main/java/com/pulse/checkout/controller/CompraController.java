package com.pulse.checkout.controller;

import com.pulse.checkout.model.CarrinhoCompras;
import com.pulse.checkout.model.Compra;
import com.pulse.checkout.model.Pagamento;
import com.pulse.checkout.repository.CompraRepository;
import com.pulse.checkout.services.CompraService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api")
@Api(value = "Api Rest Compras")
@CrossOrigin(origins = "*")
public class CompraController {

    private final CompraRepository compraRepository;
    private final CompraService compraService;

    @GetMapping("/compras")
    @ApiOperation(value = "Retorna as compras cadastradas")
    public ResponseEntity<List<Compra>> buscarTodos() {
        List<Compra> comprasCadastradas = compraRepository.findAll();

        if (comprasCadastradas.isEmpty()) {
            return noContent().build();
        } else {
            return ok(comprasCadastradas);
        }
    }

    @GetMapping("/comprasId/{id}")
    @ApiOperation(value = "Busca compras pelo id")
    public ResponseEntity<Compra> buscaPorId(@PathVariable Long id) {
        return ok(compraService.buscaPorId(id));
    }

    @GetMapping("/comprasCodigoRastreio/{codigoRastreio}")
    @ApiOperation(value = "Busca compras pelo código de rastreio")
    public ResponseEntity<Compra> buscaPorcodigoRastreio(@PathVariable String codigoRastreio) {
        return ok(compraService.buscaPorCodigoRastreio(codigoRastreio));
    }

    @GetMapping("/comprasNumeroPedido/{numeroPedido}")
    @ApiOperation(value = "Busca compras pelo número do pedido")
    public ResponseEntity<Compra> buscaPornumeroPedido(@PathVariable String numeroPedido) {
        return ok(compraService.buscaPorNumeroPedido(numeroPedido));
    }

    @GetMapping("/comprasIdStatusCompra/{idStatusCompra}")
    @ApiOperation(value = "Busca compras pelo id de status compra")
    public ResponseEntity<List<Compra>> buscaPornumeroPedido(@PathVariable Long idStatusCompra) {
        List<Compra> comprasPorStatusId = compraService.listaComprasPorStatusCompraId(idStatusCompra);

        return ok(comprasPorStatusId);
    }

}
