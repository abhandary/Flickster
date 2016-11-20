package com.example.akshayb.flickster.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
