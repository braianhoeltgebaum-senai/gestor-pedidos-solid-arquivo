package com.tecdes.pedido.service;

import java.util.List;
import com.tecdes.pedido.model.entity.ItemPedido;
import com.tecdes.pedido.repository.ItemPedidoRepository;
import com.tecdes.pedido.repository.ItemPedidoRepositoryImpl;

public class ItemPedidoService {

    private final ItemPedidoRepository repository;

    // CORRIGIDO: Inicialização no construtor
    public ItemPedidoService() {
        this.repository = new ItemPedidoRepositoryImpl();
    }

    public void salvar(ItemPedido item) {
        if (item.getQtProduto() <= 0) { // MUDOU: getQuantidade() → getQtProduto()
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
        if (item.getIdPedido() <= 0) {
            throw new IllegalArgumentException("ID do pedido é obrigatório.");
        }
        if (item.getIdProduto() <= 0) {
            throw new IllegalArgumentException("ID do produto é obrigatório.");
        }
        repository.save(item);
    }
    
    public void adicionarItem(int idPedido, int idProduto, int quantidade) {
        ItemPedido item = new ItemPedido();
        item.setQtProduto(quantidade); // MUDOU: setQuantidade() → setQtProduto()
        item.setIdPedido(idPedido);
        item.setIdProduto(idProduto);
        salvar(item);
    }

    public ItemPedido buscarPorId(int id) {
        ItemPedido item = repository.findById(id);
        if (item == null) {
            throw new RuntimeException("Item não encontrado com ID: " + id);
        }
        return item;
    }

    public List<ItemPedido> listarTodos() {
        return repository.findAll();
    }
    
    public List<ItemPedido> listarPorPedido(int idPedido) {
        return repository.findByPedido(idPedido);
    }

    public void atualizar(ItemPedido item) {
        // Verifica se item existe
        buscarPorId(item.getIdItem());
        repository.update(item);
    }
    
    public void atualizarQuantidade(int idItem, int novaQuantidade) {
        if (novaQuantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        ItemPedido item = buscarPorId(idItem);
        item.setQtProduto(novaQuantidade); // MUDOU: setQuantidade() → setQtProduto()
        repository.update(item);
    }

    public void deletar(int id) {
        // Verifica se item existe
        buscarPorId(id);
        repository.delete(id);
    }
    
    public List<ItemPedido> buscarItensPorPedido(int idPedido) {
        return repository.findByPedido(idPedido);
    }
    
    public void limparItensPedido(int idPedido) {
        List<ItemPedido> itens = listarPorPedido(idPedido);
        for (ItemPedido item : itens) {
            deletar(item.getIdItem());
        }
    }
    
    // REMOVIDO: calcularSubtotal (precisa de ProdutoService)
    // Isso deve ser feito em um Service mais alto (PedidoService)
    
    // NOVO: Verificar se item existe
    public boolean itemExiste(int idItem) {
        return repository.existsById(idItem);
    }
    
    // NOVO: Contar itens de um pedido
    public int contarItensPorPedido(int idPedido) {
        return listarPorPedido(idPedido).size();
    }
}