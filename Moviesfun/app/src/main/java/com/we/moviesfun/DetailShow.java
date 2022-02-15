package com.we.moviesfun;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class DetailShow extends AppCompatActivity {
    String nameOfMovie;
    TextView plotSynopsis, userRating, releaseDate;
    ImageView backDrop, titleThumbnial;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initCollapsingToolbar();

        pd = new ProgressDialog(this);
        pd.setMessage("Fetching Movies ...");
        pd.setCancelable(false);
        pd.show();
        backDrop = (ImageView) findViewById(R.id.thumbnail_image_header);
        titleThumbnial = (ImageView) findViewById(R.id.iv_movie_poster);
        plotSynopsis = (TextView) findViewById(R.id.tv_biography);
        userRating = (TextView) findViewById(R.id.tv_people_age);
        releaseDate = (TextView) findViewById(R.id.tv_gender);


        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("is_movie")) {

            String titleThumnail = getIntent().
                    getExtras().getString("poster_path");
            String backDropPath = getIntent().
                    getExtras().getString("backdrop_path");
            String movieName = getIntent().
                    getExtras().getString("name");
            String synopsis = getIntent().
                    getExtras().getString("overview");
            String rating = getIntent().
                    getExtras().getString("vote_average");
            String dateOfRelease = getIntent().
                    getExtras().getString("first_air_date");

            Glide.with(DetailShow.this).load(backDropPath).
                    placeholder(R.drawable.load).into(backDrop);
            Glide.with(DetailShow.this).load(titleThumnail).
                    placeholder(R.drawable.load).into(titleThumbnial);

            nameOfMovie = movieName;
            plotSynopsis.setText(synopsis);
            userRating.setText(rating + "/10");
            releaseDate.setText(dateOfRelease);
            pd.dismiss();

        } else {
            Toast.makeText(this, "No API data",
                    Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }
    }

    private void initCollapsingToolbar(){
        final CollapsingToolbarLayout collapsingToolbarLayout=
                (CollapsingToolbarLayout)
                        findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitle(" ");
        AppBarLayout appBarLayout =(AppBarLayout)
                findViewById(R.id.appBar);
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
}
