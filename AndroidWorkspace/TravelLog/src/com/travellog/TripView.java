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
	private Context context;

	//Views
	private View tripLayout;
	private TextView tripTitle;
	private ImageView tripPhoto;
	//private boolean add_trip_view; //clicking this view adds a new trip rather than opening entrie
	private Trip trip;


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
		t.setTitle("Trip Title");
		ArrayList<String> tags = new ArrayList<String>();
		tags.add("tag1");
		tags.add("heres another tag");
		t.setTags(tags);
		t.setDescription("This trip was so great.  Bla bla bla let's talk about this trip some more over here");
		t.setLocation("best place on earth");
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

	//Not doing this anymore
	//first trip in the list of trips should be a button that lets you add a new trip
	/*public void setAsAddTripView() {
		add_trip_view = true;
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
		setTitle(trip.getTitle());
		this.trip = trip;
	}

	public TripInfoView getTripInfoView(Context c) {
		return new TripInfoView(c);
	}

/*	public boolean isAddTripView() {
		return add_trip_view;
	}*/

	//View that displays the trip's info - description, title, etc.
	public class TripInfoView extends LinearLayout {
		Context c;
		View tripInfoLayout;

		public TripInfoView(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			this.c = context;
			init();
		}

		public TripInfoView(Context context, AttributeSet attrs) {
			super(context, attrs);
			this.c = context;
			init();
		}

		public TripInfoView(Context context) {
			super(context);
			this.c = context;
			init();
		}

		public void init() {
			//inflate the layout
			LayoutInflater vi = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			tripInfoLayout = vi.inflate(R.layout.trip_description, null);
			this.addView(tripInfoLayout);

			setTextViews();

		}

		public void setTextViews() {
			//fill in/update the information of the trip 
			((TextView) tripInfoLayout.findViewById(R.id.trip_info_title)).setText(trip.getTitle());
			((TextView) tripInfoLayout.findViewById(R.id.trip_info_description)).setText(trip.getDescription());
			((TextView) tripInfoLayout.findViewById(R.id.trip_info_location)).setText(trip.getLocation());
			String date = trip.getDepartDateAsString(); //set as depart date or empty string
			String return_date = trip.getReturnDateAsString();
			if(!return_date.equals("")) {
				date = date + " to " + trip.getReturnDateAsString(); //depart date - return date
			}
			((TextView) tripInfoLayout.findViewById(R.id.trip_info_dates)).setText(date);

		}

	}
}