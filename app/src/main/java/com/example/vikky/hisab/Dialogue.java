package com.example.vikky.hisab;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import java.util.Map;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by vikky on 6/30/15.
 */
public class Dialogue extends DialogFragment {
    LayoutInflater layoutInflater;
    View view;
    EditText inputPlace;
    String place;
    String date;
    Map<String, String> placeData;
    Context context;
    BehaviorSubject<Map<String, String>> placeName = BehaviorSubject.create();

    static Dialogue newInstance() {
        Log.i("Notes", "newInstance called");
        Dialogue dialogue = new Dialogue();
        return dialogue;
    }

    public Observable<Map<String, String>> inputPlaceName() {
        Log.i("Notes", "Observable called");
        return placeName.asObservable();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialogue_box, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Log.i("Notes", "onCreateView");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Notes", "onActivityCreated called");
    }

    public void onPlaceNameSet() {
        Log.i("Notes", "onNext called");
        placeName.onNext(placeData);
    }
}

