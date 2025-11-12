package com.tecdes.pedido.service;

import java.util.List;

import com.tecdes.pedido.model.entity.Cliente;
import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.repository.PedidoRepository;
import com.tecdes.pedido.repository.PedidoRepositoryImpl;
import com.tecdes.pedido.repository.ProdutoRepository;
import com.tecdes.pedido.repository.ProdutoRepositoryImpl;
import com.tecdes.pedido.model.entity.ItemPedido;
import java.time.LocalDateTime;


public class PedidoService {

    // private final PedidoRepository repository = new PedidoRepositoryImpl();


    // // --- Criar ---
    // public void salvarPedido(String nome, Double preco) {
    //     if (nome == null || nome.trim().isEmpty()) {
    //         throw new IllegalArgumentException("O campo produto é obrigatório.");
    //     }
    //     if (preco == null) {
    //         throw new IllegalArgumentException("O campo preço é obrigatório.");
    //     }


    //     Pedido pedido = new Pedido(nome, preco);
    //     repository.save(pedido);
    // }


    // // --- Listar todos ---
    // public List<Produto> buscarTodos() {
    //     return repository.findAll();
    // }


    // // --- Buscar por ID ---
    // public Pedido buscarPorId(int id) {
    //     Pedido pedido = repository.findById(id);
    //     if (pedido == null) {
    //         throw new IllegalArgumentException("Pedido não encontrado com ID: " + id);
    //     }
    //     return pedido;
    // }


    // // --- Atualizar ---
    // public void atualizarProduto(int id, String nome, Double preco) {
    //     Produto produtoExistente = repository.findById(id);
    //     if (produtoExistente == null) {
    //         throw new IllegalArgumentException("Produto não encontrado para atualização. ID: " + id);
    //     }


    //     if (nome == null || nome.trim().isEmpty()) {
    //         throw new IllegalArgumentException("O campo produto é obrigatório.");
    //     }
    //     if (preco == null) {
    //         throw new IllegalArgumentException("O campo preço é obrigatório.");
    //     }


    //     produtoExistente.setNome(nome);
    //     produtoExistente.setPreco(preco);


    //     repository.update(produtoExistente);
    // }


    // // --- Deletar ---
    // public void deletarProduto(int id) {
    //     Produto produto = repository.findById(id);
    //     if (produto == null) {
    //         throw new IllegalArgumentException("Produto não encontrado para exclusão. ID: " + id);
    //     }
    //     repository.delete(id);
    // }
}