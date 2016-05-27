import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import core.NDWebCrawler;
import org.bson.Document;
import utils.NDDate;
import utils.NDDebug;
import utils.NDFile;
import utils.NDMongo;

import java.io.File;

import static java.util.Arrays.asList;

/**
 * Created by duong on 3/27/16.
 */
public class NDMain {

    public static void main(String[] args) {
        NDDate.getInstance();
        NDFile.createStorageFolder();
        NDWebCrawler crawler = new NDWebCrawler();
        crawler.execute();
//        deleteAllDocumentAndInsertFromFiles();
//        preProcessingDatabase();
    }

    public static void deleteAllDocumentAndInsertFromFiles() {
        NDMongo NDMongo = new NDMongo(utils.NDMongo.URI);
        NDMongo.openCollection(utils.NDMongo.INFORMATIONS).deleteMany(new Document());
        File file = new File(NDFile.storage);
        if (file != null && file.exists()) {
            File[] listFiles = file.listFiles();
            for (File currentFile: listFiles) {
                String path = currentFile.getAbsolutePath();
                if (path.contains(".json")) {
                    String content = NDFile.readStringFromFile(path);
                    Document document = Document.parse(content);
                    NDMongo.insertOne(document);
                }
            }
        }
    }

    public static void preProcessingDatabase() {
        NDMongo NDMongo = new NDMongo(utils.NDMongo.URI);
        NDMongo.openCollection(utils.NDMongo.INFORMATIONS);
        NDMongo.mongoCollection.findOneAndDelete(new Document("price", ""));
        int count = 0;
        for (Document doc : NDMongo.mongoCollection.find(new Document())) {
            if (doc.getString("price").length() == 0 || doc.getString("area").length() == 0) {
                NDMongo.mongoCollection.deleteOne(doc);
                count++;
                NDDebug.log(0, "DELETED " + count, doc.getString("title"));
            }
        }
        AggregateIterable<Document> iterable = NDMongo.mongoCollection.aggregate(asList(
                new Document("$group", new Document("_id","$title"))));
        //final int[] count = {0};
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document.getString("_id"));
                //count[0]++;
                for (Document doc : NDMongo.mongoCollection.find(new Document("title", document.getString("_id")))) {
                    if (doc.getString("price").length() == 0 || doc.getString("area").length() == 0) {
                        NDMongo.mongoCollection.deleteOne(doc);
                        //NDDebug.log(0, "DELETED " + count, doc.getString("title"));
                    }
                    NDDebug.log(1, "ITEM", doc.toJson());
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
//        System.out.println(count[0]);
//        AggregateIterable<Document> iterable = (AggregateIterable<Document>) );
    }

}
