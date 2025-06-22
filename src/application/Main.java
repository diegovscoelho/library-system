package application;

import entities.*; // Importa todas as classes do pacote entities

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors; // Importa Collectors para uso em streams

/**
 * Classe principal que executa o sistema de gerenciamento de biblioteca.
 * Oferece uma interface baseada em JOptionPane para interagir com o usuário,
 * permitindo adicionar livros, autores, usuários, gerenciar empréstimos e devoluções.
 * Os dados são persistidos em um arquivo binário.
 */
public class Main {
    /**
     * O método principal que inicia a aplicação da biblioteca.
     * Carrega os dados existentes, exibe um menu de opções para o usuário
     * e executa as operações selecionadas até que o usuário decida sair e salvar.
     *
     * @param args Argumentos de linha de comando (não utilizados nesta aplicação).
     */
    public static void main(String[] args) {
        GerenciadorBiblioteca gerenciador = new GerenciadorBiblioteca();
        gerenciador.carregarDados(); // Carrega os dados da biblioteca ao iniciar

        String menu = "Sistema de Biblioteca\n\n" +
                "Gerenciar Acervo\n" +
                "1. Adicionar Livro\n" +
                "2. Listar Livros\n" +
                "3. Adicionar Autor\n" +
                "4. Listar Autores\n\n" +
                "Gerenciar Pessoas\n" +
                "5. Adicionar Usuário\n" +
                "6. Listar Usuários\n\n" +
                "Operações\n" +
                "7. Realizar Empréstimo\n" +
                "8. Registrar Devolução\n" +
                "9. Listar Empréstimos Ativos\n\n" +
                "0. Sair e Salvar\n";

        String input;
        do {
            input = JOptionPane.showInputDialog(menu);
            if (input == null) { // Trata o caso de o usuário fechar a caixa de diálogo
                break;
            }

            try {
                int opcao = Integer.parseInt(input);
                switch (opcao) {
                    case 1: adicionarLivro(gerenciador); break; // Adiciona um livro
                    case 2: listar(gerenciador.listarTodosLivros(), "Livros Cadastrados"); break; // Lista todos os livros
                    case 3: adicionarAutor(gerenciador); break; // Adiciona um autor
                    case 4: listar(gerenciador.listarTodosAutores(), "Autores Cadastrados"); break; // Lista todos os autores
                    case 5: adicionarUsuario(gerenciador); break; // Adiciona um usuário
                    case 6: listar(gerenciador.listarTodosUsuarios(), "Usuários Cadastrados"); break; // Lista todos os usuários
                    case 7: realizarEmprestimo(gerenciador); break; // Realiza um empréstimo
                    case 8: registrarDevolucao(gerenciador); break; // Registra a devolução de um empréstimo
                    case 9: listar(gerenciador.listarEmprestimosAtivos(), "Empréstimos Ativos"); break; // Lista empréstimos ativos
                    case 0: break; // Sai do switch para o loop terminar
                    default: JOptionPane.showMessageDialog(null, "Opção inválida!");
                }
                if (opcao == 0) {
                    break; // Sai do loop principal se a opção for 0
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, insira um número válido.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro inesperado: " + e.getMessage());
            }
        } while (true);

        gerenciador.salvarDados(); // Salva todos os dados da biblioteca antes de sair
        JOptionPane.showMessageDialog(null, "Dados salvos com sucesso! Saindo...");
    }

    /**
     * Solicita ao usuário as informações para adicionar um novo autor e o adiciona ao gerenciador.
     * Valida se o nome do autor não é vazio.
     *
     * @param g O objeto GerenciadorBiblioteca para adicionar o autor.
     */
    private static void adicionarAutor(GerenciadorBiblioteca g) {
        String nome = JOptionPane.showInputDialog("Nome do Autor:");
        if (nome == null) return; // Retorna se o usuário cancelou

        String nacionalidade = JOptionPane.showInputDialog("Nacionalidade do Autor:");
        if (nacionalidade == null) return; // Retorna se o usuário cancelou

        if (!nome.trim().isEmpty()) {
            g.adicionarAutor(new Autor(nome, nacionalidade)); // Adiciona o autor
            JOptionPane.showMessageDialog(null, "Autor adicionado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "O nome do autor não pode ser vazio.");
        }
    }

    /**
     * Solicita ao usuário as informações para adicionar um novo livro, incluindo autores associados.
     * Permite adicionar múltiplos autores a um único livro.
     *
     * @param g O objeto GerenciadorBiblioteca para adicionar o livro.
     */
    private static void adicionarLivro(GerenciadorBiblioteca g) {
        String titulo = JOptionPane.showInputDialog("Título do Livro:");
        if (titulo == null || titulo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O título do livro não pode ser vazio.");
            return;
        }

        ArrayList<Autor> autoresDoLivro = new ArrayList<>();
        int adicionarMaisAutores;
        do {
            // Constrói a lista de autores existentes para exibição
            String listaAutores = g.listarTodosAutores().stream()
                    .map(Autor::toString) // Converte cada Autor para sua representação de string
                    .collect(Collectors.joining("\n")); // Junta as strings com quebras de linha

            String idAutorStr = JOptionPane.showInputDialog("Digite o ID de um autor existente para adicionar ao livro:\n" + listaAutores);
            if (idAutorStr == null) return; // Retorna se o usuário cancelou

            try {
                int idAutor = Integer.parseInt(idAutorStr);
                Autor autor = g.buscarAutorPorId(idAutor); // Busca o autor pelo ID
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
                JOptionPane.showMessageDialog(null, "ID inválido. Por favor, insira um número.");
            }
            adicionarMaisAutores = JOptionPane.showConfirmDialog(null, "Deseja adicionar mais um autor a este livro?", "Adicionar Autor", JOptionPane.YES_NO_OPTION);
        } while (adicionarMaisAutores == JOptionPane.YES_OPTION);

        if (autoresDoLivro.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Um livro deve ter pelo menos um autor. Operação cancelada.");
            return;
        }

        String isbn = JOptionPane.showInputDialog("ISBN do Livro:");
        if (isbn == null || isbn.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O ISBN do livro não pode ser vazio.");
            return;
        }

        String anoStr = JOptionPane.showInputDialog("Ano de Publicação:");
        if (anoStr == null || anoStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O ano de publicação não pode ser vazio.");
            return;
        }
        int ano;
        try {
            ano = Integer.parseInt(anoStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ano de publicação inválido. Por favor, insira um número.");
            return;
        }

        String editora = JOptionPane.showInputDialog("Editora do Livro:");
        if (editora == null || editora.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "A editora do livro não pode ser vazia.");
            return;
        }

        String qtdStr = JOptionPane.showInputDialog("Quantidade de Exemplares:");
        if (qtdStr == null || qtdStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "A quantidade de exemplares não pode ser vazia.");
            return;
        }
        int qtd;
        try {
            qtd = Integer.parseInt(qtdStr);
            if (qtd <= 0) {
                JOptionPane.showMessageDialog(null, "A quantidade de exemplares deve ser um número positivo.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Quantidade de exemplares inválida. Por favor, insira um número.");
            return;
        }

        Livro novoLivro = new Livro(titulo, isbn, ano, editora, qtd, autoresDoLivro); // Cria um novo objeto Livro
        g.adicionarLivro(novoLivro); // Adiciona o livro ao gerenciador
        JOptionPane.showMessageDialog(null, "Livro adicionado com sucesso!");
    }

    /**
     * Solicita ao usuário as informações para adicionar um novo usuário e o adiciona ao gerenciador.
     * Valida se o nome do usuário não é vazio.
     *
     * @param g O objeto GerenciadorBiblioteca para adicionar o usuário.
     */
    private static void adicionarUsuario(GerenciadorBiblioteca g) {
        String nome = JOptionPane.showInputDialog("Nome do Usuário:");
        if (nome == null || nome.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O nome do usuário não pode ser vazio.");
            return;
        }

        String cpf = JOptionPane.showInputDialog("CPF do Usuário:");
        if (cpf == null || cpf.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O CPF do usuário não pode ser vazio.");
            return;
        }

        String tel = JOptionPane.showInputDialog("Telefone do Usuário:");
        if (tel == null || tel.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O telefone do usuário não pode ser vazio.");
            return;
        }

        String email = JOptionPane.showInputDialog("E-mail do Usuário:");
        if (email == null || email.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O e-mail do usuário não pode ser vazio.");
            return;
        }

        g.adicionarUsuario(new Usuario(nome, cpf, tel, email)); // Adiciona o usuário
        JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso!");
    }

    /**
     * Solicita os IDs do livro e do usuário para realizar um empréstimo.
     * Exibe uma mensagem de sucesso ou falha.
     *
     * @param g O objeto GerenciadorBiblioteca para realizar o empréstimo.
     */
    private static void realizarEmprestimo(GerenciadorBiblioteca g) {
        String idLivroStr = JOptionPane.showInputDialog("ID do Livro para empréstimo:");
        if (idLivroStr == null) return; // Retorna se o usuário cancelou
        int idLivro;
        try {
            idLivro = Integer.parseInt(idLivroStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID do Livro inválido. Por favor, insira um número.");
            return;
        }

        String idUsuarioStr = JOptionPane.showInputDialog("ID do Usuário:");
        if (idUsuarioStr == null) return; // Retorna se o usuário cancelou
        int idUsuario;
        try {
            idUsuario = Integer.parseInt(idUsuarioStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID do Usuário inválido. Por favor, insira um número.");
            return;
        }

        Emprestimo emprestimo = g.realizarEmprestimo(idLivro, idUsuario); // Realiza o empréstimo
        if (emprestimo != null) {
            JOptionPane.showMessageDialog(null, "Empréstimo realizado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Erro: Não foi possível realizar o empréstimo.\nVerifique os IDs e a disponibilidade do livro.");
        }
    }

    /**
     * Solicita o ID de um empréstimo para registrar a devolução de um livro.
     * Exibe uma mensagem de sucesso ou falha.
     *
     * @param g O objeto GerenciadorBiblioteca para registrar a devolução.
     */
    private static void registrarDevolucao(GerenciadorBiblioteca g) {
        String idEmprestimoStr = JOptionPane.showInputDialog("ID do Empréstimo a ser devolvido:");
        if (idEmprestimoStr == null) return; // Retorna se o usuário cancelou
        int idEmprestimo;
        try {
            idEmprestimo = Integer.parseInt(idEmprestimoStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID do Empréstimo inválido. Por favor, insira um número.");
            return;
        }

        if (g.registrarDevolucao(idEmprestimo)) { // Registra a devolução
            JOptionPane.showMessageDialog(null, "Devolução registrada com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Erro: Empréstimo não encontrado ou já devolvido.");
        }
    }

    /**
     * Exibe uma lista genérica de itens em uma caixa de diálogo JOptionPane.
     * Cada item na lista é convertido para sua representação de string usando o método toString().
     *
     * @param <T> O tipo dos elementos na lista.
     * @param lista A lista de itens a ser exibida.
     * @param titulo O título da caixa de diálogo.
     */
    private static <T> void listar(List<T> lista, String titulo) {
        StringBuilder sb = new StringBuilder();
        if (lista == null || lista.isEmpty()) {
            sb.append("Nenhum item encontrado.");
        } else {
            for (T item : lista) {
                sb.append(item.toString()).append("\n");
            }
        }
        // Usa JScrollPane com JTextArea para permitir rolagem se a lista for longa
        JOptionPane.showMessageDialog(null, new javax.swing.JScrollPane(new javax.swing.JTextArea(sb.toString(), 15, 50)), titulo, JOptionPane.INFORMATION_MESSAGE);
    }
}