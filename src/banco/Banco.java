package banco;
import dados.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Types;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;
public class Banco {


    public void cadastrarFuncionario(Funcionario novoFuncionario, Connection con) throws SQLException {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO funcionarios (nome, cpf, telefone, dataContratacao, idDepartamento, tipo) "
                    + "VALUES (?, ?, ?, ?, ?, ?)");
            st.setString(1, novoFuncionario.getNome());
            st.setString(2, novoFuncionario.getCpf());
            st.setString(3, novoFuncionario.getTelefone());
            st.setDate(4, java.sql.Date.valueOf(novoFuncionario.getDataContratacao()));
            st.setInt(5, novoFuncionario.getIdDepartamento());
            st.setString(6, String.valueOf(novoFuncionario.getTipo()));
            st.executeUpdate();
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }



        public void cadastrarCliente(Cliente novoCliente, Connection con) throws SQLException {
            PreparedStatement st = null;
            try {
                st = con.prepareStatement("INSERT INTO cliente (nome, cpf, telefone) VALUES (?, ?, ?)");
                st.setString(1, novoCliente.getNome());
                st.setString(2, novoCliente.getCpf());
                st.setString(3, novoCliente.getTelefone());
                st.executeUpdate();
            } finally {
                if (st != null) {
                    st.close();
                }
            }
        }


        public void cadastrarFornecedor(Fornecedor novoFornecedor, Connection con) throws SQLException {
            PreparedStatement st = null;
            try {
                st = con.prepareStatement("INSERT INTO fornecedor (endereco, nome, telefone) VALUES (?, ?, ?)");
                st.setString(1, novoFornecedor.getEndereco());
                st.setString(2, novoFornecedor.getNome());
                st.setString(3, novoFornecedor.getTelefone());
                st.executeUpdate();
            } finally {
                if (st != null) {
                    st.close();
                }
            }
        }

    public void cadastrarPedidoCompra(PedidoCompra pedidoCompra, Connection con) throws SQLException {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO pedidoCompra (idFornecedor, idNotificacao, quantidade, totalCompra, idProduto, entregue) VALUES (?, ?, ?, ?, ?, ?)");
            st.setInt(1, pedidoCompra.getIdFornecedor());
            st.setInt(2, pedidoCompra.getIdNotificacao());
            st.setInt(3, pedidoCompra.getQuantidade());
            st.setDouble(4, pedidoCompra.getTotalCompra());
            st.setInt(5, pedidoCompra.getIdProduto());
            st.setBoolean(6, pedidoCompra.isEntregue());
            st.executeUpdate();
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    public void cadastrarNotificacao(Notificacao notificacao, Connection con) throws SQLException {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO notificacao (idProduto, quantidade, dataLimite) VALUES (?, ?, ?)");
            st.setInt(1, notificacao.getIdProduto());
            st.setInt(2, notificacao.getQuantidade());
            st.setDate(3, Date.valueOf(notificacao.getDataLimite()));
            st.executeUpdate();
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    public void cadastrarPedidoPersonalizacao(PedidoPersonalizacao pedido, Connection con) throws SQLException {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO pedidoPersonalizacao (dataEntrega,  descricao, valorPersonalizacao, idVeiculo, departamentoResponsavel) VALUES (?, ?, ?, ?, ?)");
            st.setDate(1, Date.valueOf(pedido.getDataEntrega()));
            st.setString(2, pedido.getDescricao());
            st.setDouble(3, pedido.getValorPersonalizacao());
            st.setInt(4, pedido.getIdVeiculo());
            st.setInt(5, pedido.getDepartamentoResponsavel());
            st.executeUpdate();
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    public void cadastrarProduto(Produto produto, Connection con) throws SQLException {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO produto (nome, precoPorUnidade, quantidadeEstoque, fornecedorPrincipal, tipo, quantidadeMinima, descricao) VALUES (?, ?, ?, ?, ?, ?, ?)");
            st.setString(1, produto.getNome());
            st.setDouble(2, produto.getPrecoPorUnidade());
            st.setInt(3, produto.getQuantidadeEstoque());
            st.setInt(4, produto.getFornecedorPrincipal());
            st.setString(5, String.valueOf(produto.getTipo()));
            st.setInt(6, produto.getQuantidadeMinima());
            st.setString(7, produto.getDescricao());
            st.executeUpdate();
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    public void cadastrarVeiculo(Veiculo veiculo, Connection con) throws SQLException {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO veiculo (modelo, ano, marca, idDono) VALUES (?, ?, ?, ?)");
            st.setString(1, veiculo.getModelo());
            st.setString(2, veiculo.getAno());
            st.setString(3, veiculo.getMarca());
            st.setInt(4, veiculo.getIdDono());
            st.executeUpdate();
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    public void cadastrarDepartamento(Departamento departamento, Connection con) throws SQLException {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO departamento (nome, tipoVeiculo, tipo) VALUES (?, ?, ?)");
            st.setString(1, departamento.getNome());
            st.setString(2, departamento.getTipoVeiculo());
            st.setString(3, String.valueOf(departamento.getTipo()));
            st.executeUpdate();
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }



}







