package com.tecdes.pedido.repository;

import com.tecdes.pedido.model.entity.Pedido;
import java.util.List;
import java.util.Optional;

public interface PedidoRepository {
    
    // Métodos CRUD básicos - CORRIGIDOS para int
    Pedido save(Pedido pedido);
    Optional<Pedido> findById(int id);        // MUDOU: Long → int
    List<Pedido> findAll();
    void delete(int id);                     // MUDOU: deleteById → delete, Long → int
    boolean existsById(int id);              // MUDOU: Long → int
    
    // Métodos específicos do seu DAO
    List<Pedido> findByCliente(int idCliente);        // MUDOU: Cliente → int
    List<Pedido> findByStatus(char status);           // MUDOU: String → char
    
    // Métodos extras úteis
    int count();                             // MUDOU: long → int
    int proximoNumeroPedido();
}