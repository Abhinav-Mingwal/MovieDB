package com.example.abhinav_pc.moviedb.Network;

import com.example.abhinav_pc.moviedb.MovieDataFront;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by AbHiNav-PC on 04-Oct-16.
 */
public class MovieFrontResponse {
    public ArrayList<MovieDataFront> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<MovieDataFront> movies) {
        this.movies = movies;
    }

    @SerializedName("results")
    ArrayList<MovieDataFront> movies;

    public int page;
    public int total_results;
    public int total_pages;

}
