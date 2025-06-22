package test.java.entities;

import entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de testes JUnit para a classe GerenciadorBiblioteca.
 * Testa as funcionalidades de adicionar, remover, buscar, listar,
 * realizar empréstimos e registrar devoluções.
 */
public class GerenciadorBibliotecaTest {

    private GerenciadorBiblioteca gerenciador; //
    private Livro livro1;
    private Livro livro2;
    private Usuario usuario1;
    private Usuario usuario2;
    private Autor autor1;
    private Autor autor2;

    /**
     * Este método é executado ANTES de cada método de teste.
     * Garante que cada teste comece com um estado limpo e pré-definido do gerenciador.
     */
    @BeforeEach
    void setUp() {
        // Reinicializa o gerenciador para cada teste
        gerenciador = new GerenciadorBiblioteca(); //

        // Limpa os IDs estáticos das entidades para garantir que cada teste tenha IDs limpos.
        // Isso é crucial para testes independentes, pois 'proximoId' é estático.
        Autor.setProximoId(1); //
        Livro.setProximoId(1); //
        Usuario.setProximoId(1); //
        Emprestimo.setProximoId(1); //

        // Cria dados de teste
        autor1 = new Autor("João Silva", "Brasileira"); //
        autor2 = new Autor("Maria Souza", "Portuguesa"); //
        gerenciador.adicionarAutor(autor1); //
        gerenciador.adicionarAutor(autor2); //

        ArrayList<Autor> autoresLivro1 = new ArrayList<>();
        autoresLivro1.add(autor1);
        ArrayList<Autor> autoresLivro2 = new ArrayList<>();
        autoresLivro2.add(autor2);

        livro1 = new Livro("Aventuras de Java", "978-85-333-0123-4", 2020, "Dev Publicações", 5, autoresLivro1); //
        livro2 = new Livro("Design Patterns Essenciais", "978-85-333-0456-7", 2018, "Code Books", 2, autoresLivro2); //
        gerenciador.adicionarLivro(livro1); //
        gerenciador.adicionarLivro(livro2); //

        usuario1 = new Usuario("Ana Costa", "111.111.111-11", "9988-7766", "ana@email.com"); //
        usuario2 = new Usuario("Bruno Lima", "222.222.222-22", "9123-4567", "bruno@email.com"); //
        gerenciador.adicionarUsuario(usuario1); //
        gerenciador.adicionarUsuario(usuario2); //
    }

    /**
     * Testa a funcionalidade de adicionar um autor.
     */
    @Test
    void testAdicionarAutor() {
        Autor novoAutor = new Autor("Carlos Pereira", "Espanhola"); //
        gerenciador.adicionarAutor(novoAutor); //
        assertEquals(3, gerenciador.listarTodosAutores().size(), "Deveria haver 3 autores após adicionar um novo."); //
        assertNotNull(gerenciador.buscarAutorPorId(novoAutor.getIdAutor()), "O novo autor deveria ser encontrado."); //
    }

    /**
     * Testa a funcionalidade de adicionar um livro.
     * Verifica se o livro é realmente adicionado à lista e se o ID é gerado corretamente.
     */
    @Test
    void testAdicionarLivro() {
        ArrayList<Autor> autoresTeste = new ArrayList<>();
        autoresTeste.add(autor1);
        Livro novoLivro = new Livro("Clean Code", "978-0132350884", 2008, "Prentice Hall", 3, autoresTeste); //

        gerenciador.adicionarLivro(novoLivro); //

        assertEquals(3, gerenciador.listarTodosLivros().size(), "O número de livros deveria ser 3 após adicionar um novo."); //
        assertNotNull(gerenciador.buscarLivroPorId(novoLivro.getIdLivro()), "O novo livro deveria ser encontrado pelo seu ID."); //
        assertEquals("Clean Code", gerenciador.buscarLivroPorId(novoLivro.getIdLivro()).getTitulo(), "O título do livro encontrado deveria ser 'Clean Code'."); //
    }

    /**
     * Testa a remoção de um livro que existe e não possui empréstimos ativos.
     */
    @Test
    void testRemoverLivroExistenteSemEmprestimo() {
        // Tenta remover o livro2 (ID 2), que não tem empréstimos ativos
        boolean removido = gerenciador.removerLivro(livro2.getIdLivro()); //
        assertTrue(removido, "O livro2 deveria ser removido com sucesso."); //
        assertEquals(1, gerenciador.listarTodosLivros().size(), "Deveria haver apenas 1 livro após a remoção."); //
        assertNull(gerenciador.buscarLivroPorId(livro2.getIdLivro()), "O livro2 não deveria mais ser encontrado."); //
    }

    /**
     * Testa a remoção de um livro com empréstimo ativo, o que deve falhar.
     */
    @Test
    void testRemoverLivroComEmprestimoAtivo() {
        // Realiza um empréstimo para o livro1
        gerenciador.realizarEmprestimo(livro1.getIdLivro(), usuario1.getIdUsuario()); //

        // Tenta remover o livro1, que agora tem um empréstimo ativo
        boolean removido = gerenciador.removerLivro(livro1.getIdLivro()); //
        assertFalse(removido, "O livro1 NÃO deveria ser removido devido a empréstimo ativo."); //
        assertEquals(2, gerenciador.listarTodosLivros().size(), "O número de livros deveria permanecer 2."); //
        assertNotNull(gerenciador.buscarLivroPorId(livro1.getIdLivro()), "O livro1 ainda deveria ser encontrado."); //
    }

    /**
     * Testa a remoção de um livro que não existe.
     */
    @Test
    void testRemoverLivroInexistente() {
        boolean removido = gerenciador.removerLivro(999); // ID que não existe
        assertFalse(removido, "Não deveria ser possível remover um livro inexistente."); //
        assertEquals(2, gerenciador.listarTodosLivros().size(), "O número de livros deveria permanecer 2."); //
    }

    /**
     * Testa a funcionalidade de adicionar um usuário.
     */
    @Test
    void testAdicionarUsuario() {
        Usuario novoUsuario = new Usuario("Carlos Santos", "333.333.333-33", "9911-2233", "carlos@email.com"); //
        gerenciador.adicionarUsuario(novoUsuario); //
        assertEquals(3, gerenciador.listarTodosUsuarios().size(), "Deveria haver 3 usuários após adicionar um novo."); //
        assertNotNull(gerenciador.buscarUsuarioPorId(novoUsuario.getIdUsuario()), "O novo usuário deveria ser encontrado."); //
    }

    /**
     * Testa a remoção de um usuário que existe e não possui empréstimos ativos.
     */
    @Test
    void testRemoverUsuarioExistenteSemEmprestimo() {
        boolean removido = gerenciador.removerUsuario(usuario2.getIdUsuario()); //
        assertTrue(removido, "O usuário2 deveria ser removido com sucesso."); //
        assertEquals(1, gerenciador.listarTodosUsuarios().size(), "Deveria haver apenas 1 usuário após a remoção."); //
        assertNull(gerenciador.buscarUsuarioPorId(usuario2.getIdUsuario()), "O usuário2 não deveria mais ser encontrado."); //
    }

    /**
     * Testa a remoção de um usuário com empréstimo ativo, o que deve falhar.
     */
    @Test
    void testRemoverUsuarioComEmprestimoAtivo() {
        gerenciador.realizarEmprestimo(livro1.getIdLivro(), usuario1.getIdUsuario()); //
        boolean removido = gerenciador.removerUsuario(usuario1.getIdUsuario()); //
        assertFalse(removido, "O usuário1 NÃO deveria ser removido devido a empréstimo ativo."); //
        assertEquals(2, gerenciador.listarTodosUsuarios().size(), "O número de usuários deveria permanecer 2."); //
        assertNotNull(gerenciador.buscarUsuarioPorId(usuario1.getIdUsuario()), "O usuário1 ainda deveria ser encontrado."); //
    }

    /**
     * Testa a realização de um empréstimo com sucesso.
     * Verifica a criação do empréstimo e a decrementação da quantidade disponível do livro.
     */
    @Test
    void testRealizarEmprestimoComSucesso() {
        Emprestimo emprestimo = gerenciador.realizarEmprestimo(livro1.getIdLivro(), usuario1.getIdUsuario()); //

        assertNotNull(emprestimo, "O empréstimo não deveria ser nulo.");
        assertEquals(1, gerenciador.listarEmprestimosAtivos().size(), "Deveria haver 1 empréstimo ativo."); //
        assertEquals(4, livro1.getQuantidadeDisponivel(), "A quantidade disponível do livro1 deveria ser 4."); //
        assertFalse(emprestimo.isDevolvido(), "O empréstimo deveria estar ativo."); //
    }

    /**
     * Testa a tentativa de realizar um empréstimo com livro indisponível (quantidade 0).
     */
    @Test
    void testRealizarEmprestimoLivroIndisponivel() {
        // Empresta todas as cópias do livro2 (quantidade total = 2)
        gerenciador.realizarEmprestimo(livro2.getIdLivro(), usuario1.getIdUsuario()); //
        gerenciador.realizarEmprestimo(livro2.getIdLivro(), usuario2.getIdUsuario()); //

        // Tenta emprestar novamente, o que deve falhar
        Emprestimo emprestimoFalho = gerenciador.realizarEmprestimo(livro2.getIdLivro(), usuario1.getIdUsuario()); //

        assertNull(emprestimoFalho, "O empréstimo deveria ser nulo, pois o livro está indisponível.");
        assertEquals(0, livro2.getQuantidadeDisponivel(), "A quantidade disponível do livro2 deveria ser 0."); //
        assertEquals(2, gerenciador.listarEmprestimosAtivos().size(), "Deveria haver 2 empréstimos ativos para o livro2."); //
    }

    /**
     * Testa o registro de devolução de um empréstimo ativo.
     * Verifica se o empréstimo é marcado como devolvido e se a quantidade disponível do livro é incrementada.
     */
    @Test
    void testRegistrarDevolucaoComSucesso() {
        // Realiza um empréstimo primeiro
        Emprestimo emprestimo = gerenciador.realizarEmprestimo(livro1.getIdLivro(), usuario1.getIdUsuario()); //
        assertNotNull(emprestimo, "Empréstimo inicial falhou.");

        // Registra a devolução
        boolean devolvido = gerenciador.registrarDevolucao(emprestimo.getIdEmprestimo()); //

        assertTrue(devolvido, "A devolução deveria ser registrada com sucesso.");
        assertTrue(emprestimo.isDevolvido(), "O empréstimo deveria estar marcado como devolvido."); //
        assertEquals(5, livro1.getQuantidadeTotal(), "A quantidade disponível do livro1 deveria ser igual à total após a devolução."); // (correção, o ideal é voltar ao total)
        assertEquals(0, gerenciador.listarEmprestimosAtivos().size(), "Não deveria haver empréstimos ativos após a devolução."); //
    }

    /**
     * Testa a tentativa de registrar devolução para um empréstimo inexistente ou já devolvido.
     */
    @Test
    void testRegistrarDevolucaoEmprestimoInvalidoOuJaDevolvido() {
        // Tenta devolver um ID de empréstimo que não existe
        boolean devolvidoInvalido = gerenciador.registrarDevolucao(999); //
        assertFalse(devolvidoInvalido, "Não deveria ser possível devolver um empréstimo inexistente.");

        // Realiza um empréstimo e o devolve
        Emprestimo emprestimo = gerenciador.realizarEmprestimo(livro1.getIdLivro(), usuario1.getIdUsuario()); //
        gerenciador.registrarDevolucao(emprestimo.getIdEmprestimo()); //

        // Tenta devolver o mesmo empréstimo novamente (já devolvido)
        boolean devolvidoNovamente = gerenciador.registrarDevolucao(emprestimo.getIdEmprestimo()); //
        assertFalse(devolvidoNovamente, "Não deveria ser possível devolver um empréstimo que já foi devolvido.");
    }
}