package com.example.vikky.hisab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vikky on 7/12/15.
 */
public class ComputeFragment extends Fragment implements ComputeView {
    View computeRootFragment;
    ListView showDetails;
    String whoHasToPay, whomToPay;
    int howMuch;
    Map<String, Integer> expenditureMap = new HashMap<>();
    ArrayList<ExpenditureModel> expenditureModels;
    AdapterForDetailsList adapterForDetailsList;
    String top;
    static int count;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        computeRootFragment = inflater.inflate(R.layout.compute_fragment, container);
        setRetainInstance(true);
        return computeRootFragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showDetails = (ListView) view.findViewById(R.id.transaction_details_list);
        expenditureModels = new ArrayList<>();
        adapterForDetailsList = new AdapterForDetailsList(getActivity(), expenditureModels);
        showDetails.setAdapter(adapterForDetailsList);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        expenditureMap = ((ComputeActivity) getActivity()).getExpenditureMap();
        computeFunction(expenditureMap);
    }

    private void computeFunction(Map<String, Integer> expenditureMap) {
        count = 0;
        while (listIsEmpty(expenditureMap)) {
            findTop(expenditureMap);
            computation(expenditureMap);
        }
    }

    private void findTop(Map<String, Integer> map) {
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            if (e.getValue() > 0) {
                top = e.getKey();
                break;
            }
        }
    }

    private boolean listIsEmpty(Map<String, Integer> map) {
        for (Map.Entry<String, Integer> i : map.entrySet()) {
            if (i.getValue() != 0) {
                return true;
            }
        }
        return false;
    }

    private void computation(Map<String, Integer> expenditureMap) {
        for (Map.Entry<String, Integer> i : expenditureMap.entrySet()) {
            if (i.getKey() != top && (i.getValue() * -1) > 0) {
                if (expenditureMap.get(top) >= (i.getValue() * -1)) {
                    expenditureMap.put(top, expenditureMap.get(top) + i.getValue());
                    howMuch = i.getValue() * -1;
                    whoHasToPay = i.getKey();
                    whomToPay = top;
                    Log.i("Notes", i.getKey() + "will pay -> " + (i.getValue() * -1) + "amount to -> " + top);
                    expenditureMap.put(i.getKey(), 0);
                    count = count + 1;
                } else {
                    expenditureMap.put(i.getKey(), expenditureMap.get(top) + i.getValue());
                    howMuch = expenditureMap.get(top);
                    whoHasToPay = i.getKey();
                    whomToPay = top;
                    Log.i("Notes", i.getKey() + "will pay -> " + expenditureMap.get(top) + "amount to -> " + top);
                    expenditureMap.put(top, 0);
                    count = count + 1;
                }
                ExpenditureModel expenditureModel = new ExpenditureModel();
                expenditureModel.setWhoHasToPay(whoHasToPay);
                expenditureModel.setAmount(howMuch);
                expenditureModel.setWhomToPay(whomToPay);
                expenditureModels.add(expenditureModel);
                Log.i("count", String.valueOf(count));
            }
        }
        adapterForDetailsList.notifyDataSetChanged();
    }
}
