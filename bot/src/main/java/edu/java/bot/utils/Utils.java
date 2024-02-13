package edu.java.bot.utils;

import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {

    private static final int BAD_CODES = 400;

    public boolean isValidLink(String linkStr) {
        try {
            URL url = new URL(linkStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int code = connection.getResponseCode();

            return (code < BAD_CODES);
        } catch (Exception e) {
            return false;
        }
    }
}
