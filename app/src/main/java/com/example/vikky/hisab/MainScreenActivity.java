package com.example.vikky.hisab;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class MainScreenActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        new MainScreenPresenter(mainScreenView(), mainScreenModel());
    }

    private MainScreenModel mainScreenModel() {
        return null;
    }

    private MainScreenView mainScreenView() {
        return null;
    }
}
