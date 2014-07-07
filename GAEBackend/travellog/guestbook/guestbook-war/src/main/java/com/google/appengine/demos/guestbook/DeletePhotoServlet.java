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
 * parameters: entryKey, photoKey (strings)
 * deletes photo from entry and from blobstore
 *
 */
public class DeletePhotoServlet extends HttpServlet {
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		String entryKey = req.getParameter("entryKey");
		String photoKey = req.getParameter("photoKey");
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		BlobstoreService blobstoreService = BlobstoreServiceFactory
				.getBlobstoreService();
		
		//delete photo from entry's photolist
		Entity entry = null;
		try {
			entry = datastore.get(KeyFactory.stringToKey(entryKey));
		} catch (EntityNotFoundException e1) {
			e1.printStackTrace();
		}
		List<String> photoKeys = (List<String>) entry.getProperty("photos");
		photoKeys.remove(photoKey);
		
		//delete Photo from datastore
		Entity photo = null;
		try {
			photo = datastore.get(KeyFactory.stringToKey(photoKey));
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		
		BlobKey blobKey = (BlobKey) photo.getProperty("blobKey");
		datastore.delete(KeyFactory.stringToKey(photoKey));
		
		//delete blob from blobstore
		blobstoreService.delete(blobKey);
		
		
	}
}
