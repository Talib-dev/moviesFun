package com.we.moviesfun.Activities.ui.shows;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.we.moviesfun.API.Client;
import com.we.moviesfun.API.Service;
import com.we.moviesfun.BuildConfig;
import com.we.moviesfun.R;
import com.we.moviesfun.adepter.ShowTVAdepter;
import com.we.moviesfun.model.ShowTV;
import com.we.moviesfun.model.ShowTVResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingTVShows extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_trending_tv_shows);
//    }

    private RecyclerView recyclerView;
    private ShowTVAdepter adapter;
    private List<ShowTV> showTVList;
    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;
    public static final String LOG_TAG=ShowTVAdepter.class.getName();
    private Call<ShowTVResponse> mPopularTVShowsCall;

    private boolean pagesOver = false;
    private int presentPage = 1;
    private boolean loading = true;
    private int previousTotal = 0;
    private int visibleThreshold = 5;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending_tv_shows);
        setTitle("Trending TV Shows");
        swipeContainer=(SwipeRefreshLayout)
                findViewById(R.id.trending_tv_shows_content);
        swipeContainer.
                setColorSchemeResources
                        (android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent intent= new Intent(TrendingTVShows.this,OnTV.class);
                Toast.makeText
                        (TrendingTVShows.this,
                                "Show Refreshing... ",
                                Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });
        pd=new ProgressDialog(this);
        pd.setMessage("Fetching Movies ...");
        pd.setCancelable(false);
        pd.show();
        showTVList = new ArrayList<>();
        adapter = new ShowTVAdepter(this, showTVList);

        recyclerView = (RecyclerView) findViewById(R.id.trending_shows_recyclerview);
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

        //adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPopularTVShowsCall != null) mPopularTVShowsCall.cancel();

    }




    private void loadJSON(){
        try{
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
                Toast.makeText(getApplicationContext(), "Please obtain your API Key from themoviedb.org", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                return;
            }
            Service apiService = Client.getClient().create(Service.class);
            mPopularTVShowsCall = apiService.
                    getTrendimgTVShows(BuildConfig.THE_MOVIE_DB_API_TOKEN,presentPage);
            mPopularTVShowsCall.enqueue(new Callback<ShowTVResponse>() {
                @Override
                public void onResponse(Call<ShowTVResponse> call, Response<ShowTVResponse> response) {
                    if (!response.isSuccessful()) {
                        mPopularTVShowsCall = call.clone();
                        mPopularTVShowsCall.enqueue(this);
                        return;
                    }

                    if (response.body() == null) return;
                    if (response.body().getResults() == null) return;

//                        mSmoothProgressBar.progressiveStop();
                    for (ShowTV tvShowBrief : response.body().getResults()) {
                        if (tvShowBrief != null && tvShowBrief.getName() != null && tvShowBrief.getPosterPath() != null)
                            showTVList.add(tvShowBrief);
                    }
                    adapter.notifyDataSetChanged();
                    if (response.body().getPage() == response.body().getTotalPages())
                        pagesOver = true;
                    else
                        presentPage++;
                    pd.dismiss();


                }

                @Override
                public void onFailure(Call<ShowTVResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(TrendingTVShows.this, "Error ,check you internet connectivity", Toast.LENGTH_SHORT).show();
                    pd.dismiss();

                }
            });

        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }
    }

}
