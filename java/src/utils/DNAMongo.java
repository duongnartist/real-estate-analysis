package utils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BSON;
import org.bson.Document;
import org.json.JSONObject;

import java.util.Set;

/**
 * Created by duong on 4/20/16.
 */
public class DNAMongo {

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> mongoCollection;

    public DNAMongo(String connectionString) {
        MongoClientURI mongoClientURI = new MongoClientURI(connectionString);
        mongoClient = new MongoClient(mongoClientURI);
        mongoDatabase = mongoClient.getDatabase(mongoClientURI.getDatabase());
    }

    public MongoCollection<Document> openCollection(String collectionName) {
        mongoCollection = mongoDatabase.getCollection(collectionName);
        return mongoCollection;
    }

    public void insertOne(Document document) {
        mongoCollection.insertOne(document);
    }

}