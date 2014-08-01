package com.google.appengine.demos.guestbook;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.FetchOptions;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import java.util.List;

import java.util.ArrayList;


/**
 * 
 * parameters: tripKey, title, description, location, departDate, retDate. tags
 * TODO: do we want to update dateCreated or no??
 * edits trip information, redirects to that trip's entries TODO: or do we stay at homepage?
 *
 */
public class EditTripServlet extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		// TODO: posters and viewers
	/*	String owner = req.getParameter("userKey"); 
		Key ownerKey = KeyFactory.stringToKey(owner);*/ //can't change owner of trip
		String tripKey = req.getParameter("tripKey");
		String title = req.getParameter("title");
		String description = req.getParameter("description");
		String location = req.getParameter("location");
		String depDate = req.getParameter("departDate");
		String retDate = req.getParameter("retDate");
		String latitude = req.getParameter("latitude");
		String longitude = req.getParameter("longitude");
		// if (depDate == null) depDate = new Date().toString();
		// if(retDate == null) retDate = new Date().toString();
		Date date = new Date();
		String tags = req.getParameter("tags");
		System.out.println(depDate + "________" + retDate);

		// This next stuff is not being used--must fix
		// format depart and return dates:
		Date departDate = new Date();
		Date returnDate = new Date();
		try {
			departDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
					Locale.ENGLISH).parse(depDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			returnDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
					Locale.ENGLISH).parse(retDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// create viewers and posters lists
		List<String> viewers = new ArrayList<String>();
		List<String> posters = new ArrayList<String>();
		//viewers.add(owner);
		//posters.add(owner);

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		// create trip entitiy - TODO: also make sure these are correct and
		// consistent
		Entity trip;
		System.out.println("edit trip servlet, about to get trip");
		try {
			trip = datastore.get(KeyFactory.stringToKey(tripKey));
			trip.setProperty("title", title);
			trip.setProperty("description", description);
			trip.setProperty("dateCreated", date);
			trip.setProperty("location", location);
			trip.setProperty("retDate", retDate); //these are strings. should be dates
			trip.setProperty("depDate", depDate);
			trip.setProperty("viewer", viewers);
			trip.setProperty("posters", posters);
			trip.setProperty("tags", tags);
			trip.setProperty("latitude", latitude);
			trip.setProperty("longitude", longitude);
			datastore.put(trip);
			System.out.println("updated trip");
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			System.out.println("trip not found");
		}
		

		// get the user that owns the post so that we can add friends/posters to
		// the list of viewers/posters
		// - haven't implemented friends yet so this is commented out
		/*
		 * Query ownerquery = new Query("User").addFilter("key",
		 * Query.FilterOperator.EQUAL, userKey); DatastoreService datastore =
		 * DatastoreServiceFactory.getDatastoreService(); Entity ownerUser =
		 * datastore
		 * .prepare(ownerquery).asList(FetchOptions.Builder.withLimit(1)
		 * ).get(0);
		 */

		resp.sendRedirect("/tripview.jsp?tripKey="
				+ tripKey); 

	}
}
