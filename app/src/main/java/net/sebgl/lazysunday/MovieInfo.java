package net.sebgl.lazysunday;

import android.graphics.Movie;

public class MovieInfo {

    private int imageId;
    private String movieTitle;

    public MovieInfo(int imageId, String title){
        this.imageId = imageId;
        this.movieTitle = title;
    }

    public int getImageId() {
        return imageId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }
}
