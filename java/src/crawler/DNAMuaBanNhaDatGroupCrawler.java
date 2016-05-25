package crawler;

import com.sun.javadoc.Doc;
import core.DNABaseCrawler;
import core.DNADocumentCrawler;
import core.DNAGroupCrawler;
import org.bson.BSON;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.DNADebug;
import utils.DNATime;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by duong on 3/29/16.
 */
public class DNAMuaBanNhaDatGroupCrawler extends DNABaseCrawler {

    public DNAMuaBanNhaDatGroupCrawler(long sleepTime) {
        super(sleepTime, "mua_ban_nha_dat");

        groups.add(new DNAGroupCrawler("http://www.muabannhadat.vn/nha-ban-3513?p=0", "http://www.muabannhadat.vn/nha-ban-3513?p=%s", 1, 4307, 1));
        groups.add(new DNAGroupCrawler("http://www.muabannhadat.vn/can-ho-ban-3514?p=0", "http://www.muabannhadat.vn/can-ho-ban-3514?p=%s", 1, 1595, 1));
        groups.add(new DNAGroupCrawler("http://www.muabannhadat.vn/dat-ban-3515?p=0", "http://www.muabannhadat.vn/dat-ban-3515?p=%s", 1, 2076, 1));
        groups.add(new DNAGroupCrawler("http://www.muabannhadat.vn/mat-bang-ban-3516?p=0", "http://www.muabannhadat.vn/mat-bang-ban-3516?p=%s", 1, 11, 1));

        groups.add(new DNAGroupCrawler("http://www.muabannhadat.vn/kho-xuong-ban-3517?p=0", "http://www.muabannhadat.vn/kho-xuong-ban-3517?p=%s", 1, 13, 1));
        groups.add(new DNAGroupCrawler("http://www.muabannhadat.vn/nha-cho-thue-3518?p=0", "http://www.muabannhadat.vn/nha-cho-thue-3518?p=%s", 1, 328, 1));
        groups.add(new DNAGroupCrawler("http://www.muabannhadat.vn/can-ho-cho-thue-3519?p=0", "http://www.muabannhadat.vn/can-ho-cho-thue-3519?p=%s", 1, 263, 1));
        groups.add(new DNAGroupCrawler("http://www.muabannhadat.vn/dat-cho-thue-3520?p=0", "http://www.muabannhadat.vn/dat-cho-thue-3520?p=%s", 1, 4, 1));

        groups.add(new DNAGroupCrawler("http://www.muabannhadat.vn/mat-bang-cho-thue-3521?p=0", "http://www.muabannhadat.vn/mat-bang-cho-thue-3521?p=%s", 1, 74, 1));
        groups.add(new DNAGroupCrawler("http://www.muabannhadat.vn/kho-xuong-cho-thue-3522?p=0", "http://www.muabannhadat.vn/kho-xuong-cho-thue-3522?p=%s", 1, 95, 1));
    }

    @Override
    public ArrayList<String> getChildUrls(String parentUrl) {
        DNADebug.log(0, "GET CHILD", parentUrl);
        ArrayList<String> urls = new ArrayList<String>();
        try {
            Document document = DNADocumentCrawler.getDocumentFromUrl(parentUrl);
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
                        DNADebug.log(1, "GET", href);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urls;
    }

    @Override
    public DNADocumentCrawler getDocFromUrl(String url) {
        DNADocumentCrawler documentCrawler = new DNADocumentCrawler(url);
        String title = "";
        String street = "";
        String ward = "";
        String district = "";
        String city = "";
        String price = "0";
        String priceUnit = "";
        String area = "0";
        String areaUnit = "";
        String type = "";
        String category = "";
        String project = "";
        String direction = "";
        String bedroom = "";
        String bathroom = "";
        String environment = "";
        String utility = "";
        String floor = "";
        String name = "";
        String mobile = "";
        String phone = "";
        String email = "";
        String validate = "";
        String invalidate = "";
        String dateCreated = "";
        String dateUpdated = "";
        String image = "";
        Elements elements = null;
        Element element = null;
        try {
            Document document = DNADocumentCrawler.getDocumentFromUrl(url);
            Element bodyElement = document.body();
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("h1.navi-title");
            if (elements != null) {
                title = elements.first().text().trim();
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("span#MainContent_ctlDetailBox_lblPrice");
            if (elements != null) {
                price = elements.text().trim();
                if (price.contains("Giá bảo mật")) {
                    price = "0";
                }
                int index = price.indexOf(" ");
                if (index >= 0) {
                    priceUnit = price.substring(index, price.length()).trim();
                    price = price.substring(0, index).trim();
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("span#MainContent_ctlDetailBox_lblSurface");
            if (elements != null) {
                area = elements.text().trim();
                int index = area.indexOf(" ");
                if (index >= 0) {
                    areaUnit = area.substring(index, area.length()).trim();
                    area = area.substring(0, index).trim();
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("span#MainContent_ctlDetailBox_lblFengShuiDirection");
            if (elements != null) {
                direction = elements.text().trim();
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("span#MainContent_ctlDetailBox_lblBedRoom");
            if (elements != null) {
                bedroom = elements.text().trim();
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("span#MainContent_ctlDetailBox_lblBathRoom");
            if (elements != null) {
                bathroom = elements.text().trim();
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("span#MainContent_ctlDetailBox_lblFloor");
            if (elements != null) {
                floor = elements.text().trim();
            }
            org.bson.Document environmentDocument = new org.bson.Document();
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("span#MainContent_ctlDetailBox_lblEnvironment");
            if (elements != null) {
                environment = elements.html();
                String[] environments = environment.split("<br>");
                for (int i = 0; i < environments.length; i++) {
                    environmentDocument.put(DNADocumentCrawler.ENVIRONMENT + "_" + i, environments[i].trim());
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            org.bson.Document utilityDocument = new org.bson.Document();
            elements = bodyElement.select("span#MainContent_ctlDetailBox_lblUtility");
            if (elements != null) {
                utility = elements.html();
                String[] utilities = utility.split("<br>");
                for (int i = 0; i < utilities.length; i++) {
                    utilityDocument.put(DNADocumentCrawler.UTILITY + "_" + i, utilities[i].trim());
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("span#MainContent_ctlDetailBox_lblDateCreated");
            if (elements != null) {
                dateCreated = elements.text().trim().replace(".", "-");
                Timestamp timestamp = DNATime.convertStringToTimestamp(dateCreated, "dd-MM-yyyy");
                if (timestamp != null) {
                    long time = timestamp.getTime();
                    dateCreated = DNATime.secondsInMilliseconds(time) + "";
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("span#MainContent_ctlDetailBox_lblDateUpdated");
            if (elements != null) {
                dateUpdated = elements.text().trim().replace(".", "-");
                Timestamp timestamp = DNATime.convertStringToTimestamp(dateUpdated, "dd-MM-yyyy");
                if (timestamp != null) {
                    long time = timestamp.getTime();
                    dateUpdated = DNATime.secondsInMilliseconds(time) + "";
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("span#MainContent_ctlDetailBox_lblStreet");
            if (elements != null) {
                street = elements.text().trim();
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("span#MainContent_ctlDetailBox_lblWard");
            if (elements != null) {
                ward = elements.text().trim();
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("span#MainContent_ctlDetailBox_lblDistrict");
            if (elements != null) {
                district = elements.text().trim();
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("span#MainContent_ctlDetailBox_lblCity");
            if (elements != null) {
                city = elements.text().trim();
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("span#MainContent_ctlDetailBox_lblProject");
            if (elements != null) {
                project = elements.text().trim();
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("span#MainContent_ctlDetailBox_lblContactName");
            if (elements != null) {
                name = elements.text().trim();
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("span#MainContent_ctlDetailBox_lblContactPhone");
            if (elements != null) {
                phone = elements.text().replace("xxx", "").replace("(click để xem)", "").trim();
                elements = bodyElement.select("div#Description");
                if (elements != null) {
                    String text = elements.text().replace(".", "").replace(" ", "").replace("-", "");
                    int index = text.indexOf(phone);
                    if (index >= 0 && text.length() >= index + phone.length() + 3) {
                        phone = text.substring(index, index + phone.length() + 3);
                    } else {
                        phone += "xxx";
                    }
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            org.bson.Document imageDocument = new org.bson.Document();
            elements = bodyElement.select("a");
            if (elements != null) {
                System.out.println(elements.text());
                int count = 0;
                for (int i = 0; i < elements.size(); i++) {
                    element = elements.get(i);
                    String imageUrl = element.attr("href");
                    if (imageUrl.contains("http://www.muabannhadat.vn/uploads/images/")) {
                        System.out.println(imageUrl);
                        imageDocument.put(DNADocumentCrawler.IMAGE + "_" + count++, imageUrl);
                    }
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            documentCrawler.put(DNADocumentCrawler.TITLE, title);
            documentCrawler.put(DNADocumentCrawler.STREET, street);
            documentCrawler.put(DNADocumentCrawler.WARD, ward);
            documentCrawler.put(DNADocumentCrawler.DISTRICT, district);
            documentCrawler.put(DNADocumentCrawler.CITY, city);
            documentCrawler.put(DNADocumentCrawler.PRICE, price);
            documentCrawler.put(DNADocumentCrawler.PRICE_UNIT, priceUnit);
            documentCrawler.put(DNADocumentCrawler.AREA, area);
            documentCrawler.put(DNADocumentCrawler.AREA_UNIT, areaUnit);
            documentCrawler.put(DNADocumentCrawler.TYPE, type);
            documentCrawler.put(DNADocumentCrawler.CATEGORY, category);
            documentCrawler.put(DNADocumentCrawler.PROJECT, project);
            documentCrawler.put(DNADocumentCrawler.DIRECTION, direction);
            documentCrawler.put(DNADocumentCrawler.BEDROOM, bedroom);
            documentCrawler.put(DNADocumentCrawler.BATHROOM, bathroom);
            documentCrawler.put(DNADocumentCrawler.FLOOR, floor);
            documentCrawler.put(DNADocumentCrawler.ENVIRONMENT, environmentDocument);
            documentCrawler.put(DNADocumentCrawler.UTILITY, utilityDocument);
            documentCrawler.put(DNADocumentCrawler.NAME, name);
            documentCrawler.put(DNADocumentCrawler.MOBILE, mobile);
            documentCrawler.put(DNADocumentCrawler.PHONE, phone);
            documentCrawler.put(DNADocumentCrawler.EMAIL, email);
            documentCrawler.put(DNADocumentCrawler.VALIDATE, validate);
            documentCrawler.put(DNADocumentCrawler.INAVLIDATE, invalidate);
            documentCrawler.put(DNADocumentCrawler.DATE_CREATED, dateCreated);
            documentCrawler.put(DNADocumentCrawler.DATE_UPDATED, dateUpdated);
            documentCrawler.put(DNADocumentCrawler.IMAGE, imageDocument);
            documentCrawler.printDocument();
            documentCrawler.writeDocument();
            documentCrawler.insertDocument(dnaMongo.mongoCollection);
            if (price != "0" && area != "0") {
//                documentCrawler.insertDocument(dnaMongo.mongoCollection);
//                documentCrawler.printDocument();
//                documentCrawler.writeDocument();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return documentCrawler;
    }

}
