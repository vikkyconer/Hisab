package com.ideabank.vikky.hisab;

import rx.Observable;

/**
 * Created by vikky on 7/1/15.
 */
public interface AddFriendsView {
    Observable<Friend> enterFriend();

    void showFriend(Friend friend);

    void initialize();
}
