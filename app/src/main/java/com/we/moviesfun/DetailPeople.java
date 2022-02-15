package com.we.moviesfun;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.we.moviesfun.API.Client;
import com.we.moviesfun.API.Service;
import com.we.moviesfun.model.PeopleInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPeople extends AppCompatActivity {

   private Service apiInterface;
    private ImageView imageView;
    private TextView name, dateOfbirth, gender, biography,paceofBirth;
    private int mPersonId;

    Call<List<PeopleInfo>> mPersonDetailsCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_people);

        imageView = (ImageView) findViewById(R.id.iv_profile_photo);
        name = findViewById(R.id.tv_people_name);
        dateOfbirth = findViewById(R.id.tv_people_age);
        gender= findViewById(R.id.tv_gender);
        biography = findViewById(R.id.tv_biography);
        paceofBirth=findViewById(R.id.tv_place_of_birth);

        setData();
    }


    void setData(){
        mPersonId = getIntent().
                getExtras().getInt("PERSON_ID");
        apiInterface= Client.getClient().create(Service.class);
        Call<PeopleInfo> call=apiInterface.getpersonDetail(mPersonId, BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<PeopleInfo>() {
            @Override
            public void onResponse(Call<PeopleInfo> call, Response<PeopleInfo> response) {
                name.setText(response.body().getName());
                dateOfbirth.setText(response.body().getDateOfBirth());
                paceofBirth.setText(response.body().getPlaceOfBirth());
                biography.setText(response.body().getBiography());
                Glide.with(getApplicationContext()).load(response.body().getProfilePath()).
                        placeholder(R.drawable.load)
                        .into(imageView);
                if (response.body().getGender()==2){
                    gender.setText("Male");
                }
                else {
                    gender.setText("Female");
                }


            }

            @Override
            public void onFailure(Call<PeopleInfo> call, Throwable t) {
                Toast.makeText(DetailPeople.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });



    }


//        void loadData(){
//
//            Service apiService = Client.getClient().create(Service.class);
//            mPersonDetailsCall = apiService.
//                    getpersonDetail(mPersonId, BuildConfig.THE_MOVIE_DB_API_TOKEN);
//            mPersonDetailsCall.enqueue(new Callback<PeopleInfo>() {
//                @Override
//                public void onResponse(Call<PeopleInfo> call, Response<PeopleInfo> response) {
//
//                    try {
//                        String name = response.body().getName();
//                        Toast.makeText(DetailPeople.this, ""+name, Toast.LENGTH_SHORT).show();
////                        castTextView.setText("Cast");
////                        castRecyclerView.setAdapter(new MovieCastAdapter(getApplicationContext(),
////                                casts));
////                        castRecyclerView.smoothScrollToPosition(0);
//                    } catch(NullPointerException e)
//                    {
//                        System.out.print("NullPointerException caught");
//                    }
//
////                if (!response.isSuccessful()) {
////                    mMovieCreditsCall = call.clone();
////                    mMovieCreditsCall.enqueue(this);
////                    return;
////                }
////
////                if (response.body() == null) return;
////                if (response.body().getCasts() == null) return;
////
////                for (MovieCast castBrief : response.body().getCasts()) {
////                    if (castBrief != null && castBrief.getName() != null)
////                        castList.add(castBrief);
////                }
////
////                if (!castList.isEmpty())
////                    mCastTextView.setVisibility(View.VISIBLE);
////                mCastAdapter.notifyDataSetChanged();
//                }
//
//                @Override
//                public void onFailure(Call<PeopleInfo> call, Throwable t) {
//
//                }
//            });



        }



//}





