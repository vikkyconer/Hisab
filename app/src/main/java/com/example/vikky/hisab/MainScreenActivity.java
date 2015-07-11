package com.example.vikky.hisab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;


public class MainScreenActivity extends ActionBarActivity {

    Fragment slidingDrawerFragment;
    public static final String DRAWER_FRAGMENT_TAG = "DRAWER_FRAGMENT_TAG";
    Map<String, String> place = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Log.i("MainScreenActivity", "in onCreate");

        new MainScreenPresenter(mainScreenView(), mainScreenModel());
//        new SlidingDrawerPresenter(slidingDrawerView(), slidingDrawerModel());

    }

    private SlidingDrawerModel slidingDrawerModel() {
        return null;
    }

    private SlidingDrawerView slidingDrawerView() {
        return null; //(SlidingDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.left_drawer);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("MainScreenActivity", "in onCreateOptionsMenu");

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_place, menu);
        return super.onCreateOptionsMenu(menu);
    }

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("MainScreenActivity", "in onOptionsItemSelected");

        switch (item.getItemId()) {
            case R.id.action_new_place:
//                Navigator.createGameActivity(this);
//                MainScreenFragment mainScreenFragment = new MainScreenFragment();
//                mainScreenFragment.addPlaceData();
                Dialogue placeData = Dialogue.newInstance();
                placeData.inputPlaceName().subscribe(place -> placeSelected(place));
                placeData.show(getSupportFragmentManager(), "Select gender");
                return true;
            case R.id.action_left_drawer:

        }
        return super.onOptionsItemSelected(item);
    }*/

    public Map<String, String> getPlace() {
        return place;
    }

    private void placeSelected(Map<String, String> place) {
        Log.i("MainScreenActivity", "in placeSelected");

        Log.i("MainScreenActivity", String.valueOf(place));
        this.place = place;
//        MainScreenFragment mainScreenFragment = new MainScreenFragment();
//        mainScreenFragment.onNextFunction(place);
//        mainScreenFragment.placeAdded.asObservable();
//        mainScreenFragment.placeAdded.onNext(place);

//        new MainScreenFragment(this.place);
//        mainScreenFragment.placeAdded.onNext(place);
    }

    private MainScreenModel mainScreenModel() {
        return null;
    }

    private MainScreenView mainScreenView() {
        return (MainScreenFragment) getSupportFragmentManager().findFragmentById(R.id.main_screen_fragment);
    }
}
