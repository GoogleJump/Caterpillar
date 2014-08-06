package com.travellog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.appengine.api.datastore.KeyFactory;
import com.travellog.photoendpoint.Photoendpoint;

import com.travellog.photoendpoint.model.Photo;
import android.os.AsyncTask;
import android.util.Log;

public class AddNewPhotoTask extends AsyncTask {

    @Override
    protected Object doInBackground(Object... params) {
        Photoendpoint.Builder endpointBuilder = new Photoendpoint.Builder(
                AndroidHttp.newCompatibleTransport(),
                new JacksonFactory(),
                new HttpRequestInitializer() {
                    public void initialize(HttpRequest httpRequest) { }
                });

        Photoendpoint endpoint = CloudEndpointUtils.updateBuilder(
                endpointBuilder).build();
        
        try {
            Photo p = new Photo().setBlobkey((String) params[1]);
            p.setTitle((String) params[2]);
            p.setDescription((String) params[3]);
            
            Log.w("myApp", "trying to work");
            Photo result = endpoint.insertPhoto(p).execute();
            Log.w("myApp", "works " + result.getKey());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return params[0];
    }

}
