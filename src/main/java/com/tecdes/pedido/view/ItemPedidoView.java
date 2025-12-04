package com.tecdes.pedido.view;

import com.tecdes.pedido.controller.ItemPedidoController;
import com.tecdes.pedido.controller.PedidoController;
import com.tecdes.pedido.controller.ProdutoController;
import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.model.entity.Pedido;
import javax.swing.*;
import java.awt.*;

public class ItemPedidoView extends JFrame {
    
    private JTextField txtPedidoId, txtProdutoId, txtQtd;
    private JTextArea txtObs;
    private JButton btnBuscarProduto, btnCalcular, btnSalvar, btnLimpar;
    
    private final ItemPedidoController itemPedidoController;
    private final PedidoController pedidoController;
    private final ProdutoController produtoController;
    
    public ItemPedidoView() {
        this.itemPedidoController = new ItemPedidoController();
        this.pedidoController = new PedidoController();
        this.produtoController = new ProdutoController();
        
        configurarJanela();
        criarComponentes();
        setVisible(true);
    }
    
    private void configurarJanela() {
        setTitle("🛒 Adicionar Item ao Pedido");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
    }
    
    private void criarComponentes() {
        // TÍTULO
        JLabel lblTitulo = new JLabel("ADICIONAR ITEM AO PEDIDO");
        lblTitulo.setBounds(100, 10, 300, 30);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo);
        
        // PAINEL DE DADOS
        JPanel painelDados = new JPanel();
        painelDados.setLayout(null);
        painelDados.setBounds(20, 50, 450, 250);
        painelDados.setBorder(BorderFactory.createTitledBorder("Dados do Item"));
        
        // ID do Pedido
        JLabel lblPedidoId = new JLabel("ID do Pedido:");
        lblPedidoId.setBounds(10, 30, 120, 25);
        painelDados.add(lblPedidoId);
        
        txtPedidoId = new JTextField();
        txtPedidoId.setBounds(140, 30, 150, 25);
        painelDados.add(txtPedidoId);
        
        // ID do Produto
        JLabel lblProdutoId = new JLabel("ID do Produto:");
        lblProdutoId.setBounds(10, 70, 120, 25);
        painelDados.add(lblProdutoId);
        
        txtProdutoId = new JTextField();
        txtProdutoId.setBounds(140, 70, 150, 25);
        painelDados.add(txtProdutoId);
        
        btnBuscarProduto = new JButton("🔍 Buscar");
        btnBuscarProduto.setBounds(300, 70, 80, 25);
        btnBuscarProduto.addActionListener(e -> buscarProduto());
        painelDados.add(btnBuscarProduto);
        
        // Quantidade
        JLabel lblQtd = new JLabel("Quantidade:");
        lblQtd.setBounds(10, 110, 120, 25);
        painelDados.add(lblQtd);
        
        txtQtd = new JTextField();
        txtQtd.setBounds(140, 110, 150, 25);
        txtQtd.setText("1");
        painelDados.add(txtQtd);
        
        btnCalcular = new JButton("💰 Calcular");
        btnCalcular.setBounds(300, 110, 80, 25);
        btnCalcular.addActionListener(e -> calcularValor());
        painelDados.add(btnCalcular);
        
        // Observações
        JLabel lblObs = new JLabel("Observações:");
        lblObs.setBounds(10, 150, 120, 25);
        painelDados.add(lblObs);
        
        txtObs = new JTextArea();
        txtObs.setLineWrap(true);
        JScrollPane scrollObs = new JScrollPane(txtObs);
        scrollObs.setBounds(140, 150, 240, 80);
        painelDados.add(scrollObs);
        
        add(painelDados);
        
        // BOTÕES PRINCIPAIS
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoes.setBounds(20, 310, 450, 80);
        
        btnSalvar = new JButton("💾 Adicionar Item");
        btnSalvar.setBackground(new Color(46, 125, 50));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.addActionListener(e -> adicionarItem());
        painelBotoes.add(btnSalvar);
        
        btnLimpar = new JButton("🧹 Limpar");
        btnLimpar.setBackground(new Color(108, 117, 125));
        btnLimpar.setForeground(Color.WHITE);
        btnLimpar.addActionListener(e -> limparCampos());
        painelBotoes.add(btnLimpar);
        
        JButton btnVoltar = new JButton("⬅️ Voltar");
        btnVoltar.addActionListener(e -> dispose());
        painelBotoes.add(btnVoltar);
        
        add(painelBotoes);
    }
    
    private void buscarProduto() {
        try {
            String idStr = txtProdutoId.getText().trim();
            if (idStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite o ID do produto!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int idProduto = Integer.parseInt(idStr);
            Produto produto = produtoController.buscarPorId(idProduto);
            
            if (produto != null) {
                JOptionPane.showMessageDialog(this,
                    "✅ Produto encontrado!\n\n" +
                    "Nome: " + produto.getNmProduto() + "\n" +
                    "Tipo: " + produto.getTpProduto() + "\n" +
                    "Valor: R$ " + String.format("%.2f", produto.getVlProduto().doubleValue()) + "\n" +
                    "Descrição: " + produto.getDsProduto(),
                    "Informações do Produto",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "❌ Produto não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID do produto deve ser um número!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void calcularValor() {
        try {
            String idProdutoStr = txtProdutoId.getText().trim();
            String qtdStr = txtQtd.getText().trim();
            
            if (idProdutoStr.isEmpty() || qtdStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha ID do produto e quantidade!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int idProduto = Integer.parseInt(idProdutoStr);
            int quantidade = Integer.parseInt(qtdStr);
            
            Produto produto = produtoController.buscarPorId(idProduto);
            if (produto == null) {
                JOptionPane.showMessageDialog(this, "Produto não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double preco = produto.getVlProduto().doubleValue();
            double total = preco * quantidade;
            
            JOptionPane.showMessageDialog(this,
                "💰 Cálculo do Item:\n\n" +
                "Produto: " + produto.getNmProduto() + "\n" +
                "Quantidade: " + quantidade + "\n" +
                "Preço Unitário: R$ " + String.format("%.2f", preco) + "\n" +
                "Total do Item: R$ " + String.format("%.2f", total),
                "Cálculo",
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade deve ser um número!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void adicionarItem() {
        try {
            // Validações
            String pedidoIdStr = txtPedidoId.getText().trim();
            String produtoIdStr = txtProdutoId.getText().trim();
            String qtdStr = txtQtd.getText().trim();
            
            if (pedidoIdStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID do Pedido é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (produtoIdStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID do Produto é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (qtdStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Quantidade é obrigatória!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int idPedido = Integer.parseInt(pedidoIdStr);
            int idProduto = Integer.parseInt(produtoIdStr);
            int quantidade = Integer.parseInt(qtdStr);
            
            // Verificar se pedido existe
            Pedido pedido = pedidoController.buscarPorId(idPedido);
            if (pedido == null) {
                JOptionPane.showMessageDialog(this, "Pedido não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar se produto existe
            Produto produto = produtoController.buscarPorId(idProduto);
            if (produto == null) {
                JOptionPane.showMessageDialog(this, "Produto não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Adicionar item ao pedido
            itemPedidoController.adicionarItem(idPedido, idProduto, quantidade);
            
            JOptionPane.showMessageDialog(this,
                "✅ Item adicionado com sucesso!\n\n" +
                "Pedido: #" + pedido.getNrPedido() + "\n" +
                "Produto: " + produto.getNmProduto() + "\n" +
                "Quantidade: " + quantidade + "\n" +
                "Total: R$ " + String.format("%.2f", produto.getVlProduto().doubleValue() * quantidade),
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
            
            limparCampos();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "IDs e quantidade devem ser números!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limparCampos() {
        txtPedidoId.setText("");
        txtProdutoId.setText("");
        txtQtd.setText("1");
        txtObs.setText("");
    }
}