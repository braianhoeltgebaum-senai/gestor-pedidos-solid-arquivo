package com.tecdes.pedido.repository;

import com.tecdes.pedido.model.entity.Pagamento;
import java.util.List;
import java.util.Optional;

public interface PagamentoRepository {
    void save(Pagamento pagamento);
    Optional<Pagamento> findById(int id);
    Optional<Pagamento> findByPedido(int idPedido);
    List<Pagamento> findAll();
    List<Pagamento> findByCliente(int idCliente);
    List<Pagamento> findByStatus(String status);
    void updateStatus(int idPagamento, String novoStatus);
    void delete(int id);
    boolean existsById(int id);
}