package core;

import crawler.DNABatDongSanGroupCrawler;
import utils.DNATime;

import java.util.ArrayList;

/**
 * Created by duong on 3/29/16.
 */
public class DNAWebCrawler implements DNADelegateCrawler {

    private ArrayList<DNABaseCrawler> crawlers;

    public DNAWebCrawler() {
        crawlers = new ArrayList<DNABaseCrawler>();
        crawlers.add(new DNABatDongSanGroupCrawler(DNATime.millisecondsInHours(24)));
    }

    @Override
    public void execute() {
        for (DNABaseCrawler crawler: crawlers) {
            crawler.execute();
        }
    }
}