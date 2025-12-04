package com.tecdes.pedido.controller;

import com.tecdes.pedido.auth.AutenticacaoSistema;

public class FuncionarioController {
    
    private final AutenticacaoSistema autenticacao;
    
    public FuncionarioController() {
        this.autenticacao = new AutenticacaoSistema();
    }
    
    // Login de funcionário
    public boolean login(String cpf, String senha) {
        return autenticacao.autenticar(cpf, senha);
    }
    
    // Logout
    public void logout() {
        autenticacao.logout();
    }
    
    // Verifica se está autenticado
    public boolean isAutenticado() {
        return autenticacao.isAutenticado();
    }
    
    // Verifica se é gerente
    public boolean isGerente() {
        return autenticacao.isGerente();
    }
    
    // Verifica se é atendente (ou gerente)
    public boolean isAtendente() {
        return autenticacao.isAtendente();
    }
    
    // Retorna cargo do funcionário logado
    public String getCargoLogado() {
        return autenticacao.getCargoLogado();
    }
    
    // Retorna CPF do funcionário logado
    public String getCpfLogado() {
        return autenticacao.getCpfLogado();
    }
    
    // Verifica permissões
    public boolean podeGerenciar() {
        return autenticacao.isGerente();
    }
    
    public boolean podeAtender() {
        return autenticacao.isAtendente();
    }
    
    // Métodos para administração (apenas gerente)
    public void adicionarFuncionario(String cpf, String senha, String cargo) {
        if (!isGerente()) {
            throw new IllegalStateException("Apenas gerentes podem adicionar funcionários");
        }
        autenticacao.adicionarFuncionario(cpf, senha, cargo);
    }
    
    public void listarFuncionarios() {
        if (!isGerente()) {
            throw new IllegalStateException("Apenas gerentes podem listar funcionários");
        }
        autenticacao.listarFuncionarios();
    }
    
    // Valida CPF (formato: 000.000.000-00)
    public boolean validarCpf(String cpf) {
        return cpf != null && cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
    }
    
    // Valida senha (mínimo 4 caracteres)
    public boolean validarSenha(String senha) {
        return senha != null && senha.length() >= 4;
    }
}