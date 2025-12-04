package com.tecdes.pedido;


import com.tecdes.pedido.controller.ClienteController;

public class TesteCadastroCliente {
    public static void main(String[] args) {
        try {
            System.out.println("🧪 TESTANDO CADASTRO DE CLIENTE");
            
            ClienteController controller = new ClienteController();
            
            // 1. Listar clientes existentes
            System.out.println("\n📋 Clientes existentes ANTES:");
            var clientes = controller.listarTodos();
            if (clientes.isEmpty()) {
                System.out.println("   Nenhum cliente cadastrado");
            } else {
                for (var c : clientes) {
                    System.out.println("   ID: " + c.getIdCliente() + 
                                     ", Nome: " + c.getNmCliente() + 
                                     ", Email: " + c.getDsEmail());
                }
            }
            
            // 2. Cadastrar novo cliente
            System.out.println("\n💾 Cadastrando novo cliente...");
            controller.salvar("Leonardo Teste", "999", "teste@gmail.com", "11999999999");
            System.out.println("✅ Cliente cadastrado!");
            
            // 3. Listar novamente
            System.out.println("\n📋 Clientes existentes DEPOIS:");
            clientes = controller.listarTodos();
            for (var c : clientes) {
                System.out.println("   ID: " + c.getIdCliente() + 
                                 ", Nome: " + c.getNmCliente() + 
                                 ", Email: " + c.getDsEmail() +
                                 ", Cadastro: " + c.getNrCadastro());
            }
            
            // 4. Tentar fazer login com o cliente cadastrado
            System.out.println("\n🔐 Testando login com cliente cadastrado...");
            boolean loginSucesso = controller.login("teste@gmail.com", "999");
            System.out.println("Login " + (loginSucesso ? "✅ SUCESSO" : "❌ FALHOU"));
            
        } catch (Exception e) {
            System.err.println("\n💥 ERRO NO TESTE: " + e.getMessage());
            e.printStackTrace();
        }
    }
}