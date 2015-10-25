package com.ideabank.vikky.hisab;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.List;


public class MainScreenActivity extends ActionBarActivity {

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Log.i("MainScreenActivity", "in onCreate");
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        setTitle("Home");
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.blue1));


        new MainScreenPresenter(mainScreenView(), mainScreenModel());
//        new SlidingDrawerPresenter(slidingDrawerView(), slidingDrawerModel());

    }

    private MainScreenModel mainScreenModel() {
        return null;
    }

    private MainScreenView mainScreenView() {
        return (MainScreenFragment) getSupportFragmentManager().findFragmentById(R.id.main_screen_fragment);
    }

    @Override
    public void onBackPressed() {
        int userLoginCount = Integer.parseInt(AppSettings.getValue(this, AppSettings.PREF_IS_USER_LOGIN_COUNT, "0"));

        if (userLoginCount%5 == 0) {
            showRatingDialogue();
        }
        else if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void showRatingDialogue() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please tell your experiance!!!");

        alertDialogBuilder.setPositiveButton("Rate Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                openAppRating(MainScreenActivity.this);
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("Later",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void openAppRating(Context context) {
        Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName()));
        boolean marketFound = false;

        // find all applications able to handle our rateIntent
        final List<ResolveInfo> otherApps = context.getPackageManager().queryIntentActivities(rateIntent, 0);
        for (ResolveInfo otherApp: otherApps) {
            // look for Google Play application
            if (otherApp.activityInfo.applicationInfo.packageName.equals("com.android.vending")) {

                ActivityInfo otherAppActivity = otherApp.activityInfo;
                ComponentName componentName = new ComponentName(
                        otherAppActivity.applicationInfo.packageName,
                        otherAppActivity.name
                );
                rateIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                rateIntent.setComponent(componentName);
                context.startActivity(rateIntent);
                marketFound = true;
                break;

            }
        }

        // if GP not present on device, open web browser
        if (!marketFound) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+context.getPackageName()));
            context.startActivity(webIntent);
        }
    }
}
