package com.getarticleinfo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.getarticleinfo.R;
import com.github.barcodeeye.scan.CaptureQRCodeActivity;
import com.google.android.glass.app.Card;
import com.google.android.glass.media.Sounds;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import com.google.android.glass.view.WindowUtils;

public class SelectFlowActivity extends Activity {

	private GestureDetector mGestureDetector;
	private AudioManager mAudioManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		dLog("OnCreate");
		super.onCreate(savedInstanceState);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		Card welcomeCard = new Card(this);
		welcomeCard.setText("Select An Option:\nScan\nChange Store\nClose");
		View welcomeCardView = welcomeCard.getView();

		setContentView(welcomeCardView);

		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mGestureDetector = createGestureDetector(this);

	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onCreatePanelMenu(int featureId, Menu menu) {
		if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
			menu.addSubMenu(0, 1, Menu.NONE,
					getResources().getString(R.string.search));
			menu.addSubMenu(0, 2, Menu.NONE,
					getResources().getString(R.string.change_store));
			menu.addSubMenu(0, 3, Menu.NONE,
					getResources().getString(R.string.close_app));
			return true;
		}
		// Pass through to super to setup touch menu.
		return super.onCreatePanelMenu(featureId, menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		Intent intent = null;
		if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
			switch (item.getItemId()) {
			case 1:// Scan Next : start GetVoiceInputActivity
				intent = new Intent(this, GetVoiceInputActivity.class);
				startActivity(intent);
				finish();
				break;
			case 2: // Change Store : start SelectOutletActivity
				intent = new Intent(this, SelectStoreActivity.class);
				startActivity(intent);
				finish();
				break;
			case 3: // Close : close the app
				finish();
				break;
			default:
				eLog("Voice Command Catches Wrong Option");
				return true;
			}
			return true;
		}
		// Good practice to pass through to super if not handled
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		menu.addSubMenu(0, 1, Menu.NONE, getResources()
				.getString(R.string.search));
		menu.addSubMenu(0, 2, Menu.NONE,
				getResources().getString(R.string.change_store));
		menu.addSubMenu(0, 3, Menu.NONE,
				getResources().getString(R.string.close_app));
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		mAudioManager.playSoundEffect(Sounds.TAP);
		Intent intent = null;

		switch (item.getItemId()) {
		case 1:// Scan Next : start GetVoiceInputActivity
			intent = new Intent(this, GetVoiceInputActivity.class);
			startActivity(intent);
			finish();
			break;
		case 2: // Change Store : start SelectOutletActivity
			intent = new Intent(this, SelectStoreActivity.class);
			startActivity(intent);
			finish();
			break;
		case 3: // Close : close the app
			finish();
			break;
		default:
			eLog("Voice Command Catches Wrong Option");
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/*
	 * Send generic motion events to the gesture detector
	 */
	@Override
	public boolean onGenericMotionEvent(MotionEvent event) {
		if (mGestureDetector != null) {
			return mGestureDetector.onMotionEvent(event);
		}
		return false;
	}

	private GestureDetector createGestureDetector(final Context context) {

		GestureDetector gestureDetector = new GestureDetector(context);
		// Create a base listener for generic gestures
		gestureDetector.setBaseListener(new GestureDetector.BaseListener() {
			@Override
			public boolean onGesture(Gesture gesture) {
				if (gesture == Gesture.TAP) {
					dLog("Gesture.TAP");
					mAudioManager.playSoundEffect(Sounds.TAP);
					openOptionsMenu();
					return true;
				} else if (gesture == Gesture.SWIPE_DOWN) {
					dLog("Gesture.SWIPE_DOWN");
					mAudioManager.playSoundEffect(Sounds.DISMISSED);
					return false;
				}
				return false;
			}
		});

		gestureDetector.setFingerListener(new GestureDetector.FingerListener() {
			@Override
			public void onFingerCountChanged(int previousCount, int currentCount) {
				// do something on finger count changes
			}
		});

		gestureDetector.setScrollListener(new GestureDetector.ScrollListener() {
			@Override
			public boolean onScroll(float displacement, float delta,
					float velocity) {
				// do something on scrolling
				return true;
			}
		});
		return gestureDetector;

	}

	private void dLog(String message) {
		// Log.d(SelectFlowActivity.this.getLocalClassName(), message);
	}

	private void eLog(String message) {
		// Log.e(SelectFlowActivity.this.getLocalClassName(), message);
	}

}
