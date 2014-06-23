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
/**
 * parameters: email, password
 * checks databse for matching user based on email.  if password given matches, redirect to 
 * homepage.  
 * TODO: if it doesn't match, make it known to front end somehow so the user knows
 **/
public class SignInServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		String email = req.getParameter("email");
		String password = req.getParameter("password");

		if (email == null || password == null) {
			System.out.println("email or password null");
			resp.sendRedirect("/signin.jsp");
		}

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query("User").addFilter("email",
				Query.FilterOperator.EQUAL, email);
		List<Entity> users = datastore.prepare(query).asList(
				FetchOptions.Builder.withLimit(1));

		// nothing matches email
		if (users.isEmpty()) {
			resp.sendRedirect("/signin.jsp");
		}

		else {
			Entity user = users.get(0);
			// correct password
			if (user.getProperty("password").equals(password)) {
				req.setAttribute("userKey",
						KeyFactory.keyToString(user.getKey()));
				resp.sendRedirect("/homepage.jsp?userKey="
						+ KeyFactory.keyToString(user.getKey()));

				return;
			}
			// incorrect password
			else {
				resp.sendRedirect("/signin.jsp");
			}
		}
		resp.sendRedirect("/signin.jsp");

	}
}