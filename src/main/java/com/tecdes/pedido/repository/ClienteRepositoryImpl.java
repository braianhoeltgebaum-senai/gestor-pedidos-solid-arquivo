package com.tecdes.pedido.repository;

import java.util.List;
//import java.util.Optional;
import com.tecdes.pedido.model.DAO.ClienteDAO;
import com.tecdes.pedido.model.entity.Cliente;

public class ClienteRepositoryImpl implements ClienteRepository {
    
    private final ClienteDAO clienteDAO;

    public ClienteRepositoryImpl() {
        this.clienteDAO = new ClienteDAO();
    }

    @Override
    public void save(Cliente cliente) {
        clienteDAO.inserir(cliente);
    }

    @Override
    public List<Cliente> findAll() {
        return clienteDAO.buscarTodos();
    }

    @Override
    public Cliente findById(int id) {
        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente == null) {
            throw new RuntimeException("Cliente não encontrado com ID: " + id);
        }
        return cliente;
    }

    @Override
    public void update(Cliente cliente) {
        clienteDAO.atualizar(cliente);
    }

    @Override
    public void delete(int id) {
        clienteDAO.deletar(id);
    }

    @Override
    public boolean existsById(int id) {
        return clienteDAO.buscarPorId(id) != null;
    }

    @Override
    public Cliente findByEmail(String email) {
        return clienteDAO.buscarPorEmail(email);
    }
}