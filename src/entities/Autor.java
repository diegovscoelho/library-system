package entities;

import java.io.Serializable;

public class Autor implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int proximoId = 1;

    private int idAutor;
    private String nome;
    private String nacionalidade;

    public Autor(String nome, String nacionalidade) {
        this.idAutor = proximoId++;
        this.nome = nome;
        this.nacionalidade = nacionalidade;
    }

    public int getIdAutor() { return idAutor; }
    public String getNome() { return nome; }
    public String getNacionalidade() { return nacionalidade; }

    public void setNome(String nome) { this.nome = nome; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }

    @Override
    public String toString() {
        return "ID: " + idAutor + " | Nome: " + nome + " | Nacionalidade: " + nacionalidade;
    }

    public static void setProximoId(int proximoId) {
        Autor.proximoId = proximoId;
    }
}