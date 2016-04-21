package ru.yandex.android.andrew.yandexmobilisation.utils;

import android.support.annotation.Nullable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 03.04.2016.
 */
public class Utils {

    public static final boolean IS_DEBUG = true;
    public static final boolean ALLOW_SEND_ERROR_CODE = true;
    public static final String YANDEX_METRICA_API_KEY = "16e04b59-3a13-4e82-ac9d-088eb47a4e2c";
    public static final String YANDEX_METRICA_NET_ERROR_TAG = "net error tag";
    public static final String YANDEX_METRICA_JACKSON_ERROR_TAG = "jackson error tag";
    public static final String LOG_TAG = "myLogTag";
    public static final String URL = "http://cache-default02f.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json";
    public static final String SHARED_PREFERENCE_TAG = "shared_preference";
    public static final String SHARED_PREFERENCE_JSON_ARTISTS_KEY = "json_artists";
    public static final String RECEIVER_TAG_ITEM_LIST_CLICK = "BROADCAST_ITEM_LIST_CLICK";
    public static final String EXTRA_LIST_INTENT_TAG = "extra list intent tag";
    public static final int LOADER_ID = 1234567890;

    public static final int LOAD_JSON_TIMEOUT_MILIS = 5000;

    private static int netErrorMonitor = 0;
    private static int jacksonErrorMonitor = 0;

    private static String badJson = "";

    public static int getNetErrorMonitor() {
        return netErrorMonitor;
    }

    public static int getJacksonErrorMonitor() {
        return jacksonErrorMonitor;
    }

    public static String getBadJson() {
        return badJson;
    }



    public static List<?> getListFromJson(String json, Class<?> type) {
        jacksonErrorMonitor = 0;

        String jsonValue = json;

        List<?> list = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        try {
            //get json content without root name
            JsonNode root = mapper.readTree(jsonValue);
            TypeFactory tf = mapper.getTypeFactory();
            JavaType listOfObjs = tf.constructCollectionType(ArrayList.class, type);
            list = mapper.readValue(root.traverse(), listOfObjs);

        } catch (NullPointerException e) {
            jacksonErrorMonitor += 1;
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            jacksonErrorMonitor += 10;
            e.printStackTrace();
        } catch (IOException e) {
            jacksonErrorMonitor += 100;
            e.printStackTrace();
        } finally {
            badJson = "";
            if (jacksonErrorMonitor != 0) badJson = json;
        }

        return list;
    }

    @Nullable
    public static String getJsonFromUrl(String stringURL) {
        netErrorMonitor = 0;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer();
        URL url = null;
        try {
            url = new URL(stringURL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(LOAD_JSON_TIMEOUT_MILIS);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            netErrorMonitor += 1000 * urlConnection.getResponseCode();
            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String tmp;
                while ((tmp = reader.readLine()) != null) {
                    buffer.append(tmp);
                }
                return buffer.toString();
            }
        } catch (MalformedURLException e) {
            netErrorMonitor += 1;
            e.printStackTrace();
        } catch (ProtocolException e) {
            netErrorMonitor += 10;
            e.printStackTrace();
        } catch (IOException e) {
            netErrorMonitor += 100;
            e.printStackTrace();
        } finally {
        }
        return null;
    }


}

