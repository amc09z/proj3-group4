package com.example.grabmyspot;

import java.util.HashMap;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdb.android.GetRowData;
import com.mobdb.android.MobDB;
import com.mobdb.android.MobDBResponseListener;
import com.mobdb.android.UpdateRowData;

public class CheckInActivity extends Activity {
	
	Button checkIn;
	Button checkOut;
	final String APP_KEY = "00#OoQ-1Ss-euagfD2021Y010Jum3WoBmmM-DOCLlK5HtbmWgGgCsW0O1CIA77Y509";
	String garageName;
	TextView garageInfo;
	int current;
	int capacity;
	TextView comment1;
	TextView comment2;
	TextView comment3;
	TextView comment4;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.checkin);
	    
	    garageName = getIntent().getStringExtra("garageName");
	    checkIn = (Button) findViewById(R.id.checkIn);
	    checkOut = (Button) findViewById(R.id.checkOut);
	    garageInfo = (TextView) findViewById(R.id.garageInfo);
	    comment1 = (TextView) findViewById(R.id.comment1);
	    comment2 = (TextView) findViewById(R.id.comment2);
	    comment3 = (TextView) findViewById(R.id.comment3);
	    comment4 = (TextView) findViewById(R.id.comment4);
	    	    
	    GetRowData getRowData2 = new GetRowData("comments");
	    getRowData2.getField("comment");
	    getRowData2.whereEqualsTo("garagename", garageName);
	    
	    MobDB.getInstance().execute(APP_KEY, getRowData2, null, false, new MobDBResponseListener() {
		     
		    @Override public void mobDBSuccessResponse() {
		    //request successfully executed
		    	//Toast.makeText(getApplicationContext(), "got comments data", Toast.LENGTH_LONG).show();
		    }          
		     
		    @Override public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {
		    //row list in Vector<HashMap<String, Object[]>> object             
		    }          
		     
		    @Override public void mobDBResponse(String jsonStr) {
		    //table row list in raw JSON string (for format example: refer JSON REST API)
		    	try{
		    		JSONObject response = new JSONObject(jsonStr);
		    		int status = response.getInt("status");
		    		if(status == 101){
		    			
		    			JSONArray array = response.getJSONArray("row");
		    			JSONObject object1 = array.getJSONObject(0);
		    			JSONObject object2 = array.getJSONObject(1);
		    			JSONObject object3 = array.getJSONObject(2);
		    			JSONObject object4 = array.getJSONObject(3);
		    			
		    			
		    			comment1.setText(object1.getString("comment"));
		    			comment2.setText(object2.getString("comment"));
		    			comment3.setText(object3.getString("comment"));
		    			comment4.setText(object4.getString("comment"));
		    			
		    			
		    		}
		    		
		    		
		    	}catch(JSONException e){
		    		
		    	}
		    }
		     
		    @Override public void mobDBFileResponse(String fileName, byte[] fileData) {
		    //get file name with extension and file byte array
		    }          
		     
		    @Override public void mobDBErrorResponse(Integer errValue, String errMsg) {
		    //request failed
		    	Toast.makeText(getApplicationContext(), "didnt get comments data", Toast.LENGTH_LONG).show();
		    }
		});	
	    
	    
	    
	    
	    
	    
	    
	    GetRowData getRowData = new GetRowData("garage");
		getRowData.getField("current");
		getRowData.getField("capacity");
		getRowData.whereEqualsTo("name", garageName);
		
		MobDB.getInstance().execute(APP_KEY, getRowData, null, false, new MobDBResponseListener() {
		     
		    @Override public void mobDBSuccessResponse() {
		    //request successfully executed
		    	//Toast.makeText(getApplicationContext(), "got garage data", Toast.LENGTH_LONG).show();
		    }          
		     
		    @Override public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {
		    //row list in Vector<HashMap<String, Object[]>> object             
		    }          
		     
		    @Override public void mobDBResponse(String jsonStr) {
		    //table row list in raw JSON string (for format example: refer JSON REST API)
		    	try{
		    		JSONObject response = new JSONObject(jsonStr);
		    		int status = response.getInt("status");
		    		if(status == 101){
		    			
		    			JSONArray array = response.getJSONArray("row");
		    			JSONObject object = array.getJSONObject(0);
		    			current = object.getInt("current");
		    			capacity = object.getInt("capacity");
		    			
		    			
		    			garageInfo.setText(garageName + "    " + current + "/" + capacity);
		    		}
		    		
		    		
		    	}catch(JSONException e){
		    		
		    	}
		    }
		     
		    @Override public void mobDBFileResponse(String fileName, byte[] fileData) {
		    //get file name with extension and file byte array
		    }          
		     
		    @Override public void mobDBErrorResponse(Integer errValue, String errMsg) {
		    //request failed
		    	Toast.makeText(getApplicationContext(), "didnt get garage data", Toast.LENGTH_LONG).show();
		    }
		});	
		
		
	    
	    
	}
	
	
	
	public void checkInHandler(View v){
		
		current = current + 1;
		UpdateRowData updateRowData = new UpdateRowData("garage");
		updateRowData.setValue("current", current);
		updateRowData.whereEqualsTo("name", garageName);
		
		MobDB.getInstance().execute(APP_KEY, updateRowData, null, false, new MobDBResponseListener() {
		     
		    @Override public void mobDBSuccessResponse() {
		    //request successfully executed
		    	Toast.makeText(getApplicationContext(), "You have been checked into " + garageName, Toast.LENGTH_LONG).show();
		    	
		    	garageInfo.setText(garageName + "    " + current + "/" + capacity);
		    }          
		     
		    @Override public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {
		    //row list in Vector<HashMap<String, Object[]>> object             
		    }          
		     
		    @Override public void mobDBResponse(String jsonStr) {
		    //table row list in raw JSON string (for format example: refer JSON REST API)
		    	
		    }
		     
		    @Override public void mobDBFileResponse(String fileName, byte[] fileData) {
		    //get file name with extension and file byte array
		    }          
		     
		    @Override public void mobDBErrorResponse(Integer errValue, String errMsg) {
		    //request failed
		    	Toast.makeText(getApplicationContext(), "not updated", Toast.LENGTH_LONG).show();
		    }
		});	
		
		
	}
	
	
	public void checkOutHandler(View v){
		current = current - 1;
		UpdateRowData updateRowData = new UpdateRowData("garage");
		updateRowData.setValue("current", current);
		updateRowData.whereEqualsTo("name", garageName);
		
		MobDB.getInstance().execute(APP_KEY, updateRowData, null, false, new MobDBResponseListener() {
		     
		    @Override public void mobDBSuccessResponse() {
		    //request successfully executed
		    	
		    	garageInfo.setText(garageName + "    " + current + "/" + capacity);
		    	Toast.makeText(getApplicationContext(), "You have been checked out of " + garageName, Toast.LENGTH_LONG).show();
		    }          
		     
		    @Override public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {
		    //row list in Vector<HashMap<String, Object[]>> object             
		    }          
		     
		    @Override public void mobDBResponse(String jsonStr) {
		    //table row list in raw JSON string (for format example: refer JSON REST API)
		    	
		    }
		     
		    @Override public void mobDBFileResponse(String fileName, byte[] fileData) {
		    //get file name with extension and file byte array
		    }          
		     
		    @Override public void mobDBErrorResponse(Integer errValue, String errMsg) {
		    //request failed
		    	Toast.makeText(getApplicationContext(), "not updated", Toast.LENGTH_LONG).show();
		    }
		});	
		
		
	}
	

}
