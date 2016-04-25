package core;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.bson.BSON;
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

    public static final String ID = "_id";
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
    public static final String VALIDATE = "validate";
    public static final String INAVLIDATE = "invalidate";
    public static final String NAME = "name";
    public static final String MOBILE = "mobile";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String IMAGE = "image";

    private DNADelegateCrawler callback;
    private org.bson.Document document;

    public DNADocumentCrawler(String url) {
        document = new org.bson.Document();
        document.append(URL, url);
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
        String name = document.getObjectId(ID).toString();
        String file = DNAFile.storage + File.separator + name + ".json";
        File docFile = new File(file);
        if (docFile.exists() == true) {
            docFile.delete();
        }
        DNAFile.writeStringToFile(document.toJson(), file);
    }

    public void insertDocument(MongoCollection<org.bson.Document> mongoCollection) {
        org.bson.Document findDocument = new org.bson.Document();
        findDocument.append(URL, document.getString(URL));
        FindIterable<org.bson.Document> documentFindIterable = mongoCollection.find(findDocument);
        org.bson.Document resultDocument = documentFindIterable.first();
        if (resultDocument != null) {
            mongoCollection.updateOne(resultDocument, new org.bson.Document("$set", document));
            DNADebug.log(0, "UPDATED", document.getString(URL));
        } else {
            mongoCollection.insertOne(document);
            DNADebug.log(0, "INSERTED", document.getString(URL));
        }
    }

    public void put(String key, String value) {
        document.append(key, value);
    }

    public void put(String key, org.bson.Document value) {
        document.append(key, value);
    }

    public org.bson.Document getDocument() {
        return document;
    }

    public void printDocument() {
        DNADebug.log(0, ID, document.getObjectId(ID).toString());
        DNADebug.log(1, URL, document.getString(URL));
        DNADebug.log(1, VALIDATE, document.getString(VALIDATE));
        DNADebug.log(1, INAVLIDATE, document.getString(INAVLIDATE));
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
        DNADebug.log(1, NAME, document.getString(NAME));
        DNADebug.log(1, MOBILE, document.getString(MOBILE));
        DNADebug.log(1, MOBILE, document.getString(PHONE));
        DNADebug.log(1, EMAIL, document.getString(EMAIL));
        DNADebug.log(1, IMAGE, document.get(IMAGE).toString());
    }

}
