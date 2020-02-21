package com.udacity.popularmovies.model;

import androidx.annotation.NonNull;

public class Movie {

    private String mTitle;
    private String mPosterPath;
    private String mOverview;   //A plot synopsis
    private int mVoteAverage;    //user rating
    private String mReleaseDate;

    public Movie(String title, String posterPath, String overview, int voteAverage, String releaseDate) {
        mTitle = title;
        mPosterPath = posterPath;
        mOverview = overview;
        mVoteAverage = voteAverage;
        mReleaseDate = releaseDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public int getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(int voteAverage) {
        mVoteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    @NonNull
    @Override
    public String toString() {
        return "Title: " + getTitle() +
                "\nPosterPath: " + getPosterPath() +
                "\nOverview: " + getOverview() +
                "\nVoteAverage: " + getVoteAverage() +
                "\nReleaseDate: " + getReleaseDate();
    }
}
