package com.tecdes.pedido.view;

import com.tecdes.pedido.controller.PedidoController;
//import com.tecdes.pedido.controller.ClienteController;
import com.tecdes.pedido.model.entity.Pedido;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ListaPedidosView extends JFrame {
    
    private PedidoController pedidoController;
    //private ClienteController clienteController;
    private JTable tabelaPedidos;
    private DefaultTableModel tableModel;
    private JComboBox<String> cbxFiltroStatus;
    private JTextField txtBuscaCliente;
    
    public ListaPedidosView() {
        pedidoController = new PedidoController();
    //    clienteController = new ClienteController();
        configurarJanela();
        criarComponentes();
        carregarPedidos();
    }
    
    private void configurarJanela() {
        setTitle("📋 Lista de Pedidos");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }
    
    private void criarComponentes() {
        // Painel superior (filtros e busca)
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltros.setBorder(BorderFactory.createTitledBorder("Filtros e Busca"));
        
        // Filtro por status
        panelFiltros.add(new JLabel("Status:"));
        String[] statusOptions = {"Todos", "Aberto (A)", "Em preparo (E)", "Pronto (P)", "Concluído (C)"};
        cbxFiltroStatus = new JComboBox<>(statusOptions);
        cbxFiltroStatus.addActionListener(e -> filtrarPedidos());
        panelFiltros.add(cbxFiltroStatus);
        
        panelFiltros.add(Box.createHorizontalStrut(20));
        
        // Busca por cliente
        panelFiltros.add(new JLabel("Buscar Cliente (ID ou Nome):"));
        txtBuscaCliente = new JTextField(15);
        panelFiltros.add(txtBuscaCliente);
        
        JButton btnBuscar = new JButton("🔍 Buscar");
        btnBuscar.addActionListener(e -> buscarPorCliente());
        panelFiltros.add(btnBuscar);
        
        JButton btnLimpar = new JButton("🧹 Limpar");
        btnLimpar.addActionListener(e -> {
            txtBuscaCliente.setText("");
            carregarPedidos();
        });
        panelFiltros.add(btnLimpar);
        
        add(panelFiltros, BorderLayout.NORTH);
        
        // Tabela de pedidos
        String[] colunas = {"ID", "Nº Pedido", "Cliente", "Status", "Valor Total", "Data/Hora", "Ações"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Apenas a coluna de ações é editável
            }
            
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 6 ? JButton.class : String.class;
            }
        };
        
        tabelaPedidos = new JTable(tableModel);
        tabelaPedidos.setRowHeight(40);
        tabelaPedidos.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        tabelaPedidos.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));
        
        JScrollPane scrollPane = new JScrollPane(tabelaPedidos);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Pedidos Registrados"));
        
        add(scrollPane, BorderLayout.CENTER);
        
        // Painel inferior (estatísticas e botões)
        JPanel panelInferior = new JPanel(new BorderLayout());
        
        // Estatísticas
        JPanel panelStats = new JPanel(new GridLayout(1, 4, 10, 10));
        panelStats.setBorder(BorderFactory.createTitledBorder("Estatísticas"));
        
        JLabel lblTotalPedidos = new JLabel("Total: 0", SwingConstants.CENTER);
        JLabel lblAbertos = new JLabel("Abertos: 0", SwingConstants.CENTER);
        JLabel lblConcluidos = new JLabel("Concluídos: 0", SwingConstants.CENTER);
        JLabel lblValorTotal = new JLabel("Valor Total: R$ 0,00", SwingConstants.CENTER);
        
        lblTotalPedidos.setFont(new Font("Arial", Font.BOLD, 14));
        lblAbertos.setFont(new Font("Arial", Font.BOLD, 14));
        lblConcluidos.setFont(new Font("Arial", Font.BOLD, 14));
        lblValorTotal.setFont(new Font("Arial", Font.BOLD, 14));
        
        lblAbertos.setForeground(Color.BLUE);
        lblConcluidos.setForeground(new Color(0, 100, 0));
        lblValorTotal.setForeground(new Color(139, 0, 0));
        
        panelStats.add(lblTotalPedidos);
        panelStats.add(lblAbertos);
        panelStats.add(lblConcluidos);
        panelStats.add(lblValorTotal);
        
        panelInferior.add(panelStats, BorderLayout.NORTH);
        
        // Botões
        JPanel panelBotoes = new JPanel(new FlowLayout());
        
        JButton btnAtualizar = new JButton("🔄 Atualizar");
        btnAtualizar.addActionListener(e -> carregarPedidos());
        
        JButton btnNovoPedido = new JButton("➕ Novo Pedido");
        btnNovoPedido.addActionListener(e -> abrirNovoPedido());
        
        JButton btnVerDetalhes = new JButton("👁️ Ver Detalhes");
        btnVerDetalhes.addActionListener(e -> verDetalhesPedido());
        
        JButton btnImprimir = new JButton("🖨️ Imprimir Lista");
        btnImprimir.addActionListener(e -> imprimirLista());
        
        JButton btnFechar = new JButton("❌ Fechar");
        btnFechar.addActionListener(e -> dispose());
        
        panelBotoes.add(btnAtualizar);
        panelBotoes.add(btnNovoPedido);
        panelBotoes.add(btnVerDetalhes);
        panelBotoes.add(btnImprimir);
        panelBotoes.add(btnFechar);
        
        panelInferior.add(panelBotoes, BorderLayout.SOUTH);
        
        add(panelInferior, BorderLayout.SOUTH);
        
        // Atualizar estatísticas
        atualizarEstatisticas(lblTotalPedidos, lblAbertos, lblConcluidos, lblValorTotal);
    }
    
    private void carregarPedidos() {
        tableModel.setRowCount(0);
        List<Pedido> pedidos = pedidoController.listarTodos();
        
        for (Pedido pedido : pedidos) {
            adicionarPedidoNaTabela(pedido);
        }
    }
    
    private void adicionarPedidoNaTabela(Pedido pedido) {
        try {
            // Obter nome do cliente
            String nomeCliente = "Cliente #" + pedido.getIdCliente();
            // Você pode descomentar esta linha se tiver um método para buscar nome do cliente:
            // nomeCliente = clienteController.buscarPorId(pedido.getIdCliente()).getNmCliente();
            
            // Calcular total do pedido
            double total = pedidoController.calcularTotal(pedido.getIdPedido());
            
            // Formatar status
            String statusFormatado = formatarStatus(pedido.getStPedido());
            
            // Formatar data
            String dataFormatada = pedido.getDtPedido()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            
            tableModel.addRow(new Object[]{
                pedido.getIdPedido(),
                pedido.getNrPedido(),
                nomeCliente,
                statusFormatado,
                String.format("R$ %.2f", total),
                dataFormatada,
                "Ações"
            });
            
        } catch (Exception e) {
            System.err.println("Erro ao adicionar pedido #" + pedido.getNrPedido() + ": " + e.getMessage());
        }
    }
    
    private String formatarStatus(char status) {
        switch (status) {
            case 'A': return "🟡 ABERTO";
            case 'E': return "👨‍🍳 EM PREPARO";
            case 'P': return "✅ PRONTO";
            case 'C': return "🏁 CONCLUÍDO";
            default: return String.valueOf(status);
        }
    }
    
    private void filtrarPedidos() {
        String filtro = (String) cbxFiltroStatus.getSelectedItem();
        if (filtro.equals("Todos")) {
            carregarPedidos();
            return;
        }
        
        tableModel.setRowCount(0);
        char statusFiltro = filtro.charAt(filtro.indexOf('(') + 1); // Pega o caractere entre parênteses
        
        List<Pedido> todosPedidos = pedidoController.listarTodos();
        for (Pedido pedido : todosPedidos) {
            if (pedido.getStPedido() == statusFiltro) {
                adicionarPedidoNaTabela(pedido);
            }
        }
    }
    
    private void buscarPorCliente() {
        String busca = txtBuscaCliente.getText().trim();
        if (busca.isEmpty()) {
            carregarPedidos();
            return;
        }
        
        tableModel.setRowCount(0);
        List<Pedido> todosPedidos = pedidoController.listarTodos();
        
        for (Pedido pedido : todosPedidos) {
            // Verifica se é número (ID do cliente)
            try {
                int idClienteBusca = Integer.parseInt(busca);
                if (pedido.getIdCliente() == idClienteBusca) {
                    adicionarPedidoNaTabela(pedido);
                }
            } catch (NumberFormatException e) {
                // Se não for número, poderia buscar por nome (se implementado)
                // Por enquanto, busca apenas por ID
            }
        }
    }
    
    private void atualizarEstatisticas(JLabel total, JLabel abertos, JLabel concluidos, JLabel valorTotal) {
        List<Pedido> pedidos = pedidoController.listarTodos();
        
        int countTotal = pedidos.size();
        int countAbertos = 0;
        int countConcluidos = 0;
        double somaTotal = 0.0;
        
        for (Pedido p : pedidos) {
            if (p.getStPedido() == 'A' || p.getStPedido() == 'E' || p.getStPedido() == 'P') {
                countAbertos++;
            } else if (p.getStPedido() == 'C') {
                countConcluidos++;
            }
            
            try {
                somaTotal += pedidoController.calcularTotal(p.getIdPedido());
            } catch (Exception e) {
                // Ignora pedidos sem cálculo
            }
        }
        
        total.setText("Total: " + countTotal);
        abertos.setText("Abertos: " + countAbertos);
        concluidos.setText("Concluídos: " + countConcluidos);
        valorTotal.setText(String.format("Valor Total: R$ %.2f", somaTotal));
    }
    
    private void abrirNovoPedido() {
        SwingUtilities.invokeLater(() -> {
            PedidoView pedidoView = new PedidoView();
            pedidoView.setVisible(true);
            dispose(); // Fecha esta tela se desejar
        });
    }
    
    private void verDetalhesPedido() {
        int linhaSelecionada = tabelaPedidos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Selecione um pedido para ver detalhes!",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int idPedido = (int) tableModel.getValueAt(linhaSelecionada, 0);
            Pedido pedido = pedidoController.buscarPorId(idPedido);
            
            if (pedido != null) {
                mostrarDetalhesPedido(pedido);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao buscar detalhes do pedido: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void mostrarDetalhesPedido(Pedido pedido) {
        StringBuilder detalhes = new StringBuilder();
        detalhes.append("📋 DETALHES DO PEDIDO\n");
        detalhes.append("=".repeat(40)).append("\n\n");
        detalhes.append("Número: #").append(pedido.getNrPedido()).append("\n");
        detalhes.append("Status: ").append(pedido.getStatusDescricao()).append("\n");
        detalhes.append("Cliente ID: ").append(pedido.getIdCliente()).append("\n");
        detalhes.append("Data/Hora: ").append(pedido.getDtPedido()
            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))).append("\n");
        detalhes.append("Endereço ID: ").append(pedido.getIdEndereco()).append("\n\n");
        
        try {
            double total = pedidoController.calcularTotal(pedido.getIdPedido());
            detalhes.append("💰 VALOR TOTAL: R$ ").append(String.format("%.2f", total)).append("\n");
        } catch (Exception e) {
            detalhes.append("💰 VALOR TOTAL: (Não calculado)\n");
        }
        
        JOptionPane.showMessageDialog(this,
            detalhes.toString(),
            "Detalhes do Pedido #" + pedido.getNrPedido(),
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void imprimirLista() {
        try {
            String nomeArquivo = "relatorios/lista_pedidos_" + 
                java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
            
            java.io.FileWriter writer = new java.io.FileWriter(nomeArquivo);
            
            writer.write("=".repeat(70) + "\n");
            writer.write("           LISTA DE PEDIDOS - LANCHONETE\n");
            writer.write("=".repeat(70) + "\n");
            writer.write("Data: " + java.time.LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n");
            writer.write("-".repeat(70) + "\n\n");
            
            writer.write(String.format("%-8s %-15s %-20s %-12s %-12s\n", 
                "NÚMERO", "DATA", "CLIENTE", "STATUS", "VALOR"));
            writer.write("-".repeat(70) + "\n");
            
            List<Pedido> pedidos = pedidoController.listarTodos();
            double totalGeral = 0.0;
            
            for (Pedido p : pedidos) {
                String nomeCliente = "Cliente #" + p.getIdCliente();
                double total = pedidoController.calcularTotal(p.getIdPedido());
                totalGeral += total;
                
                writer.write(String.format("%-8d %-15s %-20s %-12s R$ %9.2f\n",
                    p.getNrPedido(),
                    p.getDtPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    nomeCliente.length() > 20 ? nomeCliente.substring(0, 17) + "..." : nomeCliente,
                    p.getStatusDescricao(),
                    total));
            }
            
            writer.write("-".repeat(70) + "\n");
            writer.write(String.format("TOTAL GERAL: R$ %.2f\n", totalGeral));
            writer.write(String.format("QUANTIDADE: %d pedidos\n", pedidos.size()));
            writer.write("=".repeat(70) + "\n");
            
            writer.close();
            
            JOptionPane.showMessageDialog(this,
                "✅ Lista de pedidos impressa com sucesso!\n" +
                "📁 Arquivo: " + nomeArquivo,
                "Lista Impressa",
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "❌ Erro ao imprimir lista: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Classes internas para renderizar botões na tabela
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }
    
    class ButtonEditor extends javax.swing.AbstractCellEditor 
            implements javax.swing.table.TableCellEditor, java.awt.event.ActionListener {
        private JButton button;
        private int currentRow;
        
        public ButtonEditor(JCheckBox checkBox) {
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(this);
        }
        
        public java.awt.Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            currentRow = row;
            button.setText("Editar");
            return button;
        }
        
        public Object getCellEditorValue() {
            return "Editar";
        }
        
        public void actionPerformed(java.awt.event.ActionEvent e) {
            fireEditingStopped();
            // Aqui você pode adicionar lógica para editar o pedido
            JOptionPane.showMessageDialog(ListaPedidosView.this,
                "Editar pedido da linha " + (currentRow + 1),
                "Editar Pedido",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Método estático para facilitar a abertura
    public static void mostrarTela() {
        SwingUtilities.invokeLater(() -> {
            ListaPedidosView view = new ListaPedidosView();
            view.setVisible(true);
        });
    }
}