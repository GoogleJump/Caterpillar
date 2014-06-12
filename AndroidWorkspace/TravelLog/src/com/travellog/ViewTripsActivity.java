package com.travellog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import com.travellog.DrawerActivity.MenuFragment;
import com.travellog.ViewPhotosActivity.AsyncTaskLoadFiles;
import com.travellog.ViewPhotosActivity.ImageAdapter;

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
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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

/**
 * ViewTripsActivity
 * View all trips as a photo preview
 * Edit the information of trips
 * View entries for a trip
 * Edit entries
 * TODO:
 * major refactoring has to happen asap - too many fragments and confusing/hacky ways of doing things
 * loading/updating backend
 * 
 */
public class ViewTripsActivity extends DrawerActivity implements
		ActionBar.TabListener {

	/*
	 * fields specific to sliding drawer:
	 */
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mMenuTitles;
	RelativeLayout content;

	/* Fields specific to tabs (trip/map tabs at the top) */
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	// Tab titles
	private String[] tabs = { "Trips", "Map" };

	/* fields specific to viewing entries and trips */
	//TableLayout tableOfTrips; //table layout of all trips
	static TripView selectedTrip; //currently selected trip as a trip view - in order to open the correct entries
	ArrayList<TripView> allTripViews; //list of user's trips as trip views
	private static EntryView selectedEntry;
	private static int TRIPS_PER_ROW = 2;
	private TripViewAdapter myTaskAdapter; // image adapter that uses allTripViews to
	// load the gridview with tripviews
	TripViewAdapter myTripViewAdapter;
private AsyncTaskLoadFiles myAsyncTaskLoadFiles; // async task that will
					// load photos from
					// database (TODO)

	/*
	 * these are to keep track of which date picker dialog the user is currently
	 * editing:
	 */
	static boolean edit_ret; //true if user is currently editing return date
	static boolean edit_dep; //true if user is currently editing depart date
	static Button depart; //button that opens depart date picker
	static Button ret; //button that opens return date picker


	/*for uploading images to an entry*/
	private static final int SELECT_PICTURE = 1; 
	private String selectedImagePath;
	
	


	private static String userKey;
	// static boolean edit; // determines whether edit mode or new trip mode

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		userKey = getUserKey();

		getLayoutContent(); //replace activity main content with content for this activity

		allTripViews = new ArrayList<TripView>();

		//setTableLayoutParams(); //fixing some formatting issues with table layout 

		/* view photo activity specific stuff */
		GridView gridview = (GridView) findViewById(R.id.gridview_trips);
		gridview.getLayoutParams().height = MainActivity.SCREEN_HEIGHT;
		gridview.setColumnWidth(MainActivity.SCREEN_HEIGHT/(2*TRIPS_PER_ROW)); //pictures per row
		myTripViewAdapter = new TripViewAdapter(this);
		gridview.setAdapter(myTripViewAdapter);
		ImageAdapter myTripAdapter;

		myAsyncTaskLoadFiles = new AsyncTaskLoadFiles(myTripViewAdapter);
		myAsyncTaskLoadFiles.execute();

		// when the user clicks one of the images in the gridview, open the view
		// photo fragment
		gridview.setOnItemClickListener(onTripViewClickListener);

		/*for tabs (trip/map tabs at the top)*/
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
	
	// listener for clicking on any image in the gridview
	OnItemClickListener onTripViewClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {


		}
	};

	/*private void setTableLayoutParams() {
		//fix table layout parameters 
				TableLayout.LayoutParams params = new TableLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				tableOfTrips = new TableLayout(this);
				tableOfTrips.setLayoutParams(params);
				tableOfTrips.setGravity(Gravity.CENTER);
				// center everything (for some reason gravity center not working even
				// though it works in xml.)
				Display display = getWindowManager().getDefaultDisplay();
				Point size = new Point();
				display.getSize(size);
				int width = size.x;
				int padding = (width - 300) / 6; // 2 images, of width 300 each, (3
													// spaces * 2 images = 6)
				tableOfTrips.setPadding(padding, 10, padding, 0);
	}*/
	
	public void getLayoutContent() {
		content = (RelativeLayout) findViewById(R.id.content_homepage);
		content.removeAllViews();
		getLayoutInflater().inflate(R.layout.activity_view_trips, content);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/*for sliding drawer menu*/
	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/*for sliding drawer menu*/
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

	/*a few hard coded trips to test layout - adds directly to layout*/
	/*public void loadTripsExample() {
		LinearLayout layout = (LinearLayout) content
				.findViewById(R.id.view_entries_content);
		layout.removeAllViews();
		layout.addView(tableOfTrips);

		TableRow tr1 = new TableRow(this);
		TripView trip2 = new TripView(this);
		trip2.setAsAddTripView(); //first trip in list of trips is for adding a new trip
		tr1.addView(trip2);

		TripView trip = new TripView(this);
		ArrayList<EntryView> entry = new ArrayList<EntryView>();
		entry.add(new EntryView(this));
		entry.add(new EntryView(this));
		EntryView long_text_ex = new EntryView(this);
		long_text_ex.setDescription(getString(R.string.lorem_ipsum_long));
		entry.add(long_text_ex);
		trip.getTrip().setEntries(entry);
		tr1.addView(trip);
		tr1.addView(new TripView(this));
		tableOfTrips.addView(tr1);
	}*/

	//clears the layout - keeps table, but removes its children
	public void removeAllFromLayout() {
		LinearLayout layout = (LinearLayout) content
				.findViewById(R.id.view_trips_layout);
		layout.removeAllViews();
		//tableOfTrips.removeAllViews();
	}

	// part of viewing entries. if text is too long, clicking this button will
	// show more/less
	public boolean onSeeMoreClick(View v) {
		EntryView entry = (EntryView) v.getParent().getParent();
		entry.toggleShortenedText();
		return true;
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
		//trips tab - loads trips example for now
		if (tab.getPosition() == 0) {
			//loadTripsExample();
			// removeAllFromLayout();
		}

		//map tab - does nothing for early deliverable
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

	//editing a new trip - replaces fragment with entries for that trip
	public boolean onSubmitTripClick(View v) {
		Fragment fragment = new Fragment();
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.view_trips_layout, fragment).commit();

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

		// show trips again -- replace this example with the actual trips
		//this.loadTripsExample();
		// update DB

		return true;
	}


	public void loadEntriesFromTrip(Trip trip) {
		LinearLayout layout = (LinearLayout) content
				.findViewById(R.id.view_trips_layout);
		// as a test load a few sample entries:
		for (EntryView entry : trip.getEntries()) {
			layout.addView(entry);
		}
	}

	//clicking a trip view will load the entries from that trip
	//unless it is the very first trip view - the "addTripView"
	//this will open an editTripFragment to add a new trip
	public boolean onTripViewClick(View v) {
		Trip trip = ((TripView) v.getParent().getParent()).getTrip();
		this.removeAllFromLayout();

	/*	if (((TripView) v.getParent().getParent()).isAddTripView()) {
			// create new trip, add it to list of trips to display
			selectedTrip = new TripView(this);
			this.allTripViews.add(selectedTrip);
			// open edit trip fragment
			Fragment fragment = new EditTripFragment();
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.view_trips_layout, fragment).commit();
			// open the empty entries page for the created trip
			// this.loadEntriesExample();
			loadEntriesFromTrip(selectedTrip.getTrip());

			// TODO add trip to database
		}
*/
		// get trip, get entries, load entries
		//else {
			selectedTrip = (TripView) v.getParent().getParent();
			Intent i = new Intent(this, ViewEntriesActivity.class);
			//TODO: send trip id to entries activity so it can load up the right entries
			startActivity(i);
		//}
		return true;
	}

	public boolean onAddTripClick(View v) {
		selectedTrip = new TripView(this);
		this.allTripViews.add(selectedTrip);
		// open edit trip fragment
		Fragment fragment = new EditTripFragment();
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.view_trips_layout, fragment).commit();
		// open the empty entries page for the created trip
		removeAllFromLayout();
		loadEntriesFromTrip(selectedTrip.getTrip());

		// TODO add trip to database
		return true;
	}

	public boolean onPhotoViewClick(View v) {
		// TODO: which entry was clicked? - send over the id of the entry to the next activity
		Intent i = new Intent(this, ViewPhotosActivity.class);
		startActivity(i);
		return true;
	}


	public boolean onEditTripClick(View v) {
		this.removeAllFromLayout(); // clear the page and start the fragment
		TripView trip = (TripView) v.getParent().getParent();
		selectedTrip = trip;
		Fragment fragment = new EditTripFragment();
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.view_trips_layout, fragment).commit();
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

	public boolean onUploadPhotoClick(View v) {
		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, SELECT_PICTURE);
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
					.setText(entry.getDescription());
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
	
	public String getUserKey() {
		SharedPreferences prefs = this.getSharedPreferences(
			      MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
		return prefs.getString("userKey", null);
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
		
		//returns current text in field
		public String getTitle(){
			return ((EditText) view.findViewById(R.id.edit_trip_title)).getText().toString();
		}
		
		//returns current text in field
		public String getDescription(){
			return ((EditText) view.findViewById(R.id.edit_trip_description)).getText().toString();
		}
		
		//returns current text in field
		public String getTags(){
			return ((EditText) view.findViewById(R.id.edit_trip_tags)).getText().toString();
		}
		
		//returns current text in field
		public String getLocation(){
			return ((EditText) view.findViewById(R.id.edit_trip_location)).getText().toString();
		}
		
		//returns current text in field
		public String getDepart(){
			return depart.getText().toString();
		}
		
		//returns current text in field
		public String getReturn(){
			return ret.getText().toString();
		}
		
		//String[] trip = {null, owner, title, description, location, depart, return }
		//get's the fields in this fragment and calls the task that inserts trip
		public void addTrip() {
			new AddNewTripTask().execute(userKey, getTitle(), getDescription(), getLocation(),
					getDepart(), getReturn());	
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

	// retrieve the photo:
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				Uri selectedImageUri = data.getData();
				selectedImagePath = getPath(selectedImageUri);
			}
		}
	}

	/**
	 * helper to retrieve the path of an image URI
	 */
	public String getPath(Uri uri) {
		// just some safety built in
		if (uri == null) {
			// TODO perform some logging or show user feedback
			return null;
		}
		// try to retrieve the image from the media store first
		// this will only work for images selected from gallery
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		if (cursor != null) {
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		}
		// this is our fallback here
		return uri.getPath();
	}
	
	public class AsyncTaskLoadFiles extends AsyncTask<Void, String, Void> {

		public AsyncTaskLoadFiles(TripViewAdapter myTripViewAdapter) {
			myTaskAdapter = myTripViewAdapter;
		}

		@Override
		protected void onPreExecute() {
			/*
			 * String ExternalStorageDirectoryPath = Environment
			 * .getExternalStorageDirectory().getAbsolutePath() + "/DCIM/";
			 * 
			 * String targetPath = ExternalStorageDirectoryPath; targetDirector
			 * = new File(targetPath); myTaskAdapter.clear();
			 */

			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {

			/*
			 * File[] files = targetDirector.listFiles(); for (File file :
			 * files) { publishProgress(file.getAbsolutePath()); if
			 * (isCancelled()) break; }
			 */
			return null;
		}

		@Override
		protected void onProgressUpdate(String... values) {
			// myTaskAdapter.add(values[0]);
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Void result) {
			myTaskAdapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}

	}

	public class TripViewAdapter extends BaseAdapter {

		private Context mContext;

		public TripViewAdapter(Context c) {
			mContext = c;
			// test images:
			allTripViews = new ArrayList<TripView>();

			//add sample trips to list
			for (int i = 0; i < 10; i++) {
				allTripViews.add(new TripView(c));

			}
		}

		void add(TripView trip) {
			allTripViews.add(trip);
		}

		void clear() {
			// itemList.clear();
			allTripViews.clear();
		}

		/*
		 * void remove(int index){ photoList.remove(index); }
		 */

		@Override
		public int getCount() {
			return allTripViews.size();
		}

		@Override
		public Object getItem(int position) {
			return allTripViews.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TripView trip = allTripViews.get(position);
			if (convertView == null) { // if it's not recycled, initialize some
				// attributes
				trip.setLayoutParams(new GridView.LayoutParams(250, 300));
				//trip.setScaleType(TripView.ScaleType.CENTER_CROP);
				trip.setPadding(0, 0, 0, 0);
			} else {

			}

			/*img = img.createScaledBitmap(img, 220, 220, true);

			// imageView.setImageBitmap(bm);
			imageView.setImageBitmap(img);*/
			return trip;
		}
	}

}
