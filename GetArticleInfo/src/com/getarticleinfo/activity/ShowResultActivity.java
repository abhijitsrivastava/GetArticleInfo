package com.getarticleinfo.activity;

import org.json.JSONObject;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.android.getarticleinfo.R;
import com.getarticleinfo.model.Constants;
import com.getarticleinfo.utils.GetDetailTask;
import com.getarticleinfo.utils.LoadImageFromUrlTask;
import com.getarticleinfo.utils.PublishOnTimeLineTask;
import com.getarticleinfo.utils.Utils;
import com.github.barcodeeye.scan.CaptureQRCodeActivity;
import com.google.android.glass.media.Sounds;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import com.google.android.glass.view.WindowUtils;

public class ShowResultActivity extends Activity {

	private GestureDetector mGestureDetector;
	private AudioManager mAudioManager;
	private String productId;

	private View progress;

	private ImageView imageViewProduct;
	private TextView textViewArticleNumber;
	private TextView textViewManufacturer;
	private TextView textViewName;
	private TextView textViewAvailabilityInStore;
	private TextView textViewAvailabilityInWareHouse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		dLog("OnCreate");
		super.onCreate(savedInstanceState);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_show_result);

		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mGestureDetector = createGestureDetector(this);

		progress = findViewById(R.id.progress);

		imageViewProduct = (ImageView) findViewById(R.id.imageViewProduct);

		textViewArticleNumber = (TextView) findViewById(R.id.textViewArticleNumber);
		textViewManufacturer = (TextView) findViewById(R.id.textViewManufacturer);
		textViewName = (TextView) findViewById(R.id.textViewName);
		textViewAvailabilityInStore = (TextView) findViewById(R.id.textViewAvailabilityInStore);
		textViewAvailabilityInWareHouse = (TextView) findViewById(R.id.textViewAvailabilityInWareHouse);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			productId = bundle.getString("product_id");
		}

		String outletId = Utils.getStringPreferences(ShowResultActivity.this,
				Constants.KEY_STORE_ID);
		String storeWebServiceIp = Utils.getStringPreferences(
				ShowResultActivity.this, Constants.KEY_STORE_WEB_SERVICE_IP);
		
		dLog("complete url : " + Constants.SERVICE_ID_PRODUCT + Constants.OUTLET_ID
				+ outletId + Constants.STORE_WEB_SERVICE_IP
				+ storeWebServiceIp + Constants.PRODUCT_ID + productId);
		
		new GetDetailTask(ShowResultActivity.this)
		.execute(Constants.SERVICE_ID_PRODUCT + Constants.OUTLET_ID
				+ outletId + Constants.STORE_WEB_SERVICE_IP
				+ storeWebServiceIp + Constants.PRODUCT_ID + productId);

		// showScannedCode(ScannedCode);
	}

	/**
	 * Function used to display Scanned QRCode data.
	 * 
	 * @param scannedCode
	 */
	private void showScannedCode(String scannedCode) {
		// TODO Auto-generated method stub
		TextView textViewManufacturer;
		textViewManufacturer = (TextView) findViewById(R.id.textViewArticleNumber);
		textViewManufacturer.setText(scannedCode);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		dLog("onStart");
		super.onStart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		dLog("onResume");
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		dLog("onPause");
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		dLog("onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		dLog("onDestroy");
		super.onDestroy();
	}

	@Override
	public boolean onCreatePanelMenu(int featureId, Menu menu) {
		if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
			menu.addSubMenu(0, 1, Menu.NONE,
					getResources().getString(R.string.get_more_info));
			menu.addSubMenu(0, 2, Menu.NONE,
					getResources().getString(R.string.search_next));
			menu.addSubMenu(0, 3, Menu.NONE,
					getResources().getString(R.string.change_store));
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
			case 1: // Get More Info : start GetMoreInfoActivity
				intent = new Intent(this, GetMoreInfoActivity.class);
				intent.putExtra("product_id", productId);
				startActivity(intent);
				finish();
				break;
			case 2: // Scan Next : start GetVoiceInputActivity
				intent = new Intent(this, GetVoiceInputActivity.class);
				startActivity(intent);
				finish();
				break;
			case 3: // Change Store : open SelectStoreActivity
				intent = new Intent(this, SelectStoreActivity.class);
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
				getResources().getString(R.string.get_more_info));
		menu.addSubMenu(0, 2, Menu.NONE,
				getResources().getString(R.string.search_next));
		menu.addSubMenu(0, 3, Menu.NONE,
				getResources().getString(R.string.change_store));
		menu.addSubMenu(0, 4, Menu.NONE,
				getResources().getString(R.string.close_app));
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		mAudioManager.playSoundEffect(Sounds.TAP);
		Intent intent = null;

		switch (item.getItemId()) {
		case 1: // Get More Info : start GetMoreInfoActivity
			intent = new Intent(this, GetMoreInfoActivity.class);
			intent.putExtra("product_id", productId);
			startActivity(intent);
			finish();
			break;
		case 2: // Scan Next : start GetVoiceInputActivity
			intent = new Intent(this, GetVoiceInputActivity.class);
			startActivity(intent);
			finish();
			break;
		case 3:  // Change Store : open SelectStoreActivity
			intent = new Intent(this, SelectStoreActivity.class);
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

	public void onCompleteGetDetailTask(JSONObject productInfo) {
		progress.setVisibility(View.GONE);

		eLog("productInfo" + productInfo);

		if (productInfo != null) {
			// JSONObject productInfo = new JSONObject(result);
			String articleNumber = productInfo
					.optString(Constants.KEY_ARTIKELNUMMER);
			String manufacturer = productInfo
					.optString(Constants.KEY_HERSTELLER);
			String name = productInfo.optString(Constants.KEY_NAME);
			String availabilityInStore = productInfo
					.optString(Constants.KEY_STOCK6);
			String availabilityInWareHouse = productInfo
					.optString(Constants.KEY_STOCK2);
			String imageUrl = productInfo.optString(Constants.KEY_DOI);

			textViewArticleNumber.setText("ArticleNumber = " + articleNumber);
			textViewManufacturer.setText("Manufacturer = " + manufacturer);
			textViewName.setText("Name = " + name);
			textViewAvailabilityInStore.setText("Availability In Store = "
					+ availabilityInStore);
			textViewAvailabilityInWareHouse
					.setText("Availability In Warehouse = "
							+ availabilityInWareHouse);
			loadImageFromUrl(imageViewProduct, imageUrl);
			
			publishOnTimeline(productInfo);
		} else {
			textViewArticleNumber
					.setText("Sorry, no product info is available for this product.");
			dLog("Getting empty response");
		}
	}

	private void publishOnTimeline(JSONObject productInfo) {
		// TODO Auto-generated method stub
		String outletId = Utils.getStringPreferences(ShowResultActivity.this, Constants.KEY_STORE_NAME);
		String refreshToken = Utils.getStringPreferences(ShowResultActivity.this, Constants.KEY_REFRESH_TOKEN);
		if (!"".equalsIgnoreCase(refreshToken)) {
			new PublishOnTimeLineTask(productInfo,productId,outletId,refreshToken).execute();
		}
		
	}

	private void loadImageFromUrl(ImageView imageViewProduct, String imageUrl) {
		new LoadImageFromUrlTask(ShowResultActivity.this, imageViewProduct)
				.execute(imageUrl);
	}

	private void dLog(String message) {
		//Log.d(ShowResultActivity.this.getLocalClassName(), message);
	}

	private void eLog(String message) {
		//Log.e(ShowResultActivity.this.getLocalClassName(), message);
	}

}
