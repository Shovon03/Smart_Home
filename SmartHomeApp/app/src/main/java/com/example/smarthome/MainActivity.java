package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    //declaring the variables
    Switch swtch1;
    Switch swtch2;
    Switch swtch3;
    Switch swtch4;

    Button allButton;

    Button resetButton;
    TextView textView;

    //declaring wifi
    WifiManager wifiManager;
    WifiInfo connection;

    //string for storing IP address
    String ipaddress;



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
        resetButton = (Button) findViewById(R.id.buttonReset);
        //connecting textView with textViewConn
        textView = (TextView) findViewById(R.id.textViewConn);



        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
                connection = wifiManager.getConnectionInfo();
                if(connection.getNetworkId()==-1){
                    ipaddress = "Status: Disconnected";
                    textView.setText("Status: Disconnected");
                }
                else {
                    ipaddress = Formatter.formatIpAddress(connection.getIpAddress());
                    textView.setText("Status: Connected to " + ipaddress);
                }
            }
        });

        swtch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swtch1.isChecked()){
                    Toast toast = Toast.makeText(getApplicationContext(), "Switch 1 turned on", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 300);
                    toast.show();
                }else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Switch 1 turned off", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 300);
                    toast.show();
                }

            }
        });

        swtch2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        //checking which button is clicked in the view
        if (view.getId()==R.id.button1){

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
            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 300);
            toast.show();
        }

        else if (swtch2.isChecked()){
            Toast toast = Toast.makeText(getApplicationContext(), "Switch 2 turned on", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 300);
            toast.show();
        }else if (!swtch2.isChecked()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Switch 2 turned off", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 300);
            toast.show();
        }


    }
}
