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
import android.widget.AdapterView;
import android.widget.GridView;

import net.sebgl.lazysunday.R;
import net.sebgl.lazysunday.activity.MovieDetailActivity;
import net.sebgl.lazysunday.bgtask.FetchMoviesTask;
import net.sebgl.lazysunday.movie.MovieInfo;
import net.sebgl.lazysunday.movie.MovieInfoAdapter;
import net.sebgl.lazysunday.provider.MovieInfoProvider;
import net.sebgl.lazysunday.provider.TheMovieDbProvider;


public class MovieGrid extends Fragment {

    private static final String LOG_TAG = MovieGrid.class.getName();
    private MovieInfoAdapter movieInfoAdapter;

    public MovieGrid() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        GridView gridView = (GridView)inflater.inflate(R.layout.fragment_movie_grid, container, false);
        movieInfoAdapter = new MovieInfoAdapter(getActivity());
        gridView.setAdapter(movieInfoAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieInfo movie = (MovieInfo)movieInfoAdapter.getItem(position);
                Intent movieDetailIntent = new Intent(getActivity(), MovieDetailActivity.class);
                movieDetailIntent.putExtra(getString(R.string.extra_movie_info), movie);
                startActivity(movieDetailIntent);
            }
        });
        // first time update
        updateMovies();
        return gridView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.moviegrid, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            updateMovies();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateMovies(){
        MovieInfoProvider provider = new TheMovieDbProvider(getString(R.string.apikey_themoviedb));
        new FetchMoviesTask(movieInfoAdapter).execute(provider);
    }

}
