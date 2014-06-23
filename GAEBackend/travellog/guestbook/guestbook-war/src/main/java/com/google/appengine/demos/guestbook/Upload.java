package com.google.appengine.demos.guestbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class Upload extends HttpServlet {
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		System.out.println("uploading entries/photos");
		
		String[] photoTitles;
		photoTitles = req.getParameterValues("photoTitle");
		if (photoTitles != null) {
			for (int i = 0; i < photoTitles.length; i++) {
				System.out.println("title: " + photoTitles[i]);
			}
		}

		String[] photoDescriptions;
		photoDescriptions = req.getParameterValues("photoDescription");
		if (photoDescriptions != null) {
			for (int i = 0; i < photoTitles.length; i++) {
				System.out.println("title: " + photoTitles[i]);
			}
		}
		// get parameters for entry
		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		System.out.println("number of blobs uploaded: " + blobs.size());
		List<BlobKey> blobKeys = blobs.get("fileUpload");
		String title = req.getParameter("title");
		String description = req.getParameter("description");
		String tags = req.getParameter("tags");
		String location = req.getParameter("location");
		Key entryKey = KeyFactory
				.createKey("Entry", System.currentTimeMillis());
		String poster = req.getParameter("userKey"); // user that posted the
														// trip/entry
		// Key posterKey = KeyFactory.stringToKey(poster);
		String posterTrip = req.getParameter("tripKey"); // trip that this entry
															// // belongs to
		// Key posterTripKey = KeyFactory.stringToKey(posterTrip);
		Date date = new Date();
		String photoTitle = req.getParameter("photoTitle");
		System.out.println("photo title is: " + photoTitle);
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		// make photos from blobs
		List<String> photos = new ArrayList<String>(); // list of photo
														// entities' key strings
		for (int i = 0; i < blobs.size(); i++) {
		/*	Key photoKey = KeyFactory.createKey("Photo",
					System.currentTimeMillis());
			Entity photo = new Entity("Photo", photoKey);*/
			Entity photo = new Entity("Photo");
			photo.setProperty("blobKey", blobKeys.get(i));
			//TODO: check if this works (if the order uploaded = order info)
			//if not, upload file name and match them
			//either way, this will work for just one photo uploaded
			if(i < photoTitles.length) photo.setProperty("title", photoTitles[i]);
			else photo.setProperty("title", "");
			if(i < photoDescriptions.length)photo.setProperty("description", photoDescriptions[i]); 
			else photo.setProperty("description", "");
			datastore.put(photo);
			String photoKeyString = KeyFactory.keyToString(photo.getKey());
			photos.add(photoKeyString);
		}
	       
		// create entry
		Entity entry = new Entity("Entry", entryKey);
		entry.setProperty("title", title);
		entry.setProperty("description", description);
		entry.setProperty("location", location);
		entry.setProperty("poster", poster); // string
		entry.setProperty("tags", tags);
		entry.setProperty("dateCreated", date);
		entry.setProperty("tripPoster", posterTrip); // string
		entry.setProperty("photos", photos); // list of photo entity key strings
		// entry.setProperty("videoKey", value); TODO: videos

		datastore.put(entry);

		res.sendRedirect("/tripview.jsp?entryKey="
				+ KeyFactory.keyToString(entry.getKey()) + "&tripKey="
				+ posterTrip);
		
		/*
		 * if (blobKeys == null || blobKeys.size() == 0) {
		 * System.out.println("null or 0 blobkey redirect");
		 * res.sendRedirect("/tripview.jsp?userKey="+
		 * KeyFactory.keyToString(entry.getKey()) +"&tripKey=" + posterTrip); }
		 * else { System.out.println("nonnull blobkey redirect");
		 * res.sendRedirect("/tripview.jsp?userKey="+
		 * KeyFactory.keyToString(entry.getKey()) +"&tripKey=" + posterTrip); }
		 */
	}
}