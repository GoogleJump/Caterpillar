package com.travellog;

import android.graphics.Bitmap;

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
