package core;

import crawler.NDMuaBanNhaDatGroupCrawler;
import utils.NDTime;

import java.util.ArrayList;

/**
 * Created by duong on 3/29/16.
 */
public class NDWebCrawler implements NDDelegateCrawler {

    private ArrayList<NDBaseCrawler> crawlers;

    public NDWebCrawler() {
        crawlers = new ArrayList<NDBaseCrawler>();
//        crawlers.add(new NDBatDongSanGroupCrawler(NDTime.millisecondsInHours(24)));
        crawlers.add(new NDMuaBanNhaDatGroupCrawler(NDTime.millisecondsInHours(24)));
    }

    @Override
    public void execute() {
        for (NDBaseCrawler crawler : crawlers) {
            crawler.execute();
        }
    }
}