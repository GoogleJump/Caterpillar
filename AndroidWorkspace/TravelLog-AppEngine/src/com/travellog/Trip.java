package com.travellog;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Entity
public class Trip {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	private List<String> viewers;
	private List<String> posters;
	private String owner;
	private String title;
	private String description;
	private String location;
	private Date dateCreated;
	private Date returnDate;
	private Date departDate;
	private List<String> hashtags;

	
	public Trip () {
	}
	
	
	public Key getKey() {
		return key;
	}

	void clearKey() {
		key = null;
	}
	
	
	public List<String> getPosters() {
		return posters;
	}
	public void setPosters(List<String> posters) {
		this.posters = posters;
	}
	public List<String> getViewers() {
		return viewers;
	}
	public void setViewers(List<String> viewers) {
		this.viewers = viewers;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public Date getDepartDate() {
		return departDate;
	}
	public void setDepartDate(Date departDate) {
		this.departDate = departDate;
	}
	public List<String> getHashtags() {
		return hashtags;
	}
	public void setHashtags(List<String> hashtags) {
		this.hashtags = hashtags;
	}
	
}
