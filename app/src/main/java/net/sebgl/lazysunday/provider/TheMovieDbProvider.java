package net.sebgl.lazysunday.provider;

import android.net.Uri;
import android.util.Log;

import net.sebgl.lazysunday.R;
import net.sebgl.lazysunday.movie.MovieInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TheMovieDbProvider implements MovieInfoProvider {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private final String apiKey;
    private final String baseUrl = "http://api.themoviedb.org/3";

    public TheMovieDbProvider(String apiKey){
        this.apiKey = apiKey;
    }

    public Uri.Builder buildBaseUri(){
        return Uri.parse(baseUrl)
                .buildUpon()
                .appendQueryParameter("api_key", apiKey);
    }

    public URL uriToUrl(Uri uri){
        URL url;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Malformed URL: "+uri.toString(), e);
            url = null;
        }
        return url;
    }

    public URL buildUrl(List<String> paths, Map<String, String> parameters){
        Uri.Builder uriBuilder = buildBaseUri();
        // add url segments
        for (String path: paths){
            uriBuilder.appendPath(path);
        }
        // add each query parameter
        for (Map.Entry<String, String> entry: parameters.entrySet()){
            uriBuilder.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        Uri uri = uriBuilder.build();
        return uriToUrl(uri);
    }

    public String readInputStream(InputStream inputStream){
        if (inputStream==null){
            Log.e(LOG_TAG, "The provided input stream is null");
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            // read line by line for better JSON output logging
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
        } catch (IOException e){
            Log.e(LOG_TAG, "Error while reading the input stream", e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Unable to close BufferedReader stream", e);
            }
        }
        return buffer.toString();
    }

    public String executeGetRequest(URL url){
        HttpURLConnection urlConnection = null;
        String result;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            result = readInputStream(inputStream);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error while executing get request for "+url.toString(), e);
            result = "";
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return result.toString();
    }

    @Override
    public List<MovieInfo> getPopularMovies() {
        List<String> paths = Arrays.asList("discover", "movie");
        Map<String, String> parameters = new HashMap<>();
//        parameters.put("sort_by", "popularity");
        URL url = buildUrl(paths, parameters);
        String serverResponse = executeGetRequest(url);
        Log.v(LOG_TAG, "Executing get request with url :"+url);
        List<MovieInfo> movies;
        try {
            movies = readFromServerResponse(serverResponse);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error while reading server response: "+serverResponse, e);
            movies = new ArrayList<>();
        }
        return movies;
    }

    public List<MovieInfo> readFromServerResponse(String serverResponse)
        throws JSONException{
        List<MovieInfo> movies = new ArrayList<>();
        JSONObject jsonObj = new JSONObject(serverResponse);
        JSONArray serverResults = jsonObj.getJSONArray("results");
        for (int i=0; i<serverResults.length(); i++){
            String title = serverResults.getJSONObject(i).getString("original_title");
            String posterPath = serverResults.getJSONObject(i).getString("poster_path");
            int imageId = R.drawable.sample_0; //TODO: process poster_path instead
            MovieInfo movieInfo = new MovieInfo(imageId, title);
            movies.add(movieInfo);
        }
        return movies;
    }
}
