package com.tecdes.pedido.model.DAO;

import com.tecdes.pedido.config.ConnectionFactory;
import com.tecdes.pedido.model.entity.Funcionario;
import java.sql.*;
//import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {
    
    // INSERIR
    public void inserir(Funcionario funcionario) {
        String sql = "INSERT INTO T_SGP_FUNCIONARIO (nm_funcionario, ds_cargo, nr_telefone, nr_cpf, vl_salario) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, funcionario.getNmFuncionario());
            stmt.setString(2, funcionario.getDsCargo());
            stmt.setString(3, funcionario.getNrTelefone());
            stmt.setString(4, funcionario.getNrCpf());
            stmt.setBigDecimal(5, funcionario.getVlSalario());
            stmt.executeUpdate();
            
            // Pega ID gerado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    funcionario.setIdFuncionario(rs.getInt(1));
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir funcionário: " + e.getMessage(), e);
        }
    }
    
    // BUSCAR POR CPF (para login)
    public Funcionario buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM T_SGP_FUNCIONARIO WHERE nr_cpf = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cpf);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearFuncionario(rs);
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionário por CPF: " + e.getMessage(), e);
        }
        
        return null;
    }
    
    // BUSCAR TODOS
    public List<Funcionario> buscarTodos() {
        String sql = "SELECT * FROM T_SGP_FUNCIONARIO";
        List<Funcionario> funcionarios = new ArrayList<>();
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                funcionarios.add(mapearFuncionario(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionários: " + e.getMessage(), e);
        }
        
        return funcionarios;
    }
    
    // MÉTODO AUXILIAR
    private Funcionario mapearFuncionario(ResultSet rs) throws SQLException {
        Funcionario f = new Funcionario();
        f.setIdFuncionario(rs.getInt("id_funcionario"));
        f.setNmFuncionario(rs.getString("nm_funcionario"));
        f.setDsCargo(rs.getString("ds_cargo"));
        f.setNrTelefone(rs.getString("nr_telefone"));
        f.setNrCpf(rs.getString("nr_cpf"));
        f.setVlSalario(rs.getBigDecimal("vl_salario"));
        f.setDtAdmissao(rs.getTimestamp("dt_admissao").toLocalDateTime());
        
        Timestamp demissao = rs.getTimestamp("dt_demissao");
        if (demissao != null) {
            f.setDtDemissao(demissao.toLocalDateTime());
        }
        
        return f;
    }
}