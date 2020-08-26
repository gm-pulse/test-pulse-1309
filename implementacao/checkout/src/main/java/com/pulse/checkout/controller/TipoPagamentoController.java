package com.pulse.checkout.controller;

import com.pulse.checkout.model.TipoPagamento;
import com.pulse.checkout.model.TipoPagamento;
import com.pulse.checkout.repository.TipoPagamentoRepository;
import com.pulse.checkout.services.TipoPagamentoService;
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
@Api(value = "Api Rest Tipo Pagamento")
@CrossOrigin(origins = "*")
public class TipoPagamentoController {

    private final TipoPagamentoService tipoPagamentoService;
    private final TipoPagamentoRepository tipoPagamentoRepository;

    @GetMapping("/tipospagamentos")
    @ApiOperation(value = "Retorna os tipos de pagamentos cadastrados")
    public ResponseEntity<List<TipoPagamento>> buscarTodos(){
        List<TipoPagamento> tiposPagamentosCadastrados = tipoPagamentoRepository.findAll();

        if(tiposPagamentosCadastrados.isEmpty()) {
            return noContent().build();
        }else{
            return ok(tiposPagamentosCadastrados);
        }
    }

    @GetMapping("/tipoPagamentoId/{id}")
    @ApiOperation(value = "Busca tipoPagamento pelo id")
    public ResponseEntity<TipoPagamento> buscaPorId(@PathVariable Long id){
        return ok(tipoPagamentoService.buscaPorId(id));
    }

    @PostMapping("/tipoPagamento")
    @ApiOperation(value = "Cadastra um novo tipoPagamento")
    public ResponseEntity<TipoPagamento>cadastrar(@Valid @RequestBody TipoPagamento tipoPagamento){
        TipoPagamento novoTipoPagamento = tipoPagamentoService.salvar(tipoPagamento);
        return new ResponseEntity<>(novoTipoPagamento, HttpStatus.CREATED);
    }

    @PutMapping("/tipoPagamentoUpdate/{id}")
    @ApiOperation(value = "Atualiza dados de um tipoPagamento")
    public ResponseEntity<TipoPagamento> atualizar(@Valid @RequestBody TipoPagamento tipoPagamento){
        TipoPagamento tipoPagamentoAtualizado = tipoPagamentoService.alterar(tipoPagamento);
        return new ResponseEntity<>(tipoPagamentoAtualizado, HttpStatus.CREATED);
    }

    @DeleteMapping("/tipoPagamentoId/{id}")
    @ApiOperation(value = "Deleta um tipoPagamento")
    public ResponseEntity<Void>deletar(@PathVariable Long id){
        tipoPagamentoService.deletaTipoPagamentoPorId(id);
        return noContent().build();
    }

}
