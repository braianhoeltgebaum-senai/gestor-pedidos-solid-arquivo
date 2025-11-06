package com.tecdes.pedido.repository;

import java.util.List;

import com.tecdes.pedido.model.entity.Cliente;

public class ClienteRepositoryImpl {
    private final ClienteRepositorylmpl clienteRepositorylmpl = new ClienteRepositorylmpl();

    public void salvar(Cliente cliente) {
        clienteRepositorylmpl.inserir(cliente);
    }

    public List<Cliente> buscarTodos(){
        return clienteRepositorylmpl.buscarTodos();
    }
}
