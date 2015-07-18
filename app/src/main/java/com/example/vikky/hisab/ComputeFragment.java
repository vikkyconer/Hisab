package com.example.vikky.hisab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vikky on 7/12/15.
 */
public class ComputeFragment extends Fragment implements ComputeView {
    View computeRootFragment;
    //    ListView showDetails;
    ArrayList<TransactionDetails> arrayList = new ArrayList<>();
    String whoHasToPay, howMuch, whomToPay;
    TextView whoPais, amount, toWhom;

    TransactionDetails details;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        computeRootFragment = inflater.inflate(R.layout.transaction_details_list_item, container);
        setRetainInstance(true);
        return computeRootFragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        showDetails = (ListView) view.findViewById(R.id.transaction_details_list);
        whoPais = (TextView) view.findViewById(R.id.who_has_to_pay);
        amount = (TextView) view.findViewById(R.id.how_much);
        toWhom = (TextView) view.findViewById(R.id.whom_to_pay);


//        showDetails.setAdapter(new AdapterForDetailsList(getActivity(), whoHasToPay, howMuch, whomToPay));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        howMuch = ((ComputeActivity) getActivity()).getAmount();
//        Log.i("ComputeFragment", howMuch);
//        arrayList = ((ComputeActivity) getActivity()).getDetails();
//        Log.i("ComputeFragment0", arrayList.get(0).whoPaid);
//        Log.i("ComputeFragment1", arrayList.get(1).whoPaid);
//        whomToPay = ((ComputeActivity) getActivity()).getWhoPaid();
//        whoPais.setText(whoHasToPay);
//        amount.setText(howMuch);
//        toWhom.setText(whomToPay);
    }
}
