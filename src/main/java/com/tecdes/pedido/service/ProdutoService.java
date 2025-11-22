package com.tecdes.pedido.service;


import java.util.List;


import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.repository.ProdutoRepository;
import com.tecdes.pedido.repository.ProdutoRepositoryImpl;


public class ProdutoService {


    private final ProdutoRepository repository = new ProdutoRepositoryImpl();


      // Valida os campos
        private void validarCampos(String nome, double preco, String categoria, String descricao) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo nome é obrigatório.");
        }
        if (preco < 0) {
            throw new IllegalArgumentException("O campo preço não pode ser negativo.");
        }
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo categoria é obrigatório.");
        }
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo descrição é obrigatório.");
        }
    }

    // --- Salva ---
    public void salvarProduto(String nome, double preco, String categoria, String descricao) {
        validarCampos(nome, preco, categoria, descricao);
        Produto produto = new Produto(nome, preco, categoria, descricao);
        
        repository.save(produto);
        System.out.println("Produto salvo: " + nome);
    }


    // --- Listar todos ---
    public List<Produto> buscarTodos() {
        return repository.findAll();
    }


    // --- Buscar por ID ---
    public Produto buscarPorId(int idProduto) {
        Produto produto = repository.findById(idProduto);
        if (produto == null) {
            throw new IllegalArgumentException("Produto não encontrado com ID: " + idProduto);
        }
        return produto;
    }


    // --- Atualizar ---
    public void atualizarProduto(int idProduto, String nome,  double preco, String categoria, String descricao) {
        Produto produtoExistente = repository.findById(idProduto);

        if (produtoExistente == null) {
            throw new IllegalArgumentException("Produto não encontrado para atualização. ID: " + idProduto);
        }
       

        produtoExistente.setIdProduto(idProduto);
        produtoExistente.setNome(nome);
        produtoExistente.setPreco(preco);
        produtoExistente.setCategoria(categoria);
        produtoExistente.setDescricao(descricao);


        repository.update(produtoExistente);
        System.out.println("Produto atualizado: " + nome);
    }


    // --- Deletar ---
    public void deletarProduto(int idProduto) {
        Produto produto = repository.findById(idProduto);
        if (produto == null) {
            throw new IllegalArgumentException("Produto não encontrado para exclusão. ID: " + idProduto);
        }
        repository.delete(idProduto);
        System.out.println("Produto deletado: " + idProduto);
    }
}


