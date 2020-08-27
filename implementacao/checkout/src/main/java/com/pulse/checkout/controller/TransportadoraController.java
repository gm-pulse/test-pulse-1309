package com.pulse.checkout.controller;

import com.pulse.checkout.model.Transportadora;
import com.pulse.checkout.repository.TransportadoraRepository;
import com.pulse.checkout.services.TransportadoraService;
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
@Api(value = "Api Rest Transportadora")
@CrossOrigin(origins = "*")
public class TransportadoraController {

    private final TransportadoraService transportadoraService;
    private final TransportadoraRepository transportadoraRepository;

    @GetMapping("/transportadoras")
    @ApiOperation(value = "Retorna os transportadoras cadastrados")
    public ResponseEntity<List<Transportadora>> buscarTodos(){
        List<Transportadora> transportadorasCadastrados = transportadoraRepository.findAll();

        if(transportadorasCadastrados.isEmpty()) {
            return noContent().build();
        }else{
            return ok(transportadorasCadastrados);
        }
    }

    @GetMapping("/transportadoraId/{id}")
    @ApiOperation(value = "Busca transportadora pelo id")
    public ResponseEntity<Transportadora> buscaPorId(@PathVariable Long id){
        return ok(transportadoraService.buscaPorId(id));
    }

    @GetMapping("/transportadoraCnpj/{cnpj}")
    @ApiOperation(value = "Busca transportadora pelo CNPJ")
    public ResponseEntity<Transportadora>buscaPorCnpj(@PathVariable String cnpj){
        return ok(transportadoraService.buscaPorCnpj(cnpj));
    }

    @PostMapping("/transportadora")
    @ApiOperation(value = "Cadastra um novo transportadora")
    public ResponseEntity<Transportadora>cadastrar(@Valid @RequestBody Transportadora transportadora){
        Transportadora novoTransportadora = transportadoraService.salvar(transportadora);
        return new ResponseEntity<>(novoTransportadora, HttpStatus.CREATED);
    }

    @PutMapping("/transportadoraUpdate/{id}")
    @ApiOperation(value = "Atualiza dados de um transportadora")
    public ResponseEntity<Transportadora> atualizar(@Valid @RequestBody Transportadora transportadora){
        Transportadora transportadoraAtualizado = transportadoraService.alterar(transportadora);
        return new ResponseEntity<>(transportadoraAtualizado, HttpStatus.CREATED);
    }

    @DeleteMapping("/transportadoraId/{id}")
    @ApiOperation(value = "Deleta um transportadora")
    public ResponseEntity<Void>deletar(@PathVariable Long id){
        transportadoraService.deletaTransportadoraPorId(id);
        return noContent().build();
    }
}
