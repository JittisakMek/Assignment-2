package com.egci428.assignment2;

import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Random;

public class Sign_up extends AppCompatActivity implements SensorEventListener {

    private static EditText USER_NAME, USER_PASS, latText, longiText;
    public static String user_name, user_pass, latitude, longitude;

    public static double rand_lat, rand_long;
    public static String stringdouble, stringdouble2;
    double minX = 85.000000f;
    double maxX = 85.000000f;
    double minY = 179.999989f;
    double maxY = 179.999989f;

    private SensorManager sensorManager; // for any kind of sensor use sensorManager
    private long lastUpdate;
    private ProfileDataSource dataSource;

    public boolean namestatus = false;
    Cursor CR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setCustomView(R.layout.abs3_layout);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP
                | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        setActiobarTitle("Sign-up Page");

        USER_NAME = (EditText)findViewById(R.id.userTxt);
        USER_PASS = (EditText)findViewById(R.id.passTxt);
        latText = (EditText)findViewById(R.id.latTxt);
        longiText = (EditText)findViewById(R.id.longiTxt);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();

        dataSource = new ProfileDataSource(this);
        dataSource.open();
    }

    public void randButtonListener(View v){
        Random rand = new Random();
        rand_lat = (rand.nextDouble()*(maxX+1+minX))-minX;
        rand_lat = Math.round(rand_lat*1000000.0)/1000000.0;
        stringdouble = Double.toString(rand_lat);
        latText.setText(stringdouble);

        Random rand2 = new Random();
        rand_long = (rand2.nextDouble()*(maxY+1+minY))-minY;
        rand_long = Math.round(rand_long*1000000.0)/1000000.0;
        stringdouble2 = Double.toString(rand_long);
        longiText.setText(stringdouble2);
    }



    public void addButtonListener(View v){
        user_name = USER_NAME.getText().toString();
        user_pass = USER_PASS.getText().toString();
        latitude = latText.getText().toString();
        longitude = longiText.getText().toString();
        CR = dataSource.getInformation(dataSource);
        CR.moveToFirst();

        if(user_name.equals("") | latitude.equals("") | longitude.equals("")){
            Toast.makeText(getBaseContext(), "Please fill in the missing information", Toast.LENGTH_SHORT ).show();
        }
        else{
            namestatus = true;
            if( CR != null && CR.moveToFirst() ){

                do{
                    if(!user_name.equals(CR.getString(1))){
                        namestatus = true;
                    }
                    else{
                        Toast.makeText(getBaseContext(), "Username "+user_name+" has been taken", Toast.LENGTH_SHORT ).show();
                        namestatus = false;
                    }

                }while(CR.moveToNext() && namestatus !=false);
            }
            if(namestatus == true){

                    dataSource.createProfile(user_name,user_pass,latitude,longitude);
                    Toast.makeText(getBaseContext(), "Registration complete", Toast.LENGTH_SHORT ).show();
                    finish();
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            getAccelerometer(event);
        }

    }

    private void getAccelerometer(SensorEvent event){

        float[] values = event.values;

        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelerationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);  //calculation for accelerometer
        long actualTime = System.currentTimeMillis();

        if (accelerationSquareRoot >= 8) //
        {
            if (actualTime - lastUpdate < 200) {  //200 = 2millisec
                return;
            }
            lastUpdate = actualTime;
            Toast.makeText(this, "Device was shuffled", Toast.LENGTH_SHORT)
                    .show();
            // random generator
            Random rand = new Random();
            rand_lat = (rand.nextDouble()*(maxX+1+minX))-minX;
            rand_lat = Math.round(rand_lat);
            stringdouble = Double.toString(rand_lat);
            latText.setText(stringdouble);

            Random rand2 = new Random();
            rand_long = (rand2.nextDouble()*(maxY+1+minY))-minY;
            rand_long = Math.round(rand_long);
            stringdouble2 = Double.toString(rand_long);
            longiText.setText(stringdouble2);

        }
    }

    private void setActiobarTitle(String title)
    {
        View v = getSupportActionBar().getCustomView();
        TextView titleTxtView = (TextView)v.findViewById(R.id.myTitle);
        titleTxtView.setText(title);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
