package com.example.abhinav_pc.moviedb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.abhinav_pc.moviedb.Network.ApiServices;
import com.example.abhinav_pc.moviedb.Network.ChildAnimationExample;
import com.example.abhinav_pc.moviedb.Network.TvBackResponse;
import com.example.abhinav_pc.moviedb.Network.URLconstants;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TV_Activity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    ImageView poster;
    TextView Title, id, vote_count, genre, first_air_date, rating,language, noe, nos,
            overview, popularity, production_companies;
    private SliderLayout Creator_Slider;
    private SliderLayout Season_Slider;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URLconstants.Movie_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        initialise_view();
        ApiServices service = retrofit.create(ApiServices.class);
        Call<TvBackResponse> call = service.getTVDetails(intent.getStringExtra("TV_ID"));
        call.enqueue(new Callback<TvBackResponse>() {
            @Override
            public void onResponse(Call<TvBackResponse> call, Response<TvBackResponse> response) {
                TvBackResponse tvBackResponse = response.body();
                Log.i("ABCD", response.code() + "   Code generated");
                if (tvBackResponse == null) {
                    return;
                } else {
                    Picasso.with(TV_Activity.this).load("http://image.tmdb.org/t/p/w342" + tvBackResponse.poster_path).into(poster);
                    Title.setText(tvBackResponse.name);
                    rating.setText("Rating : "+ tvBackResponse.vote_average);
                    popularity.setText("Popularity : "+tvBackResponse.popularity);
                    vote_count.setText("Vote count : "+tvBackResponse.vote_count);
                    first_air_date.setText("First Air Date : "+ tvBackResponse.first_air_date);
                    String temp="";
                    for(int i=0;i<tvBackResponse.genres.size();i++){
                        temp = temp + tvBackResponse.genres.get(i).name+" ,";
                    }
                    genre.setText("Genre : "+temp);
                    temp= "";
                    for(int i=0;i<tvBackResponse.production_companies.size();i++){
                        temp = temp + tvBackResponse.production_companies.get(i).name+" ,";
                    }
                    production_companies.setText("Production : "+temp);
                    overview.setText(tvBackResponse.overview);
                    for(int i=0;i<tvBackResponse.created_by.size();i++){
                        TextSliderView textSliderView = new TextSliderView(TV_Activity.this);
                        // initialize a SliderLayout
                        textSliderView
                                .description(tvBackResponse.created_by.get(i).name)
                                .image("http://image.tmdb.org/t/p/w342" + tvBackResponse.created_by.get(i).profile_path)
                                .setScaleType(BaseSliderView.ScaleType.CenterInside)
                                .setOnSliderClickListener(TV_Activity.this);

                        //add your extra information
                        textSliderView.bundle(new Bundle());
                        textSliderView.getBundle()
                                .putString("extra",tvBackResponse.created_by.get(i).name);

                        Creator_Slider.addSlider(textSliderView);
                    }
                    Creator_Slider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                    Creator_Slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    Creator_Slider.setCustomAnimation(new ChildAnimationExample());
                    Creator_Slider.setDuration(4000);
                    Creator_Slider.addOnPageChangeListener(TV_Activity.this);
                    for(int i=0;i<tvBackResponse.seasons.size();i++){
                        TextSliderView textSliderView = new TextSliderView(TV_Activity.this);
                        // initialize a SliderLayout
                        textSliderView
                                .description("Season "+tvBackResponse.seasons.get(i).season_number)
                                .image("http://image.tmdb.org/t/p/w342" + tvBackResponse.seasons.get(i).poster_path)
                                .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                                .setOnSliderClickListener(TV_Activity.this);

                        //add your extra information
                        textSliderView.bundle(new Bundle());
                        textSliderView.getBundle()
                                .putString("extra","Season "+tvBackResponse.seasons.get(i).season_number);

                        Season_Slider.addSlider(textSliderView);
                    }
                    Season_Slider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                    Season_Slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    Season_Slider.setCustomAnimation(new ChildAnimationExample());
                    Season_Slider.setDuration(4000);
                    Season_Slider.addOnPageChangeListener(TV_Activity.this);
                }
            }

            @Override
            public void onFailure(Call<TvBackResponse> call, Throwable t) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    void initialise_view() {
        poster = (ImageView) findViewById(R.id.Tv_AppBar_Poster);
        Title = (TextView) findViewById(R.id.TV_title);
        rating = (TextView) findViewById(R.id.Tv_genre);
        popularity = (TextView) findViewById(R.id.Tv_popularity);
        vote_count = (TextView) findViewById(R.id.TV_Votecount);
        first_air_date = (TextView) findViewById(R.id.Tv_first_air_date);
        genre = (TextView) findViewById(R.id.Tv_genre);
        production_companies = (TextView) findViewById(R.id.Tv_productions);
        overview = (TextView)findViewById(R.id.Tv_Overview);
//        ,language,noe,nos,overview,popularity,production_companies;
        Season_Slider = (SliderLayout) findViewById(R.id.Tv_slider_season);
        Creator_Slider = (SliderLayout) findViewById(R.id.Tv_slider_creator);


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
        Creator_Slider.stopAutoCycle();
        Season_Slider.stopAutoCycle();
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
