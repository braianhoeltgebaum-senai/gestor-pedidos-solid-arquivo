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

    // ✅ MÉTODOS DE AUTENTICAÇÃO ADICIONADOS:
    
    // Login do cliente
    public Cliente login(String email, String numeroCadastro) {
        return service.autenticarCliente(email, numeroCadastro);
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
    
    // MÉTODOS EXISTENTES (mantenha todos):

    // ✅ CORRETO: 4 parâmetros como no banco
    public void salvar(String nome, String cadastro, String email, String telefone) {
        service.salvarCliente(nome, cadastro, email, telefone);
    }

    // ✅ CORRETO: int
    public Cliente buscarPorId(int idCliente) {
        return service.buscarClientePorId(idCliente);
    }

    // ✅ CORRETO: int + 4 parâmetros
    public void atualizar(int idCliente, String nome, String cadastro, String email, String telefone) {
        service.atualizarCliente(idCliente, nome, cadastro, email, telefone);
    }

    // ✅ CORRETO: int
    public void excluir(int idCliente) {
        service.excluirCliente(idCliente);
    }

    public List<Cliente> listarTodos() {
        return service.buscarTodosClientes();
    }
    
    // ✅ Buscar por email
    public Cliente buscarPorEmail(String email) {
        return service.buscarClientePorEmail(email);
    }
    
    // ✅ Validar dados
    public boolean validarDadosCliente(String nome, String cadastro, String email, String telefone) {
        try {
            Cliente teste = new Cliente();
            teste.setNmCliente(nome);
            teste.setNrCadastro(cadastro);
            teste.setDsEmail(email);
            teste.setNrTelefone(telefone);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    
    // ✅ Validar login
    public boolean validarLogin(String email, String cadastro) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        if (cadastro == null || cadastro.trim().isEmpty() || cadastro.length() != 3) {
            return false;
        }
        return true;
    }
}