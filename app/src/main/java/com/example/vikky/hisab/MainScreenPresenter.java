package com.example.vikky.hisab;

import android.util.Log;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vikky on 6/29/15.
 */
public class MainScreenPresenter {
    public MainScreenPresenter(MainScreenView view, MainScreenModel model) {
        Log.i("Notes", "in MainScreenPresenter");
        view.addPlace().subscribe(place -> model.place(place).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(places -> view.showPlaces(places)));
    }
}
