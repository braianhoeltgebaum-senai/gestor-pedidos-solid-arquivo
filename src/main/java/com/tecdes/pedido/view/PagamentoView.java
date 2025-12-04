package com.tecdes.pedido.view;

import com.tecdes.pedido.controller.PagamentoController;
import com.tecdes.pedido.controller.PedidoController;
import com.tecdes.pedido.model.entity.Pedido;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class PagamentoView extends JFrame {
    
    private PagamentoController pagamentoController;
    private PedidoController pedidoController;
    private JTable tabelaPedidos;
    private DefaultTableModel tableModel;
    private JComboBox<String> cbxFormaPagamento;
    private JTextField txtValorPago;
    
    public PagamentoView() {
        pagamentoController = new PagamentoController();
        pedidoController = new PedidoController();
        configurarJanela();
        criarComponentes();
        carregarPedidosAbertos();
    }
    
    private void configurarJanela() {
        setTitle("💰 Registro de Pagamentos");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }
    
    private void criarComponentes() {
        // Painel superior (busca)
        JPanel panelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusca.setBorder(BorderFactory.createTitledBorder("Buscar Pedidos"));
        panelBusca.setBackground(new Color(240, 240, 240));
        
        JLabel lblBusca = new JLabel("Número do Pedido:");
        JTextField txtBusca = new JTextField(15);
        JButton btnBuscar = new JButton("🔍 Buscar");
        JButton btnLimpar = new JButton("🧹 Limpar");
        
        btnBuscar.addActionListener(e -> buscarPedido(txtBusca.getText()));
        btnLimpar.addActionListener(e -> {
            txtBusca.setText("");
            carregarPedidosAbertos();
        });
        
        panelBusca.add(lblBusca);
        panelBusca.add(txtBusca);
        panelBusca.add(btnBuscar);
        panelBusca.add(btnLimpar);
        
        add(panelBusca, BorderLayout.NORTH);
        
        // Tabela de pedidos
        String[] colunas = {"ID", "Nº Pedido", "Cliente ID", "Status", "Data", "Valor Calculado"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabela não editável
            }
        };
        
        tabelaPedidos = new JTable(tableModel);
        tabelaPedidos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                atualizarValorPago();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tabelaPedidos);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Pedidos para Pagamento"));
        
        add(scrollPane, BorderLayout.CENTER);
        
        // Painel inferior (pagamento)
        JPanel panelPagamento = new JPanel(new GridBagLayout());
        panelPagamento.setBorder(BorderFactory.createTitledBorder("Registrar Pagamento"));
        panelPagamento.setBackground(new Color(240, 240, 240));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Forma de pagamento
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelPagamento.add(new JLabel("Forma de Pagamento:"), gbc);
        
        gbc.gridx = 1;
        String[] formas = {"Pix", "Credito", "Debito", "Dinheiro"};
        cbxFormaPagamento = new JComboBox<>(formas);
        cbxFormaPagamento.setPreferredSize(new Dimension(150, 25));
        panelPagamento.add(cbxFormaPagamento, gbc);
        
        // Valor do pedido
        gbc.gridx = 2;
        panelPagamento.add(new JLabel("Valor do Pedido:"), gbc);
        
        gbc.gridx = 3;
        JTextField txtValorPedido = new JTextField("R$ 0,00");
        txtValorPedido.setEditable(false);
        txtValorPedido.setBackground(Color.LIGHT_GRAY);
        txtValorPedido.setPreferredSize(new Dimension(120, 25));
        panelPagamento.add(txtValorPedido, gbc);
        
        // Valor pago
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelPagamento.add(new JLabel("Valor Pago:"), gbc);
        
        gbc.gridx = 1;
        txtValorPago = new JTextField();
        txtValorPago.setPreferredSize(new Dimension(150, 25));
        panelPagamento.add(txtValorPago, gbc);
        
        // Botão de calcular troco
        gbc.gridx = 2;
        JButton btnCalcularTroco = new JButton("💰 Calcular Troco");
        btnCalcularTroco.addActionListener(e -> calcularTroco(txtValorPedido.getText(), txtValorPago.getText()));
        panelPagamento.add(btnCalcularTroco, gbc);
        
        // Botão de registrar pagamento
        JButton btnRegistrar = new JButton("✅ Registrar Pagamento");
        btnRegistrar.setBackground(new Color(40, 167, 69));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrar.addActionListener(e -> registrarPagamento(txtValorPedido));
        
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotoes.add(btnRegistrar);
        
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(panelPagamento, BorderLayout.CENTER);
        panelInferior.add(panelBotoes, BorderLayout.SOUTH);
        
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private void carregarPedidosAbertos() {
        tableModel.setRowCount(0);
        
        try {
            // Buscar pedidos com status 'A' (Aberto) ou 'E' (Em preparo)
            List<Pedido> pedidosAbertos = pedidoController.listarPorStatus('A');
            List<Pedido> pedidosEmPreparo = pedidoController.listarPorStatus('E');
            
            // Combinar pedidos abertos e em preparo
            for (Pedido p : pedidosAbertos) {
                adicionarPedidoNaTabela(p);
            }
            
            for (Pedido p : pedidosEmPreparo) {
                adicionarPedidoNaTabela(p);
            }
            
            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,
                    "Não há pedidos aguardando pagamento.",
                    "Informação",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar pedidos: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void adicionarPedidoNaTabela(Pedido pedido) {
        try {
            // Calcular total do pedido
            double total = pedidoController.calcularTotal(pedido.getIdPedido());
            
            tableModel.addRow(new Object[]{
                pedido.getIdPedido(),
                pedido.getNrPedido(),
                pedido.getIdCliente(),
                pedido.getStatusDescricao(),
                pedido.getDtPedido().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                String.format("R$ %.2f", total)
            });
            
        } catch (Exception e) {
            System.err.println("Erro ao adicionar pedido #" + pedido.getNrPedido() + ": " + e.getMessage());
        }
    }
    
    private void buscarPedido(String numero) {
        if (numero == null || numero.trim().isEmpty()) {
            carregarPedidosAbertos();
            return;
        }
        
        try {
            int nrPedido = Integer.parseInt(numero.trim());
            
            // Buscar todos os pedidos e filtrar
            List<Pedido> todosPedidos = pedidoController.listarTodos();
            Pedido pedidoEncontrado = null;
            
            for (Pedido p : todosPedidos) {
                if (p.getNrPedido() == nrPedido && (p.getStPedido() == 'A' || p.getStPedido() == 'E')) {
                    pedidoEncontrado = p;
                    break;
                }
            }
            
            if (pedidoEncontrado != null) {
                tableModel.setRowCount(0);
                adicionarPedidoNaTabela(pedidoEncontrado);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Pedido #" + nrPedido + " não encontrado ou já pago.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Número de pedido inválido!",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void atualizarValorPago() {
        int linhaSelecionada = tabelaPedidos.getSelectedRow();
        if (linhaSelecionada != -1) {
            String valorPedidoStr = (String) tableModel.getValueAt(linhaSelecionada, 5);
            txtValorPago.setText(valorPedidoStr.replace("R$ ", "").trim());
        }
    }
    
    private void calcularTroco(String valorPedidoStr, String valorPagoStr) {
        try {
            double valorPedido = Double.parseDouble(valorPedidoStr.replace("R$ ", "").replace(",", ".").trim());
            double valorPago = Double.parseDouble(valorPagoStr.replace("R$ ", "").replace(",", ".").trim());
            
            if (valorPago < valorPedido) {
                JOptionPane.showMessageDialog(this,
                    "Valor insuficiente!\n" +
                    "Falta: R$ " + String.format("%.2f", valorPedido - valorPago),
                    "Valor Insuficiente",
                    JOptionPane.WARNING_MESSAGE);
            } else {
                double troco = valorPago - valorPedido;
                JOptionPane.showMessageDialog(this,
                    "Troco: R$ " + String.format("%.2f", troco) + "\n\n" +
                    "Detalhes:\n" +
                    "Valor do pedido: R$ " + String.format("%.2f", valorPedido) + "\n" +
                    "Valor pago: R$ " + String.format("%.2f", valorPago) + "\n" +
                    "Troco: R$ " + String.format("%.2f", troco),
                    "Cálculo do Troco",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Valores inválidos! Use números com ponto ou vírgula.",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void registrarPagamento(JTextField txtValorPedido) {
        int linhaSelecionada = tabelaPedidos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int idPedido = (int) tableModel.getValueAt(linhaSelecionada, 0);
            int nrPedido = (int) tableModel.getValueAt(linhaSelecionada, 1);
            int idCliente = (int) tableModel.getValueAt(linhaSelecionada, 2);
            String formaPagamento = (String) cbxFormaPagamento.getSelectedItem();
            
            // Obter valor do pedido
            String valorPedidoStr = txtValorPedido.getText().replace("R$ ", "").replace(",", ".").trim();
            BigDecimal valor = new BigDecimal(valorPedidoStr);
            
            // Verificar se pedido já foi pago
            if (pagamentoController.pedidoFoiPago(idPedido)) {
                JOptionPane.showMessageDialog(this,
                    "Pedido #" + nrPedido + " já foi pago!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Criar pagamento
            pagamentoController.criarPagamento(idCliente, idPedido, formaPagamento, valor);
            
            // Atualizar status do pedido para "Pronto" (P) após pagamento
            pedidoController.atualizarStatus(idPedido, 'P');
            
            // Processar pagamento
            boolean pagamentoProcessado = pagamentoController.processarPagamento(formaPagamento, valor.doubleValue());
            
            JOptionPane.showMessageDialog(this,
                "✅ Pagamento registrado com sucesso!\n\n" +
                "Detalhes:\n" +
                "Pedido: #" + nrPedido + "\n" +
                "Forma: " + formaPagamento + "\n" +
                "Valor: R$ " + String.format("%.2f", valor.doubleValue()) + "\n" +
                "Status: " + (pagamentoProcessado ? "Processado" : "Pendente") + "\n\n" +
                "Pedido agora está: PRONTO PARA RETIRADA",
                "Pagamento Registrado",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Limpar campos e atualizar tabela
            txtValorPago.setText("");
            carregarPedidosAbertos();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Valor inválido! Use números.",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                "Erro: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "❌ Erro ao registrar pagamento: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    // Método para facilitar o uso em outras telas
    public static void mostrarTelaPagamento() {
        SwingUtilities.invokeLater(() -> {
            PagamentoView pagamentoView = new PagamentoView();
            pagamentoView.setVisible(true);
        });
    }
}