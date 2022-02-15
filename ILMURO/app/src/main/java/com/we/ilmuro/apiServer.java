package com.we.ilmuro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface apiServer {
    @GET("10ci1v/")
    Call<List<model>> getData();
}
