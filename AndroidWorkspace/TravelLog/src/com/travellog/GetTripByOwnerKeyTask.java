package com.travellog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.travellog.tripendpoint.Tripendpoint;
import com.travellog.tripendpoint.model.CollectionResponseTrip;

public class GetTripByOwnerKeyTask extends AsyncTask<Object, List<Trip>, List<Trip>> {
	List<Trip> tripsObject = new ArrayList<Trip>();
	@Override
	protected List<Trip> doInBackground(Object... params) {

		Tripendpoint.Builder endpointBuilder = new Tripendpoint.Builder(
				AndroidHttp.newCompatibleTransport(),
				new JacksonFactory(),
				new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) { }
				});
//		endpointBuilder.setApplicationName("gjtravellog");
		Tripendpoint endpoint = CloudEndpointUtils.updateBuilder(
				endpointBuilder).build();
		List<com.travellog.tripendpoint.model.Trip> trips = new ArrayList();
		try {
			System.out.println("about to log trip.  user key is" + params[1]);
			Log.w("trip ", endpoint.listTrip((String) params[1]).setPrettyPrint(true).execute().toString());
			
			CollectionResponseTrip tripCollection = endpoint.listTrip((String) params[1]).setPrettyPrint(true).execute();
			trips = tripCollection.getItems();
			tripsObject = new ArrayList<Trip>(); //java trip, not entity
			if(trips == null) { 
				System.out.println("null trips, returning empty list");
				return new ArrayList<Trip>();
			}
			for(int i = 0; i < trips.size(); i++) {
				Trip trip = new Trip();
				String title = trips.get(i).getTitle();
				trip.setTitle(title);
				
				String description = trips.get(i).getDescription();
				trip.setDescription(description);
				
				String location = trips.get(i).getLocation();
				trip.setLocation(location);
				
				//TODO dates
			//	DateTime depDate = trips.get(i).getDepartDate();
				//tripsObject.get(i).setDepartDate(depDate.toString());
				
				List<String> tags = trips.get(i).getHashtags();
				trip.setTags(tags);
				tripsObject.add(trip);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return tripsObject;
		//return null;
	}
protected void onPostExecute(List<Trip> tripsObject) {
	
	}

}
