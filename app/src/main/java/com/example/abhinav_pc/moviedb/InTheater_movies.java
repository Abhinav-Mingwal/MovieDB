package com.example.abhinav_pc.moviedb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.example.abhinav_pc.moviedb.Network.URLconstants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class InTheater_movies extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private int mParam1;
    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    ArrayList<MovieDataFront> movieList;

    public InTheater_movies() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static InTheater_movies newInstance(int param1) {
        InTheater_movies fragment = new InTheater_movies();
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
        View v=inflater.inflate(R.layout.fragment_in_theater_movies,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.InTheaterMovieList);
        movieList = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(container.getContext(),2);
        recyclerView.setLayoutManager(mLayoutManager);
        loadNextDataFromApi(1);
        movieAdapter = new MovieAdapter(container.getContext(), movieList);
        recyclerView.setAdapter(movieAdapter);
        GridLayoutManager girGridLayoutManager = new GridLayoutManager(container.getContext(),2);
        recyclerView.setLayoutManager(girGridLayoutManager);
        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(girGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        recyclerView.addOnScrollListener(scrollListener);

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = recyclerView.getChildLayoutPosition(v);
                Intent i = new Intent();
                i.setClass(getActivity().getApplicationContext(), MovieActivity.class);
                int temp = movieList.get(itemPosition).movieID;
                i.putExtra("MovieID", temp);
                startActivity(i);
            }
        });
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
        Call<MovieFrontResponse> call = service.getInTheaterMovies(offset);
        call.enqueue(new Callback<MovieFrontResponse>() {
            @Override
            public void onResponse(Call<MovieFrontResponse> call, Response<MovieFrontResponse> response) {
                ArrayList<MovieDataFront> movieList = response.body().getMovies();
                if (movieList == null){
                    Log.i("ABCD", "FAILED");
                    return;
                }
                else {
                    Log.i("ABCD", "PASSED");
                    for (MovieDataFront obj : movieList) {
                        InTheater_movies.this.movieList.add(obj);
                    }
                    movieAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieFrontResponse> call, Throwable t) {

            }
        });
    }
}
