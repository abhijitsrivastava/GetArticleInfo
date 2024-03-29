/**
 * 
 */
package com.getarticleinfo.timeline;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

import com.getarticleinfo.model.Constants;

/**
 * @author qainfotech
 * 
 */
public class ServerConnection {
	// http://localhost:8080/RESTfulExample/json/product/post

	/**
	 * Method to get Non-expired access_token form the refresh_token.
	 * 
	 * @param postInput
	 * @param requesturl
	 * @return
	 */
	public static String postHttpsUrlConnectionForAccessToken(String refreshToken) {

		InputStream in = null;
		
		String access_token = null;

		/**
		 * The response received from the server
		 */
		String mResponse = "";

		try {
			URL urlObject = new URL(
					"https://accounts.google.com/o/oauth2/token");

			HttpsURLConnection con = (HttpsURLConnection) urlObject
					.openConnection();

			con.setRequestMethod("POST");
			con.addRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			con.setDoOutput(true);
			con.connect();

			// Now write the parameters:
			// Client ID, Client Secret, Refresh Token, and what kind of Grant
			OutputStreamWriter out = new OutputStreamWriter(
					con.getOutputStream());

			String urlParams = "client_id="
					+ Constants.CLIENT_ID
					+ "&client_secret="
					+ Constants.CLIENT_SECRET
					+ "&refresh_token="
					+ refreshToken
					+ "&grant_type=refresh_token";

			out.write(urlParams);

			out.flush();
			out.close();

			int serverCode = con.getResponseCode();

			//Log.i("ServerConnection", "HttpURLConnection response: "
			//		+ serverCode);

			if (serverCode != HttpURLConnection.HTTP_OK) {
				// Response is bad
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(con.getErrorStream()));

				for (String line = reader.readLine(); line != null; line = reader
						.readLine()) {
					//Log.e("ServerConnection", line);
				}
			} else {
				// Response is good

				in = new BufferedInputStream(con.getInputStream());
				mResponse = getStringFromInputStream(in);

				JSONObject jsonObject = new JSONObject(mResponse);
				access_token = jsonObject.optString("access_token");
				
				return access_token;

				/*if (access_token != null) {
					Utils.saveStringPreferences(activity,
							Constants.KEY_ACCESS_TOKEN, access_token);
				}

				Log.i("ServerConnection", "Response: \"\n" + mResponse + "\n\"");*/
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return access_token;
	}

	/**
	 * Method returns String from input stream.
	 * 
	 * @param is
	 * @return jsonString
	 */
	public static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

}
