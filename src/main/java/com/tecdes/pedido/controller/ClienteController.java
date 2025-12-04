package com.tecdes.pedido.controller;

import java.util.List;
import com.tecdes.pedido.model.entity.Cliente;
import com.tecdes.pedido.service.ClienteService;
import com.tecdes.pedido.repository.ClienteRepositoryImpl;

public class ClienteController {
   
    private final ClienteService service;

    public ClienteController() {
        this.service = new ClienteService(new ClienteRepositoryImpl());
    }

    // ✅ MÉTODOS DE AUTENTICAÇÃO CORRIGIDOS:
    
    // Login do cliente - versão corrigida
    public boolean login(String email, String numeroCadastro) {
        System.out.println("🔍 ClienteController.login() chamado");
        System.out.println("📧 Email recebido: " + email);
        System.out.println("🔢 Cadastro recebido: " + numeroCadastro);
        
        try {
            // Primeiro valida os dados
            if (!validarLogin(email, numeroCadastro)) {
                System.out.println("❌ Validação falhou no login");
                return false;
            }
            
            // Usa o serviço para autenticar (passando email e cadastro)
            Cliente cliente = service.autenticarCliente(email, numeroCadastro);
            System.out.println("🔍 Cliente autenticado: " + (cliente != null ? "Sim" : "Não"));
            
            if (cliente != null) {
                System.out.println("🎉 Cliente autenticado com sucesso: " + cliente.getNmCliente());
                return true;
            } else {
                System.out.println("❌ Cliente não autenticado");
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("💥 Erro no login do cliente: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Logout
    public void logout() {
        service.logoutCliente();
    }
    
    // Verifica se há cliente logado
    public boolean isClienteLogado() {
        return service.isClienteAutenticado();
    }
    
    // Retorna cliente logado
    public Cliente getClienteLogado() {
        return service.getClienteAutenticado();
    }
    
    // MÉTODOS EXISTENTES:
    
    public void salvar(String nome, String cadastro, String email, String telefone) {
        service.salvarCliente(nome, cadastro, email, telefone);
    }

    public Cliente buscarPorId(int idCliente) {
        return service.buscarClientePorId(idCliente);
    }

    public void atualizar(int idCliente, String nome, String cadastro, String email, String telefone) {
        service.atualizarCliente(idCliente, nome, cadastro, email, telefone);
    }

    public void excluir(int idCliente) {
        service.excluirCliente(idCliente);
    }

    public List<Cliente> listarTodos() {
        return service.buscarTodosClientes();
    }
    
    // Buscar por email
    public Cliente buscarPorEmail(String email) {
        try {
            return service.buscarClientePorEmail(email);
        } catch (Exception e) {
            System.err.println("Erro ao buscar cliente por email: " + e.getMessage());
            return null;
        }
    }
    
    // Validar dados
    public boolean validarDadosCliente(String nome, String cadastro, String email, String telefone) {
        try {
            Cliente teste = new Cliente();
            teste.setNmCliente(nome);
            teste.setNrCadastro(cadastro);
            teste.setDsEmail(email);
            teste.setNrTelefone(telefone);
            return true;
        } catch (IllegalArgumentException e) {
            System.err.println("❌ Validação falhou: " + e.getMessage());
            return false;
        }
    }
    
    // Validar login - CORRIGIDO
    public boolean validarLogin(String email, String cadastro) {
        System.out.println("📋 Validando login...");
        System.out.println("📧 Email: " + email);
        System.out.println("🔢 Cadastro: " + cadastro);
        
        if (email == null || email.trim().isEmpty()) {
            System.out.println("❌ Email vazio");
            return false;
        }
        if (cadastro == null || cadastro.trim().isEmpty()) {
            System.out.println("❌ Cadastro vazio");
            return false;
        }
        if (cadastro.length() != 3) {
            System.out.println("❌ Cadastro deve ter 3 dígitos, tem: " + cadastro.length());
            return false;
        }
        
        // Valida se cadastro tem apenas números
        if (!cadastro.matches("\\d{3}")) {
            System.out.println("❌ Cadastro deve conter apenas números");
            return false;
        }
        
        System.out.println("✅ Validação OK");
        return true;
    }
    
    // Método para debug - lista todos os clientes
    public void listarClientesParaDebug() {
        try {
            System.out.println("📋 LISTA DE CLIENTES CADASTRADOS:");
            List<Cliente> clientes = listarTodos();
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
        } catch (Exception e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
        }
    }
}