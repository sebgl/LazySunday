package net.sebgl.lazysunday.bgtask;

import android.os.AsyncTask;
import android.util.Log;

import net.sebgl.lazysunday.movie.MovieInfo;
import net.sebgl.lazysunday.movie.MovieInfoAdapter;
import net.sebgl.lazysunday.provider.MovieInfoProvider;

import java.util.List;


public class FetchMoviesTask extends AsyncTask<MovieInfoProvider, String, List<MovieInfo>> {

        private final String LOG_TAG = this.getClass().getSimpleName();
        private MovieInfoAdapter adapter;

        public FetchMoviesTask(MovieInfoAdapter adapter){
            super();
            this.adapter = adapter;
        }

        @Override
        protected List<MovieInfo> doInBackground(MovieInfoProvider... params) {
            if (params.length!=1){
                return null;
            }
            MovieInfoProvider provider = params[0];
            return provider.getPopularMovies();
        }

        @Override
        protected void onPostExecute(List<MovieInfo> movies) {
            if (adapter != null){
                List<MovieInfo> moviesFromAdapter = adapter.getMovies();
                if (movies.size() > 0){
                    // clear the existing movies only if we can replace them
                    moviesFromAdapter.clear();
                }
                for (MovieInfo movie: movies){
                    moviesFromAdapter.add(movie);
                }
                Log.v(LOG_TAG, "Fetched "+movies.size()+" movies");
                adapter.notifyDataSetChanged();
            }
        }

}
