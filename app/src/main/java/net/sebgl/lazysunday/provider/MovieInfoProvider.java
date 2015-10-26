package net.sebgl.lazysunday.provider;


import net.sebgl.lazysunday.movie.MovieInfo;

import java.util.List;

public interface MovieInfoProvider {

    List<MovieInfo> getPopularMovies();
}
