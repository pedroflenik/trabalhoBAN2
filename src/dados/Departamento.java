package dados;

public class Departamento
{

    private int idDep;

    private String nome;

    private String tipoVeiculo;

    private char tipo;


    public int getIdDep() {
        return idDep;
    }

    public void setIdDep(int idDep) {
        this.idDep = idDep;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(String tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }
}
