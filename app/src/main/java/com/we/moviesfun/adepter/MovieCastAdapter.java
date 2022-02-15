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
import com.we.moviesfun.DetailPeople;
import com.we.moviesfun.R;
import com.we.moviesfun.model.MovieCast;

import java.util.List;

public class MovieCastAdapter extends RecyclerView.Adapter<MovieCastAdapter.MyViewHolder> {
 private Context context;
 private List<MovieCast> mCasts;

    public MovieCastAdapter(Context context, List<MovieCast> mCasts) {
        this.context = context;
        this.mCasts = mCasts;
    }

    @Override
    public MovieCastAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_show_of_cast, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieCastAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.real_name.setText(mCasts.get(i).getName());
        myViewHolder.cheracter_name.setText(mCasts.get(i).getCharacter());
        Glide.with(context).load(mCasts.get(i).getProfilePath()).
                placeholder(R.drawable.load)
                .into(myViewHolder.photo);
    }




    @Override
    public int getItemCount(){
        return mCasts.size();

    }

     class MyViewHolder extends RecyclerView.ViewHolder{
         TextView cheracter_name;
         TextView real_name;
         ImageView photo;

         MyViewHolder(View view){
            super(view);
            real_name=view.findViewById(R.id.text_view_title_show_cast);
            cheracter_name =  view.findViewById(R.id.text_view_cast_character_show_cast);
            photo = view.findViewById(R.id.image_view_show_cast);
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        int peopleId = mCasts.get(pos).getId();
                        Intent intent = new Intent(context, DetailPeople.class);
                        intent.putExtra("PERSON_ID",peopleId);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                     }
                }
            });

        }
    }
}
