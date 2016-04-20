package utils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BSON;
import org.bson.Document;

import java.util.Set;

/**
 * Created by duong on 4/20/16.
 */
public class DNAMongo {

    public static final String URI = "mongodb://duongnartist:123123@ds025180.mlab.com:25180/real_estate";
    public static final String INFORMATIONS = "informations";

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
