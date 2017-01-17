package com.example.abhinav_pc.moviedb;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.abhinav_pc.moviedb.Network.ApiServices;
import com.example.abhinav_pc.moviedb.Network.ChildAnimationExample;
import com.example.abhinav_pc.moviedb.Network.MovieBackResponse;
import com.example.abhinav_pc.moviedb.Network.URLconstants;
import com.example.abhinav_pc.moviedb.Network.YoutubeResponse;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{
    String poster_ID;
    String MovieName;
    double Rating;
    double popularity;
    ArrayList<prod> production = new ArrayList<>();
    int voteCount;
    int movieID;
    String ReleaseDate;
    String Overview;
    ArrayList<Genre> genre = new ArrayList<>();
    ArrayList<YoutubeKey> key = new ArrayList<>();
    private SliderLayout mDemoSlider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        Intent intent = getIntent();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URLconstants.Movie_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices service = retrofit.create(ApiServices.class);
        Call<MovieBackResponse> callMovie = service.getMovieInfo(intent.getIntExtra("MovieID",0), IntentConstants.Api_key);
        callMovie.enqueue(new Callback<MovieBackResponse>() {
            @Override
            public void onResponse(Call<MovieBackResponse> call, Response<MovieBackResponse> response) {
                poster_ID = response.body().getPoster_ID();
                MovieName = response.body().getMovieName();
                Rating = response.body().getRating();
                popularity = response.body().getPopularity();
                ArrayList<prod> obj1 = response.body().getProduction();
                for (int i = 0; i < obj1.size(); i++) {
                    production.add(obj1.get(i));
                }
                voteCount = response.body().getVoteCount();
                movieID = response.body().getMovieID();
                ReleaseDate = response.body().getReleaseDate();
                Overview = response.body().getOverview();
                ArrayList<Genre> obj2 = response.body().getGenre();
                for (int i = 0; i < obj2.size(); i++) {
                    genre.add(obj2.get(i));
                }
                movieDataBack movieOBJ = new movieDataBack(poster_ID, MovieName, Rating, popularity, production, voteCount, movieID, ReleaseDate, Overview, genre);
                TextFinal(movieOBJ);

            }

            @Override
            public void onFailure(Call<MovieBackResponse> call, Throwable t) {

            }
        });
        Call<YoutubeResponse> callyoutube = service.getTrailerInfo(intent.getIntExtra("MovieID",0), "6ed2279f7b98c9369069fe4760ac0e1f");
        callyoutube.enqueue(new Callback<YoutubeResponse>() {
            @Override
            public void onResponse(Call<YoutubeResponse> call, Response<YoutubeResponse> response) {
                MovieActivity.this.key = response.body().getYoutubeInfo();
                if (MovieActivity.this.key == null) {
                    return;
                } else {
//                    SLIDER
                    for (int i = 0; i < MovieActivity.this.key.size(); i++) {
                        final int clickedIndex = i;
                        TextSliderView textSliderView = new TextSliderView(MovieActivity.this);
                        // initialize a SliderLayout
                        textSliderView
                                .description(key.get(i).name)
                                .image("http://img.youtube.com/vi/" + key.get(i).key + "/hqdefault.jpg")
                                .setScaleType(BaseSliderView.ScaleType.Fit)
                                .setOnSliderClickListener(MovieActivity.this);

                        //add your extra information
                        textSliderView.bundle(new Bundle());
                        textSliderView.getBundle()
                                .putString("extra",(key.get(i).name));

                        mDemoSlider.addSlider(textSliderView);

                        textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                            @Override
                            public void onSliderClick(BaseSliderView slider) {
                                Intent intentF = getIntent();
                                Intent intent1 = new Intent();
                                intent1.setAction(intent1.ACTION_VIEW);
                                intent1.setData(Uri.parse("https://www.youtube.com/watch?v=" + key.get(clickedIndex).key));
                                startActivity(intent1);
                            }
                        });
                    }
                    mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                    mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    mDemoSlider.setCustomAnimation(new ChildAnimationExample());
                    mDemoSlider.setDuration(4000);
                    mDemoSlider.addOnPageChangeListener(MovieActivity.this);

                }
            }

            @Override
            public void onFailure(Call<YoutubeResponse> call, Throwable t) {

            }
        });
    }


    public void TextFinal(movieDataBack movie) {
        TextView MovieName = (TextView) findViewById(R.id.title2);
        ImageView poster = (ImageView) findViewById(R.id.poster2);
        TextView movieId = (TextView) findViewById(R.id.movieId2);
        TextView popularity = (TextView) findViewById(R.id.popularity_id2);
        TextView rating = (TextView) findViewById(R.id.Rating_id2);
        TextView genre = (TextView) findViewById(R.id.Genre2);
        TextView overview = (TextView) findViewById(R.id.OverView2);
        TextView releaseDate = (TextView) findViewById(R.id.ReleaseDate2);
        TextView voteCount = (TextView) findViewById(R.id.voteCount2);
        TextView production = (TextView) findViewById(R.id.production);
        MovieName.setText(this.MovieName);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w342" + movie.poster_ID).into(poster);
        movieId.setText("ID: " + this.movieID);
        rating.setText("Rating: " + this.Rating);
        StringBuffer genreSTR = new StringBuffer("");
        for (Genre obj : this.genre) {
            genreSTR.append(obj.name + ", ");
        }
        genre.setText("Genre: " + genreSTR.toString());
        voteCount.setText("Vote Count: " + this.voteCount);
        releaseDate.setText("Release Date: " + this.ReleaseDate);
        popularity.setText("Popularity :" + this.popularity);
        overview.setText(this.Overview);
        StringBuffer prodSTR = new StringBuffer("");
        for (prod obj : this.production) {
            prodSTR.append(obj.name + ", ");
        }
        production.setText("Production :" + prodSTR.toString());
    }


    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

}
