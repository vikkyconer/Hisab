package com.example.vikky.hisab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by vikky on 7/1/15.
 */
public class AddFriendsFragment extends Fragment implements AddFriendsView, View.OnClickListener {
    private View addFriendsRootFragment;
    private Button addFriends, enterExpenses, compute;
    private Friend friend;
    private ArrayList<String> friends, whoPaidList;
    private Bundle details;
    private ArrayList<TransactionDetails> detailsList;
    private int totalAmount = 0, halfamount = 0, amount, value;
    private Map<String, Integer> expenditureMap;
    //    private HashMap hashOfEachExpenditure, hashOfAmountToPay;
    private TransactionDetails transactionDetails;
    private ArrayAdapter<String> friendsAdapter;
    private TransactionDetailsRVAdapter detailsAdapter;
    private RecyclerView detailsRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ListView friendsListView;
    private BehaviorSubject<Map<String, String>> friendAdded = BehaviorSubject.create();
    private String toWhomShouldPay;
    private String whoShouldPay;
    String top;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("AddFriendsFragment", "onCreateView");
        addFriendsRootFragment = inflater.inflate(R.layout.add_friends_fragment, container);
        setRetainInstance(true);
        return addFriendsRootFragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
        defaultConfiguration();
        setEventsForViews();
    }

    private void setEventsForViews() {
        addFriends.setOnClickListener(this);
        enterExpenses.setOnClickListener(this);
        compute.setOnClickListener(this);
    }

    private void defaultConfiguration() {
        friendsListView.setAdapter(friendsAdapter);
        detailsRecyclerView.setLayoutManager(linearLayoutManager);
        detailsRecyclerView.setAdapter(detailsAdapter);

    }

    private void initializeViews(View view) {
        addFriends = (Button) view.findViewById(R.id.add_friends);
        friendsListView = (ListView) view.findViewById(R.id.list_friends);
        enterExpenses = (Button) view.findViewById(R.id.enter_expenses);
        detailsList = new ArrayList<>();
        expenditureMap = new HashMap<>();
        Log.i("AddFriendsFragment", "initilizeViews");
        whoPaidList = new ArrayList<>();
        compute = (Button) view.findViewById(R.id.compute);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        detailsRecyclerView = (RecyclerView) view.findViewById(R.id.details);
        friend = new Friend();
        friends = new ArrayList<>();
        detailsAdapter = new TransactionDetailsRVAdapter(detailsList, getActivity());
        friendsAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, friends);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_friends) {
            showNoticeDialogue();
        } else if (v.getId() == R.id.enter_expenses) {
            showCustomDialogurForWhoPaid();

        } else if (v.getId() == R.id.compute) {
//            computeData();
            computeFunction(expenditureMap);
            printHash(expenditureMap);
            Navigator.toCompute(getActivity(), details);
        }

    }

    private void computeFunction(Map<String, Integer> expenditureMap) {
        while (listIsEmpty(expenditureMap)) {
            findTop(expenditureMap);
            computation(expenditureMap);
        }
    }

    private boolean listIsEmpty(Map<String, Integer> map) {
        for (Map.Entry<String, Integer> i : expenditureMap.entrySet()) {
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
                    Log.i("Notes", i.getKey() + "will pay -> " + (i.getValue() * -1) + "amount to -> " + top);
                    expenditureMap.put(i.getKey(), 0);
                } else {
                    expenditureMap.put(i.getKey(), expenditureMap.get(top) + i.getValue());
                    Log.i("Notes", i.getKey() + "will pay -> " + expenditureMap.get(top) + "amount to -> " + top);
                    expenditureMap.put(top, 0);

                }
            }
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


    private void divideAmongFriends(int amount, ArrayList<String> friends) {

        amount = amount / friends.size();
        int previousAmount;
        for (int i = 0; i < friends.size(); i++) {
            if (expenditureMap.get(friends.get(i)) == null) {
                previousAmount = 0;
            } else {
                previousAmount = expenditureMap.get(friends.get(i));
            }
            expenditureMap.put(friends.get(i), previousAmount - amount);
        }
    }

    private void showCustomDialogurForWhoPaid() {
        DialogueBoxForExpenses dialogueBoxForExpenses = DialogueBoxForExpenses.newInstance();
        dialogueBoxForExpenses.inputPlaceName(friends).subscribe(transactionDetails -> mapTransactionDetails(transactionDetails));
        dialogueBoxForExpenses.show(getFragmentManager(), "who paid");

    }

    private void mapTransactionDetails(Map<String, String> transactionDetails) {
        this.transactionDetails = new TransactionDetails();
        this.transactionDetails.setAmount(transactionDetails.get("amount"));
        this.transactionDetails.setWhoPaid(transactionDetails.get("whoPaid"));
        this.transactionDetails.setDescription(transactionDetails.get("description"));

        detailsList.add(this.transactionDetails);

        calculate(transactionDetails);

    }

    private void calculate(Map<String, String> transactionDetails) {
        amount = Integer.parseInt(transactionDetails.get("amount"));
        boolean[] selected = MultiSelectionSpinner.mSelection;
        ArrayList<String> paidForWhom = new ArrayList<>();
        for (int i = 0; i < selected.length; i++) {
            if (selected[i] == true) {
                paidForWhom.add(friends.get(i));
            }
        }
        int previousAmount;
        if (expenditureMap.get("whoPaid") == null) {
            previousAmount = 0;

        } else {
            previousAmount = expenditureMap.get("whoPaid");
        }
        expenditureMap.put(transactionDetails.get("whoPaid"), amount + previousAmount);

        divideAmongFriends(amount, paidForWhom);
        printHash(expenditureMap);
    }

    private void printHash(Map<String, Integer> map) {
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            String pos = e.getKey();
            Log.i("hashvalue at ", pos + String.valueOf(e.getValue()));
        }
    }

    private void showNoticeDialogue() {
        DialogueForAddingFriends addingFriends = DialogueForAddingFriends.newInstance();
        addingFriends.inputPlaceName().subscribe(friend -> friendEntered(friend));
        addingFriends.show(getFragmentManager(), "add_friend");
    }

    private void friendEntered(Map<String, String> friend) {
        this.friend.setName(friend.get("friendName"));
        friendAdded.onNext(friend);

    }

    @Override
    public Observable<Map<String, String>> enterFriend() {
        return friendAdded.asObservable();
    }

    @Override
    public void showFriend(Map<String, String> friend) {
        friends.add(this.friend.getName());
        if (friends.size() > 1) {
            enterExpenses.setVisibility(View.VISIBLE);
            compute.setVisibility(View.VISIBLE);
        }

        friendsAdapter.notifyDataSetChanged();
    }
}
