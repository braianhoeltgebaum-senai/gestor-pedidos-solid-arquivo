package com.tecdes.pedido.model.entity;

import java.time.LocalDateTime;

public class Entrega {
    private int idEntrega;              // id_entrega INT
    private int idFuncionario;          // id_funcionario INT (FK)
    private int idPedido;               // id_pedido INT (FK)
    private LocalDateTime dtEntregaPrev; // dt_entrega_prev DATETIME
    private LocalDateTime dtEntregaReal; // dt_entrega_real DATETIME (pode ser null)
    private String stEntrega;           // st_entrega VARCHAR(9) - 'Saiu', 'Entregue'
    private String obEntrega;           // ob_entrega VARCHAR(255) (pode ser null)

    public Entrega() {
        this.dtEntregaPrev = LocalDateTime.now().plusDays(2); // Entrega prevista em 2 dias
    }

    public Entrega(int idFuncionario, int idPedido, String stEntrega) {
        this();
        this.idFuncionario = idFuncionario;
        this.idPedido = idPedido;
        this.stEntrega = stEntrega;
    }

    // Getters e Setters
    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public LocalDateTime getDtEntregaPrev() {
        return dtEntregaPrev;
    }

    public void setDtEntregaPrev(LocalDateTime dtEntregaPrev) {
        if (dtEntregaPrev == null) {
            throw new IllegalArgumentException("Data prevista é obrigatória");
        }
        this.dtEntregaPrev = dtEntregaPrev;
    }

    public LocalDateTime getDtEntregaReal() {
        return dtEntregaReal;
    }

    public void setDtEntregaReal(LocalDateTime dtEntregaReal) {
        this.dtEntregaReal = dtEntregaReal;
    }

    public String getStEntrega() {
        return stEntrega;
    }

    public void setStEntrega(String stEntrega) {
        if (stEntrega == null || (!stEntrega.equals("Saiu") && !stEntrega.equals("Entregue"))) {
            throw new IllegalArgumentException("Status inválido. Use: 'Saiu' ou 'Entregue'");
        }
        this.stEntrega = stEntrega;
        
        // Se status for "Entregue", registra data real
        if (stEntrega.equals("Entregue") && dtEntregaReal == null) {
            this.dtEntregaReal = LocalDateTime.now();
        }
    }

    public String getObEntrega() {
        return obEntrega;
    }

    public void setObEntrega(String obEntrega) {
        if (obEntrega != null && obEntrega.length() > 255) {  // CORRIGIDO AQUI
            throw new IllegalArgumentException("Observação muito longa (máx 255 caracteres)");
        }
        this.obEntrega = obEntrega;
    }

    public boolean isEntregue() {
        return "Entregue".equals(stEntrega);
    }

    public boolean isAtrasada() {
        return !isEntregue() && LocalDateTime.now().isAfter(dtEntregaPrev);
    }

    @Override
    public String toString() {
        String status = isEntregue() ? "✅ Entregue" : (isAtrasada() ? "⚠️ Atrasada" : "⏳ Em andamento");
        return "Entrega #" + idEntrega + " - Pedido: " + idPedido + " - " + status;
    }
}