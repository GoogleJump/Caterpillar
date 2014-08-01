package com.travellog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.appengine.api.datastore.AdminDatastoreService.KeyBuilder;
import com.travellog.tripendpoint.Tripendpoint;
import com.travellog.tripendpoint.model.Key;
import com.travellog.tripendpoint.model.Trip;

public class AddNewTripTask extends AsyncTask {

	@Override
	protected Object doInBackground(Object... arg0) {
		
		Tripendpoint.Builder endpointBuilder = new Tripendpoint.Builder(
				AndroidHttp.newCompatibleTransport(),
				new JacksonFactory(),
				new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) { }
				});

		Tripendpoint endpoint = CloudEndpointUtils.updateBuilder(
				endpointBuilder).build();
		
		try {
			Trip t = new Trip().setHashtags(new ArrayList<String>());
			t.setOwner((String) arg0[1]);
			List<String> posters = new ArrayList<String>();
			posters.add((String) arg0[1]);
			t.setPosters(posters);
			t.setViewers(posters);
			t.setTitle((String) arg0[2]);
			t.setDescription((String) arg0[3]);
			t.setLocation((String) arg0[4]);
			DateTime d = new DateTime(new Date());
			t.setDateCreated(d);
			t.setDepartDate(d);
			t.setReturnDate(d);
			
			Log.w("myApp", "trying to work");
			Trip result = endpoint.insertTrip(t).execute();
//			endpoint.insertUser(u).execute();
			Log.w("myApp", "works");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		Toast.makeText((Context) arg0[0], "this is my Toast message!!! =)",
//				   Toast.LENGTH_LONG).show();
		return arg0;
	}

}
