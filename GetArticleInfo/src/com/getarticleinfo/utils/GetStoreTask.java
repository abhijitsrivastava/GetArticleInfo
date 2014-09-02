package com.getarticleinfo.utils;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;

import com.getarticleinfo.model.Store;

public class GetStoreTask extends AsyncTask<String, Void, List<Store>> {

	private Activity activity;

	public GetStoreTask(Activity getMoreInfoActivity) {
		activity = getMoreInfoActivity;
	}

	@Override
	protected List<Store> doInBackground(String... params) {
		List<Store> storesList = null;
		storesList = GetStore.parseJson();
		return storesList;

	}

	@Override
	protected void onPostExecute(List<Store> storesList) {
		super.onPostExecute(storesList);
		//((SelectStoreActivity) activity).onCompleteGetStoreTask(storesList);
	}
}
