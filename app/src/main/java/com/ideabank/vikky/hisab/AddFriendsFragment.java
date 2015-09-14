package com.ideabank.vikky.hisab;

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
    private ArrayList<String> friends, paidForWhom;
    private ArrayList<TransactionDetails> detailsList;
    private int amount, friendPosition;
    private Map<String, String> friendData;
    private LinearLayout friendsNameContainer;
    private Map<String, Integer> expenditureMap;
    private TransactionDetails transactionDetails;
    private EditText enterFriendName;
    private TransactionDetailsRVAdapter detailsAdapter;
    private RecyclerView detailsRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private BehaviorSubject<Friend> friendAdded = BehaviorSubject.create();
    private DatabaseHelper db;
    private ArrayList<Long> friendIds;


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

    private void setEventsForViews() {
        addFriends.setOnClickListener(this);
//        enterExpenses.setOnClickListener(this);
        compute.setOnClickListener(this);
        enterFriendName.setOnClickListener(this);
    }

    private void defaultConfiguration() {
        detailsRecyclerView.setLayoutManager(linearLayoutManager);
        detailsRecyclerView.setAdapter(detailsAdapter);
    }

    private void initializeViews(View view) {
        db = new DatabaseHelper(getActivity());
        addFriends = (TextView) view.findViewById(R.id.add_friends);
//        enterExpenses = (Button) view.findViewById(R.id.enter_expenses);
        this.transactionDetails = new TransactionDetails();
        friendsNameContainer = (LinearLayout) view.findViewById(R.id.friends_name_container);
        detailsList = new ArrayList<>();
        paidForWhom = new ArrayList<>();
        expenditureMap = new HashMap<>();
        compute = (Button) view.findViewById(R.id.compute);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        detailsRecyclerView = (RecyclerView) view.findViewById(R.id.details);
        friends = new ArrayList<>();
        detailsAdapter = new TransactionDetailsRVAdapter(detailsList, getActivity(), paidForWhom);
        enterFriendName = (EditText) view.findViewById(R.id.enter_friend_name);
        friendData = new HashMap<>();
        friendIds = new ArrayList<>();
        tapToAdd = (TextView) view.findViewById(R.id.tap);
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

    private boolean isValid() {
        int friendNameLength = enterFriendName.getText().length();
        if (friendNameLength == 0) {
            return false;
        }
        return isSameFriendNameEntered(enterFriendName.getText().toString());
    }

    private boolean isSameFriendNameEntered(String friendName) {
        for (int i = 0; i < friends.size(); i++) {
            Log.i("Notes", friendName);
            if (friendName.equals(friends.get(i))) {
                Toast.makeText(getActivity(), "Name already exist", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void showFriendName(ArrayList<String> friends) {
        Log.i("AddFriendsFragment", "showFriendName()");
//        friendsAdapter.notifyDataSetChanged();
        friendsNameContainer.removeAllViews();
        for (int i = 0; i < friends.size(); i++) {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.show_friends_name, null);
            String finalName;
            if (friends.get(i).length() > 10) {
                finalName = friends.get(i).substring(0, 10);
                finalName += "...";
            } else {
                finalName = friends.get(i).substring(0);
            }
            ((TextView) view.findViewById(R.id.first_name)).setText(finalName);
            RemoveFriend(view, i);
            ((TextView) view.findViewById(R.id.names_first_letter)).setText(friends.get(i).substring(0, 1));
            friendsNameContainer.addView(view);
        }
    }

    private void RemoveFriend(View view, int i) {
        friendPosition = i;
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);

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
        dialogueBoxForExpenses.inputPlaceName(friends).subscribe(transactionDetails -> mapTransactionDetails(transactionDetails));
        dialogueBoxForExpenses.show(getFragmentManager(), "who paid");

    }

    private void mapTransactionDetails(Map<String, String> transactionDetails) {
        this.transactionDetails.setAmount(Integer.valueOf(transactionDetails.get("amount")));
        this.transactionDetails.setWhoPaid(transactionDetails.get("whoPaid"));
        this.transactionDetails.setDescription(transactionDetails.get("description"));
        this.transactionDetails.setPlaceId(((AddFriendsActivity) getActivity()).getPlaceId());

        this.transactionDetails.setForWhom(calculate(transactionDetails));
        detailsList.add(this.transactionDetails);
        Log.i("Notes","before notifyDataSetChanged");
        detailsAdapter.notifyDataSetChanged();


//        db.createExpenses(transactionDetails);

    }

    private ArrayList<String> calculate(Map<String, String> transactionDetails) {
        Log.i("AddFriendsFragment", "calculate()");
        amount = Integer.parseInt(transactionDetails.get("amount"));
        boolean[] selected = MultiSelectionSpinner.mSelection;
        paidForWhom.clear();
        for (int i = 0; i < selected.length; i++) {
            if (selected[i] == true) {
                paidForWhom.add(friends.get(i));
                Log.i("Times", "adding expense");
//                db.createExpenses(((AddFriendsActivity) getActivity()).getPlaceId(), db.getFriendId(transactionDetails.get("whoPaid"))
//                        , friendIds.get(i), Integer.parseInt(transactionDetails.get("amount")), transactionDetails.get("description"));
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
        if (expenditureMap.size() > 0) {
            tapToAdd.setVisibility(View.INVISIBLE);
        }
        return paidForWhom;
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
    public Observable<Friend> enterFriend() {
        return friendAdded.asObservable();
    }

    @Override
    public void showFriend(Friend friend) {
        friends.add(friend.getName());
        showFriendName(friends);
        db.updatePlace(((AddFriendsActivity) getActivity()).getPlaceId());

//        if (friends.size() > 1) {
//            enterExpenses.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void initialize() {
        // Getting friends under single Place
        Log.d("ToDo", "Get todos under single Tag name");

        List<Friend> friendList = db.getAllFriendsByPlace(String.valueOf(((AddFriendsActivity) getActivity()).getPlaceId()));
        for (Friend friend : friendList) {
            Log.d("ToDo Watchlist", friend.getName());
            showFriend(friend);
        }
        List<TransactionDetails> transactionDetailsList = db.getAllExpensesByPlace(String.valueOf(((AddFriendsActivity) getActivity()).getPlaceId()));
        detailsList.clear();
        for (TransactionDetails details : transactionDetailsList) {
            Log.i("expenses", details.getDescription());
            showExpenses(details);
        }
//        detailsAdapter.notifyDataSetChanged();
    }

    private void showExpenses(TransactionDetails details) {
        detailsList.add(details);
    }

    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(getActivity(), friends.get(friendPosition), Toast.LENGTH_SHORT).show();
        return true;
    }
}