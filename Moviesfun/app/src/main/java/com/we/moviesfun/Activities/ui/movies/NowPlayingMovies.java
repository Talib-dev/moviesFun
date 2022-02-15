package com.we.moviesfun.Activities.ui.movies;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class NowPlayingMovies extends AppCompatActivity {
    RecyclerView recyclerView;
    private MovieAdepter adapter;
    private List<Movie> moviesList;
    ProgressDialog pd;
    SwipeRefreshLayout swipeContainer;
    private Call<MovieResponse> mNowPlayingMovieCall;

    boolean pagesOver = false;
    private int presentPage = 1;
    private boolean loading = true;
    private int previousTotal = 0;
    private int visibleThreshold = 5;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing_movies);
        setTitle("In Theater");
        swipeContainer=findViewById(R.id.now_playing_movie_content);
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
              //  Intent intent= new Intent(NowPlayingMovies.this,NowPlayingMovies.class);
                onRestart();
                swipeContainer.setRefreshing(false);

               // startActivity(intent);
               // finish();

            }
        });
        pd=new ProgressDialog(this);
        pd.setMessage("Fetching Movies ...");
        pd.setCancelable(false);
        pd.show();
        moviesList = new ArrayList<>();
        recyclerView=findViewById(R.id.now_playing_movie_recyclerview);
        adapter = new MovieAdepter(this, moviesList);

        final GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3);
        recyclerView.setLayoutManager (gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = gridLayoutManager.getChildCount();
                int totalItemCount = gridLayoutManager.getItemCount();
                int firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount; }
                }
                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    loadJSON();
                    loading = true; }
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
        if (mNowPlayingMovieCall != null) mNowPlayingMovieCall.
                cancel();

    }

    private void loadJSON(){
            Service apiService = Client.getClient().create(Service.class);
            mNowPlayingMovieCall = apiService.
                    getNowShowingMovies(BuildConfig.
                            THE_MOVIE_DB_API_TOKEN,presentPage);
            mNowPlayingMovieCall.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                    if (!response.isSuccessful()) {
                        mNowPlayingMovieCall = call.clone();
                        mNowPlayingMovieCall.enqueue(this);
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
                public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(NowPlayingMovies.this,
                            "Something wrong please try again",
                            Toast.LENGTH_SHORT).show();

                    pd.dismiss();

                }
            });

        }
    }