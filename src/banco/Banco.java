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
            st = con.prepareStatement("INSERT INTO funcionario (nome, cpf, telefone, dataContratacao, idDepartamento, tipo) "
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
            st = con.prepareStatement("INSERT INTO pedidoPersonalizacao (dataEntrega,  descricao, valorPersonalizacao, idVeiculo, departamentoResponsavel,quantidadeProduto,idProduto) VALUES (?, ?, ?, ?, ?,?,?)");
            st.setDate(1, Date.valueOf(pedido.getDataEntrega()));
            st.setString(2, pedido.getDescricao());
            st.setDouble(3, pedido.getValorPersonalizacao());
            st.setInt(4, pedido.getIdVeiculo());
            st.setInt(5, pedido.getDepartamentoResponsavel());
            st.setInt(6, pedido.getQuantidade());
            st.setInt(7, pedido.getIdProduto());
            st.executeUpdate();
        } finally {
            if (st != null) {
                st.close();
            }
        }
        //removeQuantidadeEstoque(con);
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

    //**************************************************************
    //Ultilidade
    public int countDepartamentos(Connection con) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        int count = 0;

        try {
            st = con.prepareStatement("SELECT COUNT(*) AS total FROM departamento");
            rs = st.executeQuery();

            if (rs.next()) {
                count = rs.getInt("total");
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }

        return count;
    }

    //**************************************************************
    //Consultas

    public Funcionario selectLogin(String cpf, Connection con) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        Funcionario funcionario = null;

        try {
            st = con.prepareStatement("SELECT * FROM funcionario WHERE cpf=?");
            st.setString(1, cpf);
            rs = st.executeQuery();

            if (rs.next()) {
                funcionario = new Funcionario();
                funcionario.setIdFuncionario(rs.getInt("idFuncionario"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setCpf(rs.getString("cpf"));
                funcionario.setTelefone(rs.getString("telefone"));
                //Converte data
                Date dataContratacaoSql = rs.getDate("dataContratacao");
                LocalDate dataContratacao = dataContratacaoSql.toLocalDate();
                funcionario.setDataContratacao(dataContratacao);
                funcionario.setIdDepartamento(rs.getInt("idDepartamento"));
                funcionario.setTipo(rs.getString("tipo").charAt(0));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }

        return funcionario;
    }

    public List<Departamento> selectDepartamento(Connection con) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Departamento> departamentos = new ArrayList<>();

        try {
            st = con.prepareStatement("SELECT * FROM departamento");
            rs = st.executeQuery();

            while (rs.next()) {
                Departamento departamento = new Departamento();
                departamento.setIdDep(rs.getInt("idDep"));
                departamento.setNome(rs.getString("nome"));
                departamento.setTipoVeiculo(rs.getString("tipoVeiculo"));
                departamento.setTipo(rs.getString("tipo").charAt(0));
                departamentos.add(departamento);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }

        return departamentos;
    }

    public List<PedidoCompra> selectPedidoCompra(Connection con) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<PedidoCompra> pedidosCompra = new ArrayList<>();

        try {
            st = con.prepareStatement("SELECT * FROM pedidoCompra");
            rs = st.executeQuery();

            while (rs.next()) {
                PedidoCompra pedidoCompra = new PedidoCompra();
                pedidoCompra.setIdPedidoCompra(rs.getInt("idPedidoCompra"));
                pedidoCompra.setIdFornecedor(rs.getInt("idFornecedor"));
                pedidoCompra.setIdNotificacao(rs.getInt("idNotificacao"));
                pedidoCompra.setQuantidade(rs.getInt("quantidade"));
                pedidoCompra.setTotalCompra(rs.getDouble("totalCompra"));
                pedidoCompra.setIdProduto(rs.getInt("idProduto"));
                pedidoCompra.setEntregue(rs.getBoolean("entregue"));
                pedidosCompra.add(pedidoCompra);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }

        return pedidosCompra;
    }

    public List<Cliente> selectClientes(Connection con) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Cliente> clientes = new ArrayList<>();

        try {
            st = con.prepareStatement("SELECT * FROM cliente");
            rs = st.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setTelefone(rs.getString("telefone"));
                clientes.add(cliente);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }

        return clientes;
    }

    public List<Veiculo> selectVeiculos(Connection con) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Veiculo> veiculos = new ArrayList<>();

        try {
            st = con.prepareStatement("SELECT * FROM veiculo");
            rs = st.executeQuery();

            while (rs.next()) {
                Veiculo veiculo = new Veiculo();
                veiculo.setIdVeiculo(rs.getInt("idVeiculo"));
                veiculo.setModelo(rs.getString("modelo"));
                veiculo.setAno(rs.getString("ano"));
                veiculo.setMarca(rs.getString("marca"));
                veiculo.setIdDono(rs.getInt("idDono"));
                veiculos.add(veiculo);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }

        return veiculos;

    }

    public List<Produto> selectProdutos(Connection con) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Produto> produtos = new ArrayList<>();

        try {
            st = con.prepareStatement("SELECT * FROM produto");
            rs = st.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setIdProduto(rs.getInt("idProduto"));
                produto.setNome(rs.getString("nome"));
                produto.setPrecoPorUnidade(rs.getDouble("precoPorUnidade"));
                produto.setQuantidadeEstoque(rs.getInt("quantidadeEstoque"));
                produto.setFornecedorPrincipal(rs.getInt("fornecedorPrincipal"));
                produto.setTipo(rs.getString("tipo").charAt(0));
                produto.setQuantidadeMinima(rs.getInt("quantidadeMinima"));
                produto.setDescricao(rs.getString("descricao"));
                produtos.add(produto);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }

        return produtos;
    }

    public List<PedidoPersonalizacao> selectPedidosPersonalizacao(Connection con) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<PedidoPersonalizacao> pedidosPersonalizacao = new ArrayList<>();

        try {
            st = con.prepareStatement("SELECT * FROM pedidoPersonalizacao");
            rs = st.executeQuery();

            while (rs.next()) {
                PedidoPersonalizacao pedido = new PedidoPersonalizacao();
                pedido.setIdPedido(rs.getInt("idPedido"));
                pedido.setDataEntrega(rs.getDate("dataEntrega").toLocalDate());
                pedido.setDescricao(rs.getString("descricao"));
                pedido.setValorPersonalizacao(rs.getDouble("valorPersonalizacao"));
                pedido.setIdVeiculo(rs.getInt("idVeiculo"));
                pedido.setDepartamentoResponsavel(rs.getInt("departamentoResponsavel"));
                pedido.setQuantidade(rs.getInt("quantidadeProduto"));
                pedido.setIdProduto(rs.getInt("idProduto"));
                pedidosPersonalizacao.add(pedido);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }

        return pedidosPersonalizacao;
    }

    public List<Funcionario> selectFuncionarios(Connection con) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            st = con.prepareStatement("SELECT * FROM funcionario");
            rs = st.executeQuery();

            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setIdFuncionario(rs.getInt("idFuncionario"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setCpf(rs.getString("cpf"));
                funcionario.setTelefone(rs.getString("telefone"));
                funcionario.setDataContratacao(rs.getDate("dataContratacao").toLocalDate());
                funcionario.setIdDepartamento(rs.getInt("idDepartamento"));
                funcionario.setTipo(rs.getString("tipo").charAt(0));
                funcionarios.add(funcionario);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }

        return funcionarios;
    }

    public List<Notificacao> selectNotificacoes(Connection con) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Notificacao> notificacoes = new ArrayList<>();

        try {
            st = con.prepareStatement("SELECT * FROM notificacao");
            rs = st.executeQuery();

            while (rs.next()) {
                Notificacao notificacao = new Notificacao();
                notificacao.setIdNotificao(rs.getInt("idNotificacao"));
                notificacao.setIdProduto(rs.getInt("idProduto"));
                notificacao.setQuantidade(rs.getInt("quantidade"));
                notificacao.setDataLimite(rs.getDate("dataLimite").toLocalDate());
                notificacoes.add(notificacao);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }

        return notificacoes;
    }

    public List<Fornecedor> selectFornecedores(Connection con) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Fornecedor> fornecedores = new ArrayList<>();

        try {
            st = con.prepareStatement("SELECT * FROM fornecedor");
            rs = st.executeQuery();

            while (rs.next()) {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setIdFornecedor(rs.getInt("idFornecedor"));
                fornecedor.setEndereco(rs.getString("endereco"));
                fornecedor.setNome(rs.getString("nome"));
                fornecedor.setTelefone(rs.getString("telefone"));
                fornecedores.add(fornecedor);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }

        return fornecedores;
    }


    //**************************************************************
    //Deltar
    public void deletaProduto(int idProduto, Connection con) throws SQLException {
        PreparedStatement st = null;

        try {

            st = con.prepareStatement("DELETE FROM produto WHERE idProduto = ?");
            st.setInt(1, idProduto);
            st.executeUpdate();
            //con.commit();

        } catch (SQLException e) {
            //con.rollback();
            throw e;
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    public void deletaDepartamento(int idDepartamento, Connection con) throws SQLException {
        PreparedStatement st = null;

        try {

            st = con.prepareStatement("DELETE FROM departamento WHERE idDep = ?");
            st.setInt(1, idDepartamento);
            st.executeUpdate();
            //con.commit();

        } catch (SQLException e) {
            //con.rollback();
            throw e;
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    public void deletaCliente(int idCliente, Connection con) throws SQLException {
        PreparedStatement st = null;

        try {

            st = con.prepareStatement("DELETE FROM cliente WHERE idCliente = ?");

            st.setInt(1, idCliente);

            st.executeUpdate();

            //con.commit();

        } catch (SQLException e) {

            //con.rollback();
            throw e;
        } finally {

            if (st != null) {
                st.close();
            }
        }
    }

    public void deletaFornecedor(int idFornecedor, Connection con) throws SQLException {
        PreparedStatement st = null;

        try {

            st = con.prepareStatement("DELETE FROM fornecedor WHERE idFornecedor = ?");

            st.setInt(1, idFornecedor);

            st.executeUpdate();

            //con.commit();

        } catch (SQLException e) {

            //con.rollback();
            throw e;
        } finally {

            if (st != null) {
                st.close();
            }
        }
    }

    public void deletaFuncionario(int idFuncionario, Connection con) throws SQLException {
        PreparedStatement st = null;

        try {
            st = con.prepareStatement("DELETE FROM funcionario WHERE idFuncionario = ?");
            st.setInt(1, idFuncionario);
            st.executeUpdate();
            //con.commit();

        } catch (SQLException e) {
            // Em caso de erro, fazemos rollback da transação
            //con.rollback();
            throw e;
        } finally {
            // Fechamos os recursos
            if (st != null) {
                st.close();
            }
        }
    }

    public void deletaNotificacao(int idNotificacao, Connection con) throws SQLException {
        PreparedStatement st = null;

        try {
            st = con.prepareStatement("DELETE FROM notificacao WHERE idNotificacao = ?");
            st.setInt(1, idNotificacao);
            st.executeUpdate();
            //con.commit();
        } catch (SQLException e) {
            // Em caso de erro, fazemos rollback da transação
            //con.rollback();
            throw e;
        } finally {
            // Fechamos os recursos
            if (st != null) {
                st.close();
            }
        }
    }

    public void deletarPedidoCompra(int idPedidoCompra, Connection con) throws SQLException {
        PreparedStatement st = null;

        try {
            st = con.prepareStatement("DELETE FROM pedidoCompra WHERE idPedidoCompra = ?");

            st.setInt(1, idPedidoCompra);
            st.executeUpdate();
            //con.commit();

        } catch (SQLException e) {
            ////con.rollback();
            throw e;
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    public void deletaPedidoPersonalizacao(int idPedido, Connection con) throws SQLException {
        PreparedStatement st = null;

        try {
            st = con.prepareStatement("DELETE FROM pedidoPersonalizacao WHERE idPedido = ?");
            st.setInt(1, idPedido);
            st.executeUpdate();
            //con.commit();

        } catch (SQLException e) {
            //con.rollback();
            throw e;
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    public void deletaVeiculo(int idVeiculo, Connection con) throws SQLException {
        PreparedStatement st = null;

        try {
            st = con.prepareStatement("DELETE FROM veiculo WHERE idVeiculo = ?");
            st.setInt(1, idVeiculo);
            st.executeUpdate();
            //con.commit();

        } catch (SQLException e) {
            //con.rollback();
            throw e;
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    public double totalPedidosCompra(Connection con) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;

        double total = 0;

        try {
            st = con.prepareStatement("SELECT SUM(totalcompra) AS total FROM pedidocompra");
            rs = st.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }

        return total;
    }

    public double totalPedidiosPersonalizacao(Connection con) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;

        double total = 0;

        try {
            st = con.prepareStatement("SELECT SUM(valorpersonalizacao) AS total FROM pedidopersonalizacao");
            rs = st.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }

        return total;
    }

    public void confirmarEntrega( int idPedido,Connection con) throws SQLException {
        // Preparar a chamada da função PL/pgSQL usando uma instrução SQL simples
        String sql = "SELECT atualizar_pedido_e_produto(?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            // Configurar o idPedido como parâmetro da função
            stmt.setInt(1, idPedido);

            // Executar a instrução SQL
            stmt.execute();
        }

    }

    public void removeQuantidadeEstoque(Connection con) throws SQLException {
        // Preparar a chamada da função PL/pgSQL usando uma instrução SQL simples
        //String sql = "SELECT remover_quantidade_estoque_pedido_personalizacao()";
        /*
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.execute();
        }

         */
    }

    public void atualizaNotificacoes(Connection con) throws SQLException {
        // Preparar a chamada da função PL/pgSQL usando uma instrução SQL simples
        String sql = "SELECT atualizar_estoque_e_notificar()";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.execute();
        }
    }

}







