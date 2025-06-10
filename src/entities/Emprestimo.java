package entities;

import java.io.Serializable;
import java.time.LocalDate;

public class Emprestimo implements Serializable {
    private static int proximoIdEmprestimo = 1;
    private int idEmprestimo;
    private Livro livro;
    private Usuario usuario;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoEfetiva;

    public Emprestimo(Livro livro, Usuario usuario, LocalDate dataEmprestimo) {
        this.idEmprestimo = proximoIdEmprestimo++;
        this.livro = livro;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataEmprestimo.plusDays(14); // Ex: Prazo de 14 dias
        this.dataDevolucaoEfetiva = null; // Inicia como nulo
    }

    public int getIdEmprestimo() { return idEmprestimo; }

    public Livro getLivro() { return livro; }

    public Usuario getUsuario() { return usuario; }

    public LocalDate getDataEmprestimo() { return dataEmprestimo; }

    public LocalDate getDataDevolucaoPrevista() { return dataDevolucaoPrevista; }

    public LocalDate getDataDevolucaoEfetiva() { return dataDevolucaoEfetiva; }

    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) { this.dataDevolucaoPrevista = dataDevolucaoPrevista; }

    public void setDataDevolucaoEfetiva(LocalDate dataDevolucaoEfetiva) { this.dataDevolucaoEfetiva = dataDevolucaoEfetiva; }

    public boolean isDevolvido() {
        return dataDevolucaoEfetiva != null;
    }

    @Override
    public String toString() {
        String status = isDevolvido() ? "Devolvido em " + dataDevolucaoEfetiva : "Ativo";
        return "ID: " + idEmprestimo + " | Livro: '" + livro.getTitulo() + "' | Usuário: '" + usuario.getNome() + "' | Status: " + status;
    }
}