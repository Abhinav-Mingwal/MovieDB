package com.example.abhinav_pc.moviedb.Network;

import com.example.abhinav_pc.moviedb.TvDataFront;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by AbHiNav-PC on 04-Oct-16.
 */
public interface ApiServices {
    //    For MOVIES

    @GET("movie/{id}")
    Call<MovieBackResponse> getMovieInfo(@Path("id") int id,@Query("api_key") String api_key);
    @GET("movie/{id}/videos")
    Call<YoutubeResponse> getTrailerInfo(@Path("id") int id,@Query("api_key") String api_key);

    @Headers({"content-type: application/json"})
    @GET("movie/popular?api_key=6ed2279f7b98c9369069fe4760ac0e1f&language=en-US")
    Call<MovieFrontResponse> getPopularMovies(@Query("page") int page);

    @Headers({"content-type: application/json"})
    @GET("movie/top_rated?api_key=6ed2279f7b98c9369069fe4760ac0e1f&language=en-US")
    Call<MovieFrontResponse> getTopRatedMovies(@Query("page") int page);

    @Headers({"content-type: application/json"})
    @GET("movie/upcoming?api_key=6ed2279f7b98c9369069fe4760ac0e1f&language=en-US")
    Call<MovieFrontResponse> getUpcomingMovies(@Query("page") int page);

    @Headers({"content-type: application/json"})
    @GET("movie/now_playing?api_key=6ed2279f7b98c9369069fe4760ac0e1f&language=en-US")
    Call<MovieFrontResponse> getInTheaterMovies(@Query("page") int page);

//    For TV
    @GET("tv/popular?api_key=6ed2279f7b98c9369069fe4760ac0e1f&language=en-US")
    Call<TvFrontResponse> getPopularTv(@Query("page") int page);

    @GET("tv/top_rated?api_key=6ed2279f7b98c9369069fe4760ac0e1f&language=en-US")
    Call<TvFrontResponse> getTopRatedTv(@Query("page") int page);

    @GET("tv/airing_today?api_key=6ed2279f7b98c9369069fe4760ac0e1f&language=en-US")
    Call<TvFrontResponse> getAiringTodayTv(@Query("page") int page);

    @GET("tv/on_the_air?api_key=6ed2279f7b98c9369069fe4760ac0e1f&language=en-US")
    Call<TvFrontResponse> getOnAirTv(@Query("page") int page);

    @GET("https://api.themoviedb.org/3/tv/{id}?api_key=6ed2279f7b98c9369069fe4760ac0e1f&language=en-US")
    Call<TvBackResponse> getTVDetails(@Path("id") String id);
}
