package crawler;

import core.NDBaseCrawler;
import core.NDDocumentCrawler;
import core.NDGroupCrawler;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.NDDebug;
import utils.NDTime;

import javax.print.Doc;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.function.IntBinaryOperator;

/**
 * Created by duong on 3/29/16.
 */
public class NDEnterpriseDictionariesCrawler extends NDBaseCrawler {

    public NDEnterpriseDictionariesCrawler(long sleepTime) {
        super(sleepTime, "enterprises");
        fetchGroups();
    }

    private void fetchGroups() {
        try {
            Document document = NDDocumentCrawler.getDocumentFromUrl("http://www.muabannhadat.vn/doanh-nghiep/cong-ty-moi-gioi-va-phan-phoi-bat-dong-san-1");
            Element bodyElement = document.body();
            NDDebug.prln(bodyElement.text());
            Elements listGroupElements = bodyElement.select("a.row.list-group-item");
            int count = 0;
            if (listGroupElements != null) {
                for (Element element: listGroupElements) {
                    String base = "http://www.muabannhadat.vn" + element.attr("href");
                    NDDebug.prln(base);
                    Document baseDoc = NDDocumentCrawler.getDocumentFromUrl(base);
                    Element baseBody = baseDoc.body();
                    Elements paginationElements = baseBody.select("ul.pagination");
                    String pageString = paginationElements.text();
                    pageString = pageString.replace("< ", "");
                    pageString = pageString.replace(" >", "");
                    int page = 0;
                    if (pageString.length() > 0) {
                        String[] pages = pageString.split(" ");
                        page = Integer.parseInt(pages[pages.length - 1]) - 1;
                    }
                    NDDebug.prln("page = " + page);
                    groups.add(new NDGroupCrawler(base, base + "?p=%s", 1, page, 1));
                    count++;
                    if (count == 11) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> getChildUrls(String parentUrl) {
        NDDebug.log(0, "GET CHILD", parentUrl);
        ArrayList<String> urls = new ArrayList<String>();
        try {
            Document document = NDDocumentCrawler.getDocumentFromUrl(parentUrl);
            Element bodyElement = document.body();
            Elements productElements = bodyElement.select("a.title-filter-link");
            if (productElements != null) {
                for (Element productElement : productElements) {
                    Elements aElements = productElement.getElementsByTag("a");
                    String href = "";
                    if (aElements != null) {
                        href = "http://www.muabannhadat.vn" + aElements.attr("href");
                    }
                    if (href.length() > 0) {
                        urls.add(href);
                        NDDebug.log(1, "GET", href);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urls;
    }

    @Override
    public NDDocumentCrawler getDocFromUrl(String url) {
        NDDocumentCrawler documentCrawler = new NDDocumentCrawler(url);
        try {
            Document document = NDDocumentCrawler.getDocumentFromUrl(url);
            Element bodyElement = document.body();
            //--------------------------------------------------------------------------------------------------------//
            Elements elements = bodyElement.select("img.img-responsive");
            if (elements != null) {
                String avatar = "http://www.muabannhadat.vn" + elements.first().attr("src");
                documentCrawler.getDocument().put("avatar", avatar);
            }
            elements = bodyElement.select("div.col-xs-12.business-detail-name");
            if (elements != null) {
                String name = elements.text().trim();
                documentCrawler.getDocument().put("name", name);
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("div.col-xs-12.business-detail-description.padding-top-custom-devive");
            if (elements != null) {
                String description = elements.text().trim();
                documentCrawler.getDocument().put("description", description);
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("a.btn.btn-call-contact.btn-action-showphone.btn-block");
            if (elements != null) {
                String phone = elements.text().trim().replace("xxx", elements.first().attr("data-phoneext")).replace("(click để xem)", "").replaceAll(" ", "").replace(".", "").replace("(", "").replace(")", "").replace("-", "").replace("/", "").replace("+84", "0");
                if (phone.length() >= 2) {
                    if (phone.charAt(0) == '8' && phone.charAt(1) == '4') {
                        phone = phone.replaceFirst("84", "0");
                    }
                    if (phone.length() >= 10) {
                        phone = phone.substring(0, 10);
                    }
                }
                documentCrawler.getDocument().put("phone", phone);
            }
            NDDebug.prln(documentCrawler.getDocument().getString("avatar"));
            NDDebug.prln(documentCrawler.getDocument().getString("name"));
            NDDebug.prln(documentCrawler.getDocument().getString("description"));
            NDDebug.prln(documentCrawler.getDocument().getString("phone"));
            documentCrawler.insertDocument(NDMongo.mongoCollection);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return documentCrawler;
    }

}
