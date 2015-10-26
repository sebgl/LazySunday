package net.sebgl.lazysunday.movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.sebgl.lazysunday.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MovieInfoAdapter extends BaseAdapter {

    private Context context;
    private final String LOG_TAG = this.getClass().getSimpleName();

    private List<MovieInfo> movies;

    public MovieInfoAdapter(Context context) {
        super();
        this.context = context;
        this.fillWithMockData();
    }

    private void fillWithMockData(){
        movies = new ArrayList<MovieInfo>();
        final MovieInfo[] movieArray = {
                new MovieInfo(R.drawable.sample_0, "Jurassic World"),
                new MovieInfo(R.drawable.sample_1, "Seul sur Mars"),
                new MovieInfo(R.drawable.sample_2, "Interstellar"),
                new MovieInfo(R.drawable.sample_3, "Terminator"),
                new MovieInfo(R.drawable.sample_4, "Vice Versa"),
                new MovieInfo(R.drawable.sample_5, "San Andreas"),
                new MovieInfo(R.drawable.sample_6, "A la poursuite de demain"),
                new MovieInfo(R.drawable.sample_7, "Pixels"),
                new MovieInfo(R.drawable.sample_8, "Mad Max"),
                new MovieInfo(R.drawable.sample_9, "Les Minions"),
                new MovieInfo(R.drawable.sample_10, "Ant-Man")
        };
        movies = Arrays.asList(movieArray);
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
        movieImageView.setImageResource(movies.get(position).getImageId());
        movieTextView.setText(movies.get(position).getMovieTitle());
        return movieItem;
    }
}
