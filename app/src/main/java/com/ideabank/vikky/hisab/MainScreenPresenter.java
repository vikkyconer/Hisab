package com.ideabank.vikky.hisab;

import android.util.Log;

/**
 * Created by vikky on 6/29/15.
 */
public class MainScreenPresenter {
    public MainScreenPresenter(MainScreenView view, MainScreenModel model) {
        Log.i("Notes", "in MainScreenPresenter");
        view.initializeSavedData();
//        view.initial().subscribe(-> view.showPlaces(place));
        view.addPlace().subscribe(place -> view.showPlaces(place));
//        view.addPlace().subscribe(place -> model.place(place).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).subscribe(places -> view.showPlaces(places)));
    }
}
