package com.example.abhinav_pc.moviedb;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by AbHiNav-PC on 15-Sep-16.
 */
public class Movie2AsyncTask extends AsyncTask<String,Void,movieData2> {
    Movie2AsyncTaskListener listener;

    public interface Movie2AsyncTaskListener {
        void onBatchesDataFetched(movieData2 movie);
    }

    public void setListener(Movie2AsyncTaskListener listener) {
        this.listener=listener;
    }

    @Override
    protected movieData2 doInBackground(String... params) {
        String urlString = params[0];
        StringBuffer output = new StringBuffer("");

        try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                Scanner scn = new Scanner(inputStream);

                while (scn.hasNext()) {
                    output.append(scn.nextLine());

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        return getMovie2Data(output.toString());

    }

    public movieData2 getMovie2Data(String output){
        movieData2 temp=null;
        try {
            JSONObject object=new JSONObject(output);
            JSONArray genres=object.getJSONArray("genres");
            temp=new movieData2();
            for(int i=0;i<genres.length();i++){
                JSONObject objectTemp=genres.getJSONObject(i);
                temp.genre+=objectTemp.getString("name")+" ,";
            }
            Log.i("ABCD",temp.genre);
            temp.movieID=object.getInt("id");
            Log.i("ABCD",temp.movieID+"");
            temp.MovieName=object.getString("original_title");
            Log.i("ABCD",temp.MovieName);
//            temp.poster_ID=object.getJSONObject("belongs_to_collection").getString("backdrop_path");
            temp.poster_ID=object.getString("backdrop_path");
            Log.i("ABCD",temp.poster_ID);
            temp.Overview=object.getString("overview");
            Log.i("ABCD",temp.Overview);
            temp.popularity=object.getInt("popularity");
            Log.i("ABCD",temp.popularity+"");
            temp.voteCount=object.getInt("vote_count");
            Log.i("ABCD",temp.voteCount+"");
            temp.voteAVG=object.getInt("vote_average");
            Log.i("ABCD"," "+temp.voteAVG);
            temp.ReleaseDate=object.getString("release_date");
            JSONArray production=object.getJSONArray("production_companies");
            for(int i=0;i<production.length();i++){
                JSONObject objectTemp=production.getJSONObject(i);
                temp.production+=objectTemp.getString("name")+" ,";
            }
            Log.i("ABCD",temp.production);
            temp.Rating=object.getInt("vote_average");
            Log.i("ABCD",temp.Rating+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return temp;

    }


    @Override
    protected void onPostExecute(movieData2 movie) {
        if(listener!=null){
            listener.onBatchesDataFetched(movie);
        }
    }
}
