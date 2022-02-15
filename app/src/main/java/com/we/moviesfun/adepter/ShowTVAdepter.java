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
import com.we.moviesfun.model.ShowTV;


import java.util.List;

public class ShowTVAdepter extends RecyclerView.Adapter<ShowTVAdepter.MyViewHolder> {
    private Context mContext;
    private List<ShowTV> ShowTVList;


    public ShowTVAdepter(Context mContext, List<ShowTV> ShowTVList) {
        this.mContext = mContext;
        this.ShowTVList = ShowTVList;

    }

    @Override
    public ShowTVAdepter.MyViewHolder onCreateViewHolder
            (ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.card_movie, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShowTVAdepter.MyViewHolder
                                         viewHolder, int i) {
        viewHolder.title.setText(ShowTVList.get(i).getName());
        String vote = Double.toString(ShowTVList.get(i).getVoteAverage());
        viewHolder.userrating.setText(vote + "/10");
        Glide.with(mContext).load(ShowTVList.get(i).getPosterPath()).
                placeholder(R.drawable.load)
                .into(viewHolder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return ShowTVList.size();
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
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        ShowTV clickedDataItem = ShowTVList.get(pos);
                        Intent intent = new Intent(mContext,
                                DetailActivity.class);
                        intent.putExtra("is_movie",0);
                        intent.putExtra("title",
                                ShowTVList.get(pos).getName());
                        intent.putExtra("poster_path",
                                ShowTVList.get(pos).getPosterPath());
                        intent.putExtra("movie_id",
                                ShowTVList.get(pos).getId());
                        intent.putExtra("backdrop_path",
                                ShowTVList.get(pos).getBackdropPath());
                        intent.putExtra("overview",
                                ShowTVList.get(pos).getOverview());
                        intent.putExtra("vote_average",
                                Double.toString(ShowTVList.get(pos).
                                        getVoteAverage()));
                        intent.putExtra("release_date",
                                ShowTVList.get(pos).getFirst_air_date());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);

                    }
                }
            });
        }
    }
}


