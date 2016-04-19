package ru.yandex.android.andrew.yandexmobilisation.view;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;
import ru.yandex.android.andrew.yandexmobilisation.R;
import ru.yandex.android.andrew.yandexmobilisation.adapter.CustomRecyclerView;
import ru.yandex.android.andrew.yandexmobilisation.adapter.RecyclerArtistListAdapter;
import ru.yandex.android.andrew.yandexmobilisation.pojo.Artist;
import ru.yandex.android.andrew.yandexmobilisation.service.NetLoader;

import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.IS_DEBUG;
import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.LOADER_ID;
import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.LOG_TAG;
import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.SHARED_PREFERENCE_JSON_ARTISTS_KEY;
import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.SHARED_PREFERENCE_TAG;
import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.URL;
import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.getListFromJson;

/**
 * Created by Andrew on 03.04.2016.
 */
public class ListFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>,
        WaveSwipeRefreshLayout.OnRefreshListener {

    public CustomRecyclerView recyclerView;
    private RecyclerArtistListAdapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerLayoutManager;
    private Context context;
    private List<Artist> list;
    private Loader<String> loader;
    private WaveSwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //setRetainInstance(true);
        View view = null;
        view = inflater.inflate(R.layout.fragment_list, container, false);
        context = getActivity();
        recyclerView = (CustomRecyclerView) view.findViewById(R.id.listview_arlist);
        recyclerLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recyclerLayoutManager);
        String oldJson;
        oldJson = getStringSharedPref(SHARED_PREFERENCE_JSON_ARTISTS_KEY);
        if (list == null)
            list = (List<Artist>) getListFromJson(oldJson, Artist.class);
        recyclerAdapter = new RecyclerArtistListAdapter(getActivity(), list);
        SlideInRightAnimationAdapter animationAdapter = new SlideInRightAnimationAdapter(recyclerAdapter);
        recyclerView.setAdapter(animationAdapter);
        swipeRefreshLayout = (WaveSwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setWaveColor(getResources().getColor(R.color.colorPrimary));
        //swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLACK, Color.BLUE, Color.CYAN);

        if (IS_DEBUG)
            Log.d(LOG_TAG, "ListFragment is onCreateView");
        return view;
    }


    public void notifyDataChanged(String json) {
        this.list = (List<Artist>) getListFromJson(json, Artist.class);
        if (list.size() > 1) putStringSharedPref(SHARED_PREFERENCE_JSON_ARTISTS_KEY, json);
        recyclerAdapter.setList(list);
        recyclerAdapter.notifyDataSetChanged();
    }

    private void loadData() {
        getLoaderManager().initLoader(LOADER_ID, null, this);
        loader.forceLoad();

    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        loader = null;
        if (id == LOADER_ID)
            loader = new NetLoader(getActivity(), URL);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        switch (loader.getId()) {
            case LOADER_ID:
                notifyDataChanged(data);
                break;
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    public void onRefresh() {

        loadData();
    }

    private void putStringSharedPref(String key, String value) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(SHARED_PREFERENCE_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private String getStringSharedPref(String key) {
        String value = "";
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(SHARED_PREFERENCE_TAG, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(key))
            value = sharedPreferences.getString(key, "");
        if (IS_DEBUG)
            Log.d(LOG_TAG, "getSharedPref value = " + value);
        return value;
    }

    public void notifyItemListClicked(Artist artist) {

    }


}
