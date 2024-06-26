package logico;

import dados.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import banco.*;
public class Sistema {

    private List<Funcionario> funcionarios;
    private List<Cliente> clientes;

    private List<Veiculo> veiculos;
    private List<Departamento> departamentos;
    private List<PedidoPersonalizacao> perdidosPersonalizacao;
    private List<Fornecedor> fornecedores;
    private List<Produto> produtos;

    private List<Notificacao> notificacoes;

    private List<PedidoCompra> pedidosCompra;

    private Banco banco;
    private Conexao con;
    public Sistema() {
        this.con = new Conexao();
        this.banco = new Banco(con);
        this.funcionarios = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.veiculos = new ArrayList<>();
        this.perdidosPersonalizacao = new ArrayList<>();
        this.departamentos = new ArrayList<>();
        this.fornecedores = new ArrayList<>();
        this.produtos = new ArrayList<>();
        this.notificacoes = new ArrayList<>();
        this.pedidosCompra = new ArrayList<>();
        this.con = new Conexao();
    }

    public List<PedidoCompra> getPedidosCompra() throws  SQLException{
        return  banco.selectPedidoCompra();
    }

    public void setPedidosCompra(List<PedidoCompra> pedidosCompra) {
        this.pedidosCompra = pedidosCompra;
    }

    public List<Notificacao> getNotificacoes() throws  SQLException{
        return banco.selectNotificacoes();
    }

    public void setNotificacoes(List<Notificacao> notificacoes) {
        this.notificacoes = notificacoes;
    }

    public List<Produto> getProdutos() throws SQLException {

        return banco.selectProdutos();

    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Fornecedor> getFornecedores() throws  SQLException {
        return banco.selectFornecedores() ;
    }

    public void setFornecedores(List<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
    }

    public List<Funcionario> getFuncionarios() throws SQLException{
        return banco.selectFuncionarios();
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public List<Cliente> getClientes() throws SQLException{
        return banco.selectClientes();
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Veiculo> getVeiculos() throws SQLException{
        return banco.selectVeiculos();
    }

    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    public List<Departamento> getDepartamentos() throws  SQLException{
        return banco.selectDepartamento();
    }

    public int countDepartamentos() throws  SQLException{
        return banco.countDepartamentos();
    }

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }

    public List<PedidoPersonalizacao> getPerdidosPersonalizacao() throws SQLException{
        return banco.selectPedidosPersonalizacao();
    }

    public void setPerdidosPersonalizacao(List<PedidoPersonalizacao> perdidosPersonalizacao) {
        this.perdidosPersonalizacao = perdidosPersonalizacao;
    }

    public int cadastrarVeiculo(Veiculo novoVeiculo) throws SQLException {
        novoVeiculo.setIdVeiculo(veiculos.size() + 1);
        veiculos.add(novoVeiculo);
        banco.cadastrarVeiculo(novoVeiculo);
        return 0;
    }

    public int cadastrarFornecedor(Fornecedor novoFornecedor) throws  SQLException{
        novoFornecedor.setIdFornecedor(fornecedores.size() + 1);
        fornecedores.add(novoFornecedor);
        banco.cadastrarFornecedor(novoFornecedor);
        return 0;
    }

    public int cadastrarPedidoCompra(PedidoCompra novoPedidoCompra) throws SQLException{
        novoPedidoCompra.setIdPedidoCompra(pedidosCompra.size() + 1);
        Notificacao notificacaoAssociada = procuraNotificacao(novoPedidoCompra.getIdNotificacao());
        Produto produtoAssociado = procuraProduto(notificacaoAssociada.getIdProduto());

        novoPedidoCompra.setEntregue(false);
        novoPedidoCompra.setIdProduto(produtoAssociado.getIdProduto());
        novoPedidoCompra.setQuantidade(notificacaoAssociada.getQuantidade());
        novoPedidoCompra.setTotalCompra(produtoAssociado.getPrecoPorUnidade() * notificacaoAssociada.getQuantidade());
        novoPedidoCompra.setIdFornecedor(produtoAssociado.getFornecedorPrincipal());
        /*
        Info pedidoCompra
        private int idFornecedor;

        private int idNotificacao;
        private int quantidade;

        private double totalCompra;

        private int idProduto;


        infoNotificacao:
          private int idNotificao;
          private int idProduto;
          private int quantidade;
          private LocalDate dataLimite;
        */

        pedidosCompra.add(novoPedidoCompra);
        banco.cadastrarPedidoCompra(novoPedidoCompra);
        //banco.deletaNotificacao(notificacaoAssociada.getIdNotificao(),con.getConnection());
        return 0;
    }

    public int cadastrarNotificacao(Notificacao novaNotificacao) throws  SQLException{
        novaNotificacao.setIdNotificao(notificacoes.size() + 1);
        notificacoes.add(novaNotificacao);
        banco.cadastrarNotificacao(novaNotificacao);
        return 0;
    }

    public int cadastrarDepartamento(Departamento novoDepartamento) throws  SQLException{
        novoDepartamento.setIdDep(departamentos.size() + 1);
        departamentos.add(novoDepartamento);
        banco.cadastrarDepartamento(novoDepartamento);
        return 0;
    }
    public int cadastraFuncionario(Funcionario novoFuncionario) throws SQLException {

        if(novoFuncionario == null){
            //TODO: Tirar isso
            System.out.println("Funcionario == null");
            return 1;
        }
        //TODO:Tirar isso
        System.out.println("Funcionario != null" + novoFuncionario.getNome());
        novoFuncionario.setIdFuncionario(funcionarios.size() + 1);
        funcionarios.add(novoFuncionario);
        banco.cadastrarFuncionario(novoFuncionario);
        return 0;
    }

    public int cadastrarPedidoPersonalizacao(PedidoPersonalizacao novoPedido) throws Exception{
        novoPedido.setIdPedido(perdidosPersonalizacao.size() + 1);
        perdidosPersonalizacao.add(novoPedido);
        banco.cadastrarPedidoPersonalizacao(novoPedido);
        banco.atualizaNotificacoes(novoPedido.getDataEntrega().toString());
        return 0;
    }

    public int cadastraCliente(Cliente novoCliente) throws  SQLException{
        novoCliente.setIdCliente(clientes.size() + 1);
        clientes.add(novoCliente);
        banco.cadastrarCliente(novoCliente);
        return 0;
    }


    public int cadastraFornecedor(Fornecedor novoFornecedor) throws  SQLException{
        novoFornecedor.setIdFornecedor(fornecedores.size() + 1);
        fornecedores.add(novoFornecedor);
        banco.cadastrarFornecedor(novoFornecedor);
        return 0;
    }

    public int cadastraProduto(Produto novoProduto) throws  SQLException{
        novoProduto.setIdProduto(produtos.size() + 1);
        produtos.add(novoProduto);
        banco.cadastrarProduto(novoProduto);
        return 0;
    }

    public Funcionario efetuaLogin(String cpf) throws SQLException{
        return banco.selectLogin(cpf);
    }

    public Notificacao procuraNotificacao(int idNotificacao){
        List<Notificacao> notificacoes2 = null;
        try{
            notificacoes2 = getNotificacoes();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        for(Notificacao n : notificacoes2){
            if(n.getIdNotificao() == idNotificacao){
                return n;
            }
        }
        return null;
    }


    public Produto procuraProduto(int idProduto){
        List<Produto> produtos2 = null;
        try {
            produtos2 = getProdutos();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        for(Produto p : produtos2){
            if(p.getIdProduto() == idProduto){
                return p;
            }
        }
        return null;
    }

    public Cliente procuraCliente(int idCliente){
        for(Cliente c : clientes){
            if(c.getIdCliente() == idCliente){
                return c;
            }
        }
        return null;
    }

    public int deletarCliente(int idCliente) throws  SQLException{
        banco.deletaCliente(idCliente);
        return 0;
    }

    public Veiculo procuraVeiculo(int idVeiculo){
        for(Veiculo v : veiculos){
            if(v.getIdVeiculo() == idVeiculo){
                return v;
            }
        }
        return null;
    }

    public int deletarVeiculo(int idVeiculo) throws SQLException{
        banco.deletaVeiculo(idVeiculo);
        return  0;
    }

    public PedidoPersonalizacao procurarPedidoPersonalizacao(int idPedido){
        for(PedidoPersonalizacao pp : perdidosPersonalizacao){
            if(pp.getIdVeiculo() == idPedido){
                return pp;
            }
        }
        return null;
    }

    public int deletarPedidoPersonalizacao(int idPedido) throws SQLException{
        banco.deletaPedidoPersonalizacao(idPedido);
        return 0;
    }

    public Funcionario procurarFuncionario(int idFuncionario){
        for(Funcionario f: funcionarios){
            if(f.getIdFuncionario() == idFuncionario){
                return f;
            }
        }
        return null;
    }


    public int deletarFuncionario(int idFuncionario) throws SQLException{
       banco.deletaFuncionario(idFuncionario);
        return 0;
    }

    public Departamento procurarDepartamento(int idDepartamento){
        for(Departamento d: departamentos){
            if(d.getIdDep() == idDepartamento){
                return d;
            }
        }
        return null;
    }

    public int deletarDepartamento(int idDepartamento) throws SQLException{
        banco.deletaDepartamento(idDepartamento);
        return 0;
    }

    public Produto procurarProduto(int idProduto){
        for(Produto p: produtos){
            if(p.getIdProduto() == idProduto){
                return p;
            }
        }
        return null;
    }

    public int deletarProduto(int idProduto) throws  SQLException{
        banco.deletaProduto(idProduto);
        return 0;
    }

    public int deletarNotificacao(int idNotificacao) throws  SQLException{
        banco.deletaNotificacao(idNotificacao);
        return 0;
    }

    public Fornecedor procuraFornecedor(int idFornecedor){
        for(Fornecedor f: fornecedores){
            if(f.getIdFornecedor() == idFornecedor){
                return f;
            }
        }
        return null;
    }

    public int deletarFornecedor(int idFornecedor) throws  SQLException{
        banco.deletaFornecedor(idFornecedor);
        return 0;
    }

    public PedidoCompra procuraPedidoCompra(int idPedidoCompra){
        for(PedidoCompra pc: pedidosCompra){
            if(pc.getIdFornecedor() == idPedidoCompra){
                return pc;
            }
        }
        return null;
    }

    public int deletarPedidoCompra(int idPedidoCompra) throws SQLException{
        banco.deletarPedidoCompra(idPedidoCompra);
        return 0;
    }

    public double getTotalCompras()throws SQLException{
        return  banco.totalPedidosCompra();
    }

    public double getTotalVendas()throws SQLException{
        return  banco.totalPedidiosPersonalizacao();
    }

    public int confirmarEntrega(int idPedido)throws SQLException{
        banco.confirmarEntrega(idPedido);
        return 0;
    }



}
