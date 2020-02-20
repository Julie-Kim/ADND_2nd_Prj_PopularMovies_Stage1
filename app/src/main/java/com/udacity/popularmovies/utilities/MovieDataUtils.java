package com.udacity.popularmovies.utilities;

public final class MovieDataUtils {

    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String IMAGE_SIZE = "w185";

    private static final String TEMP_IMAGE_URL = "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";

    public static String getMoviePosterFullPath(String moviePath) {
        return IMAGE_BASE_URL + IMAGE_SIZE + moviePath;
    }
}
