package com.udacity.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.toString();

    private static final String BASE_MOVIE_URL = "http://api.themoviedb.org/3/movie/";

    public static final String PARAM_POPULAR = "popular";
    public static final String PARAM_TOP_RATED = "top_rated";

    private static final String PARAM_API_KEY = "api_key";
    private static final String API_KEY = "";   //Your own API Key is required.

    public static URL buildUrl(String sortBy) {
        Uri uri = Uri.parse(BASE_MOVIE_URL).buildUpon()
                .appendPath(sortBy)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "buildUrl() url = " + url);
        return url;
    }

    public static String getJsonResponse(URL url) {
        if (url == null) {
            Log.e(TAG, "getJsonResponse() url is null.");
            return null;
        }

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                Log.e(TAG, "getJsonResponse() no input.");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return null;
    }
}
