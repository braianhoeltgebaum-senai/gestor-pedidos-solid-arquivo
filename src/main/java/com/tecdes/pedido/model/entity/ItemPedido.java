package com.tecdes.pedido.model.entity;

public class ItemPedido {
    private int idItem;        // id_item INT
    private int qtProduto;     // qt_produto INT
    private int idPedido;      // id_pedido INT (FK)
    private int idProduto;     // id_produto INT (FK)

    public ItemPedido() {}

    public ItemPedido(int qtProduto, int idPedido, int idProduto) {
        this.qtProduto = qtProduto;
        this.idPedido = idPedido;
        this.idProduto = idProduto;
    }

    // Getters e Setters
    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public int getQtProduto() {
        return qtProduto;
    }

    public void setQtProduto(int qtProduto) {
        if (qtProduto <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        this.qtProduto = qtProduto;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    @Override
    public String toString() {
        return "Item #" + idItem + " - Qtd: " + qtProduto + 
               " (Pedido: " + idPedido + ", Produto: " + idProduto + ")";
    }
}