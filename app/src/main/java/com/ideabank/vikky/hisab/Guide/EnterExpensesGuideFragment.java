package com.ideabank.vikky.hisab.Guide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ideabank.vikky.hisab.R;

/**
 * Created by vikky on 7/19/15.
 */
public class EnterExpensesGuideFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_enter_expenses_guide, container, false);
        ((GuideActivity) getActivity()).setCircle(3);
        return rootView;
    }

    public static EnterExpensesGuideFragment newInstance(String text) {

        EnterExpensesGuideFragment f = new EnterExpensesGuideFragment();

        return f;
    }
}
