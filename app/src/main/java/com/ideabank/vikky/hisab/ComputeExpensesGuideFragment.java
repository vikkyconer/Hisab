package com.ideabank.vikky.hisab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by vikky on 7/19/15.
 */
public class ComputeExpensesGuideFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_compute_expenses_guide, container, false);
        ((GuideActivity) getActivity()).setCircle(4);
        return rootView;
    }

    public static ComputeExpensesGuideFragment newInstance(String text) {

        ComputeExpensesGuideFragment f = new ComputeExpensesGuideFragment();

        return f;
    }
}
