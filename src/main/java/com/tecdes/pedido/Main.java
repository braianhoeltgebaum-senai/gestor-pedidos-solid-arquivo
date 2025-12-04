package com.tecdes.pedido;

import com.tecdes.pedido.view.LoginView;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Configurar Look and Feel do sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Não foi possível definir o Look and Feel do sistema");
        }
        
        // Executar na Thread do Swing
        SwingUtilities.invokeLater(() -> {
            System.out.println("🚀 Iniciando Sistema de Gestão de Pedidos");
            System.out.println("==========================================");
            
            // Iniciar com a tela de login
            new LoginView().setVisible(true);
        });
    }
}