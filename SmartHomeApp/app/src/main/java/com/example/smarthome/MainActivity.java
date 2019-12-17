package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import android.os.Handler;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //creating a request queue
    private RequestQueue mQueue;

    //declaring the variables
    Switch swtch1;
    Switch swtch2;
    Switch swtch3;
    Switch swtch4;

    Button allButton;

    Button parseButton;
    TextView textView;
    TextView mTextViewResult;

    //declaring wifi
    WifiManager wifiManager;
    WifiInfo connection;

    //for checking net connectivity every 5s.
    Handler handler = new Handler();

    //string for storing IP address
    String ipaddress;

    //string for storing API
    String urlAPI = "https://io.adafruit.com/api/v2/shovon03/feeds/alexapi-feed?X-AIO-KEY=a712e135b6bd4007aa23d99ca6e8d8d3";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //connecting the variable with elements in xml file
        swtch1 = findViewById(R.id.switch1);
        swtch2 = findViewById(R.id.switch2);
        swtch3 = findViewById(R.id.switch3);
        swtch4 = findViewById(R.id.switch4);

        allButton = (Button) findViewById(R.id.button1);
        allButton.setOnClickListener(this);

        //connecting resetButton with buttonReset from XML file
        parseButton = (Button) findViewById(R.id.buttonParse);

        //connecting textView with textViewConn
        textView = (TextView) findViewById(R.id.textViewConn);

        mTextViewResult = (TextView) findViewById(R.id.textViewAPI);

        //checking network response
        netCheck();

        parseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               netCheck();
            }
        });

        swtch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (swtch1.isChecked()) {
                    postRequestString("Lights on");
                    Toast toast = Toast.makeText(getApplicationContext(), "Switch 1 turned on", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 300);
                    toast.show();
                } else {
                    postRequestString("Lights off");
                    Toast toast = Toast.makeText(getApplicationContext(), "Switch 1 turned off", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 300);
                    toast.show();
                }

            }
        });

        swtch2.setOnClickListener(this);

        swtch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (swtch3.isChecked()) {
                    postRequestString("switch3 on");
                    Toast toast = Toast.makeText(getApplicationContext(), "Switch 3 turned on", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 300);
                    toast.show();
                } else {
                    postRequestString("switch3 off");
                    Toast toast = Toast.makeText(getApplicationContext(), "Switch 3 turned off", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 300);
                    toast.show();
                }

            }
        });

        swtch4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (swtch4.isChecked()) {
                    postRequestString("switch4 on");
                    Toast toast = Toast.makeText(getApplicationContext(), "Switch 3 turned on", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 300);
                    toast.show();
                } else {
                    postRequestString("switch4 off");
                    Toast toast = Toast.makeText(getApplicationContext(), "Switch 3 turned off", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 300);
                    toast.show();
                }

            }
        });


    }

    @Override
    public void onClick(View view) {
        //checking which button is clicked in the view
        if (view.getId() == R.id.button1) { //the turn all off button is clicked
            //giving a post request
            postRequestString("all off");

            //turning off all switches
            swtch1.setChecked(false);
            swtch2.setChecked(false);
            swtch3.setChecked(false);
            swtch4.setChecked(false);

            //Variables for showing toasts/small notifications
            Context context = getApplicationContext();
            CharSequence text = "All appliances are turned off";
            int duration = Toast.LENGTH_SHORT;

            //show the toast
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 300);
            toast.show();
        } else if (swtch2.isChecked()) {

            postRequestString("fan on");

            Toast toast = Toast.makeText(getApplicationContext(), "Switch 2 turned on", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 300);
            toast.show();
        } else if (!swtch2.isChecked()) {

            postRequestString("fan off");

            Toast toast = Toast.makeText(getApplicationContext(), "Switch 2 turned off", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 300);
            toast.show();
        }


    }


    private void networkQueue() {

        mQueue = Volley.newRequestQueue(MainActivity.this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlAPI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mTextViewResult.setText("Connected to adafruit API");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextViewResult.setText("Error connecting to adafruit API");
            }
        });

        // Add the request to the RequestQueue.
        mQueue.add(stringRequest);
    }

    private void postRequestString( final String thisString) {

        String urlpost = "https://io.adafruit.com/api/v2/shovon03/feeds/alexapi-feed/data?x-aio-key=a712e135b6bd4007aa23d99ca6e8d8d3";
        final String theHeader = "a712e135b6bd4007aa23d99ca6e8d8d3";

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, urlpost,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response

                        try {
                            JSONObject object = new JSONObject(response);

                        }catch (JSONException e){
                            mTextViewResult.setText("Server Response Error " );;
                        }
                        mTextViewResult.setText("Response: Post Successful" /*+response*/);;
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        mTextViewResult.setText("Response: Failed to post " +thisString);;
                    }
                }){


            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("x-aio-key", theHeader);
                return params;
            }

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("value",thisString);

                return params;
            }
        };
        requestQueue.add(postRequest);
    }
    private void netCheck(){
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        connection = wifiManager.getConnectionInfo();

        networkQueue();
        if (connection.getNetworkId() == -1) {

            textView.setText("           Status: Disconnected \n\n Please connect to the Internet.");

            Toast toast = Toast.makeText(getApplicationContext(), "Please turn on wifi or mobile data", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 300);
            toast.show();
        } else {

            //ipaddress = Formatter.formatIpAddress(connection.getIpAddress());
            textView.setText("Status: Connected" /*+ ipaddress*/);
        }
    }
    

}



