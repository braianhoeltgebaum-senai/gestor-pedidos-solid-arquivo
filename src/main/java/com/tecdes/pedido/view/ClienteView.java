package com.tecdes.pedido.view;

import com.tecdes.pedido.controller.ClienteController;
import com.tecdes.pedido.model.entity.Cliente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClienteView extends JFrame {
    private ClienteController controller;
    private JTable tabelaClientes;
    private DefaultTableModel tableModel;
    
    private JTextField txtNome, txtCadastro, txtEmail, txtTelefone;
    private JButton btnSalvar, btnEditar, btnExcluir, btnLimpar;
    
    // CONSTRUTOR PADRÃO (sem parâmetros)
    public ClienteView() {
        init();
    }
    
    // CONSTRUTOR PARA USO DO LoginView (com parent e modal)
    public ClienteView(JFrame parent, boolean modal) {
        init();
        setLocationRelativeTo(parent); // Centraliza em relação à janela pai
        
        if (modal) {
            // Torna a janela modal (bloqueia a janela pai)
            setAlwaysOnTop(true);
            setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        }
    }
    
    // CONSTRUTOR PARA USO DO MainMenuView (sem parent)
    public ClienteView(boolean modal) {
        init();
        if (modal) {
            setAlwaysOnTop(true);
            setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        }
    }
    
    private void init() {
        controller = new ClienteController();
        configurarJanela();
        criarComponentes();
        carregarClientes();
    }
    
    private void configurarJanela() {
        setTitle("🍔 Gestão de Clientes");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Ícone da janela
        try {
            setIconImage(new ImageIcon(getClass().getResource("/icons/clientes.png")).getImage());
        } catch (Exception e) {
            // Ignora se não tiver ícone
        }
    }
    
    private void criarComponentes() {
        // Painel superior (formulário)
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(46, 125, 50), 2),
            "📝 Dados do Cliente"
        ));
        panelForm.setBackground(new Color(240, 240, 240));
        
        panelForm.add(new JLabel("👤 Nome:"));
        txtNome = new JTextField();
        panelForm.add(txtNome);
        
        panelForm.add(new JLabel("🔢 Nº Cadastro (3 dígitos):"));
        txtCadastro = new JTextField();
        panelForm.add(txtCadastro);
        
        panelForm.add(new JLabel("📧 Email:"));
        txtEmail = new JTextField();
        panelForm.add(txtEmail);
        
        panelForm.add(new JLabel("📞 Telefone:"));
        txtTelefone = new JTextField();
        panelForm.add(txtTelefone);
        
        // Botão de busca rápida
        panelForm.add(new JLabel("🔍 Buscar por Nome:"));
        JTextField txtBusca = new JTextField();
        txtBusca.addActionListener(e -> buscarCliente(txtBusca.getText()));
        panelForm.add(txtBusca);
        
        add(panelForm, BorderLayout.NORTH);
        
        // Painel central (tabela)
        String[] colunas = {"ID", "Nome", "Cadastro", "Email", "Telefone"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabela não editável diretamente
            }
        };
        
        tabelaClientes = new JTable(tableModel);
        tabelaClientes.setRowHeight(30);
        tabelaClientes.setSelectionBackground(new Color(220, 240, 255));
        tabelaClientes.setSelectionForeground(Color.BLACK);
        
        // Centralizar conteúdo das colunas
        tabelaClientes.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        tabelaClientes.getColumnModel().getColumn(1).setPreferredWidth(200); // Nome
        tabelaClientes.getColumnModel().getColumn(2).setPreferredWidth(80);  // Cadastro
        tabelaClientes.getColumnModel().getColumn(3).setPreferredWidth(200); // Email
        tabelaClientes.getColumnModel().getColumn(4).setPreferredWidth(120); // Telefone
        
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(30, 144, 255), 2),
            "👥 Clientes Cadastrados"
        ));
        add(scrollPane, BorderLayout.CENTER);
        
        // Painel inferior (botões)
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotoes.setBackground(new Color(240, 240, 240));
        panelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        btnSalvar = criarBotao("💾 Salvar", new Color(46, 125, 50));
        btnEditar = criarBotao("✏️ Editar", new Color(30, 144, 255));
        btnExcluir = criarBotao("🗑️ Excluir", new Color(220, 53, 69));
        btnLimpar = criarBotao("🧹 Limpar", new Color(108, 117, 125));
        
        JButton btnAtualizar = criarBotao("🔄 Atualizar", new Color(255, 193, 7));
        btnAtualizar.addActionListener(e -> carregarClientes());
        
        JButton btnFechar = criarBotao("❌ Fechar", new Color(108, 117, 125));
        btnFechar.addActionListener(e -> dispose());
        
        panelBotoes.add(btnSalvar);
        panelBotoes.add(btnEditar);
        panelBotoes.add(btnExcluir);
        panelBotoes.add(btnLimpar);
        panelBotoes.add(btnAtualizar);
        panelBotoes.add(btnFechar);
        
        add(panelBotoes, BorderLayout.SOUTH);
        
        // Configurar eventos
        configurarEventos();
        
        // Seleção na tabela preenche formulário
        tabelaClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherFormulario();
            }
        });
    }
    
    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(120, 35));
        
        // Efeito hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(cor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(cor);
            }
        });
        
        return btn;
    }
    
    private void configurarEventos() {
        btnSalvar.addActionListener(e -> salvarCliente());
        btnEditar.addActionListener(e -> editarCliente());
        btnExcluir.addActionListener(e -> excluirCliente());
        btnLimpar.addActionListener(e -> limparFormulario());
    }
    
    private void carregarClientes() {
        tableModel.setRowCount(0); // Limpar tabela
        List<Cliente> clientes = controller.listarTodos();
        
        for (Cliente c : clientes) {
            tableModel.addRow(new Object[]{
                c.getIdCliente(),
                c.getNmCliente(),
                c.getNrCadastro(),
                c.getDsEmail(),
                c.getNrTelefone()
            });
        }
        
        // Mostrar contagem
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Nenhum cliente cadastrado.\nClique em 'Salvar' para adicionar um novo cliente.",
                "Sem Clientes",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void buscarCliente(String nomeBusca) {
        if (nomeBusca.trim().isEmpty()) {
            carregarClientes();
            return;
        }
        
        tableModel.setRowCount(0);
        List<Cliente> clientes = controller.listarTodos();
        
        for (Cliente c : clientes) {
            if (c.getNmCliente().toLowerCase().contains(nomeBusca.toLowerCase())) {
                tableModel.addRow(new Object[]{
                    c.getIdCliente(),
                    c.getNmCliente(),
                    c.getNrCadastro(),
                    c.getDsEmail(),
                    c.getNrTelefone()
                });
            }
        }
    }
    
    private void salvarCliente() {
        try {
            String nome = txtNome.getText().trim();
            String cadastro = txtCadastro.getText().trim();
            String email = txtEmail.getText().trim();
            String telefone = txtTelefone.getText().trim();
            
            // Validar campos
            if (nome.isEmpty() || cadastro.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "❌ Preencha todos os campos!", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Validar número de cadastro (3 dígitos)
            if (!cadastro.matches("\\d{3}")) {
                JOptionPane.showMessageDialog(this,
                    "❌ Número de cadastro deve ter exatamente 3 dígitos!\nExemplo: 123",
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar email básico
            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(this,
                    "⚠️ Email inválido! Use um email válido.\nExemplo: cliente@exemplo.com",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                // Não impede o cadastro, apenas avisa
            }
            
            // Validar telefone
            if (!telefone.matches("\\d{10,11}")) {
                JOptionPane.showMessageDialog(this,
                    "⚠️ Telefone deve ter 10 ou 11 dígitos!\nExemplo: 11987654321",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
            
            // Verificar se cadastro já existe
            if (controller.cadastroExiste(cadastro)) {
                JOptionPane.showMessageDialog(this,
                    "❌ Cadastro " + cadastro + " já está em uso!\nUse outro número de cadastro.",
                    "Cadastro Duplicado",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Salvar cliente
            controller.salvar(nome, cadastro, email, telefone);
            
            JOptionPane.showMessageDialog(this, 
                "✅ Cliente salvo com sucesso!\n\n" +
                "Nome: " + nome + "\n" +
                "Cadastro: " + cadastro + "\n" +
                "Email: " + email + "\n" +
                "Telefone: " + telefone,
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            limparFormulario();
            carregarClientes();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "❌ Erro ao salvar cliente: " + ex.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void editarCliente() {
        int linhaSelecionada = tabelaClientes.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this,
                "❌ Selecione um cliente para editar!",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int id = (int) tableModel.getValueAt(linhaSelecionada, 0);
            String nomeAntigo = tableModel.getValueAt(linhaSelecionada, 1).toString();
            String cadastroAntigo = tableModel.getValueAt(linhaSelecionada, 2).toString();
            
            String nome = txtNome.getText().trim();
            String cadastro = txtCadastro.getText().trim();
            String email = txtEmail.getText().trim();
            String telefone = txtTelefone.getText().trim();
            
            // Verificar se houve alteração no cadastro
            if (!cadastro.equals(cadastroAntigo) && controller.cadastroExiste(cadastro)) {
                JOptionPane.showMessageDialog(this,
                    "❌ Cadastro " + cadastro + " já está em uso!\nUse outro número de cadastro.",
                    "Cadastro Duplicado",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            controller.atualizar(id, nome, cadastro, email, telefone);
            
            JOptionPane.showMessageDialog(this,
                "✅ Cliente atualizado com sucesso!\n\n" +
                "Nome: " + nomeAntigo + " → " + nome + "\n" +
                "Cadastro: " + cadastroAntigo + " → " + cadastro,
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            carregarClientes();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "❌ Erro ao atualizar cliente: " + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void excluirCliente() {
        int linhaSelecionada = tabelaClientes.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this,
                "❌ Selecione um cliente para excluir!",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (int) tableModel.getValueAt(linhaSelecionada, 0);
        String nome = tableModel.getValueAt(linhaSelecionada, 1).toString();
        String cadastro = tableModel.getValueAt(linhaSelecionada, 2).toString();
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "⚠️ TEM CERTEZA QUE DESEJA EXCLUIR ESTE CLIENTE?\n\n" +
            "Nome: " + nome + "\n" +
            "Cadastro: " + cadastro + "\n\n" +
            "Esta ação NÃO pode ser desfeita!",
            "⚠️ CONFIRMAR EXCLUSÃO ⚠️",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                controller.excluir(id);
                
                JOptionPane.showMessageDialog(this,
                    "✅ Cliente excluído com sucesso!\n\n" +
                    "Nome: " + nome + "\n" +
                    "Cadastro: " + cadastro,
                    "Exclusão Concluída",
                    JOptionPane.INFORMATION_MESSAGE);
                
                limparFormulario();
                carregarClientes();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "❌ Erro ao excluir cliente: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void preencherFormulario() {
        int linhaSelecionada = tabelaClientes.getSelectedRow();
        if (linhaSelecionada != -1) {
            txtNome.setText(tableModel.getValueAt(linhaSelecionada, 1).toString());
            txtCadastro.setText(tableModel.getValueAt(linhaSelecionada, 2).toString());
            txtEmail.setText(tableModel.getValueAt(linhaSelecionada, 3).toString());
            txtTelefone.setText(tableModel.getValueAt(linhaSelecionada, 4).toString());
        }
    }
    
    private void limparFormulario() {
        txtNome.setText("");
        txtCadastro.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        tabelaClientes.clearSelection();
    }
    
    // Método estático para facilitar abertura
    public static void mostrarTela() {
        SwingUtilities.invokeLater(() -> {
            ClienteView view = new ClienteView();
            view.setVisible(true);
        });
    }
    
    // Método estático para abrir como modal
    public static void mostrarTelaModal(JFrame parent) {
        SwingUtilities.invokeLater(() -> {
            ClienteView view = new ClienteView(parent, true);
            view.setVisible(true);
        });
    }
}