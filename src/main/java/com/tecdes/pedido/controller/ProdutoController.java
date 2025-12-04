package com.tecdes.pedido.controller;


import java.math.BigDecimal;
import java.util.List;
import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.service.ProdutoService;
import com.tecdes.pedido.repository.ProdutoRepositoryImpl;


public class ProdutoController {


    private final ProdutoService service;
   
    // CORRIGIDO: Construtor sem parâmetros
    public ProdutoController() {
        this.service = new ProdutoService(new ProdutoRepositoryImpl());
    }


    // CORRIGIDO: Agora com char e BigDecimal
    public void salvar(String nome, char tipo, String descricao, BigDecimal valor) {
        service.salvarProduto(nome, tipo, descricao, valor);
    }
   
    // NOVO: Método conveniente para double
    public void salvar(String nome, char tipo, String descricao, double valor) {
        service.salvarProduto(nome, tipo, descricao, valor);
    }


    public List<Produto> buscarTodos() {
        return service.buscarTodos();
    }


    public Produto buscarPorId(int idProduto) {
        return service.buscarPorId(idProduto);
    }


    // CORRIGIDO: Agora com char e BigDecimal
    public void atualizar(int idProduto, String nome, char tipo, String descricao, BigDecimal valor) {
        service.atualizarProduto(idProduto, nome, tipo, descricao, valor);
    }
   
    // NOVO: Método conveniente para double
    public void atualizar(int idProduto, String nome, char tipo, String descricao, double valor) {
        service.atualizarProduto(idProduto, nome, tipo, descricao, valor);
    }


    public void excluir(int idProduto) {
        service.excluirProduto(idProduto);
    }
   
    // NOVOS MÉTODOS:
    public List<Produto> buscarPorTipo(char tipo) {
        return service.buscarPorTipo(tipo);
    }
   
    public List<Produto> buscarPorNome(String nome) {
        return service.buscarPorNome(nome);
    }
   
    public int contarTotal() {
        return service.contarTotalProdutos();
    }
   
    public List<Produto> buscarPorFaixaPreco(double min, double max) {
        return service.buscarPorFaixaPreco(min, max);
    }
   
    public boolean validarProduto(String nome, char tipo, double valor) {
        return service.validarProduto(nome, tipo, valor);
    }
   
    public boolean produtoExiste(int idProduto) {
        return service.produtoExiste(idProduto);
    }
}

