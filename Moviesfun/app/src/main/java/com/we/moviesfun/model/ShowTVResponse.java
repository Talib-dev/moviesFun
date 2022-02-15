package com.we.moviesfun.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShowTVResponse {

    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<ShowTV> results;
    @SerializedName("total_pages")
    private int totalPages;

    public ShowTVResponse(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public List<ShowTV> getResults() {
        return results;
    }


    public int getTotalPages() {
        return totalPages;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setResults(List<ShowTV> results) {
        this.results = results;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
