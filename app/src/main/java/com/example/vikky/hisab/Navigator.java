package com.example.vikky.hisab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by vikky on 6/29/15.
 */
public class Navigator {
    public static void toAddFriends(Context context) {
        Intent i = new Intent(context, AddFriendsActivity.class);
        context.startActivity(i);
    }

    public static void toCompute(Activity context, Map<String, Integer> expenditureMap) {
        Intent i = new Intent(context, ComputeActivity.class);
//        HashMap m = new HashMap();
//        i.putExtra("transactionDetails", details);
        i.putExtra("expenditureMap", (HashMap) expenditureMap);
        context.startActivity(i);
    }

    public static void toMainScreen(Context context) {
        Intent i = new Intent(context, MainScreenActivity.class);
        context.startActivity(i);
    }

}
