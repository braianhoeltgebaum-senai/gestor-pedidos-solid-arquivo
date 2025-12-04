package com.tecdes.pedido.view;

import com.tecdes.pedido.controller.*;
import com.tecdes.pedido.model.entity.*;
import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
//import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RelatorioView extends JFrame {
    
    private ClienteController clienteController;
    private ProdutoController produtoController;
    private PedidoController pedidoController;
  //  private FuncionarioController funcionarioController;
    private AvaliacaoController avaliacaoController;
    
    public RelatorioView() {
        inicializarControllers();
        configurarJanela();
        criarComponentes();
    }
    
    private void inicializarControllers() {
        clienteController = new ClienteController();
        produtoController = new ProdutoController();
        pedidoController = new PedidoController();
   //     funcionarioController = new FuncionarioController();
        avaliacaoController = new AvaliacaoController();
    }
    
    private void configurarJanela() {
        setTitle("📊 Sistema de Relatórios");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }
    
    private void criarComponentes() {
        // Cabeçalho
        JLabel lblTitulo = new JLabel("📊 GERAR RELATÓRIOS (.txt)", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblTitulo, BorderLayout.NORTH);
        
        // Painel principal com botões
        JPanel panel = new JPanel(new GridLayout(6, 1, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panel.setBackground(new Color(240, 240, 240));
        
        JButton btnRelClientes = criarBotaoRelatorio("👥 RELATÓRIO DE CLIENTES", 
            "Total de clientes cadastrados, lista completa", new Color(41, 128, 185));
        btnRelClientes.addActionListener(e -> gerarRelatorioClientes());
        
        JButton btnRelProdutos = criarBotaoRelatorio("🍔 RELATÓRIO DE PRODUTOS", 
            "Cardápio completo com valores", new Color(39, 174, 96));
        btnRelProdutos.addActionListener(e -> gerarRelatorioProdutos());
        
        JButton btnRelPedidos = criarBotaoRelatorio("📋 RELATÓRIO DE PEDIDOS", 
            "Todos os pedidos do sistema", new Color(142, 68, 173));
        btnRelPedidos.addActionListener(e -> gerarRelatorioPedidos());
        
        JButton btnRelVendas = criarBotaoRelatorio("💰 RELATÓRIO DE VENDAS", 
            "Vendas por período", new Color(230, 126, 34));
        btnRelVendas.addActionListener(e -> gerarRelatorioVendas());
        
        JButton btnRelAvaliacoes = criarBotaoRelatorio("⭐ RELATÓRIO DE AVALIAÇÕES", 
            "Avaliações dos clientes", new Color(241, 196, 15));
        btnRelAvaliacoes.addActionListener(e -> gerarRelatorioAvaliacoes());
        
        JButton btnRelCompleto = criarBotaoRelatorio("📈 RELATÓRIO COMPLETO", 
            "Todos os dados do sistema", new Color(192, 57, 43));
        btnRelCompleto.addActionListener(e -> gerarRelatorioCompleto());
        
        panel.add(btnRelClientes);
        panel.add(btnRelProdutos);
        panel.add(btnRelPedidos);
        panel.add(btnRelVendas);
        panel.add(btnRelAvaliacoes);
        panel.add(btnRelCompleto);
        
        add(panel, BorderLayout.CENTER);
        
        // Rodapé
        JPanel panelRodape = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lblInfo = new JLabel("Os relatórios serão salvos na pasta 'relatorios/' com data e hora");
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 11));
        panelRodape.add(lblInfo);
        add(panelRodape, BorderLayout.SOUTH);
    }
    
    private JButton criarBotaoRelatorio(String titulo, String descricao, Color cor) {
        JPanel panelBotao = new JPanel(new BorderLayout());
        panelBotao.setBackground(cor);
        panelBotao.setBorder(BorderFactory.createLineBorder(cor.darker(), 1));
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));
        
        JLabel lblDesc = new JLabel(descricao);
        lblDesc.setFont(new Font("Arial", Font.PLAIN, 10));
        lblDesc.setForeground(new Color(240, 240, 240));
        lblDesc.setBorder(BorderFactory.createEmptyBorder(0, 15, 10, 15));
        
        panelBotao.add(lblTitulo, BorderLayout.NORTH);
        panelBotao.add(lblDesc, BorderLayout.CENTER);
        
        // Criar botão transparente sobre o painel
        JButton btn = new JButton();
        btn.setLayout(new BorderLayout());
        btn.add(panelBotao);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return btn;
    }
    
    private void gerarRelatorioClientes() {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String nomeArquivo = "relatorios/relatorio_clientes_" + timestamp + ".txt";
            
            List<Cliente> clientes = clienteController.listarTodos();
            
            try (FileWriter writer = new FileWriter(nomeArquivo)) {
                writer.write("=".repeat(60) + "\n");
                writer.write("           RELATÓRIO DE CLIENTES - LANCHONETE\n");
                writer.write("=".repeat(60) + "\n");
                writer.write("Data: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n");
                writer.write("Total de Clientes: " + clientes.size() + "\n");
                writer.write("-".repeat(60) + "\n\n");
                
                writer.write(String.format("%-5s %-20s %-15s %-25s %-15s\n", 
                    "ID", "NOME", "CADASTRO", "EMAIL", "TELEFONE"));
                writer.write("-".repeat(85) + "\n");
                
                for (Cliente c : clientes) {
                    writer.write(String.format("%-5d %-20s %-15s %-25s %-15s\n",
                        c.getIdCliente(),
                        c.getNmCliente().length() > 20 ? c.getNmCliente().substring(0, 17) + "..." : c.getNmCliente(),
                        c.getNrCadastro(),
                        c.getDsEmail().length() > 25 ? c.getDsEmail().substring(0, 22) + "..." : c.getDsEmail(),
                        c.getNrTelefone()));
                }
                
                writer.write("\n" + "=".repeat(60) + "\n");
                writer.write("FIM DO RELATÓRIO\n");
                
                JOptionPane.showMessageDialog(this,
                    "✅ Relatório de Clientes gerado com sucesso!\n" +
                    "📁 Arquivo: " + nomeArquivo + "\n" +
                    "👥 Total: " + clientes.size() + " clientes",
                    "Relatório Gerado",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "❌ Erro ao gerar relatório: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void gerarRelatorioProdutos() {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String nomeArquivo = "relatorios/relatorio_produtos_" + timestamp + ".txt";
            
            List<Produto> produtos = produtoController.buscarTodos();
            
            try (FileWriter writer = new FileWriter(nomeArquivo)) {
                writer.write("=".repeat(60) + "\n");
                writer.write("           RELATÓRIO DE PRODUTOS - CARDÁPIO\n");
                writer.write("=".repeat(60) + "\n");
                writer.write("Data: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n");
                writer.write("Total de Produtos: " + produtos.size() + "\n");
                writer.write("-".repeat(60) + "\n\n");
                
                writer.write(String.format("%-5s %-20s %-5s %-30s %-10s\n", 
                    "ID", "NOME", "TIPO", "DESCRIÇÃO", "VALOR"));
                writer.write("-".repeat(80) + "\n");
                
                double valorTotalEstoque = 0;
                for (Produto p : produtos) {
                    writer.write(String.format("%-5d %-20s %-5s %-30s R$ %7.2f\n",
                        p.getIdProduto(),
                        p.getNmProduto().length() > 20 ? p.getNmProduto().substring(0, 17) + "..." : p.getNmProduto(),
                        p.getTpProduto(),
                        p.getDsProduto().length() > 30 ? p.getDsProduto().substring(0, 27) + "..." : p.getDsProduto(),
                        p.getVlProduto().doubleValue()));
                    
                    valorTotalEstoque += p.getVlProduto().doubleValue();
                }
                
                writer.write("\n" + "=".repeat(60) + "\n");
                writer.write(String.format("VALOR TOTAL DO ESTOQUE: R$ %.2f\n", valorTotalEstoque));
                writer.write("FIM DO RELATÓRIO\n");
                
                JOptionPane.showMessageDialog(this,
                    "✅ Relatório de Produtos gerado com sucesso!\n" +
                    "📁 Arquivo: " + nomeArquivo + "\n" +
                    "🍔 Total: " + produtos.size() + " produtos\n" +
                    "💰 Valor estoque: R$ " + String.format("%.2f", valorTotalEstoque),
                    "Relatório Gerado",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "❌ Erro ao gerar relatório: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void gerarRelatorioPedidos() {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String nomeArquivo = "relatorios/relatorio_pedidos_" + timestamp + ".txt";
            
            // Listar todos os pedidos (não temos método específico para últimos 30 dias)
            List<Pedido> pedidos = pedidoController.listarTodos();
            
            try (FileWriter writer = new FileWriter(nomeArquivo)) {
                writer.write("=".repeat(60) + "\n");
                writer.write("           RELATÓRIO DE PEDIDOS - TODOS OS PEDIDOS\n");
                writer.write("=".repeat(60) + "\n");
                writer.write("Data: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n");
                writer.write("Total de Pedidos: " + pedidos.size() + "\n");
                writer.write("-".repeat(60) + "\n\n");
                
                writer.write(String.format("%-8s %-15s %-20s %-12s %-10s\n", 
                    "NÚMERO", "DATA", "CLIENTE", "STATUS", "VALOR"));
                writer.write("-".repeat(75) + "\n");
                
                double valorTotalPedidos = 0;
                for (Pedido p : pedidos) {
                    // Buscar cliente para obter nome
                    String nomeCliente = "Cliente #" + p.getIdCliente(); // Simplificado
                    
                    if (nomeCliente.length() > 20) nomeCliente = nomeCliente.substring(0, 17) + "...";
                    
                    // Calcular total do pedido
                    double totalPedido = pedidoController.calcularTotal(p.getIdPedido());
                    valorTotalPedidos += totalPedido;
                    
                    writer.write(String.format("%-8d %-15s %-20s %-12s R$ %7.2f\n",
                        p.getNrPedido(),
                        p.getDtPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        nomeCliente,
                        p.getStPedido(),
                        totalPedido));
                }
                
                writer.write("\n" + "=".repeat(60) + "\n");
                writer.write(String.format("VALOR TOTAL DOS PEDIDOS: R$ %.2f\n", valorTotalPedidos));
                writer.write(String.format("MÉDIA POR PEDIDO: R$ %.2f\n", 
                    pedidos.isEmpty() ? 0 : valorTotalPedidos / pedidos.size()));
                writer.write("FIM DO RELATÓRIO\n");
                
                JOptionPane.showMessageDialog(this,
                    "✅ Relatório de Pedidos gerado com sucesso!\n" +
                    "📁 Arquivo: " + nomeArquivo + "\n" +
                    "📋 Total: " + pedidos.size() + " pedidos\n" +
                    "💰 Valor total: R$ " + String.format("%.2f", valorTotalPedidos),
                    "Relatório Gerado",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "❌ Erro ao gerar relatório: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void gerarRelatorioVendas() {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String nomeArquivo = "relatorios/relatorio_vendas_" + timestamp + ".txt";
            
            // Listar todos os pedidos
            List<Pedido> todosPedidos = pedidoController.listarTodos();
            
            // Filtrar pedidos concluídos (status 'C')
            List<Pedido> pedidosConcluidos = todosPedidos.stream()
                .filter(p -> p.getStPedido() == 'C')
                .toList();
            
            // Calcular totais
            double totalConcluidos = pedidosConcluidos.stream()
                .mapToDouble(p -> pedidoController.calcularTotal(p.getIdPedido()))
                .sum();
            
            try (FileWriter writer = new FileWriter(nomeArquivo)) {
                writer.write("=".repeat(60) + "\n");
                writer.write("           RELATÓRIO DE VENDAS (PEDIDOS CONCLUÍDOS)\n");
                writer.write("=".repeat(60) + "\n");
                writer.write("Data: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n");
                writer.write("-".repeat(60) + "\n\n");
                
                writer.write("RESUMO DE VENDAS:\n");
                writer.write("-".repeat(40) + "\n");
                writer.write(String.format("Total de Pedidos: %d\n", todosPedidos.size()));
                writer.write(String.format("Pedidos Concluídos: %d (%.1f%%)\n", 
                    pedidosConcluidos.size(), 
                    todosPedidos.isEmpty() ? 0 : (double) pedidosConcluidos.size() / todosPedidos.size() * 100));
                writer.write(String.format("Valor Total em Vendas: R$ %.2f\n\n", totalConcluidos));
                
                writer.write("DETALHES DAS VENDAS CONCLUÍDAS:\n");
                writer.write("-".repeat(60) + "\n");
                writer.write(String.format("%-8s %-15s %-20s %-10s\n", 
                    "PEDIDO", "DATA", "CLIENTE", "VALOR"));
                writer.write("-".repeat(60) + "\n");
                
                for (Pedido p : pedidosConcluidos) {
                    String nomeCliente = "Cliente #" + p.getIdCliente();
                    if (nomeCliente.length() > 20) nomeCliente = nomeCliente.substring(0, 17) + "...";
                    
                    double totalPedido = pedidoController.calcularTotal(p.getIdPedido());
                    
                    writer.write(String.format("%-8d %-15s %-20s R$ %7.2f\n",
                        p.getNrPedido(),
                        p.getDtPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        nomeCliente,
                        totalPedido));
                }
                
                writer.write("\n" + "=".repeat(60) + "\n");
                writer.write("RESUMO:\n");
                writer.write(String.format("Média por venda: R$ %.2f\n", 
                    pedidosConcluidos.isEmpty() ? 0 : totalConcluidos / pedidosConcluidos.size()));
                writer.write(String.format("Ticket médio: R$ %.2f\n", 
                    pedidosConcluidos.isEmpty() ? 0 : totalConcluidos / pedidosConcluidos.size()));
                writer.write("FIM DO RELATÓRIO\n");
                
                JOptionPane.showMessageDialog(this,
                    "✅ Relatório de Vendas gerado com sucesso!\n" +
                    "📁 Arquivo: " + nomeArquivo + "\n" +
                    "💰 Total vendido: R$ " + String.format("%.2f", totalConcluidos) + "\n" +
                    "📋 Pedidos concluídos: " + pedidosConcluidos.size(),
                    "Relatório Gerado",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "❌ Erro ao gerar relatório: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void gerarRelatorioAvaliacoes() {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String nomeArquivo = "relatorios/relatorio_avaliacoes_" + timestamp + ".txt";
            
            List<Avaliacao> avaliacoes = avaliacaoController.listarTodas();
            
            try (FileWriter writer = new FileWriter(nomeArquivo)) {
                writer.write("=".repeat(60) + "\n");
                writer.write("           RELATÓRIO DE AVALIAÇÕES\n");
                writer.write("=".repeat(60) + "\n");
                writer.write("Data: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n");
                writer.write("Total de Avaliações: " + avaliacoes.size() + "\n");
                writer.write("-".repeat(60) + "\n\n");
                
                writer.write(String.format("%-5s %-8s %-20s %-10s %-40s\n", 
                    "ID", "NOTA", "CLIENTE", "PEDIDO", "COMENTÁRIO"));
                writer.write("-".repeat(90) + "\n");
                
                double somaNotas = 0;
                for (Avaliacao a : avaliacoes) {
                    String nomeCliente = "Cliente #" + a.getIdCliente();
                    if (nomeCliente.length() > 20) nomeCliente = nomeCliente.substring(0, 17) + "...";
                    
                    String comentario = a.getDsAvaliacao();
                    if (comentario == null) comentario = "";
                    if (comentario.length() > 40) comentario = comentario.substring(0, 37) + "...";
                    
                    writer.write(String.format("%-5d %-8d %-20s %-10d %-40s\n",
                        a.getIdAvaliacao(),
                        a.getVlNota(),
                        nomeCliente,
                        a.getIdPedido(),
                        comentario));
                    
                    somaNotas += a.getVlNota();
                }
                
                double mediaNotas = avaliacoes.isEmpty() ? 0 : somaNotas / avaliacoes.size();
                
                writer.write("\n" + "=".repeat(60) + "\n");
                writer.write(String.format("MÉDIA DE NOTAS: %.1f/10.0\n", mediaNotas));
                writer.write(String.format("TOTAL DE AVALIAÇÕES: %d\n", avaliacoes.size()));
                writer.write("DISTRIBUIÇÃO DE NOTAS:\n");
                
                // Contar distribuição de notas
                int[] contagemNotas = new int[11]; // Notas de 0 a 10
                for (Avaliacao a : avaliacoes) {
                    contagemNotas[a.getVlNota()]++;
                }
                
                for (int i = 0; i <= 10; i++) {
                    if (contagemNotas[i] > 0) {
                        double percentual = avaliacoes.isEmpty() ? 0 : (double) contagemNotas[i] / avaliacoes.size() * 100;
                        writer.write(String.format("  %d estrelas: %d (%.1f%%)\n", i, contagemNotas[i], percentual));
                    }
                }
                
                writer.write("FIM DO RELATÓRIO\n");
                
                JOptionPane.showMessageDialog(this,
                    "✅ Relatório de Avaliações gerado com sucesso!\n" +
                    "📁 Arquivo: " + nomeArquivo + "\n" +
                    "⭐ Total: " + avaliacoes.size() + " avaliações\n" +
                    "🏆 Média: " + String.format("%.1f", mediaNotas) + "/10.0 estrelas",
                    "Relatório Gerado",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "❌ Erro ao gerar relatório: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void gerarRelatorioCompleto() {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String nomeArquivo = "relatorios/relatorio_completo_" + timestamp + ".txt";
            
            try (FileWriter writer = new FileWriter(nomeArquivo)) {
                writer.write("=".repeat(70) + "\n");
                writer.write("           RELATÓRIO COMPLETO DO SISTEMA - LANCHONETE\n");
                writer.write("=".repeat(70) + "\n");
                writer.write("Data: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n");
                writer.write("-".repeat(70) + "\n\n");
                
                // 1. RESUMO GERAL
                List<Cliente> clientes = clienteController.listarTodos();
                List<Produto> produtos = produtoController.buscarTodos();
                List<Pedido> pedidos = pedidoController.listarTodos();
                List<Avaliacao> avaliacoes = avaliacaoController.listarTodas();
                
                writer.write("RESUMO GERAL DO SISTEMA:\n");
                writer.write("-".repeat(40) + "\n");
                writer.write(String.format("Total de Clientes: %d\n", clientes.size()));
                writer.write(String.format("Total de Produtos: %d\n", produtos.size()));
                writer.write(String.format("Total de Pedidos: %d\n", pedidos.size()));
                writer.write(String.format("Total de Avaliações: %d\n", avaliacoes.size()));
                writer.write("\n");
                
                // 2. PEDIDOS POR STATUS
                writer.write("PEDIDOS POR STATUS:\n");
                writer.write("-".repeat(30) + "\n");
                
                long aberto = pedidos.stream().filter(p -> p.getStPedido() == 'A').count();
                long emPreparo = pedidos.stream().filter(p -> p.getStPedido() == 'E').count();
                long pronto = pedidos.stream().filter(p -> p.getStPedido() == 'P').count();
                long concluido = pedidos.stream().filter(p -> p.getStPedido() == 'C').count();
                
                writer.write(String.format("Aberto: %d (%.1f%%)\n", 
                    aberto, pedidos.isEmpty() ? 0 : (double) aberto / pedidos.size() * 100));
                writer.write(String.format("Em preparo: %d (%.1f%%)\n", 
                    emPreparo, pedidos.isEmpty() ? 0 : (double) emPreparo / pedidos.size() * 100));
                writer.write(String.format("Pronto: %d (%.1f%%)\n", 
                    pronto, pedidos.isEmpty() ? 0 : (double) pronto / pedidos.size() * 100));
                writer.write(String.format("Concluído: %d (%.1f%%)\n", 
                    concluido, pedidos.isEmpty() ? 0 : (double) concluido / pedidos.size() * 100));
                
                writer.write("\n");
                
                // 3. VALOR TOTAL DO ESTOQUE
                writer.write("VALOR TOTAL DO ESTOQUE:\n");
                writer.write("-".repeat(30) + "\n");
                
                double valorEstoque = produtos.stream()
                    .mapToDouble(p -> p.getVlProduto().doubleValue())
                    .sum();
                writer.write(String.format("R$ %.2f\n", valorEstoque));
                
                writer.write("\n");
                
                // 4. VALOR TOTAL EM VENDAS (pedidos concluídos)
                writer.write("VALOR TOTAL EM VENDAS:\n");
                writer.write("-".repeat(30) + "\n");
                
                double valorVendas = pedidos.stream()
                    .filter(p -> p.getStPedido() == 'C')
                    .mapToDouble(p -> pedidoController.calcularTotal(p.getIdPedido()))
                    .sum();
                writer.write(String.format("R$ %.2f\n", valorVendas));
                
                writer.write("\n");
                
                // 5. MÉDIA DE AVALIAÇÕES
                writer.write("AVALIAÇÕES DOS CLIENTES:\n");
                writer.write("-".repeat(30) + "\n");
                
                double mediaAvaliacoes = avaliacoes.stream()
                    .mapToInt(Avaliacao::getVlNota)
                    .average()
                    .orElse(0.0);
                writer.write(String.format("Média: %.1f/10.0\n", mediaAvaliacoes));
                writer.write(String.format("Total de avaliações: %d\n", avaliacoes.size()));
                
                writer.write("\n" + "=".repeat(70) + "\n");
                writer.write("RECOMENDAÇÕES:\n");
                writer.write("-".repeat(30) + "\n");
                
                // Recomendações baseadas nos dados
                if (aberto > 5) {
                    writer.write("⚠️ Há muitos pedidos em aberto. Considere acelerar o atendimento.\n");
                }
                
                if (mediaAvaliacoes < 5.0) {
                    writer.write("⚠️ Média de avaliações baixa. Verifique a qualidade do serviço.\n");
                }
                
                if (produtos.size() < 10) {
                    writer.write("⚠️ Cardápio pequeno. Considere adicionar mais produtos.\n");
                }
                
                if (valorVendas < 1000) {
                    writer.write("💡 Baixo volume de vendas. Considere promover ofertas.\n");
                }
                
                writer.write("\n" + "=".repeat(70) + "\n");
                writer.write("FIM DO RELATÓRIO COMPLETO\n");
                writer.write("=".repeat(70) + "\n");
                
                JOptionPane.showMessageDialog(this,
                    "✅ Relatório Completo gerado com sucesso!\n" +
                    "📁 Arquivo: " + nomeArquivo + "\n" +
                    "📊 Contém dados de todos os módulos do sistema",
                    "Relatório Gerado",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "❌ Erro ao gerar relatório: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}