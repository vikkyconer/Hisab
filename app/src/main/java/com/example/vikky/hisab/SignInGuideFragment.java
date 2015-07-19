package com.example.vikky.hisab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by vikky on 7/19/15.
 */
public class SignInGuideFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_in_guide, container, false);
        ((GuideActivity) getActivity()).setCircle(0);
        return rootView;
    }

    public static SignInGuideFragment newInstance() {

        SignInGuideFragment f = new SignInGuideFragment();


        return f;
    }
}
