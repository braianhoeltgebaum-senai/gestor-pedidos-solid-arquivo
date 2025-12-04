package com.tecdes.pedido.view;

import com.tecdes.pedido.controller.AvaliacaoController;
//import com.tecdes.pedido.controller.PedidoController;
import com.tecdes.pedido.model.entity.Avaliacao;
import javax.swing.*;
import java.awt.*;

public class AvaliacaoView extends JFrame {
    
    private JTextField txtNrPedido;
    private JTextField txtIdCliente;
    private JComboBox<Integer> cbNota;
    private JTextArea txtComentario;
    private JButton btnSalvar, btnLimpar;
    
    private final AvaliacaoController avaliacaoController;
    //private final PedidoController pedidoController;
    
    public AvaliacaoView() {
        this.avaliacaoController = new AvaliacaoController();
       // this.pedidoController = new PedidoController();
        
        configurarJanela();
        criarComponentes();
        setVisible(true);
    }
    
    private void configurarJanela() {
        setTitle("⭐ Avaliar Pedido");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private void criarComponentes() {
        getContentPane().setLayout(new BorderLayout());
        
        // Painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel lblTitulo = new JLabel("⭐ AVALIAR PEDIDO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(255, 193, 7)); // Amarelo
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblTitulo);
        
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Número do Pedido
        JPanel panelPedido = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelPedido.add(new JLabel("Número do Pedido:"));
        txtNrPedido = new JTextField(10);
        panelPedido.add(txtNrPedido);
        panel.add(panelPedido);
        
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // ID do Cliente
        JPanel panelCliente = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCliente.add(new JLabel("ID do Cliente:"));
        txtIdCliente = new JTextField(10);
        panelCliente.add(txtIdCliente);
        panel.add(panelCliente);
        
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Nota (0-10 conforme sua entidade)
        JPanel panelNota = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelNota.add(new JLabel("Nota (0-10):"));
        Integer[] notas = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        cbNota = new JComboBox<>(notas);
        cbNota.setSelectedIndex(10); // 10 por padrão
        panelNota.add(cbNota);
        panel.add(panelNota);
        
        // Indicador visual da nota
        JPanel panelEstrelas = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblEstrelas = new JLabel("★★★★★☆☆☆☆☆");
        lblEstrelas.setFont(new Font("Arial", Font.BOLD, 16));
        lblEstrelas.setForeground(new Color(255, 193, 7));
        panelEstrelas.add(lblEstrelas);
        panel.add(panelEstrelas);
        
        // Atualizar estrelas quando mudar a nota
        cbNota.addActionListener(e -> {
            int nota = (Integer) cbNota.getSelectedItem();
            StringBuilder estrelas = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                if (i < nota) {
                    estrelas.append("★");
                } else {
                    estrelas.append("☆");
                }
            }
            lblEstrelas.setText(estrelas.toString());
        });
        
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Comentário
        JPanel panelComentario = new JPanel(new BorderLayout());
        panelComentario.add(new JLabel("Comentário (opcional, máx 255 caracteres):"), BorderLayout.NORTH);
        txtComentario = new JTextArea(4, 30);
        txtComentario.setLineWrap(true);
        txtComentario.setWrapStyleWord(true);
        JScrollPane scrollComentario = new JScrollPane(txtComentario);
        panelComentario.add(scrollComentario, BorderLayout.CENTER);
        panel.add(panelComentario);
        
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        
        // Botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        btnSalvar = new JButton("⭐ Registrar Avaliação");
        btnSalvar.setBackground(new Color(255, 193, 7)); // Amarelo
        btnSalvar.setForeground(Color.BLACK);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalvar.addActionListener(e -> registrarAvaliacao());
        panelBotoes.add(btnSalvar);
        
        btnLimpar = new JButton("🧹 Limpar");
        btnLimpar.addActionListener(e -> limparCampos());
        panelBotoes.add(btnLimpar);
        
        JButton btnVoltar = new JButton("⬅️ Voltar");
        btnVoltar.addActionListener(e -> dispose());
        panelBotoes.add(btnVoltar);
        
        panel.add(panelBotoes);
        
        getContentPane().add(panel, BorderLayout.CENTER);
    }
    
    private void registrarAvaliacao() {
        try {
            // Validações
            String nrPedidoStr = txtNrPedido.getText().trim();
            String idClienteStr = txtIdCliente.getText().trim();
            
            if (nrPedidoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Informe o número do pedido!", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (idClienteStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Informe o ID do cliente!",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int nrPedido = Integer.parseInt(nrPedidoStr);
            int idCliente = Integer.parseInt(idClienteStr);
            int nota = (Integer) cbNota.getSelectedItem();
            String comentario = txtComentario.getText().trim();
            
            // Validar nota
            if (!avaliacaoController.validarNota(nota)) {
                JOptionPane.showMessageDialog(this,
                    "Nota inválida! Deve ser entre 0 e 10.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar comentário
            if (!avaliacaoController.validarComentario(comentario)) {
                JOptionPane.showMessageDialog(this,
                    "Comentário muito longo! Máximo 255 caracteres.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
           
            
            int idPedido = nrPedido; // Assumindo que número do pedido = ID (para simplificar)
            
            // Verificar se pedido já foi avaliado
            if (avaliacaoController.pedidoFoiAvaliado(idPedido)) {
                int resposta = JOptionPane.showConfirmDialog(this,
                    "Este pedido já foi avaliado. Deseja substituir a avaliação?",
                    "Avaliação Existente",
                    JOptionPane.YES_NO_OPTION);
                
                if (resposta != JOptionPane.YES_OPTION) {
                    return;
                }
            }
            
            // Registrar avaliação usando o método CORRETO do controller
            Avaliacao avaliacao = avaliacaoController.registrar(
                idPedido, idCliente, nota, comentario);
            
            if (avaliacao != null) {
                JOptionPane.showMessageDialog(this,
                    "✅ Avaliação registrada com sucesso!\n\n" +
                    "Detalhes:\n" +
                    "📋 Pedido: #" + nrPedido + "\n" +
                    "👤 Cliente: #" + idCliente + "\n" +
                    "⭐ Nota: " + nota + "/10\n" +
                    (comentario.isEmpty() ? "" : "💬 Comentário: " + comentario + "\n") +
                    "🆔 ID Avaliação: " + avaliacao.getIdAvaliacao() + "\n\n" +
                    "Obrigado pela sua avaliação!",
                    "Avaliação Registrada",
                    JOptionPane.INFORMATION_MESSAGE);
                
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this,
                    "❌ Não foi possível registrar a avaliação.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Número do pedido e ID do cliente devem ser números!",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                "❌ " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "❌ Erro ao registrar avaliação: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Para debug
        }
    }
    
    private void limparCampos() {
        txtNrPedido.setText("");
        txtIdCliente.setText("");
        cbNota.setSelectedIndex(10); // Volta para nota 10
        txtComentario.setText("");
    }
    
    // Método estático para facilitar o uso
    public static void mostrarTela() {
        SwingUtilities.invokeLater(() -> {
            AvaliacaoView view = new AvaliacaoView();
            view.setVisible(true);
        });
    }
    
    // Método para avaliar pedido específico (conveniência)
    public static void avaliarPedido(int nrPedido, int idCliente) {
        SwingUtilities.invokeLater(() -> {
            AvaliacaoView view = new AvaliacaoView();
            view.txtNrPedido.setText(String.valueOf(nrPedido));
            view.txtIdCliente.setText(String.valueOf(idCliente));
            view.setVisible(true);
        });
    }
}