package com.example.grabmyspot;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

public class ProximityAlertReceiver extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 1000;
	 
	    @Override
	    public void onReceive(Context context, Intent intent) {
	         
	        String key = LocationManager.KEY_PROXIMITY_ENTERING;
	 
	        Boolean entering = intent.getBooleanExtra(key, false);
	         
	        if (entering) {
	            Log.d(getClass().getSimpleName(), "entering");
	            Toast.makeText(context, "Entering", Toast.LENGTH_SHORT).show();
		        NotificationManager notificationManager =
			            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			        
		        	Intent myIntent = new Intent(context, GarageListActivity.class);
			        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent, 0);       
			         
			        Notification notification = createNotification();
			        notification.setLatestEventInfo(context,
			            "Proximity Alert!", "You are nearing a garage!", pendingIntent);
			         
			        notificationManager.notify(NOTIFICATION_ID, notification);
	        }
	        else {
	            Log.d(getClass().getSimpleName(), "exiting");
	            Toast.makeText(context, "Exiting", Toast.LENGTH_SHORT).show();
	        }
	         

	         
	    }
     
	    private Notification createNotification() {
	        Notification notification = new Notification();
	         
	        notification.icon = R.drawable.ic_launcher;
	        notification.when = System.currentTimeMillis();
	         
	        notification.flags |= Notification.FLAG_AUTO_CANCEL;
	        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
	         
	        notification.defaults |= Notification.DEFAULT_VIBRATE;
	        notification.defaults |= Notification.DEFAULT_LIGHTS;
	         
	        notification.ledARGB = Color.WHITE;
	        notification.ledOnMS = 1500;
	        notification.ledOffMS = 1500;
	         
	        return notification;
	    }	     


}
