package com.tecdes.pedido.service;

public class CartaoCreditoPagamento implements PagamentoStrategy {
    @Override
    public boolean pagar(double valor) {
        System.out.println("Pagamento de R$ " + valor + " realizado com Cartão de Crédito.");
        return true;
    }
}

package com.tecdes.pedido.service;

public class CartaoDebitoPagamento implements PagamentoStrategy {
    @Override
    public boolean pagar(double valor) {
        System.out.println("Pagamento de R$ " + valor + " realizado com Cartão de Débito.");
        return true;
    }
}

package com.tecdes.pedido.service;

public class DinheiroPagamento implements PagamentoStrategy {
    @Override
    public boolean pagar(double valor) {
        System.out.println("Pagamento de R$ " + valor + " realizado em Dinheiro.");
        return true;
    }
}