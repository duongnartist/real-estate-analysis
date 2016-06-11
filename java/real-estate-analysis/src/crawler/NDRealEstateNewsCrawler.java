package crawler;

import core.NDDocumentCrawler;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by duongnartist on 6/11/16.
 */
public class NDRealEstateNewsCrawler {

    public static void main(String[] args) throws IOException {
        Document document = NDDocumentCrawler.getDocumentFromUrl("http://www.muabannhadat.vn/tin-tuc/tin-tuc-thi-truong");
    }

}
