package com.chrissha.moviepop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chrissha.moviepop.model.MovieData;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null && extras.getParcelable("movieData") != null)
        {
            MovieData movieData = extras.getParcelable("movieData");

            ImageView posterView = (ImageView)root.findViewById(R.id.posterView);
            TextView titleView = (TextView) root.findViewById(R.id.movieTitle);
            TextView dateView = (TextView) root.findViewById(R.id.date);
            TextView runtimeView = (TextView) root.findViewById(R.id.runtime);
            TextView ratingView = (TextView) root.findViewById(R.id.rating);
            TextView summaryView = (TextView) root.findViewById(R.id.summary);

            titleView.setText(movieData.title);
            dateView.setText(movieData.releaseDate);
            ratingView.setText(movieData.rating+"/10");
            summaryView.setText(movieData.summary);

            Picasso.with(getContext())
                    .load(movieData.posterURL)
                    .into(posterView);
        }

        return root;
    }
}
