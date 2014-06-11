package com.travellog;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.api.client.http.HttpResponse;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class UploadImageToBlobstoreTask extends AsyncTask {

	@Override
	protected Object doInBackground(Object... params) {
		HttpClient httpClient = new DefaultHttpClient();    
		//This will invoke "ImgUpload servlet           
		HttpGet httpGet = new HttpGet("http://10.0.0.2:8888/ImgUpload.java"); 
		org.apache.http.HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			HttpEntity urlEntity = response.getEntity();
			InputStream in = urlEntity.getContent();
			String str = "";
			while (true) {
			    int ch = in.read();
			    if (ch == -1)
			        break;
			    str += (char) ch;
			}
			
			Log.v("str", str);
			
//			HttpPost httppost = new HttpPost(str);
//			File f = new File((String) params[1]);
//			FileBody fileBody = new FileBody(f);
//			MultipartEntity reqEntity = new MultipartEntity();
//			reqEntity.addPart("file", fileBody);
//			httppost.setEntity(reqEntity);
//			response = httpClient.execute(httppost); //Here "uploaded" servlet is automatically       invoked
//			urlEntity = response.getEntity(); //Response will be returned by "uploaded" servlet in JSON format
//			in = urlEntity.getContent();
//			str = "";
//			while (true) {
//			    int ch = in.read();
//			    if (ch == -1)
//			        break;
//			    str += (char) ch;
//			}
//			JSONObject resultJson;
//			try {
//				resultJson = new JSONObject(str);
//				String blobKey = resultJson.getString("blobKey");
//				String servingUrl = resultJson.getString("servingUrl");
//				Log.v("blobkey", blobKey);
//				Log.v("servingurl", servingUrl);
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
