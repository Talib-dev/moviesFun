package com.we.moviesfun.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerResponse {
    @SerializedName("id")
    private int id_trailer;
    @SerializedName("results")
    private List<Trailer> results;

    public void setId_trailer(int id_trailer) {
        this.id_trailer = id_trailer;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }

    public int getIdTrailer(){
        return id_trailer;
    }

    public void seIdTrailer(int id_trailer){
        this.id_trailer = id_trailer;
    }

    public List<Trailer> getResults(){
        return results;
    }

    public int getId_trailer() {
        return id_trailer;
    }


}
