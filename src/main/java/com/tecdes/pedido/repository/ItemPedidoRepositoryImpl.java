package com.tecdes.pedido.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import com.tecdes.pedido.model.DAO.ItemPedidoDAO;
import com.tecdes.pedido.model.entity.ItemPedido;
import com.tecdes.pedido.config.ConnectionFactory;

public class ItemPedidoRepositoryImpl implements ItemPedidoRepository {

    private final ItemPedidoDAO itemPedidoDAO;

    public ItemPedidoRepositoryImpl() {
        try {
            Connection conn = ConnectionFactory.getConnection();
            this.itemPedidoDAO = new ItemPedidoDAO(conn);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco", e);
        }
    }

    @Override
    public void save(ItemPedido item) {
        try {
            itemPedidoDAO.salvar(item);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar item do pedido: " + e.getMessage(), e);
        }
    }

    @Override
    public ItemPedido findById(int id) { // MUDOU: Long → int
        try {
            return itemPedidoDAO.buscarPorId(id);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar item por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ItemPedido> findAll() {
        try {
            return itemPedidoDAO.listar();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar itens: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(ItemPedido item) {
        try {
            itemPedidoDAO.atualizar(item);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar item: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) { // MUDOU: Long → int
        try {
            itemPedidoDAO.deletar(id);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar item: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<ItemPedido> findByPedido(int idPedido) {
        try {
            return itemPedidoDAO.buscarPorPedido(idPedido);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar itens por pedido: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean existsById(int id) {
        try {
            return itemPedidoDAO.buscarPorId(id) != null;
        } catch (SQLException e) {
            return false;
        }
    }
}