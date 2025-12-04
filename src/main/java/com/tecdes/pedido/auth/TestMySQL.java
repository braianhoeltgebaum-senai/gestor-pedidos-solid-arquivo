package com.tecdes.pedido.auth;

import java.sql.DriverManager;

public class TestMySQL {
    public static void main(String[] args) {
        System.out.println("🔍 Testando conexão MySQL...\n");
        
        String[] senhasTeste = {"", "root", "123456", "password", "12345", "mysql"};
        boolean conectado = false;
        
        for (String senha : senhasTeste) {
            try {
                System.out.print("Tentando com senha: '" + (senha.isEmpty() ? "(vazia)" : senha) + "'... ");
                
                DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/DB_SA_2?useSSL=false&serverTimezone=UTC",
                    "root",
                    senha
                );
                
                System.out.println("✅ CONECTADO!");
                System.out.println("\n🎉 Use esta configuração no db.properties:");
                System.out.println("db.password=" + senha);
                conectado = true;
                break;
                
            } catch (Exception e) {
                System.out.println("❌ Falhou");
            }
        }
        
        if (!conectado) {
            System.out.println("\n⚠️  Nenhuma senha padrão funcionou.");
            System.out.println("Tente:");
            System.out.println("1. MySQL Workbench -> Ver conexões salvas");
            System.out.println("2. Comando: mysql -u root -p (e ver qual pede)");
            System.out.println("3. Se XAMPP/WAMP: senha geralmente é vazia");
        }
    }
}