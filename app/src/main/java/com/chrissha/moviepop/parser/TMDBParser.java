package com.chrissha.moviepop.parser;

import com.chrissha.moviepop.model.MovieDB;
import com.chrissha.moviepop.model.MovieData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Chris on 5/8/16.
 */
public class TMDBParser {
    public static ArrayList<MovieData> movieListParser(String moviesString)
    {
        try {
            ArrayList<MovieData> movieList = new ArrayList<>();

            JSONArray jsonArray = new JSONObject(moviesString).getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject movieJSON = jsonArray.getJSONObject(i);
                MovieData movie = new MovieData();
                movie.backdropURL = MovieDB.IMAGE_BASE_URL+movieJSON.getString("backdrop_path");
                movie.posterURL = MovieDB.IMAGE_BASE_URL+movieJSON.getString("poster_path");
                movie.releaseDate = movieJSON.getString("release_date");
                movie.summary = movieJSON.getString("overview");
                movie.title = movieJSON.getString("title");
                movie.tmdbID = movieJSON.getString("id");
                movie.rating = movieJSON.getString("vote_average");
                movieList.add(movie);
            }
            return movieList;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
