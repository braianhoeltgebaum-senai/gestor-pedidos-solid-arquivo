package com.tecdes.pedido.repository;

import java.util.List;
import com.tecdes.pedido.model.entity.Cliente;

public interface ClienteRepository {
    // Métodos que correspondem ao seu DAO
    void save(Cliente cliente);  // ou 'inserir'
    List<Cliente> findAll();      // ou 'buscarTodos'
    Cliente findById(int id);     // MUDOU: int em vez de Long
    void update(Cliente cliente); // ou 'atualizar'
    void delete(int id);          // MUDOU: int em vez de Long
    boolean existsById(int id);   // novo método necessário
    Cliente findByEmail(String email); // novo método
}