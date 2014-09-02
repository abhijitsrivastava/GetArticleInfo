package com.getarticleinfo.utils;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;

import com.getarticleinfo.activity.GetMoreInfoActivity;
import com.getarticleinfo.model.Competitor;

public class GetMoreInfoTask extends AsyncTask<String, Void, List<Competitor>> {

	private Activity activity;

	public GetMoreInfoTask(Activity getMoreInfoActivity) {
		activity = getMoreInfoActivity;
	}

	@Override
	protected List<Competitor> doInBackground(String... params) {
		List<Competitor> competitors = null;
		competitors = GetMoreInfo.parseJson(params[0]);
		return competitors;

	}

	@Override
	protected void onPostExecute(List<Competitor> competitorsList) {
		super.onPostExecute(competitorsList);
		((GetMoreInfoActivity) activity)
				.onCompleteGetDetailTask(competitorsList);
	}

}
