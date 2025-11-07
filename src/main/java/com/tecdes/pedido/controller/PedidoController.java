package com.tecdes.pedido.controller;

import java.util.List;

import com.tecdes.pedido.model.entity.ItemPedido;
import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.service.PedidoService;

// Intermediário entre a View e o Service
public class PedidoController {

    private final PedidoService service = new PedidoService();

    public void salvar(List<ItemPedido> itens, String tipoPagamento) {
        service.salvarPedido(itens, tipoPagamento); 
    }

    public List<Pedido> listarTodos() {
        return service.buscarTodos();
    }

    public Pedido buscarPorId(int id) {
        return service.buscarPorId(id);
    }

    public void atualizar(Pedido pedido) {
        service.atualizarPedido(pedido);
    }

    public void excluir(int id) {
        service.excluirPedido(id);
    }
}