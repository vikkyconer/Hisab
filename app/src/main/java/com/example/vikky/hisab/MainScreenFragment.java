package com.example.vikky.hisab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by vikky on 6/29/15.
 */
public class MainScreenFragment extends Fragment implements MainScreenView, View.OnClickListener {

    ArrayList<Place> places;
    RVAdapter adapter;
    String place;
    public LinkedList<Place> placesList;
    View mainScreenRootFragment;
    Button addPlace;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    BehaviorSubject<Map<String, String>> placeAdded = BehaviorSubject.create();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainScreenRootFragment = inflater.inflate(R.layout.main_screen_fragment, container);
        setRetainInstance(true);
        return mainScreenRootFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
        defaultConfiguration();
        setEventsForViews();
    }

    private void setEventsForViews() {
        addPlace.setOnClickListener(this);
        recyclerView.setOnClickListener(this);
    }

    private void defaultConfiguration() {
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initializeViews(View view) {
        addPlace = (Button) view.findViewById(R.id.add_place);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        places = new ArrayList<>();
        adapter = new RVAdapter(places, getActivity());
    }

    //    @Override
    public void showPlaces(LinkedList<Place> allPlacesResponse) {
        if (allPlacesResponse instanceof LinkedList)
            placesList = (LinkedList) allPlacesResponse;
        else
            throw new IllegalArgumentException("sports should be linked list");

    }

    @Override
    public Observable<Map<String, String>> addPlace() {
        return placeAdded.asObservable();
    }

    @Override
    public void showPlaces(Collection<Place> sports) {
        Log.i("Notes", "showPlaces");
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_place) {
            placeAdded.onNext(addPlaceData());
        } else if (v.getId() == R.id.rv) {

        }
    }

    public Map<String, String> addPlaceData() {
        Log.i("Notes", "addPlaceData");
        Dialogue placeData = new Dialogue();
        return placeData.addPlace(getActivity());
    }


}
