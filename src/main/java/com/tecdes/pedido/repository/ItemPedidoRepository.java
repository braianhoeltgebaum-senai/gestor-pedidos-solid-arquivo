package com.tecdes.pedido.repository;

import java.util.List;
import com.tecdes.pedido.model.entity.ItemPedido;

public interface ItemPedidoRepository {
    void save(ItemPedido item);
    ItemPedido findById(int id);        // MUDOU: Long → int
    List<ItemPedido> findAll();
    void update(ItemPedido item);
    void delete(int id);                // MUDOU: Long → int
    List<ItemPedido> findByPedido(int idPedido); // NOVO: necessário
    boolean existsById(int id);         // NOVO: necessário
}