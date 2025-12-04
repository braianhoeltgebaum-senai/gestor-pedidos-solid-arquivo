package com.tecdes.pedido.view;

import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.controller.PedidoController;
import com.tecdes.pedido.controller.ClienteController;
import com.tecdes.pedido.controller.ProdutoController;
import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class ComandaVirtualView extends JDialog {
    
    private PedidoController pedidoController;
    private ClienteController clienteController;
    private ProdutoController produtoController;
    private Pedido pedido;
    
    public ComandaVirtualView(JFrame parent, Pedido pedido) {
        super(parent, "📋 Comanda Virtual", true);
        this.pedidoController = new PedidoController();
        this.clienteController = new ClienteController();
        this.produtoController = new ProdutoController();
        this.pedido = pedido;
        
        configurarJanela();
        criarComponentes();
    }
    
    private void configurarJanela() {
        setSize(600, 700);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
    }
    
    private void criarComponentes() {
        // Painel principal com rolagem
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Cabeçalho da comanda
        JPanel headerPanel = criarCabecalho();
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Itens do pedido
        JPanel itemsPanel = criarListaItens();
        panel.add(new JScrollPane(itemsPanel), BorderLayout.CENTER);
        
        // Rodapé com total
        JPanel footerPanel = criarRodape();
        panel.add(footerPanel, BorderLayout.SOUTH);
        
        add(panel);
    }
    
    private JPanel criarCabecalho() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 5, 5));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Título
        JLabel lblTitulo = new JLabel("🍔 COMANDA VIRTUAL 🍟");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Número do pedido
        JLabel lblPedido = new JLabel("PEDIDO #" + pedido.getNrPedido());
        lblPedido.setFont(new Font("Arial", Font.BOLD, 16));
        lblPedido.setForeground(new Color(0, 100, 0));
        lblPedido.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Data e hora
        JLabel lblData = new JLabel("Data: " + 
            pedido.getDtPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        lblData.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Cliente
        String nomeCliente = obterNomeCliente(pedido.getIdCliente());
        JLabel lblCliente = new JLabel("Cliente: " + nomeCliente);
        lblCliente.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Status
        String status = formatarStatus(pedido.getStPedido());
        JLabel lblStatus = new JLabel("Status: " + status);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 12));
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
        
        panel.add(lblTitulo);
        panel.add(lblPedido);
        panel.add(lblData);
        panel.add(lblCliente);
        panel.add(lblStatus);
        
        return panel;
    }
    
    private String obterNomeCliente(int idCliente) {
        try {
            // Se tiver método para buscar cliente por ID
            // return clienteController.buscarPorId(idCliente).getNmCliente();
            return "Cliente #" + idCliente;
        } catch (Exception e) {
            return "Cliente #" + idCliente;
        }
    }
    
    private String obterNomeProduto(int idProduto) {
        try {
            // Se tiver método para buscar produto por ID
            // return produtoController.buscarPorId(idProduto).getNmProduto();
            return "Produto #" + idProduto;
        } catch (Exception e) {
            return "Produto #" + idProduto;
        }
    }
    
    private JPanel criarListaItens() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        
        // Cabeçalho da tabela
        JPanel headerPanel = new JPanel(new GridLayout(1, 4));
        headerPanel.setBackground(new Color(220, 220, 220));
        
        JLabel lblItem = new JLabel("ITEM");
        JLabel lblQtd = new JLabel("QTD");
        JLabel lblUnit = new JLabel("UNITÁRIO");
        JLabel lblSubtotal = new JLabel("SUBTOTAL");
        
        lblItem.setFont(new Font("Arial", Font.BOLD, 12));
        lblQtd.setFont(new Font("Arial", Font.BOLD, 12));
        lblUnit.setFont(new Font("Arial", Font.BOLD, 12));
        lblSubtotal.setFont(new Font("Arial", Font.BOLD, 12));
        
        lblItem.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        lblQtd.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        lblUnit.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        lblSubtotal.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        headerPanel.add(lblItem);
        headerPanel.add(lblQtd);
        headerPanel.add(lblUnit);
        headerPanel.add(lblSubtotal);
        
        panel.add(headerPanel);
        
        try {
            // Calcular total do pedido
            double total = pedidoController.calcularTotal(pedido.getIdPedido());
            
            // Como não temos método para listar itens, vamos mostrar o total
            JPanel itemPanel = new JPanel(new GridLayout(1, 4));
            itemPanel.setBackground(new Color(248, 248, 248));
            
            JLabel lblTotalTexto = new JLabel("TOTAL DO PEDIDO:");
            lblTotalTexto.setFont(new Font("Arial", Font.BOLD, 12));
            
            JLabel lblEspaco = new JLabel("");
            JLabel lblEspaco2 = new JLabel("");
            
            JLabel lblTotalValor = new JLabel(String.format("R$ %.2f", total));
            lblTotalValor.setFont(new Font("Arial", Font.BOLD, 12));
            lblTotalValor.setForeground(new Color(0, 100, 0));
            
            lblTotalTexto.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
            lblEspaco.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
            lblEspaco2.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
            lblTotalValor.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
            
            itemPanel.add(lblTotalTexto);
            itemPanel.add(lblEspaco);
            itemPanel.add(lblEspaco2);
            itemPanel.add(lblTotalValor);
            
            panel.add(itemPanel);
            panel.add(Box.createRigidArea(new Dimension(0, 20)));
            
            // Se você tiver acesso aos itens do pedido, adicione aqui:
            /*
            List<ItemPedido> itens = // método para obter itens
            for (ItemPedido item : itens) {
                // código para mostrar cada item
            }
            */
            
        } catch (Exception e) {
            JLabel lblErro = new JLabel("Erro ao calcular total: " + e.getMessage());
            lblErro.setForeground(Color.RED);
            lblErro.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(lblErro);
        }
        
        return panel;
    }
    
    private JPanel criarRodape() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        try {
            // Calcular total
            double subtotal = pedidoController.calcularTotal(pedido.getIdPedido());
            double taxaEntrega = 0.0; // Pode ser configurável
            double desconto = 0.0; // Pode ser configurável
            double total = subtotal + taxaEntrega - desconto;
            
            // Totais
            JPanel totalPanel = new JPanel(new GridLayout(4, 2, 10, 5));
            
            totalPanel.add(new JLabel("Subtotal:"));
            totalPanel.add(new JLabel(String.format("R$ %.2f", subtotal), SwingConstants.RIGHT));
            
            totalPanel.add(new JLabel("Taxa de Entrega:"));
            totalPanel.add(new JLabel(String.format("R$ %.2f", taxaEntrega), SwingConstants.RIGHT));
            
            totalPanel.add(new JLabel("Desconto:"));
            totalPanel.add(new JLabel(String.format("- R$ %.2f", desconto), SwingConstants.RIGHT));
            
            JLabel lblTotalTexto = new JLabel("TOTAL:");
            lblTotalTexto.setFont(new Font("Arial", Font.BOLD, 14));
            totalPanel.add(lblTotalTexto);
            
            JLabel lblTotalValor = new JLabel(String.format("R$ %.2f", total), SwingConstants.RIGHT);
            lblTotalValor.setFont(new Font("Arial", Font.BOLD, 14));
            lblTotalValor.setForeground(new Color(0, 100, 0));
            totalPanel.add(lblTotalValor);
            
            panel.add(totalPanel, BorderLayout.CENTER);
            
        } catch (Exception e) {
            JLabel lblErro = new JLabel("Erro ao calcular totais: " + e.getMessage());
            lblErro.setForeground(Color.RED);
            panel.add(lblErro, BorderLayout.CENTER);
        }
        
        // Botão de imprimir/fechar
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton btnImprimir = new JButton("🖨️ Imprimir Comanda");
        btnImprimir.addActionListener(e -> imprimirComanda());
        
        JButton btnFechar = new JButton("✅ Fechar");
        btnFechar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnImprimir);
        buttonPanel.add(btnFechar);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
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
    
    private void imprimirComanda() {
        try {
            // Criar pasta se não existir
            java.io.File pastaComandas = new java.io.File("comandas");
            if (!pastaComandas.exists()) {
                pastaComandas.mkdirs();
            }
            
            // Gerar arquivo .txt da comanda
            String fileName = "comandas/comanda_" + pedido.getNrPedido() + "_" + 
                java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
            
            java.io.FileWriter writer = new java.io.FileWriter(fileName);
            
            writer.write("=".repeat(50) + "\n");
            writer.write("           COMANDA VIRTUAL - LANCHONETE\n");
            writer.write("=".repeat(50) + "\n\n");
            writer.write("PEDIDO #" + pedido.getNrPedido() + "\n");
            writer.write("Data: " + pedido.getDtPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\n");
            writer.write("Cliente: " + obterNomeCliente(pedido.getIdCliente()) + "\n");
            writer.write("Status: " + formatarStatus(pedido.getStPedido()) + "\n\n");
            
            writer.write("RESUMO DO PEDIDO:\n");
            writer.write("-".repeat(50) + "\n");
            
            try {
                double total = pedidoController.calcularTotal(pedido.getIdPedido());
                writer.write(String.format("TOTAL: R$ %.2f\n", total));
            } catch (Exception e) {
                writer.write("Total: (Não calculado)\n");
            }
            
            writer.write("\nOBSERVAÇÕES:\n");
            writer.write("-".repeat(50) + "\n");
            writer.write("Comanda gerada automaticamente pelo sistema.\n");
            writer.write("Para detalhes dos itens, consulte o sistema.\n");
            
            writer.write("=".repeat(50) + "\n");
            writer.write("Comanda gerada em: " + java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n");
            writer.write("=".repeat(50) + "\n");
            
            writer.close();
            
            JOptionPane.showMessageDialog(this,
                "✅ Comanda impressa com sucesso!\n" +
                "📁 Salva em: " + fileName,
                "Comanda Impressa",
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "❌ Erro ao imprimir comanda: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Método estático para exibir rapidamente
    public static void mostrarComanda(JFrame parent, Pedido pedido) {
        ComandaVirtualView comandaView = new ComandaVirtualView(parent, pedido);
        comandaView.setVisible(true);
    }
    
    // Método para abrir a partir de um ID de pedido
    public static void mostrarComandaPorId(JFrame parent, int idPedido) {
        try {
            PedidoController controller = new PedidoController();
            Pedido pedido = controller.buscarPorId(idPedido);
            
            if (pedido != null) {
                mostrarComanda(parent, pedido);
            } else {
                JOptionPane.showMessageDialog(parent,
                    "Pedido não encontrado!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent,
                "Erro ao buscar pedido: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}