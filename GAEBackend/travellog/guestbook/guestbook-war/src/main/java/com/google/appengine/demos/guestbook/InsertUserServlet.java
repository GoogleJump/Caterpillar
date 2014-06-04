package com.google.appengine.demos.guestbook;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InsertUserServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

       /* String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        Date date = new Date();
        Key userKey = KeyFactory.createKey("User", System.currentTimeMillis().toString()); //**for now generate key using seconds, but figure out how to autogenerate

        Entity user = new Entity("User", userKey);
        user.setProperty("firstName", firstname);
        user.setProperty("lastName", lastname);
        user.setProperty("username", username);
        user.setProperty("password", password);
        user.setProperty("email", email);
        user.setProperty("dateCreated", date);


    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(user);

    //TODO: redirect
   // resp.sendRedirect("/guestbook.jsp?guestbookName=" + guestbookName);*/

       Key userKey = KeyFactory.createKey("User", (System.currentTimeMillis()+ ""));
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
        System.out.println("success! Inserted a user!");

        resp.sendRedirect("/guestbook.jsp?guestbookName=" + "default");

  }
}