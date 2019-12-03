package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    //declaring the variables
    SwitchCompat switch1;
    SwitchCompat switch2;
    SwitchCompat switch3;
    SwitchCompat switch4;
    Button allButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //connecting the variable with elements in xml file

        allButton = (Button) findViewById(R.id.button1);
        allButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        //checking which button is clicked in the view
        if (view.getId()==R.id.button1){

            //Variables for showing toasts/small notifications
            Context context = getApplicationContext();
            CharSequence text = "All appliances are turned off";
            int duration = Toast.LENGTH_SHORT;

            //show the toast
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 300);
            toast.show();
        }
    }
}
