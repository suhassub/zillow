package com.suhas.us.csci571homework9;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;


import org.json.JSONException;
import org.json.JSONObject;


public class zillowresult extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zillowresult);
        ActionBar.Tab tab1;
        ActionBar.Tab tab2;
        try
        {
            JSONObject jsonObject = new JSONObject(getIntent().getStringExtra("zillowjson"));
            Bundle args = new Bundle();
            args.putString("zillowjson",jsonObject.toString());
            JSONObject zillowjson=new JSONObject();
           /* String firstchart=zillowjson.get("url").toString();
            String secondchart=zillowjson.get("url2").toString();
            String thirdchart=zillowjson.get("url3").toString();*/
            Fragment InfoFragment = new TableInfo();
            Fragment ChartFragment = new ChartsInfo();
            ActionBar actionBar = getActionBar();
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            InfoFragment.setArguments(args);
            ChartFragment.setArguments(args);
            tab1 = actionBar.newTab().setText("BASIC INFO");
            tab2 = actionBar.newTab().setText("HISTORICAL ZESTIMATES");
            tab1.setTabListener(new ListenerToTab(InfoFragment));
            tab2.setTabListener(new ListenerToTab(ChartFragment));
            actionBar.addTab(tab1);
            actionBar.addTab(tab2);

        }

        catch (JSONException e) {
            e.printStackTrace();
        }

    }



}
