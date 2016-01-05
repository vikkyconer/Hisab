package com.ideabank.vikky.hisab.MainScreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ideabank.vikky.hisab.DatabaseHelper;
import com.ideabank.vikky.hisab.Dialogue;
import com.ideabank.vikky.hisab.Navigator;
import com.ideabank.vikky.hisab.OnStartDragListener;
import com.ideabank.vikky.hisab.Models.Place;
import com.ideabank.vikky.hisab.R;
import com.ideabank.vikky.hisab.Adapters.RVAdapter;
import com.ideabank.vikky.hisab.RecyclerItemClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by vikky on 6/29/15.
 */
public class MainScreenFragment extends Fragment implements MainScreenView, View.OnClickListener, OnStartDragListener {

    private ArrayList<Place> placesList;
    private RVAdapter placesAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DatabaseHelper db;
    private ItemTouchHelper mItemTouchHelper;
    private ImageView addPlace;
    private Map<String, String> placeData;
    private boolean checkDate;

//    public BehaviorSubject<Place> getPlaceAdded() {
//        return placeAdded;
//    }

    public BehaviorSubject<Place> placeAdded = BehaviorSubject.create();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("MainScreenFragment", "in onCreateView");
        setRetainInstance(true);
        return inflater.inflate(R.layout.main_screen_fragment, container);
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
//        placeNameLength = enterPlace.getText().length();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(placesAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                        Log.i("Notes Position", String.valueOf(position));              //change position to place id
                        Navigator.toAddFriends(getActivity(), position + 1);
                    }
                })
        );

//        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(placesAdapter);
//        mItemTouchHelper = new ItemTouchHelper(callback);
//        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void initializeViews(View view) {
        Log.i("MainScreenFragment", "in initializeViews");
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        db = new DatabaseHelper(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        placesList = new ArrayList<>();
        placesAdapter = new RVAdapter(placesList, getActivity());
        placeData = new HashMap<>();
        addPlace = (ImageView) view.findViewById(R.id.add_place);
    }

    @Override
    public Observable<Place> addPlace() {
        Log.i("MainScreenFragment", "Observable of addPlace");
        return placeAdded.asObservable();
    }

    @Override
    public void showPlaces(Place place) {
        placesList.add(place);
        placesAdapter.notifyDataSetChanged();
    }


    @Override
    public void initializeSavedData() {
        List<Place> allPlaces = db.getAllPlaces();
        placesList.clear();
        for (Place place : allPlaces) {
            Log.d("ToDo", place.getPlaceName());
            long updatedDays = updateDaysAgo(place.getPlaceDate());
            place.setDaysAgo((int) updatedDays);

            Place place1 = new Place(place.getPlaceId(), place.getPlaceName() , place.getDaysAgo(), place.getPlaceDate(), place.getNoOfPeopleWent());
            Log.i("after Calling", "places");

            showPlaces(place1);
            Log.i("after calling", "onNext");
        }
    }

    private long updateDaysAgo(String placeDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        long daysAgo = 0;
        Date todaysDate;
        try {
            Date date1 = simpleDateFormat.parse(placeDate);
            todaysDate = new Date();
            long delay = (todaysDate.getTime() - date1.getTime());
            if (delay < 0) {
                Toast.makeText(getActivity(), "incorrect date", Toast.LENGTH_LONG).show();
                //dismiss();
            }
            daysAgo = delay / (24 * 60 * 60 * 1000);
            placeData.put("daysAgo", String.valueOf(daysAgo));
            Log.i("date1", date1 + "");
            //this.inputDate.setText(simpleDateFormat2.format(date1));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return daysAgo;
    }

    @Override
    public void onDestroy() {
        db.closeDB();
        super.onDestroy();
    }

//    private void colorSelected(String color) {
//        Log.i("Notes", color);
////        place.setBackgroundColor(color);
//        placesAdapter.notifyDataSetChanged();
//    }


    @Override
    public void onClick(View v) {
        addPlaceData();
    }

   /* private boolean isValid() {
        int placeLength = enterPlace.getText().length();

        if (placeLength == 0 || checkDate == false) {
            return false;
        }
        return true;
    }*/

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //This piece of code will be executed when you click on your item
            // Call your fragment...
//            ColorListViewFragment colorListViewFragment = ColorListViewFragment.newInstance();
//            colorListViewFragment.inputPlaceName().subscribe(color -> colorSelected(color));
//            colorListViewFragment.show(getFragmentManager(), "timePicker");
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

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        Log.i("Notes", "onStartDrag");
        mItemTouchHelper.startDrag(viewHolder);
    }
}
