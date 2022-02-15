package com.we.moviesfun.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PopularPeopleResponse {


    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<PopularPeople> results;

    @SerializedName("total_pages")
    private int totalPages;

    public List<PopularPeople> getDetail() {
        return detail;
    }

    public void setDetail(List<PopularPeople> detail) {
        this.detail = detail;
    }

    @SerializedName("known_for")
    private List<PopularPeople> detail;


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<PopularPeople> getResults() {
        return results;
    }

    public void setResults(List<PopularPeople> results) {
        this.results = results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
