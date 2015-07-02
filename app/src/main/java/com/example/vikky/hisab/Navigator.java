package com.example.vikky.hisab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by vikky on 6/29/15.
 */
public class Navigator {
    public static void toAddFriends(Activity context) {
        Intent i = new Intent(context, AddFriendsActivity.class);
        context.startActivity(i);
    }
}
