package com.example.abhinav_pc.moviedb;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by AbHiNav-PC on 20-Oct-16.
 */
public class TvAdapter extends RecyclerView.Adapter<TvAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TvDataFront> mTV;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final TvDataFront currentTV = mTV.get(position);
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w342/"+currentTV.poster_path).into(holder.poster);
        holder.TvTitle.setText(currentTV.name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(mContext,TV_Activity.class);
                i.putExtra("TV_ID",currentTV.id+"");
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mTV.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView TvTitle;

        public MyViewHolder(View view) {
            super(view);
            poster = (ImageView) view.findViewById(R.id.Poster_ID);
            TvTitle = (TextView) view.findViewById(R.id.Title_ID);
        }
    }
    TvAdapter(Context mContext,ArrayList<TvDataFront> mTV){
        this.mContext=mContext;
        this.mTV=mTV;
    }

}
