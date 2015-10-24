package com.ideabank.vikky.hisab;

import rx.Observable;

/**
 * Created by vikky on 6/29/15.
 */
public interface MainScreenView {

    Observable<Place> addPlace();

    void showPlaces(Place place);


    void initializeSavedData();
}
