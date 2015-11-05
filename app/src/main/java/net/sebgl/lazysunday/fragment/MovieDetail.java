package net.sebgl.lazysunday.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.sebgl.lazysunday.R;
import net.sebgl.lazysunday.movie.MovieInfo;


public class MovieDetail extends Fragment {

    private MovieInfo movieInfo;

    public MovieDetail() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View movieDetailFragment = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(getString(R.string.extra_movie_info))) {
            this.movieInfo = intent.getParcelableExtra(getString(R.string.extra_movie_info));
            TextView textView = (TextView)movieDetailFragment.findViewById(R.id.movieDetailDescriptionTextView);
            textView.setText(movieInfo.getDescription());
        }
        return movieDetailFragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.moviedetail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            //updateMovies();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
