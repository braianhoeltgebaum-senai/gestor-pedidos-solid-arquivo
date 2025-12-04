package com.tecdes.pedido.controller;


import java.util.List;
import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.service.PedidoService;
import com.tecdes.pedido.service.ProdutoService;
import com.tecdes.pedido.service.ItemPedidoService;
import com.tecdes.pedido.repository.PedidoRepositoryImpl;
import com.tecdes.pedido.repository.ProdutoRepositoryImpl;


public class PedidoController {


    private final PedidoService pedidoService;


    public PedidoController() {
        // Cria dependências
        ProdutoService produtoService = new ProdutoService(new ProdutoRepositoryImpl());
        ItemPedidoService itemPedidoService = new ItemPedidoService();
        PedidoRepositoryImpl pedidoRepo = new PedidoRepositoryImpl();
       
        // Injeta dependências
        this.pedidoService = new PedidoService(pedidoRepo, produtoService, itemPedidoService);
    }


    // Criar novo pedido (número automático)
    public Pedido criarPedido(int idCliente, int idEndereco) {
        return pedidoService.criarPedido(idCliente, idEndereco);
    }
   
    // Criar pedido com número específico
    public Pedido criarPedido(int idCliente, int idEndereco, int numeroPedido) {
        return pedidoService.criarPedido(idCliente, idEndereco, numeroPedido);
    }


    // Buscar pedido por ID
    public Pedido buscarPorId(int id) {
        return pedidoService.buscarPedidoPorId(id);
    }


    // Listar todos os pedidos
    public List<Pedido> listarTodos() {
        return pedidoService.buscarTodosPedidos();
    }
   
    // Listar pedidos por cliente
    public List<Pedido> listarPorCliente(int idCliente) {
        return pedidoService.buscarPedidosPorCliente(idCliente);
    }
   
    // Listar pedidos por status
    public List<Pedido> listarPorStatus(char status) {
        return pedidoService.buscarPedidosPorStatus(status);
    }


    // Atualizar status do pedido
    public Pedido atualizarStatus(int id, char novoStatus) {
        return pedidoService.atualizarStatus(id, novoStatus);
    }


    // Cancelar pedido
    public void cancelarPedido(int id) {
        pedidoService.cancelarPedido(id);
    }
   
    // Adicionar item ao pedido
    public void adicionarItem(int idPedido, int idProduto, int quantidade) {
        pedidoService.adicionarItemPedido(idPedido, idProduto, quantidade);
    }
   
    // Calcular total do pedido
    public double calcularTotal(int idPedido) {
        return pedidoService.calcularTotalPedido(idPedido);
    }
   
    // Atualizar pedido completo
    public Pedido atualizarPedido(int id, char status, int numeroPedido, int idCliente, int idEndereco) {
        Pedido dadosAtualizados = new Pedido(status, numeroPedido, idCliente, idEndereco);
        return pedidoService.atualizarPedido(id, dadosAtualizados);
    }
   
    // Deletar pedido
    public void deletarPedido(int id) {
        pedidoService.deletarPedido(id);
    }
   
    // Verificar se pedido existe
    public boolean pedidoExiste(int id) {
        return pedidoService.pedidoExiste(id);
    }
   
    // Contar total de pedidos
    public int contarTotal() {
        return pedidoService.contarTotalPedidos();
    }
   
    // Próximo número de pedido
    public int proximoNumeroPedido() {
        return pedidoService.proximoNumeroPedido();
    }
   
    // Validar dados do pedido
    public boolean validarPedido(int idCliente, int idEndereco) {
        if (idCliente <= 0) {
            return false;
        }
        if (idEndereco <= 0) {
            return false;
        }
        return true;
    }
}
