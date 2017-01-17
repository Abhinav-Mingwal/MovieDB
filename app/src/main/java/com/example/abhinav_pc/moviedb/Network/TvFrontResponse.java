package com.example.abhinav_pc.moviedb.Network;

import com.example.abhinav_pc.moviedb.TvDataFront;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by AbHiNav-PC on 20-Oct-16.
 */
public class TvFrontResponse {

    public ArrayList<TvDataFront> getTv() {
        return tv;
    }

    public void setTv(ArrayList<TvDataFront> tv) {
        this.tv = tv;
    }

    @SerializedName("results")
    ArrayList<TvDataFront> tv;
}
