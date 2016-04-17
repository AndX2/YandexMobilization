package ru.yandex.android.andrew.yandexmobilisation.utils;

import android.util.Log;

import java.util.Locale;

/**
 * Created by Andrew on 14.04.2016.
 */
public class WordsHelper {
    private static Locale defaultLocale = Locale.ENGLISH;

    public static void setDefaultLocale(Locale locale) {
        defaultLocale = locale;
    }

    public static String getAlbumsSongsTitle(int albums, int songs) {
        String title = "";
        if (Utils.IS_DEBUG)
            Log.d(Utils.LOG_TAG, defaultLocale.toString());
        if (defaultLocale.toString().equals("ru_RU")) {
            if (albums == 1) title = albums + " альбом, ";
            else if (albums > 1 && albums < 5) title = albums + " альбома, ";
            else if (albums > 4 && albums < 21) title = albums + " альбомов, ";
            else if ((albums % 10) == 1) title = albums + " альбом, ";
            else if ((albums % 10) > 1 && (albums % 10) < 5) title = albums + " альбома, ";
            else title = albums + " альбомов, ";

            if (songs == 1) title += songs + " песня";
            else if (songs > 1 && songs < 5) title += songs + " песни";
            else if (songs > 4 && songs < 21) title += songs + " песен";
            else if ((songs % 10) == 1) title += songs + " песня";
            else if ((songs % 10) > 1 && (songs % 10) < 5) title += songs + " песни";
            else title += songs + " песен";
        }
        if (!defaultLocale.toString().equals("ru_RU")) {
            if (albums == 1) title = albums + " album, ";
            else title = albums + " albums, ";

            if (songs == 1) title += songs + " song";
            else title += songs + " songs";
        }

        return title;
    }
}
