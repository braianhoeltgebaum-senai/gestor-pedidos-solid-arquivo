package com.tecdes.pedido.model.entity;

import java.time.LocalDateTime;

public class Avaliacao {
    private int idAvaliacao;        // id_avaliacao INT
    private int idPedido;           // id_pedido INT (FK)
    private int idCliente;          // id_cliente INT (FK)
    private String dsAvaliacao;     // ds_avaliacao VARCHAR(255) - pode ser null
    private LocalDateTime dtAvaliacao = LocalDateTime.now();
    private int vlNota;             // vl_nota INT (0-10)

    public Avaliacao() {}

    public Avaliacao(int idPedido, int idCliente, int vlNota, String dsAvaliacao) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        setVlNota(vlNota);
        setDsAvaliacao(dsAvaliacao);
    }

    // Getters e Setters
    public int getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(int idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getDsAvaliacao() {
        return dsAvaliacao;
    }

    public void setDsAvaliacao(String dsAvaliacao) {
        if (dsAvaliacao != null && dsAvaliacao.length() > 255) {
            throw new IllegalArgumentException("Avaliação muito longa (máx 255 caracteres)");
        }
        this.dsAvaliacao = dsAvaliacao;
    }

    public LocalDateTime getDtAvaliacao() {
        return dtAvaliacao;
    }

    public void setDtAvaliacao(LocalDateTime dtAvaliacao) {
        this.dtAvaliacao = dtAvaliacao;
    }

    public int getVlNota() {
        return vlNota;
    }

    public void setVlNota(int vlNota) {
        if (vlNota < 0 || vlNota > 10) {
            throw new IllegalArgumentException("Nota deve ser entre 0 e 10");
        }
        this.vlNota = vlNota;
    }

    // Método para compatibilidade com Service (getNota())
    public int getNota() {
        return vlNota;
    }

    // Método para exibir estrelas
    public String getEstrelas() {
        return "★".repeat(vlNota) + "☆".repeat(10 - vlNota);
    }

    // Método para saber se tem comentário
    public boolean temComentario() {
        return dsAvaliacao != null && !dsAvaliacao.trim().isEmpty();
    }

    @Override
    public String toString() {
        String base = "Avaliação #" + idAvaliacao + ": " + getEstrelas() + " (" + vlNota + "/10)";
        if (temComentario()) {
            return base + " - \"" + dsAvaliacao + "\"";
        }
        return base;
    }
}