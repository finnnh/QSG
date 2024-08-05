package de.finnxd.qsg.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.UUID;

public class DatabaseConnector {
    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Document> playersCollection;
    private final String hostname = "localhost";
    private final int port = 27017;
    private final String databaseName = "QSG";

    String username = "";
    String password = "";

    public DatabaseConnector() {
        ConnectionString connectionString = new ConnectionString("mongodb://" + username + ":" + password + "@" + hostname + ":" + port + "/?authSource=admin");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        this.mongoClient = MongoClients.create(settings);;
        this.database = mongoClient.getDatabase(databaseName);
        this.playersCollection = database.getCollection("players");
    }

    public Document createPlayerIfNotExists(UUID uuid) {
        Document existingPlayer = playersCollection.find(Filters.eq("uuid", uuid.toString())).first();

        if (existingPlayer == null) {
            Document newPlayer = new Document("uuid", uuid.toString())
                    .append("kills", 0)
                    .append("deaths", 0)
                    .append("wins", 0);

            playersCollection.insertOne(newPlayer);
            return newPlayer;
        }
        return existingPlayer;
    }

    public void updatePlayer(Document document) {
        playersCollection.updateOne(Filters.eq("uuid", document.getString("uuid")), document);
    }

    public void close() {
        mongoClient.close();
    }
}
