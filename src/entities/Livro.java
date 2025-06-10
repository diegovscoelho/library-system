package entities;

import java.io.Serializable;

public class Livro implements Serializable {
    private static int proximoIdLivro = 1;
    private int idLivro;
    private String titulo;
    private String autor;
    private String isbn;
    private int anoPublicacao;
    private String editora;
    private int quantidadeTotal;
    private int quantidadeDisponivel;

    public Livro(String titulo, String autor, String isbn, int anoPublicacao, String editora, int quantidadeTotal) {
        this.idLivro = proximoIdLivro++;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.anoPublicacao = anoPublicacao;
        this.editora = editora;
        this.quantidadeTotal = quantidadeTotal;
        this.quantidadeDisponivel = quantidadeTotal;
    }

    public int getIdLivro() { return idLivro; }

    public String getTitulo() { return titulo; }

    public String getAutor() { return autor; }

    public String getIsbn() { return isbn; }

    public int getAnoPublicacao() { return anoPublicacao; }

    public String getEditora() { return editora; }

    public int getQuantidadeTotal() { return quantidadeTotal; }

    public int getQuantidadeDisponivel() { return quantidadeDisponivel; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public void setAutor(String autor) { this.autor = autor; }

    public void setIsbn(String isbn) { this.isbn = isbn; }

    public void setAnoPublicacao(int anoPublicacao) { this.anoPublicacao = anoPublicacao; }

    public void setEditora(String editora) { this.editora = editora; }

    public void setQuantidadeTotal(int quantidadeTotal) { this.quantidadeTotal = quantidadeTotal; }

    public boolean decrementarDisponiveis() {
        if (quantidadeDisponivel > 0) {
            quantidadeDisponivel--;
            return true;
        }
        return false;
    }

    public void incrementarDisponiveis() {
        if (quantidadeDisponivel < quantidadeTotal) {
            quantidadeDisponivel++;
        }
    }

    @Override
    public String toString() {
        return "ID: " + idLivro + " | Título: '" + titulo + "' | Autor: '" + autor + "' | Disponíveis: " + quantidadeDisponivel;
    }
}