package com.getarticleinfo.activity;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.getarticleinfo.model.Constants;
import com.getarticleinfo.model.Store;
import com.getarticleinfo.session.AppSession;
import com.getarticleinfo.utils.GetStore;
import com.getarticleinfo.utils.Utils;
import com.github.barcodeeye.scan.CaptureQRCodeActivity;
import com.google.android.glass.app.Card;
import com.google.android.glass.media.Sounds;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

public class AppLaunchActivity extends Activity {

	private GestureDetector mGestureDetector;
	private AudioManager mAudioManager;
	
	private String outletId;
	private String storeWebServiceIp;
	private boolean isInitialSetup = true;
	
	private boolean timelineSetupDone;
	private boolean includeProductInTimeline;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		//setContentView(R.layout.activity_app_launch);

		outletId = Utils.getStringPreferences(this, Constants.KEY_STORE_ID);
		storeWebServiceIp = Utils.getStringPreferences(this,
				Constants.KEY_STORE_WEB_SERVICE_IP);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	
		timelineSetupDone  = Utils.getBooleanPreferences(this, Constants.KEY_TIMELINE_SETUP_DONE);
		includeProductInTimeline = Utils.getBooleanPreferences(this, Constants.KEY_INCLUDE_PRODUCT_IN_TIMELINE);

		dLog("timelineSetupDone : " + timelineSetupDone);
		dLog("includeProductInTimeline : " + includeProductInTimeline);

		dLog("outletId : " + outletId);
		dLog("storeWebServiceIp : " + storeWebServiceIp);

		if (timelineSetupDone == false) {

			mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			mGestureDetector = createGestureDetector(this);

			Card card = new Card(AppLaunchActivity.this);
			card.setText("Do you want to include product search in your timeline?\nYes/No");
			card.setFootnote("Tap to select");
			View cardView = card.getView();
			setContentView(cardView);
		} else {
			if (outletId.equals("") || storeWebServiceIp.equals("")) {
				isInitialSetup = true;
				new GetStoreTask().execute();
			} else {

				isInitialSetup = false;
				new GetStoreTask().execute();

				Intent intent = new Intent(AppLaunchActivity.this,
						GetVoiceInputActivity.class);
				startActivity(intent);
				finish();
			}
		}
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

	private class GetStoreTask extends AsyncTask<Void, Void, List<Store>> {

		@Override
		protected List<Store> doInBackground(Void... params) {
			List<Store> storesList = null;
			AppSession appSession = AppSession.getInstance();
			if (appSession.getStoresList() == null) {
				storesList = GetStore.parseJson();
				appSession.setStoresList(storesList);
			}else{
				storesList = appSession.getStoresList();
			}
			return storesList;
		}

		@Override
		protected void onPostExecute(List<Store> storesList) {
			super.onPostExecute(storesList);

			if (isInitialSetup) {
				Intent intent = new Intent(AppLaunchActivity.this,
						SelectStoreActivity.class);
				intent.putExtra(
						Constants.KEY_IS_COMING_FROM_APP_LAUNCH_ACTIVITY, true);
				startActivity(intent);
				finish();

			}
			
			// Parcelable StoresList
			//StoresList stores = new StoresList();

			// populate normal store list into parcelable store list
			//for (Store store : storesList) {
			//	stores.add(store);
			//}
			//Bundle b = new Bundle();
			//b.putParcelable("stores", stores); // Insert list in a Bundle object
			//intent.putExtras(b); // Insert the Bundle object in the Intent'
									// Extras

			/*
			 * String storesListAsString = ""; for (Store store : storesList) {
			 * storesListAsString = storesListAsString + store.getActive() + ","
			 * + store.getName() + "," + store.getNumber() + "," +
			 * store.getWwsEndpoint() + ","; }
			 * intent.putExtra(Constants.KEY_STORE_LIST, storesListAsString);
			 */

			 // Start Activity
		}

	}
	
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		dLog("onPrepareOptionsMenu");
		menu.add(0, 1, Menu.NONE, "Yes");
		menu.add(0, 2, Menu.NONE, "No");
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		mAudioManager.playSoundEffect(Sounds.TAP);
		dLog("onOptionsItemSelected");
		switch (item.getItemId()) {
		case 1: // yes : include product in timeline.
				Intent intent = new Intent(AppLaunchActivity.this, CaptureQRCodeActivity.class);
				intent.putExtra(Constants.KEY_IS_COMING_FOR_ACCESS_N_REFRESH_TOKEN, true);
				finish();
				startActivity(intent);
				break;
		case 2:// no : doesn't include product in timeline.
				Utils.saveBooleanPreferences(AppLaunchActivity.this, Constants.KEY_TIMELINE_SETUP_DONE, true);
				Utils.saveBooleanPreferences(AppLaunchActivity.this, Constants.KEY_INCLUDE_PRODUCT_IN_TIMELINE, false);
				onResume();
				break;
		default:
			break;
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
					showToast("Swipe left/right to scroll");
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
		//Log.d(AppLaunchActivity.this.getLocalClassName(), message);
	}

	private void eLog(String message) {
		//Log.e(AppLaunchActivity.this.getLocalClassName(), message);
	}
	
	private void showToast(final String message) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(AppLaunchActivity.this, message, Toast.LENGTH_LONG)
						.show();
			}
		});
	}

}
