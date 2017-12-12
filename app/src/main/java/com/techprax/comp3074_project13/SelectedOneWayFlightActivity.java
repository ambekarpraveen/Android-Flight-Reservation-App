package com.techprax.comp3074_project13;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SelectedOneWayFlightActivity extends AppCompatActivity {

    private ListView selectedFlight;
    private SharedPreferences sharedPreferences;
    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private Bundle bundle;
    private int flightID;
    private android.support.v7.app.ActionBar actionBar;
    private TextView departureTime;
    private TextView arrivalTime;
    private TextView airline;
    private TextView fare;
    private TextView flightClass;
    private TextView flightDuration;
    private Button btnSelectFlight;
    private int currentTab;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_one_way_flight);

        sharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        currentTab = sharedPreferences.getInt("CURRENT_TAB", 0);

        flightID= sharedPreferences.getInt("ONEWAY_FLIGHT_ID",0);

        actionBar = getSupportActionBar();

        bundle = getIntent().getExtras();

        //flightID = bundle.getInt("SELECTED_FLIGHT_ID");


        departureTime = (TextView)findViewById(R.id.txtDepTime);
        arrivalTime = (TextView)findViewById(R.id.txtArrTime);
        airline = (TextView)findViewById(R.id.txtAir);
        fare = (TextView)findViewById(R.id.txtF);
        flightClass = (TextView)findViewById(R.id.txtCl);
        flightDuration = (TextView)findViewById(R.id.txtTrTime);

        btnSelectFlight = (Button)findViewById(R.id.btnSelect);

        displaySelection(flightID);

        btnSelectFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(getApplicationContext(), CheckOutActivity.class);

                intent.putExtra("ONEWAY_FLIGHT_ID", flightID);
                startActivity(intent);
            }
        });


    }

    public void displaySelection(int id){
        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();

            cursor = DatabaseHelper.selectFlightWithID(db, id);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                departureTime.setText(cursor.getString(5));
                arrivalTime.setText(cursor.getString(6));
                flightDuration.setText(cursor.getString(7));
                fare.setText(cursor.getString(8));
                airline.setText(cursor.getString(9));
                flightClass.setText(cursor.getString(11));

                actionBar.setTitle("Select flight");
                actionBar.setSubtitle(cursor.getString(2) + " -> " + cursor.getString(3));

            }

        }catch (SQLiteException ex){

        }
    }



}
