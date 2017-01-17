package com.example.abhinav_pc.moviedb;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.abhinav_pc.moviedb.Network.ApiServices;
import com.example.abhinav_pc.moviedb.Network.EndlessRecyclerViewScrollListener;
import com.example.abhinav_pc.moviedb.Network.MovieFrontResponse;
import com.example.abhinav_pc.moviedb.Network.TvFrontResponse;
import com.example.abhinav_pc.moviedb.Network.URLconstants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class popular_TVshow extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private EndlessRecyclerViewScrollListener scrollListener;
    RecyclerView recyclerView;
    TvAdapter tvAdapter;
    ArrayList<TvDataFront> tvList;

    // TODO: Rename and change types of parameters
    private int mParam1;

    public popular_TVshow() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static popular_TVshow newInstance(int param1) {
        popular_TVshow fragment = new popular_TVshow();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_popular__tvshow,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.popularTvList);
        tvList = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(container.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        loadNextDataFromApi(1);
        tvAdapter = new TvAdapter(container.getContext(),tvList);
        recyclerView.setAdapter(tvAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page+1);
            }
        };
        // Adds the scroll listener to RecyclerView
        recyclerView.addOnScrollListener(scrollListener);
        return v;
    }
    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URLconstants.Movie_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices service = retrofit.create(ApiServices.class);
        Call<TvFrontResponse> call = service.getPopularTv(offset);
        call.enqueue(new Callback<TvFrontResponse>() {
            @Override
            public void onResponse(Call<TvFrontResponse> call, Response<TvFrontResponse> response) {
                ArrayList<TvDataFront> TemptvList= response.body().getTv();
                if(TemptvList==null){
                    return;
                }
                else{
                    for(TvDataFront temp:TemptvList){
                        tvList.add(temp);
                    }
                    tvAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<TvFrontResponse> call, Throwable t) {

            }
        });
    }


}
