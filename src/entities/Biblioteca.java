package entities;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Biblioteca {
    private List<Livro> livros;
    private List<Usuario> usuarios;
    private List<Emprestimo> emprestimos;

    public Biblioteca() {
        this.livros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.emprestimos = new ArrayList<>();
    }

    public void adicionarLivro(Livro livro) { livros.add(livro); }
    public Livro buscarLivroPorId(int idLivro) {
        return livros.stream().filter(l -> l.getIdLivro() == idLivro).findFirst().orElse(null);
    }

    public boolean removerLivro(int idLivro) {
        return livros.removeIf(l -> l.getIdLivro() == idLivro);
    }

    public List<Livro> listarTodosLivros() { return new ArrayList<>(livros); }

    public void adicionarUsuario(Usuario usuario) { usuarios.add(usuario); }

    public Usuario buscarUsuarioPorId(int idUsuario) {
        return usuarios.stream().filter(u -> u.getIdUsuario() == idUsuario).findFirst().orElse(null);
    }

    public boolean removerUsuario(int idUsuario) {
        return usuarios.removeIf(u -> u.getIdUsuario() == idUsuario);
    }

    public List<Usuario> listarTodosUsuarios() { return new ArrayList<>(usuarios); }

    public Emprestimo realizarEmprestimo(int idLivro, int idUsuario) {
        Livro livro = buscarLivroPorId(idLivro);
        Usuario usuario = buscarUsuarioPorId(idUsuario);

        if (livro != null && usuario != null && livro.decrementarDisponiveis()) {
            Emprestimo emprestimo = new Emprestimo(livro, usuario, LocalDate.now());
            emprestimos.add(emprestimo);
            return emprestimo;
        }
        return null;
    }

    public boolean registrarDevolucao(int idEmprestimo) {
        Emprestimo emprestimo = emprestimos.stream().filter(e -> e.getIdEmprestimo() == idEmprestimo && !e.isDevolvido()).findFirst().orElse(null);
        if (emprestimo != null) {
            emprestimo.setDataDevolucaoEfetiva(LocalDate.now());
            emprestimo.getLivro().incrementarDisponiveis();
            return true;
        }
        return false;
    }

    public List<Emprestimo> listarEmprestimosAtivos() {
        return emprestimos.stream().filter(e -> !e.isDevolvido()).collect(Collectors.toList());
    }
    public List<Emprestimo> listarTodosEmprestimos() { return new ArrayList<>(emprestimos); }

    public void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("biblioteca.dat"))) {
            oos.writeObject(livros);
            oos.writeObject(usuarios);
            oos.writeObject(emprestimos);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    public void carregarDados() {
        File f = new File("biblioteca.dat");
        if (!f.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            livros = (List<Livro>) ois.readObject();
            usuarios = (List<Usuario>) ois.readObject();
            emprestimos = (List<Emprestimo>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
        }
    }
}