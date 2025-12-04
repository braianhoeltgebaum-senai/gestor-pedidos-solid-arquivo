com.tecdes.pedido.model.entity;


public class Cliente {
    private int idCliente;           // INT no banco
    private String nmCliente;        // nm_cliente no banco
    private String nrCadastro;       // nr_cadastro no banco
    private String dsEmail;          // ds_email no banco  
    private String nrTelefone;       // nr_telefone no banco


    public Cliente() {}


    public Cliente(String nmCliente, String nrCadastro, String dsEmail, String nrTelefone) {
        this.nmCliente = nmCliente;
        this.nrCadastro = nrCadastro;
        this.dsEmail = dsEmail;
        this.nrTelefone = nrTelefone;
    }


    // Getters e Setters CORRETOS (mantenha todos)
    public int getIdCliente() {
        return idCliente;
    }


    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }


    public String getNmCliente() {
        return nmCliente;
    }


    public void setNmCliente(String nmCliente) {
        if (nmCliente == null || nmCliente.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do cliente é obrigatório");
        }
        if (nmCliente.length() > 60) {
            throw new IllegalArgumentException("Nome muito longo (máx 60 caracteres)");
        }
        this.nmCliente = nmCliente;
    }


    public String getNrCadastro() {
        return nrCadastro;
    }


    public void setNrCadastro(String nrCadastro) {
        if (nrCadastro == null || nrCadastro.trim().isEmpty()) {
            throw new IllegalArgumentException("Número de cadastro é obrigatório");
        }
        if (nrCadastro.length() != 3) {
            throw new IllegalArgumentException("Número de cadastro deve ter 3 caracteres");
        }
        this.nrCadastro = nrCadastro;
    }


    public String getDsEmail() {
        return dsEmail;
    }


    public void setDsEmail(String dsEmail) {
        if (dsEmail == null || dsEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        if (!dsEmail.contains("@") || !dsEmail.contains(".")) {
            throw new IllegalArgumentException("Email inválido");
        }
        if (dsEmail.length() > 150) {
            throw new IllegalArgumentException("Email muito longo (máx 150 caracteres)");
        }
        this.dsEmail = dsEmail;
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


    // ✅ MÉTODOS DE AUTENTICAÇÃO ADICIONADOS:
   
    // Verifica se email e número de cadastro correspondem
    public boolean autenticar(String email, String numeroCadastro) {
        if (email == null || numeroCadastro == null) {
            return false;
        }
        return this.dsEmail.equalsIgnoreCase(email) &&
               this.nrCadastro.equals(numeroCadastro);
    }
   
    // Verifica apenas email (para recuperação de conta)
    public boolean emailCorresponde(String email) {
        return this.dsEmail.equalsIgnoreCase(email);
    }
   
    // Para uso no sistema (identificação)
    public String getTipoUsuario() {
        return "CLIENTE";
    }
   
    public boolean podeFazerPedidos() {
        return true; // Clientes sempre podem fazer pedidos
    }
   
    public String getInfoLogin() {
        return nmCliente + " (Cadastro: " + nrCadastro + ")";
    }


    @Override
    public String toString() {
        return "Cliente [id=" + idCliente +
               ", nome=" + nmCliente +
               ", cadastro=" + nrCadastro +
               ", email=" + dsEmail +
               ", telefone=" + nrTelefone + "]";
    }
}
