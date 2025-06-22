package test.java.entities;

import entities.Autor;
import entities.Emprestimo;
import entities.Livro;
import entities.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Classe de testes JUnit para a classe Emprestimo.
 * Testa a lógica de status de devolução e datas.
 */
public class EmprestimoTest {

    private Livro livroTeste;
    private Usuario usuarioTeste;
    private Emprestimo emprestimo;

    /**
     * Configura um novo objeto Emprestimo antes de cada teste.
     */
    @BeforeEach
    void setUp() {
        // Resetar IDs estáticos para consistência nos testes
        Autor.setProximoId(1);
        Livro.setProximoId(1);
        Usuario.setProximoId(1);
        Emprestimo.setProximoId(1); //

        ArrayList<Autor> autores = new ArrayList<>();
        autores.add(new Autor("Test Autor", "N/A"));
        livroTeste = new Livro("Livro Teste", "ISBN123", 2020, "Editora Teste", 1, autores); //
        usuarioTeste = new Usuario("Usuario Teste", "12345678900", "987654321", "teste@email.com"); //
        emprestimo = new Emprestimo(livroTeste, usuarioTeste, LocalDate.now()); //
    }

    /**
     * Testa se o empréstimo é criado corretamente e não está devolvido inicialmente.
     */
    @Test
    void testConstrutorEStatusInicial() {
        assertNotNull(emprestimo.getIdEmprestimo(), "ID do empréstimo não deveria ser nulo."); //
        assertEquals(livroTeste, emprestimo.getLivro(), "Livro do empréstimo deveria ser o livro de teste."); //
        assertEquals(usuarioTeste, emprestimo.getUsuario(), "Usuário do empréstimo deveria ser o usuário de teste."); //
        assertEquals(LocalDate.now(), emprestimo.getDataEmprestimo(), "Data de empréstimo deveria ser a data atual."); //
        assertEquals(LocalDate.now().plusDays(14), emprestimo.getDataDevolucaoPrevista(), "Data de devolução prevista deveria ser 14 dias após."); //
        assertNull(emprestimo.getDataDevolucaoEfetiva(), "Data de devolução efetiva deveria ser nula inicialmente."); //
        assertFalse(emprestimo.isDevolvido(), "Empréstimo não deveria estar devolvido inicialmente."); //
    }

    /**
     * Testa o método isDevolvido() após a devolução.
     */
    @Test
    void testIsDevolvidoAposDevolucao() {
        assertFalse(emprestimo.isDevolvido(), "Empréstimo não deveria estar devolvido."); //
        emprestimo.setDataDevolucaoEfetiva(LocalDate.now()); //
        assertTrue(emprestimo.isDevolvido(), "Empréstimo deveria estar devolvido após definir data efetiva."); //
    }

    /**
     * Testa os getters e setters de datas.
     */
    @Test
    void testSetters() {
        LocalDate novaDataPrevista = LocalDate.now().plusDays(20);
        emprestimo.setDataDevolucaoPrevista(novaDataPrevista); //
        assertEquals(novaDataPrevista, emprestimo.getDataDevolucaoPrevista(), "Data prevista deveria ser atualizada."); //

        LocalDate novaDataEfetiva = LocalDate.now().plusDays(5);
        emprestimo.setDataDevolucaoEfetiva(novaDataEfetiva); //
        assertEquals(novaDataEfetiva, emprestimo.getDataDevolucaoEfetiva(), "Data efetiva deveria ser atualizada."); //
    }

    /**
     * Testa o método toString.
     */
    @Test
    void testToString() {
        // Empréstimo ativo
        String expectedToStringAtivo = "ID: 1 | Livro: 'Livro Teste' | Usuário: 'Usuario Teste' | Status: Ativo";
        assertEquals(expectedToStringAtivo, emprestimo.toString(), "toString deveria indicar status 'Ativo'."); //

        // Empréstimo devolvido
        emprestimo.setDataDevolucaoEfetiva(LocalDate.now()); //
        String expectedToStringDevolvido = "ID: 1 | Livro: 'Livro Teste' | Usuário: 'Usuario Teste' | Status: Devolvido em " + LocalDate.now();
        assertEquals(expectedToStringDevolvido, emprestimo.toString(), "toString deveria indicar status 'Devolvido em'."); //
    }
}