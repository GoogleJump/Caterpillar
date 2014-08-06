package com.travellog;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class Entry {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	private String title;
	private String description;
	private List<String> hashtags;
	private String location;
	private String tripPoster;
	private String poster;
	private List<Long> photoIds;
	private Date dateCreated;
	
	
	public Key getKey() {
		return key;
	}
	void clearKey() {
		key = null;
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
	public List<String> getHashtags() {
		return hashtags;
	}
	public void setHashtags(List<String> hashtags) {
		this.hashtags = hashtags;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTripPoster() {
		return tripPoster;
	}
	public void setTripPoster(String tripPoster) {
		this.tripPoster = tripPoster;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public List<Long> photoIds() {
		return photoIds;
	}
	public void setPhotoIds(List<Long> blobkeys) {
		this.photoIds = photoIds;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

}
