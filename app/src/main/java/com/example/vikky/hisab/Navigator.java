package com.example.vikky.hisab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by vikky on 6/29/15.
 */
public class Navigator {
    public static void toAddFriends(Context context, int placeId) {
        Intent i = new Intent(context, AddFriendsActivity.class);
        Log.i("while sending", String.valueOf(placeId));
        i.putExtra("placeId", placeId);
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

    public static void toGuideScreens(Context context) {
        Intent i = new Intent(context, GuideActivity.class);
        context.startActivity(i);
    }
}
