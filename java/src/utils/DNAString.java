package utils;

/**
 * Created by duong on 3/29/16.
 */
public class DNAString {

    public static String normalizeTitle(String title) {
        return title.replaceAll("[\\\\/:\"*?<>|]+", " ");
    }

}
