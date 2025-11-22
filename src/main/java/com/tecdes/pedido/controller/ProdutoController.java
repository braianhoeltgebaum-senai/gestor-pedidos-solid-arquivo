package com.tecdes.pedido.controller;


import java.util.List;


import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.service.ProdutoService;


public class ProdutoController {


    private final ProdutoService service;

    
    public ProdutoController(ProdutoService service) {
        this.service = service;
    }


    // Salva
    public void save(String nome ,  double preco, String categoria, String descricao) {
        service.salvarProduto(nome, preco, categoria, descricao);
    }


    // Busca todos
    public List<Produto> buscarTodos() {
        return service.buscarTodos();
    }


     // Busca por Id
     public Produto findById(int idProduto){
        return service.buscarPorId(idProduto);
    }


    // Atualiza
    public void update(int idProduto, String nome ,  double preco, String categoria, String descricao){
        service.atualizarProduto(idProduto, nome, preco, categoria, descricao);
    }


    // Deleta
    public void delete(int idProduto){
        service.deletarProduto(idProduto);
    }
}


