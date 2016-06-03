import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.NDDebug;
import utils.NDFile;

import java.io.File;

/**
 * Created by duong on 6/1/16.
 */
public class NDTest {
    public static void main(String[] args) {
        String data = NDFile.readStringFromFile(NDFile.dir + File.separator + "res" + File.separator + "data.tmp");
//        NDDebug.prln(data);
        Document document = Jsoup.parse(data);
        Element body = document.body();
        Elements elements = body.select("option");
        org.bson.Document bson = new org.bson.Document();
        for (int i = 1; i < elements.size(); i++) {
            String text = elements.get(i).text();
            NDDebug.prln(text);
            org.bson.Document x = new org.bson.Document();
            x.put("district", text);
            bson.put((i - 1) + "", x);
        }
        NDDebug.prln(bson.toJson());
    }
}
