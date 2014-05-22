package com.travellog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import com.travellog.AddEditTripActivity.DatePickerFragment;
import com.travellog.DrawerActivity.MenuFragment;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;

//@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class ViewTripsActivity extends DrawerActivity implements
		ActionBar.TabListener {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mMenuTitles;
	RelativeLayout content;

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	// Tab titles
	private String[] tabs = { "Trips", "Map" };
	TableLayout tableOfTrips;
	static TripView selectedTrip;
	ArrayList<TripView> allTripViews;
	private static EntryView selectedEntry;

	// these are to keep track of which date picker dialog the user is currently
	// editing
	static boolean edit_ret;
	static boolean edit_dep;
	static Button depart;
	static Button ret;

	// static boolean edit; // determines whether edit mode or new trip mode

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getLayoutContent();

		this.allTripViews = new ArrayList<TripView>();

		TableLayout.LayoutParams params = new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		tableOfTrips = new TableLayout(this);
		tableOfTrips.setLayoutParams(params);
		tableOfTrips.setGravity(Gravity.CENTER);
		// center everything (for some reason gravity center not working even
		// though it works in xml...hm)
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int padding = (width - 300) / 6; // 2 images, of width 300 each, (3
											// spaces * 2 images = 6)
		tableOfTrips.setPadding(padding, 10, padding, 0);

		// for tabs:
		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		// for sliding drawer menu:
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

	public void getLayoutContent() {
		content = (RelativeLayout) findViewById(R.id.content_homepage);
		content.removeAllViews();
		getLayoutInflater().inflate(R.layout.activity_view_entries, content);
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

	public void loadEntriesExample() {
		// TODO: load entries into the layout
		LinearLayout layout = (LinearLayout) content
				.findViewById(R.id.view_entries_content);
		// as a test load a few sample entries:
		for (int i = 0; i < 3; i++) {
			layout.addView(new EntryView(this));

		}
		EntryView entry = new EntryView(this);
		((ImageView) entry.findViewById(R.id.entry_photo))
				.setImageResource(R.drawable.hiking_sample_photo);
		System.out.println("child count is: " + content.getChildCount());
		layout.addView(entry);
	}

	public void loadTripsExample() {
		LinearLayout layout = (LinearLayout) content
				.findViewById(R.id.view_entries_content);
		layout.removeAllViews();
		layout.addView(tableOfTrips);

		TableRow tr1 = new TableRow(this);
		TripView trip2 = new TripView(this);
		trip2.setAsAddTripView();
		tr1.addView(trip2);

		TripView trip = new TripView(this);
		ArrayList<EntryView> entry = new ArrayList<EntryView>();
		entry.add(new EntryView(this));
		entry.add(new EntryView(this));
		entry.add(new EntryView(this));
		trip.getTrip().setEntries(entry);
		tr1.addView(trip);
		tableOfTrips.addView(tr1);
	}

	public void removeAllFromLayout() {
		LinearLayout layout = (LinearLayout) content
				.findViewById(R.id.view_entries_content);
		layout.removeAllViews();
		tableOfTrips.removeAllViews();
	}


	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		if (tab.getPosition() == 0) {
			// removeAllEntries();
		}

		if (tab.getPosition() == 1) {
			// loadEntries();
		}

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		if (tab.getPosition() == 0) {
			loadTripsExample();
			// removeAllFromLayout();
		}

		if (tab.getPosition() == 1) {
			removeAllFromLayout();
			// loadEntriesExample();
		}

		/*
		 * if(tab.getPosition() == 2) { removeAllFromLayout(); }
		 */

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	public boolean onAddEntryClick(View v) {
		Fragment fragment = new Fragment();
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.view_entries_content, fragment).commit();

		// update entry
		selectedEntry.setTitle(((EditText) findViewById(R.id.edit_text_title))
				.getText().toString());
		selectedEntry
				.setDescription(((EditText) findViewById(R.id.edit_text_entry_description))
						.getText().toString());
		selectedEntry
				.setLocation(((EditText) findViewById(R.id.edit_text_location))
						.getText().toString());

		//update the view of entries
		loadEntriesFromTrip(selectedTrip.getTrip());
		// TODO update in DB
		
		return false;
	}

	public boolean onSubmitTripClick(View v) {
		Fragment fragment = new Fragment();
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.view_entries_content, fragment).commit();

		// update trip
		Trip editedTrip = selectedTrip.getTrip();
		editedTrip
				.setDescription(((EditText) findViewById(R.id.edit_trip_description))
						.getText().toString());
		editedTrip.setTitle(((EditText) findViewById(R.id.edit_trip_title))
				.getText().toString());
		editedTrip
				.setLocation(((EditText) findViewById(R.id.edit_trip_location))
						.getText().toString());
		// editedTrip.setTags(((EditText)findViewById(R.id.edit_trip_tags)).getText().toString()));
		// TODO
		// editedTrip.setReturnDate(year, monthOfYear, dayOfMonth); TODO and
		// depart date
		// update trip view
		selectedTrip.setTrip(editedTrip);
		
		//show trips again -- replace this example with the actual trips
		this.loadTripsExample();
		// update DB

		return true;
	}

	public void loadEntriesFromTrip(Trip trip) {
		LinearLayout layout = (LinearLayout) content
				.findViewById(R.id.view_entries_content);
		// as a test load a few sample entries:
		for (EntryView entry : trip.getEntries()) {
			layout.addView(entry);
		}
	}

	public boolean onTripViewClick(View v) {
		Trip trip = ((TripView) v.getParent().getParent()).getTrip();
		this.removeAllFromLayout();
		
		if (((TripView) v.getParent().getParent()).isAddTripView()) {
			// create new trip, add it to list of trips to display
			selectedTrip = new TripView(this);
			this.allTripViews.add(selectedTrip);
			// open edit trip fragment
			Fragment fragment = new EditTripFragment();
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.view_entries_content, fragment).commit();
			// open the empty entries page for the created trip
			// this.loadEntriesExample();
			loadEntriesFromTrip(selectedTrip.getTrip());

			// TODO add trip to database
		}

		// get trip, get entries, load entries
		else {
			selectedTrip = (TripView) v.getParent().getParent();
			// this.loadEntriesExample();
			loadEntriesFromTrip(trip);
		}
		return true;
	}

	public boolean onEditTripClick(View v) {
		this.removeAllFromLayout(); //clear the page and start the fragment
		TripView trip = (TripView) v.getParent().getParent();
		selectedTrip = trip;
		Fragment fragment = new EditTripFragment();
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.view_entries_content, fragment).commit();
		/*
		 * if (trip == null) { System.out.println("tripview was null"); return
		 * false; } else { //selectedTrip = trip; }
		 * 
		 * Intent i = new Intent(this, AddEditTripActivity.class);
		 * i.putExtra("edit", true); // editing trip, not new one //
		 * startActivityForResult(i);
		 */
		return true;
	}

	public boolean onEditEntryClick(View v) {
		removeAllFromLayout();
		EntryView entry = (EntryView) v.getParent().getParent();
		selectedEntry = entry;
		Fragment fragment = new EditEntryFragment();
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.view_entries_content, fragment).commit();
		return true;
	}

	public Trip getSelectedTrip() {
		return selectedTrip.getTrip();
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

	public boolean onAddPhotoClick(View v) {
		// TODO
		return true;
	}

	public static class EditEntryFragment extends Fragment {
		View view;

		public EditEntryFragment() {

		}

		private void fillWithCurrentEntry(EntryView entry) {
			((EditText) view.findViewById(R.id.edit_text_title))
					.setText(entry.entryTitle.getText());
			((EditText) view.findViewById(R.id.edit_text_entry_description))
					.setText(entry.entryText.getText());
			((EditText) view.findViewById(R.id.edit_text_location))
					.setText(entry.getLocation());
			// TODO: tags ((EditText)
			// view.findViewById(R.id.edit_trip_location)).setText(selectedTrip.getLocation());

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			// Inflate the layout for this fragment
			view = inflater.inflate(R.layout.activity_add_entry, container,
					false);
			fillWithCurrentEntry(selectedEntry);
			return view;
		}
	}

	public static class EditTripFragment extends Fragment {
		View view;

		public EditTripFragment() {

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			// Inflate the layout for this fragment
			view = inflater.inflate(R.layout.activity_add_trip, container,
					false);
			depart = (Button) view.findViewById(R.id.button_date_depart);
			ret = (Button) view.findViewById(R.id.button_date_return);
			fillWithCurrentTrip(selectedTrip.getTrip());
			return view;
		}

		private void fillWithCurrentTrip(Trip selectedTrip) {
			((EditText) view.findViewById(R.id.edit_trip_title))
					.setText(selectedTrip.getTitle());
			((EditText) view.findViewById(R.id.edit_trip_description))
					.setText(selectedTrip.getDescription());
			((EditText) view.findViewById(R.id.edit_trip_tags))
					.setText(selectedTrip.getTags().toString().replace("[", "")
							.replace("]", ""));
			((EditText) view.findViewById(R.id.edit_trip_location))
					.setText(selectedTrip.getLocation());
			depart.setText((CharSequence) selectedTrip.getDepartDateAsString());
			ret.setText((CharSequence) selectedTrip.getReturnDateAsString());

		}

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

		public static void setReturnDate(int year, int month, int day) {
			Calendar date = Calendar.getInstance();
			date.set(year, month, day);
			SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
			String formattedDate = df.format(date.getTime());
			ret.setText(formattedDate);
		}

		public static void setDepartDate(int year, int month, int day) {
			Calendar date = Calendar.getInstance();
			date.set(year, month, day);
			SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
			String formattedDate = df.format(date.getTime());
			depart.setText(formattedDate);
		}

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			if (edit_dep) {
				// setDepartDate(year, monthOfYear, dayOfMonth);
				selectedTrip.getTrip().setDepartDate(year, monthOfYear,
						dayOfMonth);
				setDepartDate(year, monthOfYear, dayOfMonth);
			}

			if (edit_ret) {
				selectedTrip.getTrip().setReturnDate(year, monthOfYear,
						dayOfMonth);
				setReturnDate(year, monthOfYear, dayOfMonth);
			}

		}
	}

}