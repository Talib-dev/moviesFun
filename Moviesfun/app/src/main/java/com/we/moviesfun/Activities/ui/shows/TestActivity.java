package com.we.moviesfun.Activities.ui.shows;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.we.moviesfun.API.Client;
import com.we.moviesfun.API.Service;
import com.we.moviesfun.BuildConfig;
import com.we.moviesfun.R;
import com.we.moviesfun.model.PeopleInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {
    Service apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Button test;
        test=findViewById(R.id.test);
        apiInterface= Client.getClient().create(Service.class);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<PeopleInfo> call=apiInterface.getpersonDetail(228150, BuildConfig.THE_MOVIE_DB_API_TOKEN);
                call.enqueue(new Callback<PeopleInfo>() {
                    @Override
                    public void onResponse(Call<PeopleInfo> call, Response<PeopleInfo> response) {
                        Toast.makeText(TestActivity.this, ""+response.body().getName(), Toast.LENGTH_SHORT).show();
                    }

                    @Override

                    public void onFailure(Call<PeopleInfo> call, Throwable t) {
                        Toast.makeText(TestActivity.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });



    }
}
