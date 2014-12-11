package com.example.messagepay;

import com.facebook.AppEventsLogger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class PaypalActivity extends FragmentActivity {
	
	private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            // Add the fragment on initial activity setup
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, mainFragment)
            .commit();
        } else {
            // Or set the fragment from restored state info
            mainFragment = (MainFragment) getSupportFragmentManager()
            .findFragmentById(android.R.id.content);
        }
    }
    
    @Override
    protected void onResume(){
    	super.onResume();
    	AppEventsLogger.activateApp(this);
    }
    
    @Override
    protected void onPause(){
    	super.onPause();
    	AppEventsLogger.deactivateApp(this);
    }

    public void transferClick(View view){
		Intent intent = new Intent(this, TransferActivity.class);
		this.startActivity(intent);
	}
    
    public void settingsClick(View view){
    }
    
  
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.paypal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
