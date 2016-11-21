// Copyright (c) 2016 Akshay Bhandary (https://github.com/abhandary)
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.


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

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

/**
 * Created by akshayb on 11/9/16.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    final static int kNotPopularMovies = 0;
    final static int kPopularMovies    = 1;

    static class MovieViewHolder {
        @BindView(R.id.ivMovieImage)    ImageView   ivImage;
        @BindView(R.id.tvTitle)         TextView    tvTitle;
        @BindView(R.id.tvOverview)      TextView    tvOverview;

        public MovieViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ImageOnlyMovieViewHolder {
        @BindView(R.id.ivMovieImage)   ImageView ivImage;

        public ImageOnlyMovieViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
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

        // 3. if the view is not being reused then inflate the layout
        //    and attach a view holder to the inflated view.
        if (convertView == null) {
            convertView = getInflatedLayoutForType(type);
            Object viewHolder = getViewHolderObjectForType(type, convertView);
            convertView.setTag(viewHolder);
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
            ImageOnlyMovieViewHolder viewHolder = (ImageOnlyMovieViewHolder) convertView.getTag();
            viewHolder.ivImage.setImageResource(0);
            Picasso.with(getContext()).load(movieImagePath).into(viewHolder.ivImage);
        }


        // return the view
        return convertView;
    }

    private Object getViewHolderObjectForType(int type, View convertView) {
        int orientation = getContext().getResources().getConfiguration().orientation;

        if (orientation == ORIENTATION_LANDSCAPE) {
            return  new MovieViewHolder(convertView);
        } else {
            // in portrait, only popular movies get the image only view holder
            if (type == kPopularMovies) {
                return new ImageOnlyMovieViewHolder(convertView);
            } else {
                return  new MovieViewHolder(convertView);
            }
        }
    }

    private View getInflatedLayoutForType(int type) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View convertView;
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == ORIENTATION_LANDSCAPE) {
            if (type == kPopularMovies) {
                convertView = layoutInflater.inflate(R.layout.item_popular_movie_landscape, null);
            } else {
                convertView = layoutInflater.inflate(R.layout.item_movie_landscape, null);
            }
        } else {
            if (type == kPopularMovies) {
                convertView = layoutInflater.inflate(R.layout.item_popular_movie_portrait, null);
            } else {
                convertView = layoutInflater.inflate(R.layout.item_movie_portrait, null);
            }
        }

        return convertView;
    }
}
