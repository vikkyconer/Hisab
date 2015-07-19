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
    Button addFriends, enterExpenses, compute;
    Friend friend;
    ArrayList<String> friends;
    ArrayList<String> whoPaidList;
    Bundle details;
    ArrayList<TransactionDetails> detailsList;
    private int totalAmount = 0, halfamount = 0;
    private HashMap hashOfEachExpenditure;
    private HashMap hashOfAmountToPay;
    TransactionDetails transactionDetails;
    ArrayAdapter<String> friendsAdapter;
    TransactionDetailsRVAdapter detailsAdapter;
    RecyclerView detailsRecyclerView;
    LinearLayoutManager linearLayoutManager;
    ListView friendsListView;
    int amount;
    DialogueBoxForExpenses inputWhoPaid;
    BehaviorSubject<Map<String, String>> friendAdded = BehaviorSubject.create();
    private String toWhomShouldPay;
    private String whoShouldPay;
    private int value;

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
        hashOfEachExpenditure = new HashMap();
        hashOfAmountToPay = new HashMap();

        initializeHashOfEachExpenditure();

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
            computeData();
            Navigator.toCompute(getActivity(), details);
        }

    }

    private void computeData() {
        halfamount = totalAmount / 2;
        details = new Bundle();
//        details.putString("amount", String.valueOf(totalAmount));
        Log.i("totalAmount", String.valueOf(halfamount));
        for (int i = 0; i < friends.size(); i++) {
            value = (int) hashOfEachExpenditure.get(i);
            Log.i("value at i", String.valueOf(hashOfEachExpenditure.get(i)));
            Log.i("value", String.valueOf(value));
            hashOfAmountToPay.put(i, halfamount - value);
            if ((int) hashOfAmountToPay.get(i) > 0) {
                Log.i("Notes", "in if of hashComputation");
                details.putString("amount", String.valueOf(hashOfAmountToPay.get(i)));
                details.putString("whoShouldPay", friends.get(i));
            } else {
                Log.i("Notes", "in else of hashComputation");
                details.putString("payToWhom", friends.get(i));
            }
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
        this.transactionDetails.setForWhom(transactionDetails.get("paidForWhom"));
        this.transactionDetails.setDescription(transactionDetails.get("description"));

        detailsList.add(this.transactionDetails);

        amount = Integer.parseInt(transactionDetails.get("amount"));
        totalAmount = amount + totalAmount;

        Log.i("totalAmount", String.valueOf(totalAmount));

        toWhomShouldPay = friends.get(Integer.parseInt(transactionDetails.get("friendWhoPaidIndex")));

        Log.i("hash value before if", String.valueOf(hashOfEachExpenditure.get(Integer.parseInt(transactionDetails.get("friendWhoPaidIndex")))));

        if ((int) hashOfEachExpenditure.get(Integer.parseInt(transactionDetails.get("friendWhoPaidIndex"))) > 0) {
            amount = amount + (int) hashOfEachExpenditure.get(Integer.parseInt(transactionDetails.get("friendWhoPaidIndex")));
            Log.i("amount value", String.valueOf(amount));
        }

        hashOfEachExpenditure.put(Integer.parseInt(transactionDetails.get("friendWhoPaidIndex")), amount);
        printHash();

        whoShouldPay = friends.get(Integer.parseInt(transactionDetails.get("friendPaidForWhomIndex")));
        whoPaidList.add(transactionDetails.get("whoPaid"));

        Log.i("List", whoPaidList.get(0));
        detailsAdapter.notifyDataSetChanged();
     /*   details.putParcelable("details",detailsList);
//        details.putStringArrayList("details",detailsList);
        details.putString("whoShoudPay", whoShouldPay);
        details.putString("to");
        details.putString("paidForWhom", transactionDetails.get("paidForWhom"));
        details.putString("amount", transactionDetails.get("amount"));
        totalAmount = totalAmount + Integer.parseInt(transactionDetails.get("amount"));
        details.putInt("totalAmount", totalAmount);
        details.putString("description", transactionDetails.get("description"))*/
        ;

    }

    private void printHash() {
        for (int i = 0; i < hashOfEachExpenditure.size(); i++) {
            Log.i("hashValue at i", String.valueOf(hashOfEachExpenditure.get(i)));
        }
    }

    private void initializeHashOfEachExpenditure() {
        for (int i = 0; i < 10; i++) {
            hashOfEachExpenditure.put(i, 0);
            hashOfAmountToPay.put(i, 0);
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
