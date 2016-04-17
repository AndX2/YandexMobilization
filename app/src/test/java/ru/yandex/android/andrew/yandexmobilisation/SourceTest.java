package ru.yandex.android.andrew.yandexmobilisation;

import org.junit.BeforeClass;

import java.util.List;

import ru.yandex.android.andrew.yandexmobilisation.pojo.Artist;
import ru.yandex.android.andrew.yandexmobilisation.utils.Utils;

/**
 * Created by Andrew on 03.04.2016.
 */
public class SourceTest {

    private static List<Artist> list;

    @BeforeClass
    public static void init() {
        String urlString = "http://cache-default02f.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json";
        String json = Utils.getJsonFromUrl(urlString);
        list = (List<Artist>) Utils.getListFromJson(json, Artist.class);
    }

    //@Test
    /*
    public void getAllGenres(){
        HashSet<Artist.Genres> setGenres = new HashSet<>();
        for (Artist artist : list) {
            for (Artist.Genres genre : artist.getGenres()) {
                setGenres.add(genre);
            }
        }
        Iterator<Artist.Genres> iterator = setGenres.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }*/
}
