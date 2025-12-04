package com.tecdes.pedido.repository;

import com.tecdes.pedido.model.entity.Avaliacao;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AvaliacaoRepositoryImpl implements AvaliacaoRepository {
    
    private final Map<Integer, Avaliacao> database = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1); // Começa com 1

    @Override
    public Avaliacao save(Avaliacao avaliacao) {
        if (avaliacao.getIdAvaliacao() == 0) {
            avaliacao.setIdAvaliacao(idGenerator.getAndIncrement());
        }
        database.put(avaliacao.getIdAvaliacao(), avaliacao);
        System.out.println("📝 Avaliação salva: " + avaliacao);
        return avaliacao;
    }

    @Override
    public Optional<Avaliacao> findById(int id) {
        return Optional.ofNullable(database.get(id));
    }
   
    @Override
    public Optional<Avaliacao> findByPedidoId(int idPedido) {
        return database.values().stream()
                .filter(a -> a.getIdPedido() == idPedido)
                .findFirst();
    }

    @Override
    public List<Avaliacao> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public void delete(int id) {
        Avaliacao removida = database.remove(id);
        if (removida != null) {
            System.out.println("🗑️ Avaliação removida: ID " + id);
        }
    }

    @Override
    public List<Avaliacao> findByCliente(int idCliente) {
        return database.values().stream()
                .filter(a -> a.getIdCliente() == idCliente)
                .collect(Collectors.toList());
    }

    @Override
    public List<Avaliacao> findByNotaMaiorOuIgual(int notaMinima) {
        return database.values().stream()
                .filter(a -> a.getVlNota() >= notaMinima)
                .collect(Collectors.toList());
    }

    @Override
    public double calcularMediaGeral() {
        if (database.isEmpty()) {
            return 0.0;
        }
        
        double soma = database.values().stream()
                .mapToInt(Avaliacao::getVlNota)
                .sum();
        
        return soma / database.size();
    }

    @Override
    public int contarTotal() {
        return database.size();
    }
    
    // Método extra para limpar banco em memória (para testes)
    public void limparBanco() {
        database.clear();
        idGenerator.set(1);
        System.out.println("🧹 Banco de avaliações limpo");
    }
}