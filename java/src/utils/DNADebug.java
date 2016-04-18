package utils;

/**
 * Created by duong on 3/27/16.
 */
public class DNADebug {

    public static final int PADDING_LEFT = -8;

    public static void log(int tab, String tag, String msg) {
        String tabString = "";
        for (int i = 0; i < tab; i++) {
            tabString += "\t";
        }
        System.out.println(String.format(tabString + "%" + PADDING_LEFT + "s: %s", tag, msg));
    }

}
