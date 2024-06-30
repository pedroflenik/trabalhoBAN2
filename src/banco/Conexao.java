package banco;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class Conexao {
    private MongoClient mongoClient;
    private MongoDatabase database;

    public Conexao() {
        String connectionString = "mongodb://localhost:27017/personalizacaoveiculos";

        mongoClient = MongoClients.create(connectionString);

        database = mongoClient.getDatabase("personalizacaoveiculos");
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }


    public void closeConnection() {
        mongoClient.close();
    }

}
