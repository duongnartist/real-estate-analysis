package crawler;

import core.DNABaseCrawler;
import core.DNADocumentCrawler;
import core.DNAGroupCrawler;
import org.jsoup.Jsoup;
import utils.DNADebug;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.DNAMongo;
import utils.DNAString;
import utils.DNATime;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.zip.Deflater;

/**
 * Created by duong on 3/29/16.
 */
public class DNABatDongSanGroupCrawler extends DNABaseCrawler {

    public DNABatDongSanGroupCrawler(long sleepTime) {
        super(sleepTime, "bat_dong_san");
        fetchGroups();
    }

    private void fetchGroups() {
        try {
            String home = "http://batdongsan.com.vn";
            Document document = DNADocumentCrawler.getDocumentFromUrl(home);
            Element bodyElement = document.body();
            Elements categoryElements = bodyElement.select("li.lv0");
            if (categoryElements != null) {
                for (int i = 0; i < 3; i++) {
                    Element element = categoryElements.get(i);
                    String link = home + element.select("a").attr("href");
                    Document document1 = DNADocumentCrawler.getDocumentFromUrl(link);
                    Element bodyElement1 = document1.body();
                    Elements elements = bodyElement1.select("div.background-pager-right-controls");
                    if (elements != null) {
                        String text = elements.select("a").last().attr("href").split("/")[2].replace("p", "");
                        try {
                            int number = Integer.parseInt(text);
                            DNADebug.prln(link + " has " + number + " news");
                            groups.add(new DNAGroupCrawler(link, link + "/p%s/", 2, number, 1));
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
        DNADebug.log(0, "GET CHILD", parentUrl);
        ArrayList<String> urls = new ArrayList<String>();
        try {
            Document document = DNADocumentCrawler.getDocumentFromUrl(parentUrl);
            Element bodyElement = document.body();
            Elements productElements = bodyElement.select("div.p-title");
            if (productElements != null) {
                for (Element productElement : productElements) {
                    Elements aElements = productElement.getElementsByTag("a");
                    String href = "";
                    if (aElements != null) {
                        href = "http://batdongsan.com.vn" + aElements.attr("href");
                        Elements dateElements = productElement.select("div.floatright.mar-right-10");
                        if (dateElements != null) {
                            String date = dateElements.text().trim();
                            href += "/" + date;
                        }
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
        String id = "";
        String street = "";
        String ward = "";
        String district = "";
        String city = "";
        String price = "";
        String area = "";
        String type = "";
        String category = "";
        String project = "";
        String direction = "";
        String bedroom = "";
        String name = "";
        String mobile = "";
        String phone = "";
        String email = "";
        String validate = "";
        String invalidate = "";
        String image = "";
        String description = "";
        Elements elements = null;
        Element element = null;
        try {
            Document document = DNADocumentCrawler.getDocumentFromUrl(url);
            Element bodyElement = document.body();
            id = url.substring(url.length() - 8, url.length() - 1);
            //--------------------------------------------------------------------------------------------------------//
            Elements elements0 = bodyElement.select("div.pm-content");
            if (elements0 != null) {
                description = elements0.text().trim();
            }
            elements = bodyElement.select("div.kqchitiet");
            if (elements != null) {
                //----------------------------------------------------------------------------------------------------//
                Elements elements1 = elements.select("b");
                if (elements1 != null) {
                    elements1.remove();
                }
                //----------------------------------------------------------------------------------------------------//
                Elements elements2 = elements.select("span.diadiem-title");
                if (elements2 != null) {
                    String[] diaDiemTitleStrings = elements2.text().trim().split(" - ");
                    title = diaDiemTitleStrings[0].trim().toLowerCase();
                }
                //----------------------------------------------------------------------------------------------------//
                Elements elements3 = elements.select("span.gia-title");
                if (elements3 != null) {
                    if (elements3.size() > 1) {
                        price = elements3.first().text().trim();
                        if (price.contains("thỏa") || price.contains("thuận")) {
                            price = "0";
                        }
                        if (price.contains("-")) {
                            String[] prices = price.split("-");
                            if (prices.length == 2) {
                                price = prices[0].trim();
                            }
                        }
                        price = price.replaceAll(" ", "");
                        //----------------------------------------------------------------------------------------------------//
                        area = elements3.last().text().trim();
                        if (area.contains("không") || area.contains("xác") || area.contains("định")) {
                            area = "0";
                        }
                        if (area.contains("-")) {
                            String[] areas = area.split("-");
                            if (areas.length == 2) {
                                area = areas[0].trim();
                            }
                        }
                        area = area.replaceAll(" ", "");
                    }
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("div#divCatagoryOptions");
            if (elements != null) {
                elements = elements.select("li.advance-options.current");
                if (elements != null) {
                    type = elements.text().trim().substring(8);
                    category = title.split("tại")[0].replace(type, "").trim();
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("div#divCityOptions");
            if (elements != null) {
                elements = elements.select("li.advance-options.current");
                if (elements != null) {
                    city = elements.text().trim();
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("div#divDistrictOptions");
            if (elements != null) {
                elements = elements.select("li.advance-options.current");
                if (elements != null) {
                    district = elements.text().trim();
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("div#divWardOptions");
            if (elements != null) {
                elements = elements.select("li.advance-options.current");
                if (elements != null) {
                    ward = elements.text().trim();
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("div#divStreetOptions");
            if (elements != null) {
                elements = elements.select("li.advance-options.current");
                if (elements != null) {
                    street = elements.text().trim();
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("div#divBedRoomOptions");
            if (elements != null) {
                elements = elements.select("li.advance-options.current");
                if (elements != null) {
                    bedroom = elements.text().trim().replace("+", "");
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("div#divHomeDirectionOptions");
            if (elements != null) {
                elements = elements.select("li.advance-options.current");
                if (elements != null) {
                    direction = elements.text().trim();
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("div#divProjectOptions");
            if (elements != null) {
                elements = elements.select("li.advance-options.current");
                if (elements != null) {
                    project = elements.text().trim();
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("div#LeftMainContent__productDetail_contactName");
            if (elements != null) {
                elements = elements.select("div.right");
                if (elements != null) {
                    name = elements.text().trim();
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("div#LeftMainContent__productDetail_contactPhone");
            if (elements != null) {
                elements = elements.select("div.right");
                if (elements != null) {
                    phone = elements.text().trim();
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("div#LeftMainContent__productDetail_contactMobile");
            if (elements != null) {
                elements = elements.select("div.right");
                if (elements != null) {
                    mobile = elements.text().trim();
                    if (mobile.equals(phone)) {
                        phone = "";
                    }
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("div#LeftMainContent__productDetail_contactEmail");
            if (elements != null) {
                elements = elements.select("div.right");
                if (elements != null) {
                    email = elements.html();
                    int start = email.indexOf("(\"") + 2;
                    int end = email.indexOf("\")");
                    if (start >= 0 && end >= start) {
                        email = email.substring(start, end);
                        Document doc = Jsoup.parse(email);
                        email = doc.text();
                    }
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("div.left-detail");
            if (elements != null) {
                Elements leftElements = elements.select("div.left");
                Elements rightElements = elements.select("div.right");
                if (leftElements != null && rightElements != null) {
                    for (int i = 0; i < leftElements.size(); i++) {
                        String left = leftElements.get(i).text().trim().toLowerCase();
                        if (left.contains("ngày đăng tin")) {
                            validate = rightElements.get(i).text().trim();
                            Timestamp timestamp = DNATime.convertStringToTimestamp(validate, "dd-MM-yyyy");
                            if (timestamp != null) {
                                long time = timestamp.getTime();
                                validate = DNATime.secondsInMilliseconds(time) + "";
                            }
                        }
                        if (left.contains("ngày hết hạn")) {
                            invalidate = rightElements.get(i).text().trim();
                            Timestamp timestamp = DNATime.convertStringToTimestamp(invalidate, "dd-MM-yyyy");
                            if (timestamp != null) {
                                long time = timestamp.getTime();
                                invalidate = DNATime.secondsInMilliseconds(time) + "";
                            }
                        }
                    }
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            org.bson.Document imageDocument = new org.bson.Document();
            elements = bodyElement.select("div.list-img");
            if (elements != null) {
                elements = elements.select("img");
                if (elements != null) {
                    int count = 0;
                    for (Element srcElement: elements) {
                        String imageUrl = srcElement.attr("src").replace("80x60", "745x510");
                        imageDocument.put("image_" + count, imageUrl);
                        count++;
                    }
                }
                if (image.length() > 0) {
                    image.substring(0, image.length() - 2);
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            documentCrawler.put(DNADocumentCrawler.ID, id);
            documentCrawler.put(DNADocumentCrawler.TITLE, title);
            documentCrawler.put(DNADocumentCrawler.STREET, street);
            documentCrawler.put(DNADocumentCrawler.WARD, ward);
            documentCrawler.put(DNADocumentCrawler.DISTRICT, district);
            documentCrawler.put(DNADocumentCrawler.CITY, city);
            documentCrawler.put(DNADocumentCrawler.PRICE, price);
            documentCrawler.put(DNADocumentCrawler.AREA, area);
            documentCrawler.put(DNADocumentCrawler.TYPE, type);
            documentCrawler.put(DNADocumentCrawler.CATEGORY, category);
            documentCrawler.put(DNADocumentCrawler.PROJECT, project);
            documentCrawler.put(DNADocumentCrawler.DIRECTION, direction);
            documentCrawler.put(DNADocumentCrawler.BEDROOM, bedroom);
            documentCrawler.put(DNADocumentCrawler.NAME, name);
            documentCrawler.put(DNADocumentCrawler.MOBILE, mobile);
            documentCrawler.put(DNADocumentCrawler.PHONE, phone);
            documentCrawler.put(DNADocumentCrawler.EMAIL, email);
            documentCrawler.put(DNADocumentCrawler.VALIDATE, validate);
            documentCrawler.put(DNADocumentCrawler.INAVLIDATE, invalidate);
            documentCrawler.put(DNADocumentCrawler.DESCRIPTION, description);
            documentCrawler.put(DNADocumentCrawler.IMAGE, imageDocument);
            documentCrawler.writeDocument(root);
            if (price != "0" && area != "0") {
//                documentCrawler.insertDocument(dnaMongo.mongoCollection);
//                documentCrawler.printDocument();
//                documentCrawler.writeDocument(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return documentCrawler;
    }

}
