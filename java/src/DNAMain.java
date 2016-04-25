import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import core.DNAWebCrawler;
import org.bson.BSON;
import org.bson.Document;
import utils.DNADate;
import utils.DNADebug;
import utils.DNAFile;
import utils.DNAMongo;

import javax.print.Doc;
import java.io.File;
import static java.util.Arrays.asList;

/**
 * Created by duong on 3/27/16.
 */
public class DNAMain {

    public static void main(String[] args) {
        DNADate.getInstance();
        DNAFile.createStorageFolder();
        DNAWebCrawler crawler = new DNAWebCrawler();
        crawler.execute();
//        deleteAllDocumentAndInsertFromFiles();
//        preProcessingDatabase();
    }

    public static void deleteAllDocumentAndInsertFromFiles() {
        DNAMongo dnaMongo = new DNAMongo(DNAMongo.URI);
        dnaMongo.openCollection(DNAMongo.INFORMATIONS).deleteMany(new Document());
        File file = new File(DNAFile.storage);
        if (file != null && file.exists()) {
            File[] listFiles = file.listFiles();
            for (File currentFile: listFiles) {
                String path = currentFile.getAbsolutePath();
                if (path.contains(".json")) {
                    String content = DNAFile.readStringFromFile(path);
                    Document document = Document.parse(content);
                    dnaMongo.insertOne(document);
                }
            }
        }
    }

    public static void preProcessingDatabase() {
        DNAMongo dnaMongo = new DNAMongo(DNAMongo.URI);
        dnaMongo.openCollection(DNAMongo.INFORMATIONS);
        dnaMongo.mongoCollection.findOneAndDelete(new Document("price",""));
        int count = 0;
        for (Document doc: dnaMongo.mongoCollection.find(new Document())) {
            if (doc.getString("price").length() == 0 || doc.getString("area").length() == 0) {
                dnaMongo.mongoCollection.deleteOne(doc);
                count++;
                DNADebug.log(0, "DELETED " + count, doc.getString("title"));
            }
        }
        AggregateIterable<Document> iterable = dnaMongo.mongoCollection.aggregate(asList(
                new Document("$group", new Document("_id","$title"))));
        //final int[] count = {0};
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document.getString("_id"));
                //count[0]++;
                for (Document doc: dnaMongo.mongoCollection.find(new Document("title",document.getString("_id")))) {
                    if (doc.getString("price").length() == 0 || doc.getString("area").length() == 0) {
                        dnaMongo.mongoCollection.deleteOne(doc);
                        //DNADebug.log(0, "DELETED " + count, doc.getString("title"));
                    }
                    DNADebug.log(1, "ITEM", doc.toJson());
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
