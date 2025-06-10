package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Livro implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int proximoId = 1;

    private int idLivro;
    private String titulo;
    private String isbn;
    private int anoPublicacao;
    private String editora;
    private int quantidadeTotal;
    private int quantidadeDisponivel;
    private ArrayList<Autor> autores; // Modificado de String para ArrayList<Autor>

    public Livro(String titulo, String isbn, int anoPublicacao, String editora, int quantidadeTotal, ArrayList<Autor> autores) {
        this.idLivro = proximoId++;
        this.titulo = titulo;
        this.isbn = isbn;
        this.anoPublicacao = anoPublicacao;
        this.editora = editora;
        this.quantidadeTotal = quantidadeTotal;
        this.quantidadeDisponivel = quantidadeTotal;
        this.autores = autores;
    }

    public int getIdLivro() { return idLivro; }
    public String getTitulo() { return titulo; }
    public ArrayList<Autor> getAutores() { return autores; } // Modificado
    public String getIsbn() { return isbn; }
    public int getAnoPublicacao() { return anoPublicacao; }
    public String getEditora() { return editora; }
    public int getQuantidadeTotal() { return quantidadeTotal; }
    public int getQuantidadeDisponivel() { return quantidadeDisponivel; }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setAutores(ArrayList<Autor> autores) { this.autores = autores; } // Modificado
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setAnoPublicacao(int anoPublicacao) { this.anoPublicacao = anoPublicacao; }
    public void setEditora(String editora) { this.editora = editora; }
    public void setQuantidadeTotal(int quantidadeTotal) { this.quantidadeTotal = quantidadeTotal; }

    public boolean decrementarDisponiveis() { //
        if (quantidadeDisponivel > 0) {
            quantidadeDisponivel--;
            return true;
        }
        return false;
    }

    public void incrementarDisponiveis() { //
        if (quantidadeDisponivel < quantidadeTotal) {
            quantidadeDisponivel++;
        }
    }

    @Override
    public String toString() {
        String nomesAutores = autores.stream()
                .map(Autor::getNome)
                .collect(Collectors.joining(", "));
        return "ID: " + idLivro + " | Título: '" + titulo + "' | Autores: [" + nomesAutores + "] | Disponíveis: " + quantidadeDisponivel;
    }

    public static void setProximoId(int proximoId) {
        Livro.proximoId = proximoId;
    }
}