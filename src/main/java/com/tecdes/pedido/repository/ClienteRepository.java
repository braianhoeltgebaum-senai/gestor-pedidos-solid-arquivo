package com.tecdes.pedido.repository;

import java.util.List;

import com.tecdes.pedido.model.entity.Cliente;

public abstract class ClienteRepository {
    abstract void salvar(Cliente cliente);
    abstract List<Cliente> buscarTodos();
}
