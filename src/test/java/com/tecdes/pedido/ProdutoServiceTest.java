package com.tecdes.pedido;

import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.repository.ProdutoRepository;
import com.tecdes.pedido.service.ProdutoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

@DisplayName("Testes de Sistema para ProdutoService")
public class ProdutoServiceTest {

    private ProdutoService service;
    private ProdutoRepository repositoryFake;

    private final Long ID_FIXO = 1L;

    // -----------------------------------------------------------------------
    //  FAKE REPOSITORY COMPLETO (Implementa TODOS os métodos da interface)
    // -----------------------------------------------------------------------
    class ProdutoRepositoryFake implements ProdutoRepository {

        private Map<Long, Produto> banco = new HashMap<>();
        private long idSequencia = 1;

        @Override
        public Produto save(Produto produto) {
            if (produto.getIdProduto() == null) {
                produto.setIdProduto(idSequencia++);
            }
            banco.put(produto.getIdProduto(), produto);
            return produto;
        }

        @Override
        public Optional<Produto> findById(Long id) {
            return Optional.ofNullable(banco.get(id));
        }

        @Override
        public List<Produto> findAll() {
            return new ArrayList<>(banco.values());
        }

        @Override
        public void delete(Long id) {
            banco.remove(id);
        }

        // ---- MÉTODOS OBRIGATÓRIOS DA INTERFACE ----

        @Override
        public List<Produto> findByCategoria(String categoria) {
            List<Produto> list = new ArrayList<>();
            for (Produto p : banco.values()) {
                if (p.getCategoria().equalsIgnoreCase(categoria)) {
                    list.add(p);
                }
            }
            return list;
        }

        @Override
        public List<Produto> findByNomeContaining(String nomeParcial) {
            List<Produto> list = new ArrayList<>();
            for (Produto p : banco.values()) {
                if (p.getNome().toLowerCase().contains(nomeParcial.toLowerCase())) {
                    list.add(p);
                }
            }
            return list;
        }
    }

    // Antes de cada teste o sistema inicia limpo
    @BeforeEach
    void setup() {
        repositoryFake = new ProdutoRepositoryFake();
        service = new ProdutoService(repositoryFake);

        // Produto inicial
        service.salvarProduto("Caneta Azul", 10.0, "Escritório", "Caneta esferográfica azul.");
    }

    // ----------------------------------------------------------
    // 1 ● TESTE DE FLUXO PRINCIPAL (Cenário de sucesso completo)
    // ----------------------------------------------------------
    @Test
    @DisplayName("Deve criar, buscar e atualizar um produto com sucesso")
    void deveExecutarFluxoPrincipalComSucesso() {

        Produto novo = service.salvarProduto(
                "Lápis", 3.5, "Escritório", "Lápis HB"
        );

        Produto atualizado = service.atualizarProduto(
                novo.getIdProduto(),
                "Lápis Preto", 4.0, "Escritório", "Lápis HB Preto"
        );

        Produto encontrado = service.buscarPorId(atualizado.getIdProduto());

        assertNotNull(encontrado);
        assertEquals("Lápis Preto", encontrado.getNome());
        assertEquals(4.0, encontrado.getPreco());
    }

    // ----------------------------------------------------------
    // 2 ● TESTE DE FALHA (Regras de negócio)
    // ----------------------------------------------------------
    @Test
    @DisplayName("Não deve permitir salvar produto com nome vazio ou preço inválido")
    void naoDeveSalvarDadosInvalidos() {

        assertThrows(IllegalArgumentException.class, () -> {
            service.salvarProduto("", 10.0, "Cat", "Desc");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            service.salvarProduto("Borracha", 0, "Cat", "Desc");
        });
    }

    // ----------------------------------------------------------
    // 3 ● TESTE DE LIMITE (Boundary Test)
    // ----------------------------------------------------------
    @Test
    @DisplayName("Deve permitir valores mínimos válidos (nome válido e preço > 0)")
    void deveAceitarValoresMinimosValidos() {

        Produto p = service.salvarProduto("X", 0.01, "Teste", "Produto de teste mínimo");

        assertNotNull(p.getIdProduto());
        assertEquals(0.01, p.getPreco());
    }

    // ----------------------------------------------------------
    // 4 ● Teste delete
    // ----------------------------------------------------------
    @Test
    @DisplayName("Deve excluir um produto com sucesso")
    void deveExcluirProduto() {

        Produto produto = service.buscarTodos().get(0);
        Long id = produto.getIdProduto();

        service.deletarProduto(id);

        assertThrows(RuntimeException.class, () -> {
            service.buscarPorId(id);
        });
    }

    // ----------------------------------------------------------
    // 5 ● Teste buscar não encontrado
    // ----------------------------------------------------------
    @Test
    @DisplayName("Deve falhar ao buscar produto inexistente")
    void deveFalharAoBuscarInexistente() {

        assertThrows(RuntimeException.class, () -> {
            service.buscarPorId(999L);
        });
    }
}
