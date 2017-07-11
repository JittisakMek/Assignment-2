package com.egci428.assignment2;

import android.renderscript.Double2;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyMap extends AppCompatActivity implements OnMapReadyCallback { //Change FragmentActivity to AppCompatActivity to add actionBar

    private GoogleMap mMap;
    private MainPage mainPage;

    public String user_name, x, y;
    Double lat, longi;
    Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_map2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP
                | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(R.layout.abs3_layout);
        user_name = getIntent().getStringExtra("username");
        setActiobarTitle(user_name);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        x = getIntent().getStringExtra("latitude");
        y = getIntent().getStringExtra("longitude");
        lat = Double.parseDouble(x);
        longi = Double.parseDouble(y);
        // Add a marker in Sydney and move the camera
        LatLng coordinate = new LatLng(lat, longi);
        mMap.addMarker(new MarkerOptions().position(coordinate).title(user_name+"'s Coordinate"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinate));
        Log.d("latitude", x);
        Log.d("longitude", y);
    }

    private void setActiobarTitle(String title)
    {
        View v = getSupportActionBar().getCustomView();
        TextView titleTxtView = (TextView)v.findViewById(R.id.myTitle);
        titleTxtView.setText(title+"'s Location");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
