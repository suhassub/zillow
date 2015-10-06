package com.suhas.us.csci571homework9;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends Activity {

    boolean addressExist;
    boolean cityExist;
    boolean stateExist;
    String textAddr;
    String textCity;
    String textState;
    String zillowUrl;
    EditText textAddrBox;
    EditText textCityBox;
    Spinner spinnerState;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.state, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        image= (ImageView) findViewById(R.id.imageView);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://zillow.com"));
                startActivity(intent);
            }
        });

        textAddrBox = (EditText) findViewById(R.id.Address);
        textAddrBox.addTextChangedListener(new TextWatcher() {
            TextView t= (TextView) findViewById(R.id.AddressError);
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals(""))
                {

                    t.setText(R.string.warning);
                    t.setVisibility(View.VISIBLE);
                    addressExist=false;
                }
                else
                {
                    addressExist=true;
                    t.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


         textCityBox = (EditText) findViewById(R.id.City);
         textCityBox.addTextChangedListener(new TextWatcher() {
            TextView t= (TextView) findViewById(R.id.CityError);
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals(""))
                {
                    t.setText(R.string.warning);
                    t.setVisibility(View.VISIBLE);
                    cityExist=false;
                }
                else
                {
                    t.setVisibility(View.INVISIBLE);
                    cityExist=true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        spinnerState = (Spinner) findViewById(R.id.spinner);
        Button searchbutton=(Button)findViewById(R.id.Search);
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textState = spinnerState.getSelectedItem().toString();
                if(!textState.equals("Choose State"))
                {
                    stateExist=true;
                }
                else
                {
                    stateExist=false;
                }
                if(addressExist && cityExist && stateExist) {
                    TextView t= (TextView) findViewById(R.id.AddressError);
                    t.setVisibility(View.INVISIBLE);
                    TextView t1= (TextView) findViewById(R.id.CityError);
                    t1.setVisibility(View.INVISIBLE);
                    TextView t2= (TextView) findViewById(R.id.StateError);
                    t2.setVisibility(View.INVISIBLE);
                    textAddr = textAddrBox.getText().toString();
                    textCity = textCityBox.getText().toString();

                    zillowUrl=new String("http://secondbeanapplicati-env.elasticbeanstalk.com/?Street="+new String(textAddr.replaceAll("\\s+","+"))+"&City="+new String(textCity.replaceAll("\\s+","+"))+"&drop="+textState);
                    new Fetchphpcontents().execute(zillowUrl);
                }
                else
                {
                    ValidateCity();
                    ValidateAddress();
                    ValidateState();
                    TextView noMatch= (TextView) findViewById(R.id.noaddr);
                    noMatch.setVisibility(View.INVISIBLE);

                }
            }

            public void ValidateCity()
            {
                textCity = textCityBox.getText().toString();
                if(textCity.equals("")) {
                    TextView t1 = (TextView) findViewById(R.id.CityError);
                    t1.setVisibility(View.VISIBLE);

                }
                else
                {
                    TextView t= (TextView) findViewById(R.id.CityError);
                    t.setVisibility(View.INVISIBLE);

                }
            }
            public void ValidateAddress()
            {
                textAddr = textAddrBox.getText().toString();
                if(textAddr.equals(""))
                {
                    TextView t= (TextView) findViewById(R.id.AddressError);
                    t.setVisibility(View.VISIBLE);

                }
                else
                {
                    TextView t= (TextView) findViewById(R.id.AddressError);
                    t.setVisibility(View.INVISIBLE);

                }
            }

            public void ValidateState()
            {
                textState = spinnerState.getSelectedItem().toString();
                if(textState.equals("Choose State"))
                {
                    TextView t= (TextView) findViewById(R.id.StateError);
                    t.setVisibility(View.VISIBLE);
                }
                else
                {
                    TextView t= (TextView) findViewById(R.id.StateError);
                    t.setVisibility(View.INVISIBLE);
                }

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class Fetchphpcontents extends AsyncTask<String,Void,JSONObject>
    {

        @Override
        protected JSONObject doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(params[0]);
            try {
                HttpResponse zillowresponse = client.execute(get);
                return( new JSONObject((new BufferedReader(new InputStreamReader(zillowresponse.getEntity().getContent(), "UTF-8")).readLine())));
                } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            try {
                String result= jsonObject.get("error").toString();
                if(result.equals("0"))
                {
                    TextView noMatch= (TextView) findViewById(R.id.noaddr);
                    Intent intent=new Intent(getApplicationContext(),zillowresult.class);
                    noMatch.setVisibility(View.INVISIBLE);
                    intent.putExtra("zillowjson",jsonObject.toString());
                    startActivity(intent);
                }
                else
                {
                    TextView noMatch= (TextView) findViewById(R.id.noaddr);
                    noMatch.setVisibility(View.VISIBLE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
