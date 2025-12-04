package com.tecdes.pedido.model.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.tecdes.pedido.config.ConnectionFactory;
import com.tecdes.pedido.model.entity.Cliente;

public class ClienteDAO {

    // INSERIR CLIENTE - AJUSTADO para suas colunas
    public void inserir(Cliente cliente) {
        String sql = "INSERT INTO T_SGP_CLIENTE (nm_cliente, nr_cadastro, ds_email, nr_telefone) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNmCliente());
            stmt.setString(2, cliente.getNrCadastro());
            stmt.setString(3, cliente.getDsEmail());
            stmt.setString(4, cliente.getNrTelefone());
            stmt.executeUpdate();

            // Obtém o ID gerado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cliente.setIdCliente(generatedKeys.getInt(1));
                }
            }

            System.out.println("✅ Cliente inserido com sucesso! ID: " + cliente.getIdCliente());

        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao inserir cliente: " + e.getMessage(), e);
        }
    }

    // BUSCAR TODOS - AJUSTADO
    public List<Cliente> buscarTodos() {
        String sql = "SELECT id_cliente, nm_cliente, nr_cadastro, ds_email, nr_telefone FROM T_SGP_CLIENTE";
        List<Cliente> clientes = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("id_cliente"));
                c.setNmCliente(rs.getString("nm_cliente"));
                c.setNrCadastro(rs.getString("nr_cadastro"));
                c.setDsEmail(rs.getString("ds_email"));
                c.setNrTelefone(rs.getString("nr_telefone"));
                clientes.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao buscar clientes: " + e.getMessage(), e);
        }

        return clientes;
    }

    // BUSCAR POR ID - AJUSTADO
    public Cliente buscarPorId(int id) {
        String sql = "SELECT id_cliente, nm_cliente, nr_cadastro, ds_email, nr_telefone FROM T_SGP_CLIENTE WHERE id_cliente = ?";
        Cliente cliente = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setIdCliente(rs.getInt("id_cliente"));
                    cliente.setNmCliente(rs.getString("nm_cliente"));
                    cliente.setNrCadastro(rs.getString("nr_cadastro"));
                    cliente.setDsEmail(rs.getString("ds_email"));
                    cliente.setNrTelefone(rs.getString("nr_telefone"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao buscar cliente por ID: " + e.getMessage(), e);
        }

        return cliente;
    }

    // ATUALIZAR CLIENTE - AJUSTADO
    public void atualizar(Cliente cliente) {
        String sql = "UPDATE T_SGP_CLIENTE SET nm_cliente = ?, nr_cadastro = ?, ds_email = ?, nr_telefone = ? WHERE id_cliente = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNmCliente());
            stmt.setString(2, cliente.getNrCadastro());
            stmt.setString(3, cliente.getDsEmail());
            stmt.setString(4, cliente.getNrTelefone());
            stmt.setInt(5, cliente.getIdCliente());
            stmt.executeUpdate();

            System.out.println("✅ Cliente atualizado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao atualizar cliente: " + e.getMessage(), e);
        }
    }

    // DELETAR CLIENTE - AJUSTADO (mudei para int)
    public void deletar(int id) {
        // Primeiro verifique se o cliente tem endereços relacionados
        String verificarSql = "SELECT COUNT(*) FROM T_SGP_ENDERECO WHERE id_cliente = ?";
        String deletarSql = "DELETE FROM T_SGP_CLIENTE WHERE id_cliente = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement verificarStmt = conn.prepareStatement(verificarSql);
             PreparedStatement deletarStmt = conn.prepareStatement(deletarSql)) {

            // Verifica se tem dependências
            verificarStmt.setInt(1, id);
            try (ResultSet rs = verificarStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new RuntimeException("❌ Não é possível excluir: cliente tem endereços cadastrados");
                }
            }

            // Deleta o cliente
            deletarStmt.setInt(1, id);
            int linhasAfetadas = deletarStmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("✅ Cliente removido com sucesso!");
            } else {
                System.out.println("⚠️  Cliente não encontrado com ID: " + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao deletar cliente: " + e.getMessage(), e);
        }
    }

    // MÉTODO EXTRA: Buscar por email
    public Cliente buscarPorEmail(String email) {
        String sql = "SELECT id_cliente, nm_cliente, nr_cadastro, ds_email, nr_telefone FROM T_SGP_CLIENTE WHERE ds_email = ?";
        Cliente cliente = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setIdCliente(rs.getInt("id_cliente"));
                    cliente.setNmCliente(rs.getString("nm_cliente"));
                    cliente.setNrCadastro(rs.getString("nr_cadastro"));
                    cliente.setDsEmail(rs.getString("ds_email"));
                    cliente.setNrTelefone(rs.getString("nr_telefone"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao buscar cliente por email: " + e.getMessage(), e);
        }

        return cliente;
    }
}