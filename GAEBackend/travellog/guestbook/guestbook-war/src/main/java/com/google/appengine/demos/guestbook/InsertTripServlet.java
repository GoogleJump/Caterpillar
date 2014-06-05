package com.google.appengine.demos.guestbook;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.FetchOptions;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import java.util.List;

import java.util.ArrayList;

public class InsertTripServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
  throws IOException, ServletException {

    System.out.println("Hello, insert trip servlet");

  //fields of a trip -- TODO: make sure these are correct and match the android entities
    //TODO: posters and viewers
    String owner = req.getParameter("userKey"); //owner key
    String title = req.getParameter("title");
    String description = req.getParameter("description");
    String location = req.getParameter("location");
    String depDate = req.getParameter("departDate");
    String retDate = req.getParameter("retDate");
    Date date = new Date();
    Key tripKey = KeyFactory.createKey("Trip", System.currentTimeMillis()+""); //**for now generate key using seconds, but figure out how to autogenerate


   //create trip entitiy - TODO: also make sure these are correct and consistent
    Entity trip = new Entity("Trip", tripKey);
    trip.setProperty("owner", owner);
    trip.setProperty("title", title);
    trip.setProperty("description", description);
    trip.setProperty("dateCreated", date);
    trip.setProperty("location", location);
    
    //format depart and return dates
    //TODO: figure out how date is being inputed to properly format dates


    //create viewers and posters lists
    List<String> viewers = new ArrayList<String>();
    List<String> posters = new ArrayList<String>();
    viewers.add(owner);
    posters.add(owner);
    

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    //get the user that owns the post so that we can add friends/posters to the list of viewers/posters 
    //- haven't implemented friends yet so this is commented out
   /* Query ownerquery = new Query("User").addFilter("key",
     Query.FilterOperator.EQUAL,
     userKey);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Entity ownerUser = datastore.prepare(ownerquery).asList(FetchOptions.Builder.withLimit(1)).get(0);*/


    datastore.put(trip);
    System.out.println("success!  Inserted trip! :D");

        resp.sendRedirect("/tripview.jsp?key="+tripKey); //send the trip key parameter and redirect TODO: redirect to entry page
        
      }
    }