package com.tecdes.pedido.service;

import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

public class ProdutoService {

    private final ProdutoRepository repository;

    // Construtor para injeção de dependência do Repositório
    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    private void validarDados(String nome, double preco) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }
        if (preco <= 0) {
            throw new IllegalArgumentException("O preço deve ser maior que zero.");
        }
    }

    /**
     * Cria e salva um novo produto no repositório.
     */
    public Produto salvarProduto(String nome, double preco, String categoria, String descricao) {
        validarDados(nome, preco);
        
        Produto produto = new Produto(nome, preco, categoria, descricao);
        
        return repository.save(produto);
    }

    /**
     * Busca um produto pelo seu identificador.
     */
    public Produto buscarProdutoPorId(Long idProduto) {
        if (idProduto == null || idProduto <= 0) {
            throw new IllegalArgumentException("ID do produto inválido.");
        }
        
        Optional<Produto> produto = repository.findById(idProduto);
        
        return produto.orElseThrow(() -> new RuntimeException("Produto ID " + idProduto + " não encontrado."));
    }

    /**
     * Busca todos os produtos.
     */
    public List<Produto> buscarTodos() {
        return repository.findAll();
    }

    /**
     * Atualiza os dados de um produto existente.
     */
    public Produto atualizarProduto(Long idProduto, String nome, double preco, String categoria, String descricao) {
        if (idProduto == null || idProduto <= 0) {
            throw new IllegalArgumentException("ID do produto inválido para atualização.");
        }
        validarDados(nome, preco);
        
        // 1. Busca a entidade existente
        Produto produtoExistente = buscarProdutoPorId(idProduto);
        
        // 2. Atualiza os campos
        produtoExistente.setNome(nome);
        produtoExistente.setPreco(preco);
        produtoExistente.setCategoria(categoria);
        produtoExistente.setDescricao(descricao);
        
        // 3. Salva a entidade atualizada
        return repository.save(produtoExistente);
    }

    /**
     * Deleta um produto pelo seu identificador.
     */
    public void deletarProduto(Long idProduto) {
        if (idProduto == null || idProduto <= 0) {
            throw new IllegalArgumentException("ID do produto inválido para exclusão.");
        }
        
        // Opcional: Verificar se o produto está em algum pedido antes de deletar
        
        // Deleta o produto
        repository.delete(idProduto);
    }
}