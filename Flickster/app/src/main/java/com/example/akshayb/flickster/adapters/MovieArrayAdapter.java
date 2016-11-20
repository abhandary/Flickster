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

import static android.R.attr.orientation;
import static android.R.attr.resource;
import static android.R.attr.type;
import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static com.example.akshayb.flickster.R.id.tvOverview;
import static com.example.akshayb.flickster.R.id.tvTitle;

/**
 * Created by akshayb on 11/9/16.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    final static int kNotPopularMovies = 0;
    final static int kPopularMovies    = 1;

    private static class MovieViewHolder {
        ImageView   ivImage;
        TextView    tvTitle;
        TextView    tvOverview;
    }

    private static class PopularMovieViewHolder {
        ImageView ivImage;
        ImageView ivPlayButtonOverlay;
    }

    public MovieArrayAdapter(Context context,  List<Movie> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        Movie movie = getItem(position);
        if (movie.getVoteAverage() >= 5) {
            return kPopularMovies;
        }
        return kNotPopularMovies;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Get the data item for the position
        Movie movie = getItem(position);

        // 2. check if we need a new view and can't reuse an existing view
        int type = getItemViewType(position);

        // 3. if the view is not being reused, then get inflate the layout
        //    and attach a view holder to the inflated view.
        if (convertView == null) {
            convertView = getInflatedLayoutForType(type);
        }

        // 4. Get the orientation.
        String movieImagePath;
        int orientation = getContext().getResources().getConfiguration().orientation;

        // 5. If the orientation is landscape then use the backdrop image regardless of type.
        //    If the movie is popular then use then also use the backdrop image in both orientations
        if ((orientation == ORIENTATION_LANDSCAPE) || (type == kPopularMovies)) {
            movieImagePath = movie.getBackdropPath();
        } else {
            movieImagePath = movie.getPosterPath();
        }


        if (type == kNotPopularMovies || (type == kPopularMovies && orientation == ORIENTATION_LANDSCAPE)) {
            MovieViewHolder viewHolder = (MovieViewHolder) convertView.getTag();
            // clear out the image from last time
            viewHolder.ivImage.setImageResource(0);

            // populate the data
            viewHolder.tvTitle.setText(movie.getOriginalTitle());
            viewHolder.tvOverview.setText(movie.getOverview());
            if (orientation == ORIENTATION_LANDSCAPE) {
                viewHolder.tvOverview.setMaxLines(4);
            }
            Picasso.with(getContext()).load(movieImagePath).into(viewHolder.ivImage);
        } else {
            PopularMovieViewHolder viewHolder = (PopularMovieViewHolder) convertView.getTag();
            viewHolder.ivImage.setImageResource(0);
            Picasso.with(getContext()).load(movieImagePath).into(viewHolder.ivImage);
        }


        // return the view
        return convertView;
    }

    private Object getViewHolderObjectForType(int type, View convertView) {
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (type == kNotPopularMovies || (type == kPopularMovies && orientation == ORIENTATION_LANDSCAPE)) {
            MovieViewHolder viewHolder = new MovieViewHolder();
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            viewHolder.tvTitle = (TextView) convertView.findViewById(tvTitle);
            viewHolder.tvOverview = (TextView) convertView.findViewById(tvOverview);
            return viewHolder;
        } else {
            PopularMovieViewHolder viewHolder = new PopularMovieViewHolder();
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            return viewHolder;
        }
    }

    private View getInflatedLayoutForType(int type) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View convertView;
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (type == kNotPopularMovies || (type == kPopularMovies && orientation == ORIENTATION_LANDSCAPE)) {
            convertView = layoutInflater.inflate(R.layout.item_movie, null);
        } else {
            convertView = layoutInflater.inflate(R.layout.item_popular, null);
        }
        Object viewHolder = getViewHolderObjectForType(type, convertView);
        convertView.setTag(viewHolder);
        return convertView;
    }
}
