package net.sebgl.lazysunday.movie;

public class MovieInfo {

    private String imageUrl;
    private String movieTitle;

    public MovieInfo(String imageUrl, String title){
        this.movieTitle = title;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getMovieTitle() {
        return movieTitle;
    }
}
