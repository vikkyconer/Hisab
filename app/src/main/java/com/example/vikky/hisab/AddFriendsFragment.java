package com.example.vikky.hisab;

import android.app.Service;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    private TextView addFriends;
    private Button enterExpenses;
    private Button compute;
    private Friend friend;
    private ArrayList<String> friends;
    TextView names, firstLetter;
    RelativeLayout listFriends;
    private ArrayList<TransactionDetails> detailsList;
    private int amount;
    LinearLayout friendsNameContainer;
    private Map<String, Integer> expenditureMap;
    private TransactionDetails transactionDetails;
    //    private ArrayAdapter<String> friendsAdapter;
    private TransactionDetailsRVAdapter detailsAdapter;
    private RecyclerView detailsRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    //    private ListView friendsListView;
    private BehaviorSubject<Map<String, String>> friendAdded = BehaviorSubject.create();
    private ArrayList<String> paidForWhom;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("AddFriendsFragment","onCreateView()");
        addFriendsRootFragment = inflater.inflate(R.layout.add_friends_fragment, container);
        setRetainInstance(true);
        return addFriendsRootFragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i("AddFriendsFragment","onViewCreated()");
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
//        friendsListView.setAdapter(friendsAdapter);
        detailsRecyclerView.setLayoutManager(linearLayoutManager);
        detailsRecyclerView.setAdapter(detailsAdapter);
    }

    private void initializeViews(View view) {
        addFriends = (TextView) view.findViewById(R.id.add_friends);
        names = (TextView) view.findViewById(R.id.first_name);
        firstLetter = (TextView) view.findViewById(R.id.names_first_letter);
        listFriends = (RelativeLayout) view.findViewById(R.id.list_friends);
        enterExpenses = (Button) view.findViewById(R.id.enter_expenses);
        friendsNameContainer = (LinearLayout) view.findViewById(R.id.friends_name_container);
        detailsList = new ArrayList<>();
        paidForWhom = new ArrayList<>();
        expenditureMap = new HashMap<>();
        compute = (Button) view.findViewById(R.id.compute);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        detailsRecyclerView = (RecyclerView) view.findViewById(R.id.details);
        friend = new Friend();
        friends = new ArrayList<>();
        detailsAdapter = new TransactionDetailsRVAdapter(detailsList, getActivity(), paidForWhom);
//        friendsAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, friends);
    }

    @Override
    public void onClick(View v) {
        Log.i("AddFriendsFragment","onClick()");
        if (v.getId() == R.id.add_friends) {
            showNoticeDialogue();
        } else if (v.getId() == R.id.enter_expenses) {
            showCustomDialogurForWhoPaid();
        } else if (v.getId() == R.id.compute) {
            printHash(expenditureMap);
            Navigator.toCompute(getActivity(), expenditureMap);
        }
    }

    private void showFriendName(ArrayList<String> friends) {
        Log.i("AddFriendsFragment","showFriendName()");
        friendsNameContainer.removeAllViews();
        for (int i = 0; i < friends.size(); i++) {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.show_friends_name, null);
            ((TextView) view.findViewById(R.id.first_name)).setText(friends.get(i));
            ((TextView) view.findViewById(R.id.names_first_letter)).setText(friends.get(i).substring(0, 1).toUpperCase());
            friendsNameContainer.addView(view);
        }
    }

    private void divideAmongFriends(int amount, ArrayList<String> friends) {
        Log.i("AddFriendsFragment","divideAmongFriends()");
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
        Log.i("AddFriendsFragment","calculate()");
        amount = Integer.parseInt(transactionDetails.get("amount"));
        boolean[] selected = MultiSelectionSpinner.mSelection;
        paidForWhom.clear();
        for (int i = 0; i < selected.length; i++) {
            if (selected[i] == true) {
                paidForWhom.add(friends.get(i));
            }
        }
        int previousAmount;
        if (expenditureMap.get(transactionDetails.get("whoPaid")) == null) {
            previousAmount = 0;

        } else {
            previousAmount = expenditureMap.get(transactionDetails.get("whoPaid"));
        }
        expenditureMap.put(transactionDetails.get("whoPaid"), amount + previousAmount);

        divideAmongFriends(amount, paidForWhom);
        Log.i("Notes", "after inputting values");
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
        showFriendName(friends);
        if (friends.size() > 1) {
            enterExpenses.setVisibility(View.VISIBLE);
            compute.setVisibility(View.VISIBLE);
        }
//        friendsAdapter.notifyDataSetChanged();
    }
}
