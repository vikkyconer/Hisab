package com.example.vikky.hisab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by vikky on 7/19/15.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:

                return new SignInGuideFragment();
            case 1:
                return new AddPlaceGuideFragment();
            case 2:
                return new AddFriendsGuideFragment();
            case 3:
                return new EnterExpensesGuideFragment();
            case 4:
                return new ComputeExpensesGuideFragment();
            case 5:
                return new LetsGoFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 6;
    }
}
