package com.getarticleinfo.session;

import java.util.List;

import com.getarticleinfo.model.Store;

public class AppSession {

	private static AppSession appSessionInstance = null;

	private List<Store> storesList = null;

	protected AppSession() {
		// Exists only to defeat instantiation.
	}

	public static AppSession getInstance() {
		if (appSessionInstance == null) {
			appSessionInstance = new AppSession();
		}
		return appSessionInstance;
	}

	public List<Store> getStoresList() {
		return storesList;
	}

	public void setStoresList(List<Store> storesList) {
		this.storesList = storesList;
	}

}
