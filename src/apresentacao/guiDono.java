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

public class guiDono {

    private static Sistema sistema;
    //TODO:Ajustar combo Box


    public guiDono(Sistema sistema) {
        this.sistema = sistema;
    }

    public static int extrairId(String textoCompleto) {
        // Divide o texto completo usando espaços em branco como delimitadores
        //i hate regex
        String[] partes = textoCompleto.split("\\s+");

        // A primeira parte deve conter o ID
        String idTexto = partes[0].trim();

        // Converte o ID para um valor inteiro e retorna
        return Integer.parseInt(idTexto);
    }

    public static int extrairSegundoCampo(String textoCompleto) {

        //i hate regex
        // Divide o texto completo usando espaços em branco como delimitadores
        String[] partes = textoCompleto.split("\\s+");

        // A primeira parte deve conter o ID
        String idTexto = partes[3].trim();

        // Converte o ID para um valor inteiro e retorna
        return Integer.parseInt(idTexto);
    }


    //Deletar
    public static void menuDeletaRProdutos(Frame frameAnterior) {
        JFrame frame = new JFrame("Deletar produto");

        JTabbedPane tabbedPane = new JTabbedPane();

// Painel de exclusão de produto
        JPanel deletarProdutoPanel = new JPanel(new BorderLayout());

        JPanel formularioDeletarProduto = new JPanel(new GridLayout(1, 2));
        formularioDeletarProduto.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formularioDeletarProduto.add(new JLabel("Produto:"));
        JComboBox<String> produtoBox = new JComboBox<>();
        List<Produto> produtos = new ArrayList<>();
        try {
            produtos = sistema.getProdutos();
        } catch (Exception ex) {
            System.out.println("ERRO: No getProdutos " + ex.getMessage());
        }
        if (produtos.size() != 0) {
            produtoBox.addItem("--- Escolher ---");
            for (Produto p : produtos) {
                produtoBox.addItem(p.getIdProduto() + " - Nome: " + p.getNome());
            }
            produtos.clear();
        } else {
            produtoBox.addItem("Vazio");
        }
        formularioDeletarProduto.add(produtoBox);

        JButton deletarProduto = new JButton("Deletar Produto");
        deletarProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textoProdutoBox = (String) produtoBox.getSelectedItem();
                if (textoProdutoBox.equals("--- Escolher ---")) {
                    JOptionPane.showMessageDialog(frame, "Por favor, escolha um produto!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (textoProdutoBox.equals("Vazio")) {
                    JOptionPane.showMessageDialog(frame, "Não há produtos cadastrados", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int idProduto = extrairId(textoProdutoBox);

                try {
                    sistema.deletarProduto(idProduto);
                    JOptionPane.showMessageDialog(frame, "Produto deletado", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(frame, "Falha ao deletar produto", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        JPanel botoesDelProdutoPanel = criarBotoesPanel(frameAnterior, frame, formularioDeletarProduto);

        deletarProdutoPanel.add(deletarProduto, BorderLayout.NORTH);
        deletarProdutoPanel.add(formularioDeletarProduto, BorderLayout.CENTER);
        deletarProdutoPanel.add(botoesDelProdutoPanel, BorderLayout.SOUTH);


        JPanel deletarFornecedorPanel = new JPanel(new BorderLayout());

        JPanel formularioDeletarFornecedor = new JPanel(new GridLayout(1, 2));
        formularioDeletarFornecedor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formularioDeletarFornecedor.add(new JLabel("Fornecedor:"));
        JComboBox<String> fornecedorBox = new JComboBox<>();
        List<Fornecedor> fornecedores = new ArrayList<>();
        try {
            fornecedores = sistema.getFornecedores();
        } catch (Exception ex) {
            System.out.println("ERRO: No getFornecedores " + ex.getMessage());
        }
        if (fornecedores.size() != 0) {
            fornecedorBox.addItem("--- Escolher ---");
            for (Fornecedor f : fornecedores) {
                fornecedorBox.addItem(f.getIdFornecedor() + " - Nome: " + f.getNome());
            }
            fornecedores.clear();
        } else {
            fornecedorBox.addItem("Vazio");
        }
        formularioDeletarFornecedor.add(fornecedorBox);

        JButton deletarFornecedor = new JButton("Deletar Fornecedor");
        deletarFornecedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textoFornecedorBox = (String) fornecedorBox.getSelectedItem();
                if (textoFornecedorBox.equals("--- Escolher ---")) {
                    JOptionPane.showMessageDialog(frame, "Por favor, escolha um fornecedor!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (textoFornecedorBox.equals("Vazio")) {
                    JOptionPane.showMessageDialog(frame, "Não há fornecedores cadastrados", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int idFornecedor = extrairId(textoFornecedorBox);

                try {
                    sistema.deletarFornecedor(idFornecedor);
                    JOptionPane.showMessageDialog(frame, "Fornecedor deletado", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(frame, "Falha ao deletar fornecedor", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        JPanel botoesDelFornecedorPanel = criarBotoesPanel(frameAnterior, frame, formularioDeletarFornecedor);

        deletarFornecedorPanel.add(deletarFornecedor, BorderLayout.NORTH);
        deletarFornecedorPanel.add(formularioDeletarFornecedor, BorderLayout.CENTER);
        deletarFornecedorPanel.add(botoesDelFornecedorPanel, BorderLayout.SOUTH);


        JPanel deletarNotificacaoPanel = new JPanel(new BorderLayout());

        JPanel formularioDeletarNotificacao = new JPanel(new GridLayout(1, 2));
        formularioDeletarNotificacao.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formularioDeletarNotificacao.add(new JLabel("Notificação:"));
        JComboBox<String> notificacaoBox = new JComboBox<>();
        List<Notificacao> notificacoes = new ArrayList<>();
        try {
            notificacoes = sistema.getNotificacoes();
        } catch (Exception ex) {
            System.out.println("ERRO: No getNotificacoes " + ex.getMessage());
        }
        if (notificacoes.size() != 0) {
            notificacaoBox.addItem("--- Escolher ---");
            for (Notificacao n : notificacoes) {
                notificacaoBox.addItem(n.getIdNotificao() + " - Produto: " + n.getIdProduto() + " - Data Limite: " + n.getDataLimite());
            }
            notificacoes.clear();
        } else {
            notificacaoBox.addItem("Vazio");
        }
        formularioDeletarNotificacao.add(notificacaoBox);

        JButton deletarNotificacao = new JButton("Deletar Notificação");
        deletarNotificacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textoNotificacaoBox = (String) notificacaoBox.getSelectedItem();
                if (textoNotificacaoBox.equals("--- Escolher ---")) {
                    JOptionPane.showMessageDialog(frame, "Por favor, escolha uma notificação!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (textoNotificacaoBox.equals("Vazio")) {
                    JOptionPane.showMessageDialog(frame, "Não há notificações cadastradas", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int idNotificacao = extrairId(textoNotificacaoBox);

                try {
                    sistema.deletarNotificacao(idNotificacao);
                    JOptionPane.showMessageDialog(frame, "Notificação deletada", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(frame, "Falha ao deletar notificação", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        JPanel botoesDelNotificacaoPanel = criarBotoesPanel(frameAnterior, frame, formularioDeletarNotificacao);

        deletarNotificacaoPanel.add(deletarNotificacao, BorderLayout.NORTH);
        deletarNotificacaoPanel.add(formularioDeletarNotificacao, BorderLayout.CENTER);
        deletarNotificacaoPanel.add(botoesDelNotificacaoPanel, BorderLayout.SOUTH);

        JPanel deletarPedidoCompraPanel = new JPanel(new BorderLayout());

        JPanel formularioDeletarPedidoCompra = new JPanel(new GridLayout(1, 2));
        formularioDeletarPedidoCompra.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formularioDeletarPedidoCompra.add(new JLabel("Pedido de Compra:"));
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
                pedidoCompraBox.addItem(pc.getIdPedidoCompra() + " - Fornecedor: " + pc.getIdFornecedor() + " - Produto: " + pc.getIdProduto());
            }
            pedidosCompra.clear();
        } else {
            pedidoCompraBox.addItem("Vazio");
        }
        formularioDeletarPedidoCompra.add(pedidoCompraBox);

        JButton deletarPedidoCompra = new JButton("Deletar Pedido de Compra");
        deletarPedidoCompra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textoPedidoCompraBox = (String) pedidoCompraBox.getSelectedItem();
                if (textoPedidoCompraBox.equals("--- Escolher ---")) {
                    JOptionPane.showMessageDialog(frame, "Por favor, escolha um pedido de compra!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (textoPedidoCompraBox.equals("Vazio")) {
                    JOptionPane.showMessageDialog(frame, "Não há pedidos de compra cadastrados", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int idPedidoCompra = extrairId(textoPedidoCompraBox);

                try {
                    sistema.deletarPedidoCompra(idPedidoCompra);
                    JOptionPane.showMessageDialog(frame, "Pedido de Compra deletado", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(frame, "Falha ao deletar pedido de compra", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        JPanel botoesDelPedidoCompraPanel = criarBotoesPanel(frameAnterior, frame, formularioDeletarPedidoCompra);

        deletarPedidoCompraPanel.add(deletarPedidoCompra, BorderLayout.NORTH);
        deletarPedidoCompraPanel.add(formularioDeletarPedidoCompra, BorderLayout.CENTER);
        deletarPedidoCompraPanel.add(botoesDelPedidoCompraPanel, BorderLayout.SOUTH);


        tabbedPane.addTab("Deletar Produto", deletarProdutoPanel);
        tabbedPane.addTab("Deletar Fornecedor", deletarFornecedorPanel);
        tabbedPane.addTab("Deletar Notificação", deletarNotificacaoPanel);
        tabbedPane.addTab("Deletar Pedido de Compra", deletarPedidoCompraPanel);


        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                int selectedIndex = tabbedPane.getSelectedIndex();
                if (selectedIndex == 0) {
                    frame.setTitle("Deletar Produto");
                    frame.setSize(600, 180);
                } else if (selectedIndex == 1) {
                    frame.setTitle("Deletar Fornecedor");
                    frame.setSize(600, 180);
                } else if (selectedIndex == 2) {
                    frame.setTitle("Deletar Notificação");
                    frame.setSize(600, 180);
                } else if (selectedIndex == 3) {
                    frame.setTitle("Deletar Notificação");
                    frame.setSize(600, 180);
                }
            }
        });


        frame.add(tabbedPane);


        frame.setSize(600, 180);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void menuDeletarRClientes(Frame frameAnterior) {
        JFrame frame = new JFrame("Deletar Cliente");

        JTabbedPane tabbedPane = new JTabbedPane();


        JPanel deletarClientePanel = new JPanel(new BorderLayout());

        JPanel formularioDeletarCleinte = new JPanel(new GridLayout(1, 2));
        formularioDeletarCleinte.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formularioDeletarCleinte.add(new JLabel("Cliente:"));
        JComboBox<String> clienteBox = new JComboBox<>();

        List<Cliente> clientes = new ArrayList<>();
        try {
            clientes = sistema.getClientes();
        } catch (Exception ex) {
            System.out.println("ERRO: No getClientes " + ex.getMessage());
        }
        if (clientes.size() != 0) {
            clienteBox.addItem("--- Escolher ---");
            for (Cliente c : clientes) {
                clienteBox.addItem(c.getIdCliente() + " - Nome: " + c.getNome() + " - CPF: " + c.getCpf());
            }
            clientes.clear();
        } else {
            clienteBox.addItem("Vazio");
        }
        formularioDeletarCleinte.add(clienteBox);


        JButton deletarCliente = new JButton("Deletar Cliente");
        deletarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String textoClienteBox = (String) clienteBox.getSelectedItem();
                if (textoClienteBox == "--- Escolher ---") {
                    JOptionPane.showMessageDialog(frame, "Por favor, escolha um tipo!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (textoClienteBox == "Vazio") {
                    JOptionPane.showMessageDialog(frame, "Cadastre um cliete", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int idCliente = extrairId(textoClienteBox);


                try {
                    sistema.deletarCliente(idCliente);
                    JOptionPane.showMessageDialog(frame, "Cliente deletado", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(frame, "Falha ao deletar cliente", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });


        JPanel botoesDelClientePanel = criarBotoesPanel(frameAnterior, frame, formularioDeletarCleinte);

        deletarClientePanel.add(deletarCliente, BorderLayout.NORTH);
        deletarClientePanel.add(formularioDeletarCleinte, BorderLayout.CENTER);
        deletarClientePanel.add(botoesDelClientePanel, BorderLayout.SOUTH);



        JPanel deletarVeiculoPanel = new JPanel(new BorderLayout());

        JPanel formularioDeletarVeiculo = new JPanel(new GridLayout(1, 2));
        formularioDeletarVeiculo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formularioDeletarVeiculo.add(new JLabel("Veículo:"));
        JComboBox<String> veiculoBox = new JComboBox<>();

        List<Veiculo> veiculos = new ArrayList<>();
        try {
            veiculos = sistema.getVeiculos();
        } catch (Exception ex) {
            System.out.println("ERRO: No getVeiculos " + ex.getMessage());
        }
        if (veiculos.size() != 0) {
            veiculoBox.addItem("--- Escolher ---");
            for (Veiculo v : veiculos) {
                veiculoBox.addItem(v.getIdVeiculo() + " - Modelo: " + v.getModelo() + " - Marca: " + v.getMarca());
            }
            veiculos.clear();
        } else {
            veiculoBox.addItem("Vazio");
        }
        formularioDeletarVeiculo.add(veiculoBox);

        JButton deletarVeiculo = new JButton("Deletar Veículo");
        deletarVeiculo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String textoVeiculoBox = (String) veiculoBox.getSelectedItem();
                if (textoVeiculoBox.equals("--- Escolher ---")) {
                    JOptionPane.showMessageDialog(frame, "Por favor, escolha um veículo!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (textoVeiculoBox.equals("Vazio")) {
                    JOptionPane.showMessageDialog(frame, "Não há veículos cadastrados", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int idVeiculo = extrairId(textoVeiculoBox);

                try {
                    sistema.deletarVeiculo(idVeiculo);
                    JOptionPane.showMessageDialog(frame, "Veículo deletado", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(frame, "Falha ao deletar veículo", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        JPanel botoesDelVeiculoPanel = criarBotoesPanel(frameAnterior, frame, formularioDeletarVeiculo);

        deletarVeiculoPanel.add(deletarVeiculo, BorderLayout.NORTH);
        deletarVeiculoPanel.add(formularioDeletarVeiculo, BorderLayout.CENTER);
        deletarVeiculoPanel.add(botoesDelVeiculoPanel, BorderLayout.SOUTH);


        // Painel de exclusão de pedido de personalização
        JPanel deletarPedidoPanel = new JPanel(new BorderLayout());

        JPanel formularioDeletarPedido = new JPanel(new GridLayout(1, 2));
        formularioDeletarPedido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formularioDeletarPedido.add(new JLabel("Pedido de Personalização:"));
        JComboBox<String> pedidoBox = new JComboBox<>();

        List<PedidoPersonalizacao> pedidos = new ArrayList<>();
        try {
            pedidos = sistema.getPerdidosPersonalizacao();
        } catch (Exception ex) {
            System.out.println("ERRO: No getPedidosPersonalizacao " + ex.getMessage());
        }
        if (pedidos.size() != 0) {
            pedidoBox.addItem("--- Escolher ---");
            for (PedidoPersonalizacao p : pedidos) {
                pedidoBox.addItem(p.getIdPedido() + " - Descrição: " + p.getDescricao());
            }
            pedidos.clear();
        } else {
            pedidoBox.addItem("Vazio");
        }
        formularioDeletarPedido.add(pedidoBox);

        JButton deletarPedido = new JButton("Deletar Pedido de Personalização");
        deletarPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Recupera os dados do formulário
                String textoPedidoBox = (String) pedidoBox.getSelectedItem();
                if (textoPedidoBox.equals("--- Escolher ---")) {
                    JOptionPane.showMessageDialog(frame, "Por favor, escolha um pedido de personalização!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (textoPedidoBox.equals("Vazio")) {
                    JOptionPane.showMessageDialog(frame, "Não há pedidos de personalização cadastrados", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int idPedido = extrairId(textoPedidoBox);

                try {
                    sistema.deletarPedidoPersonalizacao(idPedido);
                    JOptionPane.showMessageDialog(frame, "Pedido de Personalização deletado", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(frame, "Falha ao deletar pedido de personalização", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        JPanel botoesDelPedidoPanel = criarBotoesPanel(frameAnterior, frame, formularioDeletarPedido);

        deletarPedidoPanel.add(deletarPedido, BorderLayout.NORTH);
        deletarPedidoPanel.add(formularioDeletarPedido, BorderLayout.CENTER);
        deletarPedidoPanel.add(botoesDelPedidoPanel, BorderLayout.SOUTH);

        //
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                int selectedIndex = tabbedPane.getSelectedIndex();
                if (selectedIndex == 0) {
                    frame.setTitle("Deletar de Cliente");
                    frame.setSize(600, 180);
                } else if (selectedIndex == 1) {
                    frame.setTitle("Deletar de Veículo");
                    frame.setSize(600, 180);
                } else if (selectedIndex == 2) {
                    frame.setTitle("Deletar Pedido de Personalização");
                    frame.setSize(600, 180);
                }
            }
        });


        tabbedPane.addTab("Deletar Cliente", deletarClientePanel);
        tabbedPane.addTab("Deletar de Veículo", deletarVeiculoPanel);
        tabbedPane.addTab("Deletar Pedido de Personalização", deletarPedidoPanel);

        frame.add(tabbedPane);
        frame.setSize(600, 180);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void menuDeletaRFuncionarios(Frame frameAnterior) {
        JFrame frame = new JFrame("Deletar Funcionario");

        JTabbedPane tabbedPane = new JTabbedPane();


        JPanel deletarFuncionarioPanel = new JPanel(new BorderLayout());

        JPanel formularioDeletarFuncionario = new JPanel(new GridLayout(1, 2));
        formularioDeletarFuncionario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formularioDeletarFuncionario.add(new JLabel("Funcionario:"));
        JComboBox<String> funcionarioBox = new JComboBox<>();

        List<Funcionario> funcionarios = new ArrayList<>();
        try {
            funcionarios = sistema.getFuncionarios();
        } catch (Exception ex) {
            System.out.println("ERRO: No getFubcinario " + ex.getMessage());
        }
        if (funcionarios.size() != 0) {
            funcionarioBox.addItem("--- Escolher ---");
            for (Funcionario f : funcionarios) {
                funcionarioBox.addItem(f.getIdFuncionario() + " - Nome: " + f.getNome() + " - CPF: " + f.getCpf());
            }
            funcionarios.clear();
        } else {
            funcionarioBox.addItem("Vazio");
        }
        formularioDeletarFuncionario.add(funcionarioBox);


        JButton deletarFuncionario = new JButton("Deletar Funcionario");
        deletarFuncionario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Recupera os dados do formulário
                String textoFubcionarioBox = (String) funcionarioBox.getSelectedItem();
                if (textoFubcionarioBox == "--- Escolher ---") {
                    JOptionPane.showMessageDialog(frame, "Por favor, escolha um tipo!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (textoFubcionarioBox == "Vazio") {
                    JOptionPane.showMessageDialog(frame, "Cadastre um cliete", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int idFuncionario = extrairId(textoFubcionarioBox);


                try {
                    sistema.deletarFuncionario(idFuncionario);
                    JOptionPane.showMessageDialog(frame, "Funcionario deletado", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(frame, "Falha ao deletar funcionario", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });


        JPanel botoesDelClientePanel = criarBotoesPanel(frameAnterior, frame, formularioDeletarFuncionario);

        deletarFuncionarioPanel.add(deletarFuncionario, BorderLayout.NORTH);
        deletarFuncionarioPanel.add(formularioDeletarFuncionario, BorderLayout.CENTER);
        deletarFuncionarioPanel.add(botoesDelClientePanel, BorderLayout.SOUTH);



        JPanel deletarDepartamentoPanel = new JPanel(new BorderLayout());

        JPanel formularioDeletarDepartamento = new JPanel(new GridLayout(1, 2));
        formularioDeletarDepartamento.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formularioDeletarDepartamento.add(new JLabel("Departamento:"));
        JComboBox<String> departamentoBox = new JComboBox<>();

        List<Departamento> departamentos = new ArrayList<>();
        try {
            departamentos = sistema.getDepartamentos();
        } catch (Exception ex) {
            System.out.println("ERRO: No getVeiculos " + ex.getMessage());
        }
        if (departamentos.size() != 0) {
            departamentoBox.addItem("--- Escolher ---");
            for (Departamento d : departamentos) {
                if (d.getIdDep() != 1 && d.getIdDep() != 2) {
                    departamentoBox.addItem(d.getIdDep() + " - Nome: " + d.getNome() + " - Tipo Veiculo: " + d.getTipoVeiculo());
                }
            }
            departamentos.clear();
        } else {
            departamentoBox.addItem("Vazio");
        }
        formularioDeletarDepartamento.add(departamentoBox);

        JButton deletarDepartamento = new JButton("Deletar Departamento");
        deletarDepartamento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Recupera os dados do formulário
                String textoDepartametoBox = (String) departamentoBox.getSelectedItem();
                if (textoDepartametoBox.equals("--- Escolher ---")) {
                    JOptionPane.showMessageDialog(frame, "Por favor, escolha um departamento!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (textoDepartametoBox.equals("Vazio")) {
                    JOptionPane.showMessageDialog(frame, "Não há veículos departamentos", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int idDepartamento = extrairId(textoDepartametoBox);

                try {
                    sistema.deletarDepartamento(idDepartamento);
                    JOptionPane.showMessageDialog(frame, "Departamentio deletado", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(frame, "Falha ao deletar departamentio", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        JPanel botoesDelDepartamentoPanel = criarBotoesPanel(frameAnterior, frame, formularioDeletarDepartamento);

        deletarDepartamentoPanel.add(deletarDepartamento, BorderLayout.NORTH);
        deletarDepartamentoPanel.add(formularioDeletarDepartamento, BorderLayout.CENTER);
        deletarDepartamentoPanel.add(botoesDelDepartamentoPanel, BorderLayout.SOUTH);


        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                int selectedIndex = tabbedPane.getSelectedIndex();
                if (selectedIndex == 0) {
                    frame.setTitle("Deletar de Funcionario");
                    frame.setSize(600, 180);
                } else if (selectedIndex == 1) {
                    frame.setTitle("Deletar de Departamento");
                    frame.setSize(600, 180);
                }
            }
        });


        tabbedPane.addTab("Deletar Funcionario", deletarFuncionarioPanel);
        tabbedPane.addTab("Deletar de Departamento", deletarDepartamentoPanel);
        frame.add(tabbedPane);
        frame.setSize(600, 180);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    //Consulta
    public static void menuConsultaRProdutos(Frame frameAnterior) {
        JFrame frame = new JFrame("Consulta de Produtos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);


        JTabbedPane tabbedPane = new JTabbedPane();


        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());


        String[] colunas = {"ID", "Nome", "Preço unitario", "Quantidade em estoque", "Fornecedor principal", "Tipo", "Quantidade minima", "Descricao"};

        List<Produto> produtos = new ArrayList<>();
        try {
            produtos = sistema.getProdutos();
        } catch (Exception e) {
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


        JTable table = new JTable(dados, colunas);


        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(e -> {
            frame.dispose();

            frameAnterior.setVisible(true);
        });


        panel1.add(new JScrollPane(table), BorderLayout.CENTER);
        panel1.add(voltarButton, BorderLayout.SOUTH);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());


        String[] colunasFornecedores = {"ID", "Nome", "Endereço", "telefone"};

        List<Fornecedor> fornecedores = new ArrayList<>();
        try {
            fornecedores = sistema.getFornecedores();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Object[][] dadosFornecedores = new Object[fornecedores.size()][colunasFornecedores.length];
        for (int i = 0; i < fornecedores.size(); i++) {
            Fornecedor fornecedor = fornecedores.get(i);
            dadosFornecedores[i][0] = fornecedor.getIdFornecedor();
            dadosFornecedores[i][1] = fornecedor.getNome();
            dadosFornecedores[i][2] = fornecedor.getEndereco();
            dadosFornecedores[i][3] = fornecedor.getTelefone();
        }


        JTable table2 = new JTable(dadosFornecedores, colunasFornecedores);


        JButton voltarButton2 = new JButton("Voltar");
        voltarButton2.addActionListener(e -> {
            frame.dispose();

            frameAnterior.setVisible(true);
        });


        panel2.add(new JScrollPane(table2), BorderLayout.CENTER);
        panel2.add(voltarButton2, BorderLayout.SOUTH);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout());


        String[] colunasNotificacoes = {"ID", "Id produto", "Quantidade", "data limite"};

        List<Notificacao> notificacoes = new ArrayList<>();
        try {
            notificacoes = sistema.getNotificacoes();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Object[][] dadosNotificacoes = new Object[notificacoes.size()][colunasNotificacoes.length];
        for (int i = 0; i < notificacoes.size(); i++) {
            Notificacao notificacao = notificacoes.get(i);
            dadosNotificacoes[i][0] = notificacao.getIdNotificao();
            dadosNotificacoes[i][1] = notificacao.getIdProduto();
            dadosNotificacoes[i][2] = notificacao.getQuantidade();
            dadosNotificacoes[i][3] = notificacao.getDataLimite();
        }


        JTable table3 = new JTable(dadosNotificacoes, colunasNotificacoes);


        JButton voltarButton3 = new JButton("Voltar");
        voltarButton3.addActionListener(e -> {
            frame.dispose();
            frameAnterior.setVisible(true);
        });


        panel3.add(new JScrollPane(table3), BorderLayout.CENTER);
        panel3.add(voltarButton3, BorderLayout.SOUTH);

        JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout());


        String[] colunasPedidosCompra = {"ID", "Id fornecedor", "Id notificao", "Quantidade", "total Compra", "id produto", "entregue"};

        List<PedidoCompra> pedidosCompra = new ArrayList<>();
        try {
            pedidosCompra = sistema.getPedidosCompra();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

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


        JTable table4 = new JTable(dadosPedidoCompra, colunasPedidosCompra);


        JButton voltarButton4 = new JButton("Voltar");
        voltarButton4.addActionListener(e -> {

            frame.dispose();

            frameAnterior.setVisible(true);
        });


        panel4.add(new JScrollPane(table4), BorderLayout.CENTER);
        panel4.add(voltarButton4, BorderLayout.SOUTH);



        tabbedPane.addTab("Consulta Produtos", panel1);
        tabbedPane.addTab("Consulta Fornecedores", panel2);
        tabbedPane.addTab("Consulta Notificacoes", panel3);
        tabbedPane.addTab("Consulta Pedidos Compra", panel4);
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                int selectedIndex = tabbedPane.getSelectedIndex();

                if (selectedIndex == 0) {
                    frame.setTitle("Consulta Produtos");
                    frame.setSize(700, 400);
                } else if (selectedIndex == 1) {
                    frame.setTitle("Consulta Fornecedores");
                    frame.setSize(700, 400);
                } else if (selectedIndex == 2) {
                    frame.setTitle("Consulta Notificacoes");
                    frame.setSize(700, 400);
                } else if (selectedIndex == 3) {
                    frame.setTitle("Consulta Pedidos Compra");
                    frame.setSize(700, 400);
                }
            }
        });


        frame.add(tabbedPane);


        frame.setVisible(true);


    }


    public static void menuConsultaRFuncionarios(Frame frameAnterior) {

        JFrame frame = new JFrame("Consulta de Funcionários");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);


        JTabbedPane tabbedPane = new JTabbedPane();


        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());


        String[] colunas = {"ID", "Nome", "CPF", "Telefone", "Data Contratacao", "Id departamento", "Tipo"};

        List<Funcionario> funcionarios = new ArrayList<>();
        try {
            funcionarios = sistema.getFuncionarios();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Object[][] dados = new Object[funcionarios.size()][colunas.length];
        for (int i = 0; i < funcionarios.size(); i++) {
            Funcionario funcionario = funcionarios.get(i);
            dados[i][0] = funcionario.getIdFuncionario();
            dados[i][1] = funcionario.getNome();
            dados[i][2] = funcionario.getCpf();
            dados[i][3] = funcionario.getTelefone();
            dados[i][4] = funcionario.getDataContratacao();
            dados[i][5] = funcionario.getIdDepartamento();
            dados[i][6] = funcionario.getTipo();
        }


        JTable table = new JTable(dados, colunas);


        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(e -> {

            frame.dispose();

            frameAnterior.setVisible(true);
        });


        panel1.add(new JScrollPane(table), BorderLayout.CENTER);
        panel1.add(voltarButton, BorderLayout.SOUTH);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());


        String[] colunasDepartameto = {"ID", "Nome", "Tipo Veiculo", "Tipo"};

        List<Departamento> departamentos = new ArrayList<>();
        try {
            departamentos = sistema.getDepartamentos();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Object[][] dadosDepartamentos = new Object[departamentos.size()][4];
        for (int i = 0; i < departamentos.size(); i++) {
            Departamento departamento = departamentos.get(i);
            dadosDepartamentos[i][0] = departamento.getIdDep();
            dadosDepartamentos[i][1] = departamento.getNome();
            dadosDepartamentos[i][2] = departamento.getTipoVeiculo();
            dadosDepartamentos[i][3] = departamento.getTipo();
        }


        JTable table2 = new JTable(dadosDepartamentos, colunasDepartameto);


        JButton voltarButton2 = new JButton("Voltar");
        voltarButton2.addActionListener(e -> {

            frame.dispose();

            frameAnterior.setVisible(true);
        });


        panel2.add(new JScrollPane(table2), BorderLayout.CENTER);
        panel2.add(voltarButton2, BorderLayout.SOUTH);



        tabbedPane.addTab("Consulta Funcionarios", panel1);
        tabbedPane.addTab("Consulta Departamentos", panel2);

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                int selectedIndex = tabbedPane.getSelectedIndex();

                if (selectedIndex == 0) {
                    frame.setTitle("Consulta Funcionarios");
                    frame.setSize(600, 400);
                } else if (selectedIndex == 1) {
                    frame.setTitle("Consulta Departamentos");
                    frame.setSize(600, 400);
                }
            }
        });


        frame.add(tabbedPane);


        frame.setVisible(true);
    }

    public static void menuConsultaRClientes(Frame frameAnterior) {

        JFrame frame = new JFrame("Consulta de Clientes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);


        JTabbedPane tabbedPane = new JTabbedPane();


        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());


        String[] colunas = {"ID", "Nome", "CPF", "Telefone"};

        List<Cliente> clientes = new ArrayList<>();

        try {
            clientes = sistema.getClientes();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Object[][] dados = new Object[clientes.size()][4];
        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            dados[i][0] = cliente.getIdCliente();
            dados[i][1] = cliente.getNome();
            dados[i][2] = cliente.getCpf();
            dados[i][3] = cliente.getTelefone();
        }

        JTable table = new JTable(dados, colunas);

        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(e -> {
            // Fechar a janela atual
            frame.dispose();
            // Voltar para a janela anterior
            frameAnterior.setVisible(true);
        });

        panel1.add(new JScrollPane(table), BorderLayout.CENTER);
        panel1.add(voltarButton, BorderLayout.SOUTH);

        //
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());

        String[] colunasVeiculos = {"ID", "Modelo", "Ano", "Marca", "idDono"};

        List<Veiculo> veiculos = new ArrayList<>();

        try {
            veiculos = sistema.getVeiculos();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Object[][] dadosVeiculos = new Object[veiculos.size()][5];
        for (int i = 0; i < veiculos.size(); i++) {
            Veiculo veiculo = veiculos.get(i);
            dadosVeiculos[i][0] = veiculo.getIdVeiculo();
            dadosVeiculos[i][1] = veiculo.getModelo();
            dadosVeiculos[i][2] = veiculo.getAno();
            dadosVeiculos[i][3] = veiculo.getMarca();
            dadosVeiculos[i][4] = veiculo.getIdDono();
        }


        JTable table2 = new JTable(dadosVeiculos, colunasVeiculos);

        JButton voltarButton2 = new JButton("Voltar");
        voltarButton2.addActionListener(e -> {
            frame.dispose();
            frameAnterior.setVisible(true);
        });

        panel2.add(new JScrollPane(table2), BorderLayout.CENTER);
        panel2.add(voltarButton2, BorderLayout.SOUTH);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout());

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

        JTable table3 = new JTable(dadosPedidosPersonalizacao, colunasPedidosPesonalizacao);

        // Criar o botão voltar
        JButton voltarButton3 = new JButton("Voltar");
        voltarButton3.addActionListener(e -> {
            frame.dispose();
            frameAnterior.setVisible(true);
        });

        panel3.add(new JScrollPane(table3), BorderLayout.CENTER);
        panel3.add(voltarButton3, BorderLayout.SOUTH);


        tabbedPane.addTab("Consulta Clientes", panel1);
        tabbedPane.addTab("Consulta Veiculos", panel2);
        tabbedPane.addTab("Consulta Pedidos Personalização", panel3);

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                int selectedIndex = tabbedPane.getSelectedIndex();

                if (selectedIndex == 0) {
                    frame.setTitle("Consulta Clientes");
                    frame.setSize(600, 400);
                } else if (selectedIndex == 1) {
                    frame.setTitle("Consulta Veiculos");
                    frame.setSize(600, 400);
                } else if (selectedIndex == 2) {
                    frame.setTitle("Consulta Pedidos Personalização");
                    frame.setSize(1000, 400);
                }
            }
        });


        frame.add(tabbedPane);


        frame.setVisible(true);
    }


    public static void menuConsultaDono(Frame frameAnterior) {

        JFrame frame = new JFrame("Consulta");

        frame.setLayout(new BorderLayout());

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(0, 2)); // GridLayout com 2 colunas

        JButton botao1 = new JButton("Clientes, Veículos e Pedidos");
        JLabel label1 = new JLabel("Consulta clientes, veiculos e Pedidos de personalização:");
        JButton botao2 = new JButton("Funcionários e Departamentos");
        JLabel label2 = new JLabel("Consulta de funcionarios e departamentos:");
        JButton botao3 = new JButton("Produtos, Fornecedores e Compras");
        JLabel label3 = new JLabel("Consulta produtos, fornecedores, pedidos compra e notificações:");
        JButton botaoVoltar = new JButton("Voltar");

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
                frame.setVisible(false);
                menuConsultaRFuncionarios(frame);
            }
        });


        botao3.addActionListener(new ActionListener() {
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

        painelBotoes.add(label1);
        painelBotoes.add(botao1);
        painelBotoes.add(label2);
        painelBotoes.add(botao2);
        painelBotoes.add(label3);
        painelBotoes.add(botao3);
        painelBotoes.add(botaoVoltar);

        frame.add(painelBotoes, BorderLayout.CENTER);

        frame.setSize(800, 200);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }


    // Cadastro
    public static void menuCadastroRProdutos(Frame frameAnterior) {

        JFrame frame = new JFrame("Cadastro de Produto");

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel cadastroProduto = new JPanel(new BorderLayout());

        JPanel formularioProduto = new JPanel(new GridLayout(8, 2));
        formularioProduto.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        formularioProduto.add(new JLabel("Nome:"));
        JTextField nomeTextField = new JTextField();
        formularioProduto.add(nomeTextField);
        formularioProduto.add(new JLabel("Preco unitário:"));
        SpinnerModel spinnerModel1 = new SpinnerNumberModel(0.0, 0.0, null, 0.01);
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
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        if (fornecedores != null) {
            fornecedorComboBox.addItem("--- Escolher ---");
            for (Fornecedor f : fornecedores) {
                fornecedorComboBox.addItem(f.getIdFornecedor() + " - " + f.getNome());
            }
        } else {
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
                if (textoTipoBox == "--- Escolher ---") {
                    JOptionPane.showMessageDialog(frame, "Por favor, escolha um tipo!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                char tipo = textoTipoBox.charAt(0);
                String textoFornecedorBox = (String) fornecedorComboBox.getSelectedItem();
                if (textoFornecedorBox == "--- Escolher ---") {
                    JOptionPane.showMessageDialog(frame, "Por favor, escolha um fornecedor!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (textoFornecedorBox == "Vazio") {
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


                if (nome.isEmpty() || descricao.isEmpty() || precoPorUnidade == 0) {
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
                    JOptionPane.showMessageDialog(frame, "Produto cadastrado", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(frame, "Falha ao cadastrar produto", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        JPanel botoesProdutoPanel = criarBotoesPanel(frameAnterior, frame, formularioProduto);

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

                if (nome.isEmpty() || endereco.isEmpty() || telefone.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Preencha todos os campos corretamente", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    sistema.cadastrarFornecedor(novoFornecedor);
                    JOptionPane.showMessageDialog(frame, "Fornecedor cadastrado", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(frame, "Falha ao cadastrar fornecedor", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        JPanel botoesFornecedorPanel = criarBotoesPanel(frameAnterior, frame, formularioFornecedor);

        cadastroFornecedor.add(cadastrarFornecedor, BorderLayout.NORTH);
        cadastroFornecedor.add(formularioFornecedor, BorderLayout.CENTER);
        cadastroFornecedor.add(botoesFornecedorPanel, BorderLayout.SOUTH);


        JPanel cadatroNotificacao = new JPanel(new BorderLayout());

        JPanel formularioNotificacao = new JPanel(new GridLayout(3, 2));
        formularioFornecedor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        // Adiciona os campos do formulário de notificacao
        formularioNotificacao.add(new JLabel("Produto:"));
        JComboBox<String> produtoComboBox = new JComboBox<>();

        List<Produto> produtos = null;
        try {
            produtos = sistema.getProdutos();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        if (produtos != null) {
            produtoComboBox.addItem("--- Escolher ---");
            for (Produto p : produtos) {
                produtoComboBox.addItem(p.getIdProduto() + " - " + p.getNome() + " - Tipo:" + p.getTipo());
            }
        } else {
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
                if (textoProdutoBox == "Vazio") {
                    JOptionPane.showMessageDialog(frame, "Cadastre um produto", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    if (textoProdutoBox == "--- Escolher ---") {
                        JOptionPane.showMessageDialog(frame, "Por favor, escolha um produto", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                int idProduto = extrairId(textoProdutoBox);
                double quantidadeDouble = (double) quantidadeProdutoNotificacaoSpinner.getValue();
                int quantidade = (int) quantidadeDouble;
                String dataLimiteString = dataLimiteTxt.getText();


                if (dataLimiteString.isEmpty() || quantidade == 0) {
                    JOptionPane.showMessageDialog(frame, "Por favor, preencha corretamento todos os campos", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                LocalDate dataLimite;

                try {
                    dataLimite = LocalDate.parse(dataLimiteString);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Formato de data inválido! Use o formato yyyy-MM-dd.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Notificacao novaNotificacao = new Notificacao();

                novaNotificacao.setIdProduto(idProduto);
                novaNotificacao.setQuantidade(quantidade);
                novaNotificacao.setDataLimite(dataLimite);

                try {
                    sistema.cadastrarNotificacao(novaNotificacao);
                    JOptionPane.showMessageDialog(frame, "Notificação cadastrada", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(frame, "Falha ao cadastrar notificação", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        JPanel botoesNotificacao = criarBotoesPanel(frameAnterior, frame, formularioNotificacao);

        cadatroNotificacao.add(cadastrarNotificacao, BorderLayout.NORTH);
        cadatroNotificacao.add(formularioNotificacao, BorderLayout.CENTER);
        cadatroNotificacao.add(botoesNotificacao, BorderLayout.SOUTH);


        JPanel cadastroPedidoCompra = new JPanel(new BorderLayout());

        JPanel formularioPedidoCompra = new JPanel(new GridLayout(2, 2));
        formularioPedidoCompra.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        formularioPedidoCompra.add(new JLabel("Notificacao:"));
        JComboBox<String> notificacoesBox = new JComboBox<>();

        List<Notificacao> notificacoes = null;
        try {
            notificacoes = sistema.getNotificacoes();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        if (notificacoes != null) {
            notificacoesBox.addItem("--- Escolher ---");
            for (Notificacao n : notificacoes) {
                notificacoesBox.addItem(n.getIdNotificao() + " - " + "Produto: " + n.getIdProduto() + " - Data Limite:" + n.getDataLimite() + " Quantidade: " + n.getQuantidade());
            }
        } else {
            notificacoesBox.addItem("Vazio");
        }

        formularioPedidoCompra.add(notificacoesBox);

        formularioPedidoCompra.add(new JLabel("Fornecedor: "));
        JComboBox<String> fornecedorBox = new JComboBox<>();
        fornecedores = null;
        try {
            fornecedores = sistema.getFornecedores();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        if (fornecedores != null) {
            fornecedorBox.addItem("--- Escolher ---");
            for (Fornecedor f : fornecedores) {
                fornecedorBox.addItem(f.getIdFornecedor() + " - " + f.getNome());
            }
        } else {
            fornecedorBox.addItem("Vazio");
        }

        formularioPedidoCompra.add(fornecedorBox);

        JButton cadastrarPedidoCompra = new JButton("Cadastrar Pedido de Compra");
        cadastrarPedidoCompra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String textoFornecedorBox = (String) fornecedorBox.getSelectedItem();
                String textoNotificacaoBox = (String) notificacoesBox.getSelectedItem();
                if (textoNotificacaoBox == "Vazio") {
                    JOptionPane.showMessageDialog(frame, "Cadastre um notificação", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (textoNotificacaoBox == "--- Escolher ---") {
                    JOptionPane.showMessageDialog(frame, "Escolha uma notificação", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (textoFornecedorBox == "Vazio") {
                    JOptionPane.showMessageDialog(frame, "Cadastre um fornecedor", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (textoFornecedorBox == "--- Escolher ---") {
                    JOptionPane.showMessageDialog(frame, "Escolha um fornecedor", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                int idFornecedor = extrairId(textoFornecedorBox);
                int idNotificacao = extrairId(textoNotificacaoBox);
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
                    JOptionPane.showMessageDialog(frame, "Pedido de Compra cadastrado", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(frame, "Falha ao cadastrar Pedido de Compra", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }


            }
        });

        JPanel botoesPedidoCompraPanel = criarBotoesPanel(frameAnterior, frame, formularioFornecedor);

        cadastroPedidoCompra.add(cadastrarPedidoCompra, BorderLayout.NORTH);
        cadastroPedidoCompra.add(formularioPedidoCompra, BorderLayout.CENTER);
        cadastroPedidoCompra.add(botoesPedidoCompraPanel, BorderLayout.SOUTH);


        tabbedPane.addTab("Cadastro de Produto", cadastroProduto);
        tabbedPane.addTab("Cadastro de Fornecedor", cadastroFornecedor);
        tabbedPane.addTab("Cadastro de Notificação", cadatroNotificacao);
        tabbedPane.addTab("Cadastro de Pedido de Compra", cadastroPedidoCompra);


        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = tabbedPane.getSelectedIndex();
                if (selectedIndex == 0) {
                    frame.setTitle("Cadastro de Produto");
                    frame.setSize(700, 400);
                } else if (selectedIndex == 1) {
                    frame.setTitle("Cadastro de Fornecedor");
                    frame.setSize(700, 250);
                } else if (selectedIndex == 2) {
                    frame.setTitle("Cadastro de Notificação");
                    frame.setSize(700, 250);
                } else if (selectedIndex == 3) {
                    frame.setTitle("Cadastro de Pedido de Compra");
                    frame.setSize(700, 220);
                }
            }
        });

        frame.add(tabbedPane);
        frame.setSize(700, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public static void menuCadastroRFuncionarios(Frame frameAnterior) {
        JFrame frame = new JFrame("Cadastro de Funcionario");

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel cadastroFuncionario = new JPanel(new BorderLayout());

        JPanel formularioFuncionarioPanel = new JPanel(new GridLayout(6, 2));
        formularioFuncionarioPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formularioFuncionarioPanel.add(new JLabel("Nome:"));
        JTextField nomeTextField = new JTextField();
        formularioFuncionarioPanel.add(nomeTextField);
        formularioFuncionarioPanel.add(new JLabel("CPF:"));
        JTextField cpfTextField = new JTextField();
        formularioFuncionarioPanel.add(cpfTextField);
        formularioFuncionarioPanel.add(new JLabel("Telefone:"));
        JTextField telefoneTextField = new JTextField();
        formularioFuncionarioPanel.add(telefoneTextField);
        formularioFuncionarioPanel.add(new JLabel("Data de Contratação (yyyy-MM-dd):"));
        JTextField txtDataContratacao = new JTextField();
        formularioFuncionarioPanel.add(txtDataContratacao);
        formularioFuncionarioPanel.add(new JLabel("Tipo:"));
        JComboBox<String> tipoComboBox = new JComboBox<>();
        tipoComboBox.addItem("--- Escolher ---");
        tipoComboBox.addItem("M");
        tipoComboBox.addItem("C");


        formularioFuncionarioPanel.add(tipoComboBox);
        JLabel labelDepartamento = new JLabel("Departamento:");
        formularioFuncionarioPanel.add(labelDepartamento);

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
        formularioFuncionarioPanel.add(departamentoComboBox);


        JButton cadastrarFuncionarioButton = new JButton("Cadastrar Funcionario");
        cadastrarFuncionarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textoTipoBox = (String) tipoComboBox.getSelectedItem();
                if (textoTipoBox == "--- Escolher ---") {
                    JOptionPane.showMessageDialog(frame, "Por favor, escolha um tipo!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                char tipo = textoTipoBox.charAt(0);
                String textoDepBox = (String) departamentoComboBox.getSelectedItem();
                if (tipo != 'C') {
                    if (textoDepBox == "--- Escolher ---") {
                        JOptionPane.showMessageDialog(frame, "Por favor, escolha um departamento!", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else if (textoDepBox == "Vazio") {
                        JOptionPane.showMessageDialog(frame, "Por favor, cadastre um departamento!", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                String nome = nomeTextField.getText();
                String cpf = cpfTextField.getText();
                String telefone = telefoneTextField.getText();
                int idDep = 0;
                if (tipo == 'C') {
                    idDep = 2;
                } else {
                    idDep = extrairId(textoDepBox);
                }

                String dataContratacaoString = txtDataContratacao.getText();
                LocalDate dataContratacao;

                try {
                    dataContratacao = LocalDate.parse(dataContratacaoString);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Formato de data inválido! Use o formato yyyy-MM-dd.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Funcionario novoFuncionario = new Funcionario();
                novoFuncionario.setNome(nome);
                novoFuncionario.setCpf(cpf);
                novoFuncionario.setTelefone(telefone);
                novoFuncionario.setTipo(tipo);
                novoFuncionario.setDataContratacao(dataContratacao);
                novoFuncionario.setIdDepartamento(idDep);
                if (nome.isEmpty() || cpf.isEmpty() || telefone.isEmpty() || dataContratacaoString.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Por favor, preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    sistema.cadastraFuncionario(novoFuncionario);
                    JOptionPane.showMessageDialog(frame, "Funcionario cadastrado", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(frame, "Falha ao cadastrar funcionario", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        JPanel botoesFuncionarioPanel = criarBotoesPanel(frameAnterior, frame, formularioFuncionarioPanel);

        cadastroFuncionario.add(cadastrarFuncionarioButton, BorderLayout.NORTH);
        cadastroFuncionario.add(formularioFuncionarioPanel, BorderLayout.CENTER);
        cadastroFuncionario.add(botoesFuncionarioPanel, BorderLayout.SOUTH);


        JPanel cadastroDepartamento = new JPanel(new BorderLayout());

        JPanel formularioDepartamentoPanel = new JPanel(new GridLayout(2, 2));
        formularioDepartamentoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        formularioDepartamentoPanel.add(new JLabel("Nome:"));
        JTextField nomeDeptxt = new JTextField();
        formularioDepartamentoPanel.add(nomeDeptxt);
        formularioDepartamentoPanel.add(new JLabel("Tipo de veiculo:"));
        JTextField tipoVeiculoTxt = new JTextField();
        formularioDepartamentoPanel.add(tipoVeiculoTxt);

        JButton cadastrarDepartamentoButton = new JButton("Cadastrar Departamento");
        cadastrarDepartamentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeDeptxt.getText();
                String tipoVeiculo = tipoVeiculoTxt.getText();

                Departamento novoDepartamento = new Departamento();
                novoDepartamento.setNome(nome);
                novoDepartamento.setTipo('M');
                novoDepartamento.setTipoVeiculo(tipoVeiculo);
                if (nome.isEmpty() || tipoVeiculo.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Por favor, preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    sistema.cadastrarDepartamento(novoDepartamento);
                    JOptionPane.showMessageDialog(frame, "Departamento cadastrado", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Falha ao cadastrar departamento", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        JPanel botoesDepartamentoPanel = criarBotoesPanel(frameAnterior, frame, formularioFuncionarioPanel);

        cadastroDepartamento.add(cadastrarDepartamentoButton, BorderLayout.NORTH);
        cadastroDepartamento.add(formularioDepartamentoPanel, BorderLayout.CENTER);
        cadastroDepartamento.add(botoesDepartamentoPanel, BorderLayout.SOUTH);


        tabbedPane.addTab("Cadastro de Funcionario", cadastroFuncionario);
        tabbedPane.addTab("Cadastro de Departamento", cadastroDepartamento);


        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = tabbedPane.getSelectedIndex();
                if (selectedIndex == 0) {
                    frame.setTitle("Cadastro de Funcionario");
                    frame.setSize(600, 300);
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
                } else if (selectedIndex == 1) {
                    frame.setTitle("Cadastro de Departamento");
                    frame.setSize(600, 200);
                }
            }
        });

        frame.add(tabbedPane);
        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public static void menuCadastroRClientes(Frame frameAnterior) {
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
            System.out.println("ERRO: No getProdutos " + ex.getMessage());
        }
        if (produtos.size() > 0) {
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
            } else if (component instanceof JSpinner) {
                ((JSpinner) component).setValue(0);
            } else if (component instanceof JComboBox<?>) {
                ((JComboBox) component).setSelectedIndex(0);
            }
        }
    }


    public static void menuDeletarDono(Frame frameAnterior) {
        JFrame frame = new JFrame("Deletar");

        frame.setLayout(new BorderLayout());

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(0, 2));

        JButton botao1 = new JButton("Clientes, Veículos e Pedidos");
        JLabel label1 = new JLabel("Deleção de clientes, veiculos e Pedidos de personalização:");
        JButton botao2 = new JButton("Funcionários e Departamentos");
        JLabel label2 = new JLabel("Deleção de funcionarios e departamentos:");
        JButton botao3 = new JButton("Produtos, Fornecedores e Compras");
        JLabel label3 = new JLabel("Deleção de produtos, fornecedores, pedidos compra e notificações:");
        JButton botaoVoltar = new JButton("Voltar");

        botao1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                menuDeletarRClientes(frame);
            }
        });
        botao2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                menuDeletaRFuncionarios(frame);
            }
        });


        botao3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO:Terminar isso aqui
                frame.setVisible(false);
                menuDeletaRProdutos(frame);
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

        painelBotoes.add(label1);
        painelBotoes.add(botao1);
        painelBotoes.add(label2);
        painelBotoes.add(botao2);
        painelBotoes.add(label3);
        painelBotoes.add(botao3);
        painelBotoes.add(botaoVoltar);

        frame.add(painelBotoes, BorderLayout.CENTER);

        frame.setSize(800, 200);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    public static void menuCadastroDono(Frame frameAnterior) {
        JFrame frame = new JFrame("PROGRAMA");

        frame.setLayout(new BorderLayout());

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(0, 2));

        JButton botao1 = new JButton("Clientes, Veículos e Pedidos");
        JLabel label1 = new JLabel("Cadastro clientes, veiculos e Pedidos de personalização:");
        JButton botao2 = new JButton("Funcionários e Departamentos");
        JLabel label2 = new JLabel("Cadastro de funcionarios e departamentos:");
        JButton botao3 = new JButton("Produtos, Fornecedores e Compras");
        JLabel label3 = new JLabel("Cadastro produtos, fornecedores, pedidos compra e notificações:");
        JButton botaoVoltar = new JButton("Voltar");

        botao1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                menuCadastroRClientes(frame);
            }
        });
        botao2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                menuCadastroRFuncionarios(frame);
            }
        });


        botao3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO:Terminar isso aqui
                frame.setVisible(false);
                menuCadastroRProdutos(frame);
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

        painelBotoes.add(label1);
        painelBotoes.add(botao1);
        painelBotoes.add(label2);
        painelBotoes.add(botao2);
        painelBotoes.add(label3);
        painelBotoes.add(botao3);
        painelBotoes.add(botaoVoltar);

        frame.add(painelBotoes, BorderLayout.CENTER);

        frame.setSize(800, 200);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    public static void menuFinanceiro(Frame frameAnterior){
        JFrame frame = new JFrame("Financeiro");

        // Define o layout da janela como BorderLayout
        frame.setLayout(new BorderLayout());


        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(3, 2)); // GridLayout com 2 colunas
        JLabel entrada = new JLabel("Entrada: ");
        JLabel entraNum = new JLabel();
        JLabel saida = new JLabel("Saída: ");
        JLabel saidaNum = new JLabel();
        JLabel diferenca = new JLabel("Diferença: ");
        JLabel diferencaNum = new JLabel();


        double entradaValor = 0;
        double saidaValor = 0;

        try {
            entradaValor =sistema.getTotalVendas();
            saidaValor =  sistema.getTotalCompras();
            System.out.println(entradaValor);
            System.out.println(saidaValor);
        }catch (Exception e){
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(frame, "Erro: Cadastre pedidos de compra e personalização", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        entraNum.setText(Double.toString(entradaValor));
        saidaNum.setText(Double.toString(saidaValor));
        diferencaNum.setText(Double.toString(entradaValor - saidaValor));
        painel.add(entrada);
        painel.add(entraNum);
        painel.add(saida);
        painel.add(saidaNum);
        painel.add(diferenca);
        painel.add(diferencaNum);
        JButton botaoVoltar = new JButton("Voltar");

        botaoVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                frameAnterior.setVisible(true);
            }
        });

        frame.add(painel, BorderLayout.CENTER);

        frame.add(botaoVoltar,BorderLayout.SOUTH);

        frame.setSize(500, 200);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    public static void menuDono(Frame frameAnterior) {

        JFrame frame = new JFrame("Menu Aministrador");


        frame.setLayout(new BorderLayout());


        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER));


        painelBotoes.add(Box.createVerticalStrut(150));

        JButton botao1 = new JButton("Cadastrar");
        JButton botao2 = new JButton("Consultar");
        JButton botao3 = new JButton("Deletar");
        JButton botao4 = new JButton("Financeiro");
        JButton botaoVoltar = new JButton("Voltar");

        botao1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                menuCadastroDono(frame);
            }
        });
        botao2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                menuConsultaDono(frame);
            }
        });


        botao3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                menuDeletarDono(frame);
            }
        });


        botao4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                menuFinanceiro(frame);
            }
        });

        botaoVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                frameAnterior.setVisible(true);
            }
        });

        painelBotoes.add(botao1);
        painelBotoes.add(botao2);
        painelBotoes.add(botao3);
        painelBotoes.add(botao4);
        painelBotoes.add(botaoVoltar);

        frame.add(painelBotoes, BorderLayout.CENTER);
        frame.setSize(500, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

