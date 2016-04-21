package ru.yandex.android.andrew.yandexmobilisation;

import android.app.Application;
import android.util.Log;

import com.yandex.metrica.YandexMetrica;

import ru.yandex.android.andrew.yandexmobilisation.utils.Utils;

/**
 * Created by Andrew on 21.04.2016.
 */
public class mApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        YandexMetrica.activate(getApplicationContext(), Utils.YANDEX_METRICA_API_KEY);
        YandexMetrica.enableActivityAutoTracking(this);
        if (Utils.IS_DEBUG)
            Log.d(Utils.LOG_TAG, "YandexMetrica Activated");
    }
}
