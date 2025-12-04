package com.tecdes.pedido.view;

import com.tecdes.pedido.controller.ProdutoController;
import com.tecdes.pedido.model.entity.Produto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
//import java.math.BigDecimal;
import java.util.List;

public class ProdutoView extends JFrame {
    private ProdutoController controller;
    private JTable tabelaProdutos;
    private DefaultTableModel tableModel;
    
    private JTextField txtNome, txtDescricao, txtValor;
    private JComboBox<String> cbxTipo;
    private JButton btnSalvar, btnEditar, btnExcluir, btnLimpar;
    
    public ProdutoView() {
        controller = new ProdutoController();
        configurarJanela();
        criarComponentes();
        carregarProdutos();
    }
    
    private void configurarJanela() {
        setTitle("🍟 Gestão de Produtos");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }
    
    private void criarComponentes() {
        // Painel superior (formulário)
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Dados do Produto"));
        
        panelForm.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        panelForm.add(txtNome);
        
        panelForm.add(new JLabel("Tipo:"));
        String[] tipos = {"L (Lanche)", "B (Bebida)", "C (Complemento)"};
        cbxTipo = new JComboBox<>(tipos);
        panelForm.add(cbxTipo);
        
        panelForm.add(new JLabel("Descrição:"));
        txtDescricao = new JTextField();
        panelForm.add(txtDescricao);
        
        panelForm.add(new JLabel("Valor R$:"));
        txtValor = new JTextField();
        panelForm.add(txtValor);
        
        panelForm.add(new JLabel()); // Espaço vazio
        JLabel lblExemplo = new JLabel("Ex: 15.90");
        lblExemplo.setForeground(Color.GRAY);
        panelForm.add(lblExemplo);
        
        add(panelForm, BorderLayout.NORTH);
        
        // Painel central (tabela)
        String[] colunas = {"ID", "Nome", "Tipo", "Descrição", "Valor R$"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabelaProdutos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaProdutos);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Cardápio"));
        add(scrollPane, BorderLayout.CENTER);
        
        // Painel inferior (botões)
        JPanel panelBotoes = new JPanel(new FlowLayout());
        
        btnSalvar = criarBotao("💾 Salvar", new Color(46, 125, 50));
        btnEditar = criarBotao("✏️ Editar", new Color(30, 144, 255));
        btnExcluir = criarBotao("🗑️ Excluir", new Color(220, 53, 69));
        btnLimpar = criarBotao("🧹 Limpar", new Color(108, 117, 125));
        
        panelBotoes.add(btnSalvar);
        panelBotoes.add(btnEditar);
        panelBotoes.add(btnExcluir);
        panelBotoes.add(btnLimpar);
        
        add(panelBotoes, BorderLayout.SOUTH);
        
        configurarEventos();
        tabelaProdutos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) preencherFormulario();
        });
    }
    
    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }
    
    private void configurarEventos() {
        btnSalvar.addActionListener(e -> salvarProduto());
        btnEditar.addActionListener(e -> editarProduto());
        btnExcluir.addActionListener(e -> excluirProduto());
        btnLimpar.addActionListener(e -> limparFormulario());
    }
    
    private void carregarProdutos() {
        tableModel.setRowCount(0);
        List<Produto> produtos = controller.buscarTodos();
        
        for (Produto p : produtos) {
            tableModel.addRow(new Object[]{
                p.getIdProduto(),
                p.getNmProduto(),
                p.getTpProduto(),
                p.getDsProduto(),
                String.format("R$ %.2f", p.getVlProduto().doubleValue())
            });
        }
    }
    
    private void salvarProduto() {
        try {
            String nome = txtNome.getText();
            char tipo = ((String) cbxTipo.getSelectedItem()).charAt(0);
            String descricao = txtDescricao.getText();
            double valor = Double.parseDouble(txtValor.getText().replace(",", "."));
            
            if (!validarCampos()) return;
            
            controller.salvar(nome, tipo, descricao, valor);
            JOptionPane.showMessageDialog(this, "Produto salvo!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            limparFormulario();
            carregarProdutos();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void editarProduto() {
        int linha = tabelaProdutos.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int id = (int) tableModel.getValueAt(linha, 0);
            String nome = txtNome.getText();
            char tipo = ((String) cbxTipo.getSelectedItem()).charAt(0);
            String descricao = txtDescricao.getText();
            double valor = Double.parseDouble(txtValor.getText().replace(",", "."));
            
            if (!validarCampos()) return;
            
            controller.atualizar(id, nome, tipo, descricao, valor);
            JOptionPane.showMessageDialog(this, "Produto atualizado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            carregarProdutos();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void excluirProduto() {
        int linha = tabelaProdutos.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Excluir este produto?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = (int) tableModel.getValueAt(linha, 0);
                controller.excluir(id);
                
                JOptionPane.showMessageDialog(this, "Produto excluído!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
                limparFormulario();
                carregarProdutos();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private boolean validarCampos() {
        if (txtNome.getText().isEmpty() || txtDescricao.getText().isEmpty() || txtValor.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        try {
            double valor = Double.parseDouble(txtValor.getText().replace(",", "."));
            if (valor <= 0) {
                JOptionPane.showMessageDialog(this, "Valor deve ser maior que zero!", "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void preencherFormulario() {
        int linha = tabelaProdutos.getSelectedRow();
        if (linha != -1) {
            txtNome.setText(tableModel.getValueAt(linha, 1).toString());
            
            String tipoStr = tableModel.getValueAt(linha, 2).toString();
            for (int i = 0; i < cbxTipo.getItemCount(); i++) {
                if (cbxTipo.getItemAt(i).startsWith(tipoStr)) {
                    cbxTipo.setSelectedIndex(i);
                    break;
                }
            }
            
            txtDescricao.setText(tableModel.getValueAt(linha, 3).toString());
            
            String valorStr = tableModel.getValueAt(linha, 4).toString();
            valorStr = valorStr.replace("R$ ", "").trim();
            txtValor.setText(valorStr);
        }
    }
    
    private void limparFormulario() {
        txtNome.setText("");
        txtDescricao.setText("");
        txtValor.setText("");
        cbxTipo.setSelectedIndex(0);
        tabelaProdutos.clearSelection();
    }
}