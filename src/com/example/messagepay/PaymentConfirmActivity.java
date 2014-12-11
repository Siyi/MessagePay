package com.example.messagepay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class PaymentConfirmActivity extends Activity {
	private int sendOrRequestFlag;
	private ImageView top;
	private ImageView more;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment_confirm);
		top= (ImageView)this.findViewById(R.id.top);
		more = (ImageView)this.findViewById(R.id.more);
		Intent intent = this.getIntent();
		sendOrRequestFlag = intent.getIntExtra(Constants.SEND_OR_REQUEST_FLAG, Constants.SEND_MONEY_FLAG);
		if(sendOrRequestFlag==Constants.REQUEST_MONEY_FLAG){
			top.setImageResource(R.drawable.request_top);
			more.setImageResource(R.drawable.request_more_money);
		}
	}
	
	public void send_more_click(View view){
		Intent intent = new Intent(this, TransferActivity.class);
		this.startActivity(intent);
	}
}
