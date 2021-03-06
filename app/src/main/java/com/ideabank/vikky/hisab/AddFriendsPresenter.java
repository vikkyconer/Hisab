package com.ideabank.vikky.hisab;

import android.util.Log;

/**
 * Created by vikky on 7/1/15.
 */
public class AddFriendsPresenter {
    public AddFriendsPresenter(AddFriendsView view, AddFriendsModel model) {
        Log.i("AddFriendsPresenter", "constructor");
        view.initializeSavedData();
        view.enterFriend().subscribe(friend -> view.showFriend(friend));
        view.enterExpense().subscribe(expense -> view.showExpenses(expense));
        view.friendSelectedWhoPaid().subscribe(friend -> view.showDialogue(friend));
    }
}
