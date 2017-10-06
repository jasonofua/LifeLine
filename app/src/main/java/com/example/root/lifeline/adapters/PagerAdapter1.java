package com.example.root.lifeline.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.root.lifeline.Fragment.ChatDoc;
import com.example.root.lifeline.Fragment.FollowersDoc;
import com.example.root.lifeline.Fragment.InfoDoc;
import com.example.root.lifeline.Fragment.Post;

public class PagerAdapter1 extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PagerAdapter1(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Post tab1 = new Post();
                return tab1;
            case 1:
                InfoDoc tab2 = new InfoDoc();
                return tab2;
            case 2:
                FollowersDoc tab3 = new FollowersDoc();
                return tab3;
            case 3:
                ChatDoc tab4 = new ChatDoc();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
