package com.travellog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.travellog.tripendpoint.Tripendpoint;
import com.travellog.tripendpoint.model.CollectionResponseTrip;

public class GetTripByOwnerKeyTask extends AsyncTask {

	@Override
	protected Object doInBackground(Object... params) {

		Tripendpoint.Builder endpointBuilder = new Tripendpoint.Builder(
				AndroidHttp.newCompatibleTransport(),
				new JacksonFactory(),
				new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) { }
				});
//		endpointBuilder.setApplicationName("gjtravellog");
		Tripendpoint endpoint = CloudEndpointUtils.updateBuilder(
				endpointBuilder).build();
		List<com.travellog.tripendpoint.model.Trip> trips = null;
		try {
			Log.w("trip ", endpoint.listTrip((String) params[1]).setPrettyPrint(true).execute().toString());
			CollectionResponseTrip tripCollection = endpoint.listTrip((String) params[1]).setPrettyPrint(true).execute().setItems(trips);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	/*	List<Trip> tripsObject = new ArrayList<Trip>(); //java trip, not entity
		for(int i = 0; i < trips.size(); i++) {
			String title = trips.get(i).getTitle();
			tripsObject.get(i).setTitle(title);
			
			String description = trips.get(i).getDescription();
			tripsObject.get(i).setDescription(description);
			
			String location = trips.get(i).getLocation();
			tripsObject.get(i).setLocation(location);
			
			//TODO dates
		//	DateTime depDate = trips.get(i).getDepartDate();
			//tripsObject.get(i).setDepartDate(depDate.toString());
			
			List<String> tags = trips.get(i).getHashtags();
			tripsObject.get(i).setTags(tags);
		}*/
		return null;
		//return tripsObject;
	}


}
