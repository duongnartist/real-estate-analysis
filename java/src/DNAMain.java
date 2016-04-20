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
//        DNADate.getInstance();
//        DNAFile.createStorageFolder();
//        DNAWebCrawler crawler = new DNAWebCrawler();
//        crawler.execute();
        DNAMongo dnaMongo = new DNAMongo("mongodb://duongnartist:123123@ds025180.mlab.com:25180/real_estate");
        dnaMongo.openCollection("informations");
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
