package entities;

import java.io.Serializable;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int proximoId = 1;

    private int idUsuario;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    public Usuario(String nome, String cpf, String telefone, String email) { //
        this.idUsuario = proximoId++; //
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    public int getIdUsuario() { return idUsuario; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getTelefone() { return telefone; }
    public String getEmail() { return email; }

    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "ID: " + idUsuario + " | Nome: '" + nome + "' | CPF: " + cpf;
    }

    public static void setProximoId(int proximoId) {
        Usuario.proximoId = proximoId;
    }
}