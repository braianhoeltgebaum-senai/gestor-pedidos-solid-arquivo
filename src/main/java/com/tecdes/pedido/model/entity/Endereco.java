package com.tecdes.pedido.model.entity;

public class Endereco {
    private int idEndereco;
    private String sgEstado;        // CHAR(2)
    private String nmCidade;        // VARCHAR(80)
    private String nmBairro;        // VARCHAR(80)
    private char tpLogradouro;      // CHAR(1) - 'R', 'A', 'P', 'V', 'B'
    private String nmLogradouro;    // VARCHAR(80)
    private char tpEndereco;        // CHAR(1) - 'R', 'A', 'C'
    private int nrResidencia;       // INT
    private String obComplemento;   // VARCHAR(150) - pode ser null
    private int idCliente;          // FK para Cliente

    public Endereco() {}

    public Endereco(String sgEstado, String nmCidade, String nmBairro, 
                   char tpLogradouro, String nmLogradouro, char tpEndereco, 
                   int nrResidencia, int idCliente) {
        this.sgEstado = sgEstado;
        this.nmCidade = nmCidade;
        this.nmBairro = nmBairro;
        this.tpLogradouro = tpLogradouro;
        this.nmLogradouro = nmLogradouro;
        this.tpEndereco = tpEndereco;
        this.nrResidencia = nrResidencia;
        this.idCliente = idCliente;
    }

    // Getters e Setters com validações
    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getSgEstado() {
        return sgEstado;
    }

    public void setSgEstado(String sgEstado) {
        if (sgEstado == null || sgEstado.length() != 2) {
            throw new IllegalArgumentException("Estado deve ter 2 caracteres (ex: SP, RJ)");
        }
        this.sgEstado = sgEstado.toUpperCase();
    }

    public String getNmCidade() {
        return nmCidade;
    }

    public void setNmCidade(String nmCidade) {
        if (nmCidade == null || nmCidade.trim().isEmpty()) {
            throw new IllegalArgumentException("Cidade é obrigatória");
        }
        if (nmCidade.length() > 80) {
            throw new IllegalArgumentException("Cidade muito longa (máx 80 caracteres)");
        }
        this.nmCidade = nmCidade;
    }

    public String getNmBairro() {
        return nmBairro;
    }

    public void setNmBairro(String nmBairro) {
        if (nmBairro == null || nmBairro.trim().isEmpty()) {
            throw new IllegalArgumentException("Bairro é obrigatório");
        }
        if (nmBairro.length() > 80) {
            throw new IllegalArgumentException("Bairro muito longo (máx 80 caracteres)");
        }
        this.nmBairro = nmBairro;
    }

    public char getTpLogradouro() {
        return tpLogradouro;
    }

    public void setTpLogradouro(char tpLogradouro) {
        char tipo = Character.toUpperCase(tpLogradouro);
        if (tipo != 'R' && tipo != 'A' && tipo != 'P' && tipo != 'V' && tipo != 'B') {
            throw new IllegalArgumentException("Tipo de logradouro inválido. Use: R, A, P, V, B");
        }
        this.tpLogradouro = tipo;
    }

    public String getNmLogradouro() {
        return nmLogradouro;
    }

    public void setNmLogradouro(String nmLogradouro) {
        if (nmLogradouro == null || nmLogradouro.trim().isEmpty()) {
            throw new IllegalArgumentException("Logradouro é obrigatório");
        }
        if (nmLogradouro.length() > 80) {
            throw new IllegalArgumentException("Logradouro muito longo (máx 80 caracteres)");
        }
        this.nmLogradouro = nmLogradouro;
    }

    public char getTpEndereco() {
        return tpEndereco;
    }

    public void setTpEndereco(char tpEndereco) {
        char tipo = Character.toUpperCase(tpEndereco);
        if (tipo != 'R' && tipo != 'A' && tipo != 'C') {
            throw new IllegalArgumentException("Tipo de endereço inválido. Use: R (Residencial), A (Comercial), C (Cobrança)");
        }
        this.tpEndereco = tipo;
    }

    public int getNrResidencia() {
        return nrResidencia;
    }

    public void setNrResidencia(int nrResidencia) {
        if (nrResidencia <= 0) {
            throw new IllegalArgumentException("Número da residência deve ser maior que zero");
        }
        this.nrResidencia = nrResidencia;
    }

    public String getObComplemento() {
        return obComplemento;
    }

    public void setObComplemento(String obComplemento) {
        if (obComplemento != null && obComplemento.length() > 150) {
            throw new IllegalArgumentException("Complemento muito longo (máx 150 caracteres)");
        }
        this.obComplemento = obComplemento;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        String tipoLogradouro = switch(tpLogradouro) {
            case 'R' -> "Rua";
            case 'A' -> "Avenida";
            case 'P' -> "Praça";
            case 'V' -> "Viela";
            case 'B' -> "Beco";
            default -> "Desconhecido";
        };
        
        return tipoLogradouro + " " + nmLogradouro + ", " + nrResidencia + 
               " - " + nmBairro + ", " + nmCidade + "/" + sgEstado;
    }
}