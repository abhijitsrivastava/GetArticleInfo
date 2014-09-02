package com.getarticleinfo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.getarticleinfo.R;
import com.getarticleinfo.model.Competitor;
import com.getarticleinfo.model.Constants;
import com.getarticleinfo.utils.GetMoreInfoTask;
import com.getarticleinfo.utils.Utils;
import com.github.barcodeeye.scan.CaptureQRCodeActivity;
import com.google.android.glass.app.Card;
import com.google.android.glass.media.Sounds;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

public class GetMoreInfoActivity extends Activity {

	private GestureDetector mGestureDetector;
	private AudioManager mAudioManager;
	private String productId;

	private List<Card> mCards;
	private CardScrollView mCardScrollView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		dLog("OnCreate");
		super.onCreate(savedInstanceState);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// setContentView(R.layout.activity_show_result);

		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mGestureDetector = createGestureDetector(this);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			productId = bundle.getString("product_id");
		}

		String outletId = Utils.getStringPreferences(GetMoreInfoActivity.this,
				Constants.KEY_STORE_ID);
		String storeWebServiceIp = Utils.getStringPreferences(
				GetMoreInfoActivity.this, Constants.KEY_STORE_WEB_SERVICE_IP);

		new GetMoreInfoTask(GetMoreInfoActivity.this)
				.execute(Constants.SERVICE_ID_COMPETITOR + Constants.OUTLET_ID
						+ outletId + Constants.STORE_WEB_SERVICE_IP
						+ storeWebServiceIp + Constants.PRODUCT_ID + productId);
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
					getResources().getString(R.string.search_next));
			menu.addSubMenu(0, 2, Menu.NONE,
					getResources().getString(R.string.change_store));
			menu.addSubMenu(0, 3, Menu.NONE,
					getResources().getString(R.string.go_back));
			menu.addSubMenu(0, 4, Menu.NONE,
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
			case 1:// Scan Next : start CaptureQrCodeActivity
				intent = new Intent(this, GetVoiceInputActivity.class);
				startActivity(intent);
				finish();
				break;
			case 2: // Change Store : start SelectOutletActivity
				intent = new Intent(this, SelectStoreActivity.class);
				startActivity(intent);
				finish();
				break;
			case 3: // Go Back : start ShowResultActivity
				intent = new Intent(this, ShowResultActivity.class);
				intent.putExtra("product_id", productId);
				startActivity(intent);
				finish();
				break;
			case 4: // Close : close the app
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
		menu.addSubMenu(0, 1, Menu.NONE,
				getResources().getString(R.string.search_next));
		menu.addSubMenu(0, 2, Menu.NONE,
				getResources().getString(R.string.change_store));
		menu.addSubMenu(0, 3, Menu.NONE,
				getResources().getString(R.string.go_back));
		menu.addSubMenu(0, 4, Menu.NONE,
				getResources().getString(R.string.close_app));
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		mAudioManager.playSoundEffect(Sounds.TAP);
		Intent intent = null;

		switch (item.getItemId()) {
		case 1:// Scan Next : start CaptureQrCodeActivity
			intent = new Intent(this, GetVoiceInputActivity.class);
			startActivity(intent);
			finish();
			break;
		case 2: // Change Store : start SelectOutletActivity
			intent = new Intent(this, SelectStoreActivity.class);
			intent.putExtra("product_id", productId);
			startActivity(intent);
			finish();
			break;
		case 3: // Go Back : start ShowResultActivity
			intent = new Intent(this, ShowResultActivity.class);
			intent.putExtra("product_id", productId);
			startActivity(intent);
			finish();
			break;
		case 4: // Close : close the app
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

	public void onCompleteGetDetailTask(List<Competitor> competitorsList) {
		// progress.setVisibility(View.GONE);

		eLog("productInfo" + competitorsList);
		Card card;
		if (competitorsList != null) {
			mCards = new ArrayList<Card>();
			for (Competitor competitor : competitorsList) {
				card = new Card(this);
				card.setText(competitor.getCompetitorName() + "\n"
						+ competitor.getCompetitorPrice());
				mCards.add(card);

			}
			mCardScrollView = new CardScrollView(this);
			GetMoreInfoCardScrollAdapter adapter = new GetMoreInfoCardScrollAdapter();
			mCardScrollView.setAdapter(adapter);
			mCardScrollView.activate();
			setContentView(mCardScrollView);
			Toast.makeText(this, "Scroll left/right to explore more",
					Toast.LENGTH_SHORT).show();
		} else {
			card = new Card(this);
			card.setText("No competitor prices found.");
			View cardView = card.getView();
			setContentView(cardView);
			dLog("Getting empty response");
		}
	}

	private void dLog(String message) {
		//Log.d(GetMoreInfoActivity.this.getLocalClassName(), message);
	}

	private void eLog(String message) {
		//Log.e(GetMoreInfoActivity.this.getLocalClassName(), message);
	}

	private class GetMoreInfoCardScrollAdapter extends CardScrollAdapter {

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

}
