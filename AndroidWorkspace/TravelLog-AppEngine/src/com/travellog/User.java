package com.travellog;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Key;

@Entity
public class User {
	@Id
	Integer userId;
	
	String firstName;
	String lastName;
	Email email;
	String username;
	String password;
	Date dateCreated;
	
	public User(int id, String fname, String lname, Email email, String uname, String pword, Date d){
		this.userId = new Integer(id);
		this.firstName = fname;
		this.lastName = lname;
		this.email = email;
		this.username = uname;
		this.password = pword;
		this.dateCreated = d;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Email getEmail() {
		return email;
	}
	public void setEmail(Email email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

}
