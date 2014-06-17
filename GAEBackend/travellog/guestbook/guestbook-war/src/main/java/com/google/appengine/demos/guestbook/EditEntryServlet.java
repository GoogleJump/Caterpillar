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
 * TODO: do we want to update dateCreated or no?? it would be more like date last updated
 * edits entry information, redirects to that poster trip's entries (or entry's page if we
 * have separate page for that)
 *
 */
public class EditEntryServlet extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		// TODO: posters and viewers
	/*	String owner = req.getParameter("userKey"); 
		Key ownerKey = KeyFactory.stringToKey(owner);*/ //can't change owner of trip
		String entryKey = req.getParameter("entryKey");
		String title = req.getParameter("title");
		String description = req.getParameter("description");
		String location = req.getParameter("location");
		Date date = new Date();
		String tags = req.getParameter("tags");
		//String poster = req.getParameter("userKey"); // user that posted the
		// trip/entry -- this won't change upon edit
		// System.out.println("poster(user) key: " + poster);
		String posterTrip = req.getParameter("tripKey"); // trip that this entry
					// belongs to
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		// create trip entitiy - TODO: also make sure these are correct and
		// consistent
		Entity entry;
		try {
			entry = datastore.get(KeyFactory.stringToKey(entryKey));
			entry.setProperty("title", title);
			entry.setProperty("description", description);
			entry.setProperty("dateCreated", date);
			entry.setProperty("location", location);
			entry.setProperty("tags", tags);
			//poster trip and poster user won't change
			
			datastore.put(entry);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			System.out.println("entry not found");
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
				+ posterTrip); 

	}
}
