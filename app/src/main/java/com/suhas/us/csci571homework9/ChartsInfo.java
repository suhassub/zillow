package com.suhas.us.csci571homework9;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public class ChartsInfo extends Fragment {
    TextView hsttext;
    TextView addrtext;
    ImageView chart;
    Button next;
    Button prev;
    String fullAdress;
    int count=0;
    boolean alwaysfalse=true;
    Bitmap firstbitmap,secondbitmap,thirdbitmap;
    TextView footer1;
    TextView footer2;
    TextView footer3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle zillowBundle=getArguments();

        final String json=zillowBundle.getString("zillowjson");
        View view = inflater.inflate(R.layout.activity_charts_info, container, false);
        hsttext= (TextView) view.findViewById(R.id.hstZestimate);
        addrtext= (TextView) view.findViewById(R.id.address);
        chart= (ImageView) view.findViewById(R.id.imageView3);
        next=(Button)view.findViewById(R.id.next);
        prev=(Button)view.findViewById(R.id.prev);
        footer2 = (TextView) view.findViewById(R.id.footer2);
        footer3 = (TextView) view.findViewById(R.id.footer3);
        JSONObject zillowjson;
        try {
            zillowjson=new JSONObject(json);
            fullAdress = zillowjson.get("Street").toString();
            fullAdress=fullAdress.concat(" ");
            fullAdress=fullAdress.concat(zillowjson.get("city").toString());
            fullAdress=fullAdress.concat(" ");
            fullAdress=fullAdress.concat(zillowjson.get("state").toString());
            fullAdress=fullAdress.concat(" ");
            fullAdress=fullAdress.concat(zillowjson.get("zipcode").toString());

            if(false==alwaysfalse) {
                fullAdress=zillowjson.get("Street").toString();
                fullAdress=fullAdress.concat(" ");
                fullAdress=fullAdress.concat(zillowjson.get("city").toString());
                fullAdress=fullAdress.concat(" ");
                fullAdress=fullAdress.concat(zillowjson.get("state").toString());
                fullAdress=fullAdress.concat(" ");
                fullAdress=fullAdress.concat(zillowjson.get("zipcode").toString());

            }

            footer1= (TextView) view.findViewById(R.id.footer1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addrtext.setText(fullAdress);
                count++;
                if(count>2) {
                    count = 0;
                }
                if(count==0)
                {
                    hsttext.setText(R.string.hstone);
                    chart.setImageBitmap(Bitmap.createScaledBitmap(firstbitmap, 1200, 830, false));
                }
                else if(count==1)
                {
                    hsttext.setText(R.string.hstfive);
                    chart.setImageBitmap(Bitmap.createScaledBitmap(secondbitmap,1200,830,false));
                }
                else if(count==2)
                {
                    hsttext.setText(R.string.hstten);
                    chart.setImageBitmap(Bitmap.createScaledBitmap(thirdbitmap,1200,830,false));
                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addrtext.setText(fullAdress);
                count--;
                if(count<0) {
                    count = 2;
                }
                if(count==0)
                {
                    hsttext.setText(R.string.hstone);
                    chart.setImageBitmap(Bitmap.createScaledBitmap(firstbitmap, 1200, 830, false));
                }
                else if(count==1)
                {
                    hsttext.setText(R.string.hstfive);
                    chart.setImageBitmap(Bitmap.createScaledBitmap(secondbitmap,1200,830,false));
                }
                else if(count==2)
                {
                    hsttext.setText(R.string.hstten);
                    chart.setImageBitmap(Bitmap.createScaledBitmap(thirdbitmap,1200,830,false));
                }
            }
        });

        new fetchchart().execute(json);
        return view;
    }

    private class fetchchart extends AsyncTask<String,Void,Bitmap>
    {

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                JSONObject zillowjson=new JSONObject(params[0]);
                String firstchart=zillowjson.get("url").toString();
                String secondchart=zillowjson.get("url2").toString();
                String thirdchart=zillowjson.get("url3").toString();

                InputStream firstcharturl=new URL(firstchart).openStream();
                firstbitmap= BitmapFactory.decodeStream(firstcharturl);

                InputStream secondcharturl=new URL(secondchart).openStream();
                secondbitmap= BitmapFactory.decodeStream(secondcharturl);

                InputStream thirdcharturl=new URL(thirdchart).openStream();
                thirdbitmap= BitmapFactory.decodeStream(thirdcharturl);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return firstbitmap;
        }

        protected  void onPostExecute(Bitmap map)
        {

            super.onPostExecute(map);
            hsttext.setText(R.string.hstone);
            addrtext.setText(fullAdress);
            hsttext.setVisibility(View.VISIBLE);
            addrtext.setVisibility(View.VISIBLE);
            if(map!=null) {
                chart.setImageBitmap(Bitmap.createScaledBitmap(map, 1200, 830, false));
                prev.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
            }
            if(map==null)
            {
                hsttext.setText(R.string.warningcharts);
            }

            footer1.setVisibility(View.VISIBLE);
            footer2.setVisibility(View.VISIBLE);
            footer3.setVisibility(View.VISIBLE);
            footer2.setText(Html.fromHtml(
                    "Use is subject to " +
                            "<a href=\"http://www.zillow.com/wikipages/Privacy-and-Terms-of-Use/\">Terms of Use</a> "
            ));
            footer2.setMovementMethod(LinkMovementMethod.getInstance());

            footer3.setText(Html.fromHtml(

                    "<a href=\"http://www.zillow.com/zestimate/\">What's a Zestimate</a> "
            ));
            footer3.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
}
