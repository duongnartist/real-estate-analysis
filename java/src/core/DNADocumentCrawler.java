package core;

import jdk.nashorn.internal.runtime.JSONFunctions;
import org.bson.BSON;
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
public class DNADocumentCrawler {

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
    private org.bson.Document document;

    public DNADocumentCrawler(String url, String date) {
        document = new org.bson.Document();
        document.append(URL, url);
        String timeString = "";
        Timestamp timestamp = DNATime.convertStringToTimestamp(date);
        if (timestamp != null) {
            long time = timestamp.getTime();
            timeString += DNATime.secondsInMilliseconds(time);
        }
        document.append(DATE, timeString);
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
        String url = document.getString(URL);
        String name = url.substring(url.lastIndexOf("-") + 1, url.length());
        String file = DNAFile.storage + File.separator + name + ".json";
        File docFile = new File(file);
        if (docFile.exists() == true) {
            docFile.delete();
        }
        DNAFile.writeStringToFile(toString(), file);
    }

    public void put(String key, String value) {
        document.append(key, value);
    }

    public org.bson.Document getDocument() {
        return document;
    }

    public void printDocument() {
        DNADebug.log(0, URL, document.getString(URL));
        DNADebug.log(1, DATE, document.getString(DATE));
        DNADebug.log(1, TITLE, document.getString(TITLE));
        DNADebug.log(1, STREET, document.getString(STREET));
        DNADebug.log(1, WARD, document.getString(WARD));
        DNADebug.log(1, DISTRICT, document.getString(DISTRICT));
        DNADebug.log(1, CITY, document.getString(CITY));
        DNADebug.log(1, PRICE, document.getString(PRICE));
        DNADebug.log(1, AREA, document.getString(AREA));
        DNADebug.log(1, TYPE, document.getString(TYPE));
        DNADebug.log(1, CATEGORY, document.getString(CATEGORY));
        DNADebug.log(1, PROJECT, document.getString(PROJECT));
        DNADebug.log(1, DIRECTION, document.getString(DIRECTION));
        DNADebug.log(1, BEDROOM, document.getString(BEDROOM));
    }

}
