package org.me.gcu.coursework.weatherapplication.View;

import android.graphics.drawable.AnimationDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.me.gcu.coursework.weatherapplication.Adapter.AdapterPage;
import org.me.gcu.coursework.weatherapplication.Model.City;
import org.me.gcu.coursework.weatherapplication.R;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class DetailActivity extends AppCompatActivity {

    public static final String POSITION = "org.me.gcu.coursework.weatherapplication.FORECAST_POSITION";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("HH:mm E, dd " +
            "MMM", Locale.US);
    private String cityName;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_detail);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        //Initialize the views
        TextView cityName = (TextView)findViewById(R.id.nameDetail);
        //Set the text from the intent extra
        cityName.setText(getIntent().getStringExtra(City.NAME_KEY));


        //Create an instance of the tab layout from the view
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);

        //Set text for each tab
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label3));

        //Set the tabs to fill the entire layout
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Use the pagerAdapter to manage page views in fragments
        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        AdapterPage adapter = new AdapterPage(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        //A listener for clicks
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}
