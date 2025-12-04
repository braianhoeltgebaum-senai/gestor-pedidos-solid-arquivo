package com.tecdes.pedido.repository;

import com.tecdes.pedido.model.entity.Funcionario;
import com.tecdes.pedido.model.DAO.FuncionarioDAO;
import java.util.List;
import java.util.Optional;

public class FuncionarioRepositoryImpl implements FuncionarioRepository {
    
    private final FuncionarioDAO funcionarioDAO;
    
    public FuncionarioRepositoryImpl() {
        this.funcionarioDAO = new FuncionarioDAO();
    }
    
    @Override
    public void save(Funcionario funcionario) {
        funcionarioDAO.inserir(funcionario);
    }
    
    @Override
    public Optional<Funcionario> findByCpf(String cpf) {
        return Optional.ofNullable(funcionarioDAO.buscarPorCpf(cpf));
    }
    
    @Override
    public List<Funcionario> findAll() {
        return funcionarioDAO.buscarTodos();
    }
    
    @Override
    public List<Funcionario> findByCargo(String cargo) {
        // Se não implementou no DAO, filtra aqui
        List<Funcionario> todos = findAll();
        return todos.stream()
                .filter(f -> f.getDsCargo().equalsIgnoreCase(cargo))
                .toList();
    }
}