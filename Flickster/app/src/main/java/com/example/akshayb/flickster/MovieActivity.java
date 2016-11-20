package com.example.akshayb.flickster;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.akshayb.flickster.adapters.MovieArrayAdapter;
import com.example.akshayb.flickster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.example.akshayb.flickster.models.Movie.fromJSONArray;

public class MovieActivity extends AppCompatActivity {

    static final String TAG = "DEBUG";

    ArrayList<Movie> movies;
    MovieArrayAdapter movieArrayAdapter;
    ListView lvItems;
    private SwipeRefreshLayout swipeContainer;

    private static final int     SHOW_MOVIE_DETAIL_REQUEST = 1;
    private static final String  SELECTED_MOVIE  = "SELECTED_MOVIE";
    private static final String  SELECTED_POSISTION  = "SELECTED_POSISTION";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        lvItems = (ListView) findViewById(R.id.lvMovies);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchMovies();
            }
        });

        movies = new ArrayList<Movie>();
        movieArrayAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieArrayAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                taskSelected(i);
            }
        });


        fetchMovies();
    }

    private void taskSelected(int posistion) {
        Movie movie = movies.get(posistion);
        Intent intent = new Intent(MovieActivity.this, MovieDetailActivity.class);
        intent.putExtra(SELECTED_MOVIE, movie);
        intent.putExtra(SELECTED_POSISTION, posistion);
        startActivityForResult(intent, SHOW_MOVIE_DETAIL_REQUEST);
    }


    private void fetchMovies() {
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJSONResults = null;

                try {
                    movies.clear();
                    movieJSONResults = response.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(movieJSONResults));
                    movieArrayAdapter.notifyDataSetChanged();
                    swipeContainer.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, movies.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                swipeContainer.setRefreshing(false);
            }
        });
    }
}
