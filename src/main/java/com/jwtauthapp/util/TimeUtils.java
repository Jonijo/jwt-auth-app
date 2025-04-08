package com.jwtauthapp.util;

public class TimeUtils {

    public static String millisToHumanReadable(long millis) {
        long days = millis / (24 * 60 * 60 * 1000);
        millis %= (24 * 60 * 60 * 1000);

        long hours = millis / (60 * 60 * 1000);
        millis %= (60 * 60 * 1000);

        long minutes = millis / (60 * 1000);
        millis %= (60 * 1000);

        long seconds = millis / 1000;
        millis %= 1000;

        return String.format("%d days, %02d hours, %02d minutes, %02d seconds, %03d ms",
                days, hours, minutes, seconds, millis);
    }
}
