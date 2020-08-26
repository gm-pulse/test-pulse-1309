package com.pulse.checkout.controller;

import com.pulse.checkout.model.StatusCompra;
import com.pulse.checkout.model.StatusCompra;
import com.pulse.checkout.repository.StatusCompraRepository;
import com.pulse.checkout.services.StatusCompraService;
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
@Api(value = "Api Rest Status Compra")
@CrossOrigin(origins = "*")
public class StatusCompraController {

    private final StatusCompraService statusCompraService;
    private final StatusCompraRepository statusCompraRepository;

    @GetMapping("/statusCompra")
    @ApiOperation(value = "Retorna os status compra cadastrados")
    public ResponseEntity<List<StatusCompra>> buscarTodos(){
        List<StatusCompra> statusCompraCadastrados = statusCompraRepository.findAll();

        if(statusCompraCadastrados.isEmpty()) {
            return noContent().build();
        }else{
            return ok(statusCompraCadastrados);
        }
    }

    @GetMapping("/statusCompraId/{id}")
    @ApiOperation(value = "Busca statusCompra pelo id")
    public ResponseEntity<StatusCompra> buscaPorId(@PathVariable Long id){
        return ok(statusCompraService.buscaPorId(id));
    }

    @PostMapping("/statusCompra")
    @ApiOperation(value = "Cadastra um novo statusCompra")
    public ResponseEntity<StatusCompra>cadastrar(@Valid @RequestBody StatusCompra statusCompra){
        StatusCompra novoStatusCompra = statusCompraService.salvar(statusCompra);
        return new ResponseEntity<>(novoStatusCompra, HttpStatus.CREATED);
    }

    @PutMapping("/statusCompraUpdate/{id}")
    @ApiOperation(value = "Atualiza dados de um statusCompra")
    public ResponseEntity<StatusCompra> atualizar(@Valid @RequestBody StatusCompra statusCompra){
        StatusCompra statusCompraAtualizado = statusCompraService.alterar(statusCompra);
        return new ResponseEntity<>(statusCompraAtualizado, HttpStatus.CREATED);
    }

    @DeleteMapping("/statusCompraId/{id}")
    @ApiOperation(value = "Deleta um statusCompra")
    public ResponseEntity<Void>deletar(@PathVariable Long id){
        statusCompraService.deletaStatusCompraPorId(id);
        return noContent().build();
    }

}
