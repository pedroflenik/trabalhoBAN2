import dados.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import logico.*;

public class Main {
    private static Scanner scan = new Scanner(System.in);
    private static Sistema sistema = new Sistema();

    public static Notificacao  cadastroNotificacao(){
        Notificacao novaNotificao = new Notificacao();

        System.out.println("Escolha um produto:");
        mostraProdutos();
        novaNotificao.setIdProduto(scan.nextInt());
        scan.nextLine();
        System.out.println("Insira a quantidade");
        novaNotificao.setQuantidade(scan.nextInt());
        scan.nextLine();
        novaNotificao.setDataLimite(pegaData("Insira a data limite"));

        return novaNotificao;
    }



    public static void  confirmarEntregaProduto(){
        List<PedidoCompra> pedidos = sistema.getPedidosCompra();
        int escolha = 0;
        System.out.println("Escolha um pedido para confirmar a entrega:");
        mostrarPedidosCompra();
        escolha = scan.nextInt();
        scan.nextLine();

        pedidos.get(escolha - 1).setEntregue(true);

    }

    public static PedidoCompra  cadastroPedidoCompra(){
        PedidoCompra novoPedidoCompra = new PedidoCompra();
        System.out.println("Escolha uma notificação:");
        mostraNotificacoes();
        novoPedidoCompra.setIdNotificacao(scan.nextInt());
        scan.nextLine();

        return novoPedidoCompra;
    }

    public static Produto cadastroProduto(){
        Produto novoProduto = new Produto();
        System.out.println("Insira o nome do produto:");
        novoProduto.setNome(scan.nextLine());
        System.out.println("Insira o preço unitario do produto:");
        novoProduto.setPrecoPorUnidade(scan.nextDouble());
        System.out.println("Escolha o tipo do produto:\n" +
                "E -> Especial\n" +
                "C -> Comum");
        novoProduto.setTipo(scan.next().charAt(0));
        System.out.println("Insira a quantidade mimima que deve existir do produto em estoque:");
        novoProduto.setQuantidadeMinima(scan.nextInt());
        scan.nextLine();
        System.out.println("Escolha um fornecedor principal para o produto:");
        mostraFornecedores();
        System.out.println("Insira a quantidade atual do produto em estoque:");
        novoProduto.setQuantidadeEstoque(scan.nextInt());
        scan.nextLine();
        novoProduto.setFornecedorPrincipal(scan.nextInt());
        scan.nextLine();

        return novoProduto;
    }
    public static Fornecedor cadastroFornecedor(){
        Fornecedor novoFornecedor = new Fornecedor();
        System.out.println("Insira o nome do fornecedor:");
        novoFornecedor.setNome(scan.nextLine());
        System.out.println("Insira o telefone do fornecedor:");
        novoFornecedor.setTelefone(scan.nextLine());
        System.out.println("Insira o endereco do fornecedor:");
        novoFornecedor.setEndereco(scan.nextLine());

        return novoFornecedor;
    }
    public static Departamento cadastroDepartamento(){
        Departamento novoDepartamento = new Departamento();

        System.out.println("Insira o nome do departamento:");
        novoDepartamento.setNome(scan.nextLine());
        System.out.println("Insira o tipo de veiculo:");
        novoDepartamento.setTipoVeiculo(scan.nextLine());
        novoDepartamento.setTipo('V');

        return novoDepartamento;
    }


    public static Veiculo cadastroVeiculo(){
        Veiculo novoVeiculo = new Veiculo();
        System.out.println("Insira o modelo do veículo:");
        novoVeiculo.setModelo(scan.nextLine());
        System.out.println("Insira o ano do veículo");
        novoVeiculo.setAno(scan.nextLine());
        System.out.println("Insira a marca do veículo");
        novoVeiculo.setMarca(scan.nextLine());
        System.out.println("Escolha o cliente dono do veiculo:");
         mostraClientes();
        novoVeiculo.setIdDono(scan.nextInt());
        scan.nextLine();
        scan.nextLine();
        return novoVeiculo;
    }
    public static Cliente cadastroCliente(){
        Cliente novoCliente = new Cliente();
        System.out.println("Insira o nome do cliente:");
        novoCliente.setNome(scan.nextLine());
        System.out.println("Insira o cpf do cliente:");
        novoCliente.setCpf(scan.nextLine());
        System.out.println("Insira o telefone do cliente:");
        novoCliente.setTelefone(scan.nextLine());

        return novoCliente;
    }

    public static PedidoPersonalizacao cadastroPeididoPersonalizacao(){
        PedidoPersonalizacao novoPedidoPersonalizacao = new PedidoPersonalizacao();
        System.out.println("Insira uma descrição:");
        novoPedidoPersonalizacao.setDescricao(scan.nextLine());
        System.out.println("Insira o valor da personalização:");
        novoPedidoPersonalizacao.setValorPersonalizacao(scan.nextDouble());
        novoPedidoPersonalizacao.setDataEntrega(pegaData("Insira a data de entrega:"));
        System.out.println("Escolha um veiculo:");
        mostraVeiculos();
        novoPedidoPersonalizacao.setIdVeiculo(scan.nextInt());
        scan.nextLine();
        System.out.println("Escolha o departamento responsável:");
        mostraDepartamentosV();
        novoPedidoPersonalizacao.setDepartamentoResponsavel(scan.nextInt());
        scan.nextLine();


        return novoPedidoPersonalizacao;
    }
    public static Funcionario cadastroAministrador(){
        Funcionario novoFuncionario = new Funcionario();
        System.out.println("Insira o nome do funcionário:");
        novoFuncionario.setNome(scan.nextLine());
        System.out.println("Insira o cpf do funcionário:");
        novoFuncionario.setCpf(scan.nextLine());
        System.out.println("Insira o telefone do funcionário:");
        novoFuncionario.setTelefone(scan.nextLine());
        novoFuncionario.setDataContratacao(pegaData("Insira a data de contratação do funcionário:"));
        novoFuncionario.setTipo('D');
        novoFuncionario.setIdDepartamento(0);

        return novoFuncionario;
    }

    public static Funcionario cadastroFuncionario(){
        Funcionario novoFuncionario = new Funcionario();
        System.out.println("Insira o nome do funcionário:");
        novoFuncionario.setNome(scan.nextLine());
        System.out.println("Insira o cpf do funcionário:");
        novoFuncionario.setCpf(scan.nextLine());
        System.out.println("Insira o telefone do funcionário:");
        novoFuncionario.setTelefone(scan.nextLine());
        novoFuncionario.setDataContratacao(pegaData("Insira a data de contratação do funcionário:"));
        System.out.println("Insira o tipo de funcionario:\n" +
                "'D' -> Dono\n" +
                "'M' -> Mecanico\n" +
                "'C' -> Compras");
        novoFuncionario.setTipo(scan.next().charAt(0));
        switch (novoFuncionario.getTipo()){
            case  'D':
                novoFuncionario.setIdDepartamento(0);
                break;
            case 'C':
                novoFuncionario.setIdDepartamento(1);
                break;
            case 'M':
                if(sistema.getDepartamentos().size() == 2){
                    System.out.println("Cadastre um departamento para mecanicos antes de cadastrar um funcionario");
                    return null;
                }else{
                    System.out.println("Escolha um departamento para o funcionario:");
                    mostraDepartamentosV();
                }

                novoFuncionario.setIdDepartamento(scan.nextInt());
                scan.nextLine();
                break;
        }

        return novoFuncionario;
    }

    public static LocalDate pegaData(String msg) {
        LocalDate data = null;
        boolean valido;
        do {
            System.out.println(msg);
            System.out.println("Insira o dia:");
            int dia = scan.nextInt();
            scan.nextLine();
            System.out.println("Insira o mês:");
            int mes = scan.nextInt();
            scan.nextLine();
            System.out.println("Insira o ano:");
            int ano = scan.nextInt();
            scan.nextLine();

            try {
                data = LocalDate.of(ano, mes, dia);
                valido = true;
            } catch (Exception e) {
                System.out.println("Data inválida. Por favor, insira uma data válida.");
                valido = false;
            }
        } while (!valido);

        return data;
    }

    public static void menuCadastro(){
        int opcao = 0;

        while(opcao != 2){
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Cadastrar Funcionario");
            System.out.println("2 - Voltar");
            opcao = scan.nextInt();
            scan.nextLine();

            switch (opcao){
                case 1:
                   //cadastroFuncionario();
                    break;
                case 2:
                    System.out.println("\nVoltando......");
                    break;
                default:
                    System.out.println("Opção inválida..");
                    break;
            }
        }
    }

    //********************************************************************

    public static void menuLogin(){
        String cpf = null;
        scan.nextLine();
        System.out.println("Insira seu cpf:");
        cpf = scan.nextLine();
        Funcionario funcinarioAtivo = sistema.efetuaLogin(cpf);
        if(funcinarioAtivo != null){
            System.out.println("Login efetuado com sucesso!");
            escolheMenu(funcinarioAtivo);
        }else{
            System.out.println("ERRO: Falha ao realizar o login");
        }

    }

    //********************************************************************

    public static void escolheMenu(Funcionario funcinarioAtivo){
        switch (funcinarioAtivo.getTipo()){
            case 'D':
                menuDono(funcinarioAtivo);
                break;
            case 'M':
                menuMecanico(funcinarioAtivo);
                break;
            case 'C':
                menuCompras(funcinarioAtivo);
                break;
            default:
                throw new IllegalArgumentException("Tipo de funcionário inválido: " + funcinarioAtivo.getTipo());
        }
    }

    public static void menuDono(Funcionario funcinarioAtivo){
        int opcao = 0;
        Scanner scan = new Scanner(System.in);
        while(opcao != 6){ // Ajustei o número da opção de "Sair"
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Cadastro de Clientes, Veículos e Pedidos de Personalização");
            System.out.println("2 - Cadastro de Funcionários e Departamentos");
            System.out.println("3 - Cadastro de Produtos, Fornecedores e Notificações");
            System.out.println("4 - Consultar");
            System.out.println("5 - Deletar");
            System.out.println("6 - Sair");
            opcao = scan.nextInt();
            scan.nextLine();

            switch (opcao){
                case 1:
                    // Opção 1 - Cadastro de Clientes, Veículos e Pedidos de Personalização
                    menuCadastroRClientes();
                    break;
                case 2:
                    // Opção 2 - Cadastro de Funcionários e Departamentos
                    menuCadastroRPessoas();
                    break;
                case 3:
                    menuCadastroRProdutos();// Opção 3 - Cadastro de Produtos, Fornecedores e Notificações
                    //
                    break;
                case 4:
                    // Opção 4 - Consultar
                    //consultar();
                    break;
                case 5:
                    // Opção 4 - Deletar
                    menuDeletarDono();
                case 6:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    public static void menuMecanico(Funcionario funcinarioAtivo){
        int opcao = 0;
        Scanner scan = new Scanner(System.in);
        while(opcao != 4){ // Ajustei o número da opção de "Sair"
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Cadastro de Clientes, Veículos e Pedidos de Personalização");
            System.out.println("2 - Cadastra Notificação");
            System.out.println("3 - Consultar estoque peças");
            System.out.println("4 - Sair");
            opcao = scan.nextInt();
            scan.nextLine();

            switch (opcao){
                case 1:
                    menuCadastroRClientes();
                    break;
                case 2:
                    sistema.cadastrarNotificacao(cadastroNotificacao());
                    break;
                case 3:
                    consutarEstoqueProdutos();
                    break;
                case 4:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    public static void menuCompras(Funcionario funcinarioAtivo){
        int opcao = 0;
        Scanner scan = new Scanner(System.in);
        while(opcao != 4){ // Ajustei o número da opção de "Sair"
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Cadastro Pedido de compra");
            System.out.println("2 - Cadastra Notificação");
            System.out.println("3 - Consulta");
            System.out.println("4 - Sair");
            opcao = scan.nextInt();
            scan.nextLine();

            switch (opcao){
                case 1:
                    sistema.cadastrarPedidoCompra(cadastroPedidoCompra());
                    break;
                case 2:
                    sistema.cadastrarNotificacao(cadastroNotificacao());
                    break;
                case 3:
                    menuConsultaCompras();
                    break;
                case 4:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }




    public static void menuCadastroRClientes(){
        int opcao = 0;
        Scanner scan = new Scanner(System.in);
        while(opcao != 4){
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Cadastrar veículo");
            System.out.println("3 - Cadastrar pedido de personalização");
            System.out.println("4 - Sair");
            opcao = scan.nextInt();
            scan.nextLine();

            switch (opcao){
                case 1:
                    sistema.cadastraCliente(cadastroCliente());
                    break;
                case 2:
                    sistema.cadastrarVeiculo(cadastroVeiculo());
                    break;
                case 3:
                    sistema.cadastrarPedidoPersonalizacao(cadastroPeididoPersonalizacao());
                    break;
                case 4:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    public static void menuCadastroRPessoas(){
        int opcao = 0;
        Scanner scan = new Scanner(System.in);
        while(opcao != 3){
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Cadastrar funcionario");
            System.out.println("2 - Cadastrar setor");
            System.out.println("3 - Sair");
            opcao = scan.nextInt();
            scan.nextLine();

            switch (opcao){
                case 1:
                    // Opção 1 - Cadastro de Funcionario,
                    if(sistema.cadastraFuncionario(cadastroFuncionario()) == 1){
                        System.out.println("ERRO: Falha ao cadastrar funcionário");
                    };
                    break;
                case 2:
                    // Opção 2 - Departamentos
                    sistema.cadastrarDepartamento(cadastroDepartamento());
                    break;
                case 3:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }


    public static void menuCadastroRProdutos(){// 3 - Cadastro de Produtos, Fornecedores e Notificações
        int opcao = 0;
        Scanner scan = new Scanner(System.in);
        while(opcao != 5){
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Cadastrar fornecedor");
            System.out.println("3 - Cadastrar notificacao");
            System.out.println("4 - Cadastrar pedido compra");
            System.out.println("5 - Sair");
            opcao = scan.nextInt();
            scan.nextLine();

            switch (opcao){
                case 1:
                    // Opção 1 - Cadastro de Funcionario,
                    sistema.cadastraProduto(cadastroProduto());
                    break;
                case 2:
                    sistema.cadastraFornecedor(cadastroFornecedor());
                    break;
                case 3:
                    sistema.cadastrarNotificacao(cadastroNotificacao());
                case 4:
                    sistema.cadastrarPedidoCompra(cadastroPedidoCompra());
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    public static void menuConsultaCompras(){// 3 - Cadastro de Produtos, Fornecedores e Notificações
        int opcao = 0;
        Scanner scan = new Scanner(System.in);
        while(opcao != 4){
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Mostrar notificacoes");
            System.out.println("2 - Mostrar pedidos compra");
            System.out.println("3 - Confirmar entrega");
            System.out.println("4 - Sair");
            opcao = scan.nextInt();
            scan.nextLine();

            switch (opcao){
                case 1:
                    mostraNotificacoes();
                    break;
                case 2:
                    mostrarPedidosCompra();
                    break;
                case 3:
                    confirmarEntregaProduto();
                case 4:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }


    public static void menuDeletarDono(){// 3 - Cadastro de Produtos, Fornecedores e Notificações
        int opcao = 0;
        Scanner scan = new Scanner(System.in);
        while(opcao != 4){
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Deletar de Clientes, Veículos e Pedidos de Personalização");
            System.out.println("2 - Deletar de Funcionários e Departamentos");
            System.out.println("3 - Deletar de Produtos, Fornecedores e Notificações");
            System.out.println("4 - Voltar");
            opcao = scan.nextInt();
            scan.nextLine();

            switch (opcao){
                case 1:
                    menuDeletarRClientes();
                    break;
                case 2:
                    menuDeletarRPessoas();
                    break;
                case 3:
                    menuDeletarRProdutos();
                case 4:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    public static void menuDeletarRClientes(){
        int opcao = 0;
        Scanner scan = new Scanner(System.in);
        while(opcao != 4){

            System.out.println("Escolha uma opção:");
            System.out.println("1 - Deletar um Clientes, Veículos e Pedidos de Personalização");
            System.out.println("2 - Deletar um Veículos");
            System.out.println("3 - Deletar um Pedidos de Personalização");
            System.out.println("4 - Voltar");
            opcao = scan.nextInt();
            scan.nextLine();

            switch (opcao){
                case 1:
                    deletarCliente();
                    break;
                case 2:
                    deletarVeiculo();
                    break;
                case 3:
                    deletarPedidoPersonalizacao();
                case 4:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    public static void menuDeletarRPessoas(){
        int opcao = 0;
        Scanner scan = new Scanner(System.in);
        while(opcao != 3){

            System.out.println("Escolha uma opção:");
            System.out.println("1 - Deletar um funcionário");
            System.out.println("2 - Deletar um departamento");
            System.out.println("3 - Voltar");
            opcao = scan.nextInt();
            scan.nextLine();

            switch (opcao){
                case 1:
                    deletarFuncionario();
                    break;
                case 2:
                    deletarDepartamento();
                    break;
                case 3:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }


    public static void menuDeletarRProdutos(){
        int opcao = 0;
        Scanner scan = new Scanner(System.in);
        while(opcao != 5){
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Deletar produto");
            System.out.println("2 - Deletar fornecedor");
            System.out.println("3 - Deletar notificacao");
            System.out.println("4 - Deletar pedido compra");
            System.out.println("5 - Voltar");
            opcao = scan.nextInt();
            scan.nextLine();

            switch (opcao){
                case 1:
                    deletarProduto();
                    break;
                case 2:
                    deletarFornecedor();
                    break;
                case 3:
                    deletarNotificacao();
                case 4:
                    deletarPedidoCompra();
                case 5:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }



    //********************************************************************
    public static  void mostraMenuInicial(){
        int opcao = 0;
        Scanner scan = new Scanner(System.in);
        while(opcao != 3){
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Cadastrar administrador");
            System.out.println("2 - Login");
            System.out.println("3 - Sair");
            opcao = scan.nextInt();
            scan.nextLine();

            switch (opcao){
                case 1:
                    if(sistema.cadastraFuncionario(cadastroAministrador()) == 1){
                        System.out.println("ERRO: Falha ao cadastrar funcionário");
                    };
                    break;
                case 2:
                    menuLogin();
                    break;
                case 3:
                    System.out.println("\nSaindo......");
                    break;
                default:
                    System.out.println("Opção inválida..");
                    break;
            }
        }

    }

    //********************************************************************

    public static void consutarEstoqueProdutos(){
        List<Produto> produtos = sistema.getProdutos();
        System.out.println("Peças disponiveis:");
        for(Produto p : produtos){
            System.out.println(
                    p.getIdProduto() + " - Nome: " + p.getNome() + " Tipo: " + p.getTipo() + " Quantidade: " + p.getQuantidadeEstoque()
            );
        }
    }

    public static void mostraClientes(){
        List<Cliente> clientes = sistema.getClientes();
        for(Cliente c : clientes){
            System.out.println(
                    c.getIdCliente() + " - " + c.getNome() + " " + c.getCpf()
            );
        }
    }

    public static void mostraVeiculos(){
        List<Veiculo> veiculos = sistema.getVeiculos();
        for(Veiculo v : veiculos){
            System.out.println(
                v.getIdVeiculo() + " - Modelo: " + v.getModelo() + " Ano: " + v.getAno()
            );
        }
    }

    public static void mostraProdutos(){
        List<Produto> produtos = sistema.getProdutos();
        for(Produto p : produtos){
            System.out.println(
                     p.getIdProduto() + " - Nome: " + p.getNome() + " Fornecedor principal: " + p.getFornecedorPrincipal()
            );
        }
    }

    public static void mostraDepartamentosV(){
        List<Departamento> departamentos = sistema.getDepartamentos();
        for(Departamento d : departamentos){
            if(d.getTipo() != 'C' && d.getTipo() != 'D'){
                System.out.println(
                    d.getIdDep() + " - Nome: " + d.getNome() + " Tipo veiculo: " + d.getTipoVeiculo()
                );
            }
        }
    }


    public static void mostrarPedidosCompra(){
        List<PedidoCompra> pedidosCompra = sistema.getPedidosCompra();
        for(PedidoCompra pd : pedidosCompra){
                System.out.println(
                       pd.getIdPedidoCompra() + " - " + "Número produto: " + pd.getIdProduto() + " Quantidade: " + pd.getQuantidade() + " Valor: " + pd.getTotalCompra()
                );
        }
    }

    public static void mostraFornecedores(){
        List<Fornecedor> fornecedores = sistema.getFornecedores();
        for(Fornecedor d : fornecedores){
            System.out.println(
                    d.getIdFornecedor() + " - Nome: " + d.getNome() + " Telefone: " + d.getTelefone()
            );
        }
    }

    public static void mostraNotificacoes(){
        List<Notificacao> notificacoes = sistema.getNotificacoes();
        for(Notificacao n : notificacoes){
            System.out.println(
                    n.getIdNotificao() + " - Produto: " + n.getIdProduto() + " Data Limite: " + n.getDataLimite()
            );
        }
    }

    public static void mostraFuncionarios(){
        List<Funcionario> funcionarios = sistema.getFuncionarios();
        for(Funcionario f : funcionarios){
            System.out.println(
                    f.getIdFuncionario() + " - Nome: " + f.getNome() + " CPF: " +f.getCpf()
            );
        }
    }


    public static void mostraPedidosPersonalizacao(){
        List<PedidoPersonalizacao> pedidosPesonalizacao = sistema.getPerdidosPersonalizacao();
        for(PedidoPersonalizacao pp : pedidosPesonalizacao){
            System.out.println(
                    pp.getIdPedido() + " - Veiculo: " + pp.getIdVeiculo() +  "Valor personalização: " + pp.getValorPersonalizacao()
            );
        }
    }

    //********************************************************************

    public static void deletarCliente(){
        int escolha = 0;
        System.out.println("Escolha um cliente para deletar:");
        mostraClientes();
        escolha = scan.nextInt();
        scan.nextLine();
        sistema.deletarCliente(escolha);
    }

    public static void deletarVeiculo(){
        int escolha = 0;
        System.out.println("Escolha um veiculo para deletar:");
        mostraVeiculos();
        escolha = scan.nextInt();
        scan.nextLine();
        sistema.deletarVeiculo(escolha);
    }

    public static void deletarPedidoPersonalizacao(){
        int escolha = 0;
        System.out.println("Escolha um pedido de personalização para deletar:");
        mostraPedidosPersonalizacao();
        escolha = scan.nextInt();
        scan.nextLine();
        sistema.deletarPedidoPersonalizacao(escolha);
    }

    public static void deletarFuncionario(){
        int escolha = 0;
        System.out.println("Escolha um funcionario para deletar:");
        mostraFuncionarios();
        escolha = scan.nextInt();
        scan.nextLine();
        sistema.deletarFuncionario(escolha);
    }

    public static void deletarDepartamento(){
        int escolha = 0;
        System.out.println("Escolha um departamento para deletar:");
        mostraDepartamentosV();
        escolha = scan.nextInt();
        scan.nextLine();
        sistema.deletarDepartamento(escolha);
    }

    public static void deletarProduto(){
        int escolha = 0;
        System.out.println("Escolha um produto para deletar:");
        mostraProdutos();
        escolha = scan.nextInt();
        scan.nextLine();
        sistema.deletarProduto(escolha);
    }

    public static void deletarNotificacao(){
        int escolha = 0;
        System.out.println("Escolha uma notificação para deletar:");
        mostraNotificacoes();
        escolha = scan.nextInt();
        scan.nextLine();
        sistema.deletarNotificacao(escolha);
    }

    public static void deletarFornecedor(){
        int escolha = 0;
        System.out.println("Escolha um fornecedor para deletar:");
        mostraFornecedores();
        escolha = scan.nextInt();
        scan.nextLine();
        sistema.deletarFornecedor(escolha);
    }

    public static void deletarPedidoCompra(){
        int escolha = 0;
        System.out.println("Escolha um pedido de compra para deletar:");
        mostrarPedidosCompra();
        escolha = scan.nextInt();
        scan.nextLine();
        sistema.deletarPedidoCompra(escolha);
    }

    //********************************************************************
    //TODO: Precisa adicionar a possibilidade de deletar as coisas
    public static void main(String[] args) {
        Departamento depInicial = new Departamento();
        depInicial.setIdDep(0);
        depInicial.setTipoVeiculo(null);
        depInicial.setTipo('D');
        depInicial.setNome("Dono");
        sistema.cadastrarDepartamento(depInicial);
        depInicial.setIdDep(1);
        depInicial.setTipoVeiculo(null);
        depInicial.setTipo('C');
        depInicial.setNome("compras");
        sistema.cadastrarDepartamento(depInicial);

        mostraMenuInicial();
    }
}