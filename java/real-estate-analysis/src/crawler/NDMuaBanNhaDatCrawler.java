package crawler;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.NDDebug;
import utils.NDMongo;
import utils.NDTime;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by duong on 6/12/16.
 */
public class NDMuaBanNhaDatCrawler {

    public static NDMongo mongo = new NDMongo(utils.NDMongo.URI);

    public static org.jsoup.nodes.Document getDocumentFromUrl(String url) throws IOException {
        return Jsoup.connect(url)
                .ignoreContentType(true)
                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                .referrer("http://www.google.com")
                .timeout(30000)
                .followRedirects(true)
                .get();
    }

    public static void main(String[] args) {
        NDMuaBanNhaDatCrawler.mongo.openCollection("mua_ban_nha_dat");
        ArrayList<NDGroup> groups = new ArrayList<>();
        groups.add(new NDGroup("Cần bán", "Nhà", "Biệt thự", "http://www.muabannhadat.vn/nha-ban-biet-thu-villa-3525", 1, 170, 1));
        groups.add(new NDGroup("Cần bán", "Nhà", "Nhà phố", "http://www.muabannhadat.vn/nha-ban-nha-pho-3535", 1, 3886, 1));
        groups.add(new NDGroup("Cần bán", "Nhà", "Nhà loại khác", "http://www.muabannhadat.vn/nha-ban-khac-3592", 1, 237, 1));

        groups.add(new NDGroup("Cần bán", "Căn hộ", "Chung cư", "http://www.muabannhadat.vn/can-ho-ban-chung-cu-3529", 1, 170, 1));
        groups.add(new NDGroup("Cần bán", "Căn hộ", "Chung cư cao cấp", "http://www.muabannhadat.vn/can-ho-ban-chung-cu-cao-cap-3530", 1, 561, 1));
        groups.add(new NDGroup("Cần bán", "Căn hộ", "Chung cư khác", "http://www.muabannhadat.vn/can-ho-ban-chung-cu-khac-3594", 1, 23, 1));

        groups.add(new NDGroup("Cần bán", "Đất", "Đất thổ cư", "http://www.muabannhadat.vn/dat-ban-dat-tho-cu-3532", 1, 1762, 1));
        groups.add(new NDGroup("Cần bán", "Đất", "Đất nông nghiệp", "http://www.muabannhadat.vn/dat-ban-dat-nong-nghiep-3533", 1, 27, 1));
        groups.add(new NDGroup("Cần bán", "Đất", "Đất công nghiệp", "http://www.muabannhadat.vn/dat-ban-dat-cong-nghiep-3534", 1, 4, 1));
        groups.add(new NDGroup("Cần bán", "Đất", "Đất loại khác", "http://www.muabannhadat.vn/dat-ban-dat-loai-khac-3596", 1, 55, 1));

        groups.add(new NDGroup("Cần bán", "Mặt bằng", "Mặt bằng văn phòng", "http://www.muabannhadat.vn/mat-bang-ban-mat-bang-van-phong-3538", 1, 4, 1));
        groups.add(new NDGroup("Cần bán", "Mặt bằng", "Mặt bằng bán lẻ", "http://www.muabannhadat.vn/mat-bang-ban-mat-bang-ban-le-3539", 1, 7, 1));
        groups.add(new NDGroup("Cần bán", "Mặt bằng", "Mặt bằng khác", "http://www.muabannhadat.vn/mat-bang-ban-khac-3598", 1, 1, 1));

        groups.add(new NDGroup("Cần bán", "Kho xưởng", "Nhà xưởng", "http://www.muabannhadat.vn/kho-xuong-ban-nha-xuong-3541", 1, 7, 1));
        groups.add(new NDGroup("Cần bán", "Kho xưởng", "Nhà kho", "http://www.muabannhadat.vn/kho-xuong-ban-nha-kho-3543", 1, 1, 1));
        groups.add(new NDGroup("Cần bán", "Kho xưởng", "Kho xưởng khác", "http://www.muabannhadat.vn/kho-xuong-ban-khac-3600", 1, 1, 1));

        groups.add(new NDGroup("Cho thuê", "Nhà", "Biệt thự", "http://www.muabannhadat.vn/nha-cho-thue-biet-thu-villa-3546", 1, 11, 1));
        groups.add(new NDGroup("Cho thuê", "Nhà", "Nhà phố", "http://www.muabannhadat.vn/nha-cho-thue-nha-pho-3558", 1, 244, 1));
        groups.add(new NDGroup("Cho thuê", "Nhà", "Nhà loại khác", "http://www.muabannhadat.vn/nha-cho-thue-khac-3593", 1, 24, 1));

        groups.add(new NDGroup("Cho thuê", "Căn hộ", "Chung cư", "http://www.muabannhadat.vn/can-ho-cho-thue-chung-cu-3550", 1, 105, 1));
        groups.add(new NDGroup("Cho thuê", "Căn hộ", "Chung cư cao cấp", "http://www.muabannhadat.vn/can-ho-cho-thue-chung-cu-cao-cap-3551", 1, 135, 1));
        groups.add(new NDGroup("Cho thuê", "Căn hộ", "Chung cư khác", "http://www.muabannhadat.vn/can-ho-cho-thue-khac-3595", 1, 7, 1));

        groups.add(new NDGroup("Cho thuê", "Đất", "Đất nông nghiệp", "http://www.muabannhadat.vn/dat-cho-thue-dat-nong-nghiep-3556", 1, 1, 1));
        groups.add(new NDGroup("Cho thuê", "Đất", "Đất công nghiệp", "http://www.muabannhadat.vn/dat-cho-thue-dat-cong-nghiep-3557", 1, 1, 1));
        groups.add(new NDGroup("Cho thuê", "Đất", "Đất loại khác", "http://www.muabannhadat.vn/dat-cho-thue-khac-3597", 1, 1, 1));

        groups.add(new NDGroup("Cho thuê", "Mặt bằng", "Mặt bằng văn phòng", "http://www.muabannhadat.vn/mat-bang-cho-thue-mat-bang-van-phong-3561", 1, 67, 1));
        groups.add(new NDGroup("Cho thuê", "Mặt bằng", "Mặt bằng bán lẻ", "http://www.muabannhadat.vn/mat-bang-cho-thue-mat-bang-ban-le-3562", 1, 4, 1));
        groups.add(new NDGroup("Cho thuê", "Mặt bằng", "Mặt bằng khác", "http://www.muabannhadat.vn/mat-bang-cho-thue-khac-3599", 1, 1, 1));

        groups.add(new NDGroup("Cho thuê", "Kho xưởng", "Nhà kho", "http://www.muabannhadat.vn/kho-xuong-cho-thue-nha-kho-3566", 1, 4, 1));
        groups.add(new NDGroup("Cho thuê", "Kho xưởng", "Kho xưởng khác", "http://www.muabannhadat.vn/kho-xuong-cho-thue-khac-3601", 1, 1, 1));

        for (NDGroup group : groups) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (String parentUrl : group.urls) {
                        ArrayList<String> childUrls = new ArrayList<String>();
                        org.jsoup.nodes.Document document = null;
                        try {
                            document = NDMuaBanNhaDatCrawler.getDocumentFromUrl(parentUrl);
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
                                        childUrls.add(href);
                                        NDDebug.log(1, "GET", href);
                                    }
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        for (String childUrl : childUrls) {
                            NDNews news = new NDNews();
                            news.type = group.type;
                            news.group = group.group;
                            news.category = group.category;
                            try {
                                news.parse(childUrl);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start();
        }
    }

}

class NDGroup {

    public int start;
    public int end;
    public int step;
    public String base;
    public String format;
    public String type;
    public String group;
    public String category;
    public ArrayList<String> urls = new ArrayList<>();

    public NDGroup(String type, String group, String category, String base, String format, int start, int end, int step) {
        this.type = type;
        this.group = group;
        this.category = category;
        this.base = base;
        this.format = format;
        this.start = start;
        this.end = end;
        this.step = step;
        fetch();
    }

    public NDGroup(String type, String group, String category, String base, int start, int end, int step) {
        this.type = type;
        this.group = group;
        this.category = category;
        this.base = base;
        this.format = base + "?p=%s";
        this.start = start;
        this.end = end;
        this.step = step;
        fetch();
    }

    public void fetch() {
        urls.add(base);
        for (int i = start; i <= end; i += step) {
            urls.add(String.format(format, i));
        }
        Collections.reverse(urls);
    }
}

class NDNews {
    public int id = 0;
    public int created = 0;
    public String title = "";
    public String type = "";
    public String group = "";
    public String category = "";
    public String street = "";
    public String ward = "";
    public String district = "";
    public String city = "";
    public String description = "";
    public String legal = "";
    public String direction = "";
    public String project = "";
    public double price = 0.0;
    public double surface = 0.0;
    public double front = 0.0;
    public double latitude = 0.0;
    public double longitude = 0.0;
    public int bedroom = 0;
    public int bathroom = 0;
    public int floor = 0;
    public String name = "";
    public String address = "";
    public String phone = "";
    public Document images = new Document();
    public Document utilities = new Document();
    public Document environments = new Document();
    public Document document = new Document();

    public void put() {
        if (price > 0 && surface > 0) {
            document.put("id", id);
            document.put("created", created);
            document.put("title", title);
            document.put("type", type);
            document.put("group", group);
            document.put("category", category);
            document.put("street", street);
            document.put("ward", ward);
            document.put("district", district);
            document.put("city", city);
            document.put("latitude", latitude);
            document.put("longitude", longitude);
            document.put("description", description);
            document.put("legal", legal);
            document.put("direction", direction);
            document.put("project", project);
            document.put("price", price);
            document.put("surface", surface);
            document.put("front", front);
            document.put("bedroom", bedroom);
            document.put("bathroom", bathroom);
            document.put("floor", floor);
            document.put("name", name);
            document.put("address", address);
            document.put("phone", phone);
            document.put("images", images);
            document.put("utilities", utilities);
            document.put("environments", environments);
        }
    }

    public void log() {
        NDDebug.prln(document.toJson());
    }

    public void parse(String url) throws IOException {
        org.jsoup.nodes.Document jsoupDocument = NDMuaBanNhaDatCrawler.getDocumentFromUrl(url);
        Element bodyElement = jsoupDocument.body();
        Elements elements = null;
        //--------------------------------------------------------------------------------------------------------//
        elements = bodyElement.select("h1.navi-title");
        if (elements != null) {
            title = elements.text().trim();
        }
        elements = bodyElement.select("div#Description");
        if (elements != null) {
            description = elements.text().trim();
        }
        //--------------------------------------------------------------------------------------------------------//
        elements = bodyElement.select("span#MainContent_ctlDetailBox_lblId");
        if (elements != null) {
            id = Integer.parseInt(elements.text().trim());
        }
        //--------------------------------------------------------------------------------------------------------//
        elements = bodyElement.select("span#MainContent_ctlDetailBox_lblMapLink");
        if (elements != null) {
            elements = elements.select("a");
            if (elements != null) {
                String link = elements.attr("href");
                if (link.length() > 0) {
                    String[] location = link.split(":")[2].split(",");
                    latitude = Double.parseDouble(location[0]);
                    longitude = Double.parseDouble(location[1]);
                }
            }
        }
        //--------------------------------------------------------------------------------------------------------//
        elements = bodyElement.select("span#MainContent_ctlDetailBox_lblPrice");
        if (elements != null) {
            String priceString = elements.text().trim().replace(",", ".");
            if (priceString.contains("Giá bảo mật")) {
                priceString = "0";
            }
            int index = priceString.indexOf(" ");
            if (index >= 0) {
                String priceUnit = priceString.substring(index, priceString.length()).trim();
                priceString = priceString.substring(0, index).trim();
                price = Double.parseDouble(priceString);
                if (priceUnit.equalsIgnoreCase("Tỷ VNĐ")) {
                    price *= 1000;
                }
                price *= 1000;
                price = Math.round(price);
                price /= 1000;
            }
        }
        //--------------------------------------------------------------------------------------------------------//
        elements = bodyElement.select("span#MainContent_ctlDetailBox_lblSurface");
        if (elements != null) {
            String surfaceString = elements.text().trim().replace(",", ".");
            int index = surfaceString.indexOf(" ");
            if (index >= 0) {
                String surfaceUnit = surfaceString.substring(index, surfaceString.length()).trim();
                surfaceString = surfaceString.substring(0, index).trim();
                surface = Double.parseDouble(surfaceString);
                surface *= 1000;
                surface = Math.round(surface);
                surface /= 1000;
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
            String bedroomString = elements.text().trim();
            if (bedroomString.length() > 0) {
                bedroom = Integer.parseInt(bedroomString);
            }
        }
        //--------------------------------------------------------------------------------------------------------//
        elements = bodyElement.select("span#MainContent_ctlDetailBox_lblBathRoom");
        if (elements != null) {
            String bathroomString = elements.text().trim();
            if (bathroomString.length() > 0) {
                bathroom = Integer.parseInt(bathroomString);
            }
        }
        //--------------------------------------------------------------------------------------------------------//
        elements = bodyElement.select("span#MainContent_ctlDetailBox_lblFloor");
        if (elements != null) {
            String floorString = elements.text().trim();
            if (floorString.length() > 0) {
                floor = Integer.parseInt(floorString);
            }
        }
        //--------------------------------------------------------------------------------------------------------//
        elements = bodyElement.select("span#MainContent_ctlDetailBox_lblEnvironment");
        if (elements != null) {
            String environment = elements.html();
            String[] environmentStrings = environment.split("<br>");
            for (int i = 0; i < environmentStrings.length; i++) {
                environments.put(i + "", environmentStrings[i].trim());
            }
        }
        //--------------------------------------------------------------------------------------------------------//
        elements = bodyElement.select("span#MainContent_ctlDetailBox_lblUtility");
        if (elements != null) {
            String utility = elements.html();
            String[] utilitieStrings = utility.split("<br>");
            for (int i = 0; i < utilitieStrings.length; i++) {
                utilities.put(i + "", utilitieStrings[i].trim());
            }
        }
        //--------------------------------------------------------------------------------------------------------//
        elements = bodyElement.select("span#MainContent_ctlDetailBox_lblDateCreated");
        if (elements != null) {
            String createdString = elements.text().trim().replace(".", "-");
            Timestamp timestamp = NDTime.convertStringToTimestamp(createdString, "dd-MM-yyyy");
            if (timestamp != null) {
                long time = timestamp.getTime();
                created = (int) NDTime.secondsInMilliseconds(time);
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
        elements = bodyElement.select("span#MainContent_ctlDetailBox_lblLegalStatus");
        if (elements != null) {
            legal = elements.text().trim();
        }
        //--------------------------------------------------------------------------------------------------------//
        elements = bodyElement.select("span#MainContent_ctlDetailBox_lblFrontRoadWidth");
        if (elements != null) {
            String frontString = elements.text().trim().replace(",", ".").replace(" m", "");
            if (frontString.length() > 0) {
                front = Double.parseDouble(frontString);
                front *= 1000;
                front = Math.round(front);
                front /= 1000;
            }
        }
        //--------------------------------------------------------------------------------------------------------//
        elements = bodyElement.select("span#MainContent_ctlDetailBox_lblContactName");
        if (elements != null) {
            name = elements.text().trim();
        }
        //--------------------------------------------------------------------------------------------------------//
        elements = bodyElement.select("span#MainContent_ctlDetailBox_lblAddressContact");
        if (elements != null) {
            address = elements.text().trim();
        }
        //--------------------------------------------------------------------------------------------------------//
        elements = bodyElement.select("span#MainContent_ctlDetailBox_lblContactPhone");
        if (elements != null) {
            phone = elements.text().replace("xxx", "").replace("(click để xem)", "").trim();
            String text = description.replace(".", "").replace(" ", "").replace("-", "");
            int index = text.indexOf(phone);
            if (index >= 0 && text.length() >= index + phone.length() + 3) {
                phone = text.substring(index, index + phone.length() + 3);
                phone = phone.replace(".", "").replace("-", "").replace(" ", "");
                if (phone.length() >= 10) {
                    phone = phone.substring(0, 10);
                }
            } else {
                phone = "";
            }
        }
        //--------------------------------------------------------------------------------------------------------//
        elements = bodyElement.select("a");
        if (elements != null) {
            int count = 0;
            for (int i = 0; i < elements.size(); i++) {
                Element element = elements.get(i);
                String imageUrl = element.attr("href");
                if (imageUrl.contains("http://www.muabannhadat.vn/uploads/images/")) {
                    images.put(count++ + "", imageUrl);
                }
            }
        }
        put();
        log();
        if (document.size() > 0) {
            insert(NDMuaBanNhaDatCrawler.mongo.mongoCollection);
        }
    }

    public void insert(MongoCollection<Document> mongoCollection) {
        org.bson.Document findDocument = new org.bson.Document();
        findDocument.append("id", document.getInteger("id"));
        FindIterable<Document> documentFindIterable = mongoCollection.find(findDocument);
        org.bson.Document resultDocument = documentFindIterable.first();
        if (resultDocument != null) {
//            mongoCollection.updateOne(resultDocument, new org.bson.Document("$set", document));
//            NDDebug.log(0, "UPDATED", document.getInteger("id") + "");
        } else {
            mongoCollection.insertOne(document);
            NDDebug.log(0, "INSERTED", document.getInteger("id") + "");
        }
    }
}


