# Flickster

This is an Android demo application for displaying the latest box office movies using the [Movie Database API](http://docs.themoviedb.apiary.io/#).

Time spent: 15 hours spent in total

*Completed user stories:*

 * [x] Required: User can view a list of movies (title, poster image, and overview) currently playing in theaters from the Movie Database API.
   ** Displays a default placeholder graphic for each image during loading
   ** User can pull-to-refresh the popular stream with SwipeRefreshLayout
 * [x] Required: Lists should be fully optimized for performance with the ViewHolder pattern. 
 * [x] Required: Views should be responsive for both landscape/portrait mode.

   * In portrait mode, the poster image, title, and movie overview is shown.

   * In landscape mode, the rotated layout should use the backdrop image instead and show the title and movie overview to the right of it.
 * [x] Optional: Use Styles and Themes to keep your layout files clean of view styles
 * [ ] Optional: Improve the user interface through styling and coloring (1 to 5 points depending on the difficulty of UI improvements)
 * [x] Optional: For popular movies (i.e. a movie voted for more than 5 stars), the full backdrop image is displayed. Otherwise, a poster image, the movie title, and overview is listed. Use Heterogenous ListViews and use different ViewHolder layout files for popular movies and less popular ones.
 * [x] Optional: Expose details of movie (ratings using RatingBar, popularity, and synopsis) in a separate activity.
 * [x] Stretch: Allow video posts to be played in full-screen using the YouTubePlayerView 

   * When clicking on a popular movie (i.e. a movie voted for more than 5 stars) the video should be played immediately.

   * Less popular videos rely on the details page which should show an image preview that can initiate playing a YouTube video.

   * Use the videos API to get trailer videos.
 * [x] Stretch: Add a play icon overlay to popular movies to indicate that the movie can be played
 * [ ] Stretch: Leverage the data binding support module to bind data into one or more activity layout templates.
 * [x] Stretch: Switch to using retrolambda expressions to cleanup event handling blocks.
 * [x] Stretch: Apply the popular ButterKnife annotation library to reduce view boilerplate. 
 * [x] Stretch: Add a rounded corners for the images using the Picasso transformations.
 * [x] Stretch: Replace android-async-http network client with the popular OkHttp networking library for all TheMovieDB API calls
 * [x] Stretch: Replace all icon drawables and other static image assets with vector drawables where appropriate.
 
Notes:

Walkthrough of all user stories:

![Video Walkthrough](flickster_demo.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).

*LICENSE:*
Copyright (c) 2016 Akshay Bhandary (https://github.com/abhandary)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.




