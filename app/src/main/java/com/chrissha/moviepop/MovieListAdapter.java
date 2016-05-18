package com.chrissha.moviepop;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.chrissha.moviepop.model.MovieData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Chris on 5/8/16.
 */
public class MovieListAdapter extends ArrayAdapter<MovieData>{
    private static final String LOG_TAG = MovieListAdapter.class.getSimpleName();
    private Context context;

    public MovieListAdapter(Context context, int resource, List<MovieData> movieList) {
        super(context, resource, movieList);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            convertView =
                    View.inflate (context,R.layout.grid_item, null);
//            ((Activity)context).getLayoutInflater().inflate(R.layout.grid_item, parent, false);
        }

        ImageView posterView = (ImageView) convertView;
//        ImageView posterView = (ImageView) convertView.findViewById(R.id.grid_item);

//        posterView.setImageResource( R.mipmap.ic_launcher);

        MovieData movieData = this.getItem(position);

        Picasso.with(context)
                .load(movieData.posterURL)
                .into(posterView);

//        Glide.with(context)
//                .load(movieData.posterURL)
////                .placeholder(R.mipmap.ic_launcher)
//                .into(posterView);


        Log.d(LOG_TAG, movieData.posterURL);
        return convertView;
    }
}
