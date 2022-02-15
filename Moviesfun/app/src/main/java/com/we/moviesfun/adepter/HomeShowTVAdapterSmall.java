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

public class HomeShowTVAdapterSmall extends RecyclerView.Adapter<HomeShowTVAdapterSmall.MyViewHolder> {
private Context mContext;
private List<ShowTV> ShowTVList;



public HomeShowTVAdapterSmall(Context mContext, List<ShowTV> ShowTVList) {
        this.mContext = mContext;
        this.ShowTVList= ShowTVList;

        }

@Override
public HomeShowTVAdapterSmall.MyViewHolder onCreateViewHolder
        (ViewGroup viewGroup, int i){
        View view= LayoutInflater.from(viewGroup.getContext()).
        inflate(R.layout.card_home_movie,viewGroup,false);
        return new HomeShowTVAdapterSmall.MyViewHolder(view);
        }

@Override
public void onBindViewHolder(final HomeShowTVAdapterSmall.MyViewHolder
        viewHolder,int i) {
        viewHolder.title.setText(ShowTVList.get(i).getOriginalName());
        String vote =Double.toString(ShowTVList.get(i).getVoteAverage());
        viewHolder.userrating.setText(vote+"/10");
        Glide.with(mContext).load(ShowTVList.get(i).getPosterPath()).
        placeholder(R.drawable.load)
        .into(viewHolder.thumbnail);

        }

@Override
public int getItemCount() {
        return ShowTVList.size();
        }
public class MyViewHolder extends RecyclerView.ViewHolder{
    public TextView title,userrating;
    public ImageView thumbnail;
    public MyViewHolder(View view) {
        super(view);
        title=(TextView) view.findViewById(R.id.movie_title);
        userrating=(TextView) view.findViewById(R.id.user_rating);
        thumbnail=(ImageView) view.findViewById(R.id.image_view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=getAdapterPosition();
                if (pos!=RecyclerView.NO_POSITION){
                    ShowTV clickedDataItem=ShowTVList.get(pos);
                    Intent intent=new Intent(mContext,
                            DetailActivity.class);
                    intent.putExtra("is_ShowTV",1);
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

