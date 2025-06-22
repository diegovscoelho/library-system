package test.java.entities;

import entities.Autor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes JUnit para a classe Autor.
 * Testa os getters, setters e o método toString.
 */
public class AutorTest {

    private Autor autor;

    /**
     * Configura um novo objeto Autor antes de cada teste.
     */
    @BeforeEach
    void setUp() {
        Autor.setProximoId(1); // Resetar ID estático para consistência
        autor = new Autor("Machado de Assis", "Brasileira"); //
    }

    /**
     * Testa se o construtor inicializa os atributos corretamente e o ID é gerado.
     */
    @Test
    void testConstrutor() {
        assertEquals(1, autor.getIdAutor(), "O ID do autor deveria ser 1."); //
        assertEquals("Machado de Assis", autor.getNome(), "O nome do autor deveria ser 'Machado de Assis'."); //
        assertEquals("Brasileira", autor.getNacionalidade(), "A nacionalidade deveria ser 'Brasileira'."); //
    }

    /**
     * Testa os métodos setters e getters.
     */
    @Test
    void testSettersAndGetters() {
        autor.setNome("Guimarães Rosa"); //
        assertEquals("Guimarães Rosa", autor.getNome(), "O nome deveria ser atualizado."); //

        autor.setNacionalidade("Brasileira"); //
        assertEquals("Brasileira", autor.getNacionalidade(), "A nacionalidade deveria ser atualizada."); //
    }

    /**
     * Testa o método toString.
     */
    @Test
    void testToString() {
        String expectedToString = "ID: 1 | Nome: Machado de Assis | Nacionalidade: Brasileira";
        assertEquals(expectedToString, autor.toString(), "O método toString deveria retornar a string esperada."); //
    }
}