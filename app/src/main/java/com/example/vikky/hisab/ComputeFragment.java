package com.example.vikky.hisab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by vikky on 7/12/15.
 */
public class ComputeFragment extends Fragment implements ComputeView {
    View computeRootFragment;
    TransactionDetails details;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        computeRootFragment = inflater.inflate(R.layout.add_friends_fragment, container);
        setRetainInstance(true);
        return computeRootFragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        details = new TransactionDetails();
//        details = ((AddFriendsActivity) getActivity()).getDetails();
//        Log.i("ComputeActivity", details.amount);
    }
}
