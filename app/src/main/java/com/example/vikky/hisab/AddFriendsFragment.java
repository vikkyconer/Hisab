package com.example.vikky.hisab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
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
    Bundle details;
    int totalAmount = 0;
    //    TransactionDetails details;
    ArrayAdapter<String> adapter;
    ListView friendsListView;
    DialogueBoxForExpenses inputWhoPaid;
    BehaviorSubject<Map<String, String>> friendAdded = BehaviorSubject.create();

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

    }

    private void initializeViews(View view) {
        addFriends = (Button) view.findViewById(R.id.add_friends);
        friendsListView = (ListView) view.findViewById(R.id.list_friends);
        enterExpenses = (Button) view.findViewById(R.id.enter_expenses);
        compute = (Button) view.findViewById(R.id.compute);
//        inputWhoPaid = new DialogueBoxForExpenses(getActivity(), friends);
        friend = new Friend();
        friends = new ArrayList<>();
//        friends.add("Vikas");
//        dataAdapter = new ArrayAdapter<String>(dialogView.getContext(), android.R.layout.simple_spinner_item, friends);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        inputWhoPaid.setAdapter(dataAdapter);
//        Log.i("friendsListView", String.valueOf(friendsListView));
//        Log.i("inputWhoPaidAdapter", String.valueOf(inputWhoPaid));
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, friends);
        friendsListView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_friends) {
            showNoticeDialogue();
        } else if (v.getId() == R.id.enter_expenses) {
            showCustomDialogurForWhoPaid();

        } else if (v.getId() == R.id.compute) {
            Navigator.toCompute(getActivity(), details);
        }

    }

    private void showCustomDialogurForWhoPaid() {
        DialogueBoxForExpenses dialogueBoxForExpenses = DialogueBoxForExpenses.newInstance();
        dialogueBoxForExpenses.inputPlaceName(friends).subscribe(transactionDetails -> mapTransactionDetails(transactionDetails));
        dialogueBoxForExpenses.show(getFragmentManager(), "who paid");

    }

    private void mapTransactionDetails(Map<String, String> transactionDetails) {
//        details = new TransactionDetails();
        details = new Bundle();
        details.putString("whoPaid", transactionDetails.get("whoPaid"));
        details.putString("paidForWhom", transactionDetails.get("paidForWhom"));
        details.putString("amount", transactionDetails.get("amount"));
        totalAmount = totalAmount + Integer.parseInt(transactionDetails.get("amount"));
//        details.putInt("totalAmount", totalAmount);
        details.putString("description", transactionDetails.get("description"));

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
        adapter.notifyDataSetChanged();
    }
}
