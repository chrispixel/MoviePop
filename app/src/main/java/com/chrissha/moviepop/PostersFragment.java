package com.chrissha.moviepop;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.chrissha.moviepop.model.MovieDB;
import com.chrissha.moviepop.model.MovieData;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class PostersFragment extends Fragment {
    private MovieListAdapter mMovieAdapter;
    private GridView mGridView;
    private int preLast;
    private FetchMoviesTask mFetchMovieTask;
    private ArrayList<MovieData> movieList;
    public PostersFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_posters, container, false);

        mGridView = (GridView)rootView.findViewById(R.id.movie_grid_view);

        if(mMovieAdapter == null)
            mMovieAdapter = new MovieListAdapter(getActivity(),
                R.layout.grid_item,
                new ArrayList<MovieData>());


        mGridView.setAdapter(mMovieAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(getContext(), DetailActivity.class);
//                String dayForecast = mForecastAdapter.getItem(position);
                MovieData movie = mMovieAdapter.getItem(position);
                Bundle extras = new Bundle();
                extras.putParcelable("movieData", movie);
                detailIntent.putExtras(extras);
                startActivity(detailIntent);
                Toast.makeText(getContext(), movie.title, Toast.LENGTH_LONG).show();
            }
        });

        mGridView.setOnScrollListener(
                new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        final int lastItem = firstVisibleItem + visibleItemCount;

                        if(lastItem == totalItemCount) {
                            if(preLast!=lastItem){ //to avoid multiple calls for last item
                                Log.d("Last", "Last "+lastItem);
                                preLast = lastItem;
                            }
                        }

                    }
                }
        );

        mFetchMovieTask = new FetchMoviesTask();
        mFetchMovieTask.execute(MovieDB.MovieSort.POPULAR);

        return rootView;
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        outState.putParcelableArrayList("movies", movieList);
//        super.onSaveInstanceState(outState);
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.postersfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_refresh) {
//            updateWeather();
//            return true;
//        }

        if(!item.isChecked()) {

            item.setChecked(true);
            mFetchMovieTask = new FetchMoviesTask();

            if (id == R.id.menuSortNewest) {
                mFetchMovieTask.cancel(true);
//            mFetchMovieTask = new FetchMoviesTask();
//            mFetchMovieTask.execute(MovieDB.MovieSort.FAVORITES);
            } else if (id == R.id.menuSortRating) {
                mFetchMovieTask.execute(MovieDB.MovieSort.HIGHEST_RATED);
            } else if (id == R.id.menuSortPopular) {
                mFetchMovieTask.execute(MovieDB.MovieSort.POPULAR);
            }

        }
        return super.onOptionsItemSelected(item);
    }

    public class FetchMoviesTask extends AsyncTask<MovieDB.MovieSort, Void, ArrayList<MovieData>>
    {
        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();
        @Override
        protected ArrayList<MovieData> doInBackground(MovieDB.MovieSort... params) {
            MovieDB.MovieSort sort = params[0];

            return MovieDB.getMovies(sort);
        }

        @Override
        protected void onPostExecute(ArrayList<MovieData> output)
        {
            if(output != null) {
                movieList = output;
                mMovieAdapter.clear();
                mMovieAdapter.addAll(output);
                Log.d(LOG_TAG, "Added "+output.size()+" items.");
            }
            else
                Log.d(LOG_TAG,"Output is null.");
        }

    }
}
