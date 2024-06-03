package dados;
import java.time.LocalDate;
public class PedidoPersonalizacao

{
    private int idPedido;
    private LocalDate dataEntrega;
    private String descricao;
    private double valorPersonalizacao;
    private int idVeiculo;
    private int departamentoResponsavel;
    private int quantidade;
    private int idProduto;

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValorPersonalizacao() {
        return valorPersonalizacao;
    }

    public void setValorPersonalizacao(double valorPersonalizacao) {
        this.valorPersonalizacao = valorPersonalizacao;
    }

    public int getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public int getDepartamentoResponsavel() {
        return departamentoResponsavel;
    }

    public void setDepartamentoResponsavel(int departamentoResponsavel) {
        this.departamentoResponsavel = departamentoResponsavel;
    }
}
