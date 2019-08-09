package org.me.gcu.coursework.weatherapplication.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.me.gcu.coursework.weatherapplication.Fragments.Later;
import org.me.gcu.coursework.weatherapplication.Fragments.Today;
import org.me.gcu.coursework.weatherapplication.Fragments.Tomorrow;


public class AdapterPage extends FragmentPagerAdapter {

    int numberOfTabs;
    public AdapterPage(FragmentManager frag, int numOfTabs){
        super(frag);
        this.numberOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0:
                return new Today();
            case 1:
                return new Tomorrow();
            case 2:
                return new Later();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
