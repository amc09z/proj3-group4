package com.example.grabmyspot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdb.android.InsertRowData;
import com.mobdb.android.MobDB;
import com.mobdb.android.MobDBResponseListener;

public class PostMessageActivity extends Activity {

	final String APP_KEY = "00#OoQ-1Ss-euagfD2021Y010Jum3WoBmmM-DOCLlK5HtbmWgGgCsW0O1CIA77Y509";
	String garageName;
	TextView messageIntro;
	RadioGroup group1;
	RadioGroup group2;
	Button postMessageButton;
	String comment;
	RadioButton enter;
	RadioButton exit;
	RadioButton floor1;
	RadioButton floor2;
	RadioButton floor3;
	RadioButton floor4;
	RadioButton floor5;
	RadioButton floor6;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.post_message);
	
	    garageName = getIntent().getStringExtra("garageName");
	    messageIntro = (TextView) findViewById(R.id.messageintro);
	    group1 = (RadioGroup) findViewById(R.id.radioGroup1);
	    group2 = (RadioGroup) findViewById(R.id.radioGroup2);
	    postMessageButton = (Button) findViewById(R.id.postmessagebutton);
	    messageIntro.setText("Post a message for " + garageName);
	    enter = (RadioButton) findViewById(R.id.radioenter);
	    exit = (RadioButton) findViewById(R.id.radioexit);
	    floor1 = (RadioButton) findViewById(R.id.floor1);
	    floor2 = (RadioButton) findViewById(R.id.floor2);
	    floor3 = (RadioButton) findViewById(R.id.floor3);
	    floor4 = (RadioButton) findViewById(R.id.floor4);
	    floor5 = (RadioButton) findViewById(R.id.floor5);
	    floor6 = (RadioButton) findViewById(R.id.floor6);
	    
	}
	
	
	public void postMessageHandler(View v){
		
		int floor = 0;
		if(floor1.isChecked()){
			floor = 1;
		}else if(floor2.isChecked()){
			floor = 2;
		}else if(floor3.isChecked()){
			floor = 3;
		}else if(floor4.isChecked()){
			floor = 4;
		}else if(floor5.isChecked()){
			floor = 5;
		}else if(floor6.isChecked()){
			floor = 6;
		}
		
		if(enter.isChecked()){
			comment = "Entering the " + floor + " floor of " + garageName;
		}else{
			comment = "Exiting the " + floor + " floor of " + garageName;
		}
		
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
		String formattedDate = sdf.format(date);
		
		InsertRowData insertRowData = new InsertRowData("comments");
		insertRowData.setValue("timestamp", formattedDate);
		insertRowData.setValue("comment", comment);
		insertRowData.setValue("garagename", garageName);
		
		MobDB.getInstance().execute(APP_KEY, insertRowData, null, false, new MobDBResponseListener() {
		     
		    @Override public void mobDBSuccessResponse() {
		    //request successfully executed
		    	Toast.makeText(getApplicationContext(), "Message Posted to: " + garageName, Toast.LENGTH_LONG).show();
		    	finish();
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
		    	Toast.makeText(getApplicationContext(), "didnt insert data", Toast.LENGTH_LONG).show();
		    }
		});	
		
		
		
	}
	
	

}
