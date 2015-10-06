package com.suhas.us.csci571homework9;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * Created by suhas on 11/24/2014.
 */
public class ListenerToTab implements ActionBar.TabListener {
    Fragment classFragment;
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.replace(R.id.fragment_container,classFragment);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.remove(classFragment);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    public ListenerToTab(Fragment pf)
    {
        this.classFragment=pf;
    }
}
