package com.tecdes.pedido.service;

import com.tecdes.pedido.model.entity.Avaliacao;
import com.tecdes.pedido.repository.AvaliacaoRepository;
import java.util.List;
import java.util.Optional;

public class AvaliacaoService {
   
    private final AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
    }
   
    // Registrar nova avaliação
    public Avaliacao registrarAvaliacao(int idPedido, int idCliente, int nota, String comentario) {
        // Valida nota (0-10)
        if (nota < 0 || nota > 10) {
            throw new IllegalArgumentException("Nota deve estar entre 0 e 10");
        }
        
        // Verifica se pedido já foi avaliado
        Optional<Avaliacao> avaliacaoExistente = avaliacaoRepository.findByPedidoId(idPedido);
        if (avaliacaoExistente.isPresent()) {
            throw new RuntimeException("Pedido #" + idPedido + " já foi avaliado");
        }
        
        // Cria nova avaliação
        Avaliacao avaliacao = new Avaliacao(idPedido, idCliente, nota, comentario);
        return avaliacaoRepository.save(avaliacao);
    }
    
    // Registrar avaliação com objeto
    public Avaliacao registrarAvaliacao(Avaliacao avaliacao) {
        return registrarAvaliacao(
            avaliacao.getIdPedido(),
            avaliacao.getIdCliente(),
            avaliacao.getVlNota(),
            avaliacao.getDsAvaliacao()
        );
    }
   
    // Buscar avaliação por ID
    public Avaliacao buscarAvaliacaoPorId(int id) {
        return avaliacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação ID " + id + " não encontrada"));
    }
    
    // Buscar avaliação por pedido
    public Optional<Avaliacao> buscarAvaliacaoPorPedido(int idPedido) {
        return avaliacaoRepository.findByPedidoId(idPedido);
    }
    
    // Listar todas as avaliações
    public List<Avaliacao> listarTodasAvaliacoes() {
        return avaliacaoRepository.findAll();
    }
    
    // Listar avaliações de um cliente
    public List<Avaliacao> listarAvaliacoesPorCliente(int idCliente) {
        return avaliacaoRepository.findByCliente(idCliente);
    }
    
    // Listar avaliações com nota mínima
    public List<Avaliacao> listarAvaliacoesComNotaMinima(int notaMinima) {
        return avaliacaoRepository.findByNotaMaiorOuIgual(notaMinima);
    }
    
    // Calcular média geral
    public double calcularMediaGeral() {
        return avaliacaoRepository.calcularMediaGeral();
    }
    
    // Contar total de avaliações
    public int contarTotalAvaliacoes() {
        return avaliacaoRepository.contarTotal();
    }
    
    // Deletar avaliação
    public void deletarAvaliacao(int id) {
        avaliacaoRepository.delete(id);
    }
    
    // Verificar se pedido foi avaliado
    public boolean pedidoFoiAvaliado(int idPedido) {
        return avaliacaoRepository.findByPedidoId(idPedido).isPresent();
    }
    
    // Gerar relatório simples
    public void gerarRelatorio() {
        int total = contarTotalAvaliacoes();
        double media = calcularMediaGeral();
        
        System.out.println("📊 RELATÓRIO DE AVALIAÇÕES");
        System.out.println("Total de avaliações: " + total);
        System.out.println("Média geral: " + String.format("%.1f", media) + "/10");
        
        if (total > 0) {
            System.out.println("Distribuição por nota:");
            for (int i = 0; i <= 10; i++) {
                final int nota = i;
                long count = avaliacaoRepository.findAll().stream()
                        .filter(a -> a.getVlNota() == nota)
                        .count();
                if (count > 0) {
                    System.out.println("  " + nota + " estrelas: " + count + " avaliações");
                }
            }
        }
    }
    
    // Popular com dados de teste (para desenvolvimento)
    public void popularDadosTeste() {
        try {
            registrarAvaliacao(1, 1, 9, "Excelente lanche, muito saboroso!");
            registrarAvaliacao(2, 2, 7, "Bom, mas demorou um pouco");
            registrarAvaliacao(3, 1, 10, "Perfeito! Melhor hambúrguer da cidade");
            registrarAvaliacao(4, 3, 5, "Mais ou menos, esperava mais");
            registrarAvaliacao(5, 4, 8, null); // Sem comentário
            System.out.println("✅ Dados de teste de avaliações criados!");
        } catch (Exception e) {
            System.out.println("⚠️ Erro ao criar dados de teste: " + e.getMessage());
        }
    }
}