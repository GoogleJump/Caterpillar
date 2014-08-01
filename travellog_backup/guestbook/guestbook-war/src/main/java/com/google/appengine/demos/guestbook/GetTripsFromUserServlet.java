package com.google.appengine.demos.guestbook;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.google.appengine.demos.guestbook.JSON.JSONArray;
import com.google.appengine.demos.guestbook.JSON.JSONException;
import com.google.appengine.demos.guestbook.JSON.JSONObject;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import java.util.List;

/**
 * 
 parameters: user key, limit (number of trips to get)
 */
public class GetTripsFromUserServlet extends HttpServlet {

	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	ImagesService imagesService = ImagesServiceFactory.getImagesService();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		String userKeyString = req.getParameter("userKey");
		String limitstring = req.getParameter("limit");
		if (limitstring == null)
			limitstring = "10";
		int limit = Integer.parseInt(limitstring);

		if (userKeyString == null) {
			resp.sendRedirect("/signin.jsp");
		}
		Query query = new Query("Trip").addFilter("owner",
				Query.FilterOperator.EQUAL, userKeyString).addSort(
				"dateCreated", Query.SortDirection.DESCENDING);
		List<Entity> trips = datastore.prepare(query).asList(
				FetchOptions.Builder.withLimit(limit));

		if (trips.isEmpty()) {
			System.out.println("no trips");
		}

		// get trips as json file
		JSONObject json = new JSONObject();
		JSONArray tripsjson = new JSONArray();
		JSONObject tripjson;
		int i = 0;
		try {
			for (Entity trip : trips) {
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
				tripjson.put("userKey", userKeyString);
				tripsjson.put(tripjson);
				i++;
			}
			json.put("trips", tripsjson);
		} catch (JSONException e) {
			System.out.println("json exception with trips");
			e.printStackTrace();
		}
		resp.setContentType("application/json");
		resp.getWriter().write(json.toString());
		System.out.println(json.toString());
	}
}