package com.tecdes.pedido.controller;

import java.util.List;
import com.tecdes.pedido.model.entity.ItemPedido;
import com.tecdes.pedido.service.ItemPedidoService;

public class ItemPedidoController {

    private final ItemPedidoService service;

    public ItemPedidoController() {
        this.service = new ItemPedidoService();
    }

    // Adicionar item ao pedido
    public void adicionarItem(int idPedido, int idProduto, int quantidade) {
        service.adicionarItem(idPedido, idProduto, quantidade);
    }

    // Buscar item por ID
    public ItemPedido buscarPorId(int id) {
        return service.buscarPorId(id);
    }

    // Listar todos os itens de um pedido
    public List<ItemPedido> listarPorPedido(int idPedido) {
        return service.listarPorPedido(idPedido);
    }

    // Listar todos os itens
    public List<ItemPedido> listarTodos() {
        return service.listarTodos();
    }

    // Atualizar item
    public void atualizar(ItemPedido item) {
        service.atualizar(item);
    }
    
    // Atualizar quantidade de um item
    public void atualizarQuantidade(int idItem, int novaQuantidade) {
        service.atualizarQuantidade(idItem, novaQuantidade);
    }

    // Deletar item
    public void deletar(int id) {
        service.deletar(id);
    }
    
    // Limpar todos os itens de um pedido
    public void limparItensPedido(int idPedido) {
        service.limparItensPedido(idPedido);
    }
    
    // Verificar se item existe
    public boolean itemExiste(int idItem) {
        return service.itemExiste(idItem);
    }
    
    // Contar itens de um pedido
    public int contarItensPorPedido(int idPedido) {
        return service.contarItensPorPedido(idPedido);
    }
    
    // Validar dados antes de adicionar
    public boolean validarItem(int quantidade, int idProduto) {
        if (quantidade <= 0) {
            return false;
        }
        if (idProduto <= 0) {
            return false;
        }
        return true;
    }
}