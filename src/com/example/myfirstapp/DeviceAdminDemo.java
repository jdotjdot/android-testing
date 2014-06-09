package com.example.myfirstapp;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

//http://stackoverflow.com/questions/18887636/how-to-record-phone-calls-in-android

public class DeviceAdminDemo extends DeviceAdminReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
	}
	
	public void onEnabled(Context context, Intent intent) {
		//deliberately empty
	}
	
	public void onDisabled(Context context, Intent intent) {
		//deliberately empty
	}

}
