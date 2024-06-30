package banco;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.*;
import com.mongodb.client.result.UpdateResult;
import dados.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.*;



public class Banco {

    private MongoCollection<Document> funcionarioCollection;
    private    Conexao conexao;
    public Banco(Conexao conexao) {
        this.conexao = conexao;
    }

    public static Date parseStringToDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(dateString);
    }


    public static LocalDate parseStringToLocalDate(String dateString) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }

    public Optional<Integer> getMaxIdFuncionario() {
        MongoDatabase database = conexao.getDatabase();
        MongoCollection<Document> funcionarioCollection = database.getCollection("funcionario");

        // Maior id
        Integer maxId = funcionarioCollection.find()
                .projection(Projections.fields(Projections.include("idFuncionario"), Projections.excludeId()))
                .sort(new Document("idFuncionario", -1))
                .limit(1)
                .map(doc -> doc.getInteger("idFuncionario"))
                .first();

        return Optional.ofNullable(maxId);
    }

    public void cadastrarFuncionario(Funcionario novoFuncionario) {
        MongoDatabase database = conexao.getDatabase(); //get db
        MongoCollection<Document> funcionarioCollection = database.getCollection("funcionario");

        Optional<Integer> maxIdOptional = getMaxIdFuncionario();
        int newId = maxIdOptional.orElse(0) + 1; // calcula  new idFuncionario


        String dataContratacaoStr = novoFuncionario.getDataContratacao().toString();

        // Cria documento
        Document doc = new Document()
                .append("idFuncionario", newId)
                .append("nome", novoFuncionario.getNome())
                .append("cpf", novoFuncionario.getCpf())
                .append("telefone", novoFuncionario.getTelefone())
                .append("dataContratacao", dataContratacaoStr) // guarda como  string
                .append("idDepartamento", novoFuncionario.getIdDepartamento())
                .append("tipo", novoFuncionario.getTipo());

        // Insere
        funcionarioCollection.insertOne(doc);
    }

    public int getMaxIdCliente() {
        MongoDatabase database = conexao.getDatabase();
        MongoCollection<Document> clienteCollection = database.getCollection("cliente");

        //Sor documentos com limite 1
        Document result = clienteCollection.find()
                .sort(Sorts.descending("idCliente"))
                .limit(1)
                .first();

        if (result != null) {
            return result.getInteger("idCliente", 0) + 1;
        } else {
            return 1; // default
        }
    }


    public void cadastrarCliente(Cliente novoCliente) {

        MongoCollection<Document> clienteCollection = conexao.getDatabase().getCollection("cliente");

        int newId = getMaxIdCliente() + 1; // Calcula novo id
        // Cria novo documento
        Document doc = new Document()
                .append("idCliente",newId)
                .append("nome", novoCliente.getNome())
                .append("cpf", novoCliente.getCpf())
                .append("telefone", novoCliente.getTelefone());

        // Insere
        clienteCollection.insertOne(doc);
    }

    public int getMaxIdFornecedor() {
        MongoDatabase database = conexao.getDatabase();
        MongoCollection<Document> fornecedorCollection = database.getCollection("fornecedor");

        // Sort desc limite 1
        Document result = fornecedorCollection.find()
                .sort(Sorts.descending("idFornecedor"))
                .limit(1)
                .first();

        if (result != null) {
            return result.getInteger("idFornecedor", 0) + 1;
        } else {
            return 1; // default
        }
    }

    public void cadastrarFornecedor(Fornecedor novoFornecedor) {

        MongoCollection<Document> fornecedorCollection = conexao.getDatabase().getCollection("fornecedor");
        int newId = getMaxIdFornecedor();
        // Cria documento
        Document doc = new Document()
                .append("idFornecedor",newId)
                .append("endereco", novoFornecedor.getEndereco())
                .append("nome", novoFornecedor.getNome())
                .append("telefone", novoFornecedor.getTelefone());

        // Insere
        fornecedorCollection.insertOne(doc);
    }

    public int getMaxIdPedidoCompra() {
        MongoDatabase database = conexao.getDatabase();
        MongoCollection<Document> pedidoCompraCollection = database.getCollection("pedidoCompra");

        // Sort docs
        Document result = pedidoCompraCollection.find()
                .sort(Sorts.descending("idPedidoCompra"))
                .limit(1)
                .first();

        if (result != null) {
            return result.getInteger("idPedidoCompra", 0) + 1;
        } else {
            return 1; // Defalut
        }
    }

    public void cadastrarPedidoCompra(PedidoCompra pedidoCompra) {

        MongoCollection<Document> pedidoCompraCollection = conexao.getDatabase().getCollection("pedidoCompra");

        // Cria doc
        int newId = getMaxIdPedidoCompra(); // calcula novo id
        Document doc = new Document()
                .append("idPedidoCompra",newId)
                .append("idFornecedor", pedidoCompra.getIdFornecedor())
                .append("idNotificacao", pedidoCompra.getIdNotificacao())
                .append("quantidade", pedidoCompra.getQuantidade())
                .append("totalCompra", pedidoCompra.getTotalCompra())
                .append("idProduto", pedidoCompra.getIdProduto())
                .append("entregue", pedidoCompra.isEntregue());

        // insere
        pedidoCompraCollection.insertOne(doc);
    }

    public int getMaxIdNotificacao() {
        MongoDatabase database = conexao.getDatabase();
        MongoCollection<Document> notificacaoCollection = database.getCollection("notificacao");

        // Sort docs limite 1
        Document result = notificacaoCollection.find()
                .sort(Sorts.descending("idNotificacao"))
                .limit(1)
                .first();

        if (result != null) {
            return result.getInteger("idNotificacao", 0) + 1;
        } else {
            return 1; // default
        }
    }

    public void cadastrarNotificacao(Notificacao notificacao) {

        MongoCollection<Document> notificacaoCollection = conexao.getDatabase().getCollection("notificacao");

        int newId = getMaxIdNotificacao();
        String dataContratacaoStr = notificacao.getDataLimite().toString();
        // cria doc
        Document doc = new Document()
                .append("idNotificacao",newId)
                .append("idProduto", notificacao.getIdProduto())
                .append("quantidade", notificacao.getQuantidade())
                .append("dataLimite", dataContratacaoStr);

        //Insere
        notificacaoCollection.insertOne(doc);
    }

    public int getMaxIdPersonalizacao() {
        MongoDatabase database = conexao.getDatabase();
        MongoCollection<Document> notificacaoCollection = database.getCollection("pedidoPersonalizacao");

        //Sort docs limit 1
        Document result = notificacaoCollection.find()
                .sort(Sorts.descending("idPedidoPersonalizacao"))
                .limit(1)
                .first();

        if (result != null) {
            return result.getInteger("idPedidoPersonalizacao", 0) + 1;
        } else {
            return 1; // default
        }
    }

    public void cadastrarPedidoPersonalizacao(PedidoPersonalizacao pedido) throws Exception{

        MongoCollection<Document> pedidoPersonalizacaoCollection = conexao.getDatabase().getCollection("pedidoPersonalizacao");

        int newId = getMaxIdPersonalizacao();
        Produto produto = encontraProduto(pedido.getIdProduto());

        if(produto.getQuantidadeEstoque() < pedido.getQuantidade()){
            throw new Exception("ERRO: Ao cadastrar pedido personalizacao");
        }
        String data = pedido.getDataEntrega().toString();
        //Cria doc
        Document doc = new Document()
                .append("idPedidoPersonalizacao",newId)
                .append("dataEntrega", data)
                .append("descricao", pedido.getDescricao())
                .append("valorPersonalizacao", pedido.getValorPersonalizacao())
                .append("idVeiculo", pedido.getIdVeiculo())
                .append("departamentoResponsavel", pedido.getDepartamentoResponsavel())
                .append("quantidadeProduto", pedido.getQuantidade())
                .append("idProduto", pedido.getIdProduto());

        atualizarQuantidadeEstoque(pedido.getIdProduto(),pedido.getQuantidade() * -1);
        // Insere
        pedidoPersonalizacaoCollection.insertOne(doc);
    }


    public int getMaxIdProduto() {
        MongoDatabase database = conexao.getDatabase();
        MongoCollection<Document> produtoCollection = database.getCollection("produto");

        // Sort docs by id produto
        Document result = produtoCollection.find()
                .sort(Sorts.descending("idProduto"))
                .limit(1)
                .first();

        if (result != null) {
            return result.getInteger("idProduto", 0) + 1;
        } else {
            return 1; // default
        }
    }

    public void cadastrarProduto(Produto produto) {

        MongoCollection<Document> produtoCollection = conexao.getDatabase().getCollection("produto");

        int newId = getMaxIdProduto();
        // Cria doc
        Document doc = new Document()

                .append("idProduto",newId)
                .append("nome", produto.getNome())
                .append("precoPorUnidade", produto.getPrecoPorUnidade())
                .append("quantidadeEstoque", produto.getQuantidadeEstoque())
                .append("fornecedorPrincipal", produto.getFornecedorPrincipal())
                .append("tipo", String.valueOf(produto.getTipo()))
                .append("quantidadeMinima", produto.getQuantidadeMinima())
                .append("descricao", produto.getDescricao());

        // Inseere
        produtoCollection.insertOne(doc);
    }

    public int getMaxIdVeiculo() {
        MongoDatabase database = conexao.getDatabase();
        MongoCollection<Document> veiculoCollection = database.getCollection("veiculo");

        // Sort docs desc limit 1
        Document result = veiculoCollection.find()
                .sort(Sorts.descending("idVeiculo"))
                .limit(1)
                .first();

        if (result != null) {
            return result.getInteger("idVeiculo", 0) + 1;
        } else {
            return 1; // default
        }
    }

    public void cadastrarVeiculo(Veiculo veiculo) {

        MongoCollection<Document> veiculoCollection = conexao.getDatabase().getCollection("veiculo");

        int newId = getMaxIdVeiculo();
        //Cria doc
        Document doc = new Document()
                .append("idVeiculo",newId)
                .append("modelo", veiculo.getModelo())
                .append("ano", veiculo.getAno())
                .append("marca", veiculo.getMarca())
                .append("idDono", veiculo.getIdDono());

        // Insere
        veiculoCollection.insertOne(doc);
    }

    public int getMaxIdDepartamento() {
        MongoDatabase database = conexao.getDatabase();
        MongoCollection<Document> departamentoCollection = database.getCollection("departamento");

        // Sort docs desc limit 1
        Document result = departamentoCollection.find()
                .sort(Sorts.descending("idDepartamento"))
                .limit(1)
                .first();

        if (result != null) {
            return result.getInteger("idDepartamento", 0) + 1;
        } else {
            return 1; // default
        }
    }

    public void cadastrarDepartamento(Departamento departamento) {

        MongoCollection<Document> departamentoCollection = conexao.getDatabase().getCollection("departamento");

        // Pega novo id
        int idDepartamento = getMaxIdDepartamento();

        // Cria doc
        Document doc = new Document()
                .append("idDepartamento", idDepartamento)
                .append("nome", departamento.getNome())
                .append("tipoVeiculo", departamento.getTipoVeiculo())
                .append("tipo", String.valueOf(departamento.getTipo()));

        //Insere
        departamentoCollection.insertOne(doc);
    }


    //**************************************************************
    //Ultilidade
    public int countDepartamentos() {
        MongoCollection<Document> departamentoCollection = conexao.getDatabase().getCollection("departamento");

        // Conta deparamentos
        long count = departamentoCollection.countDocuments();

        return (int) count;
    }

    //**************************************************************
    //Consultas

    public Funcionario selectLogin(String cpf) {
        MongoDatabase database = conexao.getDatabase();
        MongoCollection<Document> funcionarioCollection = database.getCollection("funcionario");

        // Nova query com cpf que bate
        Document query = new Document("cpf", cpf);

        // Executa e encotnra o primeiro documento
        Document doc = funcionarioCollection.find(query).first();

        Funcionario funcionario = null;

        if (doc != null) {
            funcionario = new Funcionario();
            funcionario.setIdFuncionario(doc.getInteger("idFuncionario"));
            funcionario.setNome(doc.getString("nome"));
            funcionario.setCpf(doc.getString("cpf"));
            funcionario.setTelefone(doc.getString("telefone"));
            funcionario.setIdDepartamento(doc.getInteger("idDepartamento"));
            funcionario.setTipo(doc.getString("tipo").charAt(0));

            // Parse dataContratacao from string to Date
            try {
                Date dataContratacaoUtilDate = parseStringToDate(doc.getString("dataContratacao"));
                // Convert java.util.Date to LocalDate
                LocalDate dataContratacao = dataContratacaoUtilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                funcionario.setDataContratacao(dataContratacao);
            } catch (ParseException e) {
                // Handle parsing exception if necessary
                System.err.println("Error parsing dataContratacao: " + e.getMessage());
            }
        }

        return funcionario;
    }

    public List<Departamento> selectDepartamento() {
        MongoCollection<Document> departamentoCollection = conexao.getDatabase().getCollection("departamento");

        // Puxa todos os documentos
        List<Departamento> departamentos = new ArrayList<>();
        for (Document doc : departamentoCollection.find()) {
            Departamento departamento = new Departamento();
            departamento.setIdDep(doc.getInteger("idDepartamento"));
            departamento.setNome(doc.getString("nome"));
            departamento.setTipoVeiculo(doc.getString("tipoVeiculo"));
            departamento.setTipo(doc.getString("tipo").charAt(0));
            departamentos.add(departamento);
        }

        return departamentos;
    }

    public List<PedidoCompra> selectPedidoCompra() {

        MongoCollection<Document> pedidoCompraCollection = conexao.getDatabase().getCollection("pedidoCompra");

        // Puxa todos os documentos
        List<PedidoCompra> pedidosCompra = new ArrayList<>();
        for (Document doc : pedidoCompraCollection.find()) {
            PedidoCompra pedidoCompra = new PedidoCompra();
            pedidoCompra.setIdPedidoCompra(doc.getInteger("idPedidoCompra"));
            pedidoCompra.setIdFornecedor(doc.getInteger("idFornecedor"));
            pedidoCompra.setIdNotificacao(doc.getInteger("idNotificacao"));
            pedidoCompra.setQuantidade(doc.getInteger("quantidade"));
            pedidoCompra.setTotalCompra(doc.getDouble("totalCompra"));
            pedidoCompra.setIdProduto(doc.getInteger("idProduto"));
            pedidoCompra.setEntregue(doc.getBoolean("entregue"));
            pedidosCompra.add(pedidoCompra);
        }

        return pedidosCompra;
    }

    public List<Cliente> selectClientes() {

        MongoCollection<Document> clienteCollection = conexao.getDatabase().getCollection("cliente");

        // Puxa todos os documentos
        List<Cliente> clientes = new ArrayList<>();
        for (Document doc : clienteCollection.find()) {
            Cliente cliente = new Cliente();
            cliente.setIdCliente(doc.getInteger("idCliente"));
            cliente.setNome(doc.getString("nome"));
            cliente.setCpf(doc.getString("cpf"));
            cliente.setTelefone(doc.getString("telefone"));
            clientes.add(cliente);
        }

        return clientes;
    }

    public List<Veiculo> selectVeiculos() {

        MongoCollection<Document> veiculoCollection = conexao.getDatabase().getCollection("veiculo");

        // Puxa todos os documentos
        List<Veiculo> veiculos = new ArrayList<>();
        for (Document doc : veiculoCollection.find()) {
            Veiculo veiculo = new Veiculo();
            veiculo.setIdVeiculo(doc.getInteger("idVeiculo"));
            veiculo.setModelo(doc.getString("modelo"));
            veiculo.setAno(doc.getString("ano"));
            veiculo.setMarca(doc.getString("marca"));
            veiculo.setIdDono(doc.getInteger("idDono"));
            veiculos.add(veiculo);
        }

        return veiculos;
    }

    public List<Produto> selectProdutos() {

        MongoCollection<Document> produtoCollection = conexao.getDatabase().getCollection("produto");

        // Puxa todos os documentos
        List<Produto> produtos = new ArrayList<>();
        for (Document doc : produtoCollection.find()) {
            Produto produto = new Produto();
            produto.setIdProduto(doc.getInteger("idProduto"));
            produto.setNome(doc.getString("nome"));
            produto.setPrecoPorUnidade(doc.getDouble("precoPorUnidade"));
            produto.setQuantidadeEstoque(doc.getInteger("quantidadeEstoque"));
            produto.setFornecedorPrincipal(doc.getInteger("fornecedorPrincipal"));
            produto.setTipo(doc.getString("tipo").charAt(0));
            produto.setQuantidadeMinima(doc.getInteger("quantidadeMinima"));
            produto.setDescricao(doc.getString("descricao"));
            produtos.add(produto);
        }

        return produtos;
    }
    public List<PedidoPersonalizacao> selectPedidosPersonalizacao() {
        MongoCollection<Document> pedidoPersonalizacaoCollection = conexao.getDatabase().getCollection("pedidoPersonalizacao");

        List<PedidoPersonalizacao> pedidosPersonalizacao = new ArrayList<>();
        // Puxa todos os documentos
        for (Document doc : pedidoPersonalizacaoCollection.find()) {
            PedidoPersonalizacao pedido = new PedidoPersonalizacao();
            pedido.setIdPedido(doc.getInteger("idPedidoPersonalizacao"));
            pedido.setDescricao(doc.getString("descricao"));
            pedido.setValorPersonalizacao(doc.getDouble("valorPersonalizacao"));
            pedido.setIdVeiculo(doc.getInteger("idVeiculo"));
            pedido.setDepartamentoResponsavel(doc.getInteger("departamentoResponsavel"));
            pedido.setQuantidade(doc.getInteger("quantidadeProduto"));
            pedido.setIdProduto(doc.getInteger("idProduto"));

            // Try catch parser data
            try {
                String dataEntregaStr = doc.getString("dataEntrega");
                java.util.Date dateEntrega = parseStringToDate(dataEntregaStr);
                LocalDate dataEntregaLocalDate = dateEntrega.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                pedido.setDataEntrega(dataEntregaLocalDate);
            } catch (ParseException | NullPointerException e) {

                System.err.println("Error parsing dataEntrega: " + e.getMessage());

            }

            pedidosPersonalizacao.add(pedido);
        }

        return pedidosPersonalizacao;
    }
    public List<Funcionario> selectFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();
        MongoDatabase database = conexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("funcionario");

        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Funcionario funcionario = new Funcionario();
                funcionario.setIdFuncionario(doc.getInteger("idFuncionario"));
                funcionario.setNome(doc.getString("nome"));
                funcionario.setCpf(doc.getString("cpf"));
                funcionario.setTelefone(doc.getString("telefone"));

                // Converte string
                try {
                    Date dataContratacao = parseStringToDate(doc.getString("dataContratacao"));
                    funcionario.setDataContratacao(dataContratacao.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                } catch (ParseException e) {
                    System.out.println("Error parsing date for funcionario with id: " + doc.getInteger("idFuncionario"));
                    e.printStackTrace();
                    continue; //Pula
                }

                funcionario.setIdDepartamento(doc.getInteger("idDepartamento"));
                funcionario.setTipo(doc.getString("tipo").charAt(0));

                funcionarios.add(funcionario);
            }
        } finally {
            cursor.close();
        }

        return funcionarios;
    }

    public List<Notificacao> selectNotificacoes() {
        List<Notificacao> notificacoes = new ArrayList<>();
        MongoDatabase database = conexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("notificacao");
        //TODO: tIRAR
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Notificacao notificacao = new Notificacao();
                notificacao.setIdProduto(doc.getInteger("idProduto"));
                notificacao.setIdNotificao(doc.getInteger("idNotificacao"));
                notificacao.setQuantidade(doc.getInteger("quantidade"));
                try {
                    java.util.Date dateLimite = parseStringToDate(doc.getString("dataLimite"));
                    LocalDate dataLimiteLocalDate = dateLimite.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    notificacao.setDataLimite(dataLimiteLocalDate);
                } catch (Exception e) {
                    System.err.println("Error parsing dataLimite: " + e.getMessage());
                }

                notificacoes.add(notificacao);
            }
        }catch (Exception e){
            System.out.println("ERRO SELECT NOTIFICACAO: " + e.getMessage());
        }
        finally {
            cursor.close();
        }

        return notificacoes;
    }

    public List<Fornecedor> selectFornecedores() {
        List<Fornecedor> fornecedores = new ArrayList<>();
        MongoDatabase database = conexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("fornecedor");

        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setIdFornecedor(doc.getInteger("idFornecedor"));
                fornecedor.setEndereco(doc.getString("endereco"));
                fornecedor.setNome(doc.getString("nome"));
                fornecedor.setTelefone(doc.getString("telefone"));

                fornecedores.add(fornecedor);
            }
        } finally {
            cursor.close();
        }

        return fornecedores;
    }

    //**************************************************************
    //Deltar
    public void deletarPedidoCompra(int idPedidoCompra) {
        MongoDatabase database = conexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("pedidoCompra");

        // Cria filtro
        Document filter = new Document("idPedidoCompra", idPedidoCompra);

        //Deleta
        collection.deleteOne(filter);
    }
    public void deletaProduto(int idProduto) {
        MongoDatabase database = conexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("produto");

        // Cria filtro
        Document query = new Document("idProduto", idProduto);

        // Deleta
        collection.deleteMany(query);
    }

    public void deletaDepartamento(int idDepartamento) {
        MongoDatabase database = conexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("departamento");

        // Cria filtroo
        Document query = new Document("idDepartamento", idDepartamento);

        // Deleta
        collection.deleteMany(query);
    }

    public void deletaCliente(int idCliente) {
        MongoDatabase database = conexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("cliente");

        // Cria filtro
        Document query = new Document("idCliente", idCliente);

        // Deleta
        collection.deleteMany(query);
    }

    public void deletaFornecedor(int idFornecedor) {
        MongoDatabase database = conexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("fornecedor");

        // Cria filtro
        Document query = new Document("idFornecedor", idFornecedor);

        // Deleta
        collection.deleteMany(query);
    }

    public void deletaFuncionario(int idFuncionario) {
        MongoDatabase database = conexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("funcionario");

        // Cria filtro
        Document query = new Document("idFuncionario", idFuncionario);

        // Deleta
        collection.deleteMany(query);
    }


    public void deletaNotificacao(int idNotificacao) {
        MongoDatabase database = conexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("notificacao");

        // Cria filtro
        Document query = new Document("idNotificacao", idNotificacao);

        // Deleta
        collection.deleteMany(query);
    }


    public void deletaPedidoPersonalizacao(int idPedido) {
        MongoDatabase database = conexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("pedidoPersonalizacao");

        // Cria filtro
        Document query = new Document("idPedidoPersonalizacao", idPedido);

        // Deleta
        collection.deleteMany(query);
    }

    public void deletaVeiculo(int idVeiculo) {
        MongoDatabase database = conexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("veiculo");

        // Cria filtro
        Document query = new Document("idVeiculo", idVeiculo);

        // Deleta
        collection.deleteMany(query);
    }

    public double totalPedidosCompra() {
        MongoDatabase database = conexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("pedidoCompra");

        // Cria pipeline agreggate
        Document groupStage = new Document("$group", new Document("_id", null)
                .append("total", new Document("$sum", "$totalCompra")));

        // Executa
        Document result = collection.aggregate(Collections.singletonList(groupStage)).first();

        // Puxa resultado
        double total = result != null ? result.getDouble("total") : 0.0;

        return total;
    }

    public double totalPedidiosPersonalizacao() {
        MongoDatabase database = conexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("pedidoPersonalizacao");

        // Cria pipeline agreggate
        Document groupStage = new Document("$group", new Document("_id", null).append("total", new Document("$sum", "$valorPersonalizacao")));
        Document projectStage = new Document("$project", new Document("_id", 0).append("total", 1));

        // Executa
        Document result = collection.aggregate(Collections.singletonList(groupStage))
                .first();

        // Puxa resultado
        double total = result != null ? result.getDouble("total") : 0.0;

        return total;
    }

    public void confirmarEntrega(int idPedido) {
        MongoDatabase database = conexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("pedidoCompra");


        Document query = new Document("idPedidoCompra", idPedido);
        Document update = new Document("$set", new Document("entregue", true)); // Set "entregue" field para true

        // Executa updatae
        UpdateResult result = collection.updateOne(query, update);
        PedidoCompra pedido = enoncotraPedidoCompra(idPedido);
        atualizarQuantidadeEstoque(pedido.getIdProduto(),pedido.getQuantidade());
        if (result.getModifiedCount() > 0) {
            System.out.println("Delivery confirmed for Pedido with id " + idPedido);
        } else {
            System.out.println("Pedido with id " + idPedido + " not found or not updated.");
        }

    }
    /*
    public void removeQuantidadeEstoque(Connection con) throws SQLException {
        // Preparar a chamada da função PL/pgSQL usando uma instrução SQL simples
        //String sql = "SELECT remover_quantidade_estoque_pedido_personalizacao()";
        /*
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.execute();
        }


    }
    */


    public void atualizaNotificacoes(String data) {
        List<Produto> produtos = selectProdutos();

        for(Produto p : produtos){
            if(p.getQuantidadeEstoque() < p.getQuantidadeMinima()){
                Notificacao newNotificacao = new Notificacao();
                try {
                    newNotificacao.setDataLimite(parseStringToLocalDate(data));
                    newNotificacao.setIdProduto(p.getIdProduto());
                    newNotificacao.setQuantidade(p.getQuantidadeMinima() - p.getQuantidadeEstoque());
                    cadastrarNotificacao(newNotificacao);
                }catch (Exception e){
                    System.out.println("ERRO: Atualiza notificacao " + e.getMessage());
                }

            }
        }
    }
    /*
    public void atualizaNotificacoes() {
        MongoDatabase database = conexao.getDatabase(); // Get the MongoDB database from your connection class
        MongoCollection<Document> produtosCollection = database.getCollection("produto");
        MongoCollection<Document> notificacoesCollection = database.getCollection("notificacao");

        // Retrieve all products from MongoDB
        List<Document> produtos = produtosCollection.find().into(new ArrayList<>());

        for (Document produto : produtos) {
            int idProduto = produto.getInteger("idProduto");
            int quantidadeEstoque = produto.getInteger("quantidadeEstoque");
            int quantidadeMinima = produto.getInteger("quantidadeMinima");

            if (quantidadeEstoque < quantidadeMinima) {
                // Calculate notification details
                int quantidadeNotificacao = quantidadeMinima - quantidadeEstoque;
                LocalDate dataLimite = LocalDate.now().plusDays(7);

                // Check if notification exists for this product
                Document existingNotification = notificacoesCollection.find(Filters.eq("idProduto", idProduto)).first();

                if (existingNotification == null) {
                    // Insert new notification
                    Document newNotification = new Document()
                            .append("idProduto", idProduto)
                            .append("quantidade", quantidadeNotificacao)
                            .append("dataLimite", dataLimite.toString());
                    notificacoesCollection.insertOne(newNotification);
                } else {
                    // Update existing notification
                    Document updatedNotification = new Document()
                            .append("$set", new Document()
                                    .append("quantidade", quantidadeNotificacao)
                                    .append("dataLimite", dataLimite.toString()));
                    notificacoesCollection.updateOne(Filters.eq("idProduto", idProduto), updatedNotification);
                }
            } else {
                // If stock is sufficient, remove existing notifications
                notificacoesCollection.deleteMany(Filters.eq("idProduto", idProduto));
            }
        }
        */
    public void atualizarQuantidadeEstoque(int idProduto, int quantidade) {

        MongoCollection<Document> produtoCollection = conexao.getDatabase().getCollection("produto");
        Bson filter = Filters.eq("idProduto", idProduto);

        Bson update = Updates.inc("quantidadeEstoque", quantidade);
        UpdateResult result = produtoCollection.updateOne(filter, update);

        // Optionally, you can check the result if needed
        if (result.getModifiedCount() > 0) {
            System.out.println("Quantidade de estoque atualizada com sucesso para o produto com id " + idProduto);
        } else {
            System.out.println("Produto com id " + idProduto + " não encontrado. Nenhuma atualização feita.");
        }
    }

    public PedidoCompra enoncotraPedidoCompra(int idPedidoCompra) {

        MongoCollection<Document> pedidoCompraCollection = conexao.getDatabase().getCollection("pedidoCompra");

        Document query = new Document("idPedidoCompra", idPedidoCompra);

        FindIterable<Document> iterable = pedidoCompraCollection.find(query);

        Document doc = iterable.first();
        if (doc != null) {
            // Map o doc  para PedidoCompra
            PedidoCompra pedidoCompra = new PedidoCompra();
            System.out.println("Teste");
            pedidoCompra.setIdPedidoCompra(doc.getInteger("idPedidoCompra"));
            pedidoCompra.setIdFornecedor(doc.getInteger("idFornecedor"));
            pedidoCompra.setIdNotificacao(doc.getInteger("idNotificacao"));
            pedidoCompra.setQuantidade(doc.getInteger("quantidade"));
            pedidoCompra.setIdProduto(doc.getInteger("idProduto"));
            pedidoCompra.setTotalCompra(doc.getDouble("totalCompra"));
            pedidoCompra.setEntregue(doc.getBoolean("entregue"));

            return pedidoCompra;
        }

        return null;
    }

    public Produto encontraProduto(int idProduto) {
        MongoCollection<Document> produtoCollection = conexao.getDatabase().getCollection("produto");

        Document query = new Document("idProduto", idProduto);
        FindIterable<Document> iterable = produtoCollection.find(query);
        Document doc = iterable.first();

        if (doc != null) {
            Produto produto = new Produto();
            produto.setIdProduto(doc.getInteger("idProduto"));
            produto.setNome(doc.getString("nome"));
            produto.setPrecoPorUnidade(doc.getDouble("precoPorUnidade"));
            produto.setQuantidadeEstoque(doc.getInteger("quantidadeEstoque"));
            produto.setFornecedorPrincipal(doc.getInteger("fornecedorPrincipal"));
            produto.setTipo(doc.getString("tipo").charAt(0)); 
            produto.setQuantidadeMinima(doc.getInteger("quantidadeMinima"));
            produto.setDescricao(doc.getString("descricao"));

            return produto;
        }

        return null;
    }


}










