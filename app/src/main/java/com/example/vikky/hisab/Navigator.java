package com.example.vikky.hisab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by vikky on 6/29/15.
 */
public class Navigator {
    public static void toAddFriends(Context context) {
        Intent i = new Intent(context, AddFriendsActivity.class);
        context.startActivity(i);
    }

    public static void toCompute(Activity context, Bundle details) {
        Intent i = new Intent(context, ComputeActivity.class);
//        i.putExtra("transactionDetails", details);
        i.putExtras(details);
        context.startActivity(i);
    }
}
