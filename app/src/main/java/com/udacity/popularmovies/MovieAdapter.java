package com.udacity.popularmovies;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.utilities.MovieDataUtils;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();

    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(String movie);
    }

    private ArrayList<String> mMoviePosterPath = new ArrayList<>();

    MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mMovieImageView;

        MovieAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mMovieImageView = itemView.findViewById(R.id.movie_poster_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String movie = mMoviePosterPath.get(getAdapterPosition());
            mClickHandler.onClick(movie);
        }
    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_grid_item, parent, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {
        String posterPath = MovieDataUtils.getMoviePosterFullPath(mMoviePosterPath.get(position));
        Log.d(TAG, "onBindViewHolder() posterPath: " + posterPath);

        Picasso.get()
                .load(posterPath)
                .fit()
                .into(holder.mMovieImageView//,
                        //TODO: add callback - ImageLoadedCallback
                );
        //TODO: loading fail case image (placeholder, error, progressbar)
    }

    @Override
    public int getItemCount() {
        if (mMoviePosterPath.isEmpty()) {
            return 0;
        }
        return mMoviePosterPath.size();
    }

    void setMovieData(ArrayList<String> moviePosterPath) {
        mMoviePosterPath.clear();
        mMoviePosterPath.addAll(moviePosterPath);
        notifyDataSetChanged();
    }
}
