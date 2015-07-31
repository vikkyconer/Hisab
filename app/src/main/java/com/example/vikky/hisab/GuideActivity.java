package com.example.vikky.hisab;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;


public class GuideActivity extends FragmentActivity {
    ImageView circle1, circle2, circle3, circle4, circle5, circle6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new TabsPagerAdapter(getSupportFragmentManager()));
        circle1 = (ImageView) findViewById(R.id.first_circle);
        circle2 = (ImageView) findViewById(R.id.second_circle);
        circle3 = (ImageView) findViewById(R.id.third_circle);
        circle4 = (ImageView) findViewById(R.id.fourth_circle);
        circle5 = (ImageView) findViewById(R.id.fifth_circle);
        circle6 = (ImageView) findViewById(R.id.sixth_circle);

    }

    public void setCircle(int check) {
        Log.i("check at i", String.valueOf(check));
        switch (check) {
            case 0:
                circle1.setImageResource(R.drawable.filled_circle_for_view_pager);
                circle2.setImageResource(R.drawable.empty_circle_for_view_pager);
                circle3.setImageResource(R.drawable.empty_circle_for_view_pager);
                circle4.setImageResource(R.drawable.empty_circle_for_view_pager);
                circle5.setImageResource(R.drawable.empty_circle_for_view_pager);
                circle6.setImageResource(R.drawable.empty_circle_for_view_pager);
                break;
            case 1:
                circle1.setImageResource(R.drawable.empty_circle_for_view_pager);
                circle2.setImageResource(R.drawable.filled_circle_for_view_pager);
                circle3.setImageResource(R.drawable.empty_circle_for_view_pager);
                circle4.setImageResource(R.drawable.empty_circle_for_view_pager);
                circle5.setImageResource(R.drawable.empty_circle_for_view_pager);
                circle6.setImageResource(R.drawable.empty_circle_for_view_pager);
                break;
            case 2:
                circle1.setImageResource(R.drawable.empty_circle_for_view_pager);
                circle2.setImageResource(R.drawable.empty_circle_for_view_pager);
                circle3.setImageResource(R.drawable.filled_circle_for_view_pager);
                circle4.setImageResource(R.drawable.empty_circle_for_view_pager);
                circle5.setImageResource(R.drawable.empty_circle_for_view_pager);
                circle6.setImageResource(R.drawable.empty_circle_for_view_pager);
                break;
            case 3:
                circle1.setImageResource(R.drawable.empty_circle_for_view_pager);
                circle2.setImageResource(R.drawable.empty_circle_for_view_pager);
                circle3.setImageResource(R.drawable.empty_circle_for_view_pager);
                circle4.setImageResource(R.drawable.filled_circle_for_view_pager);
                circle5.setImageResource(R.drawable.empty_circle_for_view_pager);
                circle6.setImageResource(R.drawable.empty_circle_for_view_pager);
                break;
            case 4:
                circle1.setImageResource(R.drawable.empty_circle_for_view_pager);
                circle2.setImageResource(R.drawable.empty_circle_for_view_pager);
                circle3.setImageResource(R.drawable.empty_circle_for_view_pager);
                circle4.setImageResource(R.drawable.empty_circle_for_view_pager);
                circle5.setImageResource(R.drawable.filled_circle_for_view_pager);
                circle6.setImageResource(R.drawable.empty_circle_for_view_pager);
                break;
            case 5:
                circle1.setImageResource(R.drawable.empty_circle_for_view_pager);
                circle2.setImageResource(R.drawable.empty_circle_for_view_pager);
                circle3.setImageResource(R.drawable.empty_circle_for_view_pager);
                circle4.setImageResource(R.drawable.empty_circle_for_view_pager);
                circle5.setImageResource(R.drawable.empty_circle_for_view_pager);
                circle6.setImageResource(R.drawable.filled_circle_for_view_pager);
                break;
        }
    }
}