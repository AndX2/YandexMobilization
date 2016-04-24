package ru.yandex.android.andrew.yandexmobilisation.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
 * Launch and Main activity of project.
 * This activity contains listView of artists in min presentation
 */

public class MainActivity extends AppCompatActivity {

    private boolean isTwoPane;
    public FragmentManager fragmentManager;
    private ListFragment listFragment;
    private DetailFragment detailFragment;
    private BroadcastReceiver clickItemListReceiver;
    Toolbar toolbar;
    ImageView logo;
    TextView title;
    View container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Init helper for construction words "## albums, ## tracks"
        //It need do before onCreate - before creating View for Activity
        WordsHelper.setDefaultLocale(Locale.getDefault());
        if (listFragment == null)
            listFragment = new ListFragment();
        if (detailFragment == null)
            detailFragment = new DetailFragment();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //This is layout for fragments (list or detail)
        container = (View) findViewById(R.id.container);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        logo = (ImageView) findViewById(R.id.navigation_image_view);
        title = (TextView) findViewById(R.id.toolbar_title);
        //Handler "back" onClick returns from detail to list fragment
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IS_DEBUG)
                    Log.d(LOG_TAG, "Toolbar click view: " + v.getId());
                if (getFragmentManager().getBackStackEntryCount() != 0) {
                    flipFragment();
                    title.setText(R.string.artists);
                    logo.setVisibility(View.GONE);
                }
            }
        });
        //first filling container after creating activity - listFragment
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, listFragment);
        fragmentTransaction.commit();

    }

    @Override
    protected void onResume() {
        //registering BroadCastReceiver for receive Artist POJO from item ListFragment and push it to detailFragment
        clickItemListReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Utils.IS_DEBUG)
                    Log.d(Utils.LOG_TAG, "receive broadcast");
                Artist artist = intent.getParcelableExtra(EXTRA_LIST_INTENT_TAG);
                title.setText(artist.getName());
                logo.setVisibility(View.VISIBLE);
                detailFragment.setArtist(artist);
                flipFragment();
            }
        };
        registerReceiver(clickItemListReceiver, new IntentFilter(Utils.RECEIVER_TAG_ITEM_LIST_CLICK));
        super.onResume();

    }

    @Override
    protected void onPause() {
        //Unregister Receiver. onStop method call are not guaranteed...
        unregisterReceiver(clickItemListReceiver);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        //If current view - detailFragment: first press Back returns to listFragment, else first
        //press Back close Activity
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            flipFragment();
            title.setText(R.string.artists);
            logo.setVisibility(View.GONE);
        } else super.onBackPressed();


    }

    public void flipFragment() {
        //This method ask state fragmentManager and replace listFragment and detailFragment use
        //third party lib FragmentTransactionExtended animation
        //https://github.com/DesarrolloAntonio/FragmentTransactionExtended
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FragmentTransactionExtended fragmentTransactionExtended = new FragmentTransactionExtended(this,
                    fragmentTransaction, listFragment, detailFragment, R.id.container);
            fragmentTransactionExtended.addTransition(FragmentTransactionExtended.GLIDE);
            fragmentTransactionExtended.commit();
        } else {
            getFragmentManager().popBackStack();
            title.setText(R.string.artists);
        }
    }

}
