package com.tecdes.pedido.model.DAO;

import com.tecdes.pedido.config.ConnectionFactory;
import com.tecdes.pedido.model.entity.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    // INSERIR
    public void inserir(Pedido pedido) {
        // Incluindo a coluna 'id_cliente' para evitar NULL em chaves estrangeiras
        String sql = "INSERT INTO pedido (data_hora, status, valor_total, tipo_pagamento, id_cliente) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             // Permite obter o ID gerado pelo banco
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setTimestamp(1, Timestamp.valueOf(pedido.getDataHora()));
            stmt.setString(2, pedido.getStatus());
            stmt.setDouble(3, pedido.getValorTotal());
            stmt.setString(4, pedido.getTipoPagamento());
            
            // Supondo que Cliente está associado (Necessário para FK)
            if (pedido.getCliente() != null) {
                stmt.setLong(5, pedido.getCliente().getIdCliente()); 
            } else {
                stmt.setNull(5, Types.BIGINT);
            }
            
            stmt.executeUpdate();

            // Pega o ID gerado pelo banco e seta na entidade
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pedido.setId(rs.getLong(1)); // <-- USANDO Long
                }
            }

            System.out.println("Pedido inserido com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir pedido.", e);
        }
    }

    // BUSCAR TODOS
    public List<Pedido> buscarTodos() {
        // Adicionando 'id_cliente' na seleção
        String sql = "SELECT id_pedido, data_hora, status, valor_total, tipo_pagamento, id_cliente FROM pedido";
        List<Pedido> pedidos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pedido p = new Pedido();
                // ATUALIZADO: Usando getLong para manter consistência com a entidade
                p.setId(rs.getLong("id_pedido")); 
                p.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                p.setStatus(rs.getString("status"));
                p.setValorTotal(rs.getDouble("valor_total"));
                p.setTipoPagamento(rs.getString("tipo_pagamento"));
                
                // Nota: A lógica para carregar o objeto Cliente relacionado não está aqui,
                // mas seria crucial em um sistema real.

                pedidos.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pedidos.", e);
        }

        return pedidos;
    }

    // BUSCAR POR ID
    public Pedido buscarPorId(Long id) { // ATUALIZADO: Recebe Long
        String sql = "SELECT id_pedido, data_hora, status, valor_total, tipo_pagamento, id_cliente FROM pedido WHERE id_pedido = ?";
        Pedido pedido = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id); // ATUALIZADO: Usando setLong

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pedido = new Pedido();
                    pedido.setId(rs.getLong("id_pedido")); // ATUALIZADO: Usando getLong
                    pedido.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                    pedido.setStatus(rs.getString("status"));
                    pedido.setValorTotal(rs.getDouble("valor_total"));
                    pedido.setTipoPagamento(rs.getString("tipo_pagamento"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pedido por ID.", e);
        }

        return pedido;
    }

    // ATUALIZAR
    public void atualizar(Pedido pedido) {
        String sql = "UPDATE pedido SET data_hora=?, status=?, valor_total=?, tipo_pagamento=? WHERE id_pedido=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(pedido.getDataHora()));
            stmt.setString(2, pedido.getStatus());
            stmt.setDouble(3, pedido.getValorTotal());
            stmt.setString(4, pedido.getTipoPagamento());
            
            // ATUALIZADO: Usando getLong (getId)
            stmt.setLong(5, pedido.getId()); 

            stmt.executeUpdate();

            System.out.println("Pedido atualizado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar pedido.", e);
        }
    }

    // DELETAR
    public void deletar(Long id) { // ATUALIZADO: Recebe Long
        String sql = "DELETE FROM pedido WHERE id_pedido=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id); // ATUALIZADO: Usando setLong
            stmt.executeUpdate();

            System.out.println("Pedido deletado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar pedido.", e);
        }
    }
}