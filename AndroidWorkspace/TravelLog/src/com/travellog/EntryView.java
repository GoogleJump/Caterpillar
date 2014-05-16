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
	String title;
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
	
}
