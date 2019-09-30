package com.example.android.ahnaf3_ridebook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Ride> rides;
    ArrayList<String> rideToString;
    ArrayAdapter<String> rideAdapter;
    ListView rideLV;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rides = new ArrayList<Ride>();
        rideToString = new ArrayList<String>();
        rideToString.add("No Entries.......");
        rideAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rideToString);
        rideLV = findViewById(R.id.ridesLV);
        rideLV.setAdapter(rideAdapter);

        //clear sharedPreferences from last use
        sharedPreferences  = getSharedPreferences("rideBookData", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();


        FloatingActionButton fab = findViewById(R.id.addEntriesBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addEntryIntent = new Intent(getApplicationContext(), AddRideEntry.class);

                //refresh();
                startActivity(addEntryIntent);
            }
        });


        rideLV.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        if(rideToString.get(i).equals("No Entries.......")) {
                            Intent addEntryIntent = new Intent(getApplicationContext(), AddRideEntry.class);

                            startActivity(addEntryIntent);

                        } else {
                            Intent editEntryIntent = new Intent(getApplicationContext(), EditRideEntry.class);
                            editor.putInt("position", i);
                            editor.putInt("updates", i);
                            editor.putString("" + i, rides.get(i).toData());
                            editor.commit();

                            startActivity(editEntryIntent);
                        }
                    }
                }
        );
    }


    @Override
    protected void onResume() {
        super.onResume();
        String temp = sharedPreferences.getString("" + rides.size(), "$");
        if(!temp.equals("$") && rides.size() == 0) {
            rideToString.remove("No Entries.......");
            addEntry(temp);
        }
        else if(!temp.equals("$"))
            addEntry(temp);

        checkForUpdates();

        rideAdapter.notifyDataSetChanged();
        setTotalDistance();
        editor.clear();
        editor.commit();
        editor.putInt("index", rides.size());
        editor.commit();
    }

    public void addEntry(String temp) {
        Ride rideEntry = new Ride();
        String[] temps = temp.split("&");
        rideEntry.setDistance(Float.parseFloat(temps[0]));
        rideEntry.setTime(temps[1]);
        rideEntry.setDate(temps[2]);
        rideEntry.setAveSpeed(Float.parseFloat(temps[3]));
        rideEntry.setAveCadence(Float.parseFloat(temps[4]));
        rideEntry.setComments(temps[5]);

        rides.add(rideEntry);
        rideToString.add(rideEntry.toString());
    }

    public void checkForUpdates() {
        int val = sharedPreferences.getInt("updates", -1);
        if (val != -1 && val != -2) {
            updateEntry(sharedPreferences.getString(""+val, "$"), val);
        } else if (val == -2) {
            deleteEntry(sharedPreferences.getInt("position", -1));
        }

    }

    public void updateEntry (String temp, int index) {
        Ride rideEntry = rides.get(index);
        String[] temps = temp.split("&");
        rideEntry.setDistance(Float.parseFloat(temps[0]));
        rideEntry.setTime(temps[1]);
        rideEntry.setDate(temps[2]);
        rideEntry.setAveSpeed(Float.parseFloat(temps[3]));
        rideEntry.setAveCadence(Float.parseFloat(temps[4]));
        rideEntry.setComments(temps[5]);

        rideToString.set(index, rideEntry.toString());
    }

    public void deleteEntry (int index) {
        rides.remove(index);
        rideToString.remove(index);
    }

    public void setTotalDistance() {
        float total = 0;
        for (int i=0; i<rides.size(); i++) {
            total += rides.get(i).getDistance();
        }

        TextView tv = findViewById(R.id.totalDistance);
        tv.setText("Total: " + total + " km");
    }
}
