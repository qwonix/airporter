package ru.qwonix.suai.airporter.fill;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public abstract class JSONDownloader {

    private static final int timeout = 7000;

    public static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static JSONObject readJsonObjectFromUrl(String url) throws IOException, JSONException {
        return new JSONObject(readFromUrlGetRequest(url));

    }

    public static JSONArray readJsonArrayFromUrl(String url) throws IOException, JSONException {
        return new JSONArray(readFromUrlGetRequest(url));
    }

    public static String readFromUrlGetRequest(String url) throws IOException, JSONException {
        URL urlConn = new URL(url);
        URLConnection connection = urlConn.openConnection();

        connection.setConnectTimeout(timeout);
        try (InputStream is = connection.getInputStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return readAll(rd);
        }
    }

    public static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
