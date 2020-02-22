package com.udacity.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.popularmovies.model.Movie;
import com.udacity.popularmovies.utilities.MovieJsonUtils;
import com.udacity.popularmovies.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int DEFAULT_POSTER_WIDTH = 540;

    @BindView(R.id.rv_movie_poster)
    RecyclerView mMovieRecyclerView;

    private MovieAdapter mMovieAdapter;

    @BindView(R.id.tv_empty_message)
    TextView mEmptyMessage;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, DEFAULT_POSTER_WIDTH);
        mMovieRecyclerView.setLayoutManager(layoutManager);
        mMovieRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);
        mMovieRecyclerView.setAdapter(mMovieAdapter);

        loadMovieData();
    }

    private void loadMovieData() {
        showOrHideMovieData(true);

        new FetchMovieDataTask().execute(NetworkUtils.PARAM_POPULAR);
    }

    private void showOrHideMovieData(boolean show) {
        if (show) {
            mEmptyMessage.setVisibility(View.INVISIBLE);
            mMovieRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mMovieRecyclerView.setVisibility(View.INVISIBLE);
            mEmptyMessage.setVisibility(View.VISIBLE);
        }
    }

    private void showOrHideLoadingIndicator(boolean show) {
        if (show) {
            mLoadingIndicator.setVisibility(View.VISIBLE);
        } else {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(Movie movie) {
        //TODO: start DetailActivity by intent
    }

    public class FetchMovieDataTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            showOrHideLoadingIndicator(true);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            if (params.length == 0) {
                Log.e(TAG, "FetchMovieDataTask, no sortBy parameter.");
                return null;
            }

            String sortBy = params[0];
            if (TextUtils.isEmpty(sortBy)) {
                Log.e(TAG, "FetchMovieDataTask, wrong sortBy parameter.");
                return null;
            }

            URL movieRequestUrl = NetworkUtils.buildUrl(sortBy);

            String movieJsonResponse = NetworkUtils.getJsonResponse(movieRequestUrl);
            ArrayList<Movie> movieList = MovieJsonUtils.parseMovieJson(movieJsonResponse);

            Log.d(TAG, "FetchMovieDataTask, size of movieList: " + movieList.size());

            return movieList;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);

            showOrHideLoadingIndicator(false);

            if (movies != null && !movies.isEmpty()) {
                showOrHideMovieData(true);
                mMovieAdapter.setMovieData(movies);
            } else {
                showOrHideMovieData(false);
            }
        }
    }
}
