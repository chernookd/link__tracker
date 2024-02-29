package edu.java.bot.utils;

import java.net.HttpURLConnection;
import java.net.URL;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

@UtilityClass
public class LinkValidator {

    public boolean isValidLink(String linkStr) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(linkStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int code = connection.getResponseCode();
            HttpStatus httpStatus = HttpStatus.resolve(code);

            return httpStatus != null && httpStatus.is2xxSuccessful();
        } catch (Exception e) {
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
