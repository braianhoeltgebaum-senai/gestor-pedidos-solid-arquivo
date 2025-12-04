package com.tecdes.pedido.controller;

import java.util.List;
import com.tecdes.pedido.model.entity.Cliente;
import com.tecdes.pedido.service.ClienteService;
import com.tecdes.pedido.repository.ClienteRepositoryImpl;

public class ClienteController {
   
    private final ClienteService service;

    public ClienteController() {
        this.service = new ClienteService(new ClienteRepositoryImpl());
    }

    // CORRIGIDO: Agora recebe os 4 parâmetros corretos
    public void salvar(String nome, String cadastro, String email, String telefone) {
        Cliente novoCliente = new Cliente(nome, cadastro, email, telefone);
        service.cadastrarCliente(novoCliente);
    }

    // CORRIGIDO: Mudar Long para int
    public Cliente buscarPorId(int idCliente) {
        return service.buscarClientePorId(idCliente);
    }

    // CORRIGIDO: Mudar Long para int e receber 4 parâmetros
    public void atualizar(int idCliente, String nome, String cadastro, String email, String telefone) {
        Cliente dadosNovos = new Cliente();
        dadosNovos.setIdCliente(idCliente);
        dadosNovos.setNmCliente(nome);
        dadosNovos.setNrCadastro(cadastro);
        dadosNovos.setDsEmail(email);
        dadosNovos.setNrTelefone(telefone);
        
        service.atualizarCliente(idCliente, dadosNovos);
    }

    // CORRIGIDO: Mudar Long para int
    public void excluir(int idCliente) {
        service.excluirCliente(idCliente);
    }

    // MANTIDO: Já está correto
    public List<Cliente> listarTodos() {
        return service.buscarTodosClientes();
    }

    // MÉTODO ADICIONAL: Buscar por email
    public Cliente buscarPorEmail(String email) {
        return service.buscarClientePorEmail(email);
    }

    // MÉTODO ADICIONAL: Validar dados antes de salvar
    public boolean validarCliente(String nome, String cadastro, String email, String telefone) {
        try {
            Cliente teste = new Cliente();
            teste.setNmCliente(nome);
            teste.setNrCadastro(cadastro);
            teste.setDsEmail(email);
            teste.setNrTelefone(telefone);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}