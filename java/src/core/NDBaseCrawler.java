package core;

import utils.NDDebug;
import utils.NDFile;
import utils.NDMongo;
import utils.NDTime;

import java.util.ArrayList;

/**
 * Created by duong on 3/29/16.
 */
public abstract class NDBaseCrawler implements Runnable, NDDelegateCrawler {

    public static long SLEEP_PER_PARENT_URL = NDTime.millisecondsInSeconds(0);
    public static long SLEEP_PER_CHILD_URL = NDTime.millisecondsInSeconds(0);

    protected ArrayList<NDGroupCrawler> groups;
    protected long sleepTime;
    protected boolean running;
    protected NDMongo NDMongo;
    protected String root;

    public NDBaseCrawler(long sleepTime, String collection) {
        root = NDFile.createCollectionFolder(NDFile.storage, collection);
        connectMongoDd(collection);
        this.sleepTime = sleepTime;
        groups = new ArrayList<NDGroupCrawler>();
        running = true;
    }

    private void connectMongoDd(String collection) {
        NDMongo = new NDMongo(utils.NDMongo.URI);
        NDMongo.openCollection(collection);
    }

    public ArrayList<NDGroupCrawler> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<NDGroupCrawler> groups) {
        this.groups = groups;
    }

    public abstract ArrayList<String> getChildUrls(String parentUrl);

    public abstract NDDocumentCrawler getDocFromUrl(String url);

    @Override
    public void run() {
        while (running) {
            for (NDGroupCrawler group : groups) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (String parentUrl : group.getParentUrls()) {
                            for (String childUrl : getChildUrls(parentUrl)) {
                                getDocFromUrl(childUrl);
                                try {
                                    NDDebug.log(2, "SLEEP", "Sleep " + SLEEP_PER_CHILD_URL + " ms per child url.");
                                    Thread.sleep(SLEEP_PER_CHILD_URL);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            try {
                                NDDebug.log(1, "SLEEP", "Sleep " + SLEEP_PER_PARENT_URL + " ms per parent url.");
                                Thread.sleep(SLEEP_PER_PARENT_URL);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
            try {
                NDDebug.log(0, "SLEEP", sleepTime + " ms");
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
