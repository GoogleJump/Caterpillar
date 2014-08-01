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
 * With a trip key as parameter, query entries for the trip for the most recent
 * entry From the most recent entry, get the first photo and serve it TODO:
 * scale and crop image
 */
public class GetTripImage extends HttpServlet {

	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	ImagesService imagesService = ImagesServiceFactory.getImagesService();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		String tripKeyString = req.getParameter("tripKey");
	//	Key tripKey = KeyFactory.stringToKey(req.getParameter("tripKey"));
		System.out.println("trip key is:" + req.getParameter("tripKey"));

		Query entryquery = new Query("Entry").addFilter("tripPoster",
				Query.FilterOperator.EQUAL, tripKeyString).addSort("dateCreated",
				Query.SortDirection.DESCENDING);

		List<Entity> entriesInTrip = datastore.prepare(entryquery).asList(
				FetchOptions.Builder.withLimit(1));

		if (entriesInTrip.size() != 0) {
			List<String> photos = (List<String>) entriesInTrip.get(0)
					.getProperty("photos");
			System.out.println("number of photos is:" + photos.size());
			if (photos != null && photos.size() != 0) {
				String photoKey = photos.get(0);
				System.out.println("photoKey is: " + photoKey);
				Entity photoEntity = null;
				try {
					photoEntity = datastore.get(KeyFactory
							.stringToKey(photoKey));
				} catch (EntityNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("photo entity was not found");
					e.printStackTrace();
				}
				BlobKey imageBlobKey = (BlobKey) photoEntity
						.getProperty("blobKey");
				// Image image =
				// ImagesServiceFactory.makeImageFromBlob(imageBlobKey);
				String imgurl = imagesService.getServingUrl(imageBlobKey, 251,
						false); // biggest side is 251, resized, not cropped
				// System.out.println("url is" + imgurl);
				// Transform resize = ImagesServiceFactory.makeResize(200, 300);
				// Image resizedImage = imagesService.applyTransform(resize,
				// image);
				resp.sendRedirect(imgurl);
				// blobstoreService.serve(image, resp); //for now, serve like
				// this, but instead uuse image api b/c more efficient and need
				// cropping
			} else {
				System.out.println("no images in entry");
				// TODO, check all the entries and find the first image before
				// concluding no images
				// TODO: put a legit sample picture here:
				resp.sendRedirect("http://imx.solid-run.com/wiki/images/7/75/No_image_available.png");
			}
		} else {
			System.out.println("no entries in trip");
			resp.sendRedirect("http://imx.solid-run.com/wiki/images/7/75/No_image_available.png");
		}

	}
}