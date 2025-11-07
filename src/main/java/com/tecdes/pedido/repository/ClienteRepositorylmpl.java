package com.tecdes.pedido.repository;

import java.util.List;

import com.tecdes.pedido.model.entity.Cliente;

public class ClienteRepositorylmpl implements ClienteRepository {
    
    private final ClienteDAO clienteDAO = new ClienteDAO();

    @Override
    public void salvar(Cliente cliente) {
        clienteDAO.inserir(cliente);
            }
        
            private void inserir(Cliente cliente) {
                
            }
        
            @Override
    public List<Cliente> buscarTodos(){
        return clienteDAO.buscarTodos();
    }
}
