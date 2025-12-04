package com.tecdes.pedido.controller;


import com.tecdes.pedido.model.entity.Avaliacao;
import com.tecdes.pedido.service.AvaliacaoService;
import com.tecdes.pedido.repository.AvaliacaoRepositoryImpl;
import java.util.List;
import java.util.Optional;


public class AvaliacaoController {
   
    private final AvaliacaoService service;
   
    public AvaliacaoController() {
        this.service = new AvaliacaoService(new AvaliacaoRepositoryImpl());
    }
   
    // Registrar avaliação
    public Avaliacao registrar(int idPedido, int idCliente, int nota, String comentario) {
        return service.registrarAvaliacao(idPedido, idCliente, nota, comentario);
    }
   
    // Buscar por ID
    public Avaliacao buscarPorId(int id) {
        return service.buscarAvaliacaoPorId(id);
    }
   
    // Buscar por pedido
    public Optional<Avaliacao> buscarPorPedido(int idPedido) {
        return service.buscarAvaliacaoPorPedido(idPedido);
    }
   
    // Listar todas
    public List<Avaliacao> listarTodas() {
        return service.listarTodasAvaliacoes();
    }
   
    // Listar por cliente
    public List<Avaliacao> listarPorCliente(int idCliente) {
        return service.listarAvaliacoesPorCliente(idCliente);
    }
   
    // Listar com nota mínima
    public List<Avaliacao> listarComNotaMinima(int notaMinima) {
        return service.listarAvaliacoesComNotaMinima(notaMinima);
    }
   
    // Calcular média
    public double calcularMedia() {
        return service.calcularMediaGeral();
    }
   
    // Contar total
    public int contarTotal() {
        return service.contarTotalAvaliacoes();
    }
   
    // Deletar avaliação
    public void deletar(int id) {
        service.deletarAvaliacao(id);
    }
   
    // Verificar se pedido foi avaliado
    public boolean pedidoFoiAvaliado(int idPedido) {
        return service.pedidoFoiAvaliado(idPedido);
    }
   
    // Gerar relatório
    public void gerarRelatorio() {
        service.gerarRelatorio();
    }
   
    // Popular dados de teste
    public void popularDadosTeste() {
        service.popularDadosTeste();
    }
   
    // Validar nota
    public boolean validarNota(int nota) {
        return nota >= 0 && nota <= 10;
    }
   
    // Validar comentário
    public boolean validarComentario(String comentario) {
        return comentario == null || comentario.length() <= 255;
    }
}
