package com.getarticleinfo.utils;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class Utils {

	public static void saveStringPreferences(Context context, String key,
			String value) {
		SharedPreferences sPrefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sPrefs.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static void deleteStringPreferences(Context context, String key) {
		SharedPreferences sPrefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sPrefs.edit();
		editor.remove(key);
		editor.commit();
	}

	public static String getStringPreferences(Context context, String key) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		String savedPref = sharedPreferences.getString(key, "");
		return savedPref;
	}
	
	
	public static void saveBooleanPreferences(Context context, String key,
			Boolean value) {
		SharedPreferences sPrefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sPrefs.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static void deleteBooleanPreferences(Context context, String key) {
		SharedPreferences sPrefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sPrefs.edit();
		editor.remove(key);
		editor.commit();
	}

	public static boolean getBooleanPreferences(Context context, String key) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		boolean savedPref = sharedPreferences.getBoolean(key, false);
		return savedPref;
	}
	

	public static HttpResponse makeRequest(String url, JSONObject params)
			throws ClientProtocolException, IOException {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);

		// passes the results to a string builder/entity
		StringEntity se = new StringEntity(params.toString());

		// sets the post request as the resulting string
		httppost.setEntity(se);

		// sets a request header so the page receving the request
		// will know what to do with it
		httppost.setHeader("Accept", "application/json");
		httppost.setHeader("Content-type", "application/json");

		// Execute HTTP Post Request
		return httpclient.execute(httppost);
	}

	public static void dLog(Activity activity, String message) {
		//Log.d(activity.getLocalClassName(), message);
	}

	public static void eLog(Activity activity, String message) {
		//Log.e(activity.getLocalClassName(), message);
	}
}
