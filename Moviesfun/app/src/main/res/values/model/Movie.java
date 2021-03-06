package CoDevelopers.org.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("adult")
    private Boolean adult;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("genre_ids")
    private List<Integer> genreIds=new ArrayList<Integer>();
    @SerializedName("id")
    private Integer id;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("title")
    private String title;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("popularity")
    private Double popularity;
    @SerializedName("vote_count")
    private Integer voteCount;
    @SerializedName("vedio")
    private Boolean vedio;
    @SerializedName("vote_average")
    private Double voteAverage;

    public Movie(String posterPath, Boolean adult, String overview, String releaseDate,
                 List<Integer> genreIds, Integer id, String originalTitle, String originalLanguage,
                 String title, String backdropPath, Double popularity, Integer voteCount,
                 Boolean vedio, Double voteAverage){
        this.posterPath=posterPath;
        this.adult=adult;
        this.overview=overview;
        this.releaseDate=releaseDate;
        this.genreIds=genreIds;
        this.id=id;
        this.originalTitle=originalTitle;
        this.originalLanguage=originalLanguage;
        this.title=title;
        this.backdropPath=backdropPath;
        this.popularity=popularity;
        this.voteCount=voteCount;
        this.vedio=vedio;
        this.voteAverage=voteAverage;

    }




    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public void setVedio(Boolean vedio) {
        this.vedio = vedio;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return ("https://image.tmdb.org/t/p/w500"+posterPath);
    }

    public Boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public Integer getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public Double getPopularity() {
        return popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public Boolean getVedio() {
        return vedio;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }






}
