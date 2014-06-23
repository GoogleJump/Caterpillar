package com.google.appengine.demos.guestbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

@SuppressWarnings("serial")
public class GetBlobKey extends HttpServlet {
    BlobstoreService blobstoreService = BlobstoreServiceFactory
            .getBlobstoreService();

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
    
        List<BlobKey> blobs = blobstoreService.getUploads(req).get("file");
        BlobKey blobKey = blobs.get(0);

        ImagesService imagesService = ImagesServiceFactory.getImagesService();
        ServingUrlOptions servingOptions = ServingUrlOptions.Builder.withBlobKey(blobKey);

        String servingUrl = imagesService.getServingUrl(servingOptions);

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/plain");
        
//      JSONObject json = new JSONObject();
//      try {
//          json.put("servingUrl", servingUrl);
//      } catch (JSONException e) {
//          // TODO Auto-generated catch block
//          e.printStackTrace();
//      }
//      try {
//          json.put("blobKey", blobKey.getKeyString());
//      } catch (JSONException e) {
//          // TODO Auto-generated catch block
//          e.printStackTrace();
//      }

        PrintWriter out = resp.getWriter();
        out.print(blobKey.getKeyString() + " working " + servingUrl);
        out.flush();
        out.close();
       
    }
    
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
    
        List<BlobKey> blobs = blobstoreService.getUploads(req).get("file");
        BlobKey blobKey = blobs.get(0);

        ImagesService imagesService = ImagesServiceFactory.getImagesService();
        ServingUrlOptions servingOptions = ServingUrlOptions.Builder.withBlobKey(blobKey);

        String servingUrl = imagesService.getServingUrl(servingOptions);

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/plain");
        
//      JSONObject json = new JSONObject();
//      try {
//          json.put("servingUrl", servingUrl);
//      } catch (JSONException e) {
//          // TODO Auto-generated catch block
//          e.printStackTrace();
//      }
//      try {
//          json.put("blobKey", blobKey.getKeyString());
//      } catch (JSONException e) {
//          // TODO Auto-generated catch block
//          e.printStackTrace();
//      }

        PrintWriter out = resp.getWriter();
        out.print(blobKey.getKeyString() + " working " + servingUrl);
        out.flush();
        out.close();
       
    }
}
