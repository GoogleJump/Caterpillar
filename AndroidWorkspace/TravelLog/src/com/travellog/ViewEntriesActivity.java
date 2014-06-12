package com.travellog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
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
import android.content.Intent;
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
import android.widget.GridLayout;
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
 * View entries for a trip
 * Edit entries
 * TODO:
 * some refactoring
 * make clicking photo -> view photos more obvious for the user
 * fix the add photo to entry button
 * fix photo preview for adding photo to entry (nothing shows up right now not sure why?)
 * loading/updating backend
 * 
 */
public class ViewEntriesActivity extends DrawerActivity  {

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


	/* fields specific to viewing entries and trips */
	TableLayout tableOfTrips; //table layout of all trips
	static TripView selectedTrip; //currently selected trip as a trip view - in order to open the correct entries
	ArrayList<TripView> allTripViews; //list of user's trips as trip views
	private static EntryView selectedEntry;

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

	// static boolean edit; // determines whether edit mode or new trip mode

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getLayoutContent(); //replace activity main content with content for this activity

		//retreive trip, but for now just make a new one
		selectedTrip = new TripView(this);
		setTitle(selectedTrip.getTrip().getTitle());
		((LinearLayout) content
				.findViewById(R.id.view_entries_content)).addView(selectedTrip.getTripInfoView(this));

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
		this.loadEntriesExample();
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

	/*a few hard coded entries to test the layout - adds them directly to layout*/
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


	//clears the layout
	public void removeAllFromLayout() {
		LinearLayout layout = (LinearLayout) content
				.findViewById(R.id.view_entries_content);
		layout.removeAllViews();
	}

	// part of viewing entries. if text is too long, clicking this button will
	// show more/less
	public boolean onSeeMoreClick(View v) {
		EntryView entry = (EntryView) v.getParent().getParent();
		entry.toggleShortenedText();
		return true;
	}


	//opens new fragment for adding an entry
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

		// update the view of entries
		loadEntriesFromTrip(selectedTrip.getTrip());
		// TODO update in DB

		return false;
	}


	public void loadEntriesFromTrip(Trip trip) {
		LinearLayout layout = (LinearLayout) content
				.findViewById(R.id.view_entries_content);
		// as a test load a few sample entries:
		for (EntryView entry : trip.getEntries()) {
			layout.addView(entry);
		}
	}

	public boolean onPhotoViewClick(View v) {
		// TODO: which entry was clicked? - send over the id of the entry to the next activity
		Intent i = new Intent(this, ViewPhotosActivity.class);
		startActivity(i);
		return true;
	}

	public boolean onEditEntryClick(View v) {
		removeAllFromLayout();
		EntryView entry = (EntryView) v.getParent().getParent().getParent();
		;
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
				System.out.println("trying to get preview");
				File imgFile = new  File(selectedImagePath);
				/*InputStream input = this.getContentResolver()
						.openInputStream(selectedImageUri);
				Bitmap img_preview = getThumbnail(selectedImageUri, input);*/
				//Bitmap img_preview = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
				Bitmap img_preview = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
				//TODO: this doesn't work - getting null pointer for the bitmap
				//img_preview = img_preview.createScaledBitmap(img_preview, 220, 220, true);
				ImageView imgview_preview = new ImageView(this);
				imgview_preview.setImageBitmap(img_preview);
			//	((LinearLayout) findViewById(R.id.add_photo_grid)).addView(imgview_preview);
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

	 public static Bitmap getThumbnail(Uri uri, InputStream input) throws FileNotFoundException, IOException{

	        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
	        onlyBoundsOptions.inJustDecodeBounds = true;
	        onlyBoundsOptions.inDither=true;//optional
	        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
	        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
	        input.close();
	        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
	            return null;

	        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

	        //where 50 is the thumbnail size
	        double ratio = (originalSize > 50) ? (originalSize / 50) : 1.0;

	        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
	        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
	        bitmapOptions.inDither=true;//optional
	        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
	       // input = this.getContentResolver().openInputStream(uri);
	        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
	        input.close();
	        return bitmap;
	    }

	    private static int getPowerOfTwoForSampleRatio(double ratio){
	        int k = Integer.highestOneBit((int)Math.floor(ratio));
	        if(k==0) return 1;
	        else return k;
	    }
}