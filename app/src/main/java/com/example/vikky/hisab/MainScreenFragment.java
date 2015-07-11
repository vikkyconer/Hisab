package com.example.vikky.hisab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
    RVAdapter adapter;
    Place place;
    public LinkedList<Place> placesList;
    View mainScreenRootFragment;
    //    Button addPlace;
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

    @Override
    public void onResume() {
        super.onResume();
        Log.i("MainScreenFragment", "in onResume");

        placeAdded.onNext(((MainScreenActivity) getActivity()).getPlace());


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i("MainScreenFragment", "in onActivityCreated");

        super.onActivityCreated(savedInstanceState);
//        placeAdded.onNext(getActivity());
    }

    private void setEventsForViews() {
        Log.i("MainScreenFragment", "in setEventsForViews");

//        addPlace.setOnClickListener(this);
        recyclerView.setOnClickListener(this);
    }

    private void defaultConfiguration() {
        Log.i("MainScreenFragment", "in defaultConfiguration");

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initializeViews(View view) {
        Log.i("MainScreenFragment", "in initializeViews");

//        addPlace = (Button) view.findViewById(R.id.add_place);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        venueDate = (TextView) view.findViewById(R.id.venue_date);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        places = new ArrayList<>();
        place = new Place();
        adapter = new RVAdapter(places, getActivity());
    }

    public void onNextFunction(Map<String, String> place) {
        Log.i("MainScreenFragment", "onNextFunction");
        Log.i("MainScreenFragment", String.valueOf(place));
//        Place place1 = new Place();
//        this.place.setPlaceName(place.get("placeName"));
//        this.place.setPlaceDate(place.get("placeDate"));
//        places.add(this.place);
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_place, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_place:
//                Navigator.createGameActivity(this);
//                MainScreenFragment mainScreenFragment = new MainScreenFragment();
//                mainScreenFragment.addPlaceData();
                addPlaceData();
                return true;
            case R.id.action_left_drawer:

        }
        return super.onOptionsItemSelected(item);
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
//        Place p = new Place(place.get("placeName"), null, place.get("placeDate"), null, null, null);
//        places.add(p);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
 /*       if (v.getId() == R.id.add_place) {
//            Log.i("MainScreenFragment", "onNext of addPlace");
//            addPlaceData();
        } else if (v.getId() == R.id.rv) {

        }*/
    }

    public void addPlaceData() {
        Log.i("MainScreenFragment", "addPlaceData");
        Dialogue placeData = Dialogue.newInstance();
//        Log.i("MainScreenFragment", "below declaration");
        placeData.inputPlaceName().subscribe(place -> placeSelected(place));
        placeData.show(getFragmentManager(), "Select gender");
    }

    public void placeSelected(Map<String, String> place) {
        Log.i("MainScreenFragment", String.valueOf(place));
//        placeAdded.onNext(place);
//        this.place.setPlaceName(place.get("placeName"));
//        this.place.setPlaceDate(place.get("placeDate"));
        placeAdded.onNext(place);
    }
}
