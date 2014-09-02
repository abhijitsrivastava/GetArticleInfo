package com.getarticleinfo.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.getarticleinfo.model.Constants;

public class GetDetail {
	public static final String KEY_NAME = "NAME";
	public static final String KEY_STOCK6 = "STOCK6";

	public static final String KEY_HERSTELLER = "HERSTELLER";
	public static final String KEY_ARTIKELNUMMER = "ARTIKELNUMMER";
	public static final String KEY_DOI = "DOI";
	public static final String KEY_STOCK2 = "STOCK2";
	
	public static void main(String a[]) {
		//parseJson();
	}
		
	/**
	 * Method takes the data for post call and the request url.
	 * 
	 * @param postInput
	 * @param requestUrl
	 * @return JSONObject
	 */
	public static JSONObject postHttpUrlConnection(String postInput,
			String requestUrl) {

		JSONObject responseObject = null;
		InputStream in = null;
		HttpURLConnection conn = null;
		String jsonString = null;
		try {
			URL url = new URL(requestUrl);

			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			conn.setRequestProperty("Content-Length", "" + postInput.length());
			// String input = "{\"qty\":100,\"name\":\"iPad 4\"}";
			String input = postInput;

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			in = new BufferedInputStream(conn.getInputStream());
			jsonString = getStringFromInputStream(in);
			//System.out.println("JSON Response : " + jsonString);
			responseObject = new JSONObject(jsonString);

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (RuntimeException e) {
			jsonString = "{\"message\":\"Internal server error\",\"statusCode\":\"err001\"}";
			try {
				responseObject = new JSONObject(jsonString);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseObject;

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
	
	public static JSONObject parseJson(String postInput) {
		JSONObject jsonResponse = GetDetail.postHttpUrlConnection(postInput,Constants.SERVICE_URL);
		try {
			JSONArray productDescriptionsJsonArray = jsonResponse.getJSONArray("productDescriptions");
			
			if (productDescriptionsJsonArray.length() != 0) {
				JSONObject productDescriptionsJsonObject = productDescriptionsJsonArray
						.getJSONObject(0);
				return productDescriptionsJsonObject;
			}		
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
