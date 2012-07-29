package com.example.grabmyspot;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
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
