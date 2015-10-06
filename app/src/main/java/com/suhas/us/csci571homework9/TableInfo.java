package com.suhas.us.csci571homework9;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


public class TableInfo extends Fragment{
    TextView footer2;
    TextView footer3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle zillowBundle=getArguments();
        String fullAdress=new String();
        String homelink,json=zillowBundle.getString("zillowjson");
        View view = inflater.inflate(R.layout.activity_table_info, container, false);
        boolean alwaysfalse=true;
        try {
            JSONObject zillowjson=new JSONObject(json);
            fullAdress=zillowjson.get("Street").toString();
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
            homelink=new String(zillowjson.get("homedetails").toString());
            TextView tvlink=(TextView)view.findViewById(R.id.homelink);
            String homeconcatstring=new String("<a href=\""+homelink+"\">"+fullAdress+"</a>");
            tvlink.setText(Html.fromHtml(homeconcatstring));
            tvlink.setMovementMethod(LinkMovementMethod.getInstance());

            TextView propertytext= (TextView) view.findViewById(R.id.duplex);
            propertytext.setText(zillowjson.get("property_type").toString());

            TextView yearBuilt= (TextView) view.findViewById(R.id.year);
            yearBuilt.setText(zillowjson.get("yearBuilt").toString());

            TextView lotSize= (TextView) view.findViewById(R.id.lotsizevalue);
            lotSize.setText(zillowjson.get("lotSize").toString());

            TextView finishedArea= (TextView) view.findViewById(R.id.finishedareavalue);
            finishedArea.setText(zillowjson.get("finished_area").toString());

            TextView bathrooms= (TextView) view.findViewById(R.id.bathroomvalue);
            bathrooms.setText(zillowjson.get("bathrooms").toString());

            TextView bedrooms= (TextView) view.findViewById(R.id.bedroomvalue);
            bedrooms.setText(zillowjson.get("bedrooms").toString());

            TextView taxassess= (TextView) view.findViewById(R.id.taxassessmentValue);
            taxassess.setText(zillowjson.get("tax_assesment").toString());

            TextView taxassessyear= (TextView) view.findViewById(R.id.taxyearvalue);
            taxassessyear.setText(zillowjson.get("tax_assesment_year").toString());

            TextView lastsold= (TextView) view.findViewById(R.id.lastsoldValue);
            lastsold.setText(zillowjson.get("last_sold_price").toString());

            TextView lastsolddate= (TextView) view.findViewById(R.id.LastSoldDateValue);
            lastsolddate.setText(zillowjson.get("last_sold_date").toString());

            TextView Zestimate= (TextView) view.findViewById(R.id.zestimateValue);
            Zestimate.setText(zillowjson.get("ZestimatePropertyEstimate_amount").toString());

            TextView Zestimate2= (TextView) view.findViewById(R.id.zestimate2);
            Zestimate2.setText("as of "+zillowjson.get("ZestimatePropertyEstimate_date").toString());

            TextView thirtydaysoverall= (TextView) view.findViewById(R.id.thirtydaysValue);
            thirtydaysoverall.setText(zillowjson.get("thirtydays_overall_change").toString());

            TextView allTimeProperty= (TextView) view.findViewById(R.id.allTimePropertyValue);
            if(zillowjson.get("zestimate_low").toString().equals("N/A") || zillowjson.get("zestimate_high").toString().equals("N/A")) {
                allTimeProperty.setText("N/A");
            }
            else {
                allTimeProperty.setText(zillowjson.get("zestimate_low").toString() + " - " + zillowjson.get("zestimate_high").toString());
            }

            TextView rentZestimate= (TextView) view.findViewById(R.id.rentZestimateValuelue);
            rentZestimate.setText(zillowjson.get("rentzestimate").toString());

            TextView rentzestimatedate= (TextView) view.findViewById(R.id.rentZestimate2);
            rentzestimatedate.setText(zillowjson.get("rentzestimatedate").toString());

            TextView thirtydaysrentchange= (TextView) view.findViewById(R.id.thirtydaysrentValue);
            thirtydaysrentchange.setText(zillowjson.get("thirtydays_rent_change").toString());

            TextView allTimePropertychange= (TextView) view.findViewById(R.id.allTimeRentValue);
            if(zillowjson.get("rentzestimate_low").toString().equals("N/A") || zillowjson.get("rentzestimate_high").toString().equals("N/A")) {
                allTimePropertychange.setText("N/A");
            }
            else {
                allTimePropertychange.setText(zillowjson.get("rentzestimate_low").toString() + " - " + zillowjson.get("rentzestimate_high").toString());
            }

            footer2 = (TextView) view.findViewById(R.id.footer2);
            footer3 = (TextView) view.findViewById(R.id.footer3);

            footer2.setText(Html.fromHtml(
                    "Use is subject to "+
                            "<a href=\"http://www.zillow.com/wikipages/Privacy-and-Terms-of-Use/\">Terms of Use</a> "
            ));
            footer2.setMovementMethod(LinkMovementMethod.getInstance());

            footer3.setText(Html.fromHtml(

                    "<a href=\"http://www.zillow.com/zestimate/\">What's a Zestimate</a> "
            ));
            footer3.setMovementMethod(LinkMovementMethod.getInstance());

        } catch (JSONException e) {
            e.printStackTrace();
        }

            return view;
    }
}
