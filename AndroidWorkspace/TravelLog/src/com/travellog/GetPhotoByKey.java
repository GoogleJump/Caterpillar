package com.travellog;

import java.io.IOException;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import com.travellog.photoendpoint.Photoendpoint;
import com.travellog.photoendpoint.Photoendpoint.GetPhoto;
import com.travellog.photoendpoint.model.Photo;

import android.os.AsyncTask;
import android.util.Log;

public class GetPhotoByKey extends AsyncTask {

    @Override
    protected Object doInBackground(Object... params) {
        // TODO Auto-generated method stub
        Photoendpoint.Builder endpointBuilder = new Photoendpoint.Builder(
                AndroidHttp.newCompatibleTransport(),
                new JacksonFactory(),
                new HttpRequestInitializer() {
                    public void initialize(HttpRequest httpRequest) { }
                });

        Photoendpoint endpoint = CloudEndpointUtils.updateBuilder(
                endpointBuilder).build();
        
        try {
        	Photo p = endpoint.getPhoto((Long) params[1]).execute();
          //  Log.w("user", endpoint.listPhoto((String) params[1]).setPrettyPrint(true).execute().toString());
        	Log.w("photo", p.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
    }

}
