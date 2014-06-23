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
import org.apache.http.params.HttpConnectionParams;

import com.google.api.client.http.HttpResponse;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class UploadImageToBlobstoreTask extends AsyncTask {

    @Override
    protected Object doInBackground(Object... params) {
        try {
            HttpClient httpclient = new DefaultHttpClient();    
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000);
            HttpGet httpGet = new HttpGet("http://travellogwebapp.appspot.com/blob/getuploadurl");
            
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

            FileBody fileBody  = new FileBody(new File((String) params[1]));
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
            
//          JSONObject resultJson;
//          try {
//              resultJson = new JSONObject(str);
//              String blobKey = resultJson.getString("blobKey");
//              String servingUrl = resultJson.getString("servingUrl");
//          } catch (JSONException e) {
//              // TODO Auto-generated catch block
//              e.printStackTrace();
//          }
            
            Log.v("blobkey", str2);

            
        } 
        catch (ClientProtocolException e) {
            e.printStackTrace();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }

}
