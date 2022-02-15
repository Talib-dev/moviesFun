package CoDevelopers.org.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse  {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<CoDevelopers.org.model.Movie> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public int getPage() {
        return page;
    }

    public List<CoDevelopers.org.model.Movie> getResults() {
        return results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setResults(List<CoDevelopers.org.model.Movie> results) {
        this.results = results;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}

