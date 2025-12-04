package com.tecdes.pedido.view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JComboBox<String> cbxTipoLogin;
    private JButton btnLogin;
    private JButton btnCadastrar;
    
    public LoginView() {
        System.out.println("🚀 Iniciando LoginView...");
        configurarJanela();
        criarComponentesSimples();
        configurarEventos();
        setVisible(true);
        System.out.println("✅ LoginView criada e visível!");
    }
    
    private void configurarJanela() {
        setTitle("Sistema de Lanchonete - Login");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
    }
    
    private void criarComponentesSimples() {
        System.out.println("🛠️ Criando componentes SIMPLIFICADOS...");
        
        // Painel principal com BoxLayout vertical
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Título
        JLabel lblTitulo = new JLabel("🍔 Lanchonete Delícia");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblTitulo);
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Subtítulo
        JLabel lblSubtitulo = new JLabel("Sistema de Gestão de Pedidos");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblSubtitulo);
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setOpaque(false);
        
        // Tipo de Login
        formPanel.add(new JLabel("Tipo de Login:"));
        String[] tipos = {"Cliente", "Funcionário", "Gerente"};
        cbxTipoLogin = new JComboBox<>(tipos);
        formPanel.add(cbxTipoLogin);
        
        // Email/CPF
        formPanel.add(new JLabel("Email/CPF:"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail);
        
        // Senha/Cadastro
        formPanel.add(new JLabel("Senha/Cadastro:"));
        txtSenha = new JPasswordField();
        formPanel.add(txtSenha);
        
        JPanel formContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        formContainer.setOpaque(false);
        formContainer.add(formPanel);
        mainPanel.add(formContainer);
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);
        
        // Botão Login
        btnLogin = new JButton("Entrar");
        btnLogin.setBackground(new Color(70, 130, 180));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setPreferredSize(new Dimension(120, 40));
        
        // Botão Cadastrar
        btnCadastrar = new JButton("Cadastrar Cliente");
        btnCadastrar.setBackground(new Color(46, 125, 50));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(new Font("Arial", Font.PLAIN, 13));
        btnCadastrar.setPreferredSize(new Dimension(150, 40));
        
        // Adiciona os botões
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnCadastrar);
        
        // Container para centralizar
        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonContainer.setOpaque(false);
        buttonContainer.add(buttonPanel);
        
        mainPanel.add(buttonContainer);
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Rodapé
        JLabel lblRodape = new JLabel("© 2025 - TecDes - Sistema de Lanchonete");
        lblRodape.setFont(new Font("Arial", Font.PLAIN, 10));
        lblRodape.setForeground(Color.GRAY);
        lblRodape.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblRodape);
        
        // Adiciona tudo à janela
        add(mainPanel, BorderLayout.CENTER);
        
        // Força atualização
        revalidate();
        repaint();
        
        System.out.println("✅ Componentes criados!");
    }
    
    private void configurarEventos() {
        System.out.println("⚡ Configurando eventos...");
        
        // Botão Cadastrar
        if (btnCadastrar != null) {
            btnCadastrar.addActionListener(e -> {
                System.out.println("🎯 Botão Cadastrar Cliente clicado!");
                
                // Abre a tela COMPLETA de cadastro de clientes
                abrirTelaCadastroClientes();
            });
        }
        
        // Botão Login
        if (btnLogin != null) {
            btnLogin.addActionListener(e -> {
                System.out.println("🎯 Botão Login clicado!");
                fazerLogin();
            });
        }
        
        // Enter no campo senha
        if (txtSenha != null) {
            txtSenha.addActionListener(e -> fazerLogin());
        }
    }
    
    private void fazerLogin() {
        System.out.println("Tentando login...");
        
        if (txtEmail == null || txtSenha == null || cbxTipoLogin == null) {
            JOptionPane.showMessageDialog(this, "Sistema não inicializado corretamente", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String tipo = (String) cbxTipoLogin.getSelectedItem();
        String usuario = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();
        
        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validação simples de login
        // Para cliente: email + número de 3 dígitos
        // Para funcionário/gerente: CPF + senha
        
        boolean loginValido = false;
        
        // Validação baseada no tipo de usuário
        switch (tipo) {
            case "Cliente":
                // Para cliente: email deve conter @ e senha deve ter 3 dígitos
                if (usuario.contains("@") && senha.matches("\\d{3}")) {
                    loginValido = true;
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Para cliente:\n" +
                        "• Email: deve ser válido (ex: cliente@email.com)\n" +
                        "• Senha: número de cadastro de 3 dígitos (ex: 123)",
                        "Formato Inválido",
                        JOptionPane.WARNING_MESSAGE);
                }
                break;
                
            case "Funcionário":
            case "Gerente":
                // Para funcionário/gerente: CPF deve ter 11 dígitos
                if (usuario.matches("\\d{11}") && !senha.isEmpty()) {
                    loginValido = true;
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Para " + tipo.toLowerCase() + ":\n" +
                        "• CPF: 11 dígitos (sem pontos ou traços)\n" +
                        "• Senha: não pode estar vazia",
                        "Formato Inválido",
                        JOptionPane.WARNING_MESSAGE);
                }
                break;
        }
        
        if (loginValido) {
            // Login bem-sucedido
            System.out.println("✅ Login bem-sucedido como: " + tipo);
            
            JOptionPane.showMessageDialog(this, 
                "✅ Login realizado com sucesso!\n\n" +
                "👤 Tipo: " + tipo + "\n" +
                "📋 Usuário: " + usuario + "\n\n" +
                "Abrindo menu principal...",
                "Login Bem-sucedido",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Abre o menu principal
            abrirMenuPrincipal(tipo);
            
        } else {
            JOptionPane.showMessageDialog(this,
                "❌ Login falhou!\n\n" +
                "Verifique suas credenciais.\n" +
                "Para cliente: use email + número de cadastro (3 dígitos)\n" +
                "Para funcionário/gerente: use CPF (11 dígitos) + senha",
                "Erro no Login",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void abrirMenuPrincipal(String tipoUsuario) {
        System.out.println("🚀 Abrindo menu principal para: " + tipoUsuario);
        
        // Fecha a tela de login
        this.dispose();
        
        // Abre o menu principal
        SwingUtilities.invokeLater(() -> {
            try {
                MainMenuView menuView = new MainMenuView(tipoUsuario);
                menuView.setVisible(true);
                System.out.println("✅ MainMenuView aberta com sucesso!");
            } catch (Exception e) {
                System.err.println("❌ Erro ao abrir MainMenuView: " + e.getMessage());
                e.printStackTrace();
                
                // Fallback: mostra mensagem de erro
                JOptionPane.showMessageDialog(null,
                    "Erro ao abrir menu principal: " + e.getMessage() + "\n\n" +
                    "Tente reiniciar o sistema.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    private void abrirTelaCadastroClientes() {
        System.out.println("📝 Abrindo tela completa de cadastro de clientes...");
        
        try {
            // Cria e mostra a tela de cadastro de clientes
            SwingUtilities.invokeLater(() -> {
                ClienteView clienteView = new ClienteView(this, true); // true = modal
                clienteView.setVisible(true);
                System.out.println("✅ Tela ClienteView aberta com sucesso!");
            });
            
        } catch (Exception e) {
            System.err.println("❌ Erro ao abrir ClienteView: " + e.getMessage());
            e.printStackTrace();
            
            // Fallback: abre um diálogo simples se a ClienteView falhar
            JOptionPane.showMessageDialog(this,
                "Não foi possível abrir a tela de cadastro completa.\n" +
                "Erro: " + e.getMessage() + "\n\n" +
                "Abrindo formulário simples...",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
                
            abrirCadastroSimples();
        }
    }
    
    private void abrirCadastroSimples() {
        // Diálogo simples de cadastro (fallback)
        JDialog dialog = new JDialog(this, "Cadastro Rápido de Cliente", true);
        dialog.setSize(350, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextField txtNome = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtTelefone = new JTextField();
        
        panel.add(new JLabel("Nome:"));
        panel.add(txtNome);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Telefone:"));
        panel.add(txtTelefone);
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            JOptionPane.showMessageDialog(dialog, 
                "Cliente salvo (simulação)\n\n" +
                "Nome: " + txtNome.getText() + "\n" +
                "Email: " + txtEmail.getText() + "\n" +
                "Telefone: " + txtTelefone.getText(),
                "Cadastro Simulado",
                JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dialog.dispose());
        
        JPanel panelBotoes = new JPanel(new FlowLayout());
        panelBotoes.add(btnCancelar);
        panelBotoes.add(btnSalvar);
        
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(panelBotoes, BorderLayout.SOUTH);
        
        dialog.setVisible(true);
    }
    
    // MAIN para teste
    public static void main(String[] args) {
        System.out.println("🚀 ===== INICIANDO SISTEMA DE LANCHONETE ===== 🚀");
        
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            LoginView login = new LoginView();
            System.out.println("🎯 Sistema iniciado com sucesso!");
            
            // Verificação
            System.out.println("\n=== STATUS DO SISTEMA ===");
            System.out.println("Botão Login: " + (login.btnLogin != null ? "✅ OK" : "❌ FALHO"));
            System.out.println("Botão Cadastrar: " + (login.btnCadastrar != null ? "✅ OK" : "❌ FALHO"));
        });
    }
}