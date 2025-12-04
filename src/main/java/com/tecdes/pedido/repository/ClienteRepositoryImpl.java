package com.tecdes.pedido.repository;

import com.tecdes.pedido.model.entity.Cliente;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger; // Mudado para Integer

public class ClienteRepositoryImpl implements ClienteRepository {
    
    private final Map<Integer, Cliente> database = new HashMap<>(); // Map<Integer, Cliente>
    private final AtomicInteger idGenerator = new AtomicInteger(0); // AtomicInteger

    @Override
    public Cliente save(Cliente cliente) {
        if (cliente.getIdCliente() == 0) {
            cliente.setIdCliente(idGenerator.incrementAndGet());
        }
       
        database.put(cliente.getIdCliente(), cliente);
        System.out.println("[REPO] Cliente salvo em memória: ID " + cliente.getIdCliente());
        return cliente;
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        // Converte Long para Integer
        return Optional.ofNullable(database.get(id.intValue()));
    }

    @Override
    public List<Cliente> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public void deleteById(Long id) {
        database.remove(id.intValue());
        System.out.println("[REPO] Cliente deletado da memória: ID " + id);
    }

    @Override
    public boolean existsById(Long id) {
        return database.containsKey(id.intValue());
    }

    @Override
    public Optional<Cliente> findByNome(String nome) {
        return database.values().stream()
                .filter(c -> c.getNmCliente().equalsIgnoreCase(nome))
                .findFirst();
    }
    
    @Override
    public Optional<Cliente> findByEmail(String email) {
        return database.values().stream()
                .filter(c -> c.getDsEmail().equalsIgnoreCase(email))
                .findFirst();
    }
    
    @Override
    public Optional<Cliente> findByTelefone(String telefone) {
        return database.values().stream()
                .filter(c -> c.getNrTelefone().equals(telefone))
                .findFirst();
    }
}