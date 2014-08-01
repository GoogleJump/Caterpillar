package com.travellog;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * TripView
 * for viewing trips - consists of the trip title and a photo
 *
 */
public class TripView extends LinearLayout {
	//ImageButton tripPhoto;
	//String title;
	Context context;
	
	//Views
	View tripLayout;
	TextView tripTitle;
	ImageView tripPhoto;
//	boolean add_trip_view; //clicking this view adds a new trip rather than opening entrie
	Trip trip;
	
	
	public TripView(Context context) {
		super(context);
		this.context = context;
		init();
		
	}
	
	public TripView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
		
	}
	
	public TripView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init();
		
	}

	private void setSampleValues(Trip t) {
		t.setTitle("Untitled");
		ArrayList<String> tags = new ArrayList<String>();
		tags.add("travellog");
		t.setTags(tags);
		t.setDescription("");
		t.setLocation("");
	}
	
	public void init() {
		LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		tripLayout = vi.inflate(R.layout.trip_view, null);
		this.addView(tripLayout);
		trip = new Trip();
		setSampleValues(trip);
		tripPhoto = (ImageView) tripLayout.findViewById(R.id.trip_photo);
		tripTitle = (TextView) findViewById(R.id.trip_title);
		tripTitle.setText(trip.getTitle());
		//add_trip_view = false;
		
		}
	
	public void setPicture() {
		//TODO - how are we storing images?
	}
	
	public void setImgResource(int imgRes) {
		tripPhoto.setImageResource(imgRes);
	}
	
	public void setTitle(String title) {
		tripTitle.setText(title);
	}
	
	//first trip in the list of trips should be a button that lets you add a new trip
	/*public void setAsAddTripView() {
		//add_trip_view = true;
		this.setImgResource(R.drawable.plus_sign);
		this.setTitle("Add Trip");
		this.setAlpha((float).5); //faded out a bit
		//remove "edit" button
		View v = this.findViewById(R.id.trip_view).findViewById(R.id.button_edit_trip);
		((ViewGroup) this.findViewById(R.id.trip_view)).removeView(v);
	}*/
	

	public Trip getTrip() {
		return trip;
	}
	
	public void setTrip(Trip trip) {
		this.setTitle(trip.getTitle());
		this.trip = trip;
	}
	
	/*public boolean isAddTripView() {
		return add_trip_view;
	}*/
}
