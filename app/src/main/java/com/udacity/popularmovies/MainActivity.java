package com.udacity.popularmovies;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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

        //TODO: fetch movie data

        //TODO: delete test code - Loading fake movie data
        ArrayList<String> tempMovieData = new ArrayList<>();
        tempMovieData.add("//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg");
        tempMovieData.add("//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg");
        tempMovieData.add("//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg");
        tempMovieData.add("//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg");
        tempMovieData.add("//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg");
        mMovieAdapter.setMovieData(tempMovieData);
    }

    @Override
    public void onClick(String movie) {
        //TODO: start DetailActivity by intent
    }
}
