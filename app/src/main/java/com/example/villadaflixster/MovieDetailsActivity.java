package com.example.villadaflixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.villadaflixster.models.Movie;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieDetailsActivity extends AppCompatActivity {

    // the movie to display
    Movie movie;

    // the view objects

    TextView tvTitleDetails;
    TextView tvOverviewDetails;
    RatingBar rbVoteAverage;
    TextView releaseDate;
    ImageView ivPosterDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // resolve the view objects
        tvTitleDetails = (TextView) findViewById(R.id.tvTitleDetails);
        tvOverviewDetails = (TextView) findViewById(R.id.tvOverviewDetails);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        releaseDate = (TextView) findViewById(R.id.releaseDate);
        ivPosterDetails = (ImageView) findViewById(R.id.ivPosterDetails);

        // unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // set the title and overview
        tvTitleDetails.setText(movie.getTitle());
        tvOverviewDetails.setText(movie.getOverview());
        releaseDate.setText("Release Date : " + movie.getReleaseDate());

        Context context = this;
        String imageUrl;

        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // if phone is in landscape
            // imageUrl = back drop image
            imageUrl = movie.getBackdropPath();
            int radius = 30; // corner radius, higher value = more rounded
            int margin = 10;
            Glide.with(context)
                    .load(imageUrl)
                    .fitCenter()
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .placeholder(R.drawable.flicks_backdrop_placeholder)
                    .into(ivPosterDetails);
        } else {
            // phone is in portrait
            // imageUrl = poster image
            imageUrl = movie.getPosterPath();
            int radius = 30; // corner radius, higher value = more rounded
            int margin = 10;
            Glide.with(context)
                    .load(imageUrl)
                    .fitCenter() // scale image to fill the entire ImageView
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .placeholder(R.drawable.flicks_movie_placeholder)
                    .into(ivPosterDetails);
        }

        // vote average is 0-10, convert to 0-5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage / 2.0f);
    }
}