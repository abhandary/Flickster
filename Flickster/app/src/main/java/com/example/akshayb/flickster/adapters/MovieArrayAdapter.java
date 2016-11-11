package com.example.akshayb.flickster.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akshayb.flickster.R;
import com.example.akshayb.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.R.attr.resource;
import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static com.example.akshayb.flickster.R.id.tvOverview;
import static com.example.akshayb.flickster.R.id.tvTitle;

/**
 * Created by akshayb on 11/9/16.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    private static class ViewHolder {
        ImageView   ivImage;
        TextView    tvTitle;
        TextView    tvOverview;
    }


    public MovieArrayAdapter(Context context,  List<Movie> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Get the data item for the position
        Movie movie = getItem(position);

        // 2. check if we need a new view and can't reuse an existing view
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.item_movie, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            viewHolder.tvTitle = (TextView) convertView.findViewById(tvTitle);
            viewHolder.tvOverview = (TextView) convertView.findViewById(tvOverview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // clear out the image from last time
        viewHolder.ivImage.setImageResource(0);

        // populate the data
        viewHolder.tvTitle.setText(movie.getOriginalTitle());
        viewHolder.tvOverview.setText(movie.getOverview());

        String movieImagePath = movie.getPosterPath();

        if (getContext().getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            movieImagePath = movie.getBackdropPath();
            viewHolder.tvOverview.setMaxLines(4);
        }

        Picasso.with(getContext()).load(movieImagePath).into(viewHolder.ivImage);

        // return the view
        return convertView;
    }
}
