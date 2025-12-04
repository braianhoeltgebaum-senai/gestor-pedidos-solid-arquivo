package com.tecdes.pedido.view;

import javax.swing.*;
import java.awt.*;

public class MainMenuView extends JFrame {
    
    private String tipoUsuario;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    
    public MainMenuView(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
        configurarJanela();
        criarMenu();
        criarPainelCards();
    }
    
    private void configurarJanela() {
        setTitle("Sistema de Lanchonete - Menu Principal");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Layout principal
        setLayout(new BorderLayout());
    }
    
    private void criarMenu() {
        JMenuBar menuBar = new JMenuBar();
        
        // Menu Cadastros
        JMenu menuCadastros = new JMenu("📁 Cadastros");
        
        JMenuItem itemClientes = new JMenuItem("👥 Clientes");
        itemClientes.addActionListener(e -> mostrarCard("clientes"));
        
        JMenuItem itemProdutos = new JMenuItem("🍔 Produtos");
        itemProdutos.addActionListener(e -> mostrarCard("produtos"));
        
        menuCadastros.add(itemClientes);
        menuCadastros.add(itemProdutos);
        
        // Menu Pedidos (para todos)
        JMenu menuPedidos = new JMenu("📋 Pedidos");
        
        JMenuItem itemNovoPedido = new JMenuItem("➕ Novo Pedido");
        itemNovoPedido.addActionListener(e -> mostrarCard("novoPedido"));
        
        JMenuItem itemVerPedidos = new JMenuItem("👁️ Ver Pedidos");
        itemVerPedidos.addActionListener(e -> mostrarCard("pedidos"));
        
        menuPedidos.add(itemNovoPedido);
        menuPedidos.add(itemVerPedidos);
        
        // Menu Relatórios (apenas gerentes)
        if (tipoUsuario.equals("Gerente")) {
            JMenu menuRelatorios = new JMenu("📊 Relatórios");
            
            JMenuItem itemRelVendas = new JMenuItem("💰 Vendas Diárias");
            itemRelVendas.addActionListener(e -> gerarRelatorioVendas());
            
            JMenuItem itemRelProdutos = new JMenuItem("🍟 Produtos Mais Vendidos");
            itemRelProdutos.addActionListener(e -> gerarRelatorioProdutos());
            
            menuRelatorios.add(itemRelVendas);
            menuRelatorios.add(itemRelProdutos);
            menuBar.add(menuRelatorios);
        }
        
        // Menu Sobre
        JMenu menuAjuda = new JMenu("❓ Ajuda");
        JMenuItem itemSobre = new JMenuItem("ℹ️ Sobre o Sistema");
        itemSobre.addActionListener(e -> mostrarSobre());
        menuAjuda.add(itemSobre);
        
        // Adicionar menus à barra
        menuBar.add(menuCadastros);
        menuBar.add(menuPedidos);
        menuBar.add(menuAjuda);
        
        setJMenuBar(menuBar);
        
        // Barra de status (rodapé)
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        
        JLabel lblUsuario = new JLabel("Usuário: " + tipoUsuario);
        JLabel lblStatus = new JLabel("✅ Sistema Online");
        
        statusBar.add(lblUsuario);
        statusBar.add(Box.createHorizontalStrut(50));
        statusBar.add(lblStatus);
        
        add(statusBar, BorderLayout.SOUTH);
    }
    
    private void criarPainelCards() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        // Tela inicial (dashboard)
        JPanel dashboardPanel = criarDashboardPanel();
        cardPanel.add(dashboardPanel, "dashboard");
        
        // Telas específicas (você vai criar depois)
        cardPanel.add(new JLabel("Tela de Clientes - Em construção"), "clientes");
        cardPanel.add(new JLabel("Tela de Produtos - Em construção"), "produtos");
        cardPanel.add(new JLabel("Tela de Pedidos - Em construção"), "pedidos");
        cardPanel.add(new JLabel("Novo Pedido - Em construção"), "novoPedido");
        
        add(cardPanel, BorderLayout.CENTER);
        
        // Mostrar dashboard inicial
        cardLayout.show(cardPanel, "dashboard");
    }
    
    private JPanel criarDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Cabeçalho
        JLabel lblTitulo = new JLabel("🏠 Dashboard - Bem-vindo!");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);
        
        // Painel de cards
        JPanel cardsDashboard = new JPanel(new GridLayout(2, 3, 20, 20));
        cardsDashboard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        cardsDashboard.setBackground(Color.WHITE);
        
        // Card 1: Clientes
        JPanel cardClientes = criarCard("👥 Clientes", "Gerenciar clientes", Color.decode("#4CAF50"));
        cardClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mostrarCard("clientes");
            }
        });
        
        // Card 2: Produtos
        JPanel cardProdutos = criarCard("🍔 Produtos", "Gerenciar cardápio", Color.decode("#2196F3"));
        cardProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mostrarCard("produtos");
            }
        });
        
        // Card 3: Pedidos
        JPanel cardPedidos = criarCard("📋 Pedidos", "Ver pedidos", Color.decode("#FF9800"));
        cardPedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mostrarCard("pedidos");
            }
        });
        
        // Card 4: Novo Pedido
        JPanel cardNovoPedido = criarCard("➕ Novo Pedido", "Criar novo pedido", Color.decode("#E91E63"));
        cardNovoPedido.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mostrarCard("novoPedido");
            }
        });
        
        // Card 5: Pagamentos
        JPanel cardPagamentos = criarCard("💰 Pagamentos", "Registrar pagamentos", Color.decode("#9C27B0"));
        cardPagamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(this, "Funcionalidade em desenvolvimento");
            }
        });
        
        // Card 6: Avaliações
        JPanel cardAvaliacoes = criarCard("⭐ Avaliações", "Ver avaliações", Color.decode("#FF5722"));
        cardAvaliacoes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(this, "Funcionalidade em desenvolvimento");
            }
        });
        
        cardsDashboard.add(cardClientes);
        cardsDashboard.add(cardProdutos);
        cardsDashboard.add(cardPedidos);
        cardsDashboard.add(cardNovoPedido);
        cardsDashboard.add(cardPagamentos);
        cardsDashboard.add(cardAvaliacoes);
        
        panel.add(cardsDashboard, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel criarCard(String titulo, String descricao, Color cor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(cor, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(cor);
        
        JLabel lblDesc = new JLabel(descricao);
        lblDesc.setFont(new Font("Arial", Font.PLAIN, 12));
        lblDesc.setForeground(Color.DARK_GRAY);
        
        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(lblDesc, BorderLayout.CENTER);
        
        return card;
    }
    
    private void mostrarCard(String cardName) {
        cardLayout.show(cardPanel, cardName);
    }
    
    private void gerarRelatorioVendas() {
        // Implementar usando PedidoController
        JOptionPane.showMessageDialog(this, "Gerando relatório de vendas...");
    }
    
    private void gerarRelatorioProdutos() {
        // Implementar usando ProdutoController + ItemPedidoController
        JOptionPane.showMessageDialog(this, "Gerando relatório de produtos...");
    }
    
    private void mostrarSobre() {
        String sobre = """
            🍔 Sistema de Gestão de Lanchonete
            Versão: 1.0
            Desenvolvido por: [Seu Nome]
            Curso: Técnico em Desenvolvimento de Sistemas
            Professor: Gerson Trindade
            
            Funcionalidades:
            • Cadastro de clientes e produtos
            • Gestão de pedidos
            • Controle de pagamentos
            • Sistema de avaliações
            • Relatórios em .txt
            """;
            
        JOptionPane.showMessageDialog(this, sobre, "Sobre o Sistema", JOptionPane.INFORMATION_MESSAGE);
    }
}