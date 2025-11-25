package com.tecdes.pedido.view;

import com.tecdes.pedido.controller.ClienteController;
import com.tecdes.pedido.model.entity.Cliente;

import java.util.List;
import java.util.Scanner;

public class ClienteView {

    private final ClienteController controller = new ClienteController();
    private final Scanner scanner = new Scanner(System.in);

    public void menuPrincipal() {
        int opcao;
        do {
            System.out.println("\n--- MENU CLIENTE ---");
            System.out.println("1. Cadastrar Novo Cliente");
            System.out.println("2. Buscar Cliente por ID");
            System.out.println("3. Listar Todos os Clientes");
            System.out.println("4. Atualizar Cliente");
            System.out.println("5. Excluir Cliente");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); 
                
                switch (opcao) {
                    case 1: cadastrarCliente(); break;
                    case 2: buscarClientePorId(); break;
                    case 3: listarTodosClientes(); break;
                    case 4: atualizarCliente(); break;
                    case 5: excluirCliente(); break;
                    case 0: System.out.println("Retornando..."); break;
                    default: System.err.println("Opção inválida.");
                }
            } catch (java.util.InputMismatchException e) {
                System.err.println("Entrada inválida. Digite um número.");
                scanner.nextLine(); 
                opcao = -1;
            } catch (Exception e) {
                System.err.println("Erro inesperado: " + e.getMessage());
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private void cadastrarCliente() {
        System.out.println("\n--- CADASTRAR CLIENTE ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Telefone: ");
        String fone = scanner.nextLine();
        
        try {
            controller.salvar(nome, fone);
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }
    
    private void buscarClientePorId() {
        System.out.println("\n--- BUSCAR CLIENTE ---");
        System.out.print("ID do Cliente: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();
            
            Cliente cliente = controller.findById(id);
            System.out.println("\n--- DETALHES ---");
            System.out.println("ID: " + cliente.getIdCliente());
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("Fone: " + cliente.getFone());
            System.out.println("----------------");
            
        } catch (java.util.InputMismatchException e) { // <-- CAPTURA PRIMEIRO
            System.err.println("ERRO: ID deve ser um número.");
            scanner.nextLine();
        } catch (RuntimeException e) { 
            System.err.println("ERRO: " + e.getMessage());
        }
    }
    
    private void listarTodosClientes() {
        System.out.println("\n--- LISTA DE CLIENTES ---");
        List<Cliente> clientes = controller.buscarTodos();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        for (Cliente c : clientes) {
            System.out.printf("ID: %d | Nome: %s | Fone: %s\n", 
                                c.getIdCliente(), c.getNome(), c.getFone());
        }
        System.out.println("-------------------------");
    }
    
    private void atualizarCliente() {
        System.out.println("\n--- ATUALIZAR CLIENTE ---");
        System.out.print("ID do Cliente para atualizar: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();
            
            controller.findById(id); 
            
            System.out.print("Novo Nome: ");
            String novoNome = scanner.nextLine();
            System.out.print("Novo Telefone: ");
            String novoFone = scanner.nextLine();
            
            controller.update(id, novoNome, novoFone);
            System.out.println("Cliente atualizado com sucesso!");
            
        } catch (java.util.InputMismatchException e) { // <-- CAPTURA PRIMEIRO
            System.err.println("ERRO: ID deve ser um número.");
            scanner.nextLine();
        } catch (RuntimeException e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }
    
    private void excluirCliente() {
        System.out.println("\n--- EXCLUIR CLIENTE ---");
        System.out.print("ID do Cliente para excluir: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();
            
            controller.delete(id);
            System.out.println("Cliente excluído com sucesso!");
            
        } catch (java.util.InputMismatchException e) { // <-- CAPTURA PRIMEIRO
            System.err.println("ERRO: ID deve ser um número.");
            scanner.nextLine();
        } catch (RuntimeException e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }
}