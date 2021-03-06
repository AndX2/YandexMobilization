package ru.yandex.android.andrew.yandexmobilisation.utils;

import java.util.Locale;

/**
 * Helper static class for creating text "## albums, ## songs"
 * on english (default) and
 * any another lang (JUST ADD METHOD FOR IT)
 * RU lang sample getIfRuLocale method
 * It not contains dependencies androidSDK for work and testing separately from the application
 * on desktop or another environment
 */
public class WordsHelper {
    private static Locale defaultLocale = Locale.ENGLISH;

    public static void setDefaultLocale(Locale locale) {
        defaultLocale = locale;
    }

    public static String getAlbumsSongsTitle(int albums, int songs) {
        String title = "";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(albums);

        if (albums == 1) stringBuilder.append(" album ∙ ");
        else stringBuilder.append(" albums ∙ ");

        stringBuilder.append(songs);

        if (songs == 1) stringBuilder.append(" song");
        else stringBuilder.append(" songs");

        title = stringBuilder.toString();

        if (defaultLocale.toString().equals("ru_RU")) title = getIfRuLocale(albums, songs);

        return title;
    }

    private static String getIfRuLocale(int albums, int songs) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(albums);
        if (albums == 1) stringBuilder
                .append(" альбом ∙ ");
        else if (albums > 1 && albums < 5) stringBuilder
                .append(" альбома ∙ ");
        else if (albums > 4 && albums < 21) stringBuilder
                .append(" альбомов ∙ ");
        else if ((albums % 10) == 1) stringBuilder
                .append(" альбом ∙ ");
        else if ((albums % 10) > 1 && (albums % 10) < 5) stringBuilder
                .append(" альбома ∙ ");
        else stringBuilder.append(" альбомов ∙ ");

        stringBuilder.append(songs);

        if (songs == 1) stringBuilder.append(" песня");
        else if (songs > 1 && songs < 5) stringBuilder.append(" песни");
        else if (songs > 4 && songs < 21) stringBuilder.append(" песен");
        else if ((songs % 10) == 1) stringBuilder.append(" песня");
        else if ((songs % 10) > 1 && (songs % 10) < 5) stringBuilder.append(" песни");
        else stringBuilder.append(" песен");

        return stringBuilder.toString();

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


