package com.example.vikky.hisab;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;


public class MainScreenActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Log.i("MainScreenActivity", "in onCreate");
        getSupportActionBar().hide();

        new MainScreenPresenter(mainScreenView(), mainScreenModel());
//        new SlidingDrawerPresenter(slidingDrawerView(), slidingDrawerModel());

    }

    private SlidingDrawerModel slidingDrawerModel() {
        return null;
    }

    private SlidingDrawerView slidingDrawerView() {
        return null; //(SlidingDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.left_drawer);
    }

    private MainScreenModel mainScreenModel() {
        return null;
    }

    private MainScreenView mainScreenView() {
        return (MainScreenFragment) getSupportFragmentManager().findFragmentById(R.id.main_screen_fragment);
    }
}
