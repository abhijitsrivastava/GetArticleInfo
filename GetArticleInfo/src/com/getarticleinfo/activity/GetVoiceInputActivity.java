package com.getarticleinfo.activity;

import java.util.List;

import com.google.android.glass.app.Card;
import com.google.android.glass.media.Sounds;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class GetVoiceInputActivity extends Activity {

	private SpeechRecognizer mSpeechRecognizer = null;
	private static final int SPEECH_REQUEST = 0;
	private int speechFlag = 11;
	
	private GestureDetector mGestureDetector;
	private AudioManager mAudioManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		dLog("OnCreate");
		super.onCreate(savedInstanceState);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mGestureDetector = createGestureDetector(this);

		initializeSpeechRecognizer();
		displaySpeechRecognizer();

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		dLog("OnStart");
		super.onStart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		dLog("OnResume");
		super.onResume();
		
		Card card = new Card(this);
		card.setText("Tap to Search\nor\nSwipe down to close.");
		View view = card.getView();
		setContentView(view);
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		dLog("OnPause");
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		dLog("OnStop");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		dLog("onDestroy");
		destroySpeechRecognizer();
		super.onDestroy();
	}

	private void initializeSpeechRecognizer() {

		if (mSpeechRecognizer == null) {

			mSpeechRecognizer = SpeechRecognizer
					.createSpeechRecognizer(getApplicationContext());

			Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			// intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt");
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
					RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		}
	}

	private void displaySpeechRecognizer() {

		runOnUiThread(new Runnable() {

			@Override
			public void run() {

				String promptText = "";
				switch (speechFlag) {

				case 11: // After speaking result "Email"
					promptText = "Speak article number";
					speechFlag = 12;
					Intent intent = new Intent(
							RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
					intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
							RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
					intent.putExtra(RecognizerIntent.EXTRA_PROMPT, promptText);
					startActivityForResult(intent, SPEECH_REQUEST);
					break;

				case 2: // After speaking result "Drive"
					promptText = "";
					showToast("Service not available");
					break;

				case 10:
					promptText = "";
					showToast("Invalid voice command");
					speechFlag = 11;
					break;
				default:
					break;
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == SPEECH_REQUEST && resultCode == RESULT_OK) {

			List<String> results = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			String spokenText = results.get(0).toLowerCase();

			if (speechFlag == 12 && !spokenText.equals("")) {

				// Got spokenText
				dLog("got the spokenText: "+ spokenText);
				
				varifyInputForNumberOnly(spokenText);

				//convertTextToNumber(spokenText);

			} else {

				// Error flag
				speechFlag = 10;
			}
		} else {

			speechFlag = 11;
			//destroySpeechRecognizer();
		}
	}

	private void varifyInputForNumberOnly(String spokenText) {
		// TODO Auto-generated method stub
		
		spokenText = spokenText.trim();
		spokenText = spokenText.replaceAll("\\s","");
		dLog("spokenText after trim : " + spokenText);
		
		if (spokenText.matches("[0-9]+")) {
			dLog("inside if");
			Intent intent = new Intent(GetVoiceInputActivity.this,
					ShowResultActivity.class);
			intent.putExtra("product_id", spokenText);

			finish();
			startActivity(intent);
		}else{
			dLog("inside else");
			showToast("Invalide Article Number, Please speak again.");
			recreate();
		}
		
	}

	private void destroySpeechRecognizer() {

		if (mSpeechRecognizer != null) {

			mSpeechRecognizer.stopListening();
			mSpeechRecognizer.destroy();
			mSpeechRecognizer = null;
		}
	}

	private void convertTextToNumber(String spokenText) {

		String articleId = null;
		int wordLength = 0;
		String word = null;

		while (spokenText.length() > 0) {

			wordLength = spokenText.indexOf(" ");
			word = spokenText.substring(0, wordLength);
			word = word.toLowerCase();

			spokenText = spokenText.substring(wordLength, spokenText.length());

			switch (word) {
			case "zero":
				articleId = articleId.concat("0");
				break;
			case "one":
				articleId = articleId.concat("1");
				break;
			case "two":
				articleId = articleId.concat("2");
				break;
			case "three":
				articleId = articleId.concat("3");
				break;
			case "four":
				articleId = articleId.concat("4");
				break;
			case "five":
				articleId = articleId.concat("5");
				break;
			case "six":
				articleId = articleId.concat("6");
				break;
			case "seven":
				articleId = articleId.concat("7");
				break;
			case "eight":
				articleId = articleId.concat("8");
				break;
			case "nine":
				articleId = articleId.concat("9");
				break;
			default:
				showToast("Incorrect Acticle Number, Please Try Again.");
				displaySpeechRecognizer();
				break;
			}

			Intent intent = new Intent(GetVoiceInputActivity.this,
					ShowResultActivity.class);
			intent.putExtra("product_id", articleId);

			finish();
			startActivity(intent);

		}
	}

	private void showToast(final String message) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(GetVoiceInputActivity.this, message,
						Toast.LENGTH_LONG).show();
			}
		});
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
					displaySpeechRecognizer();
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
		//Log.d(GetVoiceInputActivity.this.getLocalClassName(), message);
	}

	private void eLog(String message) {
		//Log.e(GetVoiceInputActivity.this.getLocalClassName(), message);
	}
}
