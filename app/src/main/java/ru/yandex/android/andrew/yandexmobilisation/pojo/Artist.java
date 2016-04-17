package ru.yandex.android.andrew.yandexmobilisation.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Random;

import ru.yandex.android.andrew.yandexmobilisation.utils.Utils;

/**
 * Created by Andrew on 03.04.2016.
 * this class presentation object item of array from JSON.
 * for sample JSON follow http://cache-default02f.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json
 */
public class Artist implements Serializable {

    private long id;
    private String name;
    private String[] genres;
    @JsonProperty("tracks")
    private int tracksNumber;
    @JsonProperty("albums")
    private int albumNumber;
    private String link;
    private String description;
    private Cover cover;


    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    /*
    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }
    */

    public int getAlbumNumber() {
        return albumNumber;
    }

    public void setAlbumNumber(int albumNumber) {
        this.albumNumber = albumNumber;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTracksNumber() {
        return tracksNumber;
    }

    public void setTracksNumber(int tracksNumber) {
        this.tracksNumber = tracksNumber;
    }

    public Artist() {
    }

    ;

    //TODO delete this constructor. It need for testing.
    public Artist(int stub) {
        name = "kdsjafkf";
        Random random = new Random();
        tracksNumber = random.nextInt();
        albumNumber = random.nextInt();
        String[] gnrs = {"dance", "rap", "soul"};
        setGenres(gnrs);
        cover = new Cover();
        cover.setSmall("http://avatars.yandex.net/get-music-content/15ae00fc.p.2915/300x300");


    }

    @Override
    public String toString() {
        return "Artist: id = " + id + ", name = " + name + ", genres = " + "[" + Utils.genresArrayToString(genres) + "]" +
                ", tracks = " + tracksNumber + ", albums = " + albumNumber + ", link = " + link +
                ", description = " + description + ", cover small = " + cover.getSmall() + ", cover big = " + cover.getBig();
    }

    public class Cover implements Serializable {
        String small;
        String big;

        public String getBig() {
            return big;
        }

        public void setBig(String big) {
            this.big = big;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public Cover() {
        }


    }


}
