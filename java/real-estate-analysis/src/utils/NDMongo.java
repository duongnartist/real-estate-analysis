package utils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Created by duong on 4/20/16.
 */
public class NDMongo {

    public static final String URI = "mongodb://duongnartist:123123@ds025180.mlab.com:25180/real_estate";
    public static final String INFORMATIONS = "informations";
    public MongoCollection<Document> mongoCollection;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    public NDMongo(String connectionString) {
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
