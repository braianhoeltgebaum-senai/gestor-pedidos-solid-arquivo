package com.tecdes.pedido.repository;

import com.tecdes.pedido.model.entity.Produto;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProdutoRepository {
    Produto save(Produto produto);
    Optional<Produto> findById(int id);
    List<Produto> findAll();
    void delete(int id);
    boolean existsById(int id);
    List<Produto> findByTipo(char tipo);           // MUDOU: String → char
    List<Produto> findByNomeContaining(String nome); // MUDOU: findByNomeContaining
    int contarTotal();                              // NOVO
    List<Produto> buscarPorFaixaPreco(BigDecimal min, BigDecimal max); // NOVO
}