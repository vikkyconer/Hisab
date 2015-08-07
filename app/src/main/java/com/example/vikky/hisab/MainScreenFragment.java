package com.example.vikky.hisab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
    TextView addPlace;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    TextView venueDate;
    View view;
    List list;
    DatabaseHelper db = new DatabaseHelper(getActivity());

    public BehaviorSubject<Place> getPlaceAdded() {
        return placeAdded;
    }

    public BehaviorSubject<Place> placeAdded = BehaviorSubject.create();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("MainScreenFragment", "in onCreateView");

        mainScreenRootFragment = inflater.inflate(R.layout.main_screen_fragment, container);
//        db.onUpgrade(db.getWritableDatabase(), 1, 2);
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
    }

    private void defaultConfiguration() {
        Log.i("MainScreenFragment", "in defaultConfiguration");

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(placesAdapter);
    }

    private void initializeViews(View view) {
        Log.i("MainScreenFragment", "in initializeViews");
        addPlace = (TextView) view.findViewById(R.id.add_place);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        venueDate = (TextView) view.findViewById(R.id.venue_date);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        places = new ArrayList<>();
        place = new Place();
        placesAdapter = new RVAdapter(places, getActivity());
        getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter("start.fragment.action"));
    }

    @Override
    public Observable<Place> addPlace() {
        Log.i("MainScreenFragment", "Observable of addPlace");
        return placeAdded.asObservable();
    }

    @Override
    public void showPlaces(Place place) {
        Log.i("MainScreenFragment", "showPlaces");
        places.add(place);
        Log.i("MainScreenFr placesSize", String.valueOf(places.get(0)));
        placesAdapter.notifyDataSetChanged();


    }


    private void colorSelected(String color) {
        Log.i("Notes", color);
//        place.setBackgroundColor(color);
        placesAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        addPlaceData();
    }

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //This piece of code will be executed when you click on your item
            // Call your fragment...
            ColorListViewFragment colorListViewFragment = ColorListViewFragment.newInstance();
            colorListViewFragment.inputPlaceName().subscribe(color -> colorSelected(color));
            colorListViewFragment.show(getFragmentManager(), "timePicker");
        }
    };

    public void addPlaceData() {
        Log.i("MainScreenFragment", "addPlaceData");
        Dialogue placeData = Dialogue.newInstance();
        placeData.inputPlaceName().subscribe(place -> placeSelected(place));
        placeData.show(getFragmentManager(), "Select gender");
    }

    public void placeSelected(Map<String, String> place) {
        Log.i("MainScreenFragment", String.valueOf(place));
        Place placeEntered = new Place();
        placeEntered.setPlaceName(place.get("placeName"));
        placeEntered.setPlaceDate(place.get("placeDate"));
        placeEntered.setDaysAgo(place.get("daysAgo"));
        placeAdded.onNext(placeEntered);

    }
}
