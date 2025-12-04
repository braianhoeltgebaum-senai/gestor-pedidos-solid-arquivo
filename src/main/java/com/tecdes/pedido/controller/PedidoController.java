package com.tecdes.pedido.controller;

import java.util.List;
import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.service.PedidoService;
import com.tecdes.pedido.service.ProdutoService;
import com.tecdes.pedido.repository.PedidoRepositoryImpl;
import com.tecdes.pedido.repository.ProdutoRepositoryImpl;

public class PedidoController {

    private final PedidoService pedidoService;
    private final ProdutoService produtoService;

    public PedidoController() {
        ProdutoRepositoryImpl produtoRepo = new ProdutoRepositoryImpl();
        this.produtoService = new ProdutoService(produtoRepo);
        
        PedidoRepositoryImpl pedidoRepo = new PedidoRepositoryImpl();
        this.pedidoService = new PedidoService(pedidoRepo, this.produtoService);
    }

    // CORRIGIDO: Recebe parâmetros específicos
    public Pedido criarPedido(int idCliente, int idEndereco, int numeroPedido) {
        return pedidoService.criarPedido(idCliente, idEndereco, numeroPedido);
    }

    // CORRIGIDO: Mudou de Long para int
    public Pedido buscarPedidoPorId(int id) {
        return pedidoService.buscarPedidoPorId(id);
    }

    public List<Pedido> buscarTodosPedidos() {
        return pedidoService.buscarTodosPedidos();
    }
    
    // CORRIGIDO: Mudou de Long para int
    public Pedido atualizarStatus(int id, char novoStatus) {
        // Status: 'A' (Aberto), 'E' (Em preparo), 'P' (Pronto), 'C' (Concluído)
        return pedidoService.atualizarStatus(id, novoStatus);
    }

    // CORRIGIDO: Mudou de Long para int
    public Pedido cancelarPedido(int id) {
        return pedidoService.cancelarPedido(id);
    }
    
    // NOVO: Buscar pedidos por cliente
    public List<Pedido> buscarPedidosPorCliente(int idCliente) {
        return pedidoService.buscarPedidosPorCliente(idCliente);
    }
    
    // NOVO: Buscar pedidos por status
    public List<Pedido> buscarPedidosPorStatus(char status) {
        return pedidoService.buscarPedidosPorStatus(status);
    }
    
    // NOVO: Calcular total do pedido
    public double calcularTotalPedido(int idPedido) {
        return pedidoService.calcularTotalPedido(idPedido);
    }
    
    // NOVO: Adicionar item ao pedido
    public void adicionarItemPedido(int idPedido, int idProduto, int quantidade) {
        pedidoService.adicionarItemPedido(idPedido, idProduto, quantidade);
    }
}