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


package com.example.akshayb.flickster.activities;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.akshayb.flickster.R;
import com.example.akshayb.flickster.adapters.MovieArrayAdapter;
import com.example.akshayb.flickster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieActivity extends AppCompatActivity {

    static final String TAG = "DEBUG";

    ArrayList<Movie> movies;
    MovieArrayAdapter movieArrayAdapter;
    @BindView(R.id.lvMovies) ListView lvItems;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;

    private static final int     SHOW_MOVIE_DETAIL_REQUEST = 1;
    private static final int     SHOW_MOVIE_TRAILER_REQUEST = 2;
    private static final String  SELECTED_MOVIE  = "SELECTED_MOVIE";
    private static final String  SELECTED_MOVIE_TRAILER_URL = "SELECTED_MOVIE_TRAILER_URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchMovies();
            }
        });

        movies = new ArrayList<Movie>();
        movieArrayAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieArrayAdapter);

        lvItems.setOnItemClickListener((adapterView, view, i, l) -> {
            taskSelected(i);
        });

        fetchMovies();
    }

    private void taskSelected(int posistion) {

        Movie movie = movies.get(posistion);
        if (movie.getVoteAverage() >= 5 && movie.getTrailerURL() != null) {
            Intent intent = new Intent(MovieActivity.this, QuickPlayActivity.class);
            intent.putExtra(SELECTED_MOVIE_TRAILER_URL, movie.getTrailerURL());
            startActivityForResult(intent, SHOW_MOVIE_TRAILER_REQUEST);
        } else {
            Intent intent = new Intent(MovieActivity.this, MovieDetailActivity.class);
            intent.putExtra(SELECTED_MOVIE, Parcels.wrap(movie));
            startActivityForResult(intent, SHOW_MOVIE_DETAIL_REQUEST);
        }
    }

    private void setRefreshingFalseOnUIThread() {
        MovieActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swipeContainer.setRefreshing(false);
            }
        });
    }

    private void notifyArrayAdapterDataSetChangedOnUIThread(final ArrayAdapter adapter) {
        MovieActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void fetchMovies() {

        final String MOVIE_DB_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(MOVIE_DB_URL)
                .build();

        movies.clear();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                try {
                    String responseData = response.body().string();
                    JSONObject json = new JSONObject(responseData);
                    JSONArray movieJSONResults = json.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(movieJSONResults));
                    notifyArrayAdapterDataSetChangedOnUIThread(movieArrayAdapter);
                    setRefreshingFalseOnUIThread();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                setRefreshingFalseOnUIThread();
            }
        });
    }
}
