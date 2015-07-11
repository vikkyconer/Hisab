package com.example.vikky.hisab;

import java.util.Map;

import rx.Observable;

/**
 * Created by vikky on 6/29/15.
 */
public interface MainScreenView {

    Observable<Map<String, String>> addPlace();

    void showPlaces(Map<String, String> sports);
}
