package ru.yandex.android.andrew.yandexmobilisation.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.yandex.android.andrew.yandexmobilisation.R;
import ru.yandex.android.andrew.yandexmobilisation.custom.CustomImageView;
import ru.yandex.android.andrew.yandexmobilisation.pojo.Artist;
import ru.yandex.android.andrew.yandexmobilisation.utils.Utils;
import ru.yandex.android.andrew.yandexmobilisation.utils.WordsHelper;

/**
 * Created by Andrew on 11.04.2016.
 */
public class DetailFragment extends Fragment {

    private Artist artist;

    public void setArtist(Artist _artist) {
        artist = _artist;
    }

    public Artist getArtist() {
        return artist;
    }

    TextView tvGenres;
    TextView tvNumbers;
    CustomImageView customImageView;
    TextView tvDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //setRetainInstance(true);
        View view = null;
        view = inflater.inflate(R.layout.fragment_detail, container, false);
        customImageView = (CustomImageView) view.findViewById(R.id.image_big);
        tvNumbers = (TextView) view.findViewById(R.id.detail_tracks_albums);
        tvGenres = (TextView) view.findViewById(R.id.detail_genre);
        tvDescription = (TextView) view.findViewById(R.id.description);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        fillFieldsDetails();
        super.onActivityCreated(savedInstanceState);
    }

    public void fillFieldsDetails() {
        if (Utils.IS_DEBUG)
            Log.d(Utils.LOG_TAG, "Cover big" + artist.getCover().getBig());
        customImageView.loadImage(artist.getCover().getBig(), R.drawable.loading_image_100, R.drawable.loading_error_100, true);
        tvGenres.setText(WordsHelper.genresArrayToString(artist.getGenres()));
        tvNumbers.setText(WordsHelper.getAlbumsSongsTitle(artist.getAlbumNumber(),
                artist.getTracksNumber()));
        tvDescription.setText(artist.getDescription());
        //getActivity().setTitle(artist.getName());
    }


}
