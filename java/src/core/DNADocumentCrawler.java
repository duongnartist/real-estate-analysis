package core;

import jdk.nashorn.internal.runtime.JSONFunctions;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import utils.DNADebug;
import utils.DNAFile;
import utils.DNATime;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by duong on 3/29/16.
 */
public class DNADocumentCrawler extends JSONObject {

    public static final String URL = "url";
    public static final String TITLE = "title";
    public static final String STREET = "street";
    public static final String WARD = "ward";
    public static final String DISTRICT = "district";
    public static final String CITY = "city";
    public static final String PRICE = "price";
    public static final String AREA = "area";
    public static final String TYPE = "type";
    public static final String CATEGORY = "category";
    public static final String PROJECT = "project";
    public static final String DIRECTION = "direction";
    public static final String BEDROOM = "bedroom";
    public static final String DATE = "date";

    private DNADelegateCrawler callback;

    public DNADocumentCrawler(String url, String date) {
        put(URL, url);
        String timeString = "";
        Timestamp timestamp = DNATime.convertStringToTimestamp(date);
        if (timestamp != null) {
            long time = timestamp.getTime();
            timeString += DNATime.secondsInMilliseconds(time);
        }
        put(DATE, timeString);
    }

    public static Document getDocumentFromUrl(String url) throws IOException {
        return Jsoup.connect(url)
                .ignoreContentType(true)
                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                .referrer("http://www.google.com")
                .timeout(30000)
                .followRedirects(true)
                .get();
    }

    public void writeDocument() {
        String url = getString(URL);
        String name = url.substring(url.lastIndexOf("-") + 1, url.length());
        String file = DNAFile.storage + File.separator + name + ".json";
        File docFile = new File(file);
        if (docFile.exists() == true) {
            docFile.delete();
        }
        DNAFile.writeStringToFile(toString(), file);
    }

    public void printDocument() {
        DNADebug.log(0, URL, getString(URL));
        DNADebug.log(0, DATE, getString(DATE));
        DNADebug.log(1, TITLE, getString(TITLE));
        DNADebug.log(1, STREET, getString(STREET));
        DNADebug.log(1, WARD, getString(WARD));
        DNADebug.log(1, DISTRICT, getString(DISTRICT));
        DNADebug.log(1, CITY, getString(CITY));
        DNADebug.log(1, PRICE, getString(PRICE));
        DNADebug.log(1, AREA, getString(AREA));
        DNADebug.log(1, TYPE, getString(TYPE));
        DNADebug.log(1, CATEGORY, getString(CATEGORY));
        DNADebug.log(1, PROJECT, getString(PROJECT));
        DNADebug.log(1, DIRECTION, getString(DIRECTION));
        DNADebug.log(1, BEDROOM, getString(BEDROOM));
    }

}
