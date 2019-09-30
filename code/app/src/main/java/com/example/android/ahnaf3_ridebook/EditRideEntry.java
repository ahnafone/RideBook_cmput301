package com.example.android.ahnaf3_ridebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditRideEntry extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ride_entry);

        sharedPreferences = getSharedPreferences("rideBookData", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        position = sharedPreferences.getInt("position", -1);

        String temp = sharedPreferences.getString("" + position, "$");
        if (temp.equals("$"))
            finish();

        Ride rideEntry = getRide(temp);
        EditText et = findViewById(R.id.enterDistanceET);
        et.setText(rideEntry.getDistance()+"");
        et = findViewById(R.id.enterTimeEThour);
        et.setText(rideEntry.getTime().substring(0,2));
        et = findViewById(R.id.enterTimeETminute);
        et.setText(rideEntry.getTime().substring(3));
        et = findViewById(R.id.enterAveSpeedET);
        et.setText(rideEntry.getAveSpeed()+"");
        et = findViewById(R.id.enterAveCadenceET);
        et.setText(rideEntry.getAveCadence()+"");
        et = findViewById(R.id.enterDateET);
        et.setText(rideEntry.getDate());
        et = findViewById(R.id.commentBoxET);
        et.setText(rideEntry.getComments());

    }


    public Ride getRide(String temp) {
        Ride rideEntry = new Ride();
        String[] temps = temp.split("&");
        rideEntry.setDistance(Float.parseFloat(temps[0]));
        rideEntry.setTime(temps[1]);
        rideEntry.setDate(temps[2]);
        rideEntry.setAveSpeed(Float.parseFloat(temps[3]));
        rideEntry.setAveCadence(Float.parseFloat(temps[4]));
        rideEntry.setComments(temps[5]);

        return rideEntry;
    }

    private void retry(String str) {
        Toast.makeText(EditRideEntry.this, "The value entered for " + str + " is empty or not allowed.\nPlease try again.", Toast.LENGTH_LONG).show();
    }

    public void updateRide (View view) {
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
        editor.putString(("" + position), rideEntry.toData());
        editor.commit();

        finish();
    }

    public void deleteRide (View view) {
        editor.putInt("updates", -2);
        editor.putInt("position", position);
        editor.commit();

        finish();
    }

    public void cancel (View view) { finish(); }
}
