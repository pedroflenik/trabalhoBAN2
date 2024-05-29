package dados;

public class Veiculo {

    private int idVeiculo;
    private String modelo;
    private String ano;
    private String marca;
    private int idDono;


    public int getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getIdDono() {
        return idDono;
    }

    public void setIdDono(int idDono) {
        this.idDono = idDono;
    }
}
