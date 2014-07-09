package com.google.appengine.demos.guestbook;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
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
import java.util.Map;

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
 * TODO: do we want to update dateCreated or no?? it would be more like date
 * last updated edits entry information, redirects to that poster trip's entries
 * (or entry's page if we have separate page for that)
 * TODO: make edit/delete image more efficient
 */
public class EditEntryServlet extends HttpServlet {
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		// TODO: posters and viewers
		/*
		 * String owner = req.getParameter("userKey"); Key ownerKey =
		 * KeyFactory.stringToKey(owner);
		 */// can't change owner of trip
		String entryKey = req.getParameter("entryKey");
		String title = req.getParameter("title");
		String description = req.getParameter("description");
		String location = req.getParameter("location");
		Date date = new Date();
		String tags = req.getParameter("tags");
		System.out.println("title is:" + title);
		System.out.println("tags is:" + tags);
		System.out.println("description is:" + description);

		// create trip entitiy - TODO: also make sure these are correct and
		// consistent
		Entity entry = null;
		try {
			entry = datastore.get(KeyFactory.stringToKey(entryKey));
			if(entry == null) { entry = new Entity(System.currentTimeMillis()+""); }
			entry.setProperty("title", title);
			entry.setProperty("description", description);
			entry.setProperty("dateCreated", date);
			entry.setProperty("location", location);
			entry.setProperty("tags", tags);
			// poster trip and poster user won't change

			datastore.put(entry);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			System.out.println("entry not found");
		}

		// photo info
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

		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		// make photos from blobs
		List<String> photos = new ArrayList<String>(); // list of photo
														// entities' key strings

		if (blobs != null) {
			List<BlobKey> blobKeys = blobs.get("fileUpload");
			System.out.println("number blobs uploaded is:"+blobKeys.size());
			if (blobKeys != null) {
				createPhotos(photos, blobKeys, photoTitles, photoDescriptions);
				deletePhotos(entry);
				entry.setProperty("photos", photos);
			}
		}
		
		resp.sendRedirect("/entryPage.jsp?entryKey=" + entryKey);

	}
	
	//delete all photos from entity
	public void deletePhotos(Entity entry) {
		List<String> photos = (List<String>) entry.getProperty("photos");
		for(String photo : photos) {
			Entity photoEntity = null;
			try {
				photoEntity = datastore.get(KeyFactory.stringToKey(photo));
			} catch (EntityNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BlobKey blob = (BlobKey) photoEntity.getProperty("blobKey");
			//delete image from blobstore, delete photo from datastore
			blobstoreService.delete(blob);
			datastore.delete(photoEntity.getKey());
		}
	}

	/**
	 * adds photo keystrings to parameter photos
	 * 
	 * @param photos
	 * @param blobKeys
	 * @param photoTitles
	 * @param photoDescriptions
	 */
	public void createPhotos(List<String> photos, List<BlobKey> blobKeys,
		String[] photoTitles, String[] photoDescriptions) {
		int alreadyUploaded = photoTitles.length - blobKeys.size();
		System.out.println("already uploaded:" + alreadyUploaded);
		for (int i = 0; i < blobKeys.size(); i++) {
			System.out.println("photo create");
			Entity photo = new Entity("Photo");
			photo.setProperty("blobKey", blobKeys.get(i));
			if (photoTitles[i+alreadyUploaded] == null)
				photoTitles[i+alreadyUploaded] = "";
			if (i < photoTitles.length)
				photo.setProperty("title", photoTitles[i+alreadyUploaded]);
			else
				photo.setProperty("title", "");
			if (photoDescriptions[i+alreadyUploaded] == null)
				photoDescriptions[i+alreadyUploaded] = "";
			if (i < photoDescriptions.length)
				photo.setProperty("description", photoDescriptions[i+alreadyUploaded]);
			else
				photo.setProperty("description", "");
			datastore.put(photo);
			String photoKeyString = KeyFactory.keyToString(photo.getKey());
			photos.add(photoKeyString);
		}
	}
}
