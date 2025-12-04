package com.tecdes.pedido.repository;


import com.tecdes.pedido.model.entity.Avaliacao;
import java.util.List;
import java.util.Optional;


public interface AvaliacaoRepository {
   
    Avaliacao save(Avaliacao avaliacao);
    Optional<Avaliacao> findById(int id);          
    Optional<Avaliacao> findByPedidoId(int idPedido);
    List<Avaliacao> findAll();
    void delete(int id);                          
    List<Avaliacao> findByCliente(int idCliente);  
    List<Avaliacao> findByNotaMaiorOuIgual(int notaMinima);
    double calcularMediaGeral();                    
    int contarTotal();                            
}
