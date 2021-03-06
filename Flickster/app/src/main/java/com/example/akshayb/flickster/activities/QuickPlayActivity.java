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

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akshayb.flickster.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by akshayb on 11/20/16.
 */

public class QuickPlayActivity extends YouTubeBaseActivity {

    private static final String  SELECTED_MOVIE_TRAILER_URL = "SELECTED_MOVIE_TRAILER_URL";
    private static final String  API_KEY = "AIzaSyBabm9IhlRnW5J_S4vM6BmHO3SlrcFSYzI";

    @BindView(R.id.player)  YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_play);
        ButterKnife.bind(this);

        final String trailerSource = getIntent().getStringExtra(SELECTED_MOVIE_TRAILER_URL);

        youTubePlayerView.initialize(API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        final YouTubePlayer youTubePlayer, boolean b) {

                        // do any work here to cue video, play video, etc.
                        youTubePlayer.cueVideo(trailerSource);
                        youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                            @Override
                            public void onLoading() {
                                // required implementation
                            }

                            @Override
                            public void onLoaded(String s) {
                                youTubePlayer.play();
                            }

                            @Override
                            public void onAdStarted() {
                                // required implementation
                            }

                            @Override
                            public void onVideoStarted() {
                                // required implementation
                            }

                            @Override
                            public void onVideoEnded() {
                                // required implementation
                            }

                            @Override
                            public void onError(YouTubePlayer.ErrorReason errorReason) {
                                Toast.makeText(getApplicationContext(), "Video Playback Failed", Toast.LENGTH_SHORT);
                            }
                        });
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        Toast.makeText(getApplicationContext(), "Video Player Failed to Initialize", Toast.LENGTH_SHORT);
                    }
                });
    }
}
