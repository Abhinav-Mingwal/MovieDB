package com.example.abhinav_pc.moviedb;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.JsResult;

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
 * Created by AbHiNav-PC on 11-Sep-16.
 */
public class MovieAsyncTask extends AsyncTask<String,Void,MovieData[]> {

    MovieAsyncTaskListner listener;

    public interface MovieAsyncTaskListner {
        void onMoviesDataFetched(MovieData[] movies);
    }

    public void setListener(MovieAsyncTaskListner listener) {
        this.listener=listener;
    }


    @Override
    protected MovieData[] doInBackground(String... params) {
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

        return getMovieArray(output.toString());
    }

    private MovieData[] getMovieArray(String s) {
        MovieData[] movies=null;
        try {
            JSONObject object = new JSONObject(s);
            JSONArray results =object.getJSONArray("results");
            movies=new MovieData[results.length()];
            for(int i=0;i<movies.length;i++){
                JSONObject objectTemp=results.getJSONObject(i);
                MovieData movie=new MovieData();
                movie.poster_ID=objectTemp.getString("poster_path");
                movie.MovieName=objectTemp.getString("title");
                movie.Rating=objectTemp.getDouble("vote_average");
                movie.popularity=objectTemp.getDouble("popularity");
                movie.releaseDate=objectTemp.getString("release_date");
                movie.voteCount=objectTemp.getDouble("vote_count");
                movie.movieID=objectTemp.getInt("id");
                movies[i]=movie;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;

    }

    @Override
    protected void onPostExecute(MovieData[] movieDatas) {
        if(listener!=null){
            listener.onMoviesDataFetched(movieDatas);
        }
    }
}
