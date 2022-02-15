package com.we.moviesfun.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse  {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<Movie> results;
    @SerializedName("total_pages")
    private int totalPages;


    public int getPage() {
        return page;
    }

    public List<Movie> getResults() {
        return results;
    }


    public int getTotalPages() {
        return totalPages;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}

