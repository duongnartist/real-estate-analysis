package crawler;

import core.NDBaseCrawler;
import core.NDDocumentCrawler;
import core.NDGroupCrawler;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.NDDebug;
import utils.NDTime;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by duong on 3/29/16.
 */
public class NDMuaBanNhaDatGroupCrawler extends NDBaseCrawler {

    public NDMuaBanNhaDatGroupCrawler(long sleepTime) {
        super(sleepTime, "mua_ban_nha_dat");
        fetchGroups();
    }

    private void fetchGroups() {
        try {
            Document document = NDDocumentCrawler.getDocumentFromUrl("http://www.muabannhadat.vn/nha-dat-3490");
            Element bodyElement = document.body();
            Elements listGroupElements = bodyElement.select("div.list-group");
            if (listGroupElements != null) {
                Elements badgeCusspElements = listGroupElements.select("p.badge");
                Elements aElements = badgeCusspElements.select("a");
                if (aElements != null) {
                    for (int i = 0; i < 10; i++) {
                        Element element = aElements.get(i);
                        String root = "http://www.muabannhadat.vn/" + element.attr("href");
                        String numberString = element.text().replace(".", "").trim();
                        try {
                            int number = Integer.parseInt(numberString);
                            int page = Math.round((float) number / 10);
                            String homePage = root + "?p=0";
                            String formatPage = root + "?p=%s";
                            groups.add(new NDGroupCrawler(homePage, formatPage, 1, page, 1));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
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
        String id = "";
        String longitude = "";
        String latitude = "";
        String description = "";
        Elements elements = null;
        Element element = null;
        try {
            Document document = NDDocumentCrawler.getDocumentFromUrl(url);
            Element bodyElement = document.body();
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("h1.navi-title");
            if (elements != null) {
                title = elements.text().trim();
            }
            elements = bodyElement.select("div.box-description");
            if (elements != null) {
                description = elements.text().trim();
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("span#MainContent_ctlDetailBox_lblId");
            if (elements != null) {
                id = elements.text().trim();
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("span#MainContent_ctlDetailBox_lblMapLink");
            if (elements != null) {
                elements = elements.select("a");
                if (elements != null) {
                    String link = elements.attr("href");
                    if (link.length() > 0) {
                        String[] location = link.split(":")[2].split(",");
                        latitude = location[0];
                        longitude = location[1];
                    }
                }
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
                    environmentDocument.put(NDDocumentCrawler.ENVIRONMENT + "_" + i, environments[i].trim());
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            org.bson.Document utilityDocument = new org.bson.Document();
            elements = bodyElement.select("span#MainContent_ctlDetailBox_lblUtility");
            if (elements != null) {
                utility = elements.html();
                String[] utilities = utility.split("<br>");
                for (int i = 0; i < utilities.length; i++) {
                    utilityDocument.put(NDDocumentCrawler.UTILITY + "_" + i, utilities[i].trim());
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("span#MainContent_ctlDetailBox_lblDateCreated");
            if (elements != null) {
                dateCreated = elements.text().trim().replace(".", "-");
                Timestamp timestamp = NDTime.convertStringToTimestamp(dateCreated, "dd-MM-yyyy");
                if (timestamp != null) {
                    long time = timestamp.getTime();
                    dateCreated = NDTime.secondsInMilliseconds(time) + "";
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("span#MainContent_ctlDetailBox_lblDateUpdated");
            if (elements != null) {
                dateUpdated = elements.text().trim().replace(".", "-");
                Timestamp timestamp = NDTime.convertStringToTimestamp(dateUpdated, "dd-MM-yyyy");
                if (timestamp != null) {
                    long time = timestamp.getTime();
                    dateUpdated = NDTime.secondsInMilliseconds(time) + "";
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
                int count = 0;
                for (int i = 0; i < elements.size(); i++) {
                    element = elements.get(i);
                    String imageUrl = element.attr("href");
                    if (imageUrl.contains("http://www.muabannhadat.vn/uploads/images/")) {
                        imageDocument.put(NDDocumentCrawler.IMAGE + "_" + count++, imageUrl);
                    }
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            documentCrawler.put(NDDocumentCrawler.ID, id);
            documentCrawler.put(NDDocumentCrawler.TITLE, title);
            documentCrawler.put(NDDocumentCrawler.DESCRIPTION, description);
            documentCrawler.put(NDDocumentCrawler.LATITUDE, latitude);
            documentCrawler.put(NDDocumentCrawler.LONGITUDE, longitude);
            documentCrawler.put(NDDocumentCrawler.STREET, street);
            documentCrawler.put(NDDocumentCrawler.WARD, ward);
            documentCrawler.put(NDDocumentCrawler.DISTRICT, district);
            documentCrawler.put(NDDocumentCrawler.CITY, city);
            documentCrawler.put(NDDocumentCrawler.PRICE, price);
            documentCrawler.put(NDDocumentCrawler.PRICE_UNIT, priceUnit);
            documentCrawler.put(NDDocumentCrawler.AREA, area);
            documentCrawler.put(NDDocumentCrawler.AREA_UNIT, areaUnit);
            documentCrawler.put(NDDocumentCrawler.TYPE, type);
            documentCrawler.put(NDDocumentCrawler.CATEGORY, category);
            documentCrawler.put(NDDocumentCrawler.PROJECT, project);
            documentCrawler.put(NDDocumentCrawler.DIRECTION, direction);
            documentCrawler.put(NDDocumentCrawler.BEDROOM, bedroom);
            documentCrawler.put(NDDocumentCrawler.BATHROOM, bathroom);
            documentCrawler.put(NDDocumentCrawler.FLOOR, floor);
            documentCrawler.put(NDDocumentCrawler.ENVIRONMENT, environmentDocument);
            documentCrawler.put(NDDocumentCrawler.UTILITY, utilityDocument);
            documentCrawler.put(NDDocumentCrawler.NAME, name);
            documentCrawler.put(NDDocumentCrawler.MOBILE, mobile);
            documentCrawler.put(NDDocumentCrawler.PHONE, phone);
            documentCrawler.put(NDDocumentCrawler.EMAIL, email);
            documentCrawler.put(NDDocumentCrawler.VALIDATE, validate);
            documentCrawler.put(NDDocumentCrawler.INAVLIDATE, invalidate);
            documentCrawler.put(NDDocumentCrawler.DATE_CREATED, dateCreated);
            documentCrawler.put(NDDocumentCrawler.DATE_UPDATED, dateUpdated);
            documentCrawler.put(NDDocumentCrawler.IMAGE, imageDocument);
            documentCrawler.writeDocument(root);
//            documentCrawler.printDocument();
//            documentCrawler.insertDocument(NDMongo.mongoCollection);
            if (price != "0" && area != "0") {
//                documentCrawler.insertDocument(NDMongo.mongoCollection);
//                documentCrawler.printDocument();
//                documentCrawler.writeDocument();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return documentCrawler;
    }

}
