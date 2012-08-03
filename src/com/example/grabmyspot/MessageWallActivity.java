package com.example.grabmyspot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.mobdb.android.GetRowData;
import com.mobdb.android.MobDB;
import com.mobdb.android.MobDBResponseListener;

public class MessageWallActivity extends ListActivity {
	
	String APP_KEY = "00#OoQ-1Ss-euagfD2021Y010Jum3WoBmmM-DOCLlK5HtbmWgGgCsW0O1CIA77Y509";
	ArrayAdapter<String>adapter;
	String[] comments;
	String[] timestamp;
	int counter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.message_wall);
	
	    
	    GetRowData getRowData = new GetRowData("comments");
	    getRowData.getField("comment");
	    getRowData.getField("timestamp");
	    
	    
	    MobDB.getInstance().execute(APP_KEY, getRowData, null, false, new MobDBResponseListener() {
		     
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
		    			
		    			comments = new String[length];
		    			timestamp = new String[length];
		    			
		    			for(int i = 0; i < length; i++){
		    				
		    				comments[length - i - 1] = array.getJSONObject(i).getString("comment"); 
		    				timestamp[length - i - 1] = array.getJSONObject(i).getString("timestamp");
		    				
		    			}
		    			
		    			
		    		
		    			List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		    			for (int i = 0; i < length; i++) {
		    			    Map<String, String> datum = new HashMap<String, String>(2);
		    			    datum.put("comment", comments[i]);
		    			    datum.put("timestamp", timestamp[i]);
		    			    data.add(datum);
		    			}
		    			SimpleAdapter adapter = new SimpleAdapter(MessageWallActivity.this, data,
		    			                                          android.R.layout.simple_list_item_2,
		    			                                          new String[] {"comment", "timestamp"},
		    			                                          new int[] {android.R.id.text1,
		    			                                                     android.R.id.text2});
		    			setListAdapter(adapter);

		    			
		    				
		    			
		    			
		    			
		    			
		    			//adapter = new ArrayAdapter<String>(MessageWallActivity.this, android.R.layout.simple_list_item_1, comments);
		    			//setListAdapter(adapter);
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
	      
	}
	
	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
		finish();
	
	
	}

}
