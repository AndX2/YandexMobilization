package ru.yandex.android.andrew.yandexmobilisation.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.yandex.android.andrew.yandexmobilisation.R;
import ru.yandex.android.andrew.yandexmobilisation.custom.CustomImageView;
import ru.yandex.android.andrew.yandexmobilisation.pojo.Artist;
import ru.yandex.android.andrew.yandexmobilisation.utils.Utils;
import ru.yandex.android.andrew.yandexmobilisation.utils.WordsHelper;

/**
 * Created by Andrew on 03.04.2016.
 */
public class RecyclerArtistListAdapter extends RecyclerView.Adapter<RecyclerArtistListAdapter.ViewHolder> {

    private List<Artist> list;

    public void setList(List<Artist> _list) {
        list = _list;
    }

    private Context context;


    public RecyclerArtistListAdapter(Context _context, List<Artist> _list) {
        context = _context;
        list = _list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvName.setText(list.get(position).getName());
        holder.tvGenres.setText(WordsHelper.genresArrayToString(list.get(position).getGenres()));
        holder.tvNumbers.setText(WordsHelper.getAlbumsSongsTitle(
                list.get(position).getAlbumNumber(), list.get(position).getTracksNumber()));
        holder.customImageView.loadImage(list.get(position).getCover().getSmall(), R.drawable.loading_image_100,
                R.drawable.loading_error_100, true);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.IS_DEBUG)
                    Log.d(Utils.LOG_TAG, "onClick for position " + position);
                sendNotifyItemClicked(list.get(position));

            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvName;
        TextView tvGenres;
        TextView tvNumbers;
        CustomImageView customImageView;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.cardView);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvGenres = (TextView) view.findViewById(R.id.tv_genres);
            tvNumbers = (TextView) view.findViewById(R.id.tv_numbers);
            customImageView = (CustomImageView) view.findViewById(R.id.image_small);
        }
    }

    private void sendNotifyItemClicked(Artist artist) {
        Intent intent = new Intent(Utils.RECEIVER_TAG_ITEM_LIST_CLICK);
        intent.putExtra(Utils.EXTRA_LIST_INTENT_TAG, artist);
        if (Utils.IS_DEBUG)
            Log.d(Utils.LOG_TAG, "context = " + context);
        context.sendBroadcast(intent);
        if (Utils.IS_DEBUG)
            Log.d(Utils.LOG_TAG, "send Broadcast");

    }


}
