package logico;

import dados.*;
import java.util.ArrayList;
import java.util.List;

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



    public Sistema() {
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

    public int cadastrarVeiculo(Veiculo novoVeiculo){
        novoVeiculo.setIdVeiculo(veiculos.size() + 1);
        veiculos.add(novoVeiculo);
        return 0;
    }

    public int cadastrarPedidoCompra(PedidoCompra novoPedidoCompra){
        novoPedidoCompra.setIdPedidoCompra(pedidosCompra.size() + 1);
        Notificacao notificacaoAssociada = procuraNotificacao(novoPedidoCompra.getIdNotificacao());
        Produto produtoAssociado = procuraProduto(notificacaoAssociada.getIdProduto());

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
        return 0;
    }

    public int cadastrarNotificacao(Notificacao novaNotificacao){
        novaNotificacao.setIdNotificao(notificacoes.size() + 1);
        notificacoes.add(novaNotificacao);
        return 0;
    }

    public int cadastrarDepartamento(Departamento novoDepartamento){
        novoDepartamento.setIdDep(departamentos.size() + 1);
        departamentos.add(novoDepartamento);
        return 0;
    }
    public int cadastraFuncionario(Funcionario novoFuncionario) {
        novoFuncionario.setIdFuncionario(funcionarios.size() + 1);
        funcionarios.add(novoFuncionario);
        return 0;
    }

    public int cadastrarPedidoPersonalizacao(PedidoPersonalizacao novoPedido) {
        novoPedido.setIdPedido(perdidosPersonalizacao.size() + 1);
        perdidosPersonalizacao.add(novoPedido);
        return 0;
    }

    public int cadastraCliente(Cliente novoCliente){
        novoCliente.setIdCliente(clientes.size() + 1);
        clientes.add(novoCliente);
        return 0;
    }


    public int cadastraFornecedor(Fornecedor novoFornecedor){
        novoFornecedor.setIdFornecedor(fornecedores.size() + 1);
        fornecedores.add(novoFornecedor);
        return 0;
    }

    public int cadastraProduto(Produto novoProduto){
        novoProduto.setIdProduto(produtos.size() + 1);
        produtos.add(novoProduto);
        return 0;
    }

    public Funcionario efetuaLogin(String cpf){
        for(Funcionario f : funcionarios){
            if(f.getCpf().equals(cpf)){
                return f; // SUCESSO LOGIN
            }
        }
        return null; // ERRO LOGIN
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

}
