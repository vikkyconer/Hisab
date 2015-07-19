package com.example.vikky.hisab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by vikky on 7/19/15.
 */
public class AddPlaceGuideFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_place_guide, container, false);
        ((GuideActivity) getActivity()).setCircle(1);
        return rootView;
    }

    public static AddPlaceGuideFragment newInstance(String text) {

        AddPlaceGuideFragment f = new AddPlaceGuideFragment();

        return f;
    }
}
