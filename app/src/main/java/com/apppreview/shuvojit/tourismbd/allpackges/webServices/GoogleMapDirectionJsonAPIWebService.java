package com.apppreview.shuvojit.tourismbd.allpackges.webServices;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class GoogleMapDirectionJsonAPIWebService {
	private String webUrl;
	private HttpClient httpClient;
	private HttpResponse httpResponse;
	private HttpEntity httpEntity;
	private HttpPost httpPost;


	public GoogleMapDirectionJsonAPIWebService(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getJsonData() {
		String response = null;
		httpPost = null;
		httpClient = new DefaultHttpClient();
		httpPost = new HttpPost(webUrl);
		httpResponse = null;
		httpEntity = null;

		try {
			httpResponse = httpClient.execute(httpPost);
			int status = httpResponse.getStatusLine().getStatusCode();
			if (status == 200) {
				Log.e(getClass().getName(), "Data found");
				httpEntity = httpResponse.getEntity();
				response = EntityUtils.toString(httpEntity);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}
