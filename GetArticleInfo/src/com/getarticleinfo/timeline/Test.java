package com.getarticleinfo.timeline;

import java.io.IOException;
import java.util.List;

import org.json.JSONObject;

import com.getarticleinfo.model.Constants;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.mirror.model.TimelineItem;
import com.google.api.services.mirror.model.TimelineListResponse;

public class Test {

	public static void main(String s[]) {/*
		GoogleCredential credential = new GoogleCredential();

		credential
				.setAccessToken("ya29.aAB3DTu1C2M3TSEAAAAsDh8Y0-mh9Ya1ajwDG3kfF0Km_ZNa8Xtef6lgbga7qloVpCLRER5aLJhM4r5O4Rg");
		TimelineItem timelineItem = new TimelineItem();

		try {

			TimelineListResponse timelineListResponse = MirrorClient.listItems(
					credential, 15);

			List<TimelineItem> list = timelineListResponse.getItems();

			boolean productNotExistInTimeLine = true;
			for (TimelineItem timeline : list) {
				if (timeline.getText().contains("productId")
						&& timeline.getText().contains("outletId")) {
					productNotExistInTimeLine = false;
				}
			}
			if (productNotExistInTimeLine) {

				timelineItem
						.setHtml("<article> <figure>   <img src=\"http://api.qrserver.com/v1/create-qr-code/?data=HelloWorld!&size=300x300\">"
								+ " </figure><section> "
								+ "<table class=\"text-minor no-border\"> <tbody> <tr> <td >"
								+ "Article Number = 1230140 </br>"
								+ " Manufacturer = SAMSUNG </br>"
								+ "Name = UE42F5000AWXXN </br>"
								+ "Availability Store = 10 </br>"
								+ "Availability Warehouse = 22</td> </tr> "
								+ "</tbody> </table> </section></article>");

				// timelineItem.setText("This \n is a new message from Nitesh");

				// timelineItem.setHtml("<Html><body><table width=\"80%\"><tr><td width=\"80%\"><center><h1>Product Info</h1></center></td></tr></table><table width=\"80%\"> <tr><td rowspan=\"6\"><img src=\"http://api.qrserver.com/v1/create-qr-code/?data=HelloWorld!&size=400x400\" alt=\"Red dot\"></td></tr><tr><td ><font size=\"5\">Article Number = 1230140</font></td></tr><tr><td ><font size=\"5\">Manufacturer = SAMSUNG</font> </td></tr><tr><td ><font size=\"5\">Name = UE42F5000AWXXN</font> </td></tr><tr><td ><font size=\"5\">Availability in Store = 22</font> </td></tr><tr><td ><font size=\"5\">Availability in Warehouse = 22</font></td></tr></table></body></html>");

				// timelineItem.setHtml("<Html><body><table width=\"80%\"><tr><td width=\"80%\"><center><h1>Product Info</h1></center></td></tr></table><table width=\"80%\"> <tr><td rowspan=\"6\"><img src=\"http://api.qrserver.com/v1/create-qr-code/?data=HelloWorld!&size=400x400\" alt=\"Red dot\"></td></tr><tr><td >Article Number = 1230140</td></tr><tr><td >Manufacturer = SAMSUNG </td></tr><tr><td >Name = UE42F5000AWXXN</td></tr><tr><td >Availability in Store = 22 </td></tr><tr><td >Availability in Warehouse = 22</td></tr></table></body></html>");

				// timelineItem.setHtml("<Html><body><table width=\"80%\"><tr><td width=\"80%\"><center><h1>Product Info</h1></center></td></tr></table><table width=\"80%\"> <tr><td rowspan=\"6\"><img src=\"http://api.qrserver.com/v1/create-qr-code/?data=HelloWorld!&size=400x400\" alt=\"Red dot\"></td></tr><tr><td ><h1>Article Number = 1230140</h1></td></tr><tr><td ><h1>Manufacturer = SAMSUNG </h1></td></tr><tr><td ><h1>Name = UE42F5000AWXXN</h1></td></tr><tr><td ><h1>Availability in Store = 22 </h1></td></tr><tr><td ><h1>Availability in Warehouse = 22</h1></td></tr></table></body></html>");

				
				 * URL url = new URL(
				 * "http://www.wired.com/images/productreviews/2008/09/samsung_ln26a450c1_lcd_tv_f.jpg"
				 * ); String contentType = "image/jpeg";
				 * MirrorClient.insertTimelineItem(credential, timelineItem,
				 * contentType, url.openStream());
				 
				MirrorClient.insertTimelineItem(credential, timelineItem);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	*/}

	public void publishOnTimeLine(JSONObject productInfo, String productId,
			String outletId, String accessToken) {

		String articleNumber = productInfo
				.optString(Constants.KEY_ARTIKELNUMMER);
		String manufacturer = productInfo.optString(Constants.KEY_HERSTELLER);
		String name = productInfo.optString(Constants.KEY_NAME);
		String availabilityInStore = productInfo
				.optString(Constants.KEY_STOCK6);
		String availabilityInWareHouse = productInfo
				.optString(Constants.KEY_STOCK2);
		String imageUrl = productInfo.optString(Constants.KEY_DOI);

		GoogleCredential credential = new GoogleCredential();

		credential.setAccessToken(accessToken);

		TimelineItem timelineItem = new TimelineItem();
		try {

			TimelineListResponse timelineListResponse = MirrorClient.listItems(
					credential, 15);

			List<TimelineItem> list = timelineListResponse.getItems();

			boolean productNotExistInTimeLine = true;
			for (TimelineItem timeline : list) {
				if (null!=timeline.getText() && timeline.getText().contains(productId)
						&& timeline.getText().contains(outletId)) {
					productNotExistInTimeLine = false;
				}
			}
			if (productNotExistInTimeLine) {

				timelineItem
						.setHtml("<article> <figure>   <img src="
								+ "\""
								+ imageUrl
								+ "\""
								+ ">"
								+ " </figure><section> "
								+ "<table class=\"text-minor no-border\"> <tbody> <tr> <td >"
								+ "Article Number = " + articleNumber
								+ " </br>" + "Manufacturer = " + manufacturer
								+ " </br>" + "Name = " + name + " </br>"
								+ "Availability Store = " + availabilityInStore
								+ " </br>" + "Availability Warehouse = "
								+ availabilityInWareHouse + "</td> </tr> "
								+ "</tbody> </table> </section></article>");
				timelineItem.setText(productId + ","+outletId);
				MirrorClient.insertTimelineItem(credential, timelineItem);
			}
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}

}
