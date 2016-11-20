package com.example.akshayb.flickster.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.akshayb.flickster.R;
import com.example.akshayb.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

public class MovieDetailActivity extends AppCompatActivity {

    ImageView   ivPoster;
    TextView    tvTitle;
    RatingBar   rbRating;
    TextView    tvReleaseDate;
    TextView    tvSynopsis;

    Movie movie;

    private static final String  SELECTED_MOVIE  = "SELECTED_MOVIE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(SELECTED_MOVIE));
        setupViews();
    }

    private void setupViews() {
        ivPoster = (ImageView) findViewById(R.id.ivPoster);
        tvTitle  = (TextView) findViewById(R.id.tvTitle);
        rbRating  = (RatingBar) findViewById(R.id.rbMovieRating);
        tvReleaseDate  = (TextView) findViewById(R.id.tvReleaseDate);
        tvSynopsis  = (TextView) findViewById(R.id.tvSynopsis);

        Picasso.with(getApplicationContext()).load(movie.getBackdropPath()).into(ivPoster);
        tvTitle.setText(movie.getOriginalTitle());
        tvSynopsis.setText(movie.getOverview());
        tvReleaseDate.setText("Release Date: " + movie.getReleaseDate());
        double vote = movie.getVoteAverage() / 2.0;
        rbRating.setNumStars((int) vote);
    }
}
