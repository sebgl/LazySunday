package net.sebgl.lazysunday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ThumbnailAdapter extends BaseAdapter {

    private Context context;
    private final String LOG_TAG = this.getClass().getSimpleName();

    private static final int[] mockThumbnailsId = {
        R.drawable.sample_0, R.drawable.sample_1,
        R.drawable.sample_2, R.drawable.sample_3,
        R.drawable.sample_4, R.drawable.sample_5,
        R.drawable.sample_6, R.drawable.sample_7,
        R.drawable.sample_8, R.drawable.sample_9,
        R.drawable.sample_10
    };

    public ThumbnailAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return mockThumbnailsId.length;
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
        movieImageView.setImageResource(mockThumbnailsId[position]);
        movieTextView.setText(""+position);
        return movieItem;
    }
}
