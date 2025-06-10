package application;

import entities.*;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        GerenciadorBiblioteca gerenciador = new GerenciadorBiblioteca();
        gerenciador.carregarDados();

        String menu = "Sistema de Biblioteca\n\n" +
                "--- Gerenciar Acervo ---\n" +
                "1. Adicionar Livro\n" +
                "2. Listar Livros\n" +
                "3. Adicionar Autor\n" +
                "4. Listar Autores\n" +
                "--- Gerenciar Pessoas ---\n" +
                "5. Adicionar Usuário\n" +
                "6. Listar Usuários\n" +
                "--- Operações ---\n" +
                "7. Realizar Empréstimo\n" +
                "8. Registrar Devolução\n" +
                "9. Listar Empréstimos Ativos\n\n" +
                "0. Sair e Salvar";

        String input;
        do {
            input = JOptionPane.showInputDialog(menu);
            if (input == null) {
                break;
            }

            try {
                int opcao = Integer.parseInt(input);
                switch (opcao) {
                    case 1: adicionarLivro(gerenciador); break;
                    case 2: listar(gerenciador.listarTodosLivros(), "Livros Cadastrados"); break;
                    case 3: adicionarAutor(gerenciador); break;
                    case 4: listar(gerenciador.listarTodosAutores(), "Autores Cadastrados"); break;
                    case 5: adicionarUsuario(gerenciador); break;
                    case 6: listar(gerenciador.listarTodosUsuarios(), "Usuários Cadastrados"); break;
                    case 7: realizarEmprestimo(gerenciador); break;
                    case 8: registrarDevolucao(gerenciador); break;
                    case 9: listar(gerenciador.listarEmprestimosAtivos(), "Empréstimos Ativos"); break;
                    case 0: break; // Sai do switch para o loop terminar
                    default: JOptionPane.showMessageDialog(null, "Opção inválida!");
                }
                if (opcao == 0) {
                    break; // Sai do loop se a opção for 0
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, insira um número válido.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro inesperado: " + e.getMessage());
            }
        } while (true);

        gerenciador.salvarDados();
        JOptionPane.showMessageDialog(null, "Dados salvos com sucesso! Saindo...");
    }

    private static void adicionarAutor(GerenciadorBiblioteca g) {
        String nome = JOptionPane.showInputDialog("Nome do Autor:");
        // <<< ALTERAÇÃO: Verifica se o usuário cancelou
        if (nome == null) return;

        String nacionalidade = JOptionPane.showInputDialog("Nacionalidade do Autor:");
        if (nacionalidade == null) return;

        if (!nome.trim().isEmpty()) {
            g.adicionarAutor(new Autor(nome, nacionalidade));
            JOptionPane.showMessageDialog(null, "Autor adicionado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "O nome do autor não pode ser vazio.");
        }
    }

    private static void adicionarLivro(GerenciadorBiblioteca g) {
        String titulo = JOptionPane.showInputDialog("Título do Livro:");
        if (titulo == null || titulo.trim().isEmpty()) return;

        ArrayList<Autor> autoresDoLivro = new ArrayList<>();
        int adicionarMaisAutores;
        do {
            String listaAutores = g.listarTodosAutores().stream()
                    .map(Autor::toString)
                    .reduce("", (a, b) -> a + b + "\n");

            String idAutorStr = JOptionPane.showInputDialog("Digite o ID de um autor existente para adicionar ao livro:\n" + listaAutores);
            if (idAutorStr == null) return;

            try {
                int idAutor = Integer.parseInt(idAutorStr);
                Autor autor = g.buscarAutorPorId(idAutor);
                if (autor != null) {
                    if (!autoresDoLivro.contains(autor)) {
                        autoresDoLivro.add(autor);
                        JOptionPane.showMessageDialog(null, "Autor '" + autor.getNome() + "' adicionado ao livro.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Este autor já foi adicionado a este livro.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Autor com ID " + idAutor + " não encontrado.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ID inválido.");
            }
            adicionarMaisAutores = JOptionPane.showConfirmDialog(null, "Deseja adicionar mais um autor a este livro?", "Adicionar Autor", JOptionPane.YES_NO_OPTION);
        } while (adicionarMaisAutores == JOptionPane.YES_OPTION);

        if (autoresDoLivro.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Um livro deve ter pelo menos um autor. Operação cancelada.");
            return;
        }

        String isbn = JOptionPane.showInputDialog("ISBN do Livro:");
        if (isbn == null) return;

        String anoStr = JOptionPane.showInputDialog("Ano de Publicação:");
        if (anoStr == null) return;
        int ano = Integer.parseInt(anoStr);

        String editora = JOptionPane.showInputDialog("Editora do Livro:");
        if (editora == null) return;

        String qtdStr = JOptionPane.showInputDialog("Quantidade de Exemplares:");
        if (qtdStr == null) return;
        int qtd = Integer.parseInt(qtdStr);

        Livro novoLivro = new Livro(titulo, isbn, ano, editora, qtd, autoresDoLivro);
        g.adicionarLivro(novoLivro);
        JOptionPane.showMessageDialog(null, "Livro adicionado com sucesso!");
    }

    private static void adicionarUsuario(GerenciadorBiblioteca g) {
        String nome = JOptionPane.showInputDialog("Nome do Usuário:");
        if (nome == null || nome.trim().isEmpty()) return;

        String cpf = JOptionPane.showInputDialog("CPF do Usuário:");
        if (cpf == null) return;

        String tel = JOptionPane.showInputDialog("Telefone do Usuário:");
        if (tel == null) return;

        String email = JOptionPane.showInputDialog("E-mail do Usuário:");
        if (email == null) return;

        g.adicionarUsuario(new Usuario(nome, cpf, tel, email));
        JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso!");
    }

    private static void realizarEmprestimo(GerenciadorBiblioteca g) {
        String idLivroStr = JOptionPane.showInputDialog("ID do Livro para empréstimo:");
        if (idLivroStr == null) return;
        int idLivro = Integer.parseInt(idLivroStr);

        String idUsuarioStr = JOptionPane.showInputDialog("ID do Usuário:");
        if (idUsuarioStr == null) return;
        int idUsuario = Integer.parseInt(idUsuarioStr);

        Emprestimo emprestimo = g.realizarEmprestimo(idLivro, idUsuario);
        if (emprestimo != null) {
            JOptionPane.showMessageDialog(null, "Empréstimo realizado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Erro: Não foi possível realizar o empréstimo.\nVerifique os IDs e a disponibilidade do livro.");
        }
    }

    private static void registrarDevolucao(GerenciadorBiblioteca g) {
        String idEmprestimoStr = JOptionPane.showInputDialog("ID do Empréstimo a ser devolvido:");
        if (idEmprestimoStr == null) return;
        int idEmprestimo = Integer.parseInt(idEmprestimoStr);

        if (g.registrarDevolucao(idEmprestimo)) {
            JOptionPane.showMessageDialog(null, "Devolução registrada com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Erro: Empréstimo não encontrado ou já devolvido.");
        }
    }

    private static <T> void listar(List<T> lista, String titulo) {
        StringBuilder sb = new StringBuilder();
        if (lista == null || lista.isEmpty()) {
            sb.append("Nenhum item encontrado.");
        } else {
            for (T item : lista) {
                sb.append(item.toString()).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, new javax.swing.JScrollPane(new javax.swing.JTextArea(sb.toString(), 15, 50)), titulo, JOptionPane.INFORMATION_MESSAGE);
    }
}