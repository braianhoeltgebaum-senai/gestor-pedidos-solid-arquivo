package com.tecdes.pedido.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Funcionario {
    private int idFuncionario;          // id_funcionario INT
    private String nmFuncionario;       // nm_funcionario VARCHAR(100)
    private String dsCargo;             // ds_cargo VARCHAR(50) - "Gerente", "Atendente", etc.
    private String nrTelefone;          // nr_telefone CHAR(15)
    private String nrCpf;               // nr_cpf CHAR(14) - pode ser usado como login
    private LocalDateTime dtAdmissao;   // dt_admissao DATETIME
    private LocalDateTime dtDemissao;   // dt_demissao DATETIME (pode ser null)
    private BigDecimal vlSalario;       // vl_salario DECIMAL(6,2)
    private String senhaAcesso;         // Nova: senha para login 

    public Funcionario() {
        this.dtAdmissao = LocalDateTime.now();
    }

    public Funcionario(String nmFuncionario, String dsCargo, String nrTelefone, 
                       String nrCpf, BigDecimal vlSalario, String senhaAcesso) {
        this();
        this.nmFuncionario = nmFuncionario;
        this.dsCargo = dsCargo;
        this.nrTelefone = nrTelefone;
        this.nrCpf = nrCpf;
        this.vlSalario = vlSalario;
        this.senhaAcesso = senhaAcesso;
    }

    // Getters e Setters
    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNmFuncionario() {
        return nmFuncionario;
    }

    public void setNmFuncionario(String nmFuncionario) {
        if (nmFuncionario == null || nmFuncionario.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do funcionário é obrigatório");
        }
        if (nmFuncionario.length() > 100) {
            throw new IllegalArgumentException("Nome muito longo (máx 100 caracteres)");
        }
        this.nmFuncionario = nmFuncionario;
    }

    public String getDsCargo() {
        return dsCargo;
    }

    public void setDsCargo(String dsCargo) {
        if (dsCargo == null || dsCargo.trim().isEmpty()) {
            throw new IllegalArgumentException("Cargo é obrigatório");
        }
        if (dsCargo.length() > 50) {
            throw new IllegalArgumentException("Cargo muito longo (máx 50 caracteres)");
        }
        this.dsCargo = dsCargo;
    }

    public String getNrTelefone() {
        return nrTelefone;
    }

    public void setNrTelefone(String nrTelefone) {
        if (nrTelefone == null || nrTelefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone é obrigatório");
        }
        if (nrTelefone.length() > 15) {
            throw new IllegalArgumentException("Telefone muito longo (máx 15 caracteres)");
        }
        this.nrTelefone = nrTelefone;
    }

    public String getNrCpf() {
        return nrCpf;
    }

    public void setNrCpf(String nrCpf) {
        // Validação básica de CPF (formato: 000.000.000-00)
        if (nrCpf == null || !nrCpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            throw new IllegalArgumentException("CPF inválido. Formato: 000.000.000-00");
        }
        this.nrCpf = nrCpf;
    }

    public LocalDateTime getDtAdmissao() {
        return dtAdmissao;
    }

    public void setDtAdmissao(LocalDateTime dtAdmissao) {
        this.dtAdmissao = dtAdmissao;
    }

    public LocalDateTime getDtDemissao() {
        return dtDemissao;
    }

    public void setDtDemissao(LocalDateTime dtDemissao) {
        if (dtDemissao != null && dtDemissao.isBefore(dtAdmissao)) {
            throw new IllegalArgumentException("Data de demissão não pode ser anterior à admissão");
        }
        this.dtDemissao = dtDemissao;
    }

    public BigDecimal getVlSalario() {
        return vlSalario;
    }

    public void setVlSalario(BigDecimal vlSalario) {
        if (vlSalario == null || vlSalario.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Salário deve ser maior que zero");
        }
        this.vlSalario = vlSalario;
    }

    public String getSenhaAcesso() {
        return senhaAcesso;
    }

    public void setSenhaAcesso(String senhaAcesso) {
        if (senhaAcesso == null || senhaAcesso.length() < 4) {
            throw new IllegalArgumentException("Senha deve ter no mínimo 4 caracteres");
        }
        this.senhaAcesso = senhaAcesso;
    }

    
    // Verifica se CPF e senha correspondem
    public boolean autenticar(String cpf, String senha) {
        if (cpf == null || senha == null) {
            return false;
        }
        // Remove pontos e traço do CPF para comparação
        String cpfLimpo = this.nrCpf.replace(".", "").replace("-", "");
        String cpfDigitadoLimpo = cpf.replace(".", "").replace("-", "");
        
        return cpfLimpo.equals(cpfDigitadoLimpo) && 
               this.senhaAcesso.equals(senha);
    }
    
    // Verifica cargo
    public boolean isGerente() {
        return "Gerente".equalsIgnoreCase(dsCargo) || 
               "Administrador".equalsIgnoreCase(dsCargo);
    }
    
    public boolean isAtendente() {
        return "Atendente".equalsIgnoreCase(dsCargo) || 
               "Vendedor".equalsIgnoreCase(dsCargo) ||
               "Caixa".equalsIgnoreCase(dsCargo);
    }
    
    public boolean isAtivo() {
        return dtDemissao == null;
    }
    
    // Verifica permissões
    public boolean podeGerenciar() {
        return isGerente() && isAtivo();
    }
    
    public boolean podeAtender() {
        return (isGerente() || isAtendente()) && isAtivo();
    }

    @Override
    public String toString() {
        return nmFuncionario + " - " + dsCargo + 
               (isAtivo() ? " (Ativo)" : " (Inativo)") + 
               " - CPF: " + nrCpf;
    }
}
