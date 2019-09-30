package com.example.android.ahnaf3_ridebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AddRideEntry extends AppCompatActivity {

    int index;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ride_entry);

         sharedPreferences = getSharedPreferences("rideBookData", MODE_PRIVATE);

        index = sharedPreferences.getInt("index", -1);

        //set the text on app to current date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = "" + mdformat.format(calendar.getTime());
        EditText dateET = findViewById(R.id.enterDateET);
        dateET.setText(strDate);

    }

    private void retry(String str) {
        Toast.makeText(AddRideEntry.this, "The value entered for " + str + " is empty or not allowed. Please try again.", Toast.LENGTH_LONG).show();
    }

    public void addEntry(View view) {
        Ride rideEntry = new Ride();

        // check and set entries of Ride object
        EditText et = findViewById(R.id.enterDistanceET);
        String temp = et.getText().toString();
        if ( temp.isEmpty() || Float.parseFloat(temp) < 0) {
            retry("distance");
            return;
        }
        rideEntry.setDistance(Float.parseFloat(temp));

        et = findViewById(R.id.enterTimeEThour);
        EditText et2 = findViewById(R.id.enterTimeETminute);
        temp = et.getText().toString();
        String temp2 = et2.getText().toString();
        if (temp.isEmpty() || temp2.isEmpty() || Integer.parseInt(temp) < 0 || Integer.parseInt(temp) > 24 || Integer.parseInt(temp2) < 0 || Integer.parseInt(temp2) > 59) {
            retry("time");
            return;
        }
        rideEntry.setTime( (et.getText().toString()) + ":" + (et2.getText().toString()) );

        et = findViewById(R.id.enterDateET);
        temp = et.getText().toString();
        if(temp.isEmpty()) {
            retry("date");
            return;
        }
        int year = Integer.parseInt(temp.substring(0,4));
        int month = Integer.parseInt(temp.substring(5,7));
        int day = Integer.parseInt(temp.substring(8));
        if(year > 2019 || month < 0 || month > 12 || day < 0 || day > 31) {
            retry("date");
            return;
        }
        rideEntry.setDate(temp);

        et = findViewById(R.id.enterAveSpeedET);
        temp = et.getText().toString();
        if ( temp.isEmpty() || Float.parseFloat(temp) < 0) {
            retry("average speed");
            return;
        }
        rideEntry.setAveSpeed(Float.parseFloat(et.getText().toString()));

        et = findViewById(R.id.enterAveCadenceET);
        temp = et.getText().toString();
        if ( temp.isEmpty() || Float.parseFloat(temp) < 0) {
            retry("average cadence");
            return;
        }
        rideEntry.setAveCadence(Float.parseFloat(et.getText().toString()));

        et = findViewById(R.id.commentBoxET);
        String comments = et.getText().toString();
        rideEntry.setComments(comments);

        //save changes to SharedPreferences then go back
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(("" + index), rideEntry.toData());
        editor.commit();

        finish();

    }

    public void cancelEntry(View view) {
        finish();
    }

}
