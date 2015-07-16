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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by vikky on 6/29/15.
 */
public class MainScreenFragment extends Fragment implements MainScreenView, View.OnClickListener {

    ArrayList<Place> places;
    RVAdapter placesAdapter;
    Place place;
    public LinkedList<Place> placesList;
    View mainScreenRootFragment;
    Button addPlace;
    LayoutInflater layoutInflater;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    TextView venueDate;
    View view;

    public BehaviorSubject<Map<String, String>> getPlaceAdded() {
        return placeAdded;
    }

    public BehaviorSubject<Map<String, String>> placeAdded = BehaviorSubject.create();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("MainScreenFragment", "in onCreateView");

        mainScreenRootFragment = inflater.inflate(R.layout.main_screen_fragment, container);
        setRetainInstance(true);
        return mainScreenRootFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i("MainScreenFragment", "in onViewCreated");

        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
        defaultConfiguration();
        setEventsForViews();
    }

    private void setEventsForViews() {
        Log.i("MainScreenFragment", "in setEventsForViews");

        addPlace.setOnClickListener(this);
        recyclerView.setOnClickListener(this);

    }

    private void defaultConfiguration() {
        Log.i("MainScreenFragment", "in defaultConfiguration");

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(placesAdapter);
    }

    private void initializeViews(View view) {
        Log.i("MainScreenFragment", "in initializeViews");

        addPlace = (Button) view.findViewById(R.id.add_place);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        venueDate = (TextView) view.findViewById(R.id.venue_date);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        places = new ArrayList<>();
        place = new Place();
        placesAdapter = new RVAdapter(places, getActivity());
    }

    @Override
    public Observable<Map<String, String>> addPlace() {
        Log.i("MainScreenFragment", "Observable of addPlace");
        return placeAdded.asObservable();
    }

    @Override
    public void showPlaces(Map<String, String> place) {
        Log.i("MainScreenFragment", "showPlaces");
        places.add(this.place);
        placesAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_place) {
            addPlaceData();
        } else if (v.getId() == R.id.rv) {

        }
    }

    public void addPlaceData() {
        Log.i("MainScreenFragment", "addPlaceData");
        Dialogue placeData = Dialogue.newInstance();
        placeData.inputPlaceName().subscribe(place -> placeSelected(place));
        placeData.show(getFragmentManager(), "Select gender");
    }

    public void placeSelected(Map<String, String> place) {
        Log.i("MainScreenFragment", String.valueOf(place));
        this.place.setPlaceName(place.get("placeName"));
        this.place.setPlaceDate(place.get("placeDate"));
        placeAdded.onNext(place);
    }
}
