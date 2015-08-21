package com.example.vikky.hisab;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;


public class MainScreenActivity extends ActionBarActivity {

    private boolean doubleBackToExitPressedOnce = false;

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
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
