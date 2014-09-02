package com.getarticleinfo.utils;

import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;

import com.getarticleinfo.activity.ShowResultActivity;

public class GetDetailTask extends AsyncTask<String, Void, JSONObject> {

	private Activity activity;

	public GetDetailTask(ShowResultActivity showResultActivity) {
		activity = showResultActivity;
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		JSONObject jsonObject = null;
		jsonObject = GetDetail.parseJson(params[0]);
		return jsonObject;

	}

	@Override
	protected void onPostExecute(JSONObject response) {
		super.onPostExecute(response);
		((ShowResultActivity) activity).onCompleteGetDetailTask(response);
	}
}
