package com.chrissha.moviepop.model;

import android.net.Uri;

import com.chrissha.library.utility.http.HttpManager;
import com.chrissha.moviepop.BuildConfig;
import com.chrissha.moviepop.parser.TMDBParser;

import java.util.ArrayList;


/**
 * Created by Chris on 5/6/16.
 */
public class MovieDB {
    private static final String LOG_TAG = MovieDB.class.getSimpleName();

    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185";// w500/8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg
    private static final String DISCOVER_BASE_URL = "https://api.themoviedb.org/3/discover/movie";// sort_by=popularity.desc&api_key=deb1d71ce5c7b0b4465385a6f3b49aa9
    public enum MovieSort{
        POPULAR,
        HIGHEST_RATED,
        FAVORITES
    }

    public static ArrayList<MovieData> getMovies(MovieSort movieSort)
    {
        Uri.Builder builder = Uri.parse(DISCOVER_BASE_URL).buildUpon();

        if(movieSort.equals(MovieSort.POPULAR)) {
            builder.appendQueryParameter("sort_by", "popularity.desc");
        }
        else if(movieSort.equals(MovieSort.HIGHEST_RATED)) {
            builder.appendQueryParameter("sort_by", "vote_average.desc");
            builder.appendQueryParameter("vote_count.gte", "500");
            builder.appendQueryParameter("language", "en");
        }
        else if(movieSort.equals(MovieSort.FAVORITES))
        {}
        Uri uri = builder
                .appendQueryParameter("api_key", BuildConfig.TMDBAPI)
                .build();


        String moviesString = HttpManager.okHttpGet(uri.toString());

//        Log.d(LOG_TAG, moviesString);

        if(moviesString != null) {
            return TMDBParser.movieListParser(moviesString);
        }
        else
            return null;

    }
}
