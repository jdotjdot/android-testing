package com.example.myfirstapp;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	private static final int REQUEST_CODE = 0;
	private DevicePolicyManager mDPM;
	private ComponentName mAdminName;
	File audio_file;
	
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        try {
        	// Initiate DevicePolicyManager
        	mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        	mAdminName = new ComponentName(this, DeviceAdminDemo.class);
        	
        	if (!mDPM.isAdminActive(mAdminName)) {
        		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mAdminName);
        		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,  "Click on Activate button to secure your application.");
        		startActivityForResult(intent, REQUEST_CODE);
        	} else {
        		// mDPM.lockNow();
        		// Intent intent = new Intent(MainActivity.this, TrackDeviceService.class);
        		// startService(intent);
        	}
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
    
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//    	super.onActivityResult(requestCode, resultCode, data);
//    	
//    	if (REQUEST_CODE == requestCode) {
//    		Intent intent = new Intent (MainActivity.this, TService.class);
//    		startService(intent);
//    	}
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    

	/** called when the user clicks the Send button **/
	public void sendMessage (View view) {	
		// do something in response to the button
		
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
		
		}
	
	/* go to audio page */
	public void goToAudioPage(View view) {
		
		Intent intent = new Intent(this, AudioRecordTest.class);
		startActivity(intent);
		
	}
	
	public void testRecordAudio (View view) {
		
		//File directory stuff
		File sampleDir = new File(Environment.getExternalStorageDirectory(), "/Testrecording.amr");
		if (!sampleDir.exists()) {
			sampleDir.mkdirs();
		}
		
		String file_name = "Record";
		try {
			audio_file = File.createTempFile(file_name, ".amr", sampleDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Create and set up the recorder
		MediaRecorder recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_DOWNLINK);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(audio_file.getAbsolutePath());
		try {
			recorder.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		recorder.start();

	}
	
	
	/** When 'Call' button is pressed */
	public void makeCall(View view) {
		
		//very frustrated
		
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:7328010802"));
		startActivity(callIntent);
		
	}
}
