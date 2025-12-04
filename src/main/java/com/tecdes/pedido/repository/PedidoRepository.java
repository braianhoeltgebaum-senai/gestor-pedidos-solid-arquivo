package com.tecdes.pedido.repository;

import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.model.entity.Cliente;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PedidoRepository {
    
    // Métodos CRUD básicos
    Pedido save(Pedido pedido);
    Optional<Pedido> findById(Long id);
    List<Pedido> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    long count();
    
    // Métodos de busca específicos
    List<Pedido> findByCliente(Cliente cliente);
    List<Pedido> findByStatus(String status);
    List<Pedido> findByStatusAndDataHoraBetween(String status, 
            LocalDateTime dataInicial, LocalDateTime dataFinal);
}