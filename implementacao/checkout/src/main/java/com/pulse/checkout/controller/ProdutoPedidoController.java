package com.pulse.checkout.controller;

import com.pulse.checkout.model.ProdutoPedido;
import com.pulse.checkout.repository.ProdutoPedidoRepository;
import com.pulse.checkout.services.ProdutoPedidoService;
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
@Api(value = "Api Rest Produtos Pedidos")
@CrossOrigin(origins = "*")
public class ProdutoPedidoController {

    private final ProdutoPedidoService produtoPedidoService;
    private final ProdutoPedidoRepository produtoPedidoRepository;


    @GetMapping("/produtoPedidos")
    @ApiOperation(value = "Retorna os produtoPedidos cadastrados")
    public ResponseEntity<List<ProdutoPedido>> buscarTodos(){
        List<ProdutoPedido> produtoPedidosCadastrados = produtoPedidoRepository.findAll();

        if(produtoPedidosCadastrados.isEmpty()) {
            return noContent().build();
        }else{
            return ok(produtoPedidosCadastrados);
        }
    }

    @GetMapping("/produtoPedidoId/{id}")
    @ApiOperation(value = "Busca produtoPedido pelo id")
    public ResponseEntity<ProdutoPedido> buscaPorId(@PathVariable Long id){
        return ok(produtoPedidoService.buscaPorId(id));
    }


    @GetMapping("/produtoPedidoPorProdutoId/{produtoId}")
    @ApiOperation(value = "Busca produtos pedidos pelo id do produto")
    public ResponseEntity<List<ProdutoPedido>> buscaPorProdutoId(@PathVariable Long produtoId){

        List<ProdutoPedido> produtoPedidosPorCarrinho = produtoPedidoService.listarProdutosPedidosPorProdutoId(produtoId);


        if(produtoPedidosPorCarrinho.isEmpty()) {
            return noContent().build();
        }else {
            return ok(produtoPedidosPorCarrinho);
        }
    }

    @GetMapping("/produtoPedidoPorClienteId/{clienteId}")
    @ApiOperation(value = "Busca produtos pedidos pelo id do cliente")
    public ResponseEntity<List<ProdutoPedido>> buscaPorClienteId(@PathVariable Long clienteId){

        List<ProdutoPedido> produtoPedidosPorCliente = produtoPedidoService.listarProdutosPedidosPorClienteId(clienteId);


        if(produtoPedidosPorCliente.isEmpty()) {
            return noContent().build();
        }else {
            return ok(produtoPedidosPorCliente);
        }
    }

    @GetMapping("/produtoPedidoPorCarrinhoId/{carrinhoId}")
    @ApiOperation(value = "Busca produtos pedidos pelo id do carrinho")
    public ResponseEntity<List<ProdutoPedido>> buscaPorCarrinhoId(@PathVariable Long carrinhoId){

        List<ProdutoPedido> produtoPedidosPorCarrinho = produtoPedidoService.listarProdutosPedidosPorCarrinhoId(carrinhoId);


        if(produtoPedidosPorCarrinho.isEmpty()) {
            return noContent().build();
        }else {
            return ok(produtoPedidosPorCarrinho);
        }
    }
}
