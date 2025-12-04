package com.tecdes.pedido.view;

import com.tecdes.pedido.controller.ClienteController;
import com.tecdes.pedido.controller.FuncionarioController;
import com.tecdes.pedido.model.entity.Cliente;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JComboBox<String> cbxTipoLogin;
    private JButton btnLogin;
    private JButton btnCadastrar;
    private ClienteController clienteController;
    private FuncionarioController funcionarioController;
    
    public LoginView() {
        inicializarControllers();
        configurarJanela();
        criarComponentes();
        configurarEventos();
    }
    
    private void inicializarControllers() {
        clienteController = new ClienteController();
        funcionarioController = new FuncionarioController();
    }
    
    private void configurarJanela() {
        setTitle("Sistema de Lanchonete - Login");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centralizar
        setResizable(false);
        
        // Layout
        setLayout(new BorderLayout());
        
        // Painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        add(panel, BorderLayout.CENTER);
    }
    
    private void criarComponentes() {
        JPanel panel = (JPanel) getContentPane().getComponent(0);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Título
        JLabel lblTitulo = new JLabel("🍔 Lanchonete Delícia");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);
        
        // Subtítulo
        JLabel lblSubtitulo = new JLabel("Sistema de Gestão de Pedidos");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        panel.add(lblSubtitulo, gbc);
        
        // Espaço
        gbc.gridy = 2;
        panel.add(new JLabel(" "), gbc);
        
        // Tipo de Login
        JLabel lblTipoLogin = new JLabel("Tipo de Login:");
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblTipoLogin, gbc);
        
        String[] tipos = {"Cliente", "Funcionário", "Gerente"};
        cbxTipoLogin = new JComboBox<>(tipos);
        gbc.gridx = 1;
        panel.add(cbxTipoLogin, gbc);
        
        // Email/CPF
        JLabel lblEmail = new JLabel("Email/CPF:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(lblEmail, gbc);
        
        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtEmail, gbc);
        
        // Senha/Cadastro
        JLabel lblSenha = new JLabel("Senha/Cadastro:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(lblSenha, gbc);
        
        txtSenha = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(txtSenha, gbc);
        
        // Botões
        JPanel panelBotoes = new JPanel(new FlowLayout());
        
        btnLogin = new JButton("Entrar");
        btnLogin.setBackground(new Color(70, 130, 180));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setPreferredSize(new Dimension(120, 35));
        
        btnCadastrar = new JButton("Cadastrar Cliente");
        btnCadastrar.setBackground(new Color(46, 125, 50));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setPreferredSize(new Dimension(150, 35));
        
        panelBotoes.add(btnLogin);
        panelBotoes.add(btnCadastrar);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(panelBotoes, gbc);
        
        // Rodapé
        JLabel lblRodape = new JLabel("© 2025 - TecDes - Sistema de Lanchonete");
        lblRodape.setFont(new Font("Arial", Font.PLAIN, 10));
        lblRodape.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 7;
        panel.add(lblRodape, gbc);
    }
    
    private void configurarEventos() {
        // Botão Login
        btnLogin.addActionListener(e -> fazerLogin());
        
        // Botão Cadastrar
        btnCadastrar.addActionListener(e -> abrirCadastroCliente());
        
        // Enter no campo de senha também faz login
        txtSenha.addActionListener(e -> fazerLogin());
    }
    
    private void fazerLogin() {
        String tipo = (String) cbxTipoLogin.getSelectedItem();
        String usuario = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();
        
        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Preencha todos os campos!", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            boolean sucesso = false;
            String mensagem = "";
            
            switch (tipo) {
                case "Cliente":
                    // Cliente usa email + número de cadastro (3 dígitos)
                    System.out.println("🔐 Tentando login como CLIENTE: Email=" + usuario + ", Cadastro=" + senha);
                    sucesso = loginCliente(usuario, senha);
                    mensagem = sucesso ? "Cliente autenticado" : "Falha na autenticação do cliente";
                    break;
                    
                case "Funcionário":
                case "Gerente":
                    // Funcionário/Gerente usa CPF + senha
                    System.out.println("🔐 Tentando login como FUNCIONÁRIO: CPF=" + usuario + ", Senha=" + senha);
                    sucesso = loginFuncionario(usuario, senha);
                    mensagem = sucesso ? "Funcionário autenticado" : "Falha na autenticação do funcionário";
                    break;
            }
            
            System.out.println("✅ " + mensagem);
            
            if (sucesso) {
                JOptionPane.showMessageDialog(this, 
                    "Login realizado com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
                    
                // Abrir menu principal
                abrirMenuPrincipal(tipo);
                
                // Fechar tela de login
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Login falhou! Verifique suas credenciais.\n" +
                    "Para cliente: use email + número de cadastro (3 dígitos)\n" +
                    "Para funcionário: use CPF + senha", 
                    "Erro no Login", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception ex) {
            System.err.println("❌ Erro no login: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Erro no login: " + ex.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean loginCliente(String email, String cadastro) {
    try {
        System.out.println("\n=== TENTATIVA DE LOGIN CLIENTE ===");
        System.out.println("📧 Email: " + email);
        System.out.println("🔢 Cadastro: " + cadastro);
        
        // Primeiro valida os dados
        if (!clienteController.validarLogin(email, cadastro)) {
            System.out.println("❌ Validação inicial falhou");
            return false;
        }
        
        System.out.println("✅ Validação inicial OK");
        
        // Chama o login (retorna boolean)
        boolean sucesso = clienteController.login(email, cadastro);
        
        if (sucesso) {
            // Obtém o cliente autenticado
            Cliente cliente = clienteController.getClienteLogado();
            if (cliente != null) {
                System.out.println("✅ Cliente autenticado com sucesso!");
                System.out.println("👤 Nome: " + cliente.getNmCliente());
                System.out.println("📧 Email: " + cliente.getDsEmail());
                System.out.println("🔢 Cadastro: " + cliente.getNrCadastro());
                return true;
            } else {
                System.out.println("⚠️ Login retornou true mas cliente é null");
                return false;
            }
        } else {
            System.out.println("❌ Login falhou - credenciais incorretas");
            return false;
        }
        
    } catch (Exception e) {
        System.err.println("💥 Erro no login do cliente: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}
    
    private boolean loginFuncionario(String cpf, String senha) {
        try {
            boolean sucesso = funcionarioController.login(cpf, senha);
            System.out.println("Login funcionário: " + (sucesso ? "✅ Sucesso" : "❌ Falha"));
            return sucesso;
        } catch (Exception e) {
            System.err.println("❌ Erro no login do funcionário: " + e.getMessage());
            return false;
        }
    }
    
    private void abrirCadastroCliente() {
        SwingUtilities.invokeLater(() -> {
            ClienteView clienteView = new ClienteView();
            clienteView.setVisible(true);
        });
    }
    
    private void abrirMenuPrincipal(String tipoUsuario) {
        SwingUtilities.invokeLater(() -> {
            MainMenuView menuView = new MainMenuView(tipoUsuario);
            menuView.setVisible(true);
        });
    }
}