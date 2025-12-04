package com.tecdes.pedido.model.DAO;


import com.tecdes.pedido.config.ConnectionFactory;
import com.tecdes.pedido.model.entity.Produto;
import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class ProdutoDAO {


    // 🔹 INSERIR PRODUTO
    public void inserir(Produto produto) {
        String sql = "INSERT INTO T_SGP_PRODUTO (nm_produto, tp_produto, ds_produto, vl_produto) VALUES (?, ?, ?, ?)";
       
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


            stmt.setString(1, produto.getNmProduto());
            stmt.setString(2, String.valueOf(produto.getTpProduto())); // char para String
            stmt.setString(3, produto.getDsProduto());
            stmt.setBigDecimal(4, produto.getVlProduto());
            stmt.executeUpdate();


            // Pega o ID gerado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    produto.setIdProduto(rs.getInt(1)); // int, não Long
                }
            }


            System.out.println("✅ Produto inserido com ID: " + produto.getIdProduto());


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao inserir produto: " + e.getMessage(), e);
        }
    }


    // 🔹 BUSCAR TODOS OS PRODUTOS
    public List<Produto> buscarTodos() {
        String sql = "SELECT id_produto, nm_produto, tp_produto, ds_produto, vl_produto FROM T_SGP_PRODUTO ORDER BY nm_produto";
        List<Produto> produtos = new ArrayList<>();


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {


            while (rs.next()) {
                produtos.add(mapResultSetToProduto(rs));
            }


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao buscar produtos: " + e.getMessage(), e);
        }


        return produtos;
    }


    // 🔹 BUSCAR PRODUTO POR ID
    public Produto buscarPorId(int id) { // int, não Long
        String sql = "SELECT id_produto, nm_produto, tp_produto, ds_produto, vl_produto FROM T_SGP_PRODUTO WHERE id_produto = ?";
       
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setInt(1, id);
           
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProduto(rs);
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao buscar produto por ID: " + e.getMessage(), e);
        }


        return null;
    }


    // 🔹 BUSCAR PRODUTOS POR TIPO
    public List<Produto> buscarPorTipo(char tipo) {
        String sql = "SELECT id_produto, nm_produto, tp_produto, ds_produto, vl_produto FROM T_SGP_PRODUTO WHERE tp_produto = ? ORDER BY nm_produto";
        List<Produto> produtos = new ArrayList<>();


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setString(1, String.valueOf(tipo));
           
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    produtos.add(mapResultSetToProduto(rs));
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao buscar produtos por tipo: " + e.getMessage(), e);
        }


        return produtos;
    }


    // 🔹 BUSCAR PRODUTOS POR NOME (LIKE)
    public List<Produto> buscarPorNome(String nome) {
        String sql = "SELECT id_produto, nm_produto, tp_produto, ds_produto, vl_produto FROM T_SGP_PRODUTO WHERE nm_produto LIKE ? ORDER BY nm_produto";
        List<Produto> produtos = new ArrayList<>();


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setString(1, "%" + nome + "%");
           
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    produtos.add(mapResultSetToProduto(rs));
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao buscar produtos por nome: " + e.getMessage(), e);
        }


        return produtos;
    }


    // 🔹 ATUALIZAR PRODUTO
    public void atualizar(Produto produto) {
        String sql = "UPDATE T_SGP_PRODUTO SET nm_produto = ?, tp_produto = ?, ds_produto = ?, vl_produto = ? WHERE id_produto = ?";


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setString(1, produto.getNmProduto());
            stmt.setString(2, String.valueOf(produto.getTpProduto()));
            stmt.setString(3, produto.getDsProduto());
            stmt.setBigDecimal(4, produto.getVlProduto());
            stmt.setInt(5, produto.getIdProduto()); // int, não Long
            stmt.executeUpdate();


            System.out.println("✅ Produto atualizado: " + produto.getIdProduto());


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao atualizar produto: " + e.getMessage(), e);
        }
    }


    // 🔹 DELETAR PRODUTO
    public void deletar(int id) { // int, não Long
        // Primeiro verifica se o produto está em algum pedido
        String verificarSql = "SELECT COUNT(*) FROM T_SGP_ITEM_PEDIDO WHERE id_produto = ?";
        String deletarSql = "DELETE FROM T_SGP_PRODUTO WHERE id_produto = ?";


        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false); // Inicia transação
           
            try (PreparedStatement verificarStmt = conn.prepareStatement(verificarSql);
                 PreparedStatement deletarStmt = conn.prepareStatement(deletarSql)) {
               
                // Verifica se produto está em uso
                verificarStmt.setInt(1, id);
                try (ResultSet rs = verificarStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        throw new SQLException("Não é possível excluir: produto está em pedidos");
                    }
                }
               
                // Deleta o produto
                deletarStmt.setInt(1, id);
                int rowsAffected = deletarStmt.executeUpdate();
               
                conn.commit(); // Confirma transação
               
                if (rowsAffected > 0) {
                    System.out.println("✅ Produto deletado: " + id);
                } else {
                    System.out.println("⚠️ Produto não encontrado: " + id);
                }
               
            } catch (SQLException e) {
                conn.rollback(); // Desfaz em caso de erro
                throw e;
            }
           
        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao deletar produto: " + e.getMessage(), e);
        }
    }


    // 🔹 CONTAR TOTAL DE PRODUTOS
    public int contarTotal() {
        String sql = "SELECT COUNT(*) FROM T_SGP_PRODUTO";
       
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {


            if (rs.next()) {
                return rs.getInt(1);
            }
           
        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao contar produtos: " + e.getMessage(), e);
        }
       
        return 0;
    }


    // 🔹 BUSCAR PRODUTOS POR FAIXA DE PREÇO
    public List<Produto> buscarPorFaixaPreco(BigDecimal precoMin, BigDecimal precoMax) {
        String sql = "SELECT id_produto, nm_produto, tp_produto, ds_produto, vl_produto FROM T_SGP_PRODUTO WHERE vl_produto BETWEEN ? AND ? ORDER BY vl_produto";
        List<Produto> produtos = new ArrayList<>();


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setBigDecimal(1, precoMin);
            stmt.setBigDecimal(2, precoMax);
           
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    produtos.add(mapResultSetToProduto(rs));
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao buscar produtos por faixa de preço: " + e.getMessage(), e);
        }


        return produtos;
    }


    // MÉTODO AUXILIAR: Converter ResultSet para Produto
    private Produto mapResultSetToProduto(ResultSet rs) throws SQLException {
        Produto produto = new Produto();
        produto.setIdProduto(rs.getInt("id_produto"));
        produto.setNmProduto(rs.getString("nm_produto"));
        produto.setTpProduto(rs.getString("tp_produto").charAt(0)); // String para char
        produto.setDsProduto(rs.getString("ds_produto"));
        produto.setVlProduto(rs.getBigDecimal("vl_produto"));
        return produto;
    }
}

