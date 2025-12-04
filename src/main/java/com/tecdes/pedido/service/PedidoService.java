package com.tecdes.pedido.service;

import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.model.entity.ItemPedido;
import com.tecdes.pedido.repository.PedidoRepository;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoService produtoService;

    public PedidoService(PedidoRepository pedidoRepository, ProdutoService produtoService) {
        this.pedidoRepository = pedidoRepository;
        this.produtoService = produtoService;
    }

    // NOVO: Criar pedido (método mais adequado para seu banco)
    public Pedido criarPedido(int idCliente, int idEndereco, int numeroPedido) {
        Pedido pedido = new Pedido();
        pedido.setIdCliente(idCliente);
        pedido.setIdEndereco(idEndereco);
        pedido.setNrPedido(numeroPedido);
        pedido.setStPedido('A'); // 'A' = Aberto
        pedido.setDtPedido(LocalDateTime.now());
        
        return pedidoRepository.save(pedido);
    }

    // CORRIGIDO: Mudou de Long para int
    public Pedido buscarPedidoPorId(int id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido ID " + id + " não encontrado."));
    }

    public List<Pedido> buscarTodosPedidos() {
        return pedidoRepository.findAll();
    }
    
    public List<Pedido> buscarTodos() {
        return buscarTodosPedidos();
    }
    
    // NOVO: Buscar por cliente
    public List<Pedido> buscarPedidosPorCliente(int idCliente) {
        return pedidoRepository.findByCliente(idCliente);
    }
    
    // NOVO: Buscar por status
    public List<Pedido> buscarPedidosPorStatus(char status) {
        return pedidoRepository.findByStatus(status);
    }

    // CORRIGIDO: Mudou de Long para int e char para status
    public Pedido atualizarStatus(int id, char novoStatus) {
        // Valida status
        if (novoStatus != 'A' && novoStatus != 'E' && novoStatus != 'P' && novoStatus != 'C') {
            throw new IllegalArgumentException("Status inválido. Use: A, E, P, C");
        }
        
        Pedido pedidoExistente = buscarPedidoPorId(id);
        pedidoExistente.setStPedido(novoStatus);
        
        // Se mudar para concluído, atualiza data de conclusão
        if (novoStatus == 'C') {
            pedidoExistente.setDtConclusao(LocalDateTime.now());
        }
        
        return pedidoRepository.save(pedidoExistente);
    }

    // CORRIGIDO: Mudou de Long para int
    public Pedido cancelarPedido(int id) {
        Pedido pedidoExistente = buscarPedidoPorId(id);

        // Não pode cancelar se já estiver concluído
        if (pedidoExistente.getStPedido() == 'C') {
            throw new RuntimeException("Não é possível cancelar um pedido já concluído.");
        }

        pedidoExistente.setStPedido('X'); // 'X' = Cancelado (você pode definir outro)
        return pedidoRepository.save(pedidoExistente);
    }
    
    // NOVO: Adicionar item ao pedido
    public void adicionarItemPedido(int idPedido, int idProduto, int quantidade) {
        // Verifica se pedido existe
        Pedido pedido = buscarPedidoPorId(idPedido);
        
        // Verifica se produto existe
        produtoService.buscarPorId(idProduto);
        
        // Cria item de pedido
        ItemPedido item = new ItemPedido();
        item.setIdPedido(idPedido);
        item.setIdProduto(idProduto);
        item.setQuantidade(quantidade);
        
        // Aqui você precisaria de um ItemPedidoRepository para salvar
        // itemPedidoRepository.save(item);
    }
    
    // NOVO: Calcular total do pedido
    public double calcularTotalPedido(int idPedido) {
        // Busca o pedido
        Pedido pedido = buscarPedidoPorId(idPedido);
        
        // Aqui você precisaria buscar os itens do pedido e calcular
        // List<ItemPedido> itens = itemPedidoRepository.findByPedido(idPedido);
        // double total = 0;
        // for (ItemPedido item : itens) {
        //     Produto produto = produtoService.buscarPorId(item.getIdProduto());
        //     total += produto.getVlProduto() * item.getQuantidade();
        // }
        // return total;
        
        return 0.0; // Temporário - implemente conforme sua lógica
    }
    
    // NOVO: Método antigo mantido para compatibilidade (se necessário)
    public Pedido finalizarPedido(Pedido novoPedido) {
        // Implementação antiga - ajuste conforme necessário
        novoPedido.setStPedido('C'); // Concluído
        novoPedido.setDtConclusao(LocalDateTime.now());
        return pedidoRepository.save(novoPedido);
    }
}