package com.example.vikky.hisab;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by vikky on 7/16/15.
 */
public class ColorListViewFragment extends DialogFragment {
    ListView changeBackgroundListView;
    ListAdapter adapter;
    ArrayList<Color> colorsList;
    ArrayList<String> colorName;
    BehaviorSubject<String> colorHash = BehaviorSubject.create();

    static ColorListViewFragment newInstance() {
        Log.i("Dialogue", "newInstance called");
        ColorListViewFragment colorListViewFragment = new ColorListViewFragment();
        return colorListViewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.color_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
        DefaultConfiguration();
        SetEventsForViews();

    }

    private void initializeViews(View view) {
        changeBackgroundListView = (ListView) view.findViewById(R.id.change_background);
        colorsList = new ArrayList<>();
        colorName = new ArrayList<>();
        colorName.add("Red");
        colorName.add("Blue");
        colorName.add("Green");
        Color color = new Color();
        color.setColorName("red");
        color.setColorHexValue("#e64e4e");
        colorHash.onNext("#e64e4e");
        color.setColorPosition("0");
        color.setStatus(false);
        colorsList.add(color);
        adapter = new ListAdapter(getActivity(),colorName);
    }

    private void DefaultConfiguration() {
        changeBackgroundListView.setAdapter(adapter);
    }

    private void SetEventsForViews() {

    }

    public Observable<String> inputPlaceName() {
        return colorHash.asObservable();
    }
}
