package apresentacao;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import dados.*;
import java.time.format.DateTimeParseException;

import javafx.scene.control.ComboBox;
import logico.Sistema;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class guiMecanico {

    private static Sistema sistema;

    public guiMecanico(Sistema sistema) {
        this.sistema = sistema;
    }


    public static int extrairId(String textoCompleto) {
        // Divide o texto completo usando espaços em branco como delimitadores
        String[] partes = textoCompleto.split("\\s+");

        // A primeira parte deve conter o ID
        String idTexto = partes[0].trim();

        // Converte o ID para um valor inteiro e retorna
        return Integer.parseInt(idTexto);
    }

    public static int extrairSegundoCampo(String textoCompleto) {
        // Divide o texto completo usando espaços em branco como delimitadores
        String[] partes = textoCompleto.split("\\s+");

        // A primeira parte deve conter o ID
        String idTexto = partes[3].trim();

        // Converte o ID para um valor inteiro e retorna
        return Integer.parseInt(idTexto);
    }


    public static void menuCadastroMecanico(Frame frameAnterior) {
        JFrame frame = new JFrame("Cadastro de Cliente");

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel cadastroClientePanel = new JPanel(new BorderLayout());

        JPanel formularioClientePanel = new JPanel(new GridLayout(3, 2));
        formularioClientePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formularioClientePanel.add(new JLabel("Nome:"));
        JTextField nomeTextField = new JTextField();
        formularioClientePanel.add(nomeTextField);
        formularioClientePanel.add(new JLabel("CPF:"));
        JTextField cpfTextField = new JTextField();
        formularioClientePanel.add(cpfTextField);
        formularioClientePanel.add(new JLabel("Telefone:"));
        JTextField telefoneTextField = new JTextField();
        formularioClientePanel.add(telefoneTextField);

        JButton cadastrarClienteButton = new JButton("Cadastrar Cliente");
        cadastrarClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Recupera os dados do formulário
                String nome = nomeTextField.getText();
                String cpf = cpfTextField.getText();
                String telefone = telefoneTextField.getText();

                Cliente novoCliente = new Cliente();
                novoCliente.setNome(nome);
                novoCliente.setCpf(cpf);
                novoCliente.setTelefone(telefone);

                if (nome.isEmpty() || cpf.isEmpty() || telefone.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Por favor, preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    sistema.cadastraCliente(novoCliente);
                    JOptionPane.showMessageDialog(frame, "Cliente cadastrado", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Falha ao cadastrar cliente", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        JPanel botoesClientePanel = criarBotoesPanel(frameAnterior, frame, formularioClientePanel);

        cadastroClientePanel.add(cadastrarClienteButton, BorderLayout.NORTH);
        cadastroClientePanel.add(formularioClientePanel, BorderLayout.CENTER);
        cadastroClientePanel.add(botoesClientePanel, BorderLayout.SOUTH);

        JPanel cadastroVeiculoPanel = new JPanel(new BorderLayout());

        JPanel formularioVeiculoPanel = new JPanel(new GridLayout(4, 2));
        formularioVeiculoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formularioVeiculoPanel.add(new JLabel("Modelo:"));
        JTextField modeloTxt = new JTextField();
        formularioVeiculoPanel.add(modeloTxt);
        formularioVeiculoPanel.add(new JLabel("Marca:"));
        JTextField marcaTxt = new JTextField();
        formularioVeiculoPanel.add(marcaTxt);
        formularioVeiculoPanel.add(new JLabel("Ano:"));
        JTextField anoTxt = new JTextField();
        formularioVeiculoPanel.add(anoTxt);

        formularioVeiculoPanel.add(new JLabel("Dono:"));
        JComboBox<String> donoComboBox = new JComboBox<>();
        List<Cliente> clientes = null;
        try {
            clientes = sistema.getClientes();
        } catch (Exception ex) {
            System.out.println("ERRO: No getClientes " + ex.getMessage());
        }
        if (clientes != null) {
            donoComboBox.addItem("--- Escolher ---");
            for (Cliente c : clientes) {
                donoComboBox.addItem(c.getIdCliente() + " - " + c.getNome() + " - " + c.getCpf());
            }
            clientes.clear();
        } else {
            donoComboBox.addItem("Vazio");
        }


        formularioVeiculoPanel.add(donoComboBox);

        JButton cadastrarVeiculoButton = new JButton("Cadastrar Veículo");

        cadastrarVeiculoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String textoComboBox = (String) donoComboBox.getSelectedItem();
                if (textoComboBox == "Vazio") {
                    JOptionPane.showMessageDialog(frame, "Cadastre um cliente primeiro!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (textoComboBox == "--- Escolher ---") {
                    JOptionPane.showMessageDialog(frame, "Escolha um dono para o veículo!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String modelo = modeloTxt.getText();
                String marca = marcaTxt.getText();
                String ano = anoTxt.getText();
                int idDono = extrairId((String) donoComboBox.getSelectedItem());


                if (modelo.isEmpty() || marca.isEmpty() || ano.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Por favor, preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Veiculo novoVeiculo = new Veiculo();
                novoVeiculo.setModelo(modelo);
                novoVeiculo.setMarca(marca);
                novoVeiculo.setAno(ano);
                novoVeiculo.setIdDono(idDono);


                try {
                    sistema.cadastrarVeiculo(novoVeiculo);
                    JOptionPane.showMessageDialog(frame, "Veiculo cadastrado", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Falha ao cadastrar veiculo", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        JPanel botoesVeiculoPanel = criarBotoesPanel(frameAnterior, frame, formularioVeiculoPanel);

        cadastroVeiculoPanel.add(cadastrarVeiculoButton, BorderLayout.NORTH);
        cadastroVeiculoPanel.add(formularioVeiculoPanel, BorderLayout.CENTER);
        cadastroVeiculoPanel.add(botoesVeiculoPanel, BorderLayout.SOUTH);


        JPanel cadastroPedidoPersonalizacaoPanel = new JPanel(new BorderLayout());

        JPanel formularioPedidoPersonalizacaoPanel = new JPanel(new GridLayout(7, 2));
        formularioPedidoPersonalizacaoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        formularioPedidoPersonalizacaoPanel.add(new JLabel("Data Entrega (yyyy-MM-dd):"));
        JTextField dataEntregaTxt = new JTextField();
        formularioPedidoPersonalizacaoPanel.add(dataEntregaTxt);
        formularioPedidoPersonalizacaoPanel.add(new JLabel("Descricao:"));
        JTextField descricaoTxt = new JTextField();
        formularioPedidoPersonalizacaoPanel.add(descricaoTxt);
        formularioPedidoPersonalizacaoPanel.add(new JLabel("Valor Personalização:"));
        SpinnerModel spinnerModel = new SpinnerNumberModel(0.0, 0.0, null, 0.01);
        JSpinner valorPersonalizacaoSpiner = new JSpinner(spinnerModel);
        formularioPedidoPersonalizacaoPanel.add(valorPersonalizacaoSpiner);


        formularioPedidoPersonalizacaoPanel.add(new JLabel("Veículo:"));
        JComboBox<String> veiculoComboBox = new JComboBox<>();
        List<Veiculo> veiculos = null;
        try {
            veiculos = sistema.getVeiculos();
        } catch (Exception ex) {
            System.out.println("ERRO: No getVeiculos " + ex.getMessage());
        }
        if (veiculos != null) {
            veiculoComboBox.addItem("--- Escolher ---");
            for (Veiculo v : veiculos) {
                veiculoComboBox.addItem(v.getIdVeiculo() + " - Dono: " + v.getIdDono() + " - Modelo: " + v.getModelo());
            }
            veiculos.clear();
        } else {
            veiculoComboBox.addItem("Vazio");
        }

        formularioPedidoPersonalizacaoPanel.add(veiculoComboBox);

        formularioPedidoPersonalizacaoPanel.add(new JLabel("Departamento Responsável:"));
        JComboBox<String> departamentoComboBox = new JComboBox<>();

        List<Departamento> departamentos = null;
        try {
            departamentos = sistema.getDepartamentos();
        } catch (Exception ex) {
            System.out.println("ERRO: No getDepartamentos " + ex.getMessage());
        }
        if (departamentos.size() > 2) {
            departamentoComboBox.addItem("--- Escolher ---");
            for (Departamento d : departamentos) {
                if (d.getIdDep() != 1 && d.getIdDep() != 2) {
                    departamentoComboBox.addItem(d.getIdDep() + " - Nome: " + d.getNome() + " - Tipo Veiculo: " + d.getTipoVeiculo());
                }
            }
            departamentos.clear();
        } else {
            departamentoComboBox.addItem("Vazio");
        }
        formularioPedidoPersonalizacaoPanel.add(departamentoComboBox);


        //
        formularioPedidoPersonalizacaoPanel.add(new JLabel("Produto:"));
        JComboBox<String> produtoComboBox = new JComboBox<>();

        List<Produto> produtos = new ArrayList<>();
        try {
            produtos = sistema.getProdutos();
        } catch (Exception ex) {
            System.out.println("ERRO: No getDepartamentos " + ex.getMessage());
        }
        if (produtos.size() > 2) {
            produtoComboBox.addItem("--- Escolher ---");
            for (Produto p : produtos) {
                produtoComboBox.addItem(p.getIdProduto() + " - Nome: " + p.getNome() + " - Quantidade em estoque: " + p.getQuantidadeEstoque());
            }
            produtos.clear();
        } else {
            produtoComboBox.addItem("Vazio");
        }
        formularioPedidoPersonalizacaoPanel.add(produtoComboBox);


        formularioPedidoPersonalizacaoPanel.add(new JLabel("Quantidade produto:"));
        SpinnerModel spinnerModel2 = new SpinnerNumberModel(0.0, 0.0, null, 1);
        JSpinner quantidadeProdutoSpinner = new JSpinner(spinnerModel2);
        formularioPedidoPersonalizacaoPanel.add(quantidadeProdutoSpinner);


        JButton cadastrarPedidoDePersonalizacaoButton = new JButton("Cadastrar Pedido de Personalização");
        cadastrarPedidoDePersonalizacaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String textoComboBoxDep = (String) departamentoComboBox.getSelectedItem();
                if (textoComboBoxDep == "Vazio") {
                    JOptionPane.showMessageDialog(frame, "Cadastre um departamento primeiro!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (textoComboBoxDep == "--- Escolher ---") {
                    JOptionPane.showMessageDialog(frame, "Escolha um departamento!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String textoComboBoxVeiculo = (String) veiculoComboBox.getSelectedItem();
                if (textoComboBoxVeiculo == "Vazio") {
                    JOptionPane.showMessageDialog(frame, "Cadastre um veículo primeiro!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (textoComboBoxVeiculo == "--- Escolher ---") {
                    JOptionPane.showMessageDialog(frame, "Escolha veículo!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String textoComboProduto = (String) produtoComboBox.getSelectedItem();
                if (textoComboProduto == "Vazio") {
                    JOptionPane.showMessageDialog(frame, "Cadastre um produto primeiro!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (textoComboProduto == "--- Escolher ---") {
                    JOptionPane.showMessageDialog(frame, "Escolha produto!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String dataEntrega = dataEntregaTxt.getText();
                String descricao = descricaoTxt.getText();
                double valorPersonalizacao = (double) valorPersonalizacaoSpiner.getValue();
                int idDepartamento = extrairId((String) departamentoComboBox.getSelectedItem());
                int idVeiculo = extrairId((String) veiculoComboBox.getSelectedItem());
                int idProduto = extrairId((String) produtoComboBox.getSelectedItem());
                double quantidadeDouble = (double) quantidadeProdutoSpinner.getValue();
                int quantidade = (int) quantidadeDouble;

                if (dataEntrega.isEmpty() || descricao.isEmpty() || valorPersonalizacao == 0) {
                    JOptionPane.showMessageDialog(frame, "Por favor, preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                LocalDate dataEntregad;
                try {
                    dataEntregad = LocalDate.parse(dataEntrega);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Formato de data inválido! Use o formato yyyy-MM-dd.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return; // Sai do método se a data estiver em um formato inválido
                }

                PedidoPersonalizacao novoPedidoPersonalizacao = new PedidoPersonalizacao();
                novoPedidoPersonalizacao.setDataEntrega(dataEntregad);
                novoPedidoPersonalizacao.setDescricao(descricao);
                novoPedidoPersonalizacao.setDepartamentoResponsavel(idDepartamento);
                novoPedidoPersonalizacao.setValorPersonalizacao(valorPersonalizacao);
                novoPedidoPersonalizacao.setIdVeiculo(idVeiculo);
                novoPedidoPersonalizacao.setQuantidade(quantidade);
                novoPedidoPersonalizacao.setIdProduto(idProduto);
                try {
                    sistema.cadastrarPedidoPersonalizacao(novoPedidoPersonalizacao);
                    JOptionPane.showMessageDialog(frame, "Pedido de personalização cadastrado", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Falha ao cadastrar pedido de personalização", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });


        JPanel botoesPedidoPersonalizacaoPanel = criarBotoesPanel(frameAnterior, frame, formularioPedidoPersonalizacaoPanel);

        cadastroPedidoPersonalizacaoPanel.add(cadastrarPedidoDePersonalizacaoButton, BorderLayout.NORTH);
        cadastroPedidoPersonalizacaoPanel.add(formularioPedidoPersonalizacaoPanel, BorderLayout.CENTER);
        cadastroPedidoPersonalizacaoPanel.add(botoesPedidoPersonalizacaoPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Cadastro de Cliente", cadastroClientePanel);
        tabbedPane.addTab("Cadastro de Veículo", cadastroVeiculoPanel);
        tabbedPane.addTab("Cadastro de Pedido de Personalização", cadastroPedidoPersonalizacaoPanel);

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = tabbedPane.getSelectedIndex();
                if (selectedIndex == 0) {
                    frame.setSize(600, 300);
                    frame.setTitle("Cadastro de Cliente");
                } else if (selectedIndex == 1) {
                    frame.setSize(600, 300);
                    frame.setTitle("Cadastro de Veículo");
                    donoComboBox.removeAllItems();
                    List<Cliente> clientes = null;
                    try {
                        clientes = sistema.getClientes();
                    } catch (Exception ex) {
                        System.out.println("ERRO: No getClientes " + ex.getMessage());
                    }
                    if (clientes != null) {
                        donoComboBox.addItem("--- Escolher ---");
                        for (Cliente c : clientes) {
                            donoComboBox.addItem(c.getIdCliente() + " - " + c.getNome() + " - " + c.getCpf());
                        }
                        clientes.clear();
                    } else {
                        donoComboBox.addItem("Vazio");
                    }
                } else if (selectedIndex == 2) {
                    frame.setSize(600, 350);
                    frame.setTitle("Cadastro de Pedido de Personalização");
                    departamentoComboBox.removeAllItems();
                    List<Departamento> departamentos = null;
                    try {
                        departamentos = sistema.getDepartamentos();
                    } catch (Exception ex) {
                        System.out.println("ERRO: No getDepartamentos " + ex.getMessage());
                    }
                    if (departamentos.size() > 2) {
                        departamentoComboBox.addItem("--- Escolher ---");
                        for (Departamento d : departamentos) {
                            if (d.getIdDep() != 1 && d.getIdDep() != 2) {
                                departamentoComboBox.addItem(d.getIdDep() + " - Nome: " + d.getNome() + " - Tipo Veiculo: " + d.getTipoVeiculo());
                            }
                        }
                        departamentos.clear();
                    } else {
                        departamentoComboBox.addItem("Vazio");
                    }
                    veiculoComboBox.removeAllItems();
                    List<Veiculo> veiculos = null;
                    try {
                        veiculos = sistema.getVeiculos();
                    } catch (Exception ex) {
                        System.out.println("ERRO: No getVeiculos " + ex.getMessage());
                    }
                    if (veiculos != null) {
                        veiculoComboBox.addItem("--- Escolher ---");
                        for (Veiculo v : veiculos) {
                            veiculoComboBox.addItem(v.getIdVeiculo() + " - Dono: " + v.getIdDono() + " - Modelo: " + v.getModelo());
                        }
                        veiculos.clear();
                    } else {
                        veiculoComboBox.addItem("Vazio");
                    }
                }
            }
        });

        frame.add(tabbedPane);
        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private static JPanel criarBotoesPanel(Frame frameAnterior, Frame frameAtual, Container formularioLimpar) {
        JPanel botoesPanel = new JPanel(new FlowLayout());
        JButton voltarButton = new JButton("Voltar");
        JButton limparButton = new JButton("Limpar");

        voltarButton.addActionListener(e -> {
            frameAtual.dispose();
            frameAnterior.setVisible(true);
        });

        limparButton.addActionListener(e -> {
            limparCampos(formularioLimpar);
        });

        botoesPanel.add(voltarButton);
        botoesPanel.add(limparButton);

        return botoesPanel;
    }

    private static void limparCampos(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JTextField) {
                ((JTextField) component).setText("");
            }else if(component instanceof  JSpinner){
                ((JSpinner) component).setValue(0);
            }else if(component  instanceof JComboBox<?>){
                ((JComboBox) component).setSelectedIndex(0);
            }
        }
    }
    public static void menuConsultaRProdutos(Frame frameAnterior){
        JFrame frame = new JFrame("Consulta de Produtos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);


        JTabbedPane tabbedPane = new JTabbedPane();


        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());


        String[] colunas = {"ID", "Nome", "Preço unitario", "Quantidade em estoque","Fornecedor principal","Tipo","Quantidade minima","Descricao"};

        List<Produto> produtos = new ArrayList<>();
        try{
            produtos = sistema.getProdutos();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        Object[][] dados = new Object[produtos.size()][colunas.length];
        for (int i = 0; i < produtos.size(); i++) {
            Produto produto = produtos.get(i);
            dados[i][0] = produto.getIdProduto();
            dados[i][1] = produto.getNome();
            dados[i][2] = produto.getPrecoPorUnidade();
            dados[i][3] = produto.getQuantidadeEstoque();
            dados[i][4] = produto.getFornecedorPrincipal();
            dados[i][5] = produto.getTipo();
            dados[i][6] = produto.getQuantidadeMinima();
            dados[i][7] = produto.getDescricao();
        }

        // Criar a tabela
        JTable table = new JTable(dados, colunas);

        // Criar o botão voltar
        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(e -> {
            // Fechar a janela atual
            frame.dispose();
            // Voltar para a janela anterior
            frameAnterior.setVisible(true);
        });

        // Adicionar a tabela e o botão ao painel
        panel1.add(new JScrollPane(table), BorderLayout.CENTER);
        panel1.add(voltarButton, BorderLayout.SOUTH);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());

        // Criar as colunas
        String[] colunasFornecedores = {"ID", "Nome","Endereço","telefone"};

        List<Fornecedor> fornecedores = new ArrayList<>();
        try{
            fornecedores = sistema.getFornecedores();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        // Criar os dadosNotificacoes
        Object[][] dadosFornecedores = new Object[fornecedores.size()][colunasFornecedores.length];
        for (int i = 0; i < fornecedores.size(); i++) {
            Fornecedor fornecedor = fornecedores.get(i);
            dadosFornecedores[i][0] = fornecedor.getIdFornecedor();
            dadosFornecedores[i][1] = fornecedor.getNome();
            dadosFornecedores[i][2] = fornecedor.getEndereco();
            dadosFornecedores[i][3] = fornecedor.getTelefone();
        }

        // Criar a fornecedor
        JTable table2 = new JTable(dadosFornecedores, colunasFornecedores);

        // Criar o botão voltar
        JButton voltarButton2 = new JButton("Voltar");
        voltarButton2.addActionListener(e -> {
            // Fechar a janela atual
            frame.dispose();
            // Voltar para a janela anterior
            frameAnterior.setVisible(true);
        });

        // Adicionar a tabela e o botão ao painel
        panel2.add(new JScrollPane(table2), BorderLayout.CENTER);
        panel2.add(voltarButton2, BorderLayout.SOUTH);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout());

        // Criar as colunas
        String[] colunasNotificacoes = {"ID", "Id produto","Quantidade","data limite"};

        List<Notificacao> notificacoes = new ArrayList<>();
        try{
            notificacoes = sistema.getNotificacoes();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        // Criar os dados
        Object[][] dadosNotificacoes = new Object[notificacoes.size()][colunasNotificacoes.length];
        for (int i = 0; i < notificacoes.size(); i++) {
            Notificacao notificacao = notificacoes.get(i);
            dadosNotificacoes[i][0] = notificacao.getIdNotificao();
            dadosNotificacoes[i][1] = notificacao.getIdProduto();
            dadosNotificacoes[i][2] = notificacao.getQuantidade();
            dadosNotificacoes[i][3] = notificacao.getDataLimite();
        }

        // Criar a fornecedor
        JTable table3 = new JTable(dadosNotificacoes, colunasNotificacoes);

        // Criar o botão voltar
        JButton voltarButton3 = new JButton("Voltar");
        voltarButton3.addActionListener(e -> {
            // Fechar a janela atual
            frame.dispose();
            // Voltar para a janela anterior
            frameAnterior.setVisible(true);
        });

        // Adicionar a tabela e o botão ao painel
        panel3.add(new JScrollPane(table3), BorderLayout.CENTER);
        panel3.add(voltarButton3, BorderLayout.SOUTH);

        JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout());

        // Criar as colunas
        String[] colunasPedidosCompra = {"ID", "Id fornecedor","Id notificao","Quantidade","total Compra","id produto", "entregue"};

        List<PedidoCompra> pedidosCompra = new ArrayList<>();
        try{
            pedidosCompra = sistema.getPedidosCompra();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        // Criar os dados
        Object[][] dadosPedidoCompra = new Object[pedidosCompra.size()][colunasPedidosCompra.length];
        for (int i = 0; i < pedidosCompra.size(); i++) {
            PedidoCompra pedido = pedidosCompra.get(i);
            dadosPedidoCompra[i][0] = pedido.getIdPedidoCompra();
            dadosPedidoCompra[i][1] = pedido.getIdFornecedor();
            dadosPedidoCompra[i][2] = pedido.getIdNotificacao();
            dadosPedidoCompra[i][3] = pedido.getQuantidade();
            dadosPedidoCompra[i][4] = pedido.getTotalCompra();
            dadosPedidoCompra[i][5] = pedido.getIdProduto();
            dadosPedidoCompra[i][6] = pedido.isEntregue();
        }

        // Criar a fornecedor
        JTable table4 = new JTable(dadosPedidoCompra, colunasPedidosCompra);

        // Criar o botão voltar
        JButton voltarButton4 = new JButton("Voltar");
        voltarButton4.addActionListener(e -> {
            // Fechar a janela atual
            frame.dispose();
            // Voltar para a janela anterior
            frameAnterior.setVisible(true);
        });

        // Adicionar a tabela e o botão ao painel
        panel4.add(new JScrollPane(table4), BorderLayout.CENTER);
        panel4.add(voltarButton4, BorderLayout.SOUTH);



        // Adicionar a aba ao painel de abas
        tabbedPane.addTab("Consulta Produtos", panel1);
        tabbedPane.addTab("Consulta Fornecedores", panel2);
        tabbedPane.addTab("Consulta Notificacoes", panel3);
        tabbedPane.addTab("Consulta Pedidos Compra", panel4);
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Obtém o índice da aba atualmente selecionada
                int selectedIndex = tabbedPane.getSelectedIndex();
                // Aqui você pode adicionar ação com base na aba selecionada
                if (selectedIndex == 0) {
                    frame.setTitle("Consulta Produtos");
                    frame.setSize(700, 400);
                } else if (selectedIndex == 1) {
                    frame.setTitle("Consulta Fornecedores");
                    frame.setSize(700, 400);
                }else if(selectedIndex == 2){
                    frame.setTitle("Consulta Notificacoes");
                    frame.setSize(700, 400);
                }else if(selectedIndex == 3){
                    frame.setTitle("Consulta Pedidos Compra");
                    frame.setSize(700, 400);
                }
            }
        });

        // Adicionar o painel de abas ao frame
        frame.add(tabbedPane);

        // Exibir a janela
        frame.setVisible(true);

    }


    public static void menuConsultaRClientes(Frame frameAnterior) {
        // Criar uma nova janela
        JFrame frame = new JFrame("Consulta de Clientes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Criar uma aba
        JTabbedPane tabbedPane = new JTabbedPane();

        // Criar o painel para a primeira aba
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());

        // Criar as colunas
        String[] colunas = {"ID", "Nome", "CPF", "Telefone"};

        List<Cliente> clientes = new ArrayList<>();

        try {
            clientes = sistema.getClientes();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        // Criar os dados
        Object[][] dados = new Object[clientes.size()][4];
        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            dados[i][0] = cliente.getIdCliente();
            dados[i][1] = cliente.getNome();
            dados[i][2] = cliente.getCpf();
            dados[i][3] = cliente.getTelefone();
        }

        // Criar a tabela
        JTable table = new JTable(dados, colunas);

        // Criar o botão voltar
        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(e -> {
            // Fechar a janela atual
            frame.dispose();
            // Voltar para a janela anterior
            frameAnterior.setVisible(true);
        });

        // Adicionar a tabela e o botão ao painel
        panel1.add(new JScrollPane(table), BorderLayout.CENTER);
        panel1.add(voltarButton, BorderLayout.SOUTH);

        //Veiculos
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());

        // Criar as colunas
        String[] colunasVeiculos = {"ID", "Modelo", "Ano", "Marca","idDono"};

        List<Veiculo> veiculos = new ArrayList<>();

        try {
            veiculos = sistema.getVeiculos();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        // Criar os dados
        Object[][] dadosVeiculos = new Object[veiculos.size()][5];
        for (int i = 0; i < veiculos.size(); i++) {
            Veiculo veiculo = veiculos.get(i);
            dadosVeiculos[i][0] = veiculo.getIdVeiculo();
            dadosVeiculos[i][1] = veiculo.getModelo();
            dadosVeiculos[i][2] = veiculo.getAno();
            dadosVeiculos[i][3] = veiculo.getMarca();
            dadosVeiculos[i][4] = veiculo.getIdDono();
        }

        // Criar a tabela
        JTable table2 = new JTable(dadosVeiculos, colunasVeiculos);

        // Criar o botão voltar
        JButton voltarButton2 = new JButton("Voltar");
        voltarButton2.addActionListener(e -> {
            // Fechar a janela atual
            frame.dispose();
            // Voltar para a janela anterior
            frameAnterior.setVisible(true);
        });

        // Adicionar a tabela e o botão ao painel
        panel2.add(new JScrollPane(table2), BorderLayout.CENTER);
        panel2.add(voltarButton2, BorderLayout.SOUTH);

        //Pedidos Personalização
        JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout());

        // Criar as colunas
        String[] colunasPedidosPesonalizacao = {"ID", "dataEntrega", "descricao", "valor Personalização", "id veiculo", "departamento responsável", "Quantidade produto","Id produto"};

        List<PedidoPersonalizacao> pedidosPersonalizacao = new ArrayList<>();

        try {
            pedidosPersonalizacao = sistema.getPerdidosPersonalizacao();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Object[][] dadosPedidosPersonalizacao = new Object[pedidosPersonalizacao.size()][8];
        for (int i = 0; i < pedidosPersonalizacao.size(); i++) {
            PedidoPersonalizacao pedido = pedidosPersonalizacao.get(i);
            dadosPedidosPersonalizacao[i][0] = pedido.getIdPedido();
            dadosPedidosPersonalizacao[i][1] = pedido.getDataEntrega();
            dadosPedidosPersonalizacao[i][2] = pedido.getDescricao();
            dadosPedidosPersonalizacao[i][3] = pedido.getValorPersonalizacao();
            dadosPedidosPersonalizacao[i][4] = pedido.getIdVeiculo();
            dadosPedidosPersonalizacao[i][5] = pedido.getDepartamentoResponsavel();
            dadosPedidosPersonalizacao[i][6] = pedido.getQuantidade();
            dadosPedidosPersonalizacao[i][7] = pedido.getIdProduto();
        }

        // Criar a tabela
        JTable table3 = new JTable(dadosPedidosPersonalizacao, colunasPedidosPesonalizacao);

        // Criar o botão voltar
        JButton voltarButton3 = new JButton("Voltar");
        voltarButton3.addActionListener(e -> {
            // Fechar a janela atual
            frame.dispose();
            // Voltar para a janela anterior
            frameAnterior.setVisible(true);
        });

        // Adicionar a tabela e o botão ao painel
        panel3.add(new JScrollPane(table3), BorderLayout.CENTER);
        panel3.add(voltarButton3, BorderLayout.SOUTH);




        // Adicionar a aba ao painel de abas
        tabbedPane.addTab("Consulta Clientes", panel1);
        tabbedPane.addTab("Consulta Veiculos",panel2);
        tabbedPane.addTab("Consulta Pedidos Personalização",panel3);
        // Adicionar o painel de abas ao frame

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Obtém o índice da aba atualmente selecionada
                int selectedIndex = tabbedPane.getSelectedIndex();
                // Aqui você pode adicionar ação com base na aba selecionada
                if (selectedIndex == 0) {
                    frame.setTitle("Consulta Clientes");
                    frame.setSize(600, 400);
                } else if (selectedIndex == 1) {
                    frame.setTitle("Consulta Veiculos");
                    frame.setSize(600, 400);
                }else if(selectedIndex == 2){
                    frame.setTitle("Consulta Pedidos Personalização");
                    frame.setSize(600, 400);
                }
            }
        });


        frame.add(tabbedPane);

        // Exibir a janela
        frame.setVisible(true);
    }
    public static void menuConsultaMecanico(Frame frameAnterior) {

        // Cria uma janela Swing
        JFrame frame = new JFrame("Consulta");

        // Define o layout da janela como BorderLayout
        frame.setLayout(new BorderLayout());

        // Cria um painel para os botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(0, 2)); // GridLayout com 2 colunas

        // Cria os botões e labels
        JButton botao1 = new JButton("Clientes, Veículos e Pedidos");
        JLabel label1 = new JLabel("Consulta clientes, veiculos e Pedidos de personalização:");
        JButton botao2 = new JButton("Produtos, Fornecedores e Compras");
        JLabel label2 = new JLabel("Consulta produtos, fornecedores, pedidos compra e notificações:");
        JButton botaoVoltar = new JButton("Voltar");

        // Adiciona listeners de eventos aos botões
        botao1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                menuConsultaRClientes(frame);
            }
        });


        botao2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO:Terminar isso aqui
                frame.setVisible(false);
                menuConsultaRProdutos(frame);
            }
        });

        botaoVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO:Terminar isso aqui
                frame.dispose();
                frameAnterior.setVisible(true);
            }
        });

        // Adiciona os botões e labels ao painel de botões
        painelBotoes.add(label1);
        painelBotoes.add(botao1);
        painelBotoes.add(label2);
        painelBotoes.add(botao2);
        painelBotoes.add(botaoVoltar);

        // Adiciona o painel de botões ao centro da janela
        frame.add(painelBotoes, BorderLayout.CENTER);

        // Define o tamanho da janela
        frame.setSize(800, 200);

        // Define a operação padrão ao fechar a janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Centraliza a janela na tela
        frame.setLocationRelativeTo(null);

        // Define a janela como visível
        frame.setVisible(true);
    }

    public static void menuMecanico(Frame frameAnterior) {
        // Cria uma janela Swing
        JFrame frame = new JFrame("Menu Mecanico");

        // Define o layout da janela como BorderLayout
        frame.setLayout(new BorderLayout());

        // Cria um painel para os botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER)); // Define o layout do painel como FlowLayout e centraliza os componentes

        // Adiciona um espaço vazio acima dos botões
        painelBotoes.add(Box.createVerticalStrut(150)); // Adiciona um espaço vertical de 20 pixels

        // Cria os botões
        JButton botao1 = new JButton("Cadastrar");
        JButton botao2 = new JButton("Consultar");
        JButton botaoVoltar = new JButton("Voltar");

        // Adiciona listeners de eventos aos botões
        botao1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                menuCadastroMecanico(frame);
            }
        });
        botao2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                menuConsultaMecanico(frame);
            }
        });



        botaoVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                frameAnterior.setVisible(true);
            }
        });
        // Adiciona os botões ao painel de botões
        painelBotoes.add(botao1);
        painelBotoes.add(botao2);
        painelBotoes.add(botaoVoltar);

        // Adiciona o painel de botões ao centro da janela
        frame.add(painelBotoes, BorderLayout.CENTER);

        // Define o tamanho da janela
        frame.setSize(400, 200);

        // Define a operação padrão ao fechar a janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
