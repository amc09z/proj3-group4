package com.example.grabmyspot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdb.android.GetRowData;
import com.mobdb.android.MobDB;
import com.mobdb.android.MobDBResponseListener;
import com.mobdb.android.UpdateRowData;

public class CheckInActivity extends Activity {
	
	Button checkIn;
	Button checkOut;
	Button postMessage;
	final String APP_KEY = "00#OoQ-1Ss-euagfD2021Y010Jum3WoBmmM-DOCLlK5HtbmWgGgCsW0O1CIA77Y509";
	String garageName;
	TextView garageInfo;
	int current;
	int capacity;
	String[] comments;
	String[] timestamp;
	ListView list;
	//ArrayAdapter<String>adapter;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.checkin);
	    
	    garageName = getIntent().getStringExtra("garageName");
	    checkIn = (Button) findViewById(R.id.checkIn);
	    checkOut = (Button) findViewById(R.id.checkOut);
	    postMessage = (Button) findViewById(R.id.postmessagebutton);
	    garageInfo = (TextView) findViewById(R.id.garageInfo);
	    list = (ListView) findViewById(R.id.listView1);
	   
	    
	    	    
	   
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
	    
	      
		
		GetRowData getRowData2 = new GetRowData("comments");
	    getRowData2.getField("comment");
	    getRowData2.getField("timestamp");
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
		    			
		    			int length = array.length();
		    			    
		    			int x = length - 1;
		    			comments = new String[5];
		    			timestamp = new String[5];
		    			for(int i = 0; i < 5; i++){
		    				
		    				if(array.getJSONObject(x - i) != null){
		    				comments[i] = array.getJSONObject(x - i).getString("comment"); 
		    				timestamp[i] = array.getJSONObject(x - i).getString("timestamp");
		    				}
		        				  
		    			}
		    	
		    		}
		    		
		    		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	    			for (int i = 0; i < 5; i++) {
	    			    Map<String, String> datum = new HashMap<String, String>(2);
	    			    datum.put("comment", comments[i]);
	    			    datum.put("timestamp", timestamp[i]);
	    			    data.add(datum);
	    			}
	    			
	    			SimpleAdapter adapter = new SimpleAdapter(CheckInActivity.this, data,
	    			                                          android.R.layout.simple_list_item_2,
	    			                                          new String[] {"comment", "timestamp"},
	    			                                          new int[] {android.R.id.text1,
	    				      								  android.R.id.text2});
	    				     								 
	    			//adapter = new ArrayAdapter<String>(CheckInActivity.this, android.R.layout.simple_list_item_1, comments);
	    			list.setAdapter(adapter);
		    		   
		    		
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
	
	
	public void postMessageHandler(View v){
		
		Intent intent = new Intent(this, PostMessageActivity.class);
		intent.putExtra("garageName", garageName);
		startActivity(intent);
		
		
	}
	
	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
		finish();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		GetRowData getRowData2 = new GetRowData("comments");
	    getRowData2.getField("comment");
	    getRowData2.getField("timestamp");
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
		    			
		    			int length = array.length();
		    			    
		    			int x = length - 1;
		    			comments = new String[5];
		    			timestamp = new String[5];
		    			for(int i = 0; i < 5; i++){
		    				
		    				if(array.getJSONObject(x - i) != null){
		    				comments[i] = array.getJSONObject(x - i).getString("comment"); 
		    				timestamp[i] = array.getJSONObject(x - i).getString("timestamp");
		    				}
		        				  
		    			}
		    	
		    		}
		    		
		    		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	    			for (int i = 0; i < 5; i++) {
	    			    Map<String, String> datum = new HashMap<String, String>(2);
	    			    datum.put("comment", comments[i]);
	    			    datum.put("timestamp", timestamp[i]);
	    			    data.add(datum);
	    			}
	    			
	    			SimpleAdapter adapter = new SimpleAdapter(CheckInActivity.this, data,
	    			                                          android.R.layout.simple_list_item_2,
	    			                                          new String[] {"comment", "timestamp"},
	    			                                          new int[] {android.R.id.text1,
	    				      								  android.R.id.text2});
	    				     								 
	    			//adapter = new ArrayAdapter<String>(CheckInActivity.this, android.R.layout.simple_list_item_1, comments);
	    			list.setAdapter(adapter);
		    		   
		    		
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
	
		
	}

}
