package com.tecdes.pedido.repository;

import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.model.DAO.PedidoDAO;
import java.util.List;
import java.util.Optional;

public class PedidoRepositoryImpl implements PedidoRepository {
    
    private final PedidoDAO pedidoDAO;
    
    public PedidoRepositoryImpl() {
        this.pedidoDAO = new PedidoDAO();
    }

    @Override
    public Pedido save(Pedido pedido) {
        if (pedido.getIdPedido() == 0) {
            // É um novo pedido (inserir)
            pedidoDAO.inserir(pedido);
        } else {
            // É uma atualização
            pedidoDAO.atualizar(pedido);
        }
        return pedido;
    }

    @Override
    public Optional<Pedido> findById(int id) {
        Pedido pedido = pedidoDAO.buscarPorId(id);
        return Optional.ofNullable(pedido);
    }

    @Override
    public List<Pedido> findAll() {
        return pedidoDAO.buscarTodos();
    }

    @Override
    public void delete(int id) {
        pedidoDAO.deletar(id);
    }
    
    @Override
    public boolean existsById(int id) {
        return pedidoDAO.buscarPorId(id) != null;
    }
    
    @Override
    public List<Pedido> findByCliente(int idCliente) {
        return pedidoDAO.buscarPorCliente(idCliente);
    }
    
    @Override
    public List<Pedido> findByStatus(char status) {
        return pedidoDAO.buscarPorStatus(status);
    }
    
    @Override
    public int count() {
        return pedidoDAO.contarTotal();
    }
    
    @Override
    public int proximoNumeroPedido() {
        return pedidoDAO.proximoNumeroPedido();
    }
}