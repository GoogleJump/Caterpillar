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

public class InsertTripServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
  throws IOException, ServletException {

    System.out.println("Hello, insert trip servlet");

  //fields of a trip -- TODO: make sure these are correct and match the android entities
    String owner = req.getParameter("owner");
   // List<Integer> posters = req.getParameter("posters");
    //List<Integer> viewers = req.getParameter("viewers");
    String title = req.getParameter("title");
    String description = req.getParameter("description");
    String location = req.getParameter("location");
    Date date = new Date();
    Key tripKey = KeyFactory.createKey("Trip", System.currentTimeMillis()+""); //**for now generate key using seconds, but figure out how to autogenerate


  //create trip entitiy - TODO: also make sure these are correct
    Entity trip = new Entity("Trip", tripKey);
  /*  trip.setProperty("owner", owner);
    trip.setProperty("posters", posters);
    trip.setProperty("viewers", viewers);
    trip.setProperty("title", title);
    trip.setProperty("description", description);
    trip.setProperty("dateCreated", date);*/


    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(trip);
    System.out.println("success!  Inserted trip! :D");

        resp.sendRedirect("/tripview.jsp?key="+tripKey); //send the trip key parameter and redirect TODO: redirect to entry page
        
      }
    }