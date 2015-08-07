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
        Log.i("ComputeFragment", "onCreateView()");
        computeRootFragment = inflater.inflate(R.layout.compute_fragment, container);
        setRetainInstance(true);
        return computeRootFragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i("ComputeFragment", "onViewCreated()");
        super.onViewCreated(view, savedInstanceState);
        showDetails = (ListView) view.findViewById(R.id.transaction_details_list);
        expenditureModels = new ArrayList<>();
        adapterForDetailsList = new AdapterForDetailsList(getActivity(), expenditureModels);
        showDetails.setAdapter(adapterForDetailsList);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i("ComputeFragment", "onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
        expenditureMap = ((ComputeActivity) getActivity()).getExpenditureMap();
        computeFunction(expenditureMap);
    }

    private void computeFunction(Map<String, Integer> expenditureMap) {
        Log.i("ComputeFragment", "computeFunction()");
        count = 0;
        while (listIsEmpty(expenditureMap)) {
            Log.i("val of listIsEmpty()", String.valueOf(listIsEmpty(expenditureMap)));
            findTop(expenditureMap);
            computation(expenditureMap);
        }
    }

    private void findTop(Map<String, Integer> map) {
        Log.i("ComputeFragment", "findTop()");
        for (Map.Entry<String, Integer> i : map.entrySet()) {
            if (i.getValue() > 0) {
                top = i.getKey();
                break;
            }
        }
    }

    private boolean listIsEmpty(Map<String, Integer> map) {
        Log.i("ComputeFragment", "listIsEmpty()");
        for (Map.Entry<String, Integer> i : map.entrySet()) {
            if (i.getValue() > map.size()) {
                return true;
            }
        }
        return false;
    }

    private void computation(Map<String, Integer> expenditureMap) {
        Log.i("ComputeFragment", "computation()");
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
