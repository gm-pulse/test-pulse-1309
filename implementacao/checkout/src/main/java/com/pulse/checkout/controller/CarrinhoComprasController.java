package com.pulse.checkout.controller;

import com.pulse.checkout.model.CarrinhoCompras;
import com.pulse.checkout.model.Produto;
import com.pulse.checkout.repository.CarrinhoComprasRepository;
import com.pulse.checkout.services.CarrinhoComprasService;
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
@Api(value = "Api Rest Carrinho Compras")
@CrossOrigin(origins = "*")
public class CarrinhoComprasController {

    private final CarrinhoComprasService carrinhoComprasService;
    private final CarrinhoComprasRepository carrinhoComprasRepository;

    @GetMapping("/carrinhosCompra")
    @ApiOperation(value = "Retorna os carrinhos de compra cadastrados")
    public ResponseEntity<List<CarrinhoCompras>> buscarTodos(){
        List<CarrinhoCompras> carrinhosCompraCadastrados = carrinhoComprasRepository.findAll();

        if(carrinhosCompraCadastrados.isEmpty()) {
            return noContent().build();
        }else{
            return ok(carrinhosCompraCadastrados);
        }
    }

    @GetMapping("/carrinhoComprasId/{id}")
    @ApiOperation(value = "Busca carrinhoCompras pelo id")
    public ResponseEntity<CarrinhoCompras> buscaPorId(@PathVariable Long id){
        return ok(carrinhoComprasService.buscaPorId(id));
    }

    @GetMapping("/carrinhoComprasPorClienteId/{clienteId}")
    @ApiOperation(value = "Busca carrinho compras pelo id do cliente")
    public ResponseEntity<List<CarrinhoCompras>> buscaPorClienteId(@PathVariable Long clienteId){

        List<CarrinhoCompras> carrinhosCompraPorCliente = carrinhoComprasService.buscaPorClienteId(clienteId);

        if(carrinhosCompraPorCliente.isEmpty()) {
            return noContent().build();
        }else{
            return ok(carrinhosCompraPorCliente);
        }
    }

    @PostMapping("/carrinhoCompras/{idCliente}")
    @ApiOperation(value = "1. O cliente cria um carrinho de compras")
    public ResponseEntity<CarrinhoCompras>clienteCriaCarrinho(@Valid @PathVariable Long idCliente){
        CarrinhoCompras novoCarrinhoCompras = carrinhoComprasService.clienteCriaCarrinho(idCliente);

        return new ResponseEntity<>(novoCarrinhoCompras,HttpStatus.CREATED);
    }

    @PostMapping("/carrinhoCompras")
    @ApiOperation(value = "Cadastra um novo carrinho de compras")
    public ResponseEntity<CarrinhoCompras>cadastrar(@Valid @RequestBody CarrinhoCompras carrinhoCompras){
        CarrinhoCompras novoCarrinhoCompras = carrinhoComprasService.salvar(carrinhoCompras);
        return new ResponseEntity<>(novoCarrinhoCompras, HttpStatus.CREATED);
    }

    @PutMapping("/carrinhoComprasUpdate/{id}")
    @ApiOperation(value = "Atualiza dados de um carrinho de compras")
    public ResponseEntity<CarrinhoCompras> atualizar(@Valid @RequestBody CarrinhoCompras carrinhoCompras){
        CarrinhoCompras carrinhoComprasAtualizado = carrinhoComprasService.alterar(carrinhoCompras);
        return new ResponseEntity<>(carrinhoComprasAtualizado, HttpStatus.CREATED);
    }

    @PutMapping("carrinhoCompras/adicionarItens/{idProduto}/{qtdProdutos}/{idCarrinho}/")
    @ApiOperation(value = "2. O cliente coloca produtos nesse carrinho de compras;")
    public ResponseEntity<CarrinhoCompras>adicionaProdutos(@PathVariable Long idProduto, @PathVariable Integer qtdProdutos, @PathVariable Long idCarrinho){
        CarrinhoCompras carrinhoComprasAtualizado =  carrinhoComprasService.adicionaProdutosCarrinho(idProduto, qtdProdutos, idCarrinho);
        return new ResponseEntity<>(carrinhoComprasAtualizado, HttpStatus.CREATED);
    }

    @PutMapping("carrinhoCompras/removerItens/{idProduto}/{idCarrinho}/{qtdProdutos}")
    @ApiOperation(value = "2.Remove produtos no carrinho")
    public ResponseEntity<CarrinhoCompras>removeProdutos(@PathVariable Long idProduto, @PathVariable Long idCarrinho, @PathVariable Integer qtdProdutos){
        CarrinhoCompras carrinhoComprasAtualizado =  carrinhoComprasService.removeProdutosCarrinho(idProduto, qtdProdutos, idCarrinho);
        return new ResponseEntity<>(carrinhoComprasAtualizado, HttpStatus.CREATED);
    }

    @DeleteMapping("/carrinhoComprasId/{id}")
    @ApiOperation(value = "Deleta um carrinho de compras")
    public ResponseEntity<Void>deletar(@PathVariable Long id){
        carrinhoComprasService.deletaCarrinhoPorId(id);
        return noContent().build();
    }


}
