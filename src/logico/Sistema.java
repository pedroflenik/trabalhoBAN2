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
        this.banco = new Banco();
        this.con = new Conexao();
        this.funcionarios = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.veiculos = new ArrayList<>();
        this.perdidosPersonalizacao = new ArrayList<>();
        this.departamentos = new ArrayList<>();
        this.fornecedores = new ArrayList<>();
        this.produtos = new ArrayList<>();
        this.notificacoes = new ArrayList<>();
        this.pedidosCompra = new ArrayList<>();
    }

    public List<PedidoCompra> getPedidosCompra() {
        return pedidosCompra;
    }

    public void setPedidosCompra(List<PedidoCompra> pedidosCompra) {
        this.pedidosCompra = pedidosCompra;
    }

    public List<Notificacao> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(List<Notificacao> notificacoes) {
        this.notificacoes = notificacoes;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(List<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }

    public List<PedidoPersonalizacao> getPerdidosPersonalizacao() {
        return perdidosPersonalizacao;
    }

    public void setPerdidosPersonalizacao(List<PedidoPersonalizacao> perdidosPersonalizacao) {
        this.perdidosPersonalizacao = perdidosPersonalizacao;
    }

    public int cadastrarVeiculo(Veiculo novoVeiculo) throws SQLException {
        novoVeiculo.setIdVeiculo(veiculos.size() + 1);
        veiculos.add(novoVeiculo);
        banco.cadastrarVeiculo(novoVeiculo,con.getConnection());
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
        banco.cadastrarPedidoCompra(novoPedidoCompra, con.getConnection());
        return 0;
    }

    public int cadastrarNotificacao(Notificacao novaNotificacao) throws  SQLException{
        novaNotificacao.setIdNotificao(notificacoes.size() + 1);
        notificacoes.add(novaNotificacao);
        banco.cadastrarNotificacao(novaNotificacao, con.getConnection());
        return 0;
    }

    public int cadastrarDepartamento(Departamento novoDepartamento) throws  SQLException{
        novoDepartamento.setIdDep(departamentos.size() + 1);
        departamentos.add(novoDepartamento);
        banco.cadastrarDepartamento(novoDepartamento,con.getConnection());
        return 0;
    }
    public int cadastraFuncionario(Funcionario novoFuncionario) throws SQLException {
        if(novoFuncionario == null){
            return 1;
        }
        novoFuncionario.setIdFuncionario(funcionarios.size() + 1);
        funcionarios.add(novoFuncionario);
        banco.cadastrarFuncionario(novoFuncionario,con.getConnection());
        return 0;
    }

    public int cadastrarPedidoPersonalizacao(PedidoPersonalizacao novoPedido) throws SQLException{
        novoPedido.setIdPedido(perdidosPersonalizacao.size() + 1);
        perdidosPersonalizacao.add(novoPedido);
        banco.cadastrarPedidoPersonalizacao(novoPedido,con.getConnection());
        return 0;
    }

    public int cadastraCliente(Cliente novoCliente) throws  SQLException{
        novoCliente.setIdCliente(clientes.size() + 1);
        clientes.add(novoCliente);
        banco.cadastrarCliente(novoCliente,con.getConnection());
        return 0;
    }


    public int cadastraFornecedor(Fornecedor novoFornecedor) throws  SQLException{
        novoFornecedor.setIdFornecedor(fornecedores.size() + 1);
        fornecedores.add(novoFornecedor);
        banco.cadastrarFornecedor(novoFornecedor, con.getConnection());
        return 0;
    }

    public int cadastraProduto(Produto novoProduto) throws  SQLException{
        novoProduto.setIdProduto(produtos.size() + 1);
        produtos.add(novoProduto);
        banco.cadastrarProduto(novoProduto, con.getConnection());
        return 0;
    }

    public Funcionario efetuaLogin(String cpf) throws SQLException{
        return banco.selectLogin(cpf,con.getConnection());
    }

    public Notificacao procuraNotificacao(int idNotificacao){
        for(Notificacao n : notificacoes){
            if(n.getIdNotificao() == idNotificacao){
                return n;
            }
        }
        return null;
    }


    public Produto procuraProduto(int idProduto){
        for(Produto p : produtos){
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

    public int deletarCliente(int idCliente){
        Cliente c = procuraCliente(idCliente);
        if(c != null){
            clientes.remove(c);
            return 0;
        }

        return 1;
    }

    public Veiculo procuraVeiculo(int idVeiculo){
        for(Veiculo v : veiculos){
            if(v.getIdVeiculo() == idVeiculo){
                return v;
            }
        }
        return null;
    }

    public int deletarVeiculo(int idVeiculo){
        Veiculo v = procuraVeiculo(idVeiculo);
        if(v != null){
            veiculos.remove(v);
            return 0;
        }

        return 1;
    }

    public PedidoPersonalizacao procurarPedidoPersonalizacao(int idPedido){
        for(PedidoPersonalizacao pp : perdidosPersonalizacao){
            if(pp.getIdVeiculo() == idPedido){
                return pp;
            }
        }
        return null;
    }

    public int deletarPedidoPersonalizacao(int idPedido){
        PedidoPersonalizacao pp = procurarPedidoPersonalizacao(idPedido);
        if(pp != null){
            veiculos.remove(pp);
            return 0;
        }

        return 1;
    }

    public Funcionario procurarFuncionario(int idFuncionario){
        for(Funcionario f: funcionarios){
            if(f.getIdFuncionario() == idFuncionario){
                return f;
            }
        }
        return null;
    }

    public int deletarFuncionario(int idFuncionario){
        Funcionario f = procurarFuncionario(idFuncionario);
        if(f != null){
            funcionarios.remove(f);
            return 0;
        }

        return 1;
    }

    public Departamento procurarDepartamento(int idDepartamento){
        for(Departamento d: departamentos){
            if(d.getIdDep() == idDepartamento){
                return d;
            }
        }
        return null;
    }

    public int deletarDepartamento(int idDepartamento){
        Departamento d = procurarDepartamento(idDepartamento);
        if(d != null){
            departamentos.remove(d);
            return 0;
        }

        return 1;
    }

    public Produto procurarProduto(int idProduto){
        for(Produto p: produtos){
            if(p.getIdProduto() == idProduto){
                return p;
            }
        }
        return null;
    }

    public int deletarProduto(int idProduto){
        Produto p = procurarProduto(idProduto);
        if(p != null){
            produtos.remove(p);
            return 0;
        }

        return 1;
    }

    public int deletarNotificacao(int idNotificacao){
        Notificacao n = procuraNotificacao(idNotificacao);
        if(n != null){
            notificacoes.remove(n);
            return 0;
        }

        return 1;
    }

    public Fornecedor procuraFornecedor(int idFornecedor){
        for(Fornecedor f: fornecedores){
            if(f.getIdFornecedor() == idFornecedor){
                return f;
            }
        }
        return null;
    }

    public int deletarFornecedor(int idFornecedor){
        Fornecedor f = procuraFornecedor(idFornecedor);
        if(f != null){
            fornecedores.remove(f);
            return 0;
        }

        return 1;
    }

    public PedidoCompra procuraPedidoCompra(int idPedidoCompra){
        for(PedidoCompra pc: pedidosCompra){
            if(pc.getIdFornecedor() == idPedidoCompra){
                return pc;
            }
        }
        return null;
    }

    public int deletarPedidoCompra(int idPedidoCompra){
        PedidoCompra pc = procuraPedidoCompra(idPedidoCompra);
        if(pc != null){
            fornecedores.remove(pc);
            return 0;
        }

        return 1;
    }

}
