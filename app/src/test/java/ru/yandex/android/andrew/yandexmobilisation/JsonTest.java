package ru.yandex.android.andrew.yandexmobilisation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.yandex.android.andrew.yandexmobilisation.pojo.Artist;
import ru.yandex.android.andrew.yandexmobilisation.utils.Utils;

import static java.lang.System.out;
import static org.junit.Assert.assertTrue;

/**
 * Created by Andrew on 03.04.2016.
 */
public class JsonTest {
    public static final String URL = "http://cache-default02f.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json";

    static String fullJson;

    @BeforeClass
    public static void init() {
        fullJson = Utils.getJsonFromUrl(Utils.URL);
    }


    @Test
    public void getFullCycle() {
        String json = Utils.getJsonFromUrl(Utils.URL);
        assertTrue(json != null);
    }


    @Test
    public void jacksonPojoToJson() {
        String jsonString = "";
        Artist artist = new Artist();
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonString = mapper.writeValueAsString(artist);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //jsonString = "";
        System.out.println(jsonString);
        assertTrue(jsonString.length() > 0);
    }

    @Test
    public void jacksonJsonToPojo() {
        String jsonString = "{\"id\":1080505,\"name\":\"Tove Lo\",\"genres\":[\"pop\",\"dance\",\"electronics\"],\"tracks\":81,\"albums\":22,\"link\":\"http://www.tove-lo.com/\",\"description\":\"шведская певица и автор песен. Она привлекла к себе внимание в 2013 году с выпуском сингла «Habits», но настоящего успеха добилась с ремиксом хип-хоп продюсера Hippie Sabotage на эту песню, который получил название «Stay High». 4 марта 2014 года вышел её дебютный мини-альбом Truth Serum, а 24 сентября этого же года дебютный студийный альбом Queen of the Clouds. Туве Лу является автором песен таких артистов, как Icona Pop, Girls Aloud и Шер Ллойд.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000\"}}";
        ObjectMapper mapper = new ObjectMapper();
        Artist artist = null;
        try {
            artist = mapper.readValue(jsonString, Artist.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(artist.toString());
        assertTrue(artist != null);
    }

    @Test
    public void readJSONToList() {
        String jsonValue = "[{\"id\":1080505,\"name\":\"Tove Lo\",\"genres\":[\"pop\",\"dance\",\"electronics\"],\"tracks\":81,\"albums\":22,\"link\":\"http://www.tove-lo.com/\",\"description\":\"шведская певица и автор песен. Она привлекла к себе внимание в 2013 году с выпуском сингла «Habits», но настоящего успеха добилась с ремиксом хип-хоп продюсера Hippie Sabotage на эту песню, который получил название «Stay High». 4 марта 2014 года вышел её дебютный мини-альбом Truth Serum, а 24 сентября этого же года дебютный студийный альбом Queen of the Clouds. Туве Лу является автором песен таких артистов, как Icona Pop, Girls Aloud и Шер Ллойд.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000\"}},{\"id\":2915,\"name\":\"Ne-Yo\",\"genres\":[\"rnb\",\"pop\",\"rap\"],\"tracks\":256,\"albums\":152,\"link\":\"http://www.neyothegentleman.com/\",\"description\":\"обладатель трёх премии Грэмми, американский певец, автор песен, продюсер, актёр, филантроп. В 2009 году журнал Billboard поставил Ни-Йо на 57 место в рейтинге «Артисты десятилетия».\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/15ae00fc.p.2915/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/15ae00fc.p.2915/1000x1000\"}},{\"id\":91546,\"name\":\"Usher\",\"genres\":[\"rnb\",\"pop\",\"rap\"],\"tracks\":450,\"albums\":183,\"link\":\"http://usherworld.com/\",\"description\":\"американский певец и актёр. Один из самых коммерчески успешных R&B-музыкантов афроамериканского происхождения. В настоящее время продано более 65 миллионов копий его альбомов по всему миру. Выиграл семь премий «Грэмми», четыре премии World Music Awards, четыре премии American Music Award и девятнадцать премий Billboard Music Awards. Владелец собственной звукозаписывающей компании US Records. Он занимает 21 место в списке самых успешных музыкантов по версии Billboard, а также второе место, уступив Эминему в списке самых успешных музыкантов 2000-х годов. В 2010 году журнал Glamour включил его в список 50 самых сексуальных мужчин.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/b0e14f75.p.91546/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/b0e14f75.p.91546/1000x1000\"}},{\"id\":100500,\"name\":\"Jay Sean\",\"genres\":[\"pop\",\"rap\",\"rnb\"],\"tracks\":106,\"albums\":38,\"description\":\"британский рэпер, являющийся выходцем из Индии. Родился в западном Лондоне, Англия. Выпустил три альбома Me Against Myself, My Own Way и All or Nothing.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/db35e57a.p.100500/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/db35e57a.p.100500/1000x1000\"}},{\"id\":74614,\"name\":\"Kelly Rowland\",\"genres\":[\"rnb\",\"pop\",\"rap\"],\"tracks\":174,\"albums\":106,\"link\":\"http://www.kellyrowland.com/\",\"description\":\"американская певица и актриса. Выступает в стиле современный ритм-энд-блюз, является автором текстов песен.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/be7f0f49.p.74614/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/be7f0f49.p.74614/1000x1000\"}},{\"id\":1156,\"name\":\"Timbaland\",\"genres\":[\"rap\",\"pop\",\"dance\"],\"tracks\":227,\"albums\":141,\"link\":\"http://www.timbalandmusic.com/\",\"description\":\"американский рэпер, музыкальный продюсер, аранжировщик и автор песен. Основатель лейбла Mosley Music Group, двукратный обладатель премий «Грэмми».\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/e33024d5.p.1156/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/e33024d5.p.1156/1000x1000\"}},{\"id\":1150,\"name\":\"Keri Hilson\",\"genres\":[\"pop\",\"rnb\",\"rap\"],\"tracks\":99,\"albums\":64,\"description\":\"американская певица и автор песен в стиле современного R&B, работает на лейблы Zone 4/Mosley Music Group/Interscope Records. Является членом объединения авторов и продюсеров, известного как The Clutch.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/40598113.p.1150/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/40598113.p.1150/1000x1000\"}},{\"id\":451523,\"name\":\"Пицца\",\"genres\":[\"rusrap\"],\"tracks\":33,\"albums\":11,\"link\":\"http://pizzamusic.ru/\",\"description\":\"музыкальная группа, основанная в 2009 году Сергеем Приказчиковым и исполняющая песни на стыке таких жанров, как поп-соул, регги, фанк и даже рэп.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/120513b9.p.451523/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/120513b9.p.451523/1000x1000\"}},{\"id\":41110,\"name\":\"Дима Билан\",\"genres\":[\"pop\"],\"tracks\":119,\"albums\":29,\"link\":\"http://bilandima.ru/\",\"description\":\"Дима Николаевич Билан - российский певец, более известный как Дима Билан. В июне 2008 года принял данный псевдоним в качестве настоящего имени и фамилии. Дима Билан представлял Россию на конкурсе песни «Евровидение» два раза: в 2006 году с песней «Never Let You Go», заняв второе место, и в 2008 году с песней «Believe», заняв первое место и став первым российским артистом, победившим на конкурсе песни «Евровидение».\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/20c848e3.p.41110/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/20c848e3.p.41110/1000x1000\"}},{\"id\":166300,\"name\":\"Бьянка\",\"genres\":[\"pop\",\"rnb\"],\"tracks\":45,\"albums\":13,\"link\":\"http://www.biankanumber1.ru/\",\"description\":\"белорусская и российская R&B-исполнительница.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/f960f99a.p.166300/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/f960f99a.p.166300/1000x1000\"}},{\"id\":161010,\"name\":\"Нюша\",\"genres\":[\"pop\"],\"tracks\":99,\"albums\":34,\"link\":\"http://www.nyusha.ru/\",\"description\":\"российская певица, автор песен, композитор, актриса.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/7806607c.p.161010/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/7806607c.p.161010/1000x1000\"}},{\"id\":155914,\"name\":\"Винтаж\",\"genres\":[\"pop\"],\"tracks\":91,\"albums\":17,\"link\":\"http://www.vintagemusic.ru/\",\"description\":\"российская поп-группа, в состав которой входят певица Анна Плетнёва и певец, композитор и саунд-продюсер Алексей Романов. Ранее в состав группы входили танцовщицы Мия и Светлана Иванова.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/42479f15.p.155914/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/42479f15.p.155914/1000x1000\"}},{\"id\":159009,\"name\":\"Градусы\",\"genres\":[\"pop\"],\"tracks\":30,\"albums\":5,\"link\":\"http://www.gradusy.com/\",\"description\":\"российская поп-группа из Ставрополя, основанная в 2008 году. Первый концерт группа отыграла под названием «Градус 100» 29 мая 2008 года\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/46f09c63.p.159009/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/46f09c63.p.159009/1000x1000\"}},{\"id\":755171,\"name\":\"Иван Дорн\",\"genres\":[\"pop\",\"rap\",\"electronics\"],\"tracks\":117,\"albums\":43,\"link\":\"http://www.ivandorn.com\",\"description\":\"украинский певец, диджей и телеведущий, бывший участник группы «Пара Нормальных».\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/f8bb3e2e.p.755171/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/f8bb3e2e.p.755171/1000x1000\"}},{\"id\":2392189,\"name\":\"Андрей Леницкий\",\"genres\":[\"pop\"],\"tracks\":29,\"albums\":4,\"link\":\"http://lenitsky.com/\",\"description\":\"Украинский музыкант, солист, автор песен и композитор.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/db0fe697.p.2392189/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/db0fe697.p.2392189/1000x1000\"}},{\"id\":796797,\"name\":\"Егор Крид\",\"genres\":[\"pop\",\"rnb\",\"rap\"],\"tracks\":57,\"albums\":22,\"link\":\"https://twitter.com/egorkreed\",\"description\":\"российский певец.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/eed9efaa.p.796797/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/eed9efaa.p.796797/1000x1000\"}},{\"id\":190602,\"name\":\"5sta family\",\"genres\":[\"pop\",\"rap\",\"soul\"],\"tracks\":34,\"albums\":13,\"link\":\"http://5sta.ru\",\"description\":\"российская R&B-группа.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/c3713853.p.190602/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/c3713853.p.190602/1000x1000\"}},{\"id\":6173,\"name\":\"Sugababes\",\"genres\":[\"pop\"],\"tracks\":143,\"albums\":55,\"link\":\"https://twitter.com/SugababesHQ\",\"description\":\"женское поп-трио из Лондона, которое было сформировано в 1998-м году. Эта группа выпустила 27 синглов, шесть из которых стали № 1 в Великобритании, и семь альбомов, два из которых также забрались на вершину Британского альбомного чарта. Три альбома девушек стали трижды платиновыми. В 2003-м году они победили в номинации «Лучший танцевальный исполнитель», а в 2006-м году были названы в Великобритании исполнительницами двадцать первого века, опережая таких артистов, как Бритни Спирс и Мадонна. По всему миру было куплено более четырнадцати миллионов копий альбомов Sugababes.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/3a78c5ba.a.2987-1/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/3a78c5ba.a.2987-1/1000x1000\"}},{\"id\":29506,\"name\":\"Shaznay Lewis\",\"genres\":[\"pop\"],\"tracks\":17,\"albums\":3,\"description\":\"британская певица, автор песен и актриса.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/fb79d2d3.a.15587-2/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/fb79d2d3.a.15587-2/1000x1000\"}},{\"id\":11580,\"name\":\"Emma Bunton\",\"genres\":[\"pop\"],\"tracks\":20,\"albums\":11,\"description\":\"британская певица, автор песен и телеведущая.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/dfddb106.a.32140-1/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/dfddb106.a.32140-1/1000x1000\"}}]";
        List<Artist> objDTOS = null;

        ObjectMapper mapper = new ObjectMapper();
        try {
            //get json content without root name
            JsonNode root = mapper.readTree(jsonValue);
            TypeFactory tf = mapper.getTypeFactory();
            JavaType listOfObjs = tf.constructCollectionType(ArrayList.class, Artist.class);
            objDTOS = mapper.readValue(root.traverse(), listOfObjs);

        } catch (NullPointerException e) {

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Artist artist : objDTOS) {
            System.out.println(artist.toString());
        }

        assertTrue(objDTOS.size() > 0);
    }

    @Test
    public void utilsJsonToList() {
        String jsonValue = "[{\"id\":1080505,\"name\":\"Tove Lo\",\"genres\":[\"pop\",\"dance\",\"electronics\"],\"tracks\":81,\"albums\":22,\"link\":\"http://www.tove-lo.com/\",\"description\":\"шведская певица и автор песен. Она привлекла к себе внимание в 2013 году с выпуском сингла «Habits», но настоящего успеха добилась с ремиксом хип-хоп продюсера Hippie Sabotage на эту песню, который получил название «Stay High». 4 марта 2014 года вышел её дебютный мини-альбом Truth Serum, а 24 сентября этого же года дебютный студийный альбом Queen of the Clouds. Туве Лу является автором песен таких артистов, как Icona Pop, Girls Aloud и Шер Ллойд.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000\"}},{\"id\":2915,\"name\":\"Ne-Yo\",\"genres\":[\"rnb\",\"pop\",\"rap\"],\"tracks\":256,\"albums\":152,\"link\":\"http://www.neyothegentleman.com/\",\"description\":\"обладатель трёх премии Грэмми, американский певец, автор песен, продюсер, актёр, филантроп. В 2009 году журнал Billboard поставил Ни-Йо на 57 место в рейтинге «Артисты десятилетия».\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/15ae00fc.p.2915/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/15ae00fc.p.2915/1000x1000\"}},{\"id\":91546,\"name\":\"Usher\",\"genres\":[\"rnb\",\"pop\",\"rap\"],\"tracks\":450,\"albums\":183,\"link\":\"http://usherworld.com/\",\"description\":\"американский певец и актёр. Один из самых коммерчески успешных R&B-музыкантов афроамериканского происхождения. В настоящее время продано более 65 миллионов копий его альбомов по всему миру. Выиграл семь премий «Грэмми», четыре премии World Music Awards, четыре премии American Music Award и девятнадцать премий Billboard Music Awards. Владелец собственной звукозаписывающей компании US Records. Он занимает 21 место в списке самых успешных музыкантов по версии Billboard, а также второе место, уступив Эминему в списке самых успешных музыкантов 2000-х годов. В 2010 году журнал Glamour включил его в список 50 самых сексуальных мужчин.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/b0e14f75.p.91546/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/b0e14f75.p.91546/1000x1000\"}},{\"id\":100500,\"name\":\"Jay Sean\",\"genres\":[\"pop\",\"rap\",\"rnb\"],\"tracks\":106,\"albums\":38,\"description\":\"британский рэпер, являющийся выходцем из Индии. Родился в западном Лондоне, Англия. Выпустил три альбома Me Against Myself, My Own Way и All or Nothing.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/db35e57a.p.100500/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/db35e57a.p.100500/1000x1000\"}},{\"id\":74614,\"name\":\"Kelly Rowland\",\"genres\":[\"rnb\",\"pop\",\"rap\"],\"tracks\":174,\"albums\":106,\"link\":\"http://www.kellyrowland.com/\",\"description\":\"американская певица и актриса. Выступает в стиле современный ритм-энд-блюз, является автором текстов песен.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/be7f0f49.p.74614/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/be7f0f49.p.74614/1000x1000\"}},{\"id\":1156,\"name\":\"Timbaland\",\"genres\":[\"rap\",\"pop\",\"dance\"],\"tracks\":227,\"albums\":141,\"link\":\"http://www.timbalandmusic.com/\",\"description\":\"американский рэпер, музыкальный продюсер, аранжировщик и автор песен. Основатель лейбла Mosley Music Group, двукратный обладатель премий «Грэмми».\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/e33024d5.p.1156/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/e33024d5.p.1156/1000x1000\"}},{\"id\":1150,\"name\":\"Keri Hilson\",\"genres\":[\"pop\",\"rnb\",\"rap\"],\"tracks\":99,\"albums\":64,\"description\":\"американская певица и автор песен в стиле современного R&B, работает на лейблы Zone 4/Mosley Music Group/Interscope Records. Является членом объединения авторов и продюсеров, известного как The Clutch.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/40598113.p.1150/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/40598113.p.1150/1000x1000\"}},{\"id\":451523,\"name\":\"Пицца\",\"genres\":[\"rusrap\"],\"tracks\":33,\"albums\":11,\"link\":\"http://pizzamusic.ru/\",\"description\":\"музыкальная группа, основанная в 2009 году Сергеем Приказчиковым и исполняющая песни на стыке таких жанров, как поп-соул, регги, фанк и даже рэп.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/120513b9.p.451523/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/120513b9.p.451523/1000x1000\"}},{\"id\":41110,\"name\":\"Дима Билан\",\"genres\":[\"pop\"],\"tracks\":119,\"albums\":29,\"link\":\"http://bilandima.ru/\",\"description\":\"Дима Николаевич Билан - российский певец, более известный как Дима Билан. В июне 2008 года принял данный псевдоним в качестве настоящего имени и фамилии. Дима Билан представлял Россию на конкурсе песни «Евровидение» два раза: в 2006 году с песней «Never Let You Go», заняв второе место, и в 2008 году с песней «Believe», заняв первое место и став первым российским артистом, победившим на конкурсе песни «Евровидение».\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/20c848e3.p.41110/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/20c848e3.p.41110/1000x1000\"}},{\"id\":166300,\"name\":\"Бьянка\",\"genres\":[\"pop\",\"rnb\"],\"tracks\":45,\"albums\":13,\"link\":\"http://www.biankanumber1.ru/\",\"description\":\"белорусская и российская R&B-исполнительница.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/f960f99a.p.166300/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/f960f99a.p.166300/1000x1000\"}},{\"id\":161010,\"name\":\"Нюша\",\"genres\":[\"pop\"],\"tracks\":99,\"albums\":34,\"link\":\"http://www.nyusha.ru/\",\"description\":\"российская певица, автор песен, композитор, актриса.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/7806607c.p.161010/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/7806607c.p.161010/1000x1000\"}},{\"id\":155914,\"name\":\"Винтаж\",\"genres\":[\"pop\"],\"tracks\":91,\"albums\":17,\"link\":\"http://www.vintagemusic.ru/\",\"description\":\"российская поп-группа, в состав которой входят певица Анна Плетнёва и певец, композитор и саунд-продюсер Алексей Романов. Ранее в состав группы входили танцовщицы Мия и Светлана Иванова.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/42479f15.p.155914/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/42479f15.p.155914/1000x1000\"}},{\"id\":159009,\"name\":\"Градусы\",\"genres\":[\"pop\"],\"tracks\":30,\"albums\":5,\"link\":\"http://www.gradusy.com/\",\"description\":\"российская поп-группа из Ставрополя, основанная в 2008 году. Первый концерт группа отыграла под названием «Градус 100» 29 мая 2008 года\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/46f09c63.p.159009/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/46f09c63.p.159009/1000x1000\"}},{\"id\":755171,\"name\":\"Иван Дорн\",\"genres\":[\"pop\",\"rap\",\"electronics\"],\"tracks\":117,\"albums\":43,\"link\":\"http://www.ivandorn.com\",\"description\":\"украинский певец, диджей и телеведущий, бывший участник группы «Пара Нормальных».\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/f8bb3e2e.p.755171/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/f8bb3e2e.p.755171/1000x1000\"}},{\"id\":2392189,\"name\":\"Андрей Леницкий\",\"genres\":[\"pop\"],\"tracks\":29,\"albums\":4,\"link\":\"http://lenitsky.com/\",\"description\":\"Украинский музыкант, солист, автор песен и композитор.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/db0fe697.p.2392189/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/db0fe697.p.2392189/1000x1000\"}},{\"id\":796797,\"name\":\"Егор Крид\",\"genres\":[\"pop\",\"rnb\",\"rap\"],\"tracks\":57,\"albums\":22,\"link\":\"https://twitter.com/egorkreed\",\"description\":\"российский певец.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/eed9efaa.p.796797/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/eed9efaa.p.796797/1000x1000\"}},{\"id\":190602,\"name\":\"5sta family\",\"genres\":[\"pop\",\"rap\",\"soul\"],\"tracks\":34,\"albums\":13,\"link\":\"http://5sta.ru\",\"description\":\"российская R&B-группа.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/c3713853.p.190602/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/c3713853.p.190602/1000x1000\"}},{\"id\":6173,\"name\":\"Sugababes\",\"genres\":[\"pop\"],\"tracks\":143,\"albums\":55,\"link\":\"https://twitter.com/SugababesHQ\",\"description\":\"женское поп-трио из Лондона, которое было сформировано в 1998-м году. Эта группа выпустила 27 синглов, шесть из которых стали № 1 в Великобритании, и семь альбомов, два из которых также забрались на вершину Британского альбомного чарта. Три альбома девушек стали трижды платиновыми. В 2003-м году они победили в номинации «Лучший танцевальный исполнитель», а в 2006-м году были названы в Великобритании исполнительницами двадцать первого века, опережая таких артистов, как Бритни Спирс и Мадонна. По всему миру было куплено более четырнадцати миллионов копий альбомов Sugababes.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/3a78c5ba.a.2987-1/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/3a78c5ba.a.2987-1/1000x1000\"}},{\"id\":29506,\"name\":\"Shaznay Lewis\",\"genres\":[\"pop\"],\"tracks\":17,\"albums\":3,\"description\":\"британская певица, автор песен и актриса.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/fb79d2d3.a.15587-2/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/fb79d2d3.a.15587-2/1000x1000\"}},{\"id\":11580,\"name\":\"Emma Bunton\",\"genres\":[\"pop\"],\"tracks\":20,\"albums\":11,\"description\":\"британская певица, автор песен и телеведущая.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/dfddb106.a.32140-1/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/dfddb106.a.32140-1/1000x1000\"}}]";
        List<Artist> list = (List<Artist>) Utils.getListFromJson(jsonValue, Artist.class);
        System.out.println(list.size());
        for (Artist artist : list) {
            System.out.println(artist.toString());
        }
        assertTrue(list != null & list.size() > 0);
    }


    @Test
    public void utilGetJson() {
        String string;
        string = Utils.getJsonFromUrl(URL);
        System.out.println(string);
        assert (string.length() > 50);
    }

    @Test
    public void getFullArtists() {
        List<Artist> list;
        list = (List<Artist>) Utils.getListFromJson(fullJson, Artist.class);
        out.println(list.size());
        assert (list.size() > 25);
    }

}
