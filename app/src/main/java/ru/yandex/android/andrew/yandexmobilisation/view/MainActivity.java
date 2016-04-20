package ru.yandex.android.andrew.yandexmobilisation.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;

import java.util.Locale;

import ru.yandex.android.andrew.yandexmobilisation.R;
import ru.yandex.android.andrew.yandexmobilisation.pojo.Artist;
import ru.yandex.android.andrew.yandexmobilisation.utils.Utils;
import ru.yandex.android.andrew.yandexmobilisation.utils.WordsHelper;

import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.EXTRA_LIST_INTENT_TAG;
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
    private DetailFragment detailFragment;
    private SharedPreferences sharedPreferences;
    private BroadcastReceiver broadcastReceiver;
    private ActionBar actionBar;
    private BroadcastReceiver clickItemListReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        WordsHelper.setDefaultLocale(Locale.getDefault());
        if (listFragment == null)
            listFragment = new ListFragment();
        if (detailFragment == null)
            detailFragment = new DetailFragment();

        super.onCreate(savedInstanceState);

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

        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, listFragment);
        fragmentTransaction.commit();

    }

    @Override
    protected void onResume() {
        //registering BroadCastReceiver
        clickItemListReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Utils.IS_DEBUG)
                    Log.d(Utils.LOG_TAG, "receive broadcast");
                Artist artist = intent.getParcelableExtra(EXTRA_LIST_INTENT_TAG);
                actionBar.setTitle(artist.getName());
                detailFragment.setArtist(artist);
                flipFragment();
                //detailFragment.fillFieldsDetails(artist);
            }
        };
        registerReceiver(clickItemListReceiver, new IntentFilter(Utils.RECEIVER_TAG_ITEM_LIST_CLICK));
        super.onResume();

    }

    @Override
    protected void onPause() {
        unregisterReceiver(clickItemListReceiver);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            actionBar.setTitle(R.string.artists);
            flipFragment();
        } else super.onBackPressed();


    }

    public void flipFragment() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FragmentTransactionExtended fragmentTransactionExtended = new FragmentTransactionExtended(this,
                    fragmentTransaction, listFragment, detailFragment, R.id.container);
            fragmentTransactionExtended.addTransition(FragmentTransactionExtended.GLIDE);
            fragmentTransactionExtended.commit();
        } else {
            getFragmentManager().popBackStack();
            actionBar.setTitle(R.string.artists);
        }
    }

}
