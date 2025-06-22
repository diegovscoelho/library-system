package entities;

import java.io.Serializable;

/**
 * Representa um autor de um livro no sistema da biblioteca.
 * Implementa Serializable para permitir que objetos desta classe sejam gravados e lidos de um arquivo.
 */
public class Autor implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int proximoId = 1; // Armazena o próximo ID disponível para um objeto Autor.

    private int idAutor;
    private String nome;
    private String nacionalidade;

    /**
     * Constrói um novo Autor com o nome e nacionalidade especificados.
     * Atribui automaticamente um ID exclusivo ao autor.
     *
     * @param nome A nacionalidade do autor.
     * @param nacionalidade A nacionalidade do autor.
     */
    public Autor(String nome, String nacionalidade) {
        this.idAutor = proximoId++;
        this.nome = nome;
        this.nacionalidade = nacionalidade;
    }

    /**
     * Retorna o ID único do autor.
     * @return O ID do autor.
     */
    public int getIdAutor() { return idAutor; }

    /**
     * Retorna o nome do autor.
     * @return O nome do autor.
     */
    public String getNome() { return nome; }

    /**
     * Retorna a nacionalidade do autor.
     * @return A nacionalidade do autor.
     */
    public String getNacionalidade() { return nacionalidade; }

    /**
     * Define o nome do autor.
     * @param nome O novo nome do autor.
     */
    public void setNome(String nome) { this.nome = nome; }

    /**
     * Define a nacionalidade do autor.
     * @param nacionalidade A nova nacionalidade do autor.
     */
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }

    /**
     * Retorna uma representação de string do objeto Autor.
     * @return Uma string contendo o ID, nome e nacionalidade do autor.
     */
    @Override
    public String toString() {
        return "ID: " + idAutor + " | Nome: " + nome + " | Nacionalidade: " + nacionalidade;
    }

    /**
     * Define o próximo ID disponível para objetos Autor. Isso é usado para persistência de dados.
     * @param proximoId O próximo ID a ser usado.
     */
    public static void setProximoId(int proximoId) {
        Autor.proximoId = proximoId;
    }
}