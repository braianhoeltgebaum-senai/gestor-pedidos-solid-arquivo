package com.tecdes.pedido.service;

import com.tecdes.pedido.model.entity.Pagamento;
import com.tecdes.pedido.repository.PagamentoRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class PagamentoService {
    
    private final PagamentoRepository repository;
    
    public PagamentoService(PagamentoRepository repository) {
        this.repository = repository;
    }
    
    // Criar novo pagamento
    public Pagamento criarPagamento(int idCliente, int idPedido, String tipoPagamento, BigDecimal valor) {
        Pagamento pagamento = new Pagamento(idCliente, idPedido, tipoPagamento, valor);
        repository.save(pagamento);
        return pagamento;
    }
    
    // Efetuar pagamento usando Strategy Pattern
    public boolean processarPagamento(String tipoPagamento, double valor) {
        PagamentoStrategy estrategia = criarEstrategiaPagamento(tipoPagamento);
        if (estrategia == null) {
            throw new IllegalArgumentException("Tipo de pagamento inválido: " + tipoPagamento);
        }
        return estrategia.pagar(valor);
    }
    
    // Factory method para criar estratégia
    private PagamentoStrategy criarEstrategiaPagamento(String tipo) {
        return switch (tipo.toLowerCase()) {
            case "pix" -> new PixPagamento();
            case "credito" -> new CartaoCreditoPagamento();
            case "debito" -> new CartaoDebitoPagamento();
            case "dinheiro" -> new DinheiroPagamento();
            default -> null;
        };
    }
    
    // Efetuar pagamento (marcar como pago no banco)
    public Pagamento efetuarPagamento(int idPagamento) {
        Pagamento pagamento = buscarPorId(idPagamento);
        pagamento.efetuarPagamento();
        repository.save(pagamento);
        return pagamento;
    }
    
    // Buscar por ID
    public Pagamento buscarPorId(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento ID " + id + " não encontrado"));
    }
    
    // Buscar por pedido
    public Optional<Pagamento> buscarPorPedido(int idPedido) {
        return repository.findByPedido(idPedido);
    }
    
    // Buscar todos
    public List<Pagamento> buscarTodos() {
        return repository.findAll();
    }
    
    // Buscar por cliente
    public List<Pagamento> buscarPorCliente(int idCliente) {
        return repository.findByCliente(idCliente);
    }
    
    // Buscar por status
    public List<Pagamento> buscarPorStatus(String status) {
        return repository.findByStatus(status);
    }
    
    // Verificar se pedido tem pagamento pendente
    public boolean pedidoTemPagamentoPendente(int idPedido) {
        Optional<Pagamento> pagamentoOpt = buscarPorPedido(idPedido);
        return pagamentoOpt.isPresent() && !pagamentoOpt.get().isPago();
    }
    
    // Verificar se pedido foi pago
    public boolean pedidoFoiPago(int idPedido) {
        Optional<Pagamento> pagamentoOpt = buscarPorPedido(idPedido);
        return pagamentoOpt.isPresent() && pagamentoOpt.get().isPago();
    }
    
    // Atualizar status manualmente
    public void atualizarStatus(int idPagamento, String novoStatus) {
        repository.updateStatus(idPagamento, novoStatus);
    }
    
    // Deletar pagamento
    public void deletarPagamento(int id) {
        repository.delete(id);
    }
    
    // Verificar se pagamento existe
    public boolean pagamentoExiste(int id) {
        return repository.existsById(id);
    }
}