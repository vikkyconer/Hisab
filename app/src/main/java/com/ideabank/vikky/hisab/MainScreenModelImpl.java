package com.ideabank.vikky.hisab;

import android.util.Log;

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
