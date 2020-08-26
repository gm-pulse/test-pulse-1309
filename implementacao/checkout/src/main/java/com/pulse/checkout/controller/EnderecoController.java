package com.pulse.checkout.controller;

import com.pulse.checkout.model.Endereco;
import com.pulse.checkout.repository.EnderecoRepository;
import com.pulse.checkout.services.EnderecoService;
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
@Api(value = "Api Rest Enderecos")
@CrossOrigin(origins = "*")
public class EnderecoController {

    private final EnderecoRepository enderecoRepository;
    private final EnderecoService enderecoService;

    @GetMapping("/enderecos")
    @ApiOperation(value = "Retorna os enderecos cadastrados")
    public ResponseEntity<List<Endereco>> buscarTodos(){
        List<Endereco> enderecosCadastrados = enderecoRepository.findAll();

        if(enderecosCadastrados.isEmpty()) {
            return noContent().build();
        }else{
            return ok(enderecosCadastrados);
        }
    }

    @GetMapping("/enderecoId/{id}")
    @ApiOperation(value = "Busca endereco pelo id")
    public ResponseEntity<Endereco> buscaPorId(@PathVariable Long id){
        return ok(enderecoService.buscaPorId(id));
    }


    @PostMapping("/endereco")
    @ApiOperation(value = "Cadastra um novo endereco")
    public ResponseEntity<Endereco>cadastrar(@Valid @RequestBody Endereco endereco){
        Endereco novoEndereco = enderecoService.salvar(endereco);
        return new ResponseEntity<>(novoEndereco, HttpStatus.CREATED);
    }

    @PutMapping("/enderecoUpdate/{id}")
    @ApiOperation(value = "Atualiza dados de um endereco")
    public ResponseEntity<Endereco> atualizar(@Valid @RequestBody Endereco endereco){
        Endereco enderecoAtualizado = enderecoService.alterar(endereco);
        return new ResponseEntity<>(enderecoAtualizado, HttpStatus.CREATED);
    }

    @DeleteMapping("/enderecoId/{id}")
    @ApiOperation(value = "Deleta um endereco")
    public ResponseEntity<Void>deletar(@PathVariable Long id){
        enderecoService.deletaEnderecoPorId(id);
        return noContent().build();
    }
}
