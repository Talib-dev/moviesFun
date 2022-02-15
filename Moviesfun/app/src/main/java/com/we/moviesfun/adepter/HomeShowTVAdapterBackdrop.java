package com.we.moviesfun.adepter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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

public class HomeShowTVAdapterBackdrop extends RecyclerView.Adapter<HomeShowTVAdapterBackdrop.MyViewHolder> {
    private Context mContext;
    private List<ShowTV> ShowTVList;

    public HomeShowTVAdapterBackdrop(Context mContext, List<ShowTV> showTVList) {
        this.mContext = mContext;
        ShowTVList = showTVList;
    }

    @Override
    public HomeShowTVAdapterBackdrop.MyViewHolder onCreateViewHolder
            (ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.card_home_big, viewGroup, false);
        return new HomeShowTVAdapterBackdrop.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HomeShowTVAdapterBackdrop.MyViewHolder
                                         viewHolder, int i) {
        viewHolder.title.setText(ShowTVList.get(i).getOriginalName());
        String vote = Double.toString(ShowTVList.get(i).getVoteAverage());
        viewHolder.userRating.setText(vote + "/10");
        viewHolder.releaseDate.setText(ShowTVList.get(i).getFirst_air_date());
        Glide.with(mContext).load(ShowTVList.get(i).getBackdropPath()).
                placeholder(R.drawable.load)
                .into(viewHolder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return ShowTVList.size();
    }

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView title, userRating,releaseDate;
    ImageView thumbnail;

     MyViewHolder(View view) {
        super(view);
        title =  view.findViewById(R.id.titlehb);
        userRating =  view.findViewById(R.id.userratinghb);
        thumbnail =  view.findViewById(R.id.thumbnailhb);
        releaseDate= view.findViewById(R.id.date_of_releasehb);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(mContext,
                            DetailActivity.class);
                    intent.putExtra("title",
                            ShowTVList.get(pos).getName());
                    intent.putExtra("poster_path",
                            ShowTVList.get(pos).getPosterPath());
                    intent.putExtra("ShowTV_id",
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

