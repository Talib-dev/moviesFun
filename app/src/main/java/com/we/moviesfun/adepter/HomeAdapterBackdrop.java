package com.we.moviesfun.adepter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.we.moviesfun.DetailActivity;
import com.we.moviesfun.R;
import com.we.moviesfun.model.Movie;

import java.util.List;

public class HomeAdapterBackdrop extends RecyclerView.Adapter<HomeAdapterBackdrop.MyViewHolder> {
    private Context mContext;
    private List<Movie> movieList;


    public HomeAdapterBackdrop(Context mContext, List<Movie> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;

    }

    @Override
    public HomeAdapterBackdrop.MyViewHolder onCreateViewHolder
            (ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.card_home_big, viewGroup, false);
        return new HomeAdapterBackdrop.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HomeAdapterBackdrop.MyViewHolder
                                         viewHolder, int i) {
        viewHolder.title.setText(movieList.get(i).getOriginalTitle());
        String vote = Double.toString(movieList.get(i).getVoteAverage());
        viewHolder.userRating.setText(vote + "/10");
        viewHolder.releaseDate.setText(movieList.get(i).getReleaseDate());
        Glide.with(mContext).load(movieList.get(i).getBackdropPath()).
                placeholder(R.drawable.load)
                .into(viewHolder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, userRating,releaseDate;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.titlehb);
            userRating = (TextView) view.findViewById(R.id.userratinghb);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnailhb);
            releaseDate=(TextView) view.findViewById(R.id.date_of_releasehb);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Movie clickedDataItem = movieList.get(pos);
                        Intent intent = new Intent(mContext,
                                DetailActivity.class);
                        intent.putExtra("title",
                                movieList.get(pos).getTitle());
                        intent.putExtra("poster_path",
                                movieList.get(pos).getPosterPath());
                        intent.putExtra("movie_id",
                                movieList.get(pos).getId());
                        intent.putExtra("backdrop_path",
                                movieList.get(pos).getBackdropPath());
                        intent.putExtra("overview",
                                movieList.get(pos).getOverview());
                        intent.putExtra("vote_average",
                                Double.toString(movieList.get(pos).
                                        getVoteAverage()));
                        intent.putExtra("release_date",
                                movieList.get(pos).getReleaseDate());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);

                    }
                }
            });
        }

    }
}