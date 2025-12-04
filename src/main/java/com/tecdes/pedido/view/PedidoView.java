package com.tecdes.pedido.view;

import com.tecdes.pedido.controller.PedidoController;
import com.tecdes.pedido.controller.ProdutoController;
import com.tecdes.pedido.controller.ClienteController;
//import com.tecdes.pedido.controller.ItemPedidoController;
import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.model.entity.Cliente;
//import com.tecdes.pedido.model.entity.ItemPedido;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PedidoView extends JFrame {
    private PedidoController pedidoController;
    private ProdutoController produtoController;
    private ClienteController clienteController;
    //private ItemPedidoController itemController;
    
    private JComboBox<Cliente> cbxClientes;
    private JComboBox<Produto> cbxProdutos;
    private JSpinner spnQuantidade;
    private JTable tabelaItens;
    private DefaultTableModel tableModelItens;
    private JLabel lblTotal;
    
    private JButton btnAdicionarItem, btnRemoverItem, btnFinalizarPedido, btnLimpar;
    
    public PedidoView() {
        pedidoController = new PedidoController();
        produtoController = new ProdutoController();
        clienteController = new ClienteController();
     //   itemController = new ItemPedidoController();
        
        configurarJanela();
        criarComponentes();
        carregarDados();
    }
    
    private void configurarJanela() {
        setTitle("🛒 Novo Pedido");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }
    
    private void criarComponentes() {
        // Painel esquerdo (seleção de cliente e produtos)
        JPanel panelEsquerdo = new JPanel(new BorderLayout());
        panelEsquerdo.setBorder(BorderFactory.createTitledBorder("Seleção"));
        
        // Seleção de Cliente
        JPanel panelCliente = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCliente.add(new JLabel("Cliente:"));
        cbxClientes = new JComboBox<>();
        cbxClientes.setPreferredSize(new Dimension(300, 25));
        panelCliente.add(cbxClientes);
        
        // Seleção de Produto e Quantidade
        JPanel panelProduto = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelProduto.add(new JLabel("Produto:"));
        cbxProdutos = new JComboBox<>();
        cbxProdutos.setPreferredSize(new Dimension(250, 25));
        panelProduto.add(cbxProdutos);
        
        panelProduto.add(new JLabel("Quantidade:"));
        spnQuantidade = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        panelProduto.add(spnQuantidade);
        
        btnAdicionarItem = criarBotao("➕ Adicionar", new Color(46, 125, 50));
        panelProduto.add(btnAdicionarItem);
        
        // Adicionar ao painel esquerdo
        JPanel panelSuperior = new JPanel(new GridLayout(2, 1, 5, 5));
        panelSuperior.add(panelCliente);
        panelSuperior.add(panelProduto);
        
        panelEsquerdo.add(panelSuperior, BorderLayout.NORTH);
        
        // Lista de produtos disponíveis (cardápio)
        String[] colunasCardapio = {"ID", "Nome", "Tipo", "Preço"};
        DefaultTableModel modelCardapio = new DefaultTableModel(colunasCardapio, 0);
        JTable tabelaCardapio = new JTable(modelCardapio);
        JScrollPane scrollCardapio = new JScrollPane(tabelaCardapio);
        scrollCardapio.setBorder(BorderFactory.createTitledBorder("Cardápio"));
        panelEsquerdo.add(scrollCardapio, BorderLayout.CENTER);
        
        // Carregar cardápio
        List<Produto> produtos = produtoController.buscarTodos();
        for (Produto p : produtos) {
            modelCardapio.addRow(new Object[]{
                p.getIdProduto(),
                p.getNmProduto(),
                p.getTpProduto(),
                String.format("R$ %.2f", p.getVlProduto().doubleValue())
            });
        }
        
        // Painel direito (itens do pedido)
        JPanel panelDireito = new JPanel(new BorderLayout());
        
        // Tabela de itens do pedido
        String[] colunasItens = {"ID", "Produto", "Qtd", "Preço Unit.", "Subtotal"};
        tableModelItens = new DefaultTableModel(colunasItens, 0);
        tabelaItens = new JTable(tableModelItens);
        JScrollPane scrollItens = new JScrollPane(tabelaItens);
        scrollItens.setBorder(BorderFactory.createTitledBorder("Itens do Pedido"));
        panelDireito.add(scrollItens, BorderLayout.CENTER);
        
        // Painel inferior (total e botões)
        JPanel panelInferior = new JPanel(new BorderLayout());
        
        JPanel panelTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelTotal.add(new JLabel("Total do Pedido:"));
        lblTotal = new JLabel("R$ 0,00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotal.setForeground(new Color(0, 100, 0));
        panelTotal.add(lblTotal);
        
        JPanel panelBotoes = new JPanel(new FlowLayout());
        btnRemoverItem = criarBotao("➖ Remover Item", new Color(220, 53, 69));
        btnFinalizarPedido = criarBotao("✅ Finalizar Pedido", new Color(40, 167, 69));
        btnLimpar = criarBotao("🧹 Limpar", new Color(108, 117, 125));
        
        panelBotoes.add(btnRemoverItem);
        panelBotoes.add(btnFinalizarPedido);
        panelBotoes.add(btnLimpar);
        
        panelInferior.add(panelTotal, BorderLayout.NORTH);
        panelInferior.add(panelBotoes, BorderLayout.SOUTH);
        
        panelDireito.add(panelInferior, BorderLayout.SOUTH);
        
        // Adicionar painéis à janela principal
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelEsquerdo, panelDireito);
        splitPane.setDividerLocation(400);
        add(splitPane, BorderLayout.CENTER);
        
        // Configurar eventos
        configurarEventos();
    }
    
    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(150, 30));
        return btn;
    }
    
    private void configurarEventos() {
        btnAdicionarItem.addActionListener(e -> adicionarItem());
        btnRemoverItem.addActionListener(e -> removerItem());
        btnFinalizarPedido.addActionListener(e -> finalizarPedido());
        btnLimpar.addActionListener(e -> limparPedido());
    }
    
    private void carregarDados() {
        // Carregar clientes
        cbxClientes.removeAllItems();
        List<Cliente> clientes = clienteController.listarTodos();
        for (Cliente c : clientes) {
            cbxClientes.addItem(c);
        }
        
        // Carregar produtos
        cbxProdutos.removeAllItems();
        List<Produto> produtos = produtoController.buscarTodos();
        for (Produto p : produtos) {
            cbxProdutos.addItem(p);
        }
    }
    
    private void adicionarItem() {
        if (cbxProdutos.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione um produto!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Produto produto = (Produto) cbxProdutos.getSelectedItem();
        int quantidade = (int) spnQuantidade.getValue();
        
        // Adicionar à tabela
        double preco = produto.getVlProduto().doubleValue();
        double subtotal = preco * quantidade;
        
        tableModelItens.addRow(new Object[]{
            produto.getIdProduto(),
            produto.getNmProduto(),
            quantidade,
            String.format("R$ %.2f", preco),
            String.format("R$ %.2f", subtotal)
        });
        
        atualizarTotal();
        
        // Resetar quantidade
        spnQuantidade.setValue(1);
    }
    
    private void removerItem() {
        int linha = tabelaItens.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item para remover!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        tableModelItens.removeRow(linha);
        atualizarTotal();
    }
    
    private void finalizarPedido() {
        if (cbxClientes.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (tableModelItens.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Adicione itens ao pedido!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            Cliente cliente = (Cliente) cbxClientes.getSelectedItem();
            
            // Criar pedido (idEndereco fixo como 1 por enquanto - ajuste conforme seu sistema)
            Pedido pedido = pedidoController.criarPedido(cliente.getIdCliente(), 1);
            
            // Adicionar itens ao pedido
            for (int i = 0; i < tableModelItens.getRowCount(); i++) {
                int idProduto = (int) tableModelItens.getValueAt(i, 0);
                int quantidade = (int) tableModelItens.getValueAt(i, 2);
                pedidoController.adicionarItem(pedido.getIdPedido(), idProduto, quantidade);
            }
            
            JOptionPane.showMessageDialog(this,
                "Pedido #" + pedido.getNrPedido() + " criado com sucesso!\n" +
                "Cliente: " + cliente.getNmCliente() + "\n" +
                "Total: " + lblTotal.getText(),
                "Pedido Criado",
                JOptionPane.INFORMATION_MESSAGE);
            
            limparPedido();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void atualizarTotal() {
        double total = 0.0;
        
        for (int i = 0; i < tableModelItens.getRowCount(); i++) {
            String subtotalStr = tableModelItens.getValueAt(i, 4).toString();
            subtotalStr = subtotalStr.replace("R$ ", "").replace(",", ".").trim();
            total += Double.parseDouble(subtotalStr);
        }
        
        lblTotal.setText(String.format("R$ %.2f", total).replace(".", ","));
    }
    
    private void limparPedido() {
        tableModelItens.setRowCount(0);
        lblTotal.setText("R$ 0,00");
        spnQuantidade.setValue(1);
        if (cbxProdutos.getItemCount() > 0) {
            cbxProdutos.setSelectedIndex(0);
        }
    }
}