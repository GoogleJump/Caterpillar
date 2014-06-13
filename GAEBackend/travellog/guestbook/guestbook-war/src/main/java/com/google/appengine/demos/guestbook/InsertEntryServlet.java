package com.google.appengine.demos.guestbook;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.FetchOptions;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import java.util.List;
//NOT USING THIS.  USING UPLOAD INSTEAD
public class InsertEntryServlet extends HttpServlet {

	// private BlobstoreService blobstoreService =
	// BlobstoreServiceFactory.getBlobstoreService();

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		/*
		 * a) title - String b) description - String c) hashtags - String
		 * collection d) location - String for city and country (or can be 2
		 * properties for long and lat coordinates - easier to pin on a map with
		 * this info) e) poster - int (user key) f) image key - blob key
		 * collection (of images on blob store) (??? Should there be
		 * descriptions associated with images and videos?) g) video key - blob
		 * key collection (of videos on blob store) h) key - Key i) dateCreated
		 * - Date j) tripPoster - Key of thrip it belongs to
		 */

		// System.out.println("Hello insert entry servlet");
		BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
		System.out.println("Blobkey sent was: " + blobKey);

		String title = req.getParameter("title");
		String description = req.getParameter("description");
		String tags = req.getParameter("tags");
		String location = req.getParameter("location");
		// unlike the other inserts, get entryKey as a parameter (because of
		// blob id issues)
		//String entryKeyString = req.getParameter("entryKey");
		// System.out.println("entry key: "+entryKeyString);
		//Key entryKey = KeyFactory.stringToKey(entryKeyString);
		Key entryKey = KeyFactory.createKey("Entry", System.currentTimeMillis());
		String poster = req.getParameter("userKey"); // user that posted the
														// trip/entry
		// System.out.println("poster(user) key: " + poster);

		Key posterKey = KeyFactory.stringToKey(poster);
		String posterTrip = req.getParameter("tripKey"); // trip that this entry
															// belongs to
		// System.out.println("trip key: " + posterTrip);
		Key posterTripKey = KeyFactory.stringToKey(posterTrip);

		Date date = new Date();
		// Key entryKey = KeyFactory.createKey("Entry",
		// System.currentTimeMillis()+"");

		// Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		// System.out.println("how many blobs were uploaded?" + blobs.size());
		// List<BlobKey> blobKey = blobs.get(entryKey);
		// System.out.println("does blob key work: " + blobKey.getKeyString());

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Entity entry = new Entity("Entry", entryKey);
		entry.setProperty("title", title);
		entry.setProperty("description", description);
		entry.setProperty("location", location);
		entry.setProperty("poster", posterKey);
		entry.setProperty("tags", tags);
		entry.setProperty("dateCreated", date);
		entry.setProperty("tripPoster", posterTripKey);
		// entry.setProperty("imageKey", blobKey.get(0)); //TODO: multiple
		// images (collection of blob keys)
		// entry.setProperty("videoKey", value); TODO: videos

		// DatastoreService datastore =
		// DatastoreServiceFactory.getDatastoreService();
		datastore.put(entry);

		resp.sendRedirect("/tripview.jsp?userKey="
				+ KeyFactory.keyToString(entry.getKey()) + "&tripKey="
				+ posterTrip);

	}
}