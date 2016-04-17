package ru.yandex.android.andrew.yandexmobilisation.view;

import android.content.BroadcastReceiver;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.Locale;

import ru.yandex.android.andrew.yandexmobilisation.R;
import ru.yandex.android.andrew.yandexmobilisation.utils.Utils;
import ru.yandex.android.andrew.yandexmobilisation.utils.WordsHelper;

import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.IS_DEBUG;
import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.LOG_TAG;

/**
 * Launched and Main activity of project.
 * This activity contains listView of artists in min presentation
 */

public class MainActivity extends AppCompatActivity {

    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private boolean isTwoPane;
    public FragmentManager fragmentManager;
    private ListFragment listFragment;
    private SharedPreferences sharedPreferences;
    private BroadcastReceiver broadcastReceiver;
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WordsHelper.setDefaultLocale(Locale.getDefault());

        setContentView(R.layout.activity_main);
        actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.artists);


        if (IS_DEBUG)
            Log.d(LOG_TAG, "Main onCreate");
        if (IS_DEBUG)
            Log.d(LOG_TAG, "locale: " + Locale.getDefault().toString());

        //this code determine state UI model and save it into var isTwoPanel:
        //false - one pane only list artists
        //true - two panes list + detail fragment
        if (findViewById(R.id.artist_detail_container) != null) {
            //if for presentation UI was been selected layout with modificator -sw600dp
            //then layout contains view with id "artist_detail_container"
            isTwoPane = true;
        } else {
            isTwoPane = false;
            getSupportActionBar().setElevation(10f);
        }
        if (IS_DEBUG)
            Log.d(Utils.LOG_TAG, " twoPane = " + isTwoPane);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        FragmentManager fm = getSupportFragmentManager();
        listFragment = (ListFragment) fm.findFragmentById(R.id.list_fragment);
        RecyclerView recyclerView = listFragment.recyclerView;
        recyclerView.smoothScrollToPosition(10);

    }
}
