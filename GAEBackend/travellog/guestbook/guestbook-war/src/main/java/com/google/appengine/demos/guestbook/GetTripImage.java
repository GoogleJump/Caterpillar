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
 * With a trip key as parameter, query entries for the trip for the most recent entry
 * From the most recent entry, get the first photo and serve it
 * TODO: scale and crop image
 */
public class GetTripImage extends HttpServlet {
	
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	ImagesService imagesService = ImagesServiceFactory.getImagesService();
	
  @Override
  public void  doGet(HttpServletRequest req, HttpServletResponse resp)
  throws IOException, ServletException {
	  
	  Key tripKey = KeyFactory.stringToKey(req.getParameter("tripKey"));
	  
	  Query entryquery = new Query("Entry").addFilter("posterTrip",
		         Query.FilterOperator.EQUAL,
		        	tripKey).addSort("dateCreated", Query.SortDirection.DESCENDING);

	  DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
	  
	  List<Entity> entriesInTrip = datastore.prepare(entryquery).asList(FetchOptions.Builder.withLimit(1));

	  List<BlobKey> imageKeys = (List<BlobKey>) entriesInTrip.get(0).getProperty("imageKeys");
	  BlobKey image = imageKeys.get(0);
	  blobstoreService.serve(image, resp); //for now, serve like this, but instead uuse image api b/c more efficient and need cropping
	  
//TODO: test this, crop it how we want (put 500 pixels as image size completely randomly)
     // BlobKey blobKey = new BlobKey(req.getParameter("blobKey"));
     /*// blobstoreService.serve(blobKey, resp); //this is another option
      Image image = ImagesServiceFactory.makeImageFromBlob(blobKey);
      Transform noOpCrop = ImagesServiceFactory.makeCrop(0, 0, 1, 1);
      image = imagesService.applyTransform(noOpCrop, image);                     
      System.out.println("Stored image " + image.getWidth() + ", " + image.getHeight());
     String imgurl = imagesService.getServingUrl(blobKey, 500, true); //idk what image size should be
     resp.sendRedirect(imgurl);*/
    //  blobstoreService.serve(blobKey, resp); //for now using this b/c easier, but more inefficient
     
     
     
     
      }
    }