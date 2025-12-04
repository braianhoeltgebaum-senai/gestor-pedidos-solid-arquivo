package com.tecdes.pedido.service;

import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.repository.ProdutoRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    // CORRIGIDO: Agora usa char e BigDecimal
    private void validarDados(String nome, char tipo, BigDecimal valor, String descricao) {
        if (nome == null || nome.trim().isEmpty() || nome.length() > 60) {
            throw new IllegalArgumentException("Nome do produto é obrigatório (máx 60 caracteres)");
        }
        
        char tipoUpper = Character.toUpperCase(tipo);
        if (tipoUpper != 'L' && tipoUpper != 'B' && tipoUpper != 'C') {
            throw new IllegalArgumentException("Tipo deve ser: L (Lanche), B (Bebida) ou C (Complemento)");
        }
        
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }
        
        if (descricao == null || descricao.trim().isEmpty() || descricao.length() > 150) {
            throw new IllegalArgumentException("Descrição é obrigatória (máx 150 caracteres)");
        }
    }

    /**
     * Cria e salva um novo produto no repositório.
     */
    // CORRIGIDO: Agora com char e BigDecimal
    public Produto salvarProduto(String nome, char tipo, String descricao, BigDecimal valor) {
        validarDados(nome, tipo, valor, descricao);
        
        Produto produto = new Produto(nome, tipo, descricao, valor);
        return repository.save(produto);
    }
    
    // NOVO: Método conveniente para double
    public Produto salvarProduto(String nome, char tipo, String descricao, double valor) {
        return salvarProduto(nome, tipo, descricao, BigDecimal.valueOf(valor));
    }

    /**
     * Busca um produto pelo seu identificador.
     */
    public Produto buscarPorId(int idProduto) {
        if (idProduto <= 0) {
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
    
    // CORRIGIDO: Agora usa char
    public List<Produto> buscarPorTipo(char tipo) {
        return repository.findByTipo(tipo);
    }
    
    // NOVO: Buscar por nome
    public List<Produto> buscarPorNome(String nome) {
        return repository.findByNomeContaining(nome);
    }

    /**
     * Atualiza os dados de um produto existente.
     */
    // CORRIGIDO: Agora com char e BigDecimal
    public Produto atualizarProduto(int idProduto, String nome, char tipo, String descricao, BigDecimal valor) {
        if (idProduto <= 0) {
            throw new IllegalArgumentException("ID do produto inválido para atualização.");
        }
        validarDados(nome, tipo, valor, descricao);
        
        Produto produtoExistente = buscarPorId(idProduto);
        
        produtoExistente.setNmProduto(nome);
        produtoExistente.setTpProduto(tipo);
        produtoExistente.setDsProduto(descricao);
        produtoExistente.setVlProduto(valor);
        
        return repository.save(produtoExistente);
    }
    
    // NOVO: Método conveniente para double
    public Produto atualizarProduto(int idProduto, String nome, char tipo, String descricao, double valor) {
        return atualizarProduto(idProduto, nome, tipo, descricao, BigDecimal.valueOf(valor));
    }

    /**
     * Deleta um produto pelo seu identificador.
     */
    public void excluirProduto(int idProduto) {
        if (idProduto <= 0) {
            throw new IllegalArgumentException("ID do produto inválido para exclusão.");
        }
        
        if (!repository.existsById(idProduto)) {
            throw new RuntimeException("Produto ID " + idProduto + " não encontrado para exclusão.");
        }
        
        repository.delete(idProduto);
    }
    
    // CORRIGIDO: Agora com char e double
    public boolean validarProduto(String nome, char tipo, double valor) {
        try {
            validarDados(nome, tipo, BigDecimal.valueOf(valor), "Descrição padrão");
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    
    // NOVOS MÉTODOS:
    public int contarTotalProdutos() {
        return repository.contarTotal();
    }
    
    public List<Produto> buscarPorFaixaPreco(double min, double max) {
        return repository.buscarPorFaixaPreco(
            BigDecimal.valueOf(min), 
            BigDecimal.valueOf(max)
        );
    }
    
    // Verificar se produto existe
    public boolean produtoExiste(int idProduto) {
        return repository.existsById(idProduto);
    }
}