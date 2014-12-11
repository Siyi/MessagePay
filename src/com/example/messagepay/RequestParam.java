package com.example.messagepay;

import android.widget.TextView;

public class RequestParam {
	private int total;
	private String facebookIds;
	private String actionType;
	
	public RequestParam(int total, String facebookIds, String actionType) {
		this.total = total;
		this.facebookIds = facebookIds;
		this.actionType = actionType;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getFacebookIds() {
		return facebookIds;
	}

	public void setFacebookIds(String facebookIds) {
		this.facebookIds = facebookIds;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

}
