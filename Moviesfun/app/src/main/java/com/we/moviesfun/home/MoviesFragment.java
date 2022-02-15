package com.we.moviesfun.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.we.moviesfun.API.Client;
import com.we.moviesfun.API.NetworkConnectivity;
import com.we.moviesfun.API.Service;
import com.we.moviesfun.Activities.ui.movies.NowPlayingMovies;
import com.we.moviesfun.Activities.ui.movies.PopularMovies;
import com.we.moviesfun.Activities.ui.movies.TopRatedMovies;
import com.we.moviesfun.Activities.ui.movies.TrendingMovies;
import com.we.moviesfun.Activities.ui.movies.UpcomingMovies;
import com.we.moviesfun.BuildConfig;
import com.we.moviesfun.R;
import com.we.moviesfun.adepter.HomeAdapterBackdrop;
import com.we.moviesfun.adepter.HomeMovieAdapterSmall;
import com.we.moviesfun.model.Movie;
import com.we.moviesfun.model.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesFragment extends Fragment {

    private ProgressBar mProgressBar;


    private TextView mTrendingViewAllTextView;
    private RecyclerView mTrendingRecyclerView;
    private List<Movie> mTrendingMovies;
    private HomeAdapterBackdrop mTrendingAdapter;

    private FrameLayout mNowShowingLayout;
    private TextView mNowShowingViewAllTextView;
    private RecyclerView mNowShowingRecyclerView;
    private List<Movie> mNowShowingMovies;
    private HomeAdapterBackdrop mNowShowingAdapter;

    private FrameLayout mPopularLayout;
    private TextView mPopularViewAllTextView;
    private RecyclerView mPopularRecyclerView;
    private List<Movie> mPopularMovies;
    private HomeMovieAdapterSmall mPopularAdapter;

    private FrameLayout mUpcomingLayout;
    private TextView mUpcomingViewAllTextView;
    private RecyclerView mUpcomingRecyclerView;
    private List<Movie> mUpcomingMovies;
    private HomeMovieAdapterSmall mUpcomingAdapter;

    private FrameLayout mTopRatedLayout;
    private TextView mTopRatedViewAllTextView;
    private RecyclerView mTopRatedRecyclerView;
    private List<Movie> mTopRatedMovies;
    private HomeMovieAdapterSmall mTopRatedAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);





        mTrendingViewAllTextView=(TextView)view.findViewById(R.id.text_view_view_all_trending);
        mTrendingViewAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetworkConnectivity.isConnected(getContext())) {
                    Toast.makeText(getContext(), R.string.no_network, Toast.LENGTH_SHORT).show();

                return;
                }
                Intent intent=new Intent(getContext(), TrendingMovies.class);
                startActivity(intent);
            }
        });
        mNowShowingViewAllTextView = (TextView) view.findViewById(R.id.text_view_view_all_now_showing);
        mNowShowingViewAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetworkConnectivity.isConnected(getContext())) {
                    Toast.makeText(getContext(), R.string.no_network, Toast.LENGTH_SHORT).show();

                    return;
                }
                Intent intent=new Intent(getContext(), NowPlayingMovies.class);
                startActivity(intent);
            }
        });
        mPopularViewAllTextView = (TextView) view.findViewById(R.id.text_view_view_all_popular);
        mPopularViewAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetworkConnectivity.isConnected(getContext())) {
                    Toast.makeText(getContext(), R.string.no_network, Toast.LENGTH_SHORT).show();

                    return;
                }
                Intent intent=new Intent(getContext(), PopularMovies.class);
                startActivity(intent);
            }
        });
        mUpcomingViewAllTextView = (TextView) view.findViewById(R.id.text_view_view_all_upcoming);
        mUpcomingViewAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetworkConnectivity.isConnected(getContext())) {
                    Toast.makeText(getContext(), R.string.no_network, Toast.LENGTH_SHORT).show();

                    return;
                }
                Intent intent=new Intent(getContext(), UpcomingMovies.class);
                startActivity(intent);
            }
        });
        mTopRatedViewAllTextView = (TextView) view.findViewById(R.id.text_view_view_all_top_rated);
        mTopRatedViewAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetworkConnectivity.isConnected(getContext())) {
                    Toast.makeText(getContext(), R.string.no_network, Toast.LENGTH_SHORT).show();

                    return;
                }
                Intent intent=new Intent(getContext(), TopRatedMovies.class);
                startActivity(intent);
            }
        });

        mTrendingRecyclerView=(RecyclerView)view.findViewById(R.id.recycler_view_trending);
        (new LinearSnapHelper()).attachToRecyclerView(mTrendingRecyclerView);
        mNowShowingRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_now_showing);
        (new LinearSnapHelper()).attachToRecyclerView(mNowShowingRecyclerView);
        mPopularRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_popular);
        mUpcomingRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_upcoming);
        (new LinearSnapHelper()).attachToRecyclerView(mUpcomingRecyclerView);
        mTopRatedRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_top_rated);

        mTrendingMovies = new ArrayList<>();
        mNowShowingMovies = new ArrayList<>();
        mPopularMovies = new ArrayList<>();
        mUpcomingMovies = new ArrayList<>();
        mTopRatedMovies = new ArrayList<>();

        mTrendingAdapter = new HomeAdapterBackdrop(getContext(), mNowShowingMovies);
        mNowShowingAdapter = new HomeAdapterBackdrop(getContext(), mNowShowingMovies);
        mPopularAdapter = new HomeMovieAdapterSmall(getContext(), mPopularMovies);
        mUpcomingAdapter = new HomeMovieAdapterSmall(getContext(), mUpcomingMovies);
        mTopRatedAdapter = new HomeMovieAdapterSmall(getContext(), mTopRatedMovies);

        mTrendingRecyclerView.setAdapter(mTrendingAdapter);
        mTrendingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        mNowShowingRecyclerView.setAdapter(mNowShowingAdapter);
        mNowShowingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        mPopularRecyclerView.setAdapter(mPopularAdapter);
        mPopularRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        mUpcomingRecyclerView.setAdapter(mUpcomingAdapter);
        mUpcomingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        mTopRatedRecyclerView.setAdapter(mTopRatedAdapter);
        mTopRatedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));



                if (!NetworkConnectivity.isConnected(getContext())) {
                    Toast.makeText(getContext(), R.string.no_network, Toast.LENGTH_SHORT).show();
                }
                else {
                    loadTrending();
                    loadNowPlaying();
                    loadPopular();
                    loadToprated();
                    loadUpcoming();
                    mNowShowingAdapter.notifyDataSetChanged();
                    mPopularAdapter.notifyDataSetChanged();
                    mUpcomingAdapter.notifyDataSetChanged();
                    mTopRatedAdapter.notifyDataSetChanged();
                }
                return view;
    }


    private void loadTrending(){

        Client client=new Client();
        Service apiService=Client.
                getClient().create(Service.class);
        Call<MovieResponse> call=apiService.
                getTreandingMoviesFirstPage(BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse
                    (Call<MovieResponse> call,
                     Response<MovieResponse> response) {
                try {

                    List<Movie> movies = response.body().getResults();
                    mTrendingRecyclerView.
                            setAdapter(new HomeAdapterBackdrop
                                    (getContext(),
                                            movies));
                    mTrendingRecyclerView.smoothScrollToPosition(0);


                }catch (NullPointerException e){
               loadTrending();
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
              //  Log.d("the Movie db",""+t.getMessage());
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }


    private void loadPopular(){

            Client client=new Client();
            Service apiService=Client.
                    getClient().create(Service.class);
            Call<MovieResponse> call=apiService.
                    getPopularMoviesFirstPage
                            (BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse
                        (Call<MovieResponse> call,
                         Response<MovieResponse> response) {
                    try {

                        List<Movie> movies = response.body().
                                getResults();
                        mPopularRecyclerView.
                                setAdapter(new HomeMovieAdapterSmall
                                        (getContext(),
                                                movies));
                        mPopularRecyclerView.smoothScrollToPosition(0);
                    }catch (NullPointerException e){
                        loadPopular();
                    }

                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.d("Error",t.getMessage());


                }
            });
        }

    private void loadUpcoming(){

        Client client=new Client();
        Service apiService=Client.
                getClient().create(Service.class);
        Call<MovieResponse> call=apiService.
                       getUpcomingMoviesFirstPage (BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse
                    (Call<MovieResponse> call,
                     Response<MovieResponse> response) {
                try {

                    List<Movie> movies = response.body().
                            getResults();
                    mUpcomingRecyclerView.
                            setAdapter(new HomeMovieAdapterSmall
                                    (getContext(),
                                            movies));
                    mUpcomingRecyclerView.smoothScrollToPosition(0);
                }catch (NullPointerException e){
                    loadUpcoming();
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());


            }
        });
    }
    private void loadNowPlaying(){

        Client client=new Client();
        Service apiService=Client.
                getClient().create(Service.class);
        Call<MovieResponse> call=apiService.
                getNowShowingMoviesFirstpage(BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse
                    (Call<MovieResponse> call,
                     Response<MovieResponse> response) {
                try {

                    List<Movie> movies = response.body().
                            getResults();
                    mNowShowingRecyclerView.
                            setAdapter(new HomeAdapterBackdrop
                                    (getContext(),
                                            movies));
                    mNowShowingRecyclerView.smoothScrollToPosition(0);
                }catch (NullPointerException e){
                    loadNowPlaying();

                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
//                Log.d("Error",t.getMessage());


            }
        });
    }

    private void loadToprated(){

        Client client=new Client();
        Service apiService=Client.
                getClient().create(Service.class);
        Call<MovieResponse> call=apiService.
                getTopRatedMoviesFirstPage (BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse
                    (Call<MovieResponse> call,
                     Response<MovieResponse> response) {
                try {

                    List<Movie> movies = response.body().
                            getResults();
                    mTopRatedRecyclerView.
                            setAdapter(new HomeMovieAdapterSmall
                                    (getContext(),
                                            movies));
                    mTopRatedRecyclerView.smoothScrollToPosition(0);
                }catch (NullPointerException e){
                    loadToprated();
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());


            }
        });
    }

}