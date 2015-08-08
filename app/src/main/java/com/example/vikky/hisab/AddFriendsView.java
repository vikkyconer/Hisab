package com.example.vikky.hisab;

import java.util.Map;

import rx.Observable;

/**
 * Created by vikky on 7/1/15.
 */
public interface AddFriendsView {
    Observable<Friend> enterFriend();

    void showFriend(Friend friend);

    void initialize();
}
