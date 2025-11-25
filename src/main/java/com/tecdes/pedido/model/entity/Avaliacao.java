package com.tecdes.pedido.model.entity;

public class Avaliacao {

    private int idAvaliacao;
    private int nota;
    private String comentario;

    public Avaliacao(int idAvaliacao, int nota, String comentario) {
        this.idAvaliacao = idAvaliacao;
        this.nota = nota;
        this.comentario = comentario;
    }

    public int getNota() { return nota; }
    public String getComentario() { return comentario; }
}

