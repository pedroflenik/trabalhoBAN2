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
public class guiCompras {

    private static Sistema sistema;

    public guiCompras(Sistema sistema) {
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

    public static void menuConfirmarEntrega(Frame frameAnterior) {
        JFrame frame = new JFrame("Confirmar Entrega");

        JPanel formularioConfirmarCompra = new JPanel(new GridLayout(2, 2));
        formularioConfirmarCompra.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formularioConfirmarCompra.add(new JLabel("Pedido de compra:"));
        JComboBox<String> pedidoCompraBox = new JComboBox<>();
        List<PedidoCompra> pedidosCompra = new ArrayList<>();
        try {
            pedidosCompra = sistema.getPedidosCompra();
        } catch (Exception ex) {
            System.out.println("ERRO: No getPedidosCompra " + ex.getMessage());
        }
        if (pedidosCompra.size() != 0) {
            pedidoCompraBox.addItem("--- Escolher ---");
            for (PedidoCompra pc : pedidosCompra) {
                if(!pc.isEntregue()){
                    pedidoCompraBox.addItem(pc.getIdPedidoCompra() + " - Fornecedor: " + pc.getIdFornecedor() + " - Produto: " + pc.getIdProduto());
                }
            }
            pedidosCompra.clear();
        } else {
            pedidoCompraBox.addItem("Vazio");
        }
        formularioConfirmarCompra.add(pedidoCompraBox);

        JButton confirmarEntrega = new JButton("Confirmar entrega");
        confirmarEntrega.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textoPedidoBox = (String) pedidoCompraBox.getSelectedItem();
                if (textoPedidoBox.equals("--- Escolher ---")) {
                    JOptionPane.showMessageDialog(frame, "Por favor, escolha um pedido!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (textoPedidoBox.equals("Vazio")) {
                    JOptionPane.showMessageDialog(frame, "Não há pedidos cadastrados", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int idPedido = extrairId(textoPedidoBox);

                try {
                    sistema.confirmarEntrega(idPedido);
                    JOptionPane.showMessageDialog(frame, "Entrega confirmada ", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(frame, "Falha ao confirmar entrega", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        JPanel botoesConfirmarEntregaPanel = new JPanel();
        botoesConfirmarEntregaPanel.add(confirmarEntrega);

        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(e -> {
            // Fechar a janela atual
            frame.dispose();
            // Voltar para a janela anterior
            frameAnterior.setVisible(true);
        });

        botoesConfirmarEntregaPanel.add(voltarButton);
        formularioConfirmarCompra.add(botoesConfirmarEntregaPanel);
        frame.add(formularioConfirmarCompra, BorderLayout.CENTER);
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }


    //Consulta
    public static void menuConsultaRProdutos(Frame frameAnterior){
        JFrame frame = new JFrame("Consulta de Produtos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);

        // Criar uma aba
        JTabbedPane tabbedPane = new JTabbedPane();

        // Criar o painel para a primeira aba
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());

        // Criar as colunas
        String[] colunas = {"ID", "Nome", "Preço unitario", "Quantidade em estoque","Fornecedor principal","Tipo","Quantidade minima","Descricao"};

        List<Produto> produtos = new ArrayList<>();
        try{
            produtos = sistema.getProdutos();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        // Criar os dados
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
        String[] colunasPedidosPesonalizacao = {"ID", "dataEntrega", "descricao", "valor Personalização","id veiculo","departamento responsável"};

        List<PedidoPersonalizacao> pedidosPersonalizacao = new ArrayList<>();

        try {
            pedidosPersonalizacao = sistema.getPerdidosPersonalizacao();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        // Criar os dados
        Object[][] dadosPedidosPersonalizacao = new Object[pedidosPersonalizacao.size()][6];
        for (int i = 0; i < pedidosPersonalizacao.size(); i++) {
            PedidoPersonalizacao pedido = pedidosPersonalizacao.get(i);
            dadosPedidosPersonalizacao[i][0] = pedido.getIdPedido();
            dadosPedidosPersonalizacao[i][1] = pedido.getDataEntrega();
            dadosPedidosPersonalizacao[i][2] = pedido.getDescricao();
            dadosPedidosPersonalizacao[i][3] = pedido.getValorPersonalizacao();
            dadosPedidosPersonalizacao[i][4] = pedido.getIdVeiculo();
            dadosPedidosPersonalizacao[i][5] = pedido.getDepartamentoResponsavel();
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
    public static void menuConsultaCompras(Frame frameAnterior) {
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



    public static void menuCadastroRProdutos(Frame frameAnterior) {

        JFrame frame = new JFrame("Cadastro de Produto");

        JTabbedPane tabbedPane = new JTabbedPane();

        // Painel de cadastro de produto
        JPanel cadastroProduto = new JPanel(new BorderLayout());

        JPanel formularioProduto = new JPanel(new GridLayout(8, 2));
        formularioProduto.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Adiciona os campos do formulário de produto
        formularioProduto.add(new JLabel("Nome:"));
        JTextField nomeTextField = new JTextField();
        formularioProduto.add(nomeTextField);
        formularioProduto.add(new JLabel("Preco unitário:"));
        SpinnerModel spinnerModel1 = new SpinnerNumberModel(0.0, 0.0, null, 0.01); // Valor inicial, valor mínimo, valor máximo, passo
        JSpinner precoUnitarioSpinner = new JSpinner(spinnerModel1);
        formularioProduto.add(precoUnitarioSpinner);
        formularioProduto.add(new JLabel("Quantidade no estoque:"));
        SpinnerModel spinnerModel2 = new SpinnerNumberModel(0.0, 0.0, null, 1);
        JSpinner quantidadeEstoqueSpinner = new JSpinner(spinnerModel2);
        formularioProduto.add(quantidadeEstoqueSpinner);

        formularioProduto.add(new JLabel("Fornecedor principal:"));
        JComboBox<String> fornecedorComboBox = new JComboBox<>();
        List<Fornecedor> fornecedores = null;

        try {
            fornecedores = sistema.getFornecedores();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        if(fornecedores != null){
            fornecedorComboBox.addItem("--- Escolher ---");
            for (Fornecedor f : fornecedores){
                fornecedorComboBox.addItem(f.getIdFornecedor() + " - " + f.getNome());
            }
        }else{
            fornecedorComboBox.addItem("Vazio");
        }

        formularioProduto.add(fornecedorComboBox);


        formularioProduto.add(new JLabel("Tipo (especial ou comum):"));
        JComboBox<String> tipoComboBox = new JComboBox<>();
        tipoComboBox.addItem("--- Escolher ---");
        tipoComboBox.addItem("E");
        tipoComboBox.addItem("C");
        formularioProduto.add(tipoComboBox);
        formularioProduto.add(new JLabel("Quantidade Minima:"));
        SpinnerModel spinnerModel3 = new SpinnerNumberModel(0.0, 0.0, null, 1);
        JSpinner quantidadeMinSpiner = new JSpinner(spinnerModel3);
        formularioProduto.add(quantidadeMinSpiner);
        formularioProduto.add(new JLabel("Descricao:"));
        JTextField descricaoTxt = new JTextField();
        formularioProduto.add(descricaoTxt);




        JButton cadastrarProdutoButton = new JButton("Cadastrar Produto");
        cadastrarProdutoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textoTipoBox = (String) tipoComboBox.getSelectedItem();
                if(textoTipoBox == "--- Escolher ---"){
                    JOptionPane.showMessageDialog(frame, "Por favor, escolha um tipo!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                char tipo = textoTipoBox.charAt(0);
                String textoFornecedorBox = (String) fornecedorComboBox.getSelectedItem();
                if(textoFornecedorBox == "--- Escolher ---"){
                    JOptionPane.showMessageDialog(frame, "Por favor, escolha um fornecedor!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }else if(textoFornecedorBox == "Vazio"){
                    JOptionPane.showMessageDialog(frame, "Por favor, cadastre um fornecedor!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }



                String nome = nomeTextField.getText();
                double precoPorUnidade = ((Double) precoUnitarioSpinner.getValue()).doubleValue();
                double quantidadeEstoqueDouble = (double) quantidadeEstoqueSpinner.getValue();
                int quantidadeEstoque = (int) quantidadeEstoqueDouble;
                int fornecedorPrincipal = extrairId(textoFornecedorBox);
                double quantidadeMinimaDouble = (double) quantidadeEstoqueSpinner.getValue();
                int quantidadeMinima = (int) quantidadeMinimaDouble;
                String descricao = descricaoTxt.getText();


                if(nome.isEmpty() || descricao.isEmpty() || precoPorUnidade == 0){
                    JOptionPane.showMessageDialog(frame, "Por favor, preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Produto novoProduto = new Produto();

                novoProduto.setQuantidadeEstoque(quantidadeEstoque);
                novoProduto.setTipo(tipo);
                novoProduto.setFornecedorPrincipal(fornecedorPrincipal);
                novoProduto.setPrecoPorUnidade(precoPorUnidade);
                novoProduto.setQuantidadeMinima(quantidadeMinima);
                novoProduto.setNome(nome);
                novoProduto.setDescricao(descricao);

                try {
                    sistema.cadastraProduto(novoProduto);
                    JOptionPane.showMessageDialog(frame, "Produto cadastrado", "SUCESSO",JOptionPane.INFORMATION_MESSAGE);
                    return;
                }catch (Exception ex){
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(frame, "Falha ao cadastrar produto", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });


        JPanel botoesProdutoPanel = criarBotoesPanel(frameAnterior,frame,formularioProduto);

        cadastroProduto.add(cadastrarProdutoButton, BorderLayout.NORTH);
        cadastroProduto.add(formularioProduto, BorderLayout.CENTER);
        cadastroProduto.add(botoesProdutoPanel, BorderLayout.SOUTH);



        JPanel cadastroFornecedor = new JPanel(new BorderLayout());

        JPanel formularioFornecedor = new JPanel(new GridLayout(4, 2));
        formularioFornecedor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));



        formularioFornecedor.add(new JLabel("Nome: "));
        JTextField nomeFornetxt = new JTextField();
        formularioFornecedor.add(nomeFornetxt);
        formularioFornecedor.add(new JLabel("Telefone:"));
        JTextField telefoneTxt = new JTextField();
        formularioFornecedor.add(telefoneTxt);
        formularioFornecedor.add(new JLabel("Endereco:"));
        JTextField enderecoTxt = new JTextField();
        formularioFornecedor.add(enderecoTxt);




        JButton cadastrarFornecedor = new JButton("Cadastrar Fornecedor");
        cadastrarFornecedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String endereco = enderecoTxt.getText();
                String nome = nomeFornetxt.getText();
                String telefone = telefoneTxt.getText();

                Fornecedor novoFornecedor = new Fornecedor();
                novoFornecedor.setEndereco(endereco);
                novoFornecedor.setTelefone(telefone);
                novoFornecedor.setNome(nome);

                if(nome.isEmpty() || endereco.isEmpty() || telefone.isEmpty()){
                    JOptionPane.showMessageDialog(frame, "Preencha todos os campos corretamente", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    sistema.cadastrarFornecedor(novoFornecedor);
                    JOptionPane.showMessageDialog(frame, "Fornecedor cadastrado", "SUCESSO",JOptionPane.INFORMATION_MESSAGE);
                    return;
                }catch (Exception ex){
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(frame, "Falha ao cadastrar fornecedor", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }


            }
        });

        // Cria o painel de botões para o formulário de cliente
        JPanel botoesFornecedorPanel = criarBotoesPanel(frameAnterior,frame,formularioFornecedor);

        // Adiciona o botão cadastrar cliente e o formulário ao painel de cadastro de cliente
        cadastroFornecedor.add(cadastrarFornecedor, BorderLayout.NORTH);
        cadastroFornecedor.add(formularioFornecedor, BorderLayout.CENTER);
        cadastroFornecedor.add(botoesFornecedorPanel, BorderLayout.SOUTH);




        // Painel de cadastro de notificacao
        JPanel cadatroNotificacao = new JPanel(new BorderLayout());

        JPanel formularioNotificacao = new JPanel(new GridLayout(3, 2));
        formularioFornecedor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        // Adiciona os campos do formulário de notificacao
        formularioNotificacao.add(new JLabel("Produto:"));
        JComboBox<String> produtoComboBox = new JComboBox<>();

        List<Produto> produtos = null;
        try {
            produtos = sistema.getProdutos();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        if(produtos != null){
            produtoComboBox.addItem("--- Escolher ---");
            for (Produto p : produtos){
                produtoComboBox.addItem(p.getIdProduto() + " - " + p.getNome() + " - Tipo:" + p.getTipo());
            }
        }else{
            produtoComboBox.addItem("Vazio");
        }
        formularioNotificacao.add(produtoComboBox);
        formularioNotificacao.add(new JLabel("Quantidade:"));
        SpinnerModel spinnerModel4 = new SpinnerNumberModel(0.0, 0.0, null, 1);
        JSpinner quantidadeProdutoNotificacaoSpinner = new JSpinner(spinnerModel3);
        formularioNotificacao.add(quantidadeProdutoNotificacaoSpinner);
        formularioNotificacao.add(new JLabel("Data limite (yyyy-MM-dd):"));
        JTextField dataLimiteTxt = new JTextField();
        formularioNotificacao.add(dataLimiteTxt);



        JButton cadastrarNotificacao = new JButton("Cadastrar Notificação");
        cadastrarNotificacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String textoProdutoBox = (String) produtoComboBox.getSelectedItem();
                if(textoProdutoBox == "Vazio"){
                    JOptionPane.showMessageDialog(frame, "Cadastre um produto", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }else{
                    if(textoProdutoBox == "--- Escolher ---"){
                        JOptionPane.showMessageDialog(frame, "Por favor, escolha um produto", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                int idProduto = extrairId(textoProdutoBox);
                double quantidadeDouble = (double) quantidadeProdutoNotificacaoSpinner.getValue();
                int quantidade =(int) quantidadeDouble;
                String dataLimiteString = dataLimiteTxt.getText();



                if(dataLimiteString.isEmpty() || quantidade == 0){
                    JOptionPane.showMessageDialog(frame, "Por favor, preencha corretamento todos os campos", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                LocalDate dataLimite;

                try {
                    dataLimite = LocalDate.parse(dataLimiteString);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Formato de data inválido! Use o formato yyyy-MM-dd.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return; // Sai do método se a data estiver em um formato inválido
                }

                Notificacao novaNotificacao = new Notificacao();

                novaNotificacao.setIdProduto(idProduto);
                novaNotificacao.setQuantidade(quantidade);
                novaNotificacao.setDataLimite(dataLimite);

                try {
                    sistema.cadastrarNotificacao(novaNotificacao);
                    JOptionPane.showMessageDialog(frame, "Notificação cadastrada", "SUCESSO",JOptionPane.INFORMATION_MESSAGE);
                    return;
                }catch (Exception ex){
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(frame, "Falha ao cadastrar notificação", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        // Cria o painel de botões para o formulário de cliente
        JPanel botoesNotificacao = criarBotoesPanel(frameAnterior,frame,formularioNotificacao);

        // Adiciona o botão cadastrar cliente e o formulário ao painel de cadastro de cliente
        cadatroNotificacao.add(cadastrarNotificacao, BorderLayout.NORTH);
        cadatroNotificacao.add(formularioNotificacao, BorderLayout.CENTER);
        cadatroNotificacao.add(botoesNotificacao, BorderLayout.SOUTH);



        // Painel de cadastro de pedidoCompra
        JPanel cadastroPedidoCompra = new JPanel(new BorderLayout());

        JPanel formularioPedidoCompra = new JPanel(new GridLayout(2, 2));
        formularioPedidoCompra.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));



        // Adiciona os campos do formulário de pediddoCompra
        formularioPedidoCompra.add(new JLabel("Notificacao:"));
        JComboBox<String> notificacoesBox = new JComboBox<>();

        List<Notificacao> notificacoes = null;
        try {
            notificacoes = sistema.getNotificacoes();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        if(notificacoes != null){
            notificacoesBox.addItem("--- Escolher ---");
            for (Notificacao n : notificacoes){
                notificacoesBox.addItem(n.getIdNotificao() + " - " + "Produto: " + n.getIdProduto() + " - Data Limite:" + n.getDataLimite() + " Quantidade: " + n.getQuantidade());
            }
        }else{
            notificacoesBox.addItem("Vazio");
        }

        formularioPedidoCompra.add(notificacoesBox);

        formularioPedidoCompra.add(new JLabel("Fornecedor: "));
        JComboBox<String> fornecedorBox = new JComboBox<>();
        fornecedores = null;
        try {
            fornecedores = sistema.getFornecedores();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        if(fornecedores != null){
            fornecedorBox.addItem("--- Escolher ---");
            for (Fornecedor f : fornecedores){
                fornecedorBox.addItem(f.getIdFornecedor() + " - " + f.getNome());
            }
        }else{
            fornecedorBox.addItem("Vazio");
        }

        formularioPedidoCompra.add(fornecedorBox);

        JButton cadastrarPedidoCompra = new JButton("Cadastrar Pedido de Compra");
        cadastrarPedidoCompra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String textoFornecedorBox = (String) fornecedorBox.getSelectedItem();
                String textoNotificacaoBox = (String) notificacoesBox.getSelectedItem();
                if(textoNotificacaoBox == "Vazio"){
                    JOptionPane.showMessageDialog(frame, "Cadastre um notificação", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }else if(textoNotificacaoBox == "--- Escolher ---"){
                    JOptionPane.showMessageDialog(frame, "Escolha uma notificação", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(textoFornecedorBox == "Vazio"){
                    JOptionPane.showMessageDialog(frame, "Cadastre um fornecedor", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }else if(textoFornecedorBox == "--- Escolher ---"){
                    JOptionPane.showMessageDialog(frame, "Escolha um fornecedor", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                int idFornecedor  =  extrairId(textoFornecedorBox);
                int idNotificacao =  extrairId(textoNotificacaoBox);
                int idProduto = extrairSegundoCampo(textoNotificacaoBox);
                System.out.println("1 - " + idFornecedor);
                System.out.println("2 - " + idNotificacao);
                System.out.println("3 - " + idProduto);
                PedidoCompra novoPedidoCompra = new PedidoCompra();
                novoPedidoCompra.setEntregue(false);
                novoPedidoCompra.setIdProduto(idProduto);
                novoPedidoCompra.setIdFornecedor(idFornecedor);
                novoPedidoCompra.setIdNotificacao(idNotificacao);

                //TODO: Usar banco para calcular toda Compra

                try {
                    sistema.cadastrarPedidoCompra(novoPedidoCompra);
                    JOptionPane.showMessageDialog(frame, "Pedido de Compra cadastrado", "SUCESSO",JOptionPane.INFORMATION_MESSAGE);
                    return;
                }catch (Exception ex){
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(frame, "Falha ao cadastrar Pedido de Compra", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }


            }
        });

        // Cria o painel de botões para o formulário de cliente
        JPanel botoesPedidoCompraPanel = criarBotoesPanel(frameAnterior,frame,formularioFornecedor);

        // Adiciona o botão cadastrar cliente e o formulário ao painel de cadastro de cliente
        cadastroPedidoCompra.add(cadastrarPedidoCompra, BorderLayout.NORTH);
        cadastroPedidoCompra.add(formularioPedidoCompra, BorderLayout.CENTER);
        cadastroPedidoCompra.add(botoesPedidoCompraPanel, BorderLayout.SOUTH);





        tabbedPane.addTab("Cadastro de Produto", cadastroProduto);
        tabbedPane.addTab("Cadastro de Fornecedor", cadastroFornecedor);
        tabbedPane.addTab("Cadastro de Notificação", cadatroNotificacao);
        tabbedPane.addTab("Cadastro de Pedido de Compra", cadastroPedidoCompra);


        // Adiciona um ChangeListener para o JTabbedPane
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Obtém o índice da aba atualmente selecionada
                int selectedIndex = tabbedPane.getSelectedIndex();
                // Aqui você pode adicionar ação com base na aba selecionada
                if (selectedIndex == 0) {
                    frame.setTitle("Cadastro de Produto");
                    frame.setSize(700, 400);
                } else if (selectedIndex == 1) {
                    frame.setTitle("Cadastro de Fornecedor");
                    frame.setSize(700, 250);
                }else if(selectedIndex == 2){
                    frame.setTitle("Cadastro de Notificação");
                    frame.setSize(700, 250);
                }else if(selectedIndex == 3){
                    frame.setTitle("Cadastro de Pedido de Compra");
                    frame.setSize(700, 220);
                }
            }
        });

        // Adiciona o JTabbedPane ao JFrame
        frame.add(tabbedPane);
        frame.setSize(700, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }



    public static void menuCompras(Frame frameAnterior) {
        // Cria uma janela Swing
        JFrame frame = new JFrame("Menu Compras");

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
        JButton botao3 = new JButton("Confirmar entrega");
        JButton botaoVoltar = new JButton("Voltar");

        // Adiciona listeners de eventos aos botões
        botao1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                menuCadastroRProdutos(frame);
            }
        });
        botao2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                menuConsultaCompras(frame);
            }
        });

        botao3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                menuConfirmarEntrega(frame);
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
        painelBotoes.add(botao3);
        painelBotoes.add(botaoVoltar);

        // Adiciona o painel de botões ao centro da janela
        frame.add(painelBotoes, BorderLayout.CENTER);

        // Define o tamanho da janela
        frame.setSize(500, 200);

        // Define a operação padrão ao fechar a janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
