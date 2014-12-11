package com.example.messagepay;

import java.util.List;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;

import Task.RequestTask;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ConfirmActivity extends Activity {

	private TextView total;
	private TextView sendOrRequest;
	private static String request ="Request";
	private int sendOrRequestFlag;
	private String actionType = "Pay";
	int totalamount = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm);
		total =(TextView) findViewById(R.id.total);
		sendOrRequest = (TextView) findViewById(R.id.send_or_request);
		Intent intent = this.getIntent();
		int howmuch = intent.getIntExtra(Constants.HOW_MUCH, 0);
		int howmany = intent.getIntExtra(Constants.NUM_PERSONS, 0);
		sendOrRequestFlag = intent.getIntExtra(Constants.SEND_OR_REQUEST_FLAG, Constants.SEND_MONEY_FLAG);
		if(sendOrRequestFlag==Constants.REQUEST_MONEY_FLAG){
			sendOrRequest.setText(request);
			actionType= request;
		}
		totalamount = howmuch * howmany;
		String totalString = this.getResources().getString(R.string.total);
		String totalFormat = String.format(totalString, howmany, howmuch, totalamount);
		total.setText(totalFormat);
	}
	
	public void backClick(View view){
		this.finish();
	}
	
	public void sendClick(View view){
		Intent intent = new Intent(this, PaymentConfirmActivity.class);
		intent.putExtra(Constants.SEND_OR_REQUEST_FLAG, sendOrRequestFlag);
		PayApplication payApplication = (PayApplication) getApplication();
		List<GraphUser> users =  payApplication.getSelectedUsers();	
		String facebookIds = null;
		for(int i =0; i<users.size(); i++){
			if(i!=users.size()-1)
			    facebookIds = users.get(i).getId()+",";
			else
				facebookIds = users.get(i).getId();
		}
		RequestParam param = new RequestParam(totalamount, facebookIds, actionType);
		new RequestTask().execute(param);
		
		this.startActivity(intent);
	}
}
