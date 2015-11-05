package net.sebgl.lazysunday.provider;

import android.util.Log;

import net.sebgl.lazysunday.movie.MovieInfo;
import net.sebgl.lazysunday.utils.UrlUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private final String posterBaseUrl = "http://image.tmdb.org/t/p";
    private final String posterSize = "w185";

    public TheMovieDbProvider(String apiKey){
        this.apiKey = apiKey;
    }

    @Override
    public List<MovieInfo> getPopularMovies() {
        List<String> paths = Arrays.asList("discover", "movie");
        Map<String, String> parameters = new HashMap<>();
        URL url = buildUrlWithApiKey(baseUrl, paths, parameters);
        String serverResponse = UrlUtils.executeGetRequest(url);
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

    private List<MovieInfo> readFromServerResponse(String serverResponse)
        throws JSONException{
        List<MovieInfo> movies = new ArrayList<>();
        JSONObject jsonObj = new JSONObject(serverResponse);
        JSONArray serverResults = jsonObj.getJSONArray("results");
        for (int i=0; i<serverResults.length(); i++){
            JSONObject movie = serverResults.getJSONObject(i);
            MovieInfo movieInfo = MovieInfoFromServerJSONMovie(movie);
            movies.add(movieInfo);
        }
        return movies;
    }

    private MovieInfo MovieInfoFromServerJSONMovie(JSONObject serverResponse)
        throws JSONException {
        String title = serverResponse.getString("original_title");
        String description = serverResponse.getString("overview");
        Log.v(LOG_TAG, description);
        String posterPath = serverResponse.getString("poster_path");
        String posterFullUrl = buildPosterFullURL(posterPath).toString();
        MovieInfo movieInfo = new MovieInfo(posterFullUrl, title, description);
        return movieInfo;
    }

    private URL buildPosterFullURL(String relativePath){
        List<String> paths = new ArrayList<>();
        paths.add(posterSize);
        List<String> encodedPaths = new ArrayList<>();
        encodedPaths.add(relativePath);
        return UrlUtils.buildUrl(posterBaseUrl, paths, encodedPaths);
    }

    private URL buildUrlWithApiKey(String baseUrl, List<String> paths, Map<String, String> parameters){
        parameters.put("api_key", apiKey);
        return UrlUtils.buildUrl(baseUrl, paths, parameters);
    }
}
