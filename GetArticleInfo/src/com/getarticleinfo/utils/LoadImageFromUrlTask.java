package com.getarticleinfo.utils;

import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

public class LoadImageFromUrlTask  extends AsyncTask<String, Void, Bitmap> {

	Bitmap bitmap = null;
	Activity activity;
	ImageView imageViewProduct;
	
	public LoadImageFromUrlTask(Activity activity,ImageView imageViewProduct){
		this.activity = activity;
		this.imageViewProduct = imageViewProduct;
	}

	protected Bitmap doInBackground(String... args) {
		try {
			bitmap = BitmapFactory.decodeStream((InputStream) new URL(
					args[0]).getContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	protected void onPostExecute(Bitmap image) {
		if (image != null) {
			imageViewProduct.setImageBitmap(image);
		} else {
			Toast.makeText(activity, "No Preview available",
					Toast.LENGTH_SHORT).show();
		}
	}
}
