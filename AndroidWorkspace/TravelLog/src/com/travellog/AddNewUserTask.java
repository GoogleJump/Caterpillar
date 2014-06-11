package com.travellog;

import android.os.AsyncTask;
import java.io.IOException;
import java.util.Date;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;
import com.travellog.userendpoint.Userendpoint;
import com.travellog.userendpoint.model.Email;
import com.travellog.userendpoint.model.User;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.appengine.api.datastore.Key;

public class AddNewUserTask extends AsyncTask {
	//input is a String array.  [0] left blank
	//{ null, first name, last name, username, password, email } 
	@Override
	protected Object doInBackground(Object... arg0) {
		
		Userendpoint.Builder endpointBuilder = new Userendpoint.Builder(
				AndroidHttp.newCompatibleTransport(),
				new JacksonFactory(),
				new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) { }
				});
		Userendpoint endpoint = CloudEndpointUtils.updateBuilder(
				endpointBuilder).build();
		
		try {
		
			User u = new User().setFirstName((String) arg0[1]);
			u.setLastName((String) arg0[2]);
			DateTime d = new DateTime(new Date());
			u.setDateCreated(d);
			u.setUsername((String) arg0[3]);
			u.setPassword((String) arg0[4]);


			Email e = new Email();
			e.setEmail((String) arg0[5]);
			u.setEmail(e);          
			User result = endpoint.insertUser(u).execute();
			Log.w("myApp", "trying to work");
			endpoint.insertUser(u).execute();
			Log.w("myApp", "works");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		Toast.makeText((Context) arg0[0], "this is my Toast message!!! =)",
//				   Toast.LENGTH_LONG).show();
		return arg0;
	}

}
