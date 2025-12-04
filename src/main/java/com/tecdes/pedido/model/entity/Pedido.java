package com.tecdes.pedido.model.entity;

import java.time.LocalDateTime;

public class Pedido {
    private int idPedido;           // id_pedido INT
    private char stPedido;          // st_pedido CHAR(1) - 'A', 'E', 'P', 'C'
    private int nrPedido;           // nr_pedido INT
    private int idCliente;          // id_cliente INT (FK)
    private int idEndereco;         // id_endereco INT (FK)
    private LocalDateTime dtPedido = LocalDateTime.now();

    public Pedido() {}

    public Pedido(char stPedido, int nrPedido, int idCliente, int idEndereco) {
        this.stPedido = stPedido;
        this.nrPedido = nrPedido;
        this.idCliente = idCliente;
        this.idEndereco = idEndereco;
    }

    // Getters e Setters
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public char getStPedido() {
        return stPedido;
    }

    public void setStPedido(char stPedido) {
        char status = Character.toUpperCase(stPedido);
        if (status != 'A' && status != 'E' && status != 'P' && status != 'C') {
            throw new IllegalArgumentException("Status inválido. Use: A (Aberto), E (Entregue), P (Pendente), C (Cancelado)");
        }
        this.stPedido = status;
    }

    public int getNrPedido() {
        return nrPedido;
    }

    public void setNrPedido(int nrPedido) {
        this.nrPedido = nrPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public LocalDateTime getDtPedido() {
        return dtPedido;
    }

    public void setDtPedido(LocalDateTime dtPedido) {
        this.dtPedido = dtPedido;
    }

    public String getStatusDescricao() {
        return switch(stPedido) {
            case 'A' -> "Aberto";
            case 'E' -> "Entregue";
            case 'P' -> "Pendente";
            case 'C' -> "Cancelado";
            default -> "Desconhecido";
        };
    }

    @Override
    public String toString() {
        return "Pedido #" + nrPedido + " - Status: " + getStatusDescricao() + 
               " - Cliente ID: " + idCliente + " - Data: " + dtPedido;
    }
}