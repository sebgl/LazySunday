package net.sebgl.lazysunday.movie;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieInfo implements Parcelable {

    private String imageUrl;
    private String title;
    private String description;

    public MovieInfo(String imageUrl, String title, String description){
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeString(title);
        dest.writeString(description);
    }

    public MovieInfo(Parcel in){
        // must respect same fifo order as writeToParcel
        this.imageUrl = in.readString();
        this.title = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<MovieInfo> CREATOR = new Parcelable.Creator<MovieInfo>() {
        @Override
        public MovieInfo createFromParcel(Parcel source) {
            return new MovieInfo(source);
        }

        @Override
        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };

    public String getImageUrl() {
        return imageUrl;
    }

    public String getMovieTitle() {
        return title;
    }

    public String getDescription() { return description; }
}
