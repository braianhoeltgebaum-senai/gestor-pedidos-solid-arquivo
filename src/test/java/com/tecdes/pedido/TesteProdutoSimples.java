package com.tecdes.pedido;

import com.tecdes.pedido.controller.ProdutoController;

public class TesteProdutoSimples {
    public static void main(String[] args) {
        try {
            System.out.println("🟡 Iniciando teste de produto...");
            
            // 1. Criar controller
            ProdutoController controller = new ProdutoController();
            System.out.println("✅ Controller criado");
            
            // 2. Tentar salvar um produto SIMPLES
            System.out.println("🟡 Tentando salvar produto...");
            controller.salvar("Coca-Cola", 'B', "Refrigerante 350ml", 6.50);
            System.out.println("✅ Método salvar() executado");
            
            // 3. Tentar listar
            System.out.println("🟡 Tentando listar produtos...");
            var produtos = controller.buscarTodos();
            System.out.println("📦 Total de produtos: " + produtos.size());
            
            if (produtos.isEmpty()) {
                System.out.println("❌ NENHUM produto retornado!");
            } else {
                for (var p : produtos) {
                    System.out.println("ID: " + p.getIdProduto() + " | " + p.getNmProduto());
                }
            }
            
        } catch (Exception e) {
            System.err.println("❌ ERRO DETALHADO:");
            e.printStackTrace();
            
            // Informações adicionais
            System.err.println("\n🔍 Causa raiz:");
            Throwable causa = e;
            while (causa.getCause() != null) {
                causa = causa.getCause();
            }
            System.err.println("Classe: " + causa.getClass().getName());
            System.err.println("Mensagem: " + causa.getMessage());
        }
    }
}