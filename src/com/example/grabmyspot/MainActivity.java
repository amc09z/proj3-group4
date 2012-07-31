package com.example.grabmyspot;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {
    private static final long MINIMUM_DISTANCECHANGE_FOR_UPDATE = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATE = 1000; // in Milliseconds
	     
	private static final long POINT_RADIUS = 20; // in Meters
	private static final long PROX_ALERT_EXPIRATION = -1;
	 
	private static final String POINT_LATITUDE_KEY = "POINT_LATITUDE_KEY";
	private static final String POINT_LONGITUDE_KEY = "POINT_LONGITUDE_KEY";
     
	private static final String PROX_ALERT_INTENT =
	         "com.javacodegeeks.android.lbs.ProximityAlert";
	     	     
	private LocationManager locationManager;
	
	double Woodward_lat = 30.444595;
	double Woodward_long = -84.298858;
	double CallSt_lat = 30.44413;
	double CallSt_long = -84.289121;
	double Traditions_lat = 30.441852;
	double Traditions_long = -84.297155;
	double Copeland_lat = 30.43799;
	double Copeland_long = -84.290412;
	double Stadium_lat = 30.443476;
	double Stadium_long = -84.305232;
	
	
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, 
                        MINIMUM_TIME_BETWEEN_UPDATE, 
                        MINIMUM_DISTANCECHANGE_FOR_UPDATE,
                        new MyLocationListener());
        
        addProximityAlert(Woodward_lat, Woodward_long);
        addProximityAlert(CallSt_lat, CallSt_long);
        addProximityAlert(Traditions_lat, Traditions_long);
        addProximityAlert(Copeland_lat, Copeland_long);
        addProximityAlert(Stadium_lat, Stadium_long);
        


    }
    
    private void saveProximityAlertPoint() {
        Location location = 
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location==null) {
            Toast.makeText(this, "No last known location. Aborting...", 
                Toast.LENGTH_LONG).show();
            return;
        }
        saveCoordinatesInPreferences((float)location.getLatitude(),
               (float)location.getLongitude());
        addProximityAlert(location.getLatitude(), location.getLongitude());
    }
    
    private void addProximityAlert(double latitude, double longitude) {
        
        Intent intent = new Intent(PROX_ALERT_INTENT);
        PendingIntent proximityIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        
        locationManager.addProximityAlert(
            latitude, // the latitude of the central point of the alert region
            longitude, // the longitude of the central point of the alert region
            POINT_RADIUS, // the radius of the central point of the alert region, in meters
            PROX_ALERT_EXPIRATION, // time for this proximity alert, in milliseconds, or -1 to indicate no expiration 
            proximityIntent // will be used to generate an Intent to fire when entry to or exit from the alert region is detected
       );
        
       IntentFilter filter = new IntentFilter(PROX_ALERT_INTENT);  
       registerReceiver(new ProximityAlertReceiver(), filter);
       
    }

    
    private void saveCoordinatesInPreferences(float latitude, float longitude) {
        SharedPreferences prefs = 
           this.getSharedPreferences(getClass().getSimpleName(),
                           Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putFloat(POINT_LATITUDE_KEY, latitude);
        prefsEditor.putFloat(POINT_LONGITUDE_KEY, longitude);
        prefsEditor.commit();
    }
    
    private Location retrievelocationFromPreferences() {
        SharedPreferences prefs = 
           this.getSharedPreferences(getClass().getSimpleName(),
                           Context.MODE_PRIVATE);
        Location location = new Location("POINT_LOCATION");
        location.setLatitude(prefs.getFloat(POINT_LATITUDE_KEY, 0));
        location.setLongitude(prefs.getFloat(POINT_LONGITUDE_KEY, 0));
        return location;
    }
    
    public class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            Location pointLocation = retrievelocationFromPreferences();
            float distance = location.distanceTo(pointLocation);
            Toast.makeText(MainActivity.this, "Distance from Point:"+distance, Toast.LENGTH_LONG).show();
        }
        public void onStatusChanged(String s, int i, Bundle b) {            
        }
        public void onProviderDisabled(String s) {
        }
        public void onProviderEnabled(String s) {            
        }
    }
    

    
    public void myGarageButtonHandler(View v){
    	Toast.makeText(this, "Going to garages", Toast.LENGTH_SHORT).show();
    	Intent myIntent = new Intent(this, GarageListActivity.class);
    	startActivity(myIntent);
    	
    }
    
    public void myMessageButtonHandler(View v){
    	Toast.makeText(this, "Going to message wall", Toast.LENGTH_SHORT).show();
    	Intent myIntent = new Intent(this, MessageWallActivity.class);
    	startActivity(myIntent);
    }
    
    public void myOptionsButtonHandler(View v){
    	Toast.makeText(this, "Going to options menu", Toast.LENGTH_SHORT).show();
    	Intent myIntent = new Intent(this, OptionsPageActivity.class);
    	startActivity(myIntent);
    }
   

    
}
