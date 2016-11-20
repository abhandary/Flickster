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
    ImageView   ivPlayButton;

    Movie movie;

    private static final int     SHOW_MOVIE_TRAILER_REQUEST = 2;
    private static final String  SELECTED_MOVIE  = "SELECTED_MOVIE";
    private static final String  SELECTED_MOVIE_TRAILER_URL = "SELECTED_MOVIE_TRAILER_URL";

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
        ivPlayButton  = (ImageView) findViewById(R.id.ivVideoPreviewPlayButton);

        Picasso.with(getApplicationContext()).load(movie.getBackdropPath()).into(ivPoster);
        tvTitle.setText(movie.getOriginalTitle());
        tvSynopsis.setText(movie.getOverview());
        tvReleaseDate.setText("Release Date: " + movie.getReleaseDate());
        double vote = movie.getVoteAverage() / 2.0;
        rbRating.setNumStars((int) vote);

        // set up listerner for handling clicks on the play button.
        ivPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetailActivity.this, QuickPlayActivity.class);
                intent.putExtra(SELECTED_MOVIE_TRAILER_URL, movie.getTrailerURL());
                startActivityForResult(intent, SHOW_MOVIE_TRAILER_REQUEST);
            }
        });
    }
}
