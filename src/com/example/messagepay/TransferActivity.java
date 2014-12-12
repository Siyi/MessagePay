package com.example.messagepay;

import java.util.ArrayList;
import java.util.List;

import com.facebook.model.GraphUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;

public class TransferActivity extends Activity {

	private static int pickerRequestCode = 0;
	private ImageView transferImageView;
	private ImageView settingImageView;
	private ImageView requestImageView;
	private ImageView senderImageView;
	private ImageView nextImageView;
	private EditText emailInput;
	private EditText amountInput;
	private EditText messageInput;
	private boolean firstTimeTouch;
    private int howmuch;
    private int howmany;
    private int flag;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transfer);
		transferImageView = (ImageView) this.findViewById(R.id.transfer_image);
		transferImageView.setImageResource(R.drawable.transfer_light);
		settingImageView =(ImageView) this.findViewById(R.id.setting_image);
		settingImageView.setImageResource(R.drawable.settings);
		emailInput = (EditText) this.findViewById(R.id.email_input);
		amountInput = (EditText) this.findViewById(R.id.amount_text);
		messageInput =(EditText) this.findViewById(R.id.message_text);
		requestImageView =(ImageView) this.findViewById(R.id.request_image);
		senderImageView = (ImageView) this.findViewById(R.id.send_image);
		nextImageView =(ImageView) this.findViewById(R.id.next);
		messageInput.setOnTouchListener( new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(firstTimeTouch){
					firstTimeTouch=false;
					howmuch = Integer.parseInt(amountInput.getText().toString());
				  if(amountInput.getText().toString().contains("."))
					  amountInput.setText("$"+amountInput.getText().toString()+" USD");
				  else
					  amountInput.setText("$"+amountInput.getText().toString()+".00 USD");
				   }
				return false;				
				}
			});
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		firstTimeTouch=true;
	}

	
	public void friends_click(View view){
		Intent intent = new Intent(this, PickerActivity.class);
		this.startActivityForResult(intent, pickerRequestCode);
	}
	
	public void transferClick(View view){}
	
	public void settingsClick(View view){
		Intent intent = new Intent(this, PaypalActivity.class);
		this.startActivity(intent);
	}
	
	public void nextClick(View view) {
		if (emailInput.getText().toString().length() != 0 && amountInput.getText().toString().length() != 0) {
			Intent intent = new Intent(this, ConfirmActivity.class);
			intent.putExtra(Constants.HOW_MUCH, howmuch);
			intent.putExtra(Constants.NUM_PERSONS, howmany);
			intent.putExtra(Constants.SEND_OR_REQUEST_FLAG, flag);
			if(messageInput.getText()!=null)
			   intent.putExtra(Constants.MEMO, messageInput.getText().toString() );
			this.startActivity(intent);
		}
	}

	public void requestClick(View view){
		requestImageView.setImageResource(R.drawable.request_selected);
		senderImageView.setImageResource(R.drawable.send_unselect);
		emailInput.setHint(this.getResources().getString(R.string.request_emailhint));
		nextImageView.setImageResource(R.drawable.request_image);
		flag = Constants.REQUEST_MONEY_FLAG;
		emailInput.setText("");
		amountInput.setText("");
		
	}
	
	public void sendClick(View view){
		requestImageView.setImageResource(R.drawable.request);
		senderImageView.setImageResource(R.drawable.send);
		emailInput.setHint(this.getResources().getString(R.string.emailhint));
		nextImageView.setImageResource(R.drawable.next);
		flag =Constants.SEND_MONEY_FLAG;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == pickerRequestCode){
			if(resultCode == RESULT_OK){
				PayApplication payApplication = (PayApplication) getApplication();
				if(payApplication!=null){
				   List<GraphUser> users =  payApplication.getSelectedUsers();	
				   if(users!=null){
					   howmany=users.size();
					   StringBuilder sb = new StringBuilder();
					   for(GraphUser user: users){
						   sb.append(user.getName());
						   sb.append(", ");
					   }
					   String receipts = sb.toString();
					   if(receipts !=null && receipts.length()>1)
						   receipts = receipts.substring(0, receipts.length()-2);
					   emailInput.setText(receipts);
				   }
				}
			}
		}
	}	
	
}
