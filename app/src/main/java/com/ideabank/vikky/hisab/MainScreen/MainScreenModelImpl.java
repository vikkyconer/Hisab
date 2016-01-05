package com.ideabank.vikky.hisab.MainScreen;

import android.util.Log;

import com.ideabank.vikky.hisab.Models.Place;

import java.util.LinkedList;
import java.util.Map;

import rx.Observable;

/**
 * Created by vikky on 7/3/15.
 */
public class MainScreenModelImpl implements MainScreenModel {
    @Override
    public Observable<LinkedList<Place>> place(Map<String, String> place) {
        Log.i("Notes", "in model");
        return null;
    }
}
