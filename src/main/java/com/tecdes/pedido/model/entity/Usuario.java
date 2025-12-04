// package com.tecdes.pedido.model.entity;

// import java.time.LocalDateTime;

// public class Usuario {  
//     private int idUsuario;
//     private String login;
//     private String senha;
//     private String nome;
//     private String tipo; // "ADMIN", "FUNCIONARIO"
//     private LocalDateTime dtCadastro;

//     public Usuario() {
//         this.dtCadastro = LocalDateTime.now();
//     }

//     public Usuario(String login, String senha, String nome, String tipo) {
//         this();
//         setLogin(login);
//         setSenha(senha);
//         setNome(nome);
//         setTipo(tipo);
//     }
   
//     public boolean autenticar(String senhaDigitada) {
//         return this.senha != null && this.senha.equals(senhaDigitada);
//     }

//     // Getters e Setters
//     public int getIdUsuario() {
//         return idUsuario;
//     }

//     public void setIdUsuario(int idUsuario) {
//         this.idUsuario = idUsuario;
//     }

//     public String getLogin() {
//         return login;
//     }

//     public void setLogin(String login) {
//         if (login == null || login.trim().isEmpty()) {
//             throw new IllegalArgumentException("Login não pode ser vazio.");
//         }
//         if (login.length() > 50) {
//             throw new IllegalArgumentException("Login muito longo (máx 50 caracteres)");
//         }
//         this.login = login;
//     }

//     public String getSenha() {
//         return senha;
//     }

//     public void setSenha(String senha) {
//         if (senha == null || senha.length() < 6) {
//              throw new IllegalArgumentException("A senha deve ter no mínimo 6 caracteres.");
//         }
//         if (senha.length() > 100) {
//             throw new IllegalArgumentException("Senha muito longa (máx 100 caracteres)");
//         }
//         this.senha = senha;
//     }

//     public String getNome() {
//         return nome;
//     }

//     public void setNome(String nome) {
//         if (nome == null || nome.trim().isEmpty()) {
//             throw new IllegalArgumentException("Nome não pode ser vazio.");
//         }
//         if (nome.length() > 100) {
//             throw new IllegalArgumentException("Nome muito longo (máx 100 caracteres)");
//         }
//         this.nome = nome;
//     }

//     public String getTipo() {
//         return tipo;
//     }

//     public void setTipo(String tipo) {
//         if (tipo == null || (!tipo.equals("ADMIN") && !tipo.equals("FUNCIONARIO"))) {
//             throw new IllegalArgumentException("Tipo inválido. Use: ADMIN ou FUNCIONARIO");
//         }
//         this.tipo = tipo;
//     }

//     public boolean isAdmin() {
//         return "ADMIN".equals(tipo);
//     }

//     public LocalDateTime getDtCadastro() {
//         return dtCadastro;
//     }

//     public void setDtCadastro(LocalDateTime dtCadastro) {
//         this.dtCadastro = dtCadastro;
//     }

//     @Override
//     public String toString() {
//         return nome + " (" + login + ") - " + tipo;
//     }
// }