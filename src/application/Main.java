package application;

import entities.Biblioteca;
import entities.Emprestimo;
import entities.Livro;
import entities.Usuario;

import javax.swing.JOptionPane;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.carregarDados();

        String menu = "Sistema de Biblioteca\n\n" +
                "1. Adicionar Livro\n" +
                "2. Listar Livros\n" +
                "3. Adicionar Usuário\n" +
                "4. Listar Usuários\n" +
                "5. Realizar Empréstimo\n" +
                "6. Registrar Devolução\n" +
                "7. Listar Empréstimos Ativos\n\n" +
                "0. Sair e Salvar";

        int opcao;
        do {
            try {
                opcao = Integer.parseInt(JOptionPane.showInputDialog(menu));

                switch (opcao) {
                    case 1:
                        adicionarLivro(biblioteca);
                        break;
                    case 2:
                        listar(biblioteca.listarTodosLivros(), "Livros Cadastrados");
                        break;
                    case 3:
                        adicionarUsuario(biblioteca);
                        break;
                    case 4:
                        listar(biblioteca.listarTodosUsuarios(), "Usuários Cadastrados");
                        break;
                    case 5:
                        realizarEmprestimo(biblioteca);
                        break;
                    case 6:
                        registrarDevolucao(biblioteca);
                        break;
                    case 7:
                        listar(biblioteca.listarEmprestimosAtivos(), "Empréstimos Ativos");
                        break;
                    case 0:
                        biblioteca.salvarDados();
                        JOptionPane.showMessageDialog(null, "Dados salvos com sucesso! Saindo...");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opção inválida!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, insira um número válido.");
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private static void adicionarLivro(Biblioteca b) {
        String titulo = JOptionPane.showInputDialog("Título do Livro:");
        String autor = JOptionPane.showInputDialog("Autor do Livro:");
        String isbn = JOptionPane.showInputDialog("ISBN do Livro:");
        int ano = Integer.parseInt(JOptionPane.showInputDialog("Ano de Publicação:"));
        String editora = JOptionPane.showInputDialog("Editora do Livro:");
        int qtd = Integer.parseInt(JOptionPane.showInputDialog("Quantidade de Exemplares:"));

        Livro novoLivro = new Livro(titulo, autor, isbn, ano, editora, qtd);
        b.adicionarLivro(novoLivro);
        JOptionPane.showMessageDialog(null, "Livro adicionado com sucesso!");
    }

    private static void adicionarUsuario(Biblioteca b) {
        String nome = JOptionPane.showInputDialog("Nome do Usuário:");
        String cpf = JOptionPane.showInputDialog("CPF do Usuário:");
        String tel = JOptionPane.showInputDialog("Telefone do Usuário:");
        String email = JOptionPane.showInputDialog("E-mail do Usuário:");

        Usuario novoUsuario = new Usuario(nome, cpf, tel, email);
        b.adicionarUsuario(novoUsuario);
        JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso!");
    }

    private static void realizarEmprestimo(Biblioteca b) {
        int idLivro = Integer.parseInt(JOptionPane.showInputDialog("ID do Livro para empréstimo:"));
        int idUsuario = Integer.parseInt(JOptionPane.showInputDialog("ID do Usuário:"));

        Emprestimo emprestimo = b.realizarEmprestimo(idLivro, idUsuario);
        if (emprestimo != null) {
            JOptionPane.showMessageDialog(null, "Empréstimo realizado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Erro: Não foi possível realizar o empréstimo.\nVerifique os IDs e a disponibilidade do livro.");
        }
    }

    private static void registrarDevolucao(Biblioteca b) {
        int idEmprestimo = Integer.parseInt(JOptionPane.showInputDialog("ID do Empréstimo a ser devolvido:"));
        if (b.registrarDevolucao(idEmprestimo)) {
            JOptionPane.showMessageDialog(null, "Devolução registrada com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Erro: Empréstimo não encontrado ou já devolvido.");
        }
    }

    private static <T> void listar(List<T> lista, String titulo) {
        StringBuilder sb = new StringBuilder();
        if (lista.isEmpty()) {
            sb.append("Nenhum item encontrado.");
        } else {
            for (T item : lista) {
                sb.append(item.toString()).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, sb.toString(), titulo, JOptionPane.INFORMATION_MESSAGE);
    }
}