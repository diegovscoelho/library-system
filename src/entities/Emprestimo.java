package entities;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Representa um empréstimo de um livro para um usuário no sistema da biblioteca.
 * Implementa Serializable para permitir que objetos desta classe sejam gravados e lidos de um arquivo.
 */
public class Emprestimo implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int proximoId = 1; // Armazena o próximo ID disponível para um objeto Emprestimo.

    private int idEmprestimo;
    private Livro livro;
    private Usuario usuario;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoEfetiva;

    /**
     * Constrói um novo Emprestimo com o livro, usuário e data de empréstimo especificados.
     * Atribui automaticamente um ID exclusivo e calcula a data de retorno esperada (14 dias a partir do empréstimo).
     * A data de devolução efetiva é inicialmente nula.
     *
     * @param livro O objeto Livro sendo emprestado.
     * @param usuario O objeto Usuario emprestando o livro.
     * @param dataEmprestimo A data em que o livro foi emprestado.
     */
    public Emprestimo(Livro livro, Usuario usuario, LocalDate dataEmprestimo) {
        this.idEmprestimo = proximoId++;
        this.livro = livro;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataEmprestimo.plusDays(14);
        this.dataDevolucaoEfetiva = null;
    }

    /**
     * Retorna o ID único do empréstimo.
     * @return O ID do empréstimo.
     */
    public int getIdEmprestimo() { return idEmprestimo; }

    /**
     * Retorna o livro associado a este empréstimo.
     * @return O objeto Livro.
     */
    public Livro getLivro() { return livro; }

    /**
     * Retorna o usuário associado a este empréstimo.
     * @return O objeto Usuario.
     */
    public Usuario getUsuario() { return usuario; }

    /**
     * Retorna a data em que o livro foi emprestado.
     * @return A data do empréstimo.
     */
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }

    /**
     * Retorna a data prevista de devolução.
     * @return A data de retorno prevista.
     */
    public LocalDate getDataDevolucaoPrevista() { return dataDevolucaoPrevista; }

    /**
     * Retorna a data real de devolução. Nulo se o livro ainda não foi devolvido.
     * @return A data de retorno efetiva.
     */
    public LocalDate getDataDevolucaoEfetiva() { return dataDevolucaoEfetiva; }

    /**
     * Define a data prevista de devolução para o empréstimo.
     * @param dataDevolucaoPrevista A nova data de retorno prevista.
     */
    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) { this.dataDevolucaoPrevista = dataDevolucaoPrevista; }

    /**
     * Define a data real de devolução para o empréstimo. Isso marca o empréstimo como devolvido.
     * @param dataDevolucaoEfetiva A data de retorno real.
     */
    public void setDataDevolucaoEfetiva(LocalDate dataDevolucaoEfetiva) { this.dataDevolucaoEfetiva = dataDevolucaoEfetiva; }

    /**
     * Verifica se o livro associado a este empréstimo foi devolvido.
     * @return true se o livro foi devolvido (dataDevolucaoEfetiva não é nula), false caso contrário.
     */
    public boolean isDevolvido() {
        return dataDevolucaoEfetiva != null;
    }

    /**
     * Retorna uma representação de string do objeto Emprestimo.
     * Inclui o ID do empréstimo, título do livro, nome do usuário e status do empréstimo (ativo ou devolvido com data).
     * @return Uma string contendo os detalhes do empréstimo.
     */
    @Override
    public String toString() {
        String status = isDevolvido() ? "Devolvido em " + dataDevolucaoEfetiva : "Ativo";
        return "ID: " + idEmprestimo + " | Livro: '" + livro.getTitulo() + "' | Usuário: '" + usuario.getNome() + "' | Status: " + status;
    }

    /**
     * Define o próximo ID disponível para objetos Emprestimo. Isso é usado para persistência de dados.
     * @param proximoId O próximo ID a ser usado.
     */
    public static void setProximoId(int proximoId) {
        Emprestimo.proximoId = proximoId;
    }
}