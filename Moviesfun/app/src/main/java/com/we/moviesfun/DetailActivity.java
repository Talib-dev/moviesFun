package com.we.moviesfun;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.we.moviesfun.API.Client;
import com.we.moviesfun.API.Service;
import com.we.moviesfun.adepter.MovieCastAdapter;
import com.we.moviesfun.adepter.TrailerAdapter;
import com.we.moviesfun.model.MovieCast;
import com.we.moviesfun.model.MovieCastResponse;
import com.we.moviesfun.model.Trailer;
import com.we.moviesfun.model.TrailerResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private String nameOfMovie;
    TextView plotSynopsis,userRating,releaseDate;
    ImageView backDrop,titleThumbnial;
    ProgressDialog pd;
    TrailerAdapter trailerAdapter;
    List<Trailer> trailerList;
    RecyclerView trailerRecyclerView;
    TextView trailerTextView;
    MovieCastAdapter castAdapter;
    List<MovieCast> castList;
    RecyclerView castRecyclerView;
    Call<MovieCastResponse> mMovieCreditsCall;
    TextView castTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initCollapsingToolbar();

        pd=new ProgressDialog(this);
        pd.setMessage("Fetching Movies ...");
        pd.setCancelable(false);
        pd.show();
        backDrop=findViewById(R.id.thumbnail_image_header);
        titleThumbnial=findViewById(R.id.iv_movie_poster);
        plotSynopsis=findViewById(R.id.tv_biography);
        userRating=findViewById(R.id.tv_people_age);
        releaseDate=findViewById(R.id.tv_gender);

        setData();
        trailer();
        cast();
        pd.dismiss();


    }

    void setData(){
        String titleThumnail=getIntent().
                getExtras().getString("poster_path");
        String backDropPath=getIntent().
                getExtras().getString("backdrop_path");
        String movieName=getIntent().
                getExtras().getString("title");
        String synopsis=getIntent().
                getExtras().getString("overview");
        String rating=getIntent().
                getExtras().getString("vote_average");
        String dateOfRelease=getIntent().
                getExtras().getString("release_date");

        Glide.with(DetailActivity.this).load(backDropPath).
                placeholder(R.drawable.load).into(backDrop);
        Glide.with(DetailActivity.this).load(titleThumnail).
                placeholder(R.drawable.load).into(titleThumbnial);
        nameOfMovie=movieName;
        plotSynopsis.setText(synopsis);
        userRating.setText(rating+"/10");
        releaseDate.setText(dateOfRelease);
    }

    private void initCollapsingToolbar(){
        final CollapsingToolbarLayout collapsingToolbarLayout=findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitle(" ");
        AppBarLayout appBarLayout=findViewById(R.id.appBar);
        appBarLayout.setExpanded(true);
        appBarLayout.addOnOffsetChangedListener
                (new AppBarLayout.BaseOnOffsetChangedListener() {
                    boolean isShow=false;
                    int scrollRange=-1;
                    @Override
                    public void onOffsetChanged
                            (AppBarLayout appBarLayout, int verticalOffset) {
                        if (scrollRange==-1){
                            scrollRange=appBarLayout.getTotalScrollRange();
                            collapsingToolbarLayout.setTitle(nameOfMovie);
                        }
                        if(scrollRange+verticalOffset==0){
                            collapsingToolbarLayout.setTitle(nameOfMovie);
                            isShow=true;
                        }else if (isShow){
                            collapsingToolbarLayout.setTitle(nameOfMovie);
                            isShow=false;
                        }

                    }
                });

    }





    void trailer(){

        trailerList = new ArrayList<>();
        trailerAdapter = new TrailerAdapter(this, trailerList);

        trailerRecyclerView =findViewById(R.id.rv_recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        trailerRecyclerView.setLayoutManager(mLayoutManager);
        trailerRecyclerView.setAdapter(trailerAdapter);
        trailerAdapter.notifyDataSetChanged();
        trailerTextView=findViewById(R.id.trailerTextView);

        loadTrailerJSON();


    }


    void cast(){


        castList=new ArrayList<>();
        castAdapter=new MovieCastAdapter(this,castList);
        castRecyclerView=findViewById(R.id.recycler_view_cast_movie_detail);
        castRecyclerView.setAdapter(castAdapter);
        castRecyclerView.setLayoutManager(new LinearLayoutManager(DetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
        castRecyclerView.setAdapter(castAdapter);
        castAdapter.notifyDataSetChanged();
        castTextView=findViewById(R.id.text_view_cast_movie_detail);

        lostCredits();
    }
    private void loadTrailerJSON(){
        try{
            int movie_id=getIntent().getExtras().getInt("movie_id");


            Client Client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<TrailerResponse> call = apiService.
                    getMovieTrailer(movie_id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<TrailerResponse>() {
                @Override
                public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                    try {

                        List<Trailer> trailer = response.body().getResults();
                        trailerTextView.setText("Trailers");

                        trailerRecyclerView.setAdapter(new TrailerAdapter(getApplicationContext(),
                                trailer));
                        trailerRecyclerView.smoothScrollToPosition(0);
                    }catch(NullPointerException e)
                    {
                        System.out.print("NullPointerException caught");
                    }
                }

                @Override
                public void onFailure(Call<TrailerResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(DetailActivity.this, "Error fetching trailer data", Toast.LENGTH_SHORT).show();
                }

            });
            pd.dismiss();
        }catch (NullPointerException e){
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            pd.dismiss(); }
    }

    void lostCredits(){
        int movie_id=getIntent().getExtras().getInt("movie_id");
        Service apiService = Client.getClient().create(Service.class);
        mMovieCreditsCall = apiService.getMovieCast(movie_id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
        mMovieCreditsCall.enqueue(new Callback<MovieCastResponse>() {
            @Override
            public void onResponse(Call<MovieCastResponse> call, Response<MovieCastResponse> response) {

                try {
                    List<MovieCast> casts = response.body().getCasts();
                    castTextView.setText(getString(R.string.cast));
                    castRecyclerView.setAdapter(new MovieCastAdapter(getApplicationContext(),
                            casts));
                    castRecyclerView.smoothScrollToPosition(0);
                } catch(NullPointerException e)
                {
                    System.out.print("NullPointerException caught");
                }

//                if (!response.isSuccessful()) {
//                    mMovieCreditsCall = call.clone();
//                    mMovieCreditsCall.enqueue(this);
//                    return;
//                }
//
//                if (response.body() == null) return;
//                if (response.body().getCasts() == null) return;
//
//                for (MovieCast castBrief : response.body().getCasts()) {
//                    if (castBrief != null && castBrief.getName() != null)
//                        castList.add(castBrief);
//                }
//
//                if (!castList.isEmpty())
//                    mCastTextView.setVisibility(View.VISIBLE);
//                mCastAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieCastResponse> call, Throwable t) {

            }
        });



    }




}
