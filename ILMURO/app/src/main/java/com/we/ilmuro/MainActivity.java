package com.we.ilmuro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(apiServer.baseURL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        apiServer server = retrofit.create(apiServer.class);
//        Call<List<model>>call=apiServer.getData();
        apiServer apiService = Client.getClient().create(apiServer.class);
        Call<List<model>> call = apiService.getData();
        call.enqueue(new Callback<List<model>>() {
            @Override
            public void onResponse(Call<List<model>> call, Response<List<model>> response) {
                Toast.makeText(MainActivity.this, ""+response.body().get(0).getStory(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<model>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        });


    }
    }

