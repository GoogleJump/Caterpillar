package com.google.appengine.demos.guestbook;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.demos.guestbook.JSON.JSONException;
import com.google.appengine.demos.guestbook.JSON.JSONObject;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

/**
 * 
 parameters: tripKey
 */
public class GetTripFromKeyServlet extends HttpServlet {

	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	ImagesService imagesService = ImagesServiceFactory.getImagesService();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		String tripKeyString = req.getParameter("tripKey");
		if (tripKeyString == null) {
			resp.sendRedirect("/signin.jsp");
		}

		Entity trip = null;
		try {
			trip = datastore.get(KeyFactory.stringToKey(tripKeyString));
		} catch (EntityNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// get trips as json file
		JSONObject json = new JSONObject();
		JSONObject tripjson;
		try {
				tripjson = new JSONObject();

				tripjson.put("title", (String) trip.getProperty("title"));
				tripjson.put("description",
						(String) trip.getProperty("description"));
				tripjson.put("key",
						(String) KeyFactory.keyToString(trip.getKey()));
				tripjson.put("departDate", (String) trip.getProperty("depDate"));
				tripjson.put("returnDate", (String) trip.getProperty("retDate"));
				tripjson.put("location", (String) trip.getProperty("location"));
				tripjson.put("tags", (String) trip.getProperty("tags"));
				tripjson.put("longitude", trip.getProperty("longitude"));
				tripjson.put("latitude", trip.getProperty("latitude"));
				json.put("trip", tripjson);
				
		} catch (JSONException e) {
			System.out.println("json exception with trips");
			e.printStackTrace();
		}
		resp.setContentType("application/json");
		resp.getWriter().write(json.toString());
		System.out.println(json.toString());
	}
}