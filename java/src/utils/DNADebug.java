package utils;

/**
 * Created by duong on 3/27/16.
 */
public class DNADebug {

    public static final int PADDING_LEFT = -16;

    public static void log(int tab, String tag, String msg) {
        String tabString = "";
        for (int i = 0; i < tab; i++) {
            tabString += "\t";
        }
        prln(String.format(tabString + "%" + PADDING_LEFT + "s: %s", tag, msg));
    }

    public static void prln(String log) {
        System.out.println(log);
    }

    public static void pr(String log) {
        System.out.print(log);
    }
}
