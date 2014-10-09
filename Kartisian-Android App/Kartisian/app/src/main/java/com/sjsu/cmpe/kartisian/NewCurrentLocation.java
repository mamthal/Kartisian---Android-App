package com.sjsu.cmpe.kartisian;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NewCurrentLocation extends Activity implements LocationListener
{
    protected LocationManager locationManager;
    TextView LatLongtxt;
    Double latitude,longitude;
    protected boolean gps_enabled,network_enabled;
    EditText placename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_current_location);
        LatLongtxt = (TextView)findViewById(R.id.tv1);
        placename = (EditText)findViewById(R.id.loc_value);
        latitude = null;
        longitude = null;

        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        LatLongtxt.setText("Latitude:" + latitude + ", Longitude:" + longitude);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }

    public void onSaveCurrentLocation(View view)
    {
        placename = (EditText)findViewById(R.id.loc_value);
        DBHandler dbHandler = new DBHandler(getApplicationContext());
        if((placename.getText() != null) && (latitude != null) && (longitude != null))
        {
            dbHandler.insertPlace(placename.getText().toString(),null,latitude.toString(),longitude.toString());
            gobackHome(view);
        }
    }

    public void gobackHome(View view)
    {
        Intent intent = new Intent(this,HomeScreen.class);
        startActivity(intent);
    }
}
