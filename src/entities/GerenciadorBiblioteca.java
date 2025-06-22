package entities;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Gerencia as operações de uma biblioteca, incluindo livros, usuários, autores e empréstimos.
 * Fornece funcionalidades para adicionar, remover, pesquisar e listar itens da biblioteca,
 * bem como gerenciar empréstimos e devoluções de livros, e persistir dados.
 */
public class GerenciadorBiblioteca {
    private List<Livro> livros;
    private List<Usuario> usuarios;
    private List<Emprestimo> emprestimos;
    private List<Autor> autores;

    /**
     * Constrói um novo GerenciadorBiblioteca, inicializando listas vazias para livros, usuários, empréstimos e autores.
     */
    public GerenciadorBiblioteca() {
        this.livros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.emprestimos = new ArrayList<>();
        this.autores = new ArrayList<>();
    }

    /**
     * Adiciona um novo livro à biblioteca.
     * @param livro O objeto Livro a ser adicionado.
     */
    public void adicionarLivro(Livro livro) { livros.add(livro); }

    /**
     * Procura um livro pelo seu ID único.
     * @param idLivro O ID do livro a ser procurado.
     * @return O objeto Livro se encontrado, null caso contrário.
     */
    public Livro buscarLivroPorId(int idLivro) {
        return livros.stream().filter(l -> l.getIdLivro() == idLivro).findFirst().orElse(null);
    }

    /**
     * Remove um livro da biblioteca.
     * Um livro não pode ser removido se houver empréstimos ativos para ele.
     *
     * @param idLivro O ID do livro a ser removido.
     * @return true se o livro foi removido com sucesso, false se não pôde ser removido (por exemplo, existem empréstimos ativos).
     */
    public boolean removerLivro(int idLivro) {
        boolean emprestimoAtivo = emprestimos.stream().anyMatch(e -> !e.isDevolvido() && e.getLivro().getIdLivro() == idLivro);
        if (emprestimoAtivo) {
            return false;
        }
        return livros.removeIf(l -> l.getIdLivro() == idLivro);
    }

    /**
     * Retorna uma lista de todos os livros atualmente na biblioteca.
     * @return Um novo ArrayList contendo todos os objetos Livro.
     */
    public List<Livro> listarTodosLivros() { return new ArrayList<>(livros); }

    /**
     * Adiciona um novo usuário ao sistema da biblioteca.
     * @param usuario O objeto Usuario a ser adicionado.
     */
    public void adicionarUsuario(Usuario usuario) { usuarios.add(usuario); }

    /**
     * Procura um usuário pelo seu ID único.
     * @param idUsuario O ID do usuário a ser procurado.
     * @return O objeto Usuario se encontrado, null caso contrário.
     */
    public Usuario buscarUsuarioPorId(int idUsuario) {
        return usuarios.stream().filter(u -> u.getIdUsuario() == idUsuario).findFirst().orElse(null);
    }

    /**
     * Remove um usuário do sistema da biblioteca.
     * Um usuário não pode ser removido se tiver empréstimos ativos.
     *
     * @param idUsuario O ID do usuário a ser removido.
     * @return true se o usuário foi removido com sucesso, false se não pôde ser removido (por exemplo, existem empréstimos ativos).
     */
    public boolean removerUsuario(int idUsuario) {
        boolean emprestimoAtivo = emprestimos.stream().anyMatch(e -> !e.isDevolvido() && e.getUsuario().getIdUsuario() == idUsuario);
        if (emprestimoAtivo) {
            return false;
        }
        return usuarios.removeIf(u -> u.getIdUsuario() == idUsuario);
    }

    /**
     * Retorna uma lista de todos os usuários registrados no sistema da biblioteca.
     * @return Um novo ArrayList contendo todos os objetos Usuario.
     */
    public List<Usuario> listarTodosUsuarios() { return new ArrayList<>(usuarios); }

    /**
     * Adiciona um novo autor ao sistema da biblioteca.
     * @param autor O objeto Autor a ser adicionado.
     */
    public void adicionarAutor(Autor autor) { autores.add(autor); }

    /**
     * Procura um autor pelo seu ID único.
     * @param idAutor O ID do autor a ser procurado.
     * @return O objeto Autor se encontrado, null caso contrário.
     */
    public Autor buscarAutorPorId(int idAutor) {
        return autores.stream().filter(a -> a.getIdAutor() == idAutor).findFirst().orElse(null);
    }

    /**
     * Retorna uma lista de todos os autores registrados no sistema da biblioteca.
     * @return Um novo ArrayList contendo todos os objetos Autor.
     */
    public List<Autor> listarTodosAutores() { return new ArrayList<>(autores); }

    /**
     * Tenta realizar um empréstimo de livro.
     * Um empréstimo só pode ser feito se o livro e o usuário existirem e o livro tiver cópias disponíveis.
     * Se bem-sucedido, a quantidade disponível do livro é decrementada.
     *
     * @param idLivro O ID do livro a ser emprestado.
     * @param idUsuario O ID do usuário emprestando o livro.
     * @return O objeto Emprestimo recém-criado se o empréstimo foi bem-sucedido, null caso contrário.
     */
    public Emprestimo realizarEmprestimo(int idLivro, int idUsuario) {
        Livro livro = buscarLivroPorId(idLivro);
        Usuario usuario = buscarUsuarioPorId(idUsuario);

        if (livro != null && usuario != null) {
            if (livro.getQuantidadeDisponivel() > 0) {
                livro.decrementarDisponiveis();
                Emprestimo emprestimo = new Emprestimo(livro, usuario, LocalDate.now());
                emprestimos.add(emprestimo);
                return emprestimo;
            }
        }
        return null;
    }

    /**
     * Registra a devolução de um livro emprestado.
     * Marca o empréstimo como devolvido e incrementa a quantidade disponível do livro.
     *
     * @param idEmprestimo O ID do empréstimo a ser devolvido.
     * @return true se a devolução foi registrada com sucesso, false se o empréstimo não foi encontrado ou já foi devolvido.
     */
    public boolean registrarDevolucao(int idEmprestimo) {
        Emprestimo emprestimo = emprestimos.stream().filter(e -> e.getIdEmprestimo() == idEmprestimo && !e.isDevolvido()).findFirst().orElse(null);
        if (emprestimo != null) {
            emprestimo.setDataDevolucaoEfetiva(LocalDate.now());
            emprestimo.getLivro().incrementarDisponiveis();
            return true;
        }
        return false;
    }

    /**
     * Retorna uma lista de todos os empréstimos atualmente ativos (livros que ainda não foram devolvidos).
     * @return Uma Lista de objetos Emprestimo representando empréstimos ativos.
     */
    public List<Emprestimo> listarEmprestimosAtivos() {
        return emprestimos.stream().filter(e -> !e.isDevolvido()).collect(Collectors.toList());
    }

    /**
     * Salva o estado atual dos dados da biblioteca (autores, livros, usuários e empréstimos) em um arquivo chamado "dados_biblioteca.dat".
     * Também salva os próximos IDs disponíveis para cada entidade para garantir a geração de IDs únicos após o carregamento.
     * Captura e imprime qualquer IOException que ocorra durante o processo de salvamento.
     */
    public void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("dados_biblioteca.dat"))) {
            oos.writeObject(autores);
            oos.writeObject(livros);
            oos.writeObject(usuarios);
            oos.writeObject(emprestimos);

            oos.writeInt(autores.stream().mapToInt(Autor::getIdAutor).max().orElse(0) + 1);
            oos.writeInt(livros.stream().mapToInt(Livro::getIdLivro).max().orElse(0) + 1);
            oos.writeInt(usuarios.stream().mapToInt(Usuario::getIdUsuario).max().orElse(0) + 1);
            oos.writeInt(emprestimos.stream().mapToInt(Emprestimo::getIdEmprestimo).max().orElse(0) + 1);

        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    /**
     * Carrega os dados da biblioteca (autores, livros, usuários e empréstimos) do arquivo "dados_biblioteca.dat".
     * Também restaura os próximos IDs disponíveis para cada entidade para continuar a geração de IDs únicos.
     * Se o arquivo não existir ou ocorrer um erro durante o carregamento, as listas são reinicializadas como vazias.
     * Suprime avisos de cast não verificados para desserialização de objetos.
     */
    @SuppressWarnings("unchecked")
    public void carregarDados() {
        File f = new File("dados_biblioteca.dat");
        if (!f.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            autores = (List<Autor>) ois.readObject();
            livros = (List<Livro>) ois.readObject();
            usuarios = (List<Usuario>) ois.readObject();
            emprestimos = (List<Emprestimo>) ois.readObject();

            Autor.setProximoId(ois.readInt());
            Livro.setProximoId(ois.readInt());
            Usuario.setProximoId(ois.readInt());
            Emprestimo.setProximoId(ois.readInt());

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
            autores = new ArrayList<>();
            livros = new ArrayList<>();
            usuarios = new ArrayList<>();
            emprestimos = new ArrayList<>();
        }
    }
}