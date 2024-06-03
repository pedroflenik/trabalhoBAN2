package apresentacao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import dados.*;
import java.time.format.DateTimeParseException;
import logico.Sistema;

public class gui {
    private static guiDono guiDono;
    private static Sistema sistema;
    private static guiMecanico guiMecanico;
    private static guiCompras guiCompras;
    public gui() {
        this.sistema = new Sistema();
        guiDono = new guiDono(sistema);
        guiMecanico = new guiMecanico(sistema);
        guiCompras = new guiCompras(sistema);
    }

    public static LocalDate parseLocalDate(String dateString) {
        String[] parts = dateString.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);
        return LocalDate.of(year, month, day);
    }

    public static void menuCadastroAdmin(Frame frameAnterior) {

        JFrame frame = new JFrame("Cadastro de Administrador");


        frame.setLayout(new GridLayout(5, 2));


        JLabel lblNome = new JLabel("Nome:");
        JTextField txtNome = new JTextField();
        JLabel lblCpf = new JLabel("CPF:");
        JTextField txtCpf = new JTextField();
        JLabel lblTelefone = new JLabel("Telefone:");
        JTextField txtTelefone = new JTextField();
        JLabel lblDataContratacao = new JLabel("Data de Contratação (yyyy-MM-dd):");
        JTextField txtDataContratacao = new JTextField();


        frame.add(lblNome);
        frame.add(txtNome);
        frame.add(lblCpf);
        frame.add(txtCpf);
        frame.add(lblTelefone);
        frame.add(txtTelefone);
        frame.add(lblDataContratacao);
        frame.add(txtDataContratacao);


        JButton btnCadastrar = new JButton("Cadastrar");


        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nome = txtNome.getText();
                String cpf = txtCpf.getText();
                String telefone = txtTelefone.getText();
                String dataContratacaoStr = txtDataContratacao.getText();


                if (nome.isEmpty() || cpf.isEmpty() || telefone.isEmpty() || dataContratacaoStr.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Por favor, preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                LocalDate dataContratacao;
                try {
                    dataContratacao = LocalDate.parse(dataContratacaoStr);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Formato de data inválido! Use o formato yyyy-MM-dd.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Funcionario novoFuncionario = new Funcionario();
                novoFuncionario.setTipo('D');
                novoFuncionario.setCpf(cpf);
                novoFuncionario.setTelefone(telefone);
                novoFuncionario.setNome(nome);
                novoFuncionario.setDataContratacao(dataContratacao);
                novoFuncionario.setIdDepartamento(1);
                try {
                    sistema.cadastraFuncionario(novoFuncionario);
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(frame, "Falha no cadastro de funcionario", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            }
        });


        JButton btnVoltar = new JButton("Voltar");

        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
               frameAnterior.setVisible(true);

            }
        });


        frame.add(btnCadastrar);

        frame.add(btnVoltar);

        frame.setSize(500, 300);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }


    public static void menuLogin(Frame frameAnterior) {

        JFrame frame = new JFrame("Login");


        frame.setLayout(new GridLayout(2, 2)); // 2 linhas e 2 colunas


        JLabel lblCpf = new JLabel("CPF:");
        JTextField txtCpf = new JTextField();


        frame.add(lblCpf);
        frame.add(txtCpf);


        JButton btnLogin = new JButton("Login");


        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String cpf = txtCpf.getText();

                // Verifica se o campo CPF está vazio
                if (cpf.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Por favor, insira seu CPF!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                try {
                    Funcionario usuarioAtivo = sistema.efetuaLogin(cpf);
                    if(usuarioAtivo != null){
                        frame.dispose(); // Fecha a janela de login
                        frameAnterior.setVisible(false);
                        switch (usuarioAtivo.getTipo()){
                            case 'D':
                                guiDono.menuDono(frameAnterior);
                            break;
                            case 'M':
                                guiMecanico.menuMecanico(frameAnterior);
                                break;
                            case 'C':
                                guiCompras.menuCompras(frameAnterior);
                                break;
                        }
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(frame, "Falha ao efetuar o login!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            }
        });


        JButton btnVoltar = new JButton("Voltar");


        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                frameAnterior.setVisible(true);
            }
        });


        frame.add(btnLogin);
        frame.add(btnVoltar);


        frame.setSize(400, 150);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.setLocationRelativeTo(null);


        frame.setVisible(true);
    }

    // Menu inicial
    public static void menuInicial(String[] args) {
        JFrame frame = new JFrame("Menu Inical");

        frame.setLayout(new BorderLayout());

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER));

        painelBotoes.add(Box.createVerticalStrut(150));


        JButton botao1 = new JButton("Cadastrar");
        JButton botao2 = new JButton("Login");


        botao1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                frame.setVisible(false);
               menuCadastroAdmin(frame);
            }
        });

        botao2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO:Terminar isso aqui
                frame.setVisible(false);
                menuLogin(frame);
            }
        });


        painelBotoes.add(botao1);
        painelBotoes.add(botao2);


        frame.add(painelBotoes, BorderLayout.CENTER);


        frame.setSize(300, 200);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.setLocationRelativeTo(null);


        frame.setVisible(true);
    }

    public static void main(String[] args) {
        sistema = new Sistema();
        guiDono = new guiDono(sistema);
        guiMecanico = new guiMecanico(sistema);
        guiCompras = new guiCompras(sistema);
        menuInicial(args);
    }
}
