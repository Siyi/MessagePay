package com.example.messagepay;

import java.util.List;

import com.facebook.model.GraphUser;

import android.app.Application;

public class PayApplication extends Application{
     private List<GraphUser> selectedUsers;

	public List<GraphUser> getSelectedUsers() {
		return selectedUsers;
	}

	public void setSelectedUsers(List<GraphUser> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}
}
