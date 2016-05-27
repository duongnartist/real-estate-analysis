package utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by duong on 3/30/16.
 */
public class NDTime {

    public static long millisecondsInSeconds(long seconds) {
        return seconds * 1000;
    }
    public static long secondsInMilliseconds(long milliseconds) {
        return milliseconds / 1000;
    }

    // Minutes
    public static long millisecondsInMinutes(long minutes) {
        return millisecondsInSeconds(secondsInMinutes(minutes));
    }

    public static long secondsInMinutes(long minutes) {
        return minutes * 60;
    }

    // Hours
    public static long minutesInHours(long hours) {
        return hours * 60;
    }

    public static long secondsInHours(long hours) {
        return secondsInMinutes(minutesInHours(hours));
    }

    public static long millisecondsInHours(long hours) {
        return millisecondsInMinutes(minutesInHours(hours));
    }

    // Days
    public static long hoursInDays(long days) {
        return days * 24;
    }

    public static long minutesInDays(long days) {
        return minutesInHours(hoursInDays(days));
    }

    public static long secondsInDays(long days) {
        return secondsInMinutes(minutesInHours(hoursInDays(days)));
    }

    public static long millisecondsInDays(long days) {
        return millisecondsInSeconds(secondsInMinutes(minutesInHours(hoursInDays(days))));
    }

    public static Timestamp convertStringToTimestamp(String dateString, String formatString) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(formatString);
            Date date = dateFormat.parse(dateString);
            java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());
            return timeStampDate;
        } catch (ParseException e) {
            return null;
        }
    }
}

