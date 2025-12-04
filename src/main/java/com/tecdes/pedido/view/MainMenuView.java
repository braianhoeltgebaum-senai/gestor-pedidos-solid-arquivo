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
        itemClientes.addActionListener(e -> abrirClienteView());
        
        JMenuItem itemProdutos = new JMenuItem("🍔 Produtos");
        itemProdutos.addActionListener(e -> abrirProdutoView());
        
        menuCadastros.add(itemClientes);
        menuCadastros.add(itemProdutos);
        
        // Menu Pedidos (para todos)
        JMenu menuPedidos = new JMenu("📋 Pedidos");
        
        JMenuItem itemNovoPedido = new JMenuItem("➕ Novo Pedido");
        itemNovoPedido.addActionListener(e -> abrirPedidoView());
        
        JMenuItem itemVerPedidos = new JMenuItem("👁️ Ver Pedidos");
        itemVerPedidos.addActionListener(e -> abrirListaPedidosView());
        
        menuPedidos.add(itemNovoPedido);
        menuPedidos.add(itemVerPedidos);
        
        // Menu Pagamentos
        JMenu menuPagamentos = new JMenu("💰 Pagamentos");
        JMenuItem itemPagamentos = new JMenuItem("💳 Gerenciar Pagamentos");
        itemPagamentos.addActionListener(e -> abrirPagamentoView());
        menuPagamentos.add(itemPagamentos);
        
        // Menu Relatórios (apenas gerentes)
        if (tipoUsuario.equals("Gerente") || tipoUsuario.equals("Funcionário")) {
            JMenu menuRelatorios = new JMenu("📊 Relatórios");
            
            JMenuItem itemRelVendas = new JMenuItem("💰 Relatório de Vendas");
            itemRelVendas.addActionListener(e -> abrirRelatorioView());
            
            JMenuItem itemRelClientes = new JMenuItem("👥 Relatório de Clientes");
            itemRelClientes.addActionListener(e -> gerarRelatorioClientes());
            
            JMenuItem itemRelProdutos = new JMenuItem("🍟 Relatório de Produtos");
            itemRelProdutos.addActionListener(e -> gerarRelatorioProdutos());
            
            JMenuItem itemRelCompleto = new JMenuItem("📈 Relatório Completo");
            itemRelCompleto.addActionListener(e -> gerarRelatorioCompleto());
            
            menuRelatorios.add(itemRelVendas);
            menuRelatorios.add(itemRelClientes);
            menuRelatorios.add(itemRelProdutos);
            menuRelatorios.add(itemRelCompleto);
            menuBar.add(menuRelatorios);
        }
        
        // Menu Sobre
        JMenu menuAjuda = new JMenu("❓ Ajuda");
        JMenuItem itemSobre = new JMenuItem("ℹ️ Sobre o Sistema");
        itemSobre.addActionListener(e -> mostrarSobre());
        
        JMenuItem itemAjuda = new JMenuItem("📖 Manual do Usuário");
        itemAjuda.addActionListener(e -> mostrarAjuda());
        
        menuAjuda.add(itemSobre);
        menuAjuda.add(itemAjuda);
        
        // Adicionar menus à barra
        menuBar.add(menuCadastros);
        menuBar.add(menuPedidos);
        menuBar.add(menuPagamentos);
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
        
        add(cardPanel, BorderLayout.CENTER);
        
        // Mostrar dashboard inicial
        cardLayout.show(cardPanel, "dashboard");
    }
    
    private JPanel criarDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240));
        
        // Cabeçalho
        JLabel lblTitulo = new JLabel("🏠 Dashboard - Bem-vindo, " + tipoUsuario + "!");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        lblTitulo.setForeground(new Color(70, 130, 180));
        panel.add(lblTitulo, BorderLayout.NORTH);
        
        // Painel de cards
        JPanel cardsDashboard = new JPanel(new GridLayout(2, 3, 20, 20));
        cardsDashboard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        cardsDashboard.setBackground(new Color(240, 240, 240));
        
        // Card 1: Clientes
        JPanel cardClientes = criarCard("👥 Clientes", "Gerenciar clientes", Color.decode("#4CAF50"));
        cardClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                abrirClienteView();
            }
        });
        
        // Card 2: Produtos
        JPanel cardProdutos = criarCard("🍔 Produtos", "Gerenciar cardápio", Color.decode("#2196F3"));
        cardProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                abrirProdutoView();
            }
        });
        
        // Card 3: Pedidos
        JPanel cardPedidos = criarCard("📋 Pedidos", "Ver pedidos", Color.decode("#FF9800"));
        cardPedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                abrirListaPedidosView();
            }
        });
        
        // Card 4: Novo Pedido
        JPanel cardNovoPedido = criarCard("➕ Novo Pedido", "Criar novo pedido", Color.decode("#E91E63"));
        cardNovoPedido.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                abrirPedidoView();
            }
        });
        
        // Card 5: Pagamentos
        JPanel cardPagamentos = criarCard("💰 Pagamentos", "Registrar pagamentos", Color.decode("#9C27B0"));
        cardPagamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                abrirPagamentoView();
            }
        });
        
        // Card 6: Relatórios
        JPanel cardRelatorios = criarCard("📊 Relatórios", "Gerar relatórios", Color.decode("#FF5722"));
        cardRelatorios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                abrirRelatorioView();
            }
        });
        
        cardsDashboard.add(cardClientes);
        cardsDashboard.add(cardProdutos);
        cardsDashboard.add(cardPedidos);
        cardsDashboard.add(cardNovoPedido);
        cardsDashboard.add(cardPagamentos);
        cardsDashboard.add(cardRelatorios);
        
        panel.add(cardsDashboard, BorderLayout.CENTER);
        
        // Rodapé do dashboard
        JPanel panelRodape = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelRodape.setBackground(new Color(220, 220, 220));
        panelRodape.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JLabel lblInfo = new JLabel("🍔 Sistema de Gestão de Lanchonete - TecDes © 2025");
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 12));
        panelRodape.add(lblInfo);
        
        panel.add(panelRodape, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel criarCard(String titulo, String descricao, Color cor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(cor.darker(), 1),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efeito hover
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBackground(new Color(250, 250, 250));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBackground(Color.WHITE);
            }
        });
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(cor);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lblDesc = new JLabel(descricao);
        lblDesc.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDesc.setForeground(Color.DARK_GRAY);
        lblDesc.setHorizontalAlignment(SwingConstants.CENTER);
        
        card.add(lblTitulo, BorderLayout.CENTER);
        card.add(lblDesc, BorderLayout.SOUTH);
        
        return card;
    }
    
   
    
    // Métodos para abrir as Views específicas
    private void abrirClienteView() {
        SwingUtilities.invokeLater(() -> {
            ClienteView clienteView = new ClienteView();
            clienteView.setVisible(true);
        });
    }
    
    private void abrirProdutoView() {
        SwingUtilities.invokeLater(() -> {
            ProdutoView produtoView = new ProdutoView();
            produtoView.setVisible(true);
        });
    }
    
    private void abrirPedidoView() {
        SwingUtilities.invokeLater(() -> {
            PedidoView pedidoView = new PedidoView();
            pedidoView.setVisible(true);
        });
    }
    
    private void abrirListaPedidosView() {
        SwingUtilities.invokeLater(() -> {
            ListaPedidosView listaView = new ListaPedidosView();
            listaView.setVisible(true);
        });
    }
    
    private void abrirPagamentoView() {
        SwingUtilities.invokeLater(() -> {
            PagamentoView pagamentoView = new PagamentoView();
            pagamentoView.setVisible(true);
        });
    }
    
    private void abrirRelatorioView() {
        SwingUtilities.invokeLater(() -> {
            RelatorioView relatorioView = new RelatorioView();
            relatorioView.setVisible(true);
        });
    }
    
    // Métodos para gerar relatórios específicos
    private void gerarRelatorioClientes() {
        RelatorioView relatorioView = new RelatorioView();
        relatorioView.setVisible(true);
        // Aqui você poderia adicionar lógica para gerar relatório específico
        JOptionPane.showMessageDialog(this, 
            "Relatório de Clientes disponível na tela de relatórios!",
            "Abrindo Relatórios",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void gerarRelatorioProdutos() {
        RelatorioView relatorioView = new RelatorioView();
        relatorioView.setVisible(true);
        // Aqui você poderia adicionar lógica para gerar relatório específico
        JOptionPane.showMessageDialog(this, 
            "Relatório de Produtos disponível na tela de relatórios!",
            "Abrindo Relatórios",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void gerarRelatorioCompleto() {
        RelatorioView relatorioView = new RelatorioView();
        relatorioView.setVisible(true);
        // Aqui você poderia adicionar lógica para gerar relatório específico
        JOptionPane.showMessageDialog(this, 
            "Relatório Completo disponível na tela de relatórios!",
            "Abrindo Relatórios",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void mostrarSobre() {
        String sobre = """
            🍔 Sistema de Gestão de Lanchonete - TecDes
            
            Versão: 1.0
            Desenvolvido por: [Seu Nome]
            Curso: Técnico em Desenvolvimento de Sistemas
            Professor: Gerson Trindade
            
            📋 Funcionalidades:
            • 👥 Cadastro de clientes
            • 🍔 Gerenciamento de produtos
            • 📋 Criação de pedidos
            • 💰 Controle de pagamentos
            • ⭐ Sistema de avaliações
            • 📊 Relatórios em .txt
            • 🔐 Sistema de login multi-usuário
            
            🚀 Tecnologias:
            • Java 17+
            • Swing (Interface Gráfica)
            • MySQL (Banco de Dados)
            • Padrão MVC (Model-View-Controller)
            
            © 2025 - Todos os direitos reservados
            """;
            
        JOptionPane.showMessageDialog(this, 
            sobre, 
            "Sobre o Sistema", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void mostrarAjuda() {
        String ajuda = """
            📖 MANUAL DO USUÁRIO - SISTEMA DE LANCHONETE
            
            1. 👥 CADASTRO DE CLIENTES
               • Acesse: Cadastros → Clientes
               • Preencha: Nome, Cadastro (3 dígitos), Email, Telefone
               • Use: Salvar, Editar, Excluir
            
            2. 🍔 GERENCIAMENTO DE PRODUTOS
               • Acesse: Cadastros → Produtos
               • Preencha: Nome, Tipo, Descrição, Valor
               • Tipos: L (Lanche), B (Bebida), C (Complemento)
            
            3. 📋 CRIAR PEDIDO
               • Acesse: Pedidos → Novo Pedido
               • Selecione: Cliente, Produtos, Quantidades
               • Finalize: Clique em Finalizar Pedido
            
            4. 💰 REGISTRAR PAGAMENTO
               • Acesse: Pagamentos → Gerenciar Pagamentos
               • Selecione: Pedido, Forma de Pagamento
               • Informe: Valor Pago
               • Sistema calcula troco automaticamente
            
            5. 📊 GERAR RELATÓRIOS
               • Acesse: Relatórios
               • Escolha o tipo de relatório
               • Relatórios são salvos em .txt
            
            🔐 PERMISSÕES:
            • Gerente: Todas as funcionalidades
            • Funcionário: Criar pedidos, registrar pagamentos
            • Cliente: Ver pedidos, fazer avaliações
            
            ⚠️ DICAS:
            • Sempre confirme os dados antes de salvar
            • Verifique o status dos pedidos
            • Faça backup dos relatórios importantes
            • Em caso de dúvidas, consulte a equipe de suporte
            """;
            
        // Criar uma caixa de diálogo personalizada com scroll
        JTextArea textArea = new JTextArea(ajuda);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        
        JOptionPane.showMessageDialog(this, 
            scrollPane, 
            "Manual do Usuário", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}