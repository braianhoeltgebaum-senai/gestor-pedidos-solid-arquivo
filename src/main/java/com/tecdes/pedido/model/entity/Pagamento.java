package com.tecdes.pedido.model.entity;


import java.math.BigDecimal;
import java.time.LocalDateTime;


public class Pagamento {
    private int idPagamento;           // id_pagamento INT
    private int idCliente;             // id_cliente INT (FK)
    private int idPedido;              // id_pedido INT (FK)
    private String tpPagamento;        // tp_pagamento VARCHAR(8) - 'Credito', 'Debito', 'Pix', 'Dinheiro'
    private BigDecimal vlTotal;        // vl_total DECIMAL(6,2)
    private String stPagamento;        // st_pagamento VARCHAR(9) - 'Paga', 'Não Pago'
    private LocalDateTime dtPagamento; // dt_pagamento DATETIME


    public Pagamento() {
        this.dtPagamento = LocalDateTime.now();
    }


    public Pagamento(int idCliente, int idPedido, String tpPagamento, BigDecimal vlTotal) {
        this();
        this.idCliente = idCliente;
        this.idPedido = idPedido;
        this.tpPagamento = tpPagamento;
        this.vlTotal = vlTotal;
        this.stPagamento = "Não Pago"; // Por padrão, não pago
    }


    // Getters e Setters
    public int getIdPagamento() {
        return idPagamento;
    }


    public void setIdPagamento(int idPagamento) {
        this.idPagamento = idPagamento;
    }


    public int getIdCliente() {
        return idCliente;
    }


    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }


    public int getIdPedido() {
        return idPedido;
    }


    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }


    public String getTpPagamento() {
        return tpPagamento;
    }


    public void setTpPagamento(String tpPagamento) {
        String[] tiposValidos = {"Credito", "Debito", "Pix", "Dinheiro"};
        if (tpPagamento == null) {
            throw new IllegalArgumentException("Tipo de pagamento é obrigatório");
        }
       
        boolean valido = false;
        for (String tipo : tiposValidos) {
            if (tipo.equalsIgnoreCase(tpPagamento)) {
                this.tpPagamento = tipo; // Padroniza capitalização
                valido = true;
                break;
            }
        }
       
        if (!valido) {
            throw new IllegalArgumentException("Tipo de pagamento inválido. Use: Credito, Debito, Pix, Dinheiro");
        }
    }


    public BigDecimal getVlTotal() {
        return vlTotal;
    }


    public void setVlTotal(BigDecimal vlTotal) {
        if (vlTotal == null || vlTotal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor total deve ser maior que zero");
        }
        this.vlTotal = vlTotal;
    }


    public String getStPagamento() {
        return stPagamento;
    }


    public void setStPagamento(String stPagamento) {
        if (stPagamento == null || (!stPagamento.equals("Paga") && !stPagamento.equals("Não Pago"))) {
            throw new IllegalArgumentException("Status inválido. Use: 'Paga' ou 'Não Pago'");
        }
        this.stPagamento = stPagamento;
       
        // Se pagamento for marcado como "Paga", atualiza data
        if (stPagamento.equals("Paga")) {
            this.dtPagamento = LocalDateTime.now();
        }
    }


    public LocalDateTime getDtPagamento() {
        return dtPagamento;
    }


    public void setDtPagamento(LocalDateTime dtPagamento) {
        this.dtPagamento = dtPagamento;
    }


    public boolean isPago() {
        return "Paga".equals(stPagamento);
    }


    public void efetuarPagamento() {
        this.stPagamento = "Paga";
        this.dtPagamento = LocalDateTime.now();
    }


    @Override
    public String toString() {
        String status = isPago() ? "✅ Pago" : "❌ Pendente";
        return "Pagamento #" + idPagamento + " - " + tpPagamento +
               " - R$ " + vlTotal + " - " + status;
    }
}
