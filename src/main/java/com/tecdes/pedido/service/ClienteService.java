package com.tecdes.pedido.service;

import com.tecdes.pedido.model.entity.Cliente;
import com.tecdes.pedido.repository.ClienteRepository; 
import java.util.List;

public class ClienteService {

    private final ClienteRepository clienteRepository; 

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente cadastrarCliente(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório.");
        }
        return clienteRepository.save(cliente);
    }

    public Cliente buscarClientePorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente ID " + id + " não encontrado."));
    }

    public List<Cliente> buscarTodosClientes() {
        return clienteRepository.findAll();
    }
    
    public Cliente atualizarCliente(Long id, Cliente dadosNovos) {
        Cliente clienteExistente = buscarClientePorId(id); 
        
        clienteExistente.setNome(dadosNovos.getNome());
        clienteExistente.setFone(dadosNovos.getFone());
        
        return clienteRepository.save(clienteExistente);
    }

    public void excluirCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente ID " + id + " não pode ser excluído, pois não existe.");
        }
        clienteRepository.deleteById(id);
    }
}