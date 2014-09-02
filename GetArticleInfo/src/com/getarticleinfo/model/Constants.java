package com.getarticleinfo.model;

public class Constants {
	
	public static final String CLIENT_ID = "1066507831079-ns6ocfah56a5f07rt7581c2sq314taul.apps.googleusercontent.com";
	public static final String CLIENT_SECRET = "72ghUtkucOS8LMOQUHcUrKdh";

	public static final String SERVICE_URL = "http://85.214.232.99:9090/middleware/MWServlet";
	
	public static final String POST_DATA = "platform=android&serviceID=getProductDetail&outletId=1000&applicationName=PRODUCTADVISOR&cacheid=&userName=admin&storeWebServicesIp=10.10.10.10%3A8080&channel=rc&appver=1.0.25&appID=PAMM&productId=";
	public static final String POST_DATA_FOR_COMPETITOR = "platform=android&serviceID=getCompetitorPrices_NL&outletId=1000&applicationName=PRODUCTADVISOR&cacheid=&userName=admin&storeWebServicesIp=10.10.10.10%3A8080&channel=rc&appver=1.0.25&appID=PAMM&productId=";
	
	public static final String POST_DATA_FOR_STORES = "platform=android&serviceID=showStores&channel=rc&appID=AdminBackend&key=&myID=2";
	
	// show product info data
	public static final String SERVICE_ID_PRODUCT = "platform=android&serviceID=getProductDetail";
	
	public static final String OUTLET_ID = "&outletId=";
	public static final String STORE_WEB_SERVICE_IP = "&applicationName=PRODUCTADVISOR&cacheid=&userName=admin&storeWebServicesIp=";
	public static final String PRODUCT_ID = "&channel=rc&appver=1.0.25&appID=PAMM&productId=";

	// show product competitor data
	public static final String SERVICE_ID_COMPETITOR = "platform=android&serviceID=getProductDetail&outletId=";
	
	// product keys
	public static final String KEY_NAME = "NAME";
	public static final String KEY_STOCK6 = "STOCK6";
	public static final String KEY_HERSTELLER = "HERSTELLER";
	public static final String KEY_ARTIKELNUMMER = "ARTIKELNUMMER";
	public static final String KEY_DOI = "DOI";
	public static final String KEY_STOCK2 = "STOCK2";
	
	// outlet keys
	public static final String KEY_STORE_ID = "store_id";
	public static final String KEY_STORE_WEB_SERVICE_IP = "store_web_service_ip";
	
	public static final String KEY_IS_COMING_FROM_APP_LAUNCH_ACTIVITY = "isComingFromAppLaunchActivity";
	
	public static final String KEY_STORE_NAME = "store_name";
	public static final String KEY_STORE_LIST = "store_list";
	
	public static final String KEY_TIMELINE_SETUP_DONE = "timeline_setup_done";
	public static final String KEY_INCLUDE_PRODUCT_IN_TIMELINE = "include_product_in_timeline";
	
	public static final String KEY_IS_COMING_FOR_ACCESS_N_REFRESH_TOKEN = "is_coming_for_access_n_refresh_token";
	
	public static final String KEY_ACCESS_TOKEN = "access_token";
	public static final String KEY_REFRESH_TOKEN = "refresh_token";
	
}