package test.java.entities;

import entities.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes JUnit para a classe Usuario.
 * Testa os getters, setters e o método toString.
 */
public class UsuarioTest {

    private Usuario usuario;

    /**
     * Configura um novo objeto Usuario antes de cada teste.
     */
    @BeforeEach
    void setUp() {
        Usuario.setProximoId(1); // Resetar ID estático para consistência
        usuario = new Usuario("Carlos Drummond", "123.456.789-00", "31987654321", "carlos@email.com"); //
    }

    /**
     * Testa se o construtor inicializa os atributos corretamente e o ID é gerado.
     */
    @Test
    void testConstrutor() {
        assertEquals(1, usuario.getIdUsuario(), "O ID do usuário deveria ser 1."); //
        assertEquals("Carlos Drummond", usuario.getNome(), "O nome do usuário deveria ser 'Carlos Drummond'."); //
        assertEquals("123.456.789-00", usuario.getCpf(), "O CPF deveria ser '123.456.789-00'."); //
        assertEquals("31987654321", usuario.getTelefone(), "O telefone deveria ser '31987654321'."); //
        assertEquals("carlos@email.com", usuario.getEmail(), "O email deveria ser 'carlos@email.com'."); //
    }

    /**
     * Testa os métodos setters e getters.
     */
    @Test
    void testSettersAndGetters() {
        usuario.setNome("João Guimarães"); //
        assertEquals("João Guimarães", usuario.getNome(), "O nome deveria ser atualizado."); //

        usuario.setCpf("987.654.321-11"); //
        assertEquals("987.654.321-11", usuario.getCpf(), "O CPF deveria ser atualizado."); //

        usuario.setTelefone("21998877665"); //
        assertEquals("21998877665", usuario.getTelefone(), "O telefone deveria ser atualizado."); //

        usuario.setEmail("joao@novoemail.com"); //
        assertEquals("joao@novoemail.com", usuario.getEmail(), "O email deveria ser atualizado."); //
    }

    /**
     * Testa o método toString.
     */
    @Test
    void testToString() {
        String expectedToString = "ID: 1 | Nome: 'Carlos Drummond' | CPF: 123.456.789-00";
        assertEquals(expectedToString, usuario.toString(), "O método toString deveria retornar a string esperada."); //
    }
}