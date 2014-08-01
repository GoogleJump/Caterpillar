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

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * 
 * parameters: photoKey (string), title, description
 * deletes photo from entry and from blobstore
 *
 */
public class EditPhotoServlet extends HttpServlet {
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		String photoKey = req.getParameter("photoKey");
		String entryKey = req.getParameter("entryKey");
		String title = req.getParameter("title");
		String description = req.getParameter("description");
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
	
		//delete Photo from datastore
		Entity photo = null;
		try {
			photo = datastore.get(KeyFactory.stringToKey(photoKey));
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		
		photo.setProperty("title", title);
		photo.setProperty("description", description);
		datastore.put(photo);
		
		resp.sendRedirect("/editentry.jsp?entryKey="+entryKey);
		
		
		
	}
}
