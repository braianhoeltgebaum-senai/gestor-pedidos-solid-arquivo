package com.tecdes.pedido.controller;

import com.tecdes.pedido.model.entity.Pagamento;
import com.tecdes.pedido.service.PagamentoService;
import com.tecdes.pedido.repository.PagamentoRepositoryImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class PagamentoController {
    
    private final PagamentoService service;
    
    public PagamentoController() {
        this.service = new PagamentoService(new PagamentoRepositoryImpl());
    }
    
    // Criar pagamento para um pedido
    public Pagamento criarPagamento(int idCliente, int idPedido, String tipo, BigDecimal valor) {
        return service.criarPagamento(idCliente, idPedido, tipo, valor);
    }
    
    // Processar pagamento (usando Strategy)
    public boolean processarPagamento(String tipo, double valor) {
        return service.processarPagamento(tipo, valor);
    }
    
    // Efetuar pagamento (marcar como pago)
    public Pagamento efetuarPagamento(int idPagamento) {
        return service.efetuarPagamento(idPagamento);
    }
    
    // Buscar por ID
    public Pagamento buscarPorId(int id) {
        return service.buscarPorId(id);
    }
    
    // Buscar por pedido
    public Optional<Pagamento> buscarPorPedido(int idPedido) {
        return service.buscarPorPedido(idPedido);
    }
    
    // Listar todos
    public List<Pagamento> listarTodos() {
        return service.buscarTodos();
    }
    
    // Listar por cliente
    public List<Pagamento> listarPorCliente(int idCliente) {
        return service.buscarPorCliente(idCliente);
    }
    
    // Listar por status
    public List<Pagamento> listarPorStatus(String status) {
        return service.buscarPorStatus(status);
    }
    
    // Verificar se pedido foi pago
    public boolean pedidoFoiPago(int idPedido) {
        return service.pedidoFoiPago(idPedido);
    }
    
    // Verificar se pedido tem pagamento pendente
    public boolean pedidoTemPagamentoPendente(int idPedido) {
        return service.pedidoTemPagamentoPendente(idPedido);
    }
    
    // Deletar pagamento
    public void deletar(int id) {
        service.deletarPagamento(id);
    }
    
    // Validar tipo de pagamento
    public boolean validarTipoPagamento(String tipo) {
        String[] tiposValidos = {"Pix", "Credito", "Debito", "Dinheiro"};
        for (String valido : tiposValidos) {
            if (valido.equalsIgnoreCase(tipo)) {
                return true;
            }
        }
        return false;
    }
}