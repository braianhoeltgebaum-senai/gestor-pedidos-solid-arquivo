package com.tecdes.pedido.repository;

import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.model.DAO.ProdutoDAO;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProdutoRepositoryImpl implements ProdutoRepository {
    
    private final ProdutoDAO produtoDAO;
    
    public ProdutoRepositoryImpl() {
        this.produtoDAO = new ProdutoDAO();
    }

    @Override
    public Produto save(Produto produto) {
        if (produto.getIdProduto() == 0) {
            // É um novo produto (inserir)
            produtoDAO.inserir(produto);
        } else {
            // É uma atualização
            produtoDAO.atualizar(produto);
        }
        return produto;
    }

    @Override
    public Optional<Produto> findById(int id) {
        Produto produto = produtoDAO.buscarPorId(id);
        return Optional.ofNullable(produto);
    }

    @Override
    public List<Produto> findAll() {
        return produtoDAO.buscarTodos();
    }

    @Override
    public void delete(int id) {
        produtoDAO.deletar(id);
    }
    
    @Override
    public boolean existsById(int id) {
        return produtoDAO.buscarPorId(id) != null;
    }
    
    @Override
    public List<Produto> findByTipo(char tipo) {
        return produtoDAO.buscarPorTipo(tipo);
    }
    
    @Override
    public List<Produto> findByNomeContaining(String nome) {
        return produtoDAO.buscarPorNome(nome);
    }
    
    @Override
    public int contarTotal() {
        return produtoDAO.contarTotal();
    }
    
    @Override
    public List<Produto> buscarPorFaixaPreco(BigDecimal min, BigDecimal max) {
        return produtoDAO.buscarPorFaixaPreco(min, max);
    }
}