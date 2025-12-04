package com.tecdes.pedido.repository;

import com.tecdes.pedido.model.entity.Funcionario;
import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository {
    void save(Funcionario funcionario);
    Optional<Funcionario> findByCpf(String cpf);
    List<Funcionario> findAll();
    List<Funcionario> findByCargo(String cargo);
}