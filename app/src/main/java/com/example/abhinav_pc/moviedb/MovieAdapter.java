package com.example.abhinav_pc.moviedb;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by AbHiNav-PC on 11-Sep-16.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<MovieDataFront> mMovie;

    public MovieAdapter(Context context, ArrayList<MovieDataFront> Movie) {
        mContext = context;
        mMovie = Movie;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final MovieDataFront currentMovie = mMovie.get(position);
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w342/"+currentMovie.poster_ID).into(holder.poster);
        holder.movieTitle.setText(currentMovie.MovieName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(mContext, MovieActivity.class);
                i.putExtra("MovieID", currentMovie.movieID);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mMovie.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView poster;
        TextView movieTitle;

        public MyViewHolder(View view) {
            super(view);
            poster = (ImageView) view.findViewById(R.id.Poster_ID);
            movieTitle = (TextView) view.findViewById(R.id.Title_ID);
        }
    }
}

