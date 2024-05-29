package dados;

public class Produto {

    private int idProduto;
    private String nome;
    private double precoPorUnidade;

    private int fornecedorPrincipal;
    private char tipo; //Fundamental ou especifico
    private int quantidadeMinima;

    private String descricao;


    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrecoPorUnidade() {
        return precoPorUnidade;
    }

    public void setPrecoPorUnidade(double precoPorUnidade) {
        this.precoPorUnidade = precoPorUnidade;
    }

    public int getFornecedorPrincipal() {
        return fornecedorPrincipal;
    }

    public void setFornecedorPrincipal(int fornecedorPrincipal) {
        this.fornecedorPrincipal = fornecedorPrincipal;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public int getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public void setQuantidadeMinima(int quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }
}
