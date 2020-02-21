package com.udacity.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.popularmovies.model.Movie;
import com.udacity.popularmovies.utilities.MovieJsonUtils;
import com.udacity.popularmovies.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int DEFAULT_POSTER_WIDTH = 540;

    private RecyclerView mMovieRecyclerView;
    private MovieAdapter mMovieAdapter;

    //TODO: TextView - error message
    //TODO: ProgressBar = loading indicator

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieRecyclerView = findViewById(R.id.rv_movie_poster);
        //TODO: TextView - error message
        //TODO: ProgressBar = loading indicator

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, DEFAULT_POSTER_WIDTH);
        mMovieRecyclerView.setLayoutManager(layoutManager);
        mMovieRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);
        mMovieRecyclerView.setAdapter(mMovieAdapter);

        loadMovieData();
    }

    private void loadMovieData() {
        //TODO: show movie poster list & hide error message

        new FetchMovieDataTask().execute(NetworkUtils.PARAM_POPULAR);
    }

    @Override
    public void onClick(Movie movie) {
        //TODO: start DetailActivity by intent
    }

    public class FetchMovieDataTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //TODO: show loading indicator
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            if (params.length == 0) {
                Log.e(TAG, "FetchMovieDataTask, no sortBy parameter.");
                return null;
            }

            String sortBy = params[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(sortBy);

            String movieJsonResponse = NetworkUtils.getJsonResponse(movieRequestUrl);
            ArrayList<Movie> movieList = MovieJsonUtils.parseMovieJson(movieJsonResponse);

            Log.d(TAG, "FetchMovieDataTask, size of movieList: " + movieList.size());

            return movieList;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);

            //TODO: hide loading indicator

            if (!movies.isEmpty()) {
                //TODO: if movie data is not empty, show movie data
                mMovieAdapter.setMovieData(movies);
            } else {
                //TODO: if movie data is empty, hide movie data and show error message
            }
        }
    }
}
