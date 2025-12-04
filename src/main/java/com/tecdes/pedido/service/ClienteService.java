package com.tecdes.pedido.service;

import com.tecdes.pedido.model.entity.Cliente;
import com.tecdes.pedido.repository.ClienteRepository;
import java.util.List;

public class ClienteService {

    private final ClienteRepository clienteRepository;
    private Cliente clienteAutenticado; // ✅ Armazena cliente logado

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
        this.clienteAutenticado = null; // Inicialmente ninguém logado
    }

    // ✅ MÉTODOS DE AUTENTICAÇÃO ADICIONADOS:
    
    // Autentica cliente usando email e número de cadastro - CORRIGIDO
    public Cliente autenticarCliente(String email, String numeroCadastro) {
        System.out.println("🔐 ClienteService.autenticarCliente()");
        System.out.println("📧 Email recebido: " + email);
        System.out.println("🔢 Cadastro recebido: " + numeroCadastro);
        
        if (email == null || email.trim().isEmpty()) {
            System.out.println("❌ Email é obrigatório");
            throw new IllegalArgumentException("Email é obrigatório");
        }
        if (numeroCadastro == null || numeroCadastro.trim().isEmpty()) {
            System.out.println("❌ Número de cadastro é obrigatório");
            throw new IllegalArgumentException("Número de cadastro é obrigatório");
        }
        
        // Busca cliente pelo email - CORREÇÃO: usar buscarPorEmail
        Cliente cliente = clienteRepository.buscarPorEmail(email);
        
        System.out.println("🔍 Cliente encontrado: " + (cliente != null ? "Sim" : "Não"));
        
        if (cliente == null) {
            System.out.println("❌ Cliente não encontrado com este email: " + email);
            throw new IllegalArgumentException("Cliente não encontrado com este email");
        }
        
        System.out.println("📋 Dados do cliente:");
        System.out.println("  Nome: " + cliente.getNmCliente());
        System.out.println("  Email: " + cliente.getDsEmail());
        System.out.println("  Cadastro no banco: " + cliente.getNrCadastro());
        System.out.println("  Cadastro recebido: " + numeroCadastro);
        
        // Verifica se o número de cadastro está correto
        if (!cliente.autenticar(email, numeroCadastro)) {
            System.out.println("❌ Número de cadastro incorreto");
            throw new IllegalArgumentException("Número de cadastro incorreto");
        }
        
        // Se tudo OK, autentica o cliente
        this.clienteAutenticado = cliente;
        System.out.println("✅ Cliente autenticado com sucesso: " + cliente.getNmCliente());
        return cliente;
    }
    
    // Desloga o cliente
    public void logoutCliente() {
        if (clienteAutenticado != null) {
            System.out.println("🚪 Cliente deslogado: " + clienteAutenticado.getNmCliente());
            this.clienteAutenticado = null;
        }
    }
    
    // Verifica se há cliente autenticado
    public boolean isClienteAutenticado() {
        return clienteAutenticado != null;
    }
    
    // Retorna o cliente autenticado atual
    public Cliente getClienteAutenticado() {
        if (!isClienteAutenticado()) {
            throw new IllegalStateException("Nenhum cliente autenticado no momento");
        }
        return clienteAutenticado;
    }
    
    // MÉTODOS EXISTENTES (com logs adicionados):

    // CORRIGIDO: Usa campos corretos
    public Cliente cadastrarCliente(Cliente cliente) {
        System.out.println("📝 Cadastrando novo cliente: " + cliente.getNmCliente());
        
        if (cliente.getNmCliente() == null || cliente.getNmCliente().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório.");
        }
        if (cliente.getNrCadastro() == null || cliente.getNrCadastro().length() != 3) {
            throw new IllegalArgumentException("Número de cadastro deve ter 3 dígitos.");
        }
        
        // Verifica se email já existe
        Cliente existente = clienteRepository.buscarPorEmail(cliente.getDsEmail()); // CORREÇÃO: buscarPorEmail
        if (existente != null) {
            throw new IllegalArgumentException("Email já cadastrado: " + cliente.getDsEmail());
        }
        
        clienteRepository.save(cliente);
        System.out.println("✅ Cliente cadastrado com sucesso: " + cliente.getNmCliente());
        return cliente;
    }
    
    // CORRIGIDO: 4 parâmetros alinhados com banco
    public Cliente salvarCliente(String nome, String cadastro, String email, String telefone) {
        Cliente cliente = new Cliente(nome, cadastro, email, telefone);
        return cadastrarCliente(cliente);
    }

    // CORRIGIDO: Mudou de Long para int
    public Cliente buscarClientePorId(int id) {
        return clienteRepository.buscarPorId(id);
    }
    
    // Alias para compatibilidade
    public Cliente buscarPorId(int id) {
        return buscarClientePorId(id);
    }

    public List<Cliente> buscarTodosClientes() {
        return clienteRepository.buscarTodos();
    }
    
    public List<Cliente> buscarTodos() {
        return buscarTodosClientes();
    }

    // CORRIGIDO: Mudou de Long para int
    public Cliente atualizarCliente(int id, Cliente dadosNovos) {
        System.out.println("✏️ Atualizando cliente ID: " + id);
        
        // Verifica se existe
        Cliente clienteExistente = buscarClientePorId(id);
        
        // Atualiza campos (mantém o ID)
        clienteExistente.setNmCliente(dadosNovos.getNmCliente());
        clienteExistente.setNrCadastro(dadosNovos.getNrCadastro());
        clienteExistente.setDsEmail(dadosNovos.getDsEmail());
        clienteExistente.setNrTelefone(dadosNovos.getNrTelefone());
        
        // Verifica se email mudou e se já pertence a outro
        if (!clienteExistente.getDsEmail().equals(dadosNovos.getDsEmail())) {
            Cliente clienteComEmail = clienteRepository.buscarPorEmail(dadosNovos.getDsEmail()); // CORREÇÃO: buscarPorEmail
            if (clienteComEmail != null && clienteComEmail.getIdCliente() != id) {
                throw new IllegalArgumentException("Email já cadastrado para outro cliente");
            }
        }
        
        clienteRepository.atualizar(clienteExistente);
        System.out.println("✅ Cliente atualizado: " + clienteExistente.getNmCliente());
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
        System.out.println("🗑️ Excluindo cliente ID: " + id);
        
        // Não permite excluir cliente autenticado
        if (isClienteAutenticado() && clienteAutenticado.getIdCliente() == id) {
            throw new IllegalArgumentException("Não é possível excluir o próprio cliente enquanto autenticado");
        }
        
        if (!clienteRepository.existePorId(id)) {
            throw new RuntimeException("Cliente ID " + id + " não pode ser excluído, pois não existe.");
        }
        
        clienteRepository.excluir(id);
        System.out.println("✅ Cliente excluído ID: " + id);
    }
    
    // Buscar por email
    public Cliente buscarClientePorEmail(String email) {
        System.out.println("🔍 Buscando cliente por email: " + email);
        Cliente cliente = clienteRepository.buscarPorEmail(email); // CORREÇÃO: buscarPorEmail
        System.out.println("🔍 Cliente encontrado: " + (cliente != null ? cliente.getNmCliente() : "Não encontrado"));
        return cliente;
    }
    
    // Método para debug
    public void listarTodosParaDebug() {
        System.out.println("📋 LISTA DE CLIENTES:");
        List<Cliente> clientes = buscarTodosClientes();
        if (clientes.isEmpty()) {
            System.out.println("   Nenhum cliente cadastrado");
        } else {
            for (Cliente c : clientes) {
                System.out.println("   ID: " + c.getIdCliente() + 
                                 ", Nome: " + c.getNmCliente() + 
                                 ", Email: " + c.getDsEmail() + 
                                 ", Cadastro: " + c.getNrCadastro() +
                                 ", Telefone: " + c.getNrTelefone());
            }
        }
    }
}