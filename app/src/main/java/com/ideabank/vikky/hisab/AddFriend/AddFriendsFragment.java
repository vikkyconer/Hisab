package com.ideabank.vikky.hisab.AddFriend;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ideabank.vikky.hisab.DatabaseHelper;
import com.ideabank.vikky.hisab.DialogueBoxForExpenses;
import com.ideabank.vikky.hisab.Models.Friend;
import com.ideabank.vikky.hisab.MultiSelectionSpinner;
import com.ideabank.vikky.hisab.Navigator;
import com.ideabank.vikky.hisab.R;
import com.ideabank.vikky.hisab.Models.TransactionDetails;
import com.ideabank.vikky.hisab.Adapters.TransactionDetailsRVAdapter;
import com.ideabank.vikky.hisab.Validations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by vikky on 7/1/15.
 */
public class AddFriendsFragment extends Fragment implements AddFriendsView, View.OnClickListener, View.OnLongClickListener {
    private TextView addFriends, tapToAdd;
    private Button compute;
    private ArrayList<String> friendsList, paidForWhomList;
    private ArrayList<TransactionDetails> expenseList;
    private int amount, friendPosition;
    private Map<String, String> friendData;
    private LinearLayout friendsNameContainer;
    private Map<String, Integer> expenditureMap;
    private EditText enterFriendName;
    private TransactionDetailsRVAdapter expenseAdapter;
    private RecyclerView detailsRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private BehaviorSubject<Friend> friendAdded = BehaviorSubject.create();
    private BehaviorSubject<TransactionDetails> expenseAdded = BehaviorSubject.create();
    private BehaviorSubject<Friend> friendSelected = BehaviorSubject.create();
    private DatabaseHelper db;
    private ArrayList<Long> friendIds;
    Friend friend;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("AddFriendsFragment", "onCreateView()");
        setRetainInstance(true);
        return inflater.inflate(R.layout.add_friends_fragment, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i("AddFriendsFragment", "onViewCreated()");
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
        defaultConfiguration();
        setEventsForViews();
    }

    @Override
    public Observable<Friend> enterFriend() {
        return friendAdded.asObservable();
    }

    @Override
    public Observable<TransactionDetails> enterExpense() {
        return expenseAdded.asObservable();
    }

    @Override
    public Observable<Friend> friendSelectedWhoPaid() {
        return friendSelected.asObservable();
    }

    private void initializeViews(View view) {
        db = new DatabaseHelper(getActivity());
        addFriends = (TextView) view.findViewById(R.id.add_friends);
//        enterExpenses = (Button) view.findViewById(R.id.enter_expenses);
        friendsNameContainer = (LinearLayout) view.findViewById(R.id.friends_name_container);
        expenseList = new ArrayList<>();
        paidForWhomList = new ArrayList<>();
        expenditureMap = new HashMap<>();
        compute = (Button) view.findViewById(R.id.compute);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        detailsRecyclerView = (RecyclerView) view.findViewById(R.id.details);
        friendsList = new ArrayList<>();
        expenseAdapter = new TransactionDetailsRVAdapter(expenseList, getActivity(), paidForWhomList);
        enterFriendName = (EditText) view.findViewById(R.id.enter_friend_name);
        friendData = new HashMap<>();
        friendIds = new ArrayList<>();
        tapToAdd = (TextView) view.findViewById(R.id.tap);
    }

    private void defaultConfiguration() {
        detailsRecyclerView.setLayoutManager(linearLayoutManager);
        detailsRecyclerView.setAdapter(expenseAdapter);
    }

    private void setEventsForViews() {
        addFriends.setOnClickListener(this);
//        enterExpenses.setOnClickListener(this);
        compute.setOnClickListener(this);
        enterFriendName.setOnClickListener(this);
    }

    @Override
    public void initializeSavedData() {
        // Getting friends under single Place
        Log.d("ToDo", "Get todos under single Tag name");

        List<Friend> friendList = db.getAllFriendsByPlace(String.valueOf(((AddFriendsActivity) getActivity()).getPlaceId()));
        for (Friend friend : friendList) {
            Log.d("ToDo Watchlist", friend.getName());
            showFriend(friend);
        }
        List<TransactionDetails> transactionDetailsList = db.getAllExpensesByPlace(String.valueOf(((AddFriendsActivity) getActivity()).getPlaceId()));
        expenseList.clear();
        for (TransactionDetails details : transactionDetailsList) {
            Log.i("expenses", details.getDescription());
            Log.i("for whom paid",details.getForWhomIdStrore());
            showExpenses(details);
        }
    }

    @Override
    public void onClick(View v) {
        Log.i("AddFriendsFragment", "onClick()");

        switch (v.getId()) {
            case R.id.add_friends:
                if (isValid()) {
                    friendData.put("friendName", String.valueOf(enterFriendName.getText()));
                    friendEntered(friendData);
                } else {
                    Toast.makeText(getActivity(), "Enter Friend Name", Toast.LENGTH_LONG).show();
                }
                enterFriendName.setText("");
                break;

            case R.id.compute:
                if (expenditureMap.size() == 0) {
                    Toast.makeText(getActivity(), "Please add expenses frist", Toast.LENGTH_SHORT).show();
                } else {
                    Navigator.toCompute(getActivity(), expenditureMap);
                }
                break;

            case R.id.enter_friend_name:
                enterFriendName.setText("");
                break;

            default:
                showCustomDialogurForWhoPaid();

        }

    }

    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(getActivity(), "Enjoying.. :)", Toast.LENGTH_SHORT).show();
        return true;
    }

    private boolean isValid() {
        Validations validations = new Validations(friendsList,getActivity());

        Boolean c = validations.nameLength(enterFriendName.getText().toString());
        if (c == true)
            return validations.dublicateName(enterFriendName.getText().toString());
        return false;
    }

    private void divideAmongFriends(int amount, ArrayList<String> friends) {
        Log.i("AddFriendsFragment", "divideAmongFriends()");
        amount = amount / friends.size();           //catch divide by zero exception
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
        dialogueBoxForExpenses.inputPlaceName(friendsList).subscribe(transactionDetails -> setTransactionDetails(transactionDetails));

        dialogueBoxForExpenses.show(getFragmentManager(), "who paid");
    }

    private void setTransactionDetails(Map<String, String> transactionDetails) {

        TransactionDetails transactionDetails1 = new TransactionDetails();
        transactionDetails1.setAmount(Integer.valueOf(transactionDetails.get("amount")));
        transactionDetails1.setWhoPaid(transactionDetails.get("whoPaid"));
        transactionDetails1.setDescription(transactionDetails.get("description"));
        transactionDetails1.setPlaceId(((AddFriendsActivity) getActivity()).getPlaceId());
        transactionDetails1.setForWhom(calculate(transactionDetails));

        expenseAdded.onNext(transactionDetails1);
    }

    private ArrayList<String> calculate(Map<String, String> transactionDetails) {
        Log.i("AddFriendsFragment", "calculate()");
        amount = Integer.parseInt(transactionDetails.get("amount"));
        boolean[] selected = MultiSelectionSpinner.mSelection;
        paidForWhomList.clear();
        String friendIdsToStore =  "";
        for (int i = 0; i < selected.length; i++) {
            if (selected[i] == true) {
                paidForWhomList.add(friendsList.get(i));
                Log.i("Times", "adding expense");

                friendIdsToStore =  friendIdsToStore + "," + friendIds.get(i);

                // Remove this database entry from here and move it in setTransactionDetails before calling
                // calculate function. But before that learn how to point for whom paid list in friendsIds list.

            }
        }

        db.createExpenses(((AddFriendsActivity) getActivity()).getPlaceId(), db.getFriendId(transactionDetails.get("whoPaid"))
                , friendIdsToStore, Integer.parseInt(transactionDetails.get("amount")), transactionDetails.get("description"));


        int previousAmount;
        if (expenditureMap.get(transactionDetails.get("whoPaid")) == null) {
            previousAmount = 0;

        } else {
            previousAmount = expenditureMap.get(transactionDetails.get("whoPaid"));
        }
        expenditureMap.put(transactionDetails.get("whoPaid"), amount + previousAmount);

        divideAmongFriends(amount, paidForWhomList);
        Log.i("Notes", "after inputting values");
        if (expenditureMap.size() > 0) {
            tapToAdd.setVisibility(View.INVISIBLE);
        }
        return paidForWhomList;
    }

    private void friendEntered(Map<String, String> friend) {
        Friend friend1 = new Friend(friend.get("friendName"));
        long friend_id = db.createFriend(friend1, ((AddFriendsActivity) getActivity()).getPlaceId());
        friendIds.add(friend_id);
        Log.i("friendId", String.valueOf(db.getFriendId(friend.get("friendName"))));
//        db.updatePlace(((AddFriendsActivity) getActivity()).getPlaceId());
        friendAdded.onNext(friend1);

    }

    @Override
    public void showFriend(Friend friend) {
        friendsList.add(friend.getName());
        showFriendName(friendsList);
        db.updatePlace(((AddFriendsActivity) getActivity()).getPlaceId());
    }

    @Override
    public void showExpenses(TransactionDetails expense) {
        expenseList.add(expense);
        expenseAdapter.notifyDataSetChanged();
    }

    @Override
    public void showDialogue(Friend friend) {
        DialogueBoxForExpenses dialogueBoxForExpenses = DialogueBoxForExpenses.newInstance();
        dialogueBoxForExpenses.inputPlaceName(friendsList).subscribe(transactionDetails -> setTransactionDetails(transactionDetails));

        dialogueBoxForExpenses.show(getFragmentManager(), "who paid");
    }

    private void showFriendName(ArrayList<String> friends) {
        Log.i("AddFriendsFragment", "showFriendName()");
//        friendsAdapter.notifyDataSetChanged();
        friendsNameContainer.removeAllViews();
        String finalName;
        for (int i = 0; i < friends.size(); i++) {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.show_friends_name, null);

            if (friends.get(i).length() > 10) {
                finalName = friends.get(i).substring(0, 10);
                finalName += "...";
            } else {
                finalName = friends.get(i).substring(0);
            }
            ((TextView) view.findViewById(R.id.first_name)).setText(finalName);
            RemoveFriend(view);
            ((TextView) view.findViewById(R.id.names_first_letter)).setText(friends.get(i).substring(0, 1));
            friendsNameContainer.addView(view);
//            final String finalName1 = finalName;
//            friendsNameContainer.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(inflater.getContext(), finalName1 + "final wala",Toast.LENGTH_LONG).show();
//                }
//            });
        }
    }

    private void RemoveFriend(View view) {
//        int id = view.getId();
//        friend = db.getFriend(id);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);

    }

}
