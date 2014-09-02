package com.getarticleinfo.utils;

import org.json.JSONObject;

import com.getarticleinfo.timeline.ServerConnection;
import com.getarticleinfo.timeline.Test;

import android.os.AsyncTask;

public class PublishOnTimeLineTask extends AsyncTask<Void, Void, Void> {

	private JSONObject productObject = null;
	private String productId;
	private String outletId;
	private String refreshToken;
	private String accessToken;

	public PublishOnTimeLineTask(JSONObject productObject, String productId,
			String outletId, String refreshToken) {
		this.productObject = productObject;
		this.productId = productId;
		this.outletId = outletId;
		this.refreshToken = refreshToken;
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		// code to get access token from refresh token
		getAccessTokenFromRefreshToken();

		new Test().publishOnTimeLine(productObject, productId, outletId,
				accessToken);
		return null;
	}

	private void getAccessTokenFromRefreshToken() {
		// TODO Auto-generated method stub
		accessToken = ServerConnection
				.postHttpsUrlConnectionForAccessToken(refreshToken);
	}

}
