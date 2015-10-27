package net.sebgl.lazysunday.utils;


import android.net.Uri;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlUtils {

    private static final String LOG_TAG = UrlUtils.class.getName();

    public static URL uriToUrl(Uri uri){
        URL url;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Malformed URL: " + uri.toString(), e);
            url = null;
        }
        return url;
    }

    public static URL buildUrl(String baseUrl, List<String> paths, List<String> encodedPaths,
                               Map<String, String> parameters) {
        Uri.Builder uriBuilder = Uri.parse(baseUrl).buildUpon();
        // add url segments
        for (String path: paths)
                uriBuilder.appendPath(path);
        for (String encodedPath : encodedPaths)
                uriBuilder.appendEncodedPath(encodedPath);
        // add each query parameter
        for (Map.Entry<String, String> entry: parameters.entrySet())
                uriBuilder.appendQueryParameter(entry.getKey(), entry.getValue());
        Uri uri = uriBuilder.build();
        return uriToUrl(uri);
    }

    public static URL buildUrl(String baseUrl, List<String> paths, Map<String, String> parameters){
        return buildUrl(baseUrl, paths, new ArrayList<String>(), parameters);
    }

    public static URL buildUrl(String baseUrl, List<String> paths, List<String> encodedPaths){
        return buildUrl(baseUrl, paths, encodedPaths, new HashMap<String, String>());
    }

    public static String executeGetRequest(URL url){
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

    public static String readInputStream(InputStream inputStream){
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


}
