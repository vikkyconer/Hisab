package com.ideabank.vikky.hisab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by vikky on 7/19/15.
 */
public class LetsGoFragment extends Fragment implements View.OnClickListener {
    ImageView letGo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lets_go, container, false);
        ((GuideActivity) getActivity()).setCircle(5);
        return rootView;
    }


    public static LetsGoFragment newInstance(String text) {

        LetsGoFragment f = new LetsGoFragment();

        return f;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        letGo = (ImageView) view.findViewById(R.id.lets_go);
        letGo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getActivity(), MainScreenActivity.class);
        getActivity().startActivity(i);
//                mp.stop();
        getActivity().finish();
    }
}
