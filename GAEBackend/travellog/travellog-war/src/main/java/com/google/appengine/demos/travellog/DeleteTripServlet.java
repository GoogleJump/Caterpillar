package com.google.appengine.demos.guestbook;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * 
 * parameters: tripKey, userKey (strings)
 * deletes trip from datastore
 * redirects to homepage
 *
 */
public class DeleteTripServlet extends HttpServlet {
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		String tripKey = req.getParameter("tripKey");
		String userKey = req.getParameter("userKey");
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		datastore.delete(KeyFactory.stringToKey(tripKey));
		resp.sendRedirect("/homepage.jsp?userKey="
				+ userKey);
	}
}
