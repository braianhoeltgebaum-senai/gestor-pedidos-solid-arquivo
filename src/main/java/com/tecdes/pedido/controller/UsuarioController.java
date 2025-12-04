package com.tecdes.pedido.controller;


import com.tecdes.pedido.model.entity.Usuario;
import com.tecdes.pedido.service.UsuarioService;

public class UsuarioController {
    
    private final UsuarioService service;
    
    public UsuarioController() {
        this.service = new UsuarioService(new com.tecdes.pedido.repository.UsuarioRepositoryImpl());
    }
    
    // Cadastrar usuário
    public void cadastrar(String nome, String email, String senha, char perfil) {
        // perfil: 'A' (Attendente), 'G' (Gerente), 'C' (Cliente)
        service.cadastrarUsuario(nome, email, senha, perfil);
    }
    
    // Login
    public Usuario login(String email, String senha) {
        return service.autenticar(email, senha);
    }
    
    // Logout (se necessário na controller)
    public void logout() {
        service.logout();
    }
    
    // Buscar por ID
    public Usuario buscarPorId(int id) {
        return service.buscarPorId(id);
    }
    
    // Atualizar usuário
    public void atualizar(int id, String nome, String email, char perfil) {
        service.atualizarUsuario(id, nome, email, perfil);
    }
    
    // Alterar senha
    public void alterarSenha(int id, String novaSenha) {
        service.alterarSenha(id, novaSenha);
    }
    
    // Verificar se usuário está autenticado
    public boolean isAutenticado() {
        return service.isAutenticado();
    }
    
    // Obter usuário atual
    public Usuario getUsuarioAtual() {
        return service.getUsuarioAtual();
    }
}
