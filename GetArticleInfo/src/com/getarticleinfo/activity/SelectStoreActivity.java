package com.getarticleinfo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.getarticleinfo.R;
import com.getarticleinfo.model.Constants;
import com.getarticleinfo.model.Store;
import com.getarticleinfo.model.StoresList;
import com.getarticleinfo.session.AppSession;
import com.getarticleinfo.utils.Utils;
import com.github.barcodeeye.scan.CaptureQRCodeActivity;
import com.google.android.glass.app.Card;
import com.google.android.glass.media.Sounds;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

public class SelectStoreActivity extends Activity {

	private GestureDetector mGestureDetector;
	private AudioManager mAudioManager;
	private boolean isComingFromAppLaunchActivity = false;

	private List<Card> mCards;
	private CardScrollView mCardScrollView;

	// private List<Store> storesList;
	private String stores;

	private String storesListAsString = null;
	private String[] storesListAsStringArray = null;
	private List<Store> storesListAsList = null;
	private Store store = null;

	private StoresList storesList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Call web service and display the list of available outlets
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		//getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_select_store);

		storesListAsList = new ArrayList<Store>();
	
		// onCompleteGetStoreTask(storesListAsList);

		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mGestureDetector = createGestureDetector(this);
		
		// Bundle b = getIntent().getExtras(); //Get the intent's extras

		// storesList = b.getParcelable("stores"); //get our list

		// convert parcelable list into nomal list

		/*
		 * for (Store store : storesList) { storesListAsList.add(store); }
		 */
		/*
		 * storesListAsString = b.getString(Constants.KEY_STORE_LIST); if
		 * (!"".equalsIgnoreCase(storesListAsString)) { storesListAsList = new
		 * ArrayList<Store>(); storesListAsStringArray =
		 * storesListAsString.split(",");
		 * 
		 * for (int i = 0; i < storesListAsStringArray.length; i++) { store =
		 * new Store(); store.setActive(storesListAsStringArray[i]);
		 * store.setName(storesListAsStringArray[i+1]);
		 * store.setNumber(storesListAsStringArray[i+2]);
		 * store.setWwsEndpoint(storesListAsStringArray[i+3]);
		 * storesListAsList.add(store); } }
		 */

		// new GetStoreTask(SelectStoreActivity.this).execute();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		storesListAsList = AppSession.getInstance().getStoresList();
		if(null == storesListAsList){
			showToast("Error Fetching Stores, Please Try Again Later.");
		}else{
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					openOptionsMenu();
					showToast("Swipe left/right to scroll");
				}
			}, 1000);
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

	/*@Override
	public boolean onCreatePanelMenu(int featureId, Menu menu) {
		if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
			if (storesList != null) {
				int i = 0;
				for (Store store : storesList) {
					menu.addSubMenu(0, i, Menu.NONE, store.getName());
					i++;
				}
			}
			return true;
		}
		// Pass through to super to setup touch menu.
		return super.onCreatePanelMenu(featureId, menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
			if (item.getTitle() != null
					|| "".equalsIgnoreCase(item.getTitle().toString())) {

				int storeSecquecneNo = item.getItemId();

				Utils.saveStringPreferences(this, Constants.KEY_STORE_NAME,
						item.getTitle().toString());

				Utils.saveStringPreferences(this, Constants.KEY_STORE_ID,
						storesListAsList.get(storeSecquecneNo).getNumber());
				Utils.saveStringPreferences(this,
						Constants.KEY_STORE_WEB_SERVICE_IP, storesListAsList
								.get(storeSecquecneNo).getWwsEndpoint());

				Bundle bundle = getIntent().getExtras();
				if (bundle != null) {
					isComingFromAppLaunchActivity = bundle
							.getBoolean(Constants.KEY_IS_COMING_FROM_APP_LAUNCH_ACTIVITY);
				}
				Intent intent = null;
				if (isComingFromAppLaunchActivity) {
					intent = new Intent(SelectStoreActivity.this,
							CaptureQRCodeActivity.class);
				} else {
					intent = new Intent(SelectStoreActivity.this,
							SelectFlowActivity.class);
				}
				startActivity(intent);
				finish();
			}

			return true;
		}
		// Good practice to pass through to super if not handled
		return super.onMenuItemSelected(featureId, item);
	}
*/
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		dLog("onPrepareOptionsMenu");
		if (storesListAsList != null) {
			dLog("storesList is not null");
			int i = 1;
			for (Store store : storesListAsList) {
				menu.add(0, i, Menu.NONE, store.getName());
				i++;
			}
		}else{
			menu.add(0, 1, Menu.NONE, "No Store");
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		mAudioManager.playSoundEffect(Sounds.TAP);
		dLog("onOptionsItemSelected");
		
		if (item.getTitle() != null
				|| "".equalsIgnoreCase(item.getTitle().toString())) {
			
			//closeOptionsMenu();

			Utils.saveStringPreferences(this, Constants.KEY_STORE_NAME, item
					.getTitle().toString());
			
			int storeSecquecneNo = item.getItemId() -1 ;
			
			Utils.saveStringPreferences(this, Constants.KEY_STORE_ID,
					storesListAsList.get(storeSecquecneNo).getNumber());
			Utils.saveStringPreferences(this,
					Constants.KEY_STORE_WEB_SERVICE_IP, storesListAsList
							.get(storeSecquecneNo).getWwsEndpoint());
			
			dLog("storeSecquecneNo : "+storeSecquecneNo);
			dLog("KEY_STORE_ID : "+storesListAsList.get(storeSecquecneNo).getNumber());
			dLog("KEY_STORE_WEB_SERVICE_IP : "+ Utils.getStringPreferences(this,
					Constants.KEY_STORE_WEB_SERVICE_IP));
			

			Bundle bundle = getIntent().getExtras();
			if (bundle != null) {
				isComingFromAppLaunchActivity = bundle
						.getBoolean(Constants.KEY_IS_COMING_FROM_APP_LAUNCH_ACTIVITY);
			}
			Intent intent = null;
			/*if (isComingFromAppLaunchActivity) {
				intent = new Intent(SelectStoreActivity.this,
						CaptureQRCodeActivity.class);
			} else {
				intent = new Intent(SelectStoreActivity.this,
						SelectFlowActivity.class);
			}*/
			intent = new Intent(SelectStoreActivity.this,
					GetVoiceInputActivity.class);
			startActivity(intent);
			finish();
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

	public void onCompleteGetStoreTask(List<Store> storesList) {
		eLog("productInfo" + storesList);

		// this.storesList = storesList;

		Card card;
		if (storesList != null) {
			mCards = new ArrayList<Card>();
			for (Store store : storesListAsList) {
				card = new Card(this);
				card.setText(store.getName());
				mCards.add(card);

			}
			mCardScrollView = new CardScrollView(this);
			SelectStoreCardScrollAdapter adapter = new SelectStoreCardScrollAdapter();
			mCardScrollView.setAdapter(adapter);
			mCardScrollView.activate();
			setContentView(mCardScrollView);
			Toast.makeText(this, "Scroll left/right to explore more",
					Toast.LENGTH_SHORT).show();
		} else {
			card = new Card(this);
			card.setText("There is no available store.\nPlease try again !!");
			View cardView = card.getView();
			setContentView(cardView);
			dLog("Getting empty response");
		}
	}

	private void dLog(String message) {
		//Log.d(SelectStoreActivity.this.getLocalClassName(), message);
	}

	private void eLog(String message) {
		//Log.e(SelectStoreActivity.this.getLocalClassName(), message);
	}

	private class SelectStoreCardScrollAdapter extends CardScrollAdapter {

		@Override
		public int getPosition(Object item) {
			return mCards.indexOf(item);
		}

		@Override
		public int getCount() {
			return mCards.size();
		}

		@Override
		public Object getItem(int position) {
			return mCards.get(position);
		}

		@Override
		public int getViewTypeCount() {
			return Card.getViewTypeCount();
		}

		@Override
		public int getItemViewType(int position) {
			return mCards.get(position).getItemViewType();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return mCards.get(position).getView(convertView, parent);
		}
	}

	private void showToast(final String message) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(SelectStoreActivity.this, message, Toast.LENGTH_LONG)
						.show();
			}
		});
	}
}
