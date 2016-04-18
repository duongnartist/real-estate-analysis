package crawler;

import core.DNABaseCrawler;
import core.DNADocumentCrawler;
import core.DNAGroupCrawler;
import utils.DNADebug;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.Deflater;

/**
 * Created by duong on 3/29/16.
 */
public class DNABatDongSanGroupCrawler extends DNABaseCrawler {

    public DNABatDongSanGroupCrawler(long sleepTime) {
        super(sleepTime);
        /*
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/ban-can-ho-chung-cu",              "http://batdongsan.com.vn/ban-can-ho-chung-cu/p%s/",                2, 1627,    1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/ban-nha-rieng",                    "http://batdongsan.com.vn/ban-nha-rieng/p%s/",                      2, 1627,    1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/ban-nha-biet-thu-lien-ke",         "http://batdongsan.com.vn/ban-nha-biet-thu-lien-ke/p%s/",           2, 261,     1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/ban-nha-mat-pho",                  "http://batdongsan.com.vn/ban-nha-mat-pho/p%s/",                    2, 606,     1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/ban-dat-nen-du-an",                "http://batdongsan.com.vn/ban-dat-nen-du-an/p%s/",                  2, 439,     1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/ban-dat",                          "http://batdongsan.com.vn/ban-dat/p%s/",                            2, 928,     1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/ban-trang-trai-khu-nghi-duong",    "http://batdongsan.com.vn/ban-trang-trai-khu-nghi-duong/p%s/",      2, 12,      1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/ban-kho-nha-xuong",                "http://batdongsan.com.vn/ban-kho-nha-xuong/p%s/",                  2, 15,      1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/ban-loai-bat-dong-san-khac",       "http://batdongsan.com.vn/ban-loai-bat-dong-san-khac/p%s/",         2, 20,      1));

        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/mua-can-ho-chung-cu",              "http://batdongsan.com.vn/mua-can-ho-chung-cu/p%s/",                2, 5,       1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/mua-nha-rieng",                    "http://batdongsan.com.vn/mua-nha-rieng/p%s/",                      2, 7,       1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/mua-nha-biet-thu-lien-ke",         "http://batdongsan.com.vn/mua-nha-biet-thu-lien-ke/p%s/",           2, 0,       1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/mua-nha-mat-pho",                  "http://batdongsan.com.vn/mua-nha-mat-pho/p%s/",                    2, 2,       1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/mua-dat-nen-du-an",                "http://batdongsan.com.vn/mua-dat-nen-du-an/p%s/",                  2, 4,       1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/mua-dat",                          "http://batdongsan.com.vn/mua-dat/p%s/",                            2, 4,       1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/mua-trang-trai-khu-nghi-duong",    "http://batdongsan.com.vn/mua-trang-trai-khu-nghi-duong/p%s/",      2, 0,       1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/mua-kho-nha-xuong",                "http://batdongsan.com.vn/mua-kho-nha-xuong/p%s/",                  2, 0,       1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/mua-loai-bat-dong-san-khac",       "http://batdongsan.com.vn/mua-loai-bat-dong-san-khac/p%s/",         2, 76,      1));

        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/cho-thue-can-ho-chung-cu",         "http://batdongsan.com.vn/cho-thue-can-ho-chung-cu/p%s/",           2, 429,     1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/cho-thue-nha-rieng",               "http://batdongsan.com.vn/cho-thue-nha-rieng/p%s/",                 2, 195,     1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/cho-thue-nha-mat-pho",             "http://batdongsan.com.vn/cho-thue-nha-mat-pho/p%s/",               2, 135,     1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/cho-thue-nha-tro-phong-tro",       "http://batdongsan.com.vn/cho-thue-nha-tro-phong-tro/p%s/",         2, 166,     1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/cho-thue-van-phong",               "http://batdongsan.com.vn/cho-thue-van-phong/p%s/",                 2, 87,      1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/cho-thue-cua-hang-ki-ot",          "http://batdongsan.com.vn/cho-thue-cua-hang-ki-ot/p%s/",            2, 123,     1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/cho-thue-kho-nha-xuong-dat",       "http://batdongsan.com.vn/cho-thue-kho-nha-xuong-dat/p%s/",         2, 66,      1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/cho-thue-loai-bat-dong-san-khac",  "http://batdongsan.com.vn/cho-thue-loai-bat-dong-san-khac/p%s/",    2, 2,       1));

        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/can-thue-can-ho-chung-cu",         "http://batdongsan.com.vn/can-thue-can-ho-chung-cu/p%s/",           2, 3,       1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/can-thue-nha-rieng",               "http://batdongsan.com.vn/can-thue-nha-rieng/p%s/",                 2, 6,       1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/can-thue-nha-mat-pho",             "http://batdongsan.com.vn/can-thue-nha-mat-pho/p%s/",               2, 8,       1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/can-thue-nha-tro-phong-tro",       "http://batdongsan.com.vn/can-thue-nha-tro-phong-tro/p%s/",         2, 2,       1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/can-thue-van-phong",               "http://batdongsan.com.vn/can-thue-van-phong/p%s/",                 2, 2,       1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/can-thue-cua-hang-ki-ot",          "http://batdongsan.com.vn/can-thue-cua-hang-ki-ot/p%s/",            2, 9,       1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/can-thue-kho-nha-xuong-dat",       "http://batdongsan.com.vn/can-thue-kho-nha-xuong-dat/p%s/",         2, 3,       1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/can-thue-loai-bat-dong-san-khac",  "http://batdongsan.com.vn/can-thue-loai-bat-dong-san-khac/p%s/",    2, 76,      1));
        */

        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/nha-dat-ban",  "http://batdongsan.com.vn/nha-dat-ban/p%s/",            2, 5209,    1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/nha-dat-can-mua",  "http://batdongsan.com.vn/nha-dat-can-mua/p%s/",    2, 46,      1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/nha-dat-cho-thue", "http://batdongsan.com.vn/nha-dat-cho-thue/p%s/",   2, 1201,    1));
        groups.add(new DNAGroupCrawler("http://batdongsan.com.vn/nha-dat-can-thue", "http://batdongsan.com.vn/nha-dat-can-thue/p%s/",   2, 30,      1));
    }

    @Override
    public ArrayList<String> getChildUrls(String parentUrl) {
        ArrayList<String> urls = new ArrayList<String>();
        try {
            Document document = DNADocumentCrawler.getDocumentFromUrl(parentUrl);
            Element bodyElement = document.body();
            Elements productElements = bodyElement.select("div.vip0.search-productItem");
            if (productElements != null) {
                for (Element productElement : productElements) {
                    Elements aElements = productElement.getElementsByTag("a");
                    String href = "";
                    if (aElements != null) {
                        href = "http://batdongsan.com.vn" + aElements.attr("href");
                    }
                    Elements dateElements = productElement.select("div.floatright.mar-right-10");
                    if (dateElements != null) {
                        String date = dateElements.text().trim();
                        href += "/" + date;
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
        String docUrl = url.substring(0, url.length() - 11);
        String docDate = url.substring(url.length() - 10, url.length());
        DNADocumentCrawler documentCrawler = new DNADocumentCrawler(docUrl, docDate);
        String title = "";
        String street = "";
        String ward = "";
        String district = "";
        String city = "";
        String price = "";
        String area= "";
        String type = "";
        String category = "";
        String project = "";
        String direction = "";
        String bedroom = "";
        Elements elements = null;
        Element element = null;
        try {
            Document document = DNADocumentCrawler.getDocumentFromUrl(docUrl);
            Element bodyElement = document.body();
            //--------------------------------------------------------------------------------------------------------//
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
                price = elements3.first().text().trim();
                if (price.equalsIgnoreCase("Thỏa thuận")) {
                    price = "";
                }
                area = elements3.last().text().trim();
                if (area.equalsIgnoreCase("Không xác định")) {
                    area = "";
                }
            }
            //--------------------------------------------------------------------------------------------------------//
            elements = bodyElement.select("div#divCatagoryOptions");
            if (elements != null) {
                elements = elements.select("li.advance-options.current");
                if (elements != null) {
                    type = elements.text().trim().substring(8);
                    category = title.split(" tại ")[0].replace(type, "").trim();
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
                    bedroom = elements.text().trim();
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
            documentCrawler.printDocument();
            documentCrawler.writeDocument();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return documentCrawler;
    }

}
