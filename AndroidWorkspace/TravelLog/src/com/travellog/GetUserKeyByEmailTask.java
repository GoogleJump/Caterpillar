package com.travellog;

import java.io.IOException;
import java.util.List;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;
import com.travellog.userendpoint.Userendpoint;
import com.travellog.userendpoint.model.CollectionResponseUser;
import com.travellog.userendpoint.model.User;

import android.content.Entity;
import android.os.AsyncTask;
import android.util.Log;

public class GetUserKeyByEmailTask extends AsyncTask {

	@Override
	protected Object doInBackground(Object... params) {
//		Query q = new Query("User");
//		q.addProjection(new PropertyProjection("key", Key.class));
//		
//		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//		List<com.google.appengine.api.datastore.Entity> projTests = datastore.prepare(q).asList(null);
//		
//		for (com.google.appengine.api.datastore.Entity t : projTests) {
//			Log.w("query", t.toString());
//		}
		Userendpoint.Builder endpointBuilder = new Userendpoint.Builder(
				AndroidHttp.newCompatibleTransport(),
				new JacksonFactory(),
				new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) { }
				});
//		endpointBuilder.setApplicationName("gjtravellog");
		Userendpoint endpoint = CloudEndpointUtils.updateBuilder(
				endpointBuilder).build();
		
		try {
			Log.w("user", endpoint.listUser((String) params[1]).setPrettyPrint(true).execute().toString());
			CollectionResponseUser users = endpoint.listUser((String) params[1]).setPrettyPrint(true).execute();
			if(users.size() > 0) {
			User user = users.getItems().get(0);
			if(user.getPassword().equals(params[2])) return true;
			else return false;
			}
			else {
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
