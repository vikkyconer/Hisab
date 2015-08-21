package com.example.vikky.hisab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    //    TextView addPlace;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    String venueDate;
    View view;
    EditText enterPlace;
    long delay, daysAgo;
    List list;
    DatabaseHelper db;
    int placeNameLength;
    TextView date, go;
    private Map<String, String> placeData;
    private boolean checkDate;

    public BehaviorSubject<Place> getPlaceAdded() {
        return placeAdded;
    }

    public BehaviorSubject<Place> placeAdded = BehaviorSubject.create();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("MainScreenFragment", "in onCreateView");

        mainScreenRootFragment = inflater.inflate(R.layout.main_screen_fragment, container);
        db = new DatabaseHelper(getActivity());
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
        date.setOnClickListener(this);
        go.setOnClickListener(this);

    }

    private void defaultConfiguration() {
        Log.i("MainScreenFragment", "in defaultConfiguration");
        placeNameLength = enterPlace.getText().length();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(placesAdapter);

    }

    private void initializeViews(View view) {
        Log.i("MainScreenFragment", "in initializeViews");
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
//        venueDate = (TextView) view.findViewById(R.id.venue_date);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        places = new ArrayList<>();
        place = new Place();
        placesAdapter = new RVAdapter(places, getActivity());
        date = (TextView) view.findViewById(R.id.date);
        enterPlace = (EditText) view.findViewById(R.id.enter_place);
        placeData = new HashMap<>();
        go = (TextView) view.findViewById(R.id.go);
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


    @Override
    public void initial() {
        List<Place> allPlaces = db.getAllPlaces();
        places.clear();
        for (Place place : allPlaces) {
            Log.d("ToDo", place.getPlaceName());
            Place place1 = new Place(place.getPlaceId(), place.getPlaceName(), place.getDaysAgo(), place.getPlaceDate(), place.getNoOfPeopleWent());
            Log.i("after Calling", "places");
            showPlaces(place1);
            Log.i("after calling", "onNext");
        }
    }

    @Override
    public void onDestroy() {
        db.closeDB();
        super.onDestroy();
    }

    private void colorSelected(String color) {
        Log.i("Notes", color);
//        place.setBackgroundColor(color);
        placesAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date:
                checkDate = true;
                DateFragment newFragment = new DateFragment();
                newFragment.show(getFragmentManager(), "timePicker");

                newFragment.selectedDate().subscribe(date -> {
                    venueDate = date;
                    Log.i("Date", date);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MMM-yyyy");
                    placeData.put("placeDate", venueDate);
                    try {
                        Date date1 = simpleDateFormat.parse(date);
                        Date delay = new Date();
                        this.delay = (delay.getTime() - date1.getTime());
                        if (this.delay < 0) {
                            Toast.makeText(getActivity(), "incorrect date", Toast.LENGTH_LONG).show();
//                            dismiss();
                        }
                        daysAgo = this.delay / (24 * 60 * 60 * 1000);
                        placeData.put("daysAgo", String.valueOf(daysAgo));
                        Log.i("date1", date1 + "");
                        this.date.setText(simpleDateFormat2.format(date1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                });
                break;
            case R.id.go:
                Log.i("Notes", "lets Go");
                if (isValid()) {
                    placeData.put("placeName", String.valueOf(enterPlace.getText()));
                    placeSelected(placeData);
//                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "Enter Venue and Date", Toast.LENGTH_LONG).show();
                }
                checkDate = false;
                enterPlace.setText("Enter Place Name...");
                date.setText("Date");
                break;
        }
        addPlaceData();
    }

    private boolean isValid() {
        int placeLength = enterPlace.getText().length() - placeNameLength;

        if (placeLength == 0 || checkDate == false || enterPlace.getText().length() == 0) {
            return false;
        }
        return true;
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
//        Dialogue placeData = Dialogue.newInstance();
//        placeData.inputPlaceName().subscribe(place -> placeSelected(place));
//        placeData.show(getFragmentManager(), "Select gender");
    }

    public void placeSelected(Map<String, String> place) {
        Log.i("MainScreenFragment", String.valueOf(place));
        // Creating Place
        Place placeEntered = new Place(place.get("placeName"), Integer.parseInt(place.get("daysAgo")), place.get("placeDate"), 0);

        //inserting under database place
        long place_id = db.createPlace(placeEntered);

        Log.e("Place Count", "Place count: " + db.getPlaceCount());
        placeAdded.onNext(placeEntered);

    }
}
