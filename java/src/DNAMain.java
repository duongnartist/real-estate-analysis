import core.DNAWebCrawler;
import org.bson.BSON;
import org.bson.Document;
import utils.DNADate;
import utils.DNADebug;
import utils.DNAFile;
import utils.DNAMongo;

import javax.print.Doc;
import java.io.File;

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
}
