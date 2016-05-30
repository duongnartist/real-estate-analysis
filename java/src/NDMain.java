import core.NDWebCrawler;
import utils.NDDate;
import utils.NDFile;

/**
 * Created by duong on 3/27/16.
 */
public class NDMain {

    public static void main(String[] args) {
        NDDate.getInstance();
        NDFile.createStorageFolder();
        NDWebCrawler crawler = new NDWebCrawler();
        crawler.execute();
    }

}
