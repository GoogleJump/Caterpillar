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

public class SignInServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
  throws IOException, ServletException {

    System.out.println("signing in");
    String email = req.getParameter("email");
    String password = req.getParameter("password");

    if(email == null || password == null) {
      System.out.println("email or password null");
      // JOptionPane.showMessageDialog(frame, "please give email and password");
      resp.sendRedirect("/signin.jsp");
    }


    System.out.println("datastore service");
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    System.out.println("about to make query with email");
    Query query = new Query("User").addFilter("email",
     Query.FilterOperator.EQUAL,
     email);
    System.out.println("about to prepare to query");
    List<Entity> users = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(1));



     //nothing matches email
    if (users.isEmpty()) {
     // JOptionPane.showMessageDialog(frame, "email or password incorrect");
      System.out.println("users was empty--no matches to query");
      resp.sendRedirect("/signin.jsp");
    }

    else {
      System.out.println("success! users was not empty!  Now, get the user and check for a matching password");
      Entity user = users.get(0);
      //correct password
      if(user.getProperty("password").equals(password)) {
        System.out.println("success! password matches! :D");

         //if successful sign up, send over the user's key and redirect to tripview page:
        req.setAttribute("key", user.getProperty("key"));
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/signin.jsp");
        rd.forward(req, resp);
        resp.sendRedirect("/tripview.jsp"); 
      }
      //incorrect password
      else {
        System.out.println("password doesn't match :(");
       // JOptionPane.showMessageDialog(frame, "email or password incorrect");
          resp.sendRedirect("/signin.jsp");
        }
      }

    //TODO: redirect
   // resp.sendRedirect("/guestbook.jsp?guestbookName=" + guestbookName);*/

      /* Key userKey = KeyFactory.createKey("User", (System.currentTimeMillis()+ ""));
       System.out.println("creating user:");
       Entity user = new Entity("User", userKey);
        user.setProperty("firstName", "Goofy");
        user.setProperty("lastName", "Goober");
        user.setProperty("username", "goober123");
        user.setProperty("password", "goofy123");
        user.setProperty("email", "peanuts@email.com");
        user.setProperty("dateCreated", new Date());

        System.out.println("about to insert");
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(user);
        System.out.println("success! Inserted a user!");*/

        resp.sendRedirect("/signin.jsp");

      }
    }