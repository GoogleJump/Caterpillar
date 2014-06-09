package com.google.appengine.demos.guestbook;

import java.io.IOException;
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

		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		System.out.println("number of blobs uploaded: " + blobs.size());
		List<BlobKey> blobKeys = blobs.get("fileUpload");

		String title = req.getParameter("title");
		String description = req.getParameter("description");
		String tags = req.getParameter("tags");
		String location = req.getParameter("location");
		// TODO: don't need to get entryKey as parameter anymore, get rid of it
		// everywhere
		// (leaving for now because need to get rid of it in a bunch of places
		// and make sure still works)
		// unlike the other inserts, get entryKey as a parameter (because of
		// blob id issues)
		String entryKeyString = req.getParameter("entryKey");
		Key entryKey = KeyFactory.stringToKey(entryKeyString);
		String poster = req.getParameter("userKey"); // user that posted the
														// trip/entry
		Key posterKey = KeyFactory.stringToKey(poster);
		String posterTrip = req.getParameter("tripKey"); // trip that this entry
															// belongs to
		Key posterTripKey = KeyFactory.stringToKey(posterTrip);

		Date date = new Date();

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
		entry.setProperty("imageKeys", blobKeys);
		// entry.setProperty("videoKey", value); TODO: videos

		datastore.put(entry);

		res.sendRedirect("/tripview.jsp?userKey="
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