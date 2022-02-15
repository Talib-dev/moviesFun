package com.we.moviesfun.Activities.ui.movies;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.we.moviesfun.API.Client;
import com.we.moviesfun.API.Service;
import com.we.moviesfun.BuildConfig;
import com.we.moviesfun.R;
import com.we.moviesfun.adepter.MovieAdepter;
import com.we.moviesfun.model.Movie;
import com.we.moviesfun.model.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMovies extends AppCompatActivity {


    private RecyclerView recyclerView;
    private MovieAdepter adapter;
    private List<Movie> moviesList;
    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;
    public static final String LOG_TAG=MovieAdepter.class.getName();
    private Call<MovieResponse> mPopularMovieCall;

    private boolean pagesOver = false;
    private int presentPage = 1;
    private boolean loading = true;
    private int previousTotal = 0;
    private int visibleThreshold = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);
        setTitle("Popular Movies");
        pd=new ProgressDialog(this);
        pd.setMessage("Fetching Movies ...");
        pd.setCancelable(false);
        pd.show();
        swipeContainer=(SwipeRefreshLayout)
                findViewById(R.id.main_content);
        swipeContainer.
                setColorSchemeResources
                        (android.R.color.holo_orange_dark);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent intent= new Intent(PopularMovies.this,PopularMovies.class);
                Toast.makeText
                        (PopularMovies.this,
                                "Movies Refreshed",
                                Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });
        moviesList = new ArrayList<>();
        adapter = new MovieAdepter(this, moviesList);

        recyclerView = (RecyclerView) findViewById(R.id.
                recyclerview);
        final GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3);
        recyclerView.setLayoutManager (gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int visibleItemCount = gridLayoutManager.getChildCount();
                int totalItemCount = gridLayoutManager.getItemCount();
                int firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    loadJSON();
                    loading = true;
                }

            }
        });

        loadJSON();
    }

    @Override
    protected void onStart() {
        super.onStart();

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPopularMovieCall != null) mPopularMovieCall.
                cancel();

    }

    private void loadJSON(){
        try{
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
                Toast.makeText(getApplicationContext(), "Please obtain your API Key from themoviedb.org", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                return;
            }
            Service apiService = Client.getClient().create(Service.class);
            mPopularMovieCall = apiService.
                    getPopularMovies(BuildConfig.
                            THE_MOVIE_DB_API_TOKEN,presentPage);
            mPopularMovieCall.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    if (!response.isSuccessful()) {
                        mPopularMovieCall = call.clone();
                        mPopularMovieCall.enqueue(this);
                        return;
                    }

                    if (response.body() == null) return;
                    if (response.body().getResults() == null) return;

//                        mSmoothProgressBar.progressiveStop();
                    for (Movie tvShowBrief : response.body().
                            getResults()) {
                        if (tvShowBrief != null && tvShowBrief.
                                getTitle() != null && tvShowBrief.getPosterPath() != null)
                            moviesList.add(tvShowBrief);
                    }
                    adapter.notifyDataSetChanged();
                    if (response.body().getPage() == response.body().getTotalPages())
                        pagesOver = true;
                    else
                        presentPage++;
                    pd.dismiss();


                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(PopularMovies.this,
                            "Something wrong please try again",
                            Toast.LENGTH_SHORT).show();

                    pd.dismiss();

                }
            });

        }catch (NullPointerException e){
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(),
                    Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }






}
