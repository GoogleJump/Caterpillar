package com.travellog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.travellog.DrawerActivity.DrawerItemClickListener;

public class AddEditTripActivity extends DrawerActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mMenuTitles;
	RelativeLayout content;
	static Calendar depart_date;
	static Calendar ret_date;
	boolean edit; //determines whether edit mode or new trip mode
	
	static Button depart;
	static Button ret;

	//these are to keep track of which dialog the user is currently editing
	static boolean edit_ret;
	static boolean edit_dep;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getLayoutContent();
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    edit = extras.getBoolean("edit");
		}
		if(edit) {
			Trip selectedTrip = ((ViewTripsActivity) this.getParent()).getSelectedTrip();
			fillWithCurrentTrip(selectedTrip);
		}

		// set text for buttons as current date
		depart = (Button) findViewById(R.id.button_date_depart);
		ret = (Button) findViewById(R.id.button_date_return);

		setDateAsToday();
		depart_date = Calendar.getInstance();
		ret_date = Calendar.getInstance();

		// retrieve photo if one was already taken
		Bundle b = getIntent().getExtras();
		if (b != null && b.containsKey("photo_path")) {
			String photo_path = (String) b.get("photo_path");

			if (photo_path != null) {
				Bitmap myBitmap = BitmapFactory.decodeFile(photo_path);

				ImageView photoView = (ImageView) findViewById(R.id.entry_photo);
				photoView.setImageBitmap(myBitmap);
			}
		}

		mTitle = mDrawerTitle = getTitle();
		mMenuTitles = getResources().getStringArray(R.array.menu_options);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mMenuTitles));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				// getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				// getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

	}

	private void fillWithCurrentTrip(Trip selectedTrip) {
		// TODO Auto-generated method stub
		((EditText) content.findViewById(R.id.edit_trip_title)).setText(selectedTrip.getTitle());
		((EditText) content.findViewById(R.id.edit_trip_description)).setText(selectedTrip.getDescription());
		((EditText) content.findViewById(R.id.edit_trip_tags)).setText(selectedTrip.getTags().toString());
		((EditText) content.findViewById(R.id.button_date_depart)).setText((CharSequence) selectedTrip.getDepartDate());
		((EditText) content.findViewById(R.id.button_date_return)).setText((CharSequence) selectedTrip.getReturnDate());
	}

	public void getLayoutContent() {
		content = (RelativeLayout) findViewById(R.id.content_homepage);
		content.removeAllViews();
		getLayoutInflater().inflate(R.layout.activity_add_trip, content);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		switch (item.getItemId()) {
		/*
		 * case R.id.action_websearch: // create intent to perform web search
		 * for this Menu Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
		 * intent.putExtra(SearchManager.QUERY, getActionBar().getTitle()); //
		 * catch event that there's no activity to handle intent if
		 * (intent.resolveActivity(getPackageManager()) != null) {
		 * startActivity(intent); } else { Toast.makeText(this,
		 * R.string.app_not_available, Toast.LENGTH_LONG).show(); } return true;
		 */
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// set the date in the buttons that open return and depart date dialogs as
	// current date
	public void setDateAsToday() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
		String formattedDate = df.format(c.getTime());
		depart.setText(formattedDate);
		ret.setText(formattedDate);
	}

	public boolean onDepartDateClick(View v) {
		edit_dep = true;
		edit_ret = false;
		DialogFragment departDateFragment = new DatePickerFragment();
	    departDateFragment.show(getFragmentManager(), "departDate");
		return true;
	}
	
	public boolean onReturnDateClick(View v) {
		edit_dep = false;
		edit_ret = true;
		DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getFragmentManager(), "returnDate");
		return true;
	}

	public static void setDepartDate(int year, int month, int day) {
		depart_date.set(year,  month, day);
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
		String formattedDate = df.format(depart_date.getTime());
		depart.setText(formattedDate);
	}
	
	public static void setReturnDate(int year, int month, int day) {
		ret_date.set(year,  month, day);
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
		String formattedDate = df.format(ret_date.getTime());
		ret.setText(formattedDate);
	}


	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {
		

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			if(edit_dep) {
			   setDepartDate(year, monthOfYear, dayOfMonth);
			}
			
			if(edit_ret) {
				setReturnDate(year, monthOfYear, dayOfMonth);
				}

		}
	}

}
