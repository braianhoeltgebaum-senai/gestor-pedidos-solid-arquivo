package com.tecdes.pedido.auth;  // ✅ Note o novo pacote

import java.util.HashMap;
import java.util.Map;

public class AutenticacaoSistema {
    
    // Armazena funcionários em memória (CPF -> [senha, cargo])
    private static final Map<String, String[]> FUNCIONARIOS = new HashMap<>();
    
    static {
        // Funcionários pré-cadastrados
        FUNCIONARIOS.put("123.456.789-00", new String[]{"senha123", "Gerente"});
        FUNCIONARIOS.put("987.654.321-00", new String[]{"atende123", "Atendente"});
        FUNCIONARIOS.put("111.222.333-44", new String[]{"cozinha123", "Cozinheiro"});
    }
    
    private String cpfLogado;
    private String cargoLogado;
    
    // Autentica funcionário
    public boolean autenticar(String cpf, String senha) {
        // Limpa login anterior
        this.cpfLogado = null;
        this.cargoLogado = null;
        
        // Remove formatação do CPF
        String cpfLimpo = cpf.replace(".", "").replace("-", "");
        
        // Busca nas credenciais
        for (Map.Entry<String, String[]> entry : FUNCIONARIOS.entrySet()) {
            String cpfCadastrado = entry.getKey().replace(".", "").replace("-", "");
            
            if (cpfCadastrado.equals(cpfLimpo)) {
                String[] credenciais = entry.getValue();
                String senhaCadastrada = credenciais[0];
                String cargo = credenciais[1];
                
                if (senhaCadastrada.equals(senha)) {
                    this.cpfLogado = cpf;
                    this.cargoLogado = cargo;
                    System.out.println("✅ " + cargo + " autenticado: " + cpf);
                    return true;
                }
            }
        }
        
        System.out.println("❌ Falha na autenticação para CPF: " + cpf);
        return false;
    }
    
    // Logout
    public void logout() {
        if (cpfLogado != null) {
            System.out.println("🚪 " + cargoLogado + " deslogado: " + cpfLogado);
        }
        this.cpfLogado = null;
        this.cargoLogado = null;
    }
    
    // Getters
    public String getCpfLogado() {
        return cpfLogado;
    }
    
    public String getCargoLogado() {
        return cargoLogado;
    }
    
    public boolean isGerente() {
        return "Gerente".equals(cargoLogado);
    }
    
    public boolean isAtendente() {
        return "Atendente".equals(cargoLogado) || isGerente();
    }
    
    public boolean isAutenticado() {
        return cpfLogado != null;
    }
    
    // Adicionar novo funcionário
    public void adicionarFuncionario(String cpf, String senha, String cargo) {
        FUNCIONARIOS.put(cpf, new String[]{senha, cargo});
        System.out.println("👤 Funcionário adicionado: " + cpf + " - " + cargo);
    }
    
    // Listar funcionários cadastrados
    public void listarFuncionarios() {
        System.out.println("📋 Funcionários cadastrados:");
        for (Map.Entry<String, String[]> entry : FUNCIONARIOS.entrySet()) {
            System.out.println("  CPF: " + entry.getKey() + 
                             " | Cargo: " + entry.getValue()[1]);
        }
    }
}