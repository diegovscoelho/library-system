package entities;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GerenciadorBiblioteca {
    private List<Livro> livros;
    private List<Usuario> usuarios;
    private List<Emprestimo> emprestimos;
    private List<Autor> autores;

    public GerenciadorBiblioteca() {
        this.livros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.emprestimos = new ArrayList<>();
        this.autores = new ArrayList<>();
    }

    public void adicionarLivro(Livro livro) { livros.add(livro); }
    public Livro buscarLivroPorId(int idLivro) {
        return livros.stream().filter(l -> l.getIdLivro() == idLivro).findFirst().orElse(null);
    }
    public boolean removerLivro(int idLivro) {
        boolean emprestimoAtivo = emprestimos.stream().anyMatch(e -> !e.isDevolvido() && e.getLivro().getIdLivro() == idLivro);
        if (emprestimoAtivo) {
            return false;
        }
        return livros.removeIf(l -> l.getIdLivro() == idLivro);
    }
    public List<Livro> listarTodosLivros() { return new ArrayList<>(livros); } //

    public void adicionarUsuario(Usuario usuario) { usuarios.add(usuario); }
    public Usuario buscarUsuarioPorId(int idUsuario) {
        return usuarios.stream().filter(u -> u.getIdUsuario() == idUsuario).findFirst().orElse(null);
    }
    public boolean removerUsuario(int idUsuario) {
        // Regra: não remover se houver empréstimo ativo para o usuário
        boolean emprestimoAtivo = emprestimos.stream().anyMatch(e -> !e.isDevolvido() && e.getUsuario().getIdUsuario() == idUsuario);
        if (emprestimoAtivo) {
            return false;
        }
        return usuarios.removeIf(u -> u.getIdUsuario() == idUsuario);
    }
    public List<Usuario> listarTodosUsuarios() { return new ArrayList<>(usuarios); } //

    public void adicionarAutor(Autor autor) { autores.add(autor); }
    public Autor buscarAutorPorId(int idAutor) {
        return autores.stream().filter(a -> a.getIdAutor() == idAutor).findFirst().orElse(null);
    }
    public List<Autor> listarTodosAutores() { return new ArrayList<>(autores); }

    public Emprestimo realizarEmprestimo(int idLivro, int idUsuario) {
        Livro livro = buscarLivroPorId(idLivro);
        Usuario usuario = buscarUsuarioPorId(idUsuario);

        if (livro != null && usuario != null) {
            if (livro.getQuantidadeDisponivel() > 0) { //
                livro.decrementarDisponiveis(); //
                Emprestimo emprestimo = new Emprestimo(livro, usuario, LocalDate.now());
                emprestimos.add(emprestimo);
                return emprestimo;
            }
        }
        return null;
    }

    public boolean registrarDevolucao(int idEmprestimo) { //
        Emprestimo emprestimo = emprestimos.stream().filter(e -> e.getIdEmprestimo() == idEmprestimo && !e.isDevolvido()).findFirst().orElse(null);
        if (emprestimo != null) {
            emprestimo.setDataDevolucaoEfetiva(LocalDate.now());
            emprestimo.getLivro().incrementarDisponiveis(); //
            return true;
        }
        return false;
    }

    public List<Emprestimo> listarEmprestimosAtivos() { //
        return emprestimos.stream().filter(e -> !e.isDevolvido()).collect(Collectors.toList());
    }

    public void salvarDados() { //
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

    @SuppressWarnings("unchecked")
    public void carregarDados() { //
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