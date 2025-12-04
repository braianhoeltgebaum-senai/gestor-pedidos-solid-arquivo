package com.tecdes.pedido.model.DAO;


import com.tecdes.pedido.config.ConnectionFactory;
import com.tecdes.pedido.model.entity.Pagamento;
import java.sql.*;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class PagamentoDAO {


    // INSERIR PAGAMENTO
    public void inserir(Pagamento pagamento) {
        String sql = "INSERT INTO T_SGP_PAGAMENTO (id_cliente, id_pedido, tp_pagamento, vl_total, st_pagamento, dt_pagamento) VALUES (?, ?, ?, ?, ?, ?)";


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


            stmt.setInt(1, pagamento.getIdCliente());
            stmt.setInt(2, pagamento.getIdPedido());
            stmt.setString(3, pagamento.getTpPagamento());
            stmt.setBigDecimal(4, pagamento.getVlTotal());
            stmt.setString(5, pagamento.getStPagamento());
            stmt.setTimestamp(6, Timestamp.valueOf(pagamento.getDtPagamento()));
            stmt.executeUpdate();


            // Pega o ID gerado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pagamento.setIdPagamento(rs.getInt(1));
                }
            }


            System.out.println("✅ Pagamento inserido com ID: " + pagamento.getIdPagamento());


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao inserir pagamento: " + e.getMessage(), e);
        }
    }


    // BUSCAR TODOS OS PAGAMENTOS
    public List<Pagamento> buscarTodos() {
        String sql = "SELECT * FROM T_SGP_PAGAMENTO ORDER BY dt_pagamento DESC";
        List<Pagamento> pagamentos = new ArrayList<>();


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {


            while (rs.next()) {
                pagamentos.add(mapearPagamento(rs));
            }


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao buscar pagamentos: " + e.getMessage(), e);
        }


        return pagamentos;
    }


    // BUSCAR PAGAMENTO POR ID
    public Pagamento buscarPorId(int id) {
        String sql = "SELECT * FROM T_SGP_PAGAMENTO WHERE id_pagamento = ?";
       
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setInt(1, id);
           
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearPagamento(rs);
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao buscar pagamento por ID: " + e.getMessage(), e);
        }


        return null;
    }


    // BUSCAR PAGAMENTOS POR PEDIDO
    public Pagamento buscarPorPedido(int idPedido) {
        String sql = "SELECT * FROM T_SGP_PAGAMENTO WHERE id_pedido = ?";
       
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setInt(1, idPedido);
           
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearPagamento(rs);
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao buscar pagamento por pedido: " + e.getMessage(), e);
        }


        return null;
    }


    // BUSCAR PAGAMENTOS POR CLIENTE
    public List<Pagamento> buscarPorCliente(int idCliente) {
        String sql = "SELECT * FROM T_SGP_PAGAMENTO WHERE id_cliente = ? ORDER BY dt_pagamento DESC";
        List<Pagamento> pagamentos = new ArrayList<>();


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setInt(1, idCliente);
           
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pagamentos.add(mapearPagamento(rs));
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao buscar pagamentos do cliente: " + e.getMessage(), e);
        }


        return pagamentos;
    }


    // BUSCAR PAGAMENTOS POR STATUS
    public List<Pagamento> buscarPorStatus(String status) {
        String sql = "SELECT * FROM T_SGP_PAGAMENTO WHERE st_pagamento = ? ORDER BY dt_pagamento DESC";
        List<Pagamento> pagamentos = new ArrayList<>();


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setString(1, status);
           
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pagamentos.add(mapearPagamento(rs));
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao buscar pagamentos por status: " + e.getMessage(), e);
        }


        return pagamentos;
    }


    // ATUALIZAR STATUS DO PAGAMENTO
    public void atualizarStatus(int idPagamento, String novoStatus) {
        String sql = "UPDATE T_SGP_PAGAMENTO SET st_pagamento = ?, dt_pagamento = CURRENT_TIMESTAMP WHERE id_pagamento = ?";


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setString(1, novoStatus);
            stmt.setInt(2, idPagamento);
            stmt.executeUpdate();


            System.out.println("✅ Status do pagamento atualizado: " + idPagamento);


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao atualizar status do pagamento: " + e.getMessage(), e);
        }
    }


    // ATUALIZAR PAGAMENTO COMPLETO
    public void atualizar(Pagamento pagamento) {
        String sql = "UPDATE T_SGP_PAGAMENTO SET id_cliente = ?, id_pedido = ?, tp_pagamento = ?, vl_total = ?, st_pagamento = ?, dt_pagamento = ? WHERE id_pagamento = ?";


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setInt(1, pagamento.getIdCliente());
            stmt.setInt(2, pagamento.getIdPedido());
            stmt.setString(3, pagamento.getTpPagamento());
            stmt.setBigDecimal(4, pagamento.getVlTotal());
            stmt.setString(5, pagamento.getStPagamento());
            stmt.setTimestamp(6, Timestamp.valueOf(pagamento.getDtPagamento()));
            stmt.setInt(7, pagamento.getIdPagamento());
            stmt.executeUpdate();


            System.out.println("✅ Pagamento atualizado: " + pagamento.getIdPagamento());


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao atualizar pagamento: " + e.getMessage(), e);
        }
    }


    // DELETAR PAGAMENTO
    public void deletar(int id) {
        String sql = "DELETE FROM T_SGP_PAGAMENTO WHERE id_pagamento = ?";


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
           
            if (rowsAffected > 0) {
                System.out.println("✅ Pagamento deletado: " + id);
            } else {
                System.out.println("⚠️ Pagamento não encontrado: " + id);
            }


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao deletar pagamento: " + e.getMessage(), e);
        }
    }


    // MÉTODO AUXILIAR
    private Pagamento mapearPagamento(ResultSet rs) throws SQLException {
        Pagamento pagamento = new Pagamento();
        pagamento.setIdPagamento(rs.getInt("id_pagamento"));
        pagamento.setIdCliente(rs.getInt("id_cliente"));
        pagamento.setIdPedido(rs.getInt("id_pedido"));
        pagamento.setTpPagamento(rs.getString("tp_pagamento"));
        pagamento.setVlTotal(rs.getBigDecimal("vl_total"));
        pagamento.setStPagamento(rs.getString("st_pagamento"));
        pagamento.setDtPagamento(rs.getTimestamp("dt_pagamento").toLocalDateTime());
        return pagamento;
    }
}
