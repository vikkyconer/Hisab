package com.example.vikky.hisab;

import java.util.Map;

import rx.Observable;

/**
 * Created by vikky on 7/1/15.
 */
public interface AddFriendsView {
    Observable<Map<String, String>> enterFriend();

    void showFriend(Map<String, String> friend);
}
