package com.travellog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EntryView extends LinearLayout {
	ImageButton photoPreview;
	//String title;
	Context context;
	View entryLayout;
	TextView entryText;
	TextView entryDateLocation;
	TextView entryTitle;
	ImageView entryPhoto;
	
	
	public EntryView(Context context) {
		super(context);
		this.context = context;
		init();
		
	}
	
	public EntryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
		
	}
	
	public EntryView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init();
		
	}

	public void init() {
		LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		entryLayout = vi.inflate(R.layout.entry_view, null);
		this.addView(entryLayout);
		
		
		entryPhoto = (ImageView) entryLayout.findViewById(R.id.entry_photo);
		entryDateLocation = (TextView) findViewById(R.id.entry_date_location);
		entryText = (TextView) findViewById(R.id.entry_text);
		entryTitle = (TextView) findViewById(R.id.entry_title);
		entryDateLocation.getLayoutParams().width = MainActivity.SCREEN_WIDTH;
	}
	
	public void setPicture() {
		//TODO - how are we storing images?
	}
	
	public void setImgResource(int imgRes) {
		entryPhoto.setImageResource(imgRes);
	}
	
	
	public void setTitle(String title) {
		entryTitle.setText(title);
	}

	public void setDescription(String string) {
		entryText.setText(string);
	}
	
	
	//Date, Location
	public void setLocation(String location) {
		int split = ((String) entryDateLocation.getText()).indexOf(',');
		System.out.println(entryDateLocation.getText());
		String new_string = ((String) entryDateLocation.getText()).substring(0, split);
		new_string = new_string + ", " + location;
		entryDateLocation.setText(new_string);
	}
	
	//Date, Location
	public void setDate(String date) {
		int split = ((String) entryDateLocation.getText()).indexOf(',');
		String new_string = ((String) entryDateLocation.getText()).substring(split+1);
		new_string = date +", "+ new_string;
		entryDateLocation.setText(new_string);
	}
	
	public String getLocation() {
		int split = ((String) entryDateLocation.getText()).indexOf(',');
		String new_string = ((String) entryDateLocation.getText()).substring(split+1);
		return new_string;
	}
}
