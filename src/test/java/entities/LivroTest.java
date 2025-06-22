package test.java.entities;

import entities.Autor;
import entities.Livro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

/**
 * Classe de testes JUnit para a classe Livro.
 * Testa as funcionalidades de gerenciamento de quantidade disponível.
 */
public class LivroTest {

    private Livro livro;

    /**
     * Configura um novo objeto Livro antes de cada teste.
     */
    @BeforeEach
    void setUp() {
        Autor.setProximoId(1); // Resetar ID de autor para consistência
        Livro.setProximoId(1); // Resetar ID de livro para consistência
        ArrayList<Autor> autores = new ArrayList<>();
        autores.add(new Autor("Test Author", "Test Country"));
        livro = new Livro("Test Livro", "123-ABC", 2023, "Test Publisher", 10, autores); //
    }

    /**
     * Testa se a quantidade disponível é decrementada corretamente.
     */
    @Test
    void testDecrementarDisponiveisComSucesso() {
        assertTrue(livro.decrementarDisponiveis(), "Deveria ser possível decrementar a quantidade disponível."); //
        assertEquals(9, livro.getQuantidadeDisponivel(), "A quantidade disponível deveria ser 9."); //
    }

    /**
     * Testa se a decrementação falha quando a quantidade disponível é 0.
     */
    @Test
    void testDecrementarDisponiveisQuandoZero() {
        for (int i = 0; i < 10; i++) {
            livro.decrementarDisponiveis(); //
        }
        assertEquals(0, livro.getQuantidadeDisponivel(), "A quantidade disponível deveria ser 0 após 10 decrementos."); //
        assertFalse(livro.decrementarDisponiveis(), "Não deveria ser possível decrementar quando a quantidade é 0."); //
        assertEquals(0, livro.getQuantidadeDisponivel(), "A quantidade disponível deveria permanecer 0."); //
    }

    /**
     * Testa se a quantidade disponível é incrementada corretamente.
     */
    @Test
    void testIncrementarDisponiveis() {
        livro.decrementarDisponiveis(); // Primeiro decrementa para poder incrementar
        assertEquals(9, livro.getQuantidadeDisponivel()); //

        livro.incrementarDisponiveis(); //
        assertEquals(10, livro.getQuantidadeDisponivel(), "A quantidade disponível deveria ser 10."); //
    }

    /**
     * Testa se a quantidade disponível não excede a quantidade total ao incrementar.
     */
    @Test
    void testIncrementarDisponiveisNaoExcedeTotal() {
        // A quantidade já começa em 10 (total), então incrementar não deve mudar
        livro.incrementarDisponiveis(); //
        assertEquals(10, livro.getQuantidadeDisponivel(), "A quantidade disponível não deveria exceder a total (10)."); //
    }

    /**
     * Testa os getters e setters básicos da classe Livro.
     */
    @Test
    void testGettersAndSetters() {
        assertEquals(1, livro.getIdLivro(), "ID do livro deveria ser 1."); //
        assertEquals("Test Livro", livro.getTitulo(), "Título deveria ser 'Test Livro'."); //
        assertEquals("123-ABC", livro.getIsbn(), "ISBN deveria ser '123-ABC'."); //
        assertEquals(2023, livro.getAnoPublicacao(), "Ano de publicação deveria ser 2023."); //
        assertEquals("Test Publisher", livro.getEditora(), "Editora deveria ser 'Test Publisher'."); //
        assertEquals(10, livro.getQuantidadeTotal(), "Quantidade total deveria ser 10."); //
        assertEquals(10, livro.getQuantidadeDisponivel(), "Quantidade disponível deveria ser 10 inicialmente."); //
        assertFalse(livro.getAutores().isEmpty(), "Lista de autores não deveria estar vazia."); //

        livro.setTitulo("Novo Titulo"); //
        assertEquals("Novo Titulo", livro.getTitulo(), "Título deveria ser atualizado."); //
    }

    /**
     * Testa o método toString.
     */
    @Test
    void testToString() {
        String expectedToString = "ID: 1 | Título: 'Test Livro' | Autores: [Test Author] | Disponíveis: 10";
        assertEquals(expectedToString, livro.toString(), "O método toString deveria retornar a string esperada."); //
    }
}