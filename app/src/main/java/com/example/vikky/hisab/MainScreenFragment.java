package com.example.vikky.hisab;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by vikky on 6/29/15.
 */
public class MainScreenFragment extends Fragment implements MainScreenView, View.OnClickListener {

    ArrayList<Place> places;
    RVAdapter adapter;
    public LinkedList<Place> placesList;
    View mainScreenRootFragment;
    Button addPlace;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;


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
        adapter = new RVAdapter(places);
    }

    @Override
    public void showPlaces(LinkedList<Place> allPlacesResponse) {
        if (allPlacesResponse instanceof LinkedList)
            placesList = (LinkedList) allPlacesResponse;
        else
            throw new IllegalArgumentException("sports should be linked list");

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_place) {
            showNoticeDialogue();
        } else if (v.getId() == R.id.rv) {
            
        }
    }

    private void showNoticeDialogue() {
        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.dialogue_box, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptsView);
        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                places.add(new Place(String.valueOf(userInput.getText()), null, null, null));
                                adapter.notifyDataSetChanged();
                                Log.i("Notes", "before calling onItemOnClick");
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
