package com.example.messagepay;

import java.util.ArrayList;
import java.util.List;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;

import Task.RequestTask;
import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ConfirmActivity extends Activity {

	private TextView total;
	private TextView sendOrRequest;
	private static String request ="Request";
	private static String title ="Request Money";
	private int sendOrRequestFlag;
	private String actionType = "Claim";
	private TextView confirm_title;
	private ListView listView;
	private TextView moneySplit;
	private TextView toWho;
	private String memo;
	int howmuch =0;
	int totalAmount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm);
		total =(TextView) findViewById(R.id.total);
		confirm_title= (TextView) findViewById(R.id.confirm_title);
		sendOrRequest = (TextView) findViewById(R.id.send_or_request);
		moneySplit = (TextView) findViewById(R.id.userSplit);
		Intent intent = this.getIntent();
		totalAmount = intent.getIntExtra(Constants.HOW_MUCH, 0);
		memo = intent.getStringExtra(Constants.MEMO);
		int howmany = intent.getIntExtra(Constants.NUM_PERSONS, 0);
		sendOrRequestFlag = intent.getIntExtra(Constants.SEND_OR_REQUEST_FLAG, Constants.SEND_MONEY_FLAG);
		howmuch = (int) Math.floor(totalAmount / howmany);
		toWho = (TextView) this.findViewById(R.id.towho);
		String totalString = this.getResources().getString(R.string.total);
		String totalFormat = String.format(totalString, totalAmount);
		String splitString = this.getResources().getString(R.string.money_split);
		String splitFormat = String.format(splitString, howmuch);
		if(sendOrRequestFlag==Constants.REQUEST_MONEY_FLAG){
			sendOrRequest.setText(request);
			actionType= "Pay";
			confirm_title.setText(title);
			moneySplit.setText(splitFormat);
			toWho.setText("Request To: ");
		}
		else{
			((ViewGroup)moneySplit.getParent()).removeView(moneySplit);
			toWho.setText("Send To:");
		}
		total.setText(totalFormat);		
		listView = (ListView)this.findViewById(R.id.userList);
		String[] friends = getFriendNameArray();
		listView.setAdapter(new ArrayAdapter<String>(this,R.layout.list_item_view,friends));	
		
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
//			if(i!=users.size()-1)
//			    facebookIds = users.get(i).getId()+",";
//			else
//				facebookIds = users.get(i).getId();
			if(i==0)
				facebookIds = users.get(i).getId();
			else
				break;
		}
		RequestParam param = new RequestParam(howmuch, facebookIds, actionType, memo);
		new RequestTask().execute(param);
		
		this.startActivity(intent);
	}
	
	String[] getFriendNameArray(){
		PayApplication payApplication = (PayApplication) getApplication();
		ArrayList<String> list = new ArrayList<String>();
		if(payApplication!=null){
		   List<GraphUser> users =  payApplication.getSelectedUsers();	
		   for(GraphUser user : users){
			   list.add(user.getName());
		   }
	}
		String[] result = new String[list.size()];
		list.toArray(result);
		return result;
		
	}
}
