package com.tecdes.pedido.model.DAO;

import com.tecdes.pedido.model.entity.Produto;

public class TesteProdutoTxt {

    public static void main(String[] args) {

        ProdutoTxtDAO dao = new ProdutoTxtDAO();

        Produto p = new Produto(
            1L,
            "Mouse Gamer",
            129.90,
            "Periféricos",
            "Iluminação RGB"
        );

        dao.salvar(p);

        System.out.println("== PRODUTOS NO ARQUIVO ==");
        dao.listar().forEach(prod -> {
            System.out.println(
                prod.getIdProduto() + " | " +
                prod.getNome() + " | " +
                prod.getPreco() + " | " +
                prod.getCategoria() + " | " +
                prod.getDescricao()
            );
        });
    }
}
