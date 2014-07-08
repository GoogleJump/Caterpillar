package com.travellog;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.travellog.entryendpoint.Entryendpoint;
import com.travellog.tripendpoint.Tripendpoint;
import com.travellog.tripendpoint.model.Trip;

import android.os.AsyncTask;
import android.util.Log;

import com.travellog.entryendpoint.model.EntryendpointEntry;

public class AddNewEntryTask extends AsyncTask<Object, Map<String, List<String>>, Map<String, List<String>>> {

	@Override
	protected Map<String, List<String>> doInBackground(Object... params) {
		
		Entryendpoint.Builder endpointBuilder = new Entryendpoint.Builder(
				AndroidHttp.newCompatibleTransport(),
				new JacksonFactory(),
				new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) { }
				});

		Entryendpoint endpoint = CloudEndpointUtils.updateBuilder(
				endpointBuilder).build();
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> blobKeys = new ArrayList<String>();
		try {
			EntryendpointEntry e = new EntryendpointEntry().setTitle((String) params[1]);
			e.setDescription((String) params[2]);
			e.setHashtags((List<String>) params[3]);
			e.setLocation((String) params[4]);
			e.setTripPoster((String) params[5]);
			e.setPoster((String) params[6]);
			DateTime d = new DateTime(new Date());
			e.setDateCreated(d);
			
			Map<String, List<String>> imgHashtags = (Map<String, List<String>>) params[7];
			for (Entry<String, List<String>> mapElem : imgHashtags.entrySet()) {
				HttpClient httpclient = new DefaultHttpClient();    
				HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000);
				HttpGet httpGet = new HttpGet("http://gjtravellog.appspot.com/blob/getuploadurl");

				org.apache.http.HttpResponse response = httpclient.execute(httpGet);
				HttpEntity urlEntity = response.getEntity();
				InputStream in = urlEntity.getContent();
				String str = "";
				while (true) {
					int ch = in.read();
					if (ch == -1)
						break;
					str += (char) ch;
				}

				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(str);

				FileBody fileBody  = new FileBody(new File(mapElem.getKey()));
				MultipartEntity reqEntity = new MultipartEntity();

				reqEntity.addPart("file", fileBody);

				httppost.setEntity(reqEntity);
				org.apache.http.HttpResponse responseTwo = httpClient.execute(httppost);
				HttpEntity urlEntity2 = responseTwo.getEntity(); //Response will be returned by "uploaded" servlet in JSON format
				InputStream in2 = urlEntity2.getContent();
				String str2 = "";
				while (true) {
					int ch = in2.read();
					if (ch == -1)
						break;
					str2 += (char) ch;
				}
				map.put(str2, mapElem.getValue());
			}
			e.setBlobkeys(blobKeys);
			
			EntryendpointEntry result = endpoint.insertEntry(e).execute();
			String id = result.getKey().getId() + "";
			List<String> keyList = new ArrayList<String>();
			keyList.add(id);
			
			map.put("entrykey", keyList);
			
			
		} 
		catch (ClientProtocolException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	protected Map<String, List<String>> doInBackground(Map<String, List<String>> input) {
		return input;
	}
	
	protected void onPostExecute(Map<String, List<String>> input) {
		
//		for (Entry<String, List<String>> e : input.entrySet()) {
//			Log.v("blobkey", e.getKey());
//			Log.v("hashes", e.getValue().toString());
//		}
	}



}
