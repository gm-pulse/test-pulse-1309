package com.pulse.checkout.controller;

import com.pulse.checkout.model.CarrinhoCompras;
import com.pulse.checkout.model.Pagamento;
import com.pulse.checkout.repository.PagamentoRepository;
import com.pulse.checkout.services.PagamentoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api")
@Api(value = "Api Rest Pagamentos")
@CrossOrigin(origins = "*")
public class PagamentoController {

    private final PagamentoRepository pagamentoRepository;
    private final PagamentoService pagamentoService;

    @GetMapping("/pagamentos")
    @ApiOperation(value = "Retorna os pagamentos cadastrados")
    public ResponseEntity<List<Pagamento>> buscarTodos() {
        List<Pagamento> pagamentosCadastrados = pagamentoRepository.findAll();

        if (pagamentosCadastrados.isEmpty()) {
            return noContent().build();
        } else {
            return ok(pagamentosCadastrados);
        }
    }

    @PostMapping("/pagamento/{idTipoPagamento}/{idCarrinho}/{idEnderecoEntrega}/{idTransportadora}")
    @ApiOperation(value = "4 e 5. Informa endereço, frete(transportadora) e forma pagamento")
    public ResponseEntity<Pagamento> fazCheckout(@PathVariable Long idTipoPagamento, @PathVariable Long idCarrinho, @PathVariable Long idEnderecoEntrega, @PathVariable Long idTransportadora) {
        Pagamento novoPagamento = pagamentoService.fazCheckout(idTipoPagamento, idCarrinho, idTransportadora, idEnderecoEntrega);

        return new ResponseEntity<>(novoPagamento, HttpStatus.CREATED);
    }

    @GetMapping("/pagamentosPorClienteId/{clienteId}")
    @ApiOperation(value = "Retorna os pagamentos realizados pelo cliente")
    public ResponseEntity<List<Pagamento>> buscarPorClienteId(@PathVariable Long clienteId) {
        List<Pagamento> pagamentosPorClienteId = pagamentoService.listaPorClienteId(clienteId);

        return ok(pagamentosPorClienteId);

    }

    @GetMapping("/pagamentoPorId/{id}")
    @ApiOperation(value = "Busca Pagamento pelo id")
    public ResponseEntity<Pagamento> buscaPorId(@PathVariable Long id){
        return ok(pagamentoService.buscaPorId(id));
    }
}
