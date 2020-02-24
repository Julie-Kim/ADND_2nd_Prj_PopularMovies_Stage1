package com.udacity.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.popularmovies.model.Movie;
import com.udacity.popularmovies.utilities.MovieJsonUtils;
import com.udacity.popularmovies.utilities.NetworkUtils;
import com.udacity.popularmovies.utilities.PreferenceUtils;

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

        String softBy = NetworkUtils.getSoftByParam(this);
        Log.d(TAG, "loadMovieData() softBy " + softBy);

        new FetchMovieDataTask().execute(softBy);
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
            if (movieJsonResponse != null) {
                ArrayList<Movie> movieList = MovieJsonUtils.parseMovieJson(movieJsonResponse);
                Log.d(TAG, "FetchMovieDataTask, size of movieList: " + movieList.size());

                return movieList;
            } else {
                Log.e(TAG, "FetchMovieDataTask, No json response.");
                return null;
            }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                mMovieAdapter.setMovieData(new ArrayList<>());

                loadMovieData();
                return true;

            case R.id.action_sortby:
                showSortBySelectionDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSortBySelectionDialog() {
        int checkedItem = PreferenceUtils.getSoftBySettingValue(this);
        Log.d(TAG, "showSortBySelectionDialog() checked item: " + checkedItem);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.action_sort_by)
                .setSingleChoiceItems(R.array.sort_by_setting_strings,
                        checkedItem,
                        (dialog1, which) -> {
                            Log.d(TAG, "showSortBySelectionDialog() clicked item: " + which);
                            PreferenceUtils.setSoftBySettingValue(MainActivity.this, which);
                        })
                .setPositiveButton(R.string.ok, (dialog12, which) -> {
                    loadMovieData();
                }).create().show();
    }
}