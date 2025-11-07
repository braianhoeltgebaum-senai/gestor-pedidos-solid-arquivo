package com.tecdes.pedido.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private int id;
    private LocalDateTime dataHora;
    private String status;
    private List<ItemPedido> itens;
    private double valorTotal;
    private String tipoPagamento;

    // Construtor padrão: inicializa o pedido
    public Pedido() {
        this.dataHora = LocalDateTime.now();
        this.status = "Recebido";
        this.itens = new ArrayList<>();
        this.valorTotal = 0.0;
    }

    // Construtor opcional
    public Pedido(int id, String tipoPagamento) {
        this();
        this.id = id;
        this.tipoPagamento = tipoPagamento;
    }

    // Método para adicionar um item ao pedido
    public void adicionarItem(ItemPedido item) {
        this.itens.add(item);
        calcularTotal();
    }

    // Método para recalcular o valor total
    public void calcularTotal() {
        double total = 0.0;
        for (ItemPedido item : itens) {
            total += item.calcularTotal();
        }
        this.valorTotal = total;
    }

    // === Getters e Setters ===
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    @Override
    public String toString() {
        return "Pedido {" +
                "id=" + id +
                ", dataHora=" + dataHora +
                ", status='" + status + '\'' +
                ", valorTotal=" + valorTotal +
                ", tipoPagamento='" + tipoPagamento + '\'' +
                ", itens=" + itens +
                '}';
    }
}
// package com.tecdes.pedido.model.entity;

// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.List;

// public class Pedido {
//     private int id;
//     private LocalDateTime dataHora;
//     private String status;
//     private List<ItemPedido> produtos;
//     private double valorTotal;
//     private String TipoPagamento;
//     @SuppressWarnings("rawtypes")
//     private ArrayList itens;

    
    
//         public void pedido() {
//             this.dataHora = LocalDateTime.now();
//             this.status = "Recebido";
//             this.itens = new ArrayList<>();
//             this.valorTotal = 0.0;

//     }

//     public void calcularTotal () {
//         double total = 0.0;
//         for (Object item : itens) {
//             if (item instanceof ItemPedido) {
//                 total += ((ItemPedido) item).calcularTotal();
//             }
//         }
        
//         this.valorTotal = total;
//     }

//     public int getId() {
//         return id;
//     }

//     public void setId(int id) {
//         this.id = id;
//     }

//     public LocalDateTime getDataHora() {
//         return dataHora;
//     }

//     public void setDataHora(LocalDateTime dataHora) {
//         this.dataHora = dataHora;
//     }

//     public String getStatus() {
//         return status;
//     }

//     public void setStatus(String status) {
//         this.status = status;
//     }

//     public List<ItemPedido> getProdutos() {
//         return produtos;
//     }

//     public void setProdutos(List<ItemPedido> produtos) {
//         this.produtos = produtos;
//     }

//     public double getValorTotal() {
//         return valorTotal;
//     }

//     public void setValorTotal(double valorTotal) {
//         this.valorTotal = valorTotal;
//     }

//     public String getTipoPagamento() {
//         return TipoPagamento;
//     }

//     public void setTipoPagamento(String tipoPagamento) {
//         TipoPagamento = tipoPagamento;
//     }

//     @SuppressWarnings("rawtypes")
//     public ArrayList getItens() {
//         return itens;
//     }

//     public void setItens(@SuppressWarnings("rawtypes") ArrayList itens) {
//         this.itens = itens;
//     }



// }