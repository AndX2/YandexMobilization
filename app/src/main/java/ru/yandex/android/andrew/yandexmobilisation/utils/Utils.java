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
    public static final String LOG_TAG = "myLogTag";
    public static final String URL = "http://cache-default02f.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json";
    public static final String SHARED_PREFERENCE_TAG = "shared_preference";
    public static final String SHARED_PREFERENCE_JSON_ARTISTS_KEY = "json_artists";
    public static final String RECEIVER_TAG_UPDATE_LIST = "BROADCAST RECEIVE UPDATE LIST";
    public static final String EXTRA_LIST_INTENT_TAG = "extra list intent tag";
    public static final int LOADER_ID = 1234567890;


    public static List<?> getListFromJson(String json, Class<?> type) {

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

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Nullable
    public static String getJsonFromUrl(String stringURL) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer();
        URL url = null;
        try {
            url = new URL(stringURL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String tmp;
                while ((tmp = reader.readLine()) != null) {
                    buffer.append(tmp);
                }
                ;
                return buffer.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String genresArrayToString(String[] genres) {
        String tmp = "";
        for (String genre : genres) {
            tmp = tmp + genre + ", ";
        }
        if (tmp.length() > 2)
            tmp = tmp.substring(0, tmp.length() - 2);
        return tmp;
    }

}

