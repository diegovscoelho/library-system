package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Representa um livro no sistema da biblioteca.
 * Implementa Serializable para permitir que objetos desta classe sejam gravados e lidos de um arquivo.
 */
public class Livro implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int proximoId = 1; // Armazena o próximo ID disponível para um objeto Livro.

    private int idLivro;
    private String titulo;
    private String isbn;
    private int anoPublicacao;
    private String editora;
    private int quantidadeTotal;
    private int quantidadeDisponivel;
    private ArrayList<Autor> autores;

    /**
     * Constrói um novo Livro com os detalhes especificados.
     * Atribui automaticamente um ID exclusivo e define a quantidade disponível inicial para a quantidade total.
     *
     * @param titulo O título do livro.
     * @param isbn O ISBN do livro.
     * @param anoPublicacao O ano de publicação do livro.
     * @param editora A editora do livro.
     * @param quantidadeTotal O número total de cópias deste livro.
     * @param autores Um ArrayList de objetos Autor que escreveram o livro.
     */
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

    /**
     * Retorna o ID único do livro.
     * @return O ID do livro.
     */
    public int getIdLivro() { return idLivro; }

    /**
     * Retorna o título do livro.
     * @return O título do livro.
     */
    public String getTitulo() { return titulo; }

    /**
     * Retorna a lista de autores do livro.
     * @return Um ArrayList de objetos Autor.
     */
    public ArrayList<Autor> getAutores() { return autores; }

    /**
     * Retorna o ISBN do livro.
     * @return O ISBN do livro.
     */
    public String getIsbn() { return isbn; }

    /**
     * Retorna o ano de publicação do livro.
     * @return O ano de publicação.
     */
    public int getAnoPublicacao() { return anoPublicacao; }

    /**
     * Retorna a editora do livro.
     * @return A editora.
     */
    public String getEditora() { return editora; }

    /**
     * Retorna a quantidade total deste livro na biblioteca.
     * @return A quantidade total.
     */
    public int getQuantidadeTotal() { return quantidadeTotal; }

    /**
     * Retorna o número de cópias disponíveis deste livro.
     * @return A quantidade disponível.
     */
    public int getQuantidadeDisponivel() { return quantidadeDisponivel; }

    /**
     * Define o título do livro.
     * @param titulo O novo título do livro.
     */
    public void setTitulo(String titulo) { this.titulo = titulo; }

    /**
     * Define os autores do livro.
     * @param autores O novo ArrayList de objetos Autor.
     */
    public void setAutores(ArrayList<Autor> autores) { this.autores = autores; }

    /**
     * Define o ISBN do livro.
     * @param isbn O novo ISBN do livro.
     */
    public void setIsbn(String isbn) { this.isbn = isbn; }

    /**
     * Define o ano de publicação do livro.
     * @param anoPublicacao O novo ano de publicação.
     */
    public void setAnoPublicacao(int anoPublicacao) { this.anoPublicacao = anoPublicacao; }

    /**
     * Define a editora do livro.
     * @param editora A nova editora.
     */
    public void setEditora(String editora) { this.editora = editora; }

    /**
     * Define a quantidade total deste livro.
     * Este método não afeta a 'quantidadeDisponivel'.
     * @param quantidadeTotal A nova quantidade total.
     */
    public void setQuantidadeTotal(int quantidadeTotal) { this.quantidadeTotal = quantidadeTotal; }

    /**
     * Decrementa o número de cópias disponíveis do livro.
     * Este método é tipicamente chamado quando um livro é emprestado.
     *
     * @return true se a quantidade foi decrementada (ou seja, havia pelo menos uma cópia disponível), false caso contrário.
     */
    public boolean decrementarDisponiveis() {
        if (quantidadeDisponivel > 0) {
            quantidadeDisponivel--;
            return true;
        }
        return false;
    }

    /**
     * Incrementa o número de cópias disponíveis do livro.
     * Este método é tipicamente chamado quando um livro é devolvido.
     * A quantidade disponível não excederá a quantidade total.
     */
    public void incrementarDisponiveis() {
        if (quantidadeDisponivel < quantidadeTotal) {
            quantidadeDisponivel++;
        }
    }

    /**
     * Retorna uma representação de string do objeto Livro.
     * Inclui o ID do livro, título, autores e quantidade disponível.
     * @return Uma string contendo os detalhes do livro.
     */
    @Override
    public String toString() {
        String nomesAutores = autores.stream()
                .map(Autor::getNome)
                .collect(Collectors.joining(", "));
        return "ID: " + idLivro + " | Título: '" + titulo + "' | Autores: [" + nomesAutores + "] | Disponíveis: " + quantidadeDisponivel;
    }

    /**
     * Define o próximo ID disponível para objetos Livro. Isso é usado para persistência de dados.
     * @param proximoId O próximo ID a ser usado.
     */
    public static void setProximoId(int proximoId) {
        Livro.proximoId = proximoId;
    }
}