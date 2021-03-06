package com.bdjobs.training.firebasetest;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class HandlerActivity extends AppCompatActivity {

    Handler handler;
    Runnable runnable;
    Random random;
    DatabaseReference db;
    int latitude, longitude;
    LatLong latLong;
    ArrayList<LatLong> latLongArrayList;
    ListView listViewLatLong;
    LatLongListAdapter latLongListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        initilization();
        handlerPostDelayed();
        retrieveData();
         latLongListAdapter = new LatLongListAdapter(getApplicationContext(), latLongArrayList);
        listViewLatLong.setAdapter(latLongListAdapter);




    }

    private void retrieveData() {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                LatLong latLongvalue = dataSnapshot.getValue(LatLong.class);
                latLongArrayList.add(latLongvalue);
                latLongListAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addValueDB(LatLong value) {
         db.push().setValue(value);
        //db.child("Latitude").setValue(value.getLatitude());
        //db.child("Longitude").setValue(value.getLongitude());
    }

    private void initilization() {
        handler = new Handler();
        random = new Random();
        listViewLatLong = findViewById(R.id.latlongListLV);
        latLongArrayList = new ArrayList<LatLong>();
        db = FirebaseDatabase.getInstance().getReference();
    }

    private void handlerPostDelayed() {

        runnable = new Runnable() {
            @Override
            public void run() {
                latitude = random.nextInt(500);
                longitude= random.nextInt(500);

                latLong = new LatLong(latitude,longitude);

                Log.d("khkz", "run: "+ latitude);
                handler.postDelayed(this, 3000);

                addValueDB(latLong);
            }
        };
        handler.postDelayed(runnable, 3000);//h
    }
}
