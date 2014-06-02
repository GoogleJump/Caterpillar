package com.travellog;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Note {

  @Id
  private String id;
  private String description;
  private String emailAddress;

  public Note() {
  }

  public String getId() {
  return id;
  }

  public String getDescription() {
    return description;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setId(String idIn) {
    this.id = idIn;
  }


  public void setDescription(String description) {
    this.description = description;
  }

  public void setEmailAddress(String emailAddress) {
  this.emailAddress = emailAddress;
  }
}
