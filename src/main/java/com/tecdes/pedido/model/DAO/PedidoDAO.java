package com.tecdes.pedido.model.DAO;


import com.tecdes.pedido.config.ConnectionFactory;
import com.tecdes.pedido.model.entity.Pedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PedidoDAO {


    // INSERIR PEDIDO
    public void inserir(Pedido pedido) {
        String sql = "INSERT INTO T_SGP_PEDIDO (st_pedido, nr_pedido, id_cliente, id_endereco) VALUES (?, ?, ?, ?)";


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


            stmt.setString(1, String.valueOf(pedido.getStPedido())); // char para String
            stmt.setInt(2, pedido.getNrPedido());
            stmt.setInt(3, pedido.getIdCliente());
            stmt.setInt(4, pedido.getIdEndereco());
            stmt.executeUpdate();


            // Pega o ID gerado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pedido.setIdPedido(rs.getInt(1)); // int, não Long
                }
            }


            System.out.println("✅ Pedido inserido com ID: " + pedido.getIdPedido());


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao inserir pedido: " + e.getMessage(), e);
        }
    }


    // BUSCAR TODOS PEDIDOS
    public List<Pedido> buscarTodos() {
        String sql = "SELECT id_pedido, st_pedido, nr_pedido, id_cliente, id_endereco FROM T_SGP_PEDIDO ORDER BY id_pedido DESC";
        List<Pedido> pedidos = new ArrayList<>();


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {


            while (rs.next()) {
                pedidos.add(mapResultSetToPedido(rs));
            }


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao buscar pedidos: " + e.getMessage(), e);
        }


        return pedidos;
    }


    // BUSCAR PEDIDO POR ID
    public Pedido buscarPorId(int id) { // int, não Long
        String sql = "SELECT id_pedido, st_pedido, nr_pedido, id_cliente, id_endereco FROM T_SGP_PEDIDO WHERE id_pedido = ?";
       
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setInt(1, id);
           
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPedido(rs);
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao buscar pedido por ID: " + e.getMessage(), e);
        }


        return null;
    }


    // BUSCAR PEDIDOS POR CLIENTE
    public List<Pedido> buscarPorCliente(int idCliente) {
        String sql = "SELECT id_pedido, st_pedido, nr_pedido, id_cliente, id_endereco FROM T_SGP_PEDIDO WHERE id_cliente = ? ORDER BY id_pedido DESC";
        List<Pedido> pedidos = new ArrayList<>();


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setInt(1, idCliente);
           
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(mapResultSetToPedido(rs));
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao buscar pedidos do cliente: " + e.getMessage(), e);
        }


        return pedidos;
    }


    // BUSCAR PEDIDOS POR STATUS
    public List<Pedido> buscarPorStatus(char status) {
        String sql = "SELECT id_pedido, st_pedido, nr_pedido, id_cliente, id_endereco FROM T_SGP_PEDIDO WHERE st_pedido = ? ORDER BY id_pedido DESC";
        List<Pedido> pedidos = new ArrayList<>();


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setString(1, String.valueOf(status));
           
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(mapResultSetToPedido(rs));
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao buscar pedidos por status: " + e.getMessage(), e);
        }


        return pedidos;
    }


    // ATUALIZAR PEDIDO
    public void atualizar(Pedido pedido) {
        String sql = "UPDATE T_SGP_PEDIDO SET st_pedido = ?, nr_pedido = ?, id_cliente = ?, id_endereco = ? WHERE id_pedido = ?";


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setString(1, String.valueOf(pedido.getStPedido()));
            stmt.setInt(2, pedido.getNrPedido());
            stmt.setInt(3, pedido.getIdCliente());
            stmt.setInt(4, pedido.getIdEndereco());
            stmt.setInt(5, pedido.getIdPedido());
            stmt.executeUpdate();


            System.out.println("✅ Pedido atualizado: " + pedido.getIdPedido());


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao atualizar pedido: " + e.getMessage(), e);
        }
    }


    // ATUALIZAR APENAS STATUS DO PEDIDO
    public void atualizarStatus(int idPedido, char novoStatus) {
        String sql = "UPDATE T_SGP_PEDIDO SET st_pedido = ? WHERE id_pedido = ?";


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setString(1, String.valueOf(novoStatus));
            stmt.setInt(2, idPedido);
            stmt.executeUpdate();


            System.out.println("✅ Status do pedido " + idPedido + " atualizado para: " + novoStatus);


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao atualizar status do pedido: " + e.getMessage(), e);
        }
    }


    // DELETAR PEDIDO
    public void deletar(int id) { // int, não Long
        // Primeiro deleta os itens do pedido (por causa da FK)
        String sqlItens = "DELETE FROM T_SGP_ITEM_PEDIDO WHERE id_pedido = ?";
        String sqlPedido = "DELETE FROM T_SGP_PEDIDO WHERE id_pedido = ?";


        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false); // Inicia transação
           
            try (PreparedStatement stmtItens = conn.prepareStatement(sqlItens);
                 PreparedStatement stmtPedido = conn.prepareStatement(sqlPedido)) {
               
                // 1. Deleta itens do pedido
                stmtItens.setInt(1, id);
                stmtItens.executeUpdate();
               
                // 2. Deleta o pedido
                stmtPedido.setInt(1, id);
                int rowsAffected = stmtPedido.executeUpdate();
               
                conn.commit(); // Confirma transação
               
                if (rowsAffected > 0) {
                    System.out.println("✅ Pedido deletado: " + id);
                } else {
                    System.out.println("⚠️ Pedido não encontrado: " + id);
                }
               
            } catch (SQLException e) {
                conn.rollback(); // Desfaz em caso de erro
                throw e;
            }
           
        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao deletar pedido: " + e.getMessage(), e);
        }
    }


    // CONTAR TOTAL DE PEDIDOS
    public int contarTotal() {
        String sql = "SELECT COUNT(*) FROM T_SGP_PEDIDO";
       
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {


            if (rs.next()) {
                return rs.getInt(1);
            }
           
        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao contar pedidos: " + e.getMessage(), e);
        }
       
        return 0;
    }


    // PRÓXIMO NÚMERO DE PEDIDO (para auto-incremento lógico)
    public int proximoNumeroPedido() {
        String sql = "SELECT COALESCE(MAX(nr_pedido), 0) + 1 FROM T_SGP_PEDIDO";
       
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {


            if (rs.next()) {
                return rs.getInt(1);
            }
           
        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao buscar próximo número de pedido: " + e.getMessage(), e);
        }
       
        return 1; // Primeiro pedido
    }


    // MÉTODO AUXILIAR: Converter ResultSet para Pedido
    private Pedido mapResultSetToPedido(ResultSet rs) throws SQLException {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(rs.getInt("id_pedido"));
        pedido.setStPedido(rs.getString("st_pedido").charAt(0)); // String para char
        pedido.setNrPedido(rs.getInt("nr_pedido"));
        pedido.setIdCliente(rs.getInt("id_cliente"));
        pedido.setIdEndereco(rs.getInt("id_endereco"));
        return pedido;
    }
}
