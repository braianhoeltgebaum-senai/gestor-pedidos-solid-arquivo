package com.tecdes.pedido.model.entity;

public class ItemPedido {
    private int id;
    private Produto produto;
    private int quantidade;
    private double precoUnitario;
    private String observacoes;

    // getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Produto getProduto() {
        return produto;
    }
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public double getPrecoUnitario() {
        return precoUnitario;
    }
    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
    public String getObservacoes() {
        return observacoes;
    }
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    //metodo para calcular o total do item
    public double calcularTotal() {
        return this.precoUnitario * this.quantidade;
    }
    
    


}
