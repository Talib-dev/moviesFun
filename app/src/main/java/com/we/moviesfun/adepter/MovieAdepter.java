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

public class MovieAdepter extends RecyclerView.
        Adapter<MovieAdepter.MyViewHolder> {
    private Context mContext;
    private List<Movie> movieList;


    public MovieAdepter(Context mContext, List<Movie> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;

    }

    @Override
    public MovieAdepter.MyViewHolder onCreateViewHolder
            (ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.card_movie, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieAdepter.MyViewHolder
                                         viewHolder, int i) {
        viewHolder.title.setText(movieList.get(i).getOriginalTitle());
        String vote = Double.toString(movieList.get(i).getVoteAverage());
        viewHolder.userrating.setText(vote + "/10");
        Glide.with(mContext).load(movieList.get(i).getPosterPath()).
                placeholder(R.drawable.load)
                .into(viewHolder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, userrating;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            userrating = (TextView) view.findViewById(R.id.userrating);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    if (pos!=RecyclerView.NO_POSITION){
                        Movie clickedDataItem=movieList.get(pos);
                        Intent intent=new Intent(mContext,
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
