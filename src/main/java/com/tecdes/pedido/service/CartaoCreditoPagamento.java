package com.tecdes.pedido.service;

public class CartaoCreditoPagamento implements PagamentoStrategy {
    @Override
    public boolean pagar(double valor) {
        System.out.println("Pagamento de R$ " + valor + " realizado com Cartão de Crédito.");
        return true;
    }
}