import core.DNAWebCrawler;
import utils.DNADate;
import utils.DNAFile;

/**
 * Created by duong on 3/27/16.
 */
public class DNAMain {

    public static void main(String[] args) {
        DNADate.getInstance();
        DNAFile.createStorageFolder();
        DNAWebCrawler crawler = new DNAWebCrawler();
        crawler.execute();
    }

}
