package com.ideabank.vikky.hisab.AddFriend;

import com.ideabank.vikky.hisab.Models.Friend;
import com.ideabank.vikky.hisab.Models.TransactionDetails;

import rx.Observable;

/**
 * Created by vikky on 7/1/15.
 */
public interface AddFriendsView {
    Observable<Friend> enterFriend();

    void showFriend(Friend friend);

    void initializeSavedData();

    Observable<TransactionDetails> enterExpense();

    void showExpenses(TransactionDetails expense);

    Observable<Friend> friendSelectedWhoPaid();

    void showDialogue(Friend friend);
}
