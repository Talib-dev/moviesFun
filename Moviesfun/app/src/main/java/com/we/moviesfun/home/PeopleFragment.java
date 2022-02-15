package com.we.moviesfun.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.we.moviesfun.API.Client;
import com.we.moviesfun.API.NetworkConnectivity;
import com.we.moviesfun.API.Service;
import com.we.moviesfun.BuildConfig;
import com.we.moviesfun.R;
import com.we.moviesfun.adepter.PeopleAdepter;
import com.we.moviesfun.model.PopularPeople;
import com.we.moviesfun.model.PopularPeopleResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeopleFragment extends Fragment {


    private RecyclerView recyclerView;
    private PeopleAdepter adapter;
    private List<PopularPeople> peopleList;

    private Call<PopularPeopleResponse> mPopularPeopleCall;

    private boolean pagesOver = false;
    private int presentPage = 1;
    private boolean loading = true;
    private int previousTotal = 0;
    private int visibleThreshold = 5;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people, container, false);



        peopleList = new ArrayList<>();
        adapter = new PeopleAdepter(getContext(), peopleList);

        recyclerView = view.findViewById(R.id.
                popular_people_home_recyclerview);
        final LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false);

        recyclerView.setLayoutManager (layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

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

        if (!NetworkConnectivity.isConnected(getContext())) {
            Toast.makeText(getContext(), R.string.no_network, Toast.LENGTH_SHORT).show();
        }
        else {

            loadJSON();


        }






        return view;
    }


    private void loadJSON(){
        try{
            Service apiService = Client.getClient().create(Service.class);
            mPopularPeopleCall = apiService.
                    getPopularPeopleMovies(BuildConfig.
                            THE_MOVIE_DB_API_TOKEN,presentPage);
            mPopularPeopleCall.enqueue(new Callback<PopularPeopleResponse>() {
                @Override
                public void onResponse(Call<PopularPeopleResponse> call, Response<PopularPeopleResponse> response) {
                    if (!response.isSuccessful()) {
                        mPopularPeopleCall = call.clone();
                        mPopularPeopleCall.enqueue(this);
                        return;
                    }

                    if (response.body() == null) return;
                    if (response.body().getResults() == null) return;
                    for (PopularPeople tvShowBrief : response.body().
                            getResults()) {
                        if (tvShowBrief != null && tvShowBrief.
                                getName() != null && tvShowBrief.getProflePath() != null)
                            peopleList.add(tvShowBrief);
                    }
                  //  Toast.makeText(getContext(), ""+peopleList, Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(new PeopleAdepter
                                    (getContext(), peopleList));
                    //recyclerView.smoothScrollToPosition(0);


                    if (response.body().getPage() == response.body().getTotalPages())
                        pagesOver = true;
                    else
                        presentPage++;


                }

                @Override
                public void onFailure(Call<PopularPeopleResponse> call, Throwable t) {
                    Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_LONG).show();
               //     Log.d("the Movie db",""+t.getMessage());


                }
            });

        }catch (NullPointerException e){
            Log.d("Error", e.getMessage());
            Toast.makeText(getContext(), e.toString(),
                    Toast.LENGTH_SHORT).show();

        }
    }

}

