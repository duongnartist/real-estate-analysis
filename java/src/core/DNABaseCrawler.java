package core;

import utils.DNADebug;
import utils.DNATime;

import java.util.ArrayList;

/**
 * Created by duong on 3/29/16.
 */
public abstract class DNABaseCrawler implements Runnable, DNADelegateCrawler {

    public static long SLEEP_PER_PARENT_URL = DNATime.millisecondsInSeconds(3);
    public static long SLEEP_PER_CHILD_URL = DNATime.millisecondsInSeconds(3);

    protected ArrayList<DNAGroupCrawler> groups;
    protected long sleepTime;
    protected boolean running;

    public DNABaseCrawler(long sleepTime) {
        this.sleepTime = sleepTime;
        groups = new ArrayList<DNAGroupCrawler>();
        running = true;
    }

    public ArrayList<DNAGroupCrawler> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<DNAGroupCrawler> groups) {
        this.groups = groups;
    }

    public abstract ArrayList<String> getChildUrls(String parentUrl);

    public abstract DNADocumentCrawler getDocFromUrl(String url);

    @Override
    public void run() {
        while (running) {
            for (DNAGroupCrawler group : groups) {
                for (String parentUrl : group.getParentUrls()) {
                    for (String childUrl : getChildUrls(parentUrl)) {
                        getDocFromUrl(childUrl);
                        try {
                            DNADebug.log(2, "SLEEP", "Sleep " + SLEEP_PER_CHILD_URL + " ms per child url.");
                            Thread.sleep(SLEEP_PER_CHILD_URL);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        DNADebug.log(1, "SLEEP", "Sleep " + SLEEP_PER_PARENT_URL + " ms per parent url.");
                        Thread.sleep(SLEEP_PER_PARENT_URL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                DNADebug.log(0, "SLEEP", sleepTime + " ms");
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void execute() {
        new Thread(this).start();
    }
}
