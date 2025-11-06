package com.tecdes.pedido.repository;

import java.util.List;

import com.tecdes.pedido.model.entity.Produto;

public interface ProdutoRepository {

    Produto findById(int id);

    List<Produto> findAll();

    void update(Produto produto);

    void delete(int id);

    void save(Produto produto);

}
