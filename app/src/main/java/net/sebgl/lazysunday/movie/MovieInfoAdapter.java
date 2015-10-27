package net.sebgl.lazysunday.movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.sebgl.lazysunday.R;

import java.util.ArrayList;
import java.util.List;


public class MovieInfoAdapter extends BaseAdapter {

    private Context context;
    private final String LOG_TAG = this.getClass().getSimpleName();

    private List<MovieInfo> movies;

    public MovieInfoAdapter(Context context) {
        super();
        this.context = context;
        this.movies = new ArrayList<>();
    }

    public List<MovieInfo> getMovies(){
        return movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View movieItem;
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            movieItem = inflater.inflate(R.layout.movie_grid_item, parent, false);
        }
        else {
            movieItem = convertView;
        }
        ImageView movieImageView = (ImageView)movieItem.findViewById(R.id.movieImageView);
        TextView movieTextView = (TextView)movieItem.findViewById(R.id.movieTextView);
        Picasso.with(context).load(movies.get(position).getImageUrl()).into(movieImageView);
        movieTextView.setText(movies.get(position).getMovieTitle());
        return movieItem;
    }
}
