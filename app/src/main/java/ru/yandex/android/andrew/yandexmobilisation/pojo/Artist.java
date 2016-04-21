package ru.yandex.android.andrew.yandexmobilisation.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Random;

import ru.yandex.android.andrew.yandexmobilisation.utils.WordsHelper;

/**
 * Created by Andrew on 03.04.2016.
 * this class presentation object item of array from JSON.
 * for sample JSON follow http://cache-default02f.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json
 */

//external implementation Parcelable https://github.com/johncarl81/parceler

public class Artist implements Parcelable {

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
        return "Artist: id = " + id + ", name = " + name + ", genres = " + "[" + WordsHelper.genresArrayToString(genres) + "]" +
                ", tracks = " + tracksNumber + ", albums = " + albumNumber + ", link = " + link +
                ", description = " + description + ", cover small = " + cover.getSmall() + ", cover big = " + cover.getBig();
    }


    public static class Cover implements Parcelable {
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


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.small);
            dest.writeString(this.big);
        }

        protected Cover(Parcel in) {
            this.small = in.readString();
            this.big = in.readString();
        }

        public static final Creator<Cover> CREATOR = new Creator<Cover>() {
            @Override
            public Cover createFromParcel(Parcel source) {
                return new Cover(source);
            }

            @Override
            public Cover[] newArray(int size) {
                return new Cover[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeStringArray(this.genres);
        dest.writeInt(this.tracksNumber);
        dest.writeInt(this.albumNumber);
        dest.writeString(this.link);
        dest.writeString(this.description);
        dest.writeParcelable(this.cover, flags);
    }

    protected Artist(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.genres = in.createStringArray();
        this.tracksNumber = in.readInt();
        this.albumNumber = in.readInt();
        this.link = in.readString();
        this.description = in.readString();
        this.cover = in.readParcelable(Cover.class.getClassLoader());
    }

    public static final Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel source) {
            return new Artist(source);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };
}
