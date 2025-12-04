package com.tecdes.pedido.model.DAO;

import com.tecdes.pedido.model.entity.ItemPedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemPedidoDAO {
    
    private Connection conn;
    
    public ItemPedidoDAO(Connection conn) {
        this.conn = conn;
    }
    
    // INSERIR ItemPedido
    public void salvar(ItemPedido item) throws SQLException {
        String sql = "INSERT INTO T_SGP_ITEM_PEDIDO (qt_produto, id_pedido, id_produto) VALUES (?, ?, ?)";
        
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, item.getQtProduto());
            ps.setInt(2, item.getIdPedido());
            ps.setInt(3, item.getIdProduto());
            ps.executeUpdate();
            
            // Pega o ID gerado
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setIdItem(generatedKeys.getInt(1));
                }
            }
        }
    }
    
    // BUSCAR POR ID
    public ItemPedido buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM T_SGP_ITEM_PEDIDO WHERE id_item = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToItemPedido(rs);
                }
            }
        }
        return null;
    }
    
    // LISTAR TODOS
    public List<ItemPedido> listar() throws SQLException {
        List<ItemPedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_SGP_ITEM_PEDIDO";
        
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                lista.add(mapResultSetToItemPedido(rs));
            }
        }
        return lista;
    }
    
    // LISTAR ITENS DE UM PEDIDO ESPECÍFICO
    public List<ItemPedido> listarPorPedido(int idPedido) throws SQLException {
        List<ItemPedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_SGP_ITEM_PEDIDO WHERE id_pedido = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSetToItemPedido(rs));
                }
            }
        }
        return lista;
    }
    
    // ALIAS para findByPedido (para compatibilidade com a interface)
    public List<ItemPedido> buscarPorPedido(int idPedido) throws SQLException {
        return listarPorPedido(idPedido);
    }
    
    // ATUALIZAR
    public void atualizar(ItemPedido item) throws SQLException {
        String sql = "UPDATE T_SGP_ITEM_PEDIDO SET qt_produto = ?, id_pedido = ?, id_produto = ? WHERE id_item = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, item.getQtProduto());
            ps.setInt(2, item.getIdPedido());
            ps.setInt(3, item.getIdProduto());
            ps.setInt(4, item.getIdItem());
            ps.executeUpdate();
        }
    }
    
    // DELETAR
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM T_SGP_ITEM_PEDIDO WHERE id_item = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    // DELETAR TODOS ITENS DE UM PEDIDO
    public void deletarPorPedido(int idPedido) throws SQLException {
        String sql = "DELETE FROM T_SGP_ITEM_PEDIDO WHERE id_pedido = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            ps.executeUpdate();
        }
    }
    
    // CONTAR ITENS DE UM PEDIDO
    public int contarPorPedido(int idPedido) throws SQLException {
        String sql = "SELECT COUNT(*) FROM T_SGP_ITEM_PEDIDO WHERE id_pedido = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
    
    // VERIFICAR SE ITEM EXISTE
    public boolean existePorId(int id) throws SQLException {
        return buscarPorId(id) != null;
    }
    
    // MÉTODO AUXILIAR: Converter ResultSet para ItemPedido
    private ItemPedido mapResultSetToItemPedido(ResultSet rs) throws SQLException {
        ItemPedido item = new ItemPedido();
        item.setIdItem(rs.getInt("id_item"));
        item.setQtProduto(rs.getInt("qt_produto"));
        item.setIdPedido(rs.getInt("id_pedido"));
        item.setIdProduto(rs.getInt("id_produto"));
        return item;
    }
}