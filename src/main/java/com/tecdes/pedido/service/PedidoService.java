package com.tecdes.pedido.service;

import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.repository.PedidoRepository;
//import java.time.LocalDateTime;
import java.util.List;

public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoService produtoService;
    private final ItemPedidoService itemPedidoService;

    public PedidoService(PedidoRepository pedidoRepository, ProdutoService produtoService, ItemPedidoService itemPedidoService) {
        this.pedidoRepository = pedidoRepository;
        this.produtoService = produtoService;
        this.itemPedidoService = itemPedidoService;
    }

    // Criar novo pedido
    public Pedido criarPedido(int idCliente, int idEndereco) {
        int numeroPedido = pedidoRepository.proximoNumeroPedido();
        Pedido pedido = new Pedido('A', numeroPedido, idCliente, idEndereco);
        return pedidoRepository.save(pedido);
    }
    
    // Criar pedido com número específico
    public Pedido criarPedido(int idCliente, int idEndereco, int numeroPedido) {
        Pedido pedido = new Pedido('A', numeroPedido, idCliente, idEndereco);
        return pedidoRepository.save(pedido);
    }

    // Buscar pedido por ID
    public Pedido buscarPedidoPorId(int id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido ID " + id + " não encontrado."));
    }

    // Listar todos os pedidos
    public List<Pedido> buscarTodosPedidos() {
        return pedidoRepository.findAll();
    }
    
    // Listar pedidos por cliente
    public List<Pedido> buscarPedidosPorCliente(int idCliente) {
        return pedidoRepository.findByCliente(idCliente);
    }
    
    // Listar pedidos por status
    public List<Pedido> buscarPedidosPorStatus(char status) {
        if (status != 'A' && status != 'E' && status != 'P' && status != 'C') {
            throw new IllegalArgumentException("Status inválido. Use: A (Aberto), E (Em preparo), P (Pronto), C (Concluído)");
        }
        return pedidoRepository.findByStatus(status);
    }

    // Atualizar status do pedido
    public Pedido atualizarStatus(int id, char novoStatus) {
        if (novoStatus != 'A' && novoStatus != 'E' && novoStatus != 'P' && novoStatus != 'C') {
            throw new IllegalArgumentException("Status inválido. Use: A, E, P, C");
        }
        
        Pedido pedidoExistente = buscarPedidoPorId(id);
        pedidoExistente.setStPedido(novoStatus);
        
        return pedidoRepository.save(pedidoExistente);
    }

    // Cancelar pedido
    public void cancelarPedido(int id) {
        Pedido pedidoExistente = buscarPedidoPorId(id);

        if (pedidoExistente.getStPedido() == 'C') {
            throw new RuntimeException("Não é possível cancelar um pedido já concluído.");
        }

        pedidoExistente.setStPedido('C');
        pedidoRepository.save(pedidoExistente);
    }
    
    // Adicionar item ao pedido
    public void adicionarItemPedido(int idPedido, int idProduto, int quantidade) {
        buscarPedidoPorId(idPedido); // Valida se pedido existe
        produtoService.buscarPorId(idProduto); // Valida se produto existe
        itemPedidoService.adicionarItem(idPedido, idProduto, quantidade);
    }
    
    // Calcular total do pedido - CORRIGIDO (usa a variável)
    public double calcularTotalPedido(int idPedido) {
        Pedido pedido = buscarPedidoPorId(idPedido);
        
        List<com.tecdes.pedido.model.entity.ItemPedido> itens = 
            itemPedidoService.listarPorPedido(idPedido);
        
        double total = 0.0;
        
        for (com.tecdes.pedido.model.entity.ItemPedido item : itens) {
            com.tecdes.pedido.model.entity.Produto produto = 
                produtoService.buscarPorId(item.getIdProduto());
            
            total += produto.getVlProduto().doubleValue() * item.getQtProduto();
        }
        
        // Opcional: Log para debug
        System.out.println("Total do pedido #" + pedido.getNrPedido() + ": R$ " + total);
        
        return total;
    }
    
    // Atualizar pedido completo
    public Pedido atualizarPedido(int id, Pedido dadosAtualizados) {
        Pedido pedidoExistente = buscarPedidoPorId(id);
        
        pedidoExistente.setStPedido(dadosAtualizados.getStPedido());
        pedidoExistente.setNrPedido(dadosAtualizados.getNrPedido());
        pedidoExistente.setIdCliente(dadosAtualizados.getIdCliente());
        pedidoExistente.setIdEndereco(dadosAtualizados.getIdEndereco());
        
        return pedidoRepository.save(pedidoExistente);
    }
    
    // Deletar pedido
    public void deletarPedido(int id) {
        itemPedidoService.limparItensPedido(id);
        pedidoRepository.delete(id);
    }
    
    // Verificar se pedido existe
    public boolean pedidoExiste(int id) {
        return pedidoRepository.existsById(id);
    }
    
    // Contar total de pedidos
    public int contarTotalPedidos() {
        return pedidoRepository.count();
    }
    
    // Buscar próximo número de pedido
    public int proximoNumeroPedido() {
        return pedidoRepository.proximoNumeroPedido();
    }
}