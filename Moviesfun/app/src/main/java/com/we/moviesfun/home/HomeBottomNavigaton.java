package com.we.moviesfun.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.we.moviesfun.R;

public class HomeBottomNavigaton extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    boolean isClicked=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_bottom_navigaton);


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        if (isClicked==false) {
            isClicked=true;
            loadFragment(new MoviesFragment());
        }
        isClicked=false;


    }

    private Boolean loadFragment(Fragment fragment){
        if (fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment=null;
        switch (menuItem.getItemId()){
            case R.id.navigation_movie:
                fragment=new MoviesFragment();
                break;
            case R.id.navigation_TV_show:
                fragment=new TVShowFragment();
                break;
            case R.id.navigation_People:
                fragment=new PeopleFragment();
                break;
        }
        return loadFragment(fragment);
    }
}
