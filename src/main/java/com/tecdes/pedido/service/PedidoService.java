package com.tecdes.pedido.service;

import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.model.entity.ItemPedido;
import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.repository.PedidoRepository; 

import java.time.LocalDateTime;
import java.util.List;

public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoService produtoService; 

    public PedidoService(PedidoRepository pedidoRepository, ProdutoService produtoService) {
        this.pedidoRepository = pedidoRepository;
        this.produtoService = produtoService;
    }

    public Pedido finalizarPedido(Pedido novoPedido) {
        
        if (novoPedido.getProdutos() == null || novoPedido.getProdutos().isEmpty()) {
            throw new IllegalArgumentException("O pedido deve conter pelo menos um item.");
        }
        
        double total = 0.0;
        
        for (ItemPedido item : novoPedido.getProdutos()) { 
            // 1. Validar e Buscar o Produto no ProdutoService
            Long idProduto = item.getProduto().getIdProduto(); // Assumindo que ItemPedido.getProduto() retorna o Produto com o ID
            
            if (idProduto == null || idProduto <= 0) {
                 throw new IllegalArgumentException("ID de Produto inválido em um dos itens.");
            }
            
            Produto produto = produtoService.buscarPorId(idProduto); // Chamando o método correto do ProdutoService
            
            // 2. Validação da Quantidade
            if (item.getQuantidade() <= 0) {
                throw new IllegalArgumentException("Quantidade inválida para o item: " + produto.getNome());
            }

            // 3. Define o preço unitário e calcula o subtotal
            // Sempre usar o preço atual do produto do banco de dados (produto.getPreco())
            item.setPrecoUnitario(produto.getPreco()); 
            
            total += (item.getPrecoUnitario() * item.getQuantidade());
        }

        // 4. Finalização do Pedido
        novoPedido.setValorTotal(total);
        novoPedido.setDataHora(LocalDateTime.now());
        novoPedido.setStatus("Em Processamento");

        return pedidoRepository.save(novoPedido);
    }

    public Pedido buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido ID " + id + " não encontrado."));
    }

    public List<Pedido> buscarTodosPedidos() {
        return pedidoRepository.findAll();
    }
    
    public Pedido atualizarStatus(Long id, String novoStatus) {
        Pedido pedidoExistente = buscarPedidoPorId(id);
        
        // **OPERAÇÃO DE NEGÓCIO BÁSICA**
        pedidoExistente.setStatus(novoStatus);
        return pedidoRepository.save(pedidoExistente);
    }
    
    public Pedido cancelarPedido(Long id) {
        Pedido pedidoExistente = buscarPedidoPorId(id);
        
        // **REGRA DE NEGÓCIO**
        if (pedidoExistente.getStatus().equals("Entregue")) {
             throw new RuntimeException("Não é possível cancelar um pedido já entregue.");
        }
        
        pedidoExistente.setStatus("Cancelado");
        
        // **OPERAÇÃO DE NEGÓCIO ADICIONAL**
        // A lógica de estorno/devolução de estoque seria adicionada aqui.
        
        return pedidoRepository.save(pedidoExistente);
    }
}