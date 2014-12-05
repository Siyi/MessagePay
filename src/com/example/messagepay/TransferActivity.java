package com.example.messagepay;

import java.util.List;

import com.facebook.model.GraphUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class TransferActivity extends Activity {

	private static int pickerRequestCode = 0;
	private static int sentRequestCode =1;
	private EditText emailInput;
	private EditText amountInput;
	private EditText messageInput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transfer);
		emailInput = (EditText) this.findViewById(R.id.email_input);
		amountInput = (EditText) this.findViewById(R.id.amount_text);
		messageInput =(EditText) this.findViewById(R.id.message_text);
		
	}
	
	public void friends_click(View view){
		Intent intent = new Intent(this, PickerActivity.class);
		this.startActivityForResult(intent, pickerRequestCode);
	}
	
	public void next_click (View view){
		if(emailInput.getText().toString().length()!=0&&amountInput.getText().toString().length()!=0){
		   Intent intent = new Intent(this, PaymentConfirmActivity.class);
		   this.startActivityForResult(intent, sentRequestCode);
		}
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
		else if(requestCode == sentRequestCode){
			emailInput.setText("");		
			amountInput.setText("");
			messageInput.setText("");
		}
		
	}

	
	
}
