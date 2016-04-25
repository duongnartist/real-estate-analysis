package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by duong on 3/29/16.
 */
public class DNAString {

    public static String normalizeTitle(String title) {
        return title.replaceAll("[\\\\/:\"*?<>|]+", " ");
    }

    public static String getOnlyDigits(String s) {
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(s);
        String number = matcher.replaceAll("");
        return number;
    }
    public static String getOnlyStrings(String s) {
        Pattern pattern = Pattern.compile("[^a-z A-Z]");
        Matcher matcher = pattern.matcher(s);
        String number = matcher.replaceAll("");
        return number;
    }

}
