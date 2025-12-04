package com.tecdes.pedido.service;

import com.tecdes.pedido.model.entity.Cliente;
import com.tecdes.pedido.repository.ClienteRepository;
import java.util.List;

public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // CORRIGIDO: Usa campos corretos
    public Cliente cadastrarCliente(Cliente cliente) {
        if (cliente.getNmCliente() == null || cliente.getNmCliente().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório.");
        }
        if (cliente.getNrCadastro() == null || cliente.getNrCadastro().length() != 3) {
            throw new IllegalArgumentException("Número de cadastro deve ter 3 dígitos.");
        }
        
        // Verifica se email já existe
        Cliente existente = clienteRepository.findByEmail(cliente.getDsEmail());
        if (existente != null) {
            throw new IllegalArgumentException("Email já cadastrado: " + cliente.getDsEmail());
        }
        
        clienteRepository.save(cliente);
        return cliente;
    }
    
    // CORRIGIDO: 4 parâmetros alinhados com banco
    public Cliente salvarCliente(String nome, String cadastro, String email, String telefone) {
        Cliente cliente = new Cliente(nome, cadastro, email, telefone);
        return cadastrarCliente(cliente);
    }

    // CORRIGIDO: Mudou de Long para int
    public Cliente buscarClientePorId(int id) {
        return clienteRepository.findById(id);
    }
    
    // Alias para compatibilidade
    public Cliente buscarPorId(int id) {
        return buscarClientePorId(id);
    }

    public List<Cliente> buscarTodosClientes() {
        return clienteRepository.findAll();
    }
    
    public List<Cliente> buscarTodos() {
        return buscarTodosClientes();
    }

    // CORRIGIDO: Mudou de Long para int
    public Cliente atualizarCliente(int id, Cliente dadosNovos) {
        // Verifica se existe
        Cliente clienteExistente = buscarClientePorId(id);
        
        // Atualiza campos (mantém o ID)
        clienteExistente.setNmCliente(dadosNovos.getNmCliente());
        clienteExistente.setNrCadastro(dadosNovos.getNrCadastro());
        clienteExistente.setDsEmail(dadosNovos.getDsEmail());
        clienteExistente.setNrTelefone(dadosNovos.getNrTelefone());
        
        // Verifica se email mudou e se já pertence a outro
        if (!clienteExistente.getDsEmail().equals(dadosNovos.getDsEmail())) {
            Cliente clienteComEmail = clienteRepository.findByEmail(dadosNovos.getDsEmail());
            if (clienteComEmail != null && clienteComEmail.getIdCliente() != id) {
                throw new IllegalArgumentException("Email já cadastrado para outro cliente");
            }
        }
        
        clienteRepository.update(clienteExistente);
        return clienteExistente;
    }
    
    // CORRIGIDO: Mudou de Long para int e 5 parâmetros
    public Cliente atualizarCliente(int id, String nome, String cadastro, String email, String telefone) {
        Cliente dadosNovos = new Cliente();
        dadosNovos.setNmCliente(nome);
        dadosNovos.setNrCadastro(cadastro);
        dadosNovos.setDsEmail(email);
        dadosNovos.setNrTelefone(telefone);
        return atualizarCliente(id, dadosNovos);
    }

    // CORRIGIDO: Mudou de Long para int
    public void excluirCliente(int id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente ID " + id + " não pode ser excluído, pois não existe.");
        }
        clienteRepository.delete(id);
    }
    
    // Buscar por email
    public Cliente buscarClientePorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }
}