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
import com.we.moviesfun.model.PopularPeople;

import java.util.List;

public class PeopleAdepter extends RecyclerView.Adapter<PeopleAdepter.MyViewHolder> {

    private Context mContext;
    private List<PopularPeople> peopleList;

    public PeopleAdepter(Context mContext, List<PopularPeople> peopleList) {
        this.mContext = mContext;
        this.peopleList = peopleList;
    }

    @Override
    public PeopleAdepter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_people_home, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PeopleAdepter.MyViewHolder viewHolder, int i) {
        viewHolder.name.setText(peopleList.get(i).getName());
        Glide.with(mContext).load(peopleList.get(i).getProflePath()).
                placeholder(R.drawable.pre_download)
                .into(viewHolder.proilePhoto);


    }

    @Override
    public int getItemCount() {
        return peopleList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView proilePhoto;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.title);
            proilePhoto = (ImageView) view.findViewById(R.id.imageView);

        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION){
                    Intent intent =new Intent(mContext, DetailPeople.class);
                    intent.putExtra("PERSON_ID",
                            peopleList.get(pos).getId());
                    mContext.startActivity(intent);

                     }
                  }
                 });

              }
        }
    }


