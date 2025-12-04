package com.tecdes.pedido.config;

import java.sql.Connection;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExecutarScriptSQL {
    public static void main(String[] args) {
        System.out.println("📋 Executando script SQL...");
        
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Lê o arquivo SQL da pasta do projeto
            String caminhoScript = "src/main/resources/script.sql"; // ou caminho completo
            String script = new String(Files.readAllBytes(Paths.get(caminhoScript)));
            
            // Divide por ponto-e-vírgula para executar comandos separados
            String[] comandos = script.split(";");
            
            for (String comando : comandos) {
                comando = comando.trim();
                if (!comando.isEmpty()) {
                    System.out.println("Executando: " + comando.substring(0, Math.min(50, comando.length())) + "...");
                    stmt.execute(comando);
                }
            }
            
            System.out.println("✅ Script SQL executado com sucesso!");
            
            // Verifica tabelas criadas
            var rs = stmt.executeQuery("SHOW TABLES");
            System.out.println("\n📊 Tabelas criadas:");
            while (rs.next()) {
                System.out.println("   - " + rs.getString(1));
            }
            
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
