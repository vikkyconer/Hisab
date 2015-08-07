package com.example.vikky.hisab;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;


public class MainScreenActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Log.i("MainScreenActivity", "in onCreate");
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.blue1));


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

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Bye", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}
