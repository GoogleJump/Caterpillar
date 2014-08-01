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

public class GetImageFromBlobKey extends HttpServlet {
	
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	ImagesService imagesService = ImagesServiceFactory.getImagesService();
	
  @Override
  public void  doGet(HttpServletRequest req, HttpServletResponse resp)
  throws IOException, ServletException {
	  
	  //get the image from blobkey and resize
      BlobKey blobKey = new BlobKey(req.getParameter("blobKey"));
      Image image = ImagesServiceFactory.makeImageFromBlob(blobKey);
     // Transform resize = ImagesServiceFactory.makeResize(500, 500);
      System.out.println("blobkey is"+blobKey);
     String imgurl = imagesService.getServingUrl(blobKey); //idk what image size should be
    System.out.println("url is" + imgurl);
     resp.sendRedirect(imgurl); //center crop 300 is longest dimension
     // blobstoreService.serve(blobKey, resp); //this one is easier, but less efficient & doesn't crop
     
     
     
     
      }
    }