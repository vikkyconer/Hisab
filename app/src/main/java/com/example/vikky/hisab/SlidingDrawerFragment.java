package com.example.vikky.hisab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by vikky on 7/11/15.
 */
public class SlidingDrawerFragment extends Fragment implements SlidingDrawerView, View.OnClickListener {
    LinearLayout buttonGames, userInfo, logout, profile;
    //    ListView listDrawerMenu;
//    DrawerLayout drawerLayout;
    TextView userName;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.drawer_items, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("Notes", "nav drawer fragment called");
        intializeViews(view);
        defaultConfiguration();
        setEventsForViews();
    }

    private void defaultConfiguration() {
//        userName.setText(AppSettings.getValue(getActivity(), AppSettings.PREF_USER_FIRST_NAME, "") + " " + AppSettings.getValue(getActivity(), AppSettings.PREF_USER_LAST_NAME, ""));


    }

    private void setEventsForViews() {
        buttonGames.setOnClickListener(this);
        userInfo.setOnClickListener(this);
        logout.setOnClickListener(this);
        profile.setOnClickListener(this);
    }

    private void intializeViews(View view) {
//        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        buttonGames = (LinearLayout) view.findViewById(R.id.button_games);
        userName = (TextView) view.findViewById(R.id.set_user_name);
        userInfo = (LinearLayout) view.findViewById(R.id.user_info);
        logout = (LinearLayout) view.findViewById(R.id.button_log_out);
        profile = (LinearLayout) view.findViewById(R.id.user_info);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
          /*  case R.id.button_games:
                Navigator.searchGameActivity(getActivity());
                break;
            case R.id.button_log_out:
                clearUserData();
                break;
            case R.id.user_info:
                Navigator.toUserProfile(getActivity());
                break;*/
        }
    }

    private void clearUserData() {
//        AppSettings.clearAllPrefs(getActivity());
//        Navigator.splashActivity(getActivity());
        getActivity().finish();
    }

 /*   @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listDrawerMenu.setItemChecked(position, true);
        listDrawerMenu.setSelection(position);
    }
*/
}
