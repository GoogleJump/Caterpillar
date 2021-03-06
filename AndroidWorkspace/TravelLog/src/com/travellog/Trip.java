package com.travellog;

import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.content.Context;

/**
 * Trip holds trip information such as title, description, dates, and entries
 * TODO: holding an image: should we store as a bitmap to make it easier to set
 * view or store as a blob to make it easier to create from backend?
 */
public class Trip {
	private String tripTitle;
	private String tripKey;
	private String tripLocation;
	private String tripDescription;
	private List<String> tripTags;
	private Calendar departDate;
	private Calendar returnDate;
	private ArrayList<EntryView> entries; // TODO create actual entry class?

	// TODO: include image, but how do we store them?
	// Blob imagePreview;

	public Trip() {
		tripTitle = "Untitled";
		tripLocation = "";
		tripDescription = "";
		tripTags = new ArrayList<String>();
		departDate = Calendar.getInstance();
		returnDate = Calendar.getInstance();
		entries = new ArrayList<EntryView>();
	}
	
	public String getTripKey() {
		return tripKey;
	}

	public String getTitle() {
		return tripTitle;
	}

	public String getLocation() {
		return tripLocation;
	}

	public String getDescription() {
		return tripDescription;
	}

	public List<String> getTags() {
		return tripTags;
	}

	public Calendar getDepartDate() {
		return departDate;
	}

	public Calendar getReturnDate() {
		return returnDate;
	}

	public String getDepartDateAsString() {
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
		return df.format(departDate.getTime());

	}

	public String getReturnDateAsString() {
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
		return df.format(returnDate.getTime());
	}

	public void setTitle(String title) {
		tripTitle = title;
	}

	public void setTags(List<String> tags) {
		tripTags = tags;
	}

	public void setLocation(String location) {
		tripLocation = location;
	}

	public void setDescription(String description) {
		tripDescription = description;
	}

	public void setDepartDate(Calendar date) {
		departDate = date;
	}

	public ArrayList<EntryView> getEntries() {
		return entries;
	}

	public void setEntries(ArrayList<EntryView> entries) {
		this.entries = entries;
	}

	public void setReturnDate(Calendar date) {
		returnDate = date;
	}

	public void setReturnDate(int year, int monthOfYear, int dayOfMonth) {
		Calendar c = Calendar.getInstance();
		c.set(year, monthOfYear, dayOfMonth);
		returnDate = c;
	}

	public void setDepartDate(int year, int monthOfYear, int dayOfMonth) {
		Calendar c = Calendar.getInstance();
		c.set(year, monthOfYear, dayOfMonth);
		departDate = c;
	}
	
	public void setTripKey(String tripKey) {
		this.tripKey = tripKey;
	}

}
