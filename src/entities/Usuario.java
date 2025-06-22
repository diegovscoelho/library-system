package entities;

import java.io.Serializable;

/**
 * Representa um usuário do sistema da biblioteca.
 * Implementa Serializable para permitir que objetos desta classe sejam gravados e lidos de um arquivo.
 */
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int proximoId = 1; // Armazena o próximo ID disponível para um objeto Usuario.

    private int idUsuario;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    /**
     * Constrói um novo Usuario com o nome, CPF, telefone e email especificados.
     * Atribui automaticamente um ID exclusivo ao usuário.
     *
     * @param nome O nome completo do usuário.
     * @param cpf O CPF (Cadastro de Pessoas Físicas) do usuário.
     * @param telefone O número de telefone do usuário.
     * @param email O endereço de email do usuário.
     */
    public Usuario(String nome, String cpf, String telefone, String email) {
        this.idUsuario = proximoId++;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    /**
     * Retorna o ID único do usuário.
     * @return O ID do usuário.
     */
    public int getIdUsuario() { return idUsuario; }

    /**
     * Retorna o nome do usuário.
     * @return O nome do usuário.
     */
    public String getNome() { return nome; }

    /**
     * Retorna o CPF do usuário.
     * @return O CPF do usuário.
     */
    public String getCpf() { return cpf; }

    /**
     * Retorna o número de telefone do usuário.
     * @return O número de telefone do usuário.
     */
    public String getTelefone() { return telefone; }

    /**
     * Retorna o endereço de email do usuário.
     * @return O endereço de email do usuário.
     */
    public String getEmail() { return email; }

    /**
     * Define o nome do usuário.
     * @param nome O novo nome do usuário.
     */
    public void setNome(String nome) { this.nome = nome; }

    /**
     * Define o CPF do usuário.
     * @param cpf O novo CPF do usuário.
     */
    public void setCpf(String cpf) { this.cpf = cpf; }

    /**
     * Define o número de telefone do usuário.
     * @param telefone O novo número de telefone do usuário.
     */
    public void setTelefone(String telefone) { this.telefone = telefone; }

    /**
     * Define o endereço de email do usuário.
     * @param email O novo endereço de email do usuário.
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Retorna uma representação de string do objeto Usuario.
     * Inclui o ID, nome e CPF do usuário.
     * @return Uma string contendo os detalhes do usuário.
     */
    @Override
    public String toString() {
        return "ID: " + idUsuario + " | Nome: '" + nome + "' | CPF: " + cpf;
    }

    /**
     * Define o próximo ID disponível para objetos Usuario. Isso é usado para persistência de dados.
     * @param proximoId O próximo ID a ser usado.
     */
    public static void setProximoId(int proximoId) {
        Usuario.proximoId = proximoId;
    }
}