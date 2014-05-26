package com.travellog;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

  @Id
  private int id;
  private String firstname;
  private String lastname;
  private String emailAddress;
  private String username;
  private String password;
  private Date dateCreated; //TODO - string or string and ints?

  public User() {
  }

  public int getId() {
  return id;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setId(int idIn) {
    this.id = idIn;
  }

  public void setEmailAddress(String emailAddress) {
  this.emailAddress = emailAddress;
  }

public String getLastname() {
	return lastname;
}

public void setLastname(String lastname) {
	this.lastname = lastname;
}

public String getFirstname() {
	return firstname;
}

public void setFirstname(String firstname) {
	this.firstname = firstname;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public Date getDateCreated() {
	return dateCreated;
}

public void setDateCreated(Date dateCreated) {
	this.dateCreated = dateCreated;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}
}