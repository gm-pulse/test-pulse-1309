package com.pulse.checkout.controller;

import com.pulse.checkout.model.Cliente;
import com.pulse.checkout.model.Pagamento;
import com.pulse.checkout.repository.PagamentoRepository;
import com.pulse.checkout.services.PagamentoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<List<Pagamento>> buscarTodos(){
        List<Pagamento> pagamentosCadastrados = pagamentoRepository.findAll();

        if(pagamentosCadastrados.isEmpty()) {
            return noContent().build();
        }else{
            return ok(pagamentosCadastrados);
        }
    }

    @PostMapping("/pagamento/{idTipoPagamento}/{idCarrinho}/{idEnderecoEntrega}/{idTransportadora}")
    @ApiOperation(value = "Cadastra um novo pagamento (realiza Checkout)")
    public ResponseEntity<Pagamento>fazCheckout(@PathVariable Long idTipoPagamento, @PathVariable Long idCarrinho,@PathVariable Long idEnderecoEntrega,@PathVariable Long idTransportadora ){
        Pagamento novoPagamento = pagamentoService.criaPagamento(idTipoPagamento, idCarrinho, idTransportadora, idEnderecoEntrega);
        return new ResponseEntity<>(novoPagamento, HttpStatus.CREATED);
    }



}
