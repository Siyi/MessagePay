package com.example.messagepay;

import java.util.Arrays;

import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainFragment extends Fragment {
	
	private static final String TAG = "MainFragment";
	private UiLifecycleHelper uiHelper;
	private ImageView settingsImageView;
	//private Button sendRequestButton;
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.activity_paypal, container, false);
	    LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
	    authButton.setFragment(this);
	    authButton.setReadPermissions(Arrays.asList("email","user_friends"));
	    settingsImageView =  (ImageView) view.findViewById(R.id.setting_image);
    	settingsImageView.setImageResource(R.drawable.settings_light);
    	/*sendRequestButton = (Button) view.findViewById(R.id.sendRequestButton);
    	sendRequestButton.setOnClickListener(new View.OnClickListener() {
    	    @Override
    	    public void onClick(View v) {
    	        sendRequestDialog();        
    	    }
    	});*/
	    return view;
	}

	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if (state.isOpened()) {
	        //sendRequestButton.setVisibility(View.VISIBLE);
	    } else if (state.isClosed()) {
	        //sendRequestButton.setVisibility(View.INVISIBLE);
	    }
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	 // For scenarios where the main activity is launched and user
	    // session is not null, the session state change notification
	    // may not be triggered. Trigger it if it's open/closed.
	    Session session = Session.getActiveSession();
	    if (session != null &&
	           (session.isOpened() || session.isClosed()) ) {
	        onSessionStateChange(session, session.getState(), null);
	    }
	    uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}
	
	  private void sendRequestDialog() {
	        Bundle params = new Bundle();
	        params.putString("message", "Learn how to make your Android apps social");

	        WebDialog requestsDialog = (
	            new WebDialog.RequestsDialogBuilder(getActivity(),
	                Session.getActiveSession(),
	                params))
	                .setOnCompleteListener(new OnCompleteListener() {

	                    @Override
	                    public void onComplete(Bundle values, FacebookException error) {
	                        if (error != null) {
	                            if (error instanceof FacebookOperationCanceledException) {
	                                Toast.makeText(getActivity().getApplicationContext(), 
	                                    "Request cancelled", 
	                                    Toast.LENGTH_SHORT).show();
	                            } else {
	                                Toast.makeText(getActivity().getApplicationContext(), 
	                                    "Network Error", 
	                                    Toast.LENGTH_SHORT).show();
	                            }
	                        } else {
	                            final String requestId = values.getString("request");
	                            if (requestId != null) {
	                                Toast.makeText(getActivity().getApplicationContext(), 
	                                    "Request sent",  
	                                    Toast.LENGTH_SHORT).show();
	                            } else {
	                                Toast.makeText(getActivity().getApplicationContext(), 
	                                    "Request cancelled", 
	                                    Toast.LENGTH_SHORT).show();
	                            }
	                        }   
	                    }
	                })
	                .build();
	        requestsDialog.show();
	    }

}
