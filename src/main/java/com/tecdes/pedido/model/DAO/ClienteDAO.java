package com.tecdes.pedido.model.DAO;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.tecdes.pedido.config.ConnectionFactory;
import com.tecdes.pedido.model.entity.Cliente;


public class ClienteDAO {


    // Método para obter próximo ID disponível
    private int obterProximoId() {
        String sql = "SELECT COALESCE(MAX(id_cliente), 0) + 1 AS proximo_id FROM T_SGP_CLIENTE";
       
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
           
            if (rs.next()) {
                return rs.getInt("proximo_id");
            }
            return 1; // Primeiro ID
           
        } catch (SQLException e) {
            System.err.println("❌ Erro ao obter próximo ID: " + e.getMessage());
            return 1;
        }
    }


    // INSERIR CLIENTE - COM ID MANUAL
    public void inserir(Cliente cliente) {
        // Primeiro obtenha o próximo ID
        int proximoId = obterProximoId();
        cliente.setIdCliente(proximoId);
       
        String sql = "INSERT INTO T_SGP_CLIENTE (id_cliente, nm_cliente, nr_cadastro, ds_email, nr_telefone) VALUES (?, ?, ?, ?, ?)";
       
        System.out.println("💾 Inserindo cliente com ID: " + proximoId);


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setInt(1, cliente.getIdCliente());
            stmt.setString(2, cliente.getNmCliente());
            stmt.setString(3, cliente.getNrCadastro());
            stmt.setString(4, cliente.getDsEmail());
            stmt.setString(5, cliente.getNrTelefone());
           
            int rowsAffected = stmt.executeUpdate();
            System.out.println("✅ Linhas afetadas: " + rowsAffected);


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao inserir cliente: " + e.getMessage(), e);
        }
    }


    // BUSCAR TODOS
    public List<Cliente> buscarTodos() {
        String sql = "SELECT id_cliente, nm_cliente, nr_cadastro, ds_email, nr_telefone FROM T_SGP_CLIENTE ORDER BY id_cliente";
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


        System.out.println("🔍 Total de clientes encontrados: " + clientes.size());
        return clientes;
    }


    // BUSCAR POR ID
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
                    System.out.println("✅ Cliente encontrado: " + cliente.getNmCliente());
                } else {
                    System.out.println("❌ Cliente não encontrado com ID: " + id);
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao buscar cliente por ID: " + e.getMessage(), e);
        }


        return cliente;
    }


    // ATUALIZAR CLIENTE
    public void atualizar(Cliente cliente) {
        String sql = "UPDATE T_SGP_CLIENTE SET nm_cliente = ?, nr_cadastro = ?, ds_email = ?, nr_telefone = ? WHERE id_cliente = ?";


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setString(1, cliente.getNmCliente());
            stmt.setString(2, cliente.getNrCadastro());
            stmt.setString(3, cliente.getDsEmail());
            stmt.setString(4, cliente.getNrTelefone());
            stmt.setInt(5, cliente.getIdCliente());
           
            int rowsAffected = stmt.executeUpdate();
            System.out.println("✅ Cliente atualizado. Linhas afetadas: " + rowsAffected);


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao atualizar cliente: " + e.getMessage(), e);
        }
    }


    // DELETAR CLIENTE
    public void deletar(int id) {
        // Primeiro verifique se o cliente existe
        Cliente cliente = buscarPorId(id);
        if (cliente == null) {
            System.out.println("⚠️ Cliente não encontrado com ID: " + id);
            return;
        }
       
        // Verifique se tem dependências
        String verificarSql = "SELECT COUNT(*) FROM T_SGP_ENDERECO WHERE id_cliente = ?";
        String deletarSql = "DELETE FROM T_SGP_CLIENTE WHERE id_cliente = ?";


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement verificarStmt = conn.prepareStatement(verificarSql);
             PreparedStatement deletarStmt = conn.prepareStatement(deletarSql)) {


            // Verifica se tem endereços
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
                System.out.println("✅ Cliente '" + cliente.getNmCliente() + "' removido com sucesso!");
            }


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao deletar cliente: " + e.getMessage(), e);
        }
    }


    // BUSCAR POR EMAIL
    public Cliente buscarPorEmail(String email) {
        String sql = "SELECT id_cliente, nm_cliente, nr_cadastro, ds_email, nr_telefone FROM T_SGP_CLIENTE WHERE ds_email = ?";
        Cliente cliente = null;


        System.out.println("🔍 Buscando cliente por email: " + email);


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
                    System.out.println("✅ Cliente encontrado: " + cliente.getNmCliente());
                } else {
                    System.out.println("❌ Nenhum cliente encontrado com email: " + email);
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao buscar cliente por email: " + e.getMessage(), e);
        }


        return cliente;
    }
   
    // NOVO MÉTODO: Verificar se cadastro já existe
    public boolean cadastroExiste(String cadastro) {
        String sql = "SELECT COUNT(*) FROM T_SGP_CLIENTE WHERE nr_cadastro = ?";
       
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
           
            stmt.setString(1, cadastro);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
           
        } catch (SQLException e) {
            System.err.println("❌ Erro ao verificar cadastro: " + e.getMessage());
        }
       
        return false;
    }
   
    // NOVO MÉTODO: Verificar se email já existe
    public boolean emailExiste(String email) {
        String sql = "SELECT COUNT(*) FROM T_SGP_CLIENTE WHERE ds_email = ?";
       
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
           
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
           
        } catch (SQLException e) {
            System.err.println("❌ Erro ao verificar email: " + e.getMessage());
        }
       
        return false;
    }
}
