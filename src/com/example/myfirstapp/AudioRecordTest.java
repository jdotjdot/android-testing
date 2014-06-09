package com.example.myfirstapp;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class AudioRecordTest extends Activity {
	
	private static final String LOG_TAG = "AudioRecordTest";
	private static String mFileName = null;
	
	private RecordButton mRecordButton = null;
	private MediaRecorder mRecorder = null;
	
	private PlayButton mPlayButton = null;
	private MediaPlayer mPlayer = null;
	
	private CallButton mCallButton = null;
	
	private void onRecord(boolean start) {
		if (start) {
			startRecording();
		} else {
			stopRecording();
		}
	}
	
	private void onPlay(boolean start) {
		if (start) {
			startPlaying();
		} else {
			stopPlaying();
		}
	}
	
	private void startPlaying() {
		mPlayer = new MediaPlayer();
		try {
			Log.i(LOG_TAG, "Play file:" + mFileName);
			mPlayer.setDataSource(mFileName);
			Log.i(LOG_TAG, "About to call mPlayer.prepare()");
			mPlayer.prepare();
			Log.i(LOG_TAG, "About to call mPlayer.start()");
			mPlayer.start();
		} catch (IOException e) {
			Log.e(LOG_TAG, "mPlayer.prepare() failed");
		}
	}
	
	private void stopPlaying() {
		mPlayer.release();
		mPlayer = null;
	}
	
	private void startRecording() {
		
//		if (mFileName == null) {
//			mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
//			mFileName += "/audiorecordtest.aac";
//		}
		
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		// mRecorder.setAudioSource(MediaRecoder.AudioSource.VOICE_DOWNLINK);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecorder.setOutputFile(mFileName);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		
		try {
			mRecorder.prepare();
			Log.i(LOG_TAG, "Just called mRecorder.prepare()");
		} catch (IOException e) {
			Log.e(LOG_TAG, "mRecorder.prepare() failed");
		}
		
		Log.i(LOG_TAG, "About to call mRecorder.start()");
		mRecorder.start();
	}
	
	private void stopRecording() {
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
	}
	
	class RecordButton extends Button {
		boolean mStartRecording = true;
		
		OnClickListener clicker = new OnClickListener() {
			public void onClick(View v) {
				onRecord(mStartRecording);
				if (mStartRecording) {
					setText("Stop recording");
				} else {
					setText("Start recording");
				}
				mStartRecording = !mStartRecording;
			}
		};
		
		public RecordButton(Context ctx) {
			super(ctx);
			setText("Start recording");
			setOnClickListener(clicker);
		}
	}
	
	class PlayButton extends Button {
		boolean mStartPlaying = true;
		
		OnClickListener clicker = new OnClickListener() {
			public void onClick(View v) {
				onPlay(mStartPlaying);
				if (mStartPlaying) {
					setText("Stop playing");
				} else {
					setText("Start playing");
				}
				mStartPlaying = !mStartPlaying;
			}
		};
		
		public PlayButton(Context ctx) {
			super(ctx);
			setText("Start playing");
			setOnClickListener(clicker);
		}
	}
	
	class CallButton extends Button {
		boolean mStartCall = true;
		
		OnClickListener clicker = new OnClickListener() {
			public void onClick(View v) {
				
				// because having a separate 'onCall' fx is ridiculous
				if (mStartCall) {
					startCall();
					setText("Stop call");
				} else {
					stopCall();
					setText("Start call");
				}
				mStartCall = !mStartCall;
			}
		};
		
		public CallButton(Context ctx) {
			super(ctx);
			setText("Start call");
			setOnClickListener(clicker);
		}
	}

	private void startCall() {
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:7328010802"));
		startActivity(callIntent);
	}
	
	private void stopCall() {
		
	}
	
	public AudioRecordTest() {
		mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
		mFileName += "/audiorecordtest.aac";
	}

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		LinearLayout ll = new LinearLayout(this);
		mRecordButton = new RecordButton(this);
		ll.addView(mRecordButton,
				new LinearLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT,
						0));
		mPlayButton = new PlayButton(this);
		ll.addView(mPlayButton,
				new LinearLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT,
						0));
		mCallButton = new CallButton(this);
		ll.addView(mCallButton,
				new LinearLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT,
						0));
				
		setContentView(ll);
		
		//super.onCreate(icicle);
		//setContentView(R.layout.activity_audio_record_test);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if (mRecorder != null) {
			mRecorder.release();
			mRecorder = null;
		}
		
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.audio_record_test, menu);
		return true;
	}

}
