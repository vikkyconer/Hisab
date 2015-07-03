package com.example.vikky.hisab;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;


public class MainScreenActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Log.i("Notes", "in MainScreenActivity");
        new MainScreenPresenter(mainScreenView(), mainScreenModel());
    }

    private MainScreenModel mainScreenModel() {
        return null;
    }

    private MainScreenView mainScreenView() {
        return (MainScreenFragment) getSupportFragmentManager().findFragmentById(R.id.main_screen_fragment);
    }
}
