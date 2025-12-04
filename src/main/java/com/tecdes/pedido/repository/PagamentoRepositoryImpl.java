package com.tecdes.pedido.repository;

import com.tecdes.pedido.model.entity.Pagamento;
import com.tecdes.pedido.model.DAO.PagamentoDAO;
import java.util.List;
import java.util.Optional;

public class PagamentoRepositoryImpl implements PagamentoRepository {
    
    private final PagamentoDAO pagamentoDAO;
    
    public PagamentoRepositoryImpl() {
        this.pagamentoDAO = new PagamentoDAO();
    }

    @Override
    public void save(Pagamento pagamento) {
        if (pagamento.getIdPagamento() == 0) {
            pagamentoDAO.inserir(pagamento);
        } else {
            pagamentoDAO.atualizar(pagamento);
        }
    }

    @Override
    public Optional<Pagamento> findById(int id) {
        return Optional.ofNullable(pagamentoDAO.buscarPorId(id));
    }

    @Override
    public Optional<Pagamento> findByPedido(int idPedido) {
        return Optional.ofNullable(pagamentoDAO.buscarPorPedido(idPedido));
    }

    @Override
    public List<Pagamento> findAll() {
        return pagamentoDAO.buscarTodos();
    }

    @Override
    public List<Pagamento> findByCliente(int idCliente) {
        return pagamentoDAO.buscarPorCliente(idCliente);
    }

    @Override
    public List<Pagamento> findByStatus(String status) {
        return pagamentoDAO.buscarPorStatus(status);
    }

    @Override
    public void updateStatus(int idPagamento, String novoStatus) {
        pagamentoDAO.atualizarStatus(idPagamento, novoStatus);
    }

    @Override
    public void delete(int id) {
        pagamentoDAO.deletar(id);
    }

    @Override
    public boolean existsById(int id) {
        return pagamentoDAO.buscarPorId(id) != null;
    }
}