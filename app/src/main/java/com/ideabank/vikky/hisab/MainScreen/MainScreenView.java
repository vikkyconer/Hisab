package com.ideabank.vikky.hisab.MainScreen;

import com.ideabank.vikky.hisab.Models.Place;

import rx.Observable;

/**
 * Created by vikky on 6/29/15.
 */
public interface MainScreenView {

    Observable<Place> addPlace();

    void showPlaces(Place place);


    void initializeSavedData();
}
