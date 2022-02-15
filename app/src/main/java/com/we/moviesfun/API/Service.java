package com.we.moviesfun.API;

import com.we.moviesfun.model.MovieCastResponse;
import com.we.moviesfun.model.MovieResponse;
import com.we.moviesfun.model.PeopleInfo;
import com.we.moviesfun.model.PopularPeopleResponse;
import com.we.moviesfun.model.ShowTVResponse;
import com.we.moviesfun.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {


    //Trailer

    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getMovieTrailer
            (@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/credits")
    Call<MovieCastResponse> getMovieCast
            (@Path("movie_id") int id, @Query("api_key") String apiKey);


    // TV Shows
    @GET("trending/tv/week")
    Call<ShowTVResponse> getTrendimgTVShowsFirstPage(@Query("api_key") String apiKey);



    @GET("tv/popular")
    Call<ShowTVResponse> getPopularTVShowsFirstPage(@Query("api_key") String apiKey);

    @GET("tv/top_rated")
    Call<ShowTVResponse> getTopRatedTVShowsFirstPage(@Query("api_key") String apiKey);

    @GET("tv/on_the_air")
    Call<ShowTVResponse> getOnTVShowsFirstPage(@Query("api_key") String apiKey);

    @GET("tv/airing_today")
    Call<ShowTVResponse> getAiringTodayTVShowsFirstPage(@Query("api_key") String apiKey);

    @GET("trending/tv/week")
    Call<ShowTVResponse> getTrendimgTVShows(@Query("api_key") String apiKey, @Query("page") Integer page);


    @GET("tv/popular")
    Call<ShowTVResponse> getPopularTVShows(@Query("api_key") String apiKey, @Query("page") Integer page);

    @GET("tv/top_rated")
    Call<ShowTVResponse> getTopRatedTVShows(@Query("api_key") String apiKey, @Query("page") Integer page);

    @GET("tv/on_the_air")
    Call<ShowTVResponse> getOnTVShows(@Query("api_key") String apiKey, @Query("page") Integer page);

    @GET("tv/airing_today")
    Call<ShowTVResponse> getAiringTodayTVShows(@Query("api_key") String apiKey, @Query("page") Integer page);


    //people

    @GET("person/popular")
    Call<PopularPeopleResponse> getPopularPeopleMovies(@Query("api_key") String apiKey, @Query("page") Integer page);
    @GET("person/{id}")
    Call<PeopleInfo> getpersonDetail(@Path("id") int id, @Query("api_key") String api);





    //MOVIES

    @GET("movie/popular")
    Call<MovieResponse> getPopularMoviesFirstPage(@Query("api_key") String apiKey);

    @GET("trending/movie/week")
    Call<MovieResponse> getTreandingMoviesFirstPage(@Query("api_key") String apiKey);


    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMoviesFirstPage(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMoviesFirstPage(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<MovieResponse> getNowShowingMoviesFirstpage(@Query("api_key") String apiKey);

    @GET("trending/movie/week")
    Call<MovieResponse> getTreandingMovies(@Query("api_key") String apiKey, @Query("page") Integer page);


    @GET("movie/now_playing")
    Call<MovieResponse> getNowShowingMovies(@Query("api_key") String apiKey, @Query("page") Integer page);

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey, @Query("page") Integer page);


    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovies(@Query("api_key") String apiKey, @Query("page") Integer page);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey, @Query("page") Integer page);


}
