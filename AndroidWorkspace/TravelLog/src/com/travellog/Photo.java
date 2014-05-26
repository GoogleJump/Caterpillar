package com.travellog;

import android.graphics.Bitmap;
/**
 * 
 * Photo
 * holds image, title, description, and location for a photo
 *TODO
 * do we need tags or a photo id?
 */
public class Photo {
	Bitmap image;
	String title;
	String description;
	String location;

	// tags?? id??

	public Photo() {
		title = "";
		description = "";
		location = "";
		image = null;
	}

	public void setImage(Bitmap img) {
		this.image = img;

	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setDescrption(String description) {
		this.description = description;
	}

	public Bitmap getImage() {
		return image;
	}

	public String getDescription() {
		return description;
	}

	public String getTitle() {
		return title;
	}

	public String getLocation() {
		return location;
	}
}
