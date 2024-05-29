package dados;
import java.time.LocalDate;
public class Notificacao {

    private int idNotificao;
    private int idProduto;
    private int quantidade;
    private LocalDate dataLimite;

    public int getIdNotificao() {
        return idNotificao;
    }

    public void setIdNotificao(int idNotificao) {
        this.idNotificao = idNotificao;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDate dataLimite) {
        this.dataLimite = dataLimite;
    }
}
