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

public class InsertUserServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
  throws IOException, ServletException {

    System.out.println("Hello insert user servlet");
    String firstname = req.getParameter("firstname");
    String lastname = req.getParameter("lastname");
    String username = req.getParameter("username");
    String password = req.getParameter("password");
    String email = req.getParameter("email");
    Date date = new Date();
    Key userKey = KeyFactory.createKey("User", System.currentTimeMillis()+""); //**for now generate key using seconds, but figure out how to autogenerate

      //first check if email and username are unique
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        System.out.println("about to make query with email");
        Query emailquery = new Query("User").addFilter("email",
         Query.FilterOperator.EQUAL,
         email);
        List<Entity> usersByEmail = datastore.prepare(emailquery).asList(FetchOptions.Builder.withLimit(1));


        Query namequery = new Query("User").addFilter("username",
         Query.FilterOperator.EQUAL,
         username);
        System.out.println("about to prepare to query");
        List<Entity> usersByUsername = datastore.prepare(namequery).asList(FetchOptions.Builder.withLimit(1));

        Entity user;
        if(usersByEmail.isEmpty()) {

          if(usersByUsername.isEmpty()) {

            System.out.println("input information:"+firstname+ " "+lastname+ " "+username + " "+ password+ " " + email);

            user = new Entity("User", userKey);
       
           // user.setProperty("key", userKey);
            user.setProperty("firstName", firstname);
            user.setProperty("lastName", lastname);
            user.setProperty("username", username);
            user.setProperty("password", password);
            user.setProperty("email", email);
            user.setProperty("dateCreated", date);


           // DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            datastore.put(user);
          }

      else { //users by username is not empty
        System.out.println("user already registered with username"+ username);
        resp.sendRedirect("/signin.jsp?username=taken");
        return;
      }

    }
      else { //users by email is not empty
        System.out.println("user already registered with email: " + email);
        resp.sendRedirect("/signin.jsp?email=taken");
        return;
      }

      //if successful sign up, send over the user's key and redirect to homepage page:
        req.setAttribute("userKey", KeyFactory.keyToString(user.getKey()));
        //RequestDispatcher rd = getServletContext().getRequestDispatcher("/homepage.jsp?key="+user.getKey());
        //rd.forward(req, resp);
        resp.sendRedirect("/homepage.jsp?userKey="+ KeyFactory.keyToString(user.getKey()));
        
          //resp.sendRedirect("/signin.jsp");


      }
    }