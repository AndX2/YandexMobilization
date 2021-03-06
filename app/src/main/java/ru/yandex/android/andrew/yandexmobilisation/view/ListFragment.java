package ru.yandex.android.andrew.yandexmobilisation.view;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yandex.metrica.YandexMetrica;

import java.util.Calendar;
import java.util.List;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;
import ru.yandex.android.andrew.yandexmobilisation.R;
import ru.yandex.android.andrew.yandexmobilisation.adapter.CustomRecyclerView;
import ru.yandex.android.andrew.yandexmobilisation.adapter.RecyclerArtistListAdapter;
import ru.yandex.android.andrew.yandexmobilisation.pojo.Artist;
import ru.yandex.android.andrew.yandexmobilisation.service.NetLoader;

import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.ALLOW_SEND_ERROR_CODE;
import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.IS_DEBUG;
import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.LOADER_ID;
import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.LOG_TAG;
import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.SHARED_PREFERENCE_JSON_ARTISTS_KEY;
import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.SHARED_PREFERENCE_TAG;
import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.URL;
import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.YANDEX_METRICA_JACKSON_ERROR_TAG;
import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.YANDEX_METRICA_NET_ERROR_TAG;
import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.getBadJson;
import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.getJacksonErrorMonitor;
import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.getListFromJson;
import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.getNetErrorMonitor;

/**
 * This fragment for view list of artists use RecyclerView and LinearLayoutManager
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
        View view = null;
        view = inflater.inflate(R.layout.fragment_list, container, false);
        context = getActivity();
        recyclerView = (CustomRecyclerView) view.findViewById(R.id.listview_arlist);
        recyclerLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recyclerLayoutManager);
        //Getting (if exist) early downloaded JSON stored to SharedPreference
        String oldJson;
        oldJson = getStringSharedPref(SHARED_PREFERENCE_JSON_ARTISTS_KEY);
        if (list == null)
            list = (List<Artist>) getListFromJson(oldJson, Artist.class);
        //This Custom RecyclerAdapter
        //It mapped SlideInRightAnimationAdapter - third party lib for animation creating new items in list
        //https://github.com/wasabeef/recyclerview-animators
        recyclerAdapter = new RecyclerArtistListAdapter(getActivity(), list);
        SlideInRightAnimationAdapter animationAdapter = new SlideInRightAnimationAdapter(recyclerAdapter);
        recyclerView.setAdapter(animationAdapter);
        //Set third party lib for cool animation "pull to refresh"
        //https://github.com/recruit-lifestyle/WaveSwipeRefreshLayout
        swipeRefreshLayout = (WaveSwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setWaveColor(getResources().getColor(R.color.colorAccent));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Called Snackbar - hint to user pull empty list for get data.
        if (list == null || list.size() < 1)
            showSnackbar(getResources().getString(R.string.try_pull_down));

    }


    public void notifyDataChanged(String json) {
        //Check json and parsed from this list Artists and call methods Handling errors, push
        //JSON to SharedPreference (storage for next launch app), and refresh view with new data
        if (json == null || json.length() < 20) {
            netErrorHandle();
        } else {
            this.list = (List<Artist>) getListFromJson(json, Artist.class);
            if (list.size() > 1) {
                putStringSharedPref(SHARED_PREFERENCE_JSON_ARTISTS_KEY, json);
            } else jacksonErrorHandle();
        }
        recyclerAdapter.setList(list);
        recyclerAdapter.notifyDataSetChanged();
    }

    private void loadData() {
        //Custom method-facade (all needed action for get new data)
        getLoaderManager().initLoader(LOADER_ID, null, this);
        loader.forceLoad();

    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        //Creating custom http get loader for getting json.
        loader = null;
        if (id == LOADER_ID)
            loader = new NetLoader(getActivity(), URL);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        //Callback method loader's after download complete
        switch (loader.getId()) {
            case LOADER_ID:
                notifyDataChanged(data);
                break;
        }
        //stop "pull to refresh" animation
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    public void onRefresh() {
        //Method action "swipeRefreshLayout"
        loadData();
    }

    private void putStringSharedPref(String key, String value) {
        //Helper method Put Any String With Any Key to SharedPreference
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(SHARED_PREFERENCE_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private String getStringSharedPref(String key) {
        //Helper method Get String With Key to SharedPreference
        String value = "";
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(SHARED_PREFERENCE_TAG, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(key))
            value = sharedPreferences.getString(key, "");
        if (IS_DEBUG)
            Log.d(LOG_TAG, "getSharedPref value = " + value);
        return value;
    }

    private void netErrorHandle() {
        //Net errors handle method
        if (ALLOW_SEND_ERROR_CODE) {
            YandexMetrica.reportEvent(YANDEX_METRICA_NET_ERROR_TAG,
                    getNetErrorMonitor() + " " + Calendar.getInstance().getTime());
            showSnackbar(getResources().getString(R.string.net_error_message) +
                    getResources().getString(R.string.we_know));
        } else showSnackbar(getResources().getString(R.string.net_error_message) +
                getResources().getString(R.string.we_know));
        if (IS_DEBUG)
            Log.d(LOG_TAG, "netErrorHandle");
    }

    private void jacksonErrorHandle() {
        //Json error handle method
        if (ALLOW_SEND_ERROR_CODE) {
            YandexMetrica.reportEvent(YANDEX_METRICA_JACKSON_ERROR_TAG,
                    getJacksonErrorMonitor() + getBadJson());
            showSnackbar(getResources().getString(R.string.parse_error_message) +
                    getResources().getString(R.string.we_know));
        } else showSnackbar(getResources().getString(R.string.parse_error_message) +
                getResources().getString(R.string.we_never_know));
        if (IS_DEBUG)
            Log.d(LOG_TAG, "jacksonErrorHandle");
    }

    private void showSnackbar(String message) {
        //Helper methor call Snackbar
        Snackbar snackbar = Snackbar.make(recyclerView, message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }




}
