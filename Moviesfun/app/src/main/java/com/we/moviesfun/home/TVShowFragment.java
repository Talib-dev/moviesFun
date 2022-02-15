package com.we.moviesfun.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.we.moviesfun.API.Client;
import com.we.moviesfun.API.NetworkConnectivity;
import com.we.moviesfun.API.Service;
import com.we.moviesfun.Activities.ui.shows.AiringToday;
import com.we.moviesfun.Activities.ui.shows.OnTV;
import com.we.moviesfun.Activities.ui.shows.PopularTVshows;
import com.we.moviesfun.Activities.ui.shows.TopRatedTVshows;
import com.we.moviesfun.Activities.ui.shows.TrendingTVShows;
import com.we.moviesfun.BuildConfig;
import com.we.moviesfun.R;
import com.we.moviesfun.adepter.HomeShowTVAdapterBackdrop;
import com.we.moviesfun.adepter.HomeShowTVAdapterSmall;
import com.we.moviesfun.model.ShowTV;
import com.we.moviesfun.model.ShowTVResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowFragment extends Fragment {
//    private ProgressBar mProgressBar;
//    private boolean mNowShowingSectionLoaded;
//    private boolean mPopularSectionLoaded;
//    private boolean mUpcomingSectionLoaded;
//    private boolean mTopRatedSectionLoaded;


    private TextView mTrendingViewAllTextView;
    private RecyclerView mTrendingRecyclerView;
    private List<ShowTV> mTrendingShowTVs;
    private HomeShowTVAdapterBackdrop mTrendingAdapter;

    private FrameLayout mNowShowingLayout;
    private TextView mNowShowingViewAllTextView;
    private RecyclerView mNowShowingRecyclerView;
    private List<ShowTV> mNowShowingShowTVs;
    private HomeShowTVAdapterBackdrop mNowShowingAdapter;

    private FrameLayout mPopularLayout;
    private TextView mPopularViewAllTextView;
    private RecyclerView mPopularRecyclerView;
    private List<ShowTV> mPopularShowTVs;
    private HomeShowTVAdapterSmall mPopularAdapter;

    private FrameLayout mUpcomingLayout;
    private TextView mUpcomingViewAllTextView;
    private RecyclerView mUpcomingRecyclerView;
    private List<ShowTV> mUpcomingShowTVs;
    private HomeShowTVAdapterSmall mUpcomingAdapter;

    private FrameLayout mTopRatedLayout;
    private TextView mTopRatedViewAllTextView;
    private RecyclerView mTopRatedRecyclerView;
    private List<ShowTV> mTopRatedShowTVs;
    private HomeShowTVAdapterSmall mTopRatedAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_tv_show, null);

        mTrendingViewAllTextView=(TextView)view.findViewById(R.id.text_view_view_all_trending);
        mTrendingViewAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetworkConnectivity.isConnected(getContext())) {
                    Toast.makeText(getContext(), R.string.no_network, Toast.LENGTH_SHORT).show();

                    return;
                }
                Intent intent=new Intent(getContext(), TrendingTVShows.class);
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
                Intent intent=new Intent(getContext(), AiringToday.class);
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
                Intent intent=new Intent(getContext(), PopularTVshows.class);
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
                Intent intent=new Intent(getContext(), OnTV.class);
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
                Intent intent=new Intent(getContext(), TopRatedTVshows.class);
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

        mTrendingShowTVs = new ArrayList<>();
        mNowShowingShowTVs = new ArrayList<>();
        mPopularShowTVs = new ArrayList<>();
        mUpcomingShowTVs = new ArrayList<>();
        mTopRatedShowTVs = new ArrayList<>();

        mTrendingAdapter = new HomeShowTVAdapterBackdrop(getContext(), mNowShowingShowTVs);
        mNowShowingAdapter = new HomeShowTVAdapterBackdrop(getContext(), mNowShowingShowTVs);
        mPopularAdapter = new HomeShowTVAdapterSmall(getContext(), mPopularShowTVs);
        mUpcomingAdapter = new HomeShowTVAdapterSmall(getContext(), mUpcomingShowTVs);
        mTopRatedAdapter = new HomeShowTVAdapterSmall(getContext(), mTopRatedShowTVs);

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
        Call<ShowTVResponse> call=apiService.
                getTrendimgTVShowsFirstPage(BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<ShowTVResponse>() {
            @Override
            public void onResponse
                    (Call<ShowTVResponse> call,
                     Response<ShowTVResponse> response) {
                try {
                    List<ShowTV> ShowTVs=response.body().getResults();
                    mTrendingRecyclerView.
                            setAdapter(new HomeShowTVAdapterBackdrop
                                    (getContext(),
                                            ShowTVs));

                }catch (NullPointerException e){


                    loadTrending();

                }


                mTrendingRecyclerView.smoothScrollToPosition(0);

            }

            @Override
            public void onFailure(Call<ShowTVResponse> call, Throwable t) {
    //            Log.d("Error",t.getMessage());


            }
        });


    }


    private void loadPopular(){

        Client client=new Client();
        Service apiService=Client.
                getClient().create(Service.class);
        Call<ShowTVResponse> call=apiService.
                getPopularTVShowsFirstPage
                        (BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<ShowTVResponse>() {
            @Override
            public void onResponse
                    (Call<ShowTVResponse> call,
                     Response<ShowTVResponse> response) {
                try {


                    List<ShowTV> ShowTVs = response.body().
                            getResults();
                    mPopularRecyclerView.
                            setAdapter(new HomeShowTVAdapterSmall
                                    (getContext(),
                                            ShowTVs));
                    mPopularRecyclerView.smoothScrollToPosition(0);
                }catch (NullPointerException e){
                    loadPopular();

                }

            }

            @Override
            public void onFailure(Call<ShowTVResponse> call, Throwable t) {
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_LONG).show();
             //   Log.d("the Movie db",""+t.getMessage());


            }
        });
    }

    private void loadUpcoming(){

        Client client=new Client();
        Service apiService=Client.
                getClient().create(Service.class);
        Call<ShowTVResponse> call=apiService.
                getOnTVShowsFirstPage(BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<ShowTVResponse>() {
            @Override
            public void onResponse
                    (Call<ShowTVResponse> call,
                     Response<ShowTVResponse> response) {
                try {


                    List<ShowTV> ShowTVs = response.body().
                            getResults();
                    mUpcomingRecyclerView.
                            setAdapter(new HomeShowTVAdapterSmall
                                    (getContext(),
                                            ShowTVs));
                    mUpcomingRecyclerView.smoothScrollToPosition(0);
                }catch (NullPointerException e){
                    loadUpcoming();

                }

            }

            @Override
            public void onFailure(Call<ShowTVResponse> call, Throwable t) {
//                Log.d("Error",t.getMessage());


            }
        });
    }
    private void loadNowPlaying(){

        Client client=new Client();
        Service apiService=Client.
                getClient().create(Service.class);
        Call<ShowTVResponse> call=apiService.
                getAiringTodayTVShowsFirstPage(BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<ShowTVResponse>() {
            @Override
            public void onResponse
                    (Call<ShowTVResponse> call,
                     Response<ShowTVResponse> response) {
                try {
                    List<ShowTV> ShowTVs = response.body().
                            getResults();
                    mNowShowingRecyclerView.
                            setAdapter(new HomeShowTVAdapterBackdrop
                                    (getContext(),
                                            ShowTVs));
                    mNowShowingRecyclerView.smoothScrollToPosition(0);
                }catch (NullPointerException e){

                    loadNowPlaying();
                }

            }

            @Override
            public void onFailure(Call<ShowTVResponse> call, Throwable t) {
       //         Log.d("Error",t.getMessage());


            }
        });
    }

    private void loadToprated(){

        Client client=new Client();
        Service apiService=Client.
                getClient().create(Service.class);
        Call<ShowTVResponse> call=apiService.
                getTopRatedTVShowsFirstPage (BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<ShowTVResponse>() {
            @Override
            public void onResponse
                    (Call<ShowTVResponse> call,
                     Response<ShowTVResponse> response) {
                try {

                    List<ShowTV> ShowTVs = response.body().
                            getResults();
                    mTopRatedRecyclerView.
                            setAdapter(new HomeShowTVAdapterSmall
                                    (getContext(),
                                            ShowTVs));
                    mTopRatedRecyclerView.smoothScrollToPosition(0);
                }catch (NullPointerException e){
                    loadToprated();

                }

            }

            @Override
            public void onFailure(Call<ShowTVResponse> call, Throwable t) {
        //        Log.d("Error",t.getMessage());


            }
        });
    }



}