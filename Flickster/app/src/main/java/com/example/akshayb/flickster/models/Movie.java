package com.example.akshayb.flickster.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.example.akshayb.flickster.R.id.swipeContainer;

/**
 * Created by akshayb on 11/9/16.
 */

@org.parceler.Parcel
public class Movie {

    public String getPosterPath() {
        return  String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }
    public String getOverview() {
        return overview;
    }
    public double getVoteAverage() { return voteAverage; }

    private String posterPath;
    private String originalTitle;
    private String overview;
    private double voteAverage;
    private String id;
    private String trailerURL;

    public String getId() {
        return id;
    }

    public String getTrailerURL() {
        return trailerURL;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    private String releaseDate;

    public String getBackdropPath() {
        return  String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    String backdropPath;

    // empty constructor needed by the Parceler library
    public Movie() {

    }

    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.voteAverage   = jsonObject.getDouble("vote_average");
        this.releaseDate   = jsonObject.getString("release_date");
        this.id            = jsonObject.getString("id");
        fetchMovieTrailerURL();
    }

    private void fetchMovieTrailerURL() {
        String url = "https://api.themoviedb.org/3/movie/" + this.id + "/trailers?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJSONTrailers = null;

                try {
                    movieJSONTrailers = response.getJSONArray("youtube");
                    if (movieJSONTrailers.length() > 0) {
                        trailerURL = movieJSONTrailers.getJSONObject(0).getString("source");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Movie> result = new ArrayList<>();
        for (int ix = 0; ix < jsonArray.length(); ix++) {
            try {
                result.add(new Movie(jsonArray.getJSONObject(ix)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return  result;
    }

}
