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

import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.adapter.RecyclerViewAdapter;

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
    EditText enterPlace;
    List list;
    DatabaseHelper db;

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
        addPlace.setOnClickListener(this);

        final SwipeToDismissTouchListener<RecyclerViewAdapter> touchListener =
                new SwipeToDismissTouchListener<>(
                        new RecyclerViewAdapter(recyclerView),
                        new SwipeToDismissTouchListener.DismissCallbacks<RecyclerViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(RecyclerViewAdapter view, int position) {
//                                adapter.remove(position);
                            }
                        });

        recyclerView.setOnTouchListener(touchListener);
        recyclerView.setOnScrollListener((RecyclerView.OnScrollListener) touchListener.makeScrollListener());
       /* recyclerView.addOnItemTouchListener(new SwipeableItemClickListener(this,
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (view.getId() == R.id.txt_delete) {
                            touchListener.processPendingDismisses();
                        } else if (view.getId() == R.id.txt_undo) {
                            touchListener.undoPendingDismiss();
                        } else { // R.id.txt_data
                            Toast.makeText(context, "Position " + position, LENGTH_SHORT).show();
                        }
                    }
                }));*/
    }

    private void defaultConfiguration() {
        Log.i("MainScreenFragment", "in defaultConfiguration");

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(placesAdapter);
    }

    private void initializeViews(View view) {
        Log.i("MainScreenFragment", "in initializeViews");
//        addPlace = (TextView) view.findViewById(R.id.add_place);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
//        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        venueDate = (TextView) view.findViewById(R.id.venue_date);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        places = new ArrayList<>();
        place = new Place();
        placesAdapter = new RVAdapter(places, getActivity());
//        getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter("start.fragment.action"));
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
        // Creating Place
        Place placeEntered = new Place(place.get("placeName"), Integer.parseInt(place.get("daysAgo")), place.get("placeDate"), 0);

        //inserting under database place
        long place_id = db.createPlace(placeEntered);

        Log.e("Place Count", "Place count: " + db.getPlaceCount());
        placeAdded.onNext(placeEntered);

    }
}
