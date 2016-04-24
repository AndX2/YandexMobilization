package ru.yandex.android.andrew.yandexmobilisation.service;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.IS_DEBUG;
import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.LOG_TAG;
import static ru.yandex.android.andrew.yandexmobilisation.utils.Utils.getJsonFromUrl;

/**
 * Simple custom loader for getting json (String) use url
 */
public class NetLoader extends AsyncTaskLoader<String> {
    String url;

    public NetLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public String loadInBackground() {
        if (IS_DEBUG)
            Log.d(LOG_TAG, "Loader is begin loading");
        String json;
        json = getJsonFromUrl(url);
        if (IS_DEBUG)
            Log.d(LOG_TAG, "json is: " + json);
        return json;
    }

    @Override
    public void deliverResult(String data) {
        if (IS_DEBUG)
            Log.d(LOG_TAG, "Loader is delivered json: " + data);
        super.deliverResult(data);
    }
}
