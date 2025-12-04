package com.tecdes.pedido.model.entity;

import java.math.BigDecimal;

public class Produto {
    private int idProduto;           // id_produto INT
    private String nmProduto;        // nm_produto VARCHAR(60)
    private char tpProduto;          // tp_produto CHAR(1) - 'L', 'B', 'C'
    private String dsProduto;        // ds_produto VARCHAR(150)
    private BigDecimal vlProduto;    // vl_produto DECIMAL(6,3)

    public Produto() {}

    public Produto(String nmProduto, char tpProduto, String dsProduto, BigDecimal vlProduto) {
        this.nmProduto = nmProduto;
        this.tpProduto = tpProduto;
        this.dsProduto = dsProduto;
        this.vlProduto = vlProduto;
    }

    // Getters e Setters
    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNmProduto() {
        return nmProduto;
    }

    public void setNmProduto(String nmProduto) {
        if (nmProduto == null || nmProduto.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }
        if (nmProduto.length() > 60) {
            throw new IllegalArgumentException("Nome do produto muito longo (máx 60 caracteres)");
        }
        this.nmProduto = nmProduto;
    }

    public char getTpProduto() {
        return tpProduto;
    }

    public void setTpProduto(char tpProduto) {
        char tipo = Character.toUpperCase(tpProduto);
        if (tipo != 'L' && tipo != 'B' && tipo != 'C') {
            throw new IllegalArgumentException("Tipo de produto inválido. Use: L (Líquido), B (Bebida), C (Comida)");
        }
        this.tpProduto = tipo;
    }

    public String getDsProduto() {
        return dsProduto;
    }

    public void setDsProduto(String dsProduto) {
        if (dsProduto == null || dsProduto.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição do produto é obrigatória");
        }
        if (dsProduto.length() > 150) {
            throw new IllegalArgumentException("Descrição muito longa (máx 150 caracteres)");
        }
        this.dsProduto = dsProduto;
    }

    public BigDecimal getVlProduto() {
        return vlProduto;
    }

    public void setVlProduto(BigDecimal vlProduto) {
        if (vlProduto == null || vlProduto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do produto deve ser maior que zero");
        }
        this.vlProduto = vlProduto;
    }

    @Override
    public String toString() {
        String tipo = switch(tpProduto) {
            case 'L' -> "Líquido";
            case 'B' -> "Bebida";
            case 'C' -> "Comida";
            default -> "Desconhecido";
        };
        
        return nmProduto + " (" + tipo + ") - R$ " + vlProduto + " - " + dsProduto;
    }
}