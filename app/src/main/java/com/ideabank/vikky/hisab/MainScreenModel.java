package com.ideabank.vikky.hisab;

import java.util.LinkedList;
import java.util.Map;

import rx.Observable;

/**
 * Created by vikky on 6/29/15.
 */
public interface MainScreenModel {
    Observable<LinkedList<Place>> place(Map<String, String> place);
}
