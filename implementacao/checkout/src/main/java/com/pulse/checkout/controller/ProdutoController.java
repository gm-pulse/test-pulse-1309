package com.pulse.checkout.controller;

import com.pulse.checkout.model.Cliente;
import com.pulse.checkout.model.Produto;
import com.pulse.checkout.repository.ProdutoRepository;
import com.pulse.checkout.services.ProdutoService;
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
@Api(value = "Api Rest Produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final ProdutoService produtoService;


    @GetMapping("/produtos")
    @ApiOperation(value = "Retorna os produtos cadastrados")
    public ResponseEntity<List<Produto>> buscarTodos(){
        List<Produto> produtosCadastrados = produtoRepository.findAll();

        if(produtosCadastrados.isEmpty()) {
            return noContent().build();
        }else{
            return ok(produtosCadastrados);
        }
    }

    @GetMapping("/produtoId/{id}")
    @ApiOperation(value = "Busca produto pelo id")
    public ResponseEntity<Produto> buscaPorId(@PathVariable Long id){
        return ok(produtoService.buscaPorId(id));
    }

    @PostMapping("/produto")
    @ApiOperation(value = "Cadastra um novo produto")
    public ResponseEntity<Produto>cadastrar(@Valid @RequestBody Produto produto){
        Produto novoProduto = produtoService.salvar(produto);
        return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
    }

    @PutMapping("/produtoUpdate/{id}")
    @ApiOperation(value = "Atualiza dados de um produto")
    public ResponseEntity<Produto> atualizar(@Valid @RequestBody Produto produto){
        Produto produtoAtualizado = produtoService.alterar(produto);
        return new ResponseEntity<>(produtoAtualizado, HttpStatus.CREATED);
    }

    @DeleteMapping("/produtoId/{id}")
    @ApiOperation(value = "Deleta um produto")
    public ResponseEntity<Void>deletar(@PathVariable Long id){
        produtoService.deletaProdutoPorId(id);
        return noContent().build();
    }
}
