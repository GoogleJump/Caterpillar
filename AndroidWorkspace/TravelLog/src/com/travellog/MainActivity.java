package com.travellog;

import java.util.Locale;

import com.google.api.client.util.DateTime;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.view.GravityCompat;

/**
 * The Main Activity.
 * 
 * sign in/sign up buttons TODO implement sign up and connect button
 */
public class MainActivity extends DrawerActivity {

	static int SCREEN_WIDTH;
	static int SCREEN_HEIGHT;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mPlanetTitles;
	
	public static final String MyPREFERENCES = "MyPrefs" ;//for shared preferences

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("");

		// Intent i = new Intent(this, RegisterActivity.class);
		// startActivity(i);
		
//		new AddNewUserTask().execute(getApplicationContext(), "Ben", "Affleck", "Benny", "IamBatman", "bf@Test.com");
//		new GetUserKeyByEmailTask().execute(getApplicationContext(), "bf@Test.com");

//		new AddNewTripTask().execute(getApplicationContext(), "agtnanRyYXZlbGxvZ3IRCxIEVXNlchiAgICAgICgCQw", "Test Trip 1", "Test desc 1", "Test location 1");
//		new GetTripByOwnerKeyTask().execute(getApplicationContext(), "agtnanRyYXZlbGxvZ3IRCxIEVXNlchiAgICAgICgCQw");
		
		new UploadImageToBlobstoreTask().execute(getApplicationContext(), "/storage/emulated/0/DCIM/Camera/IMG_20140610_215900.jpg");
		
		// TODO: if the person is already logged in, start next activity
		setLayoutContent();
		Display display = getWindowManager().getDefaultDisplay();
		SCREEN_WIDTH = display.getWidth();
		SCREEN_HEIGHT = display.getHeight();

		mTitle = mDrawerTitle = getTitle();
		mPlanetTitles = getResources().getStringArray(R.array.menu_options);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mPlanetTitles));
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
		// new SampleEndpointsTask().execute(getApplicationContext());

	}

//	public void resizeIcons() {
//		TableRow tablerow1 = (TableRow) findViewById(R.id.tableRow1);
//		TableRow tablerow2 = (TableRow) findViewById(R.id.tableRow2);
//		int row_count = 2;
//
//		TableLayout icon_table = (TableLayout) findViewById(R.id.icon_table);
//		int row1_children_count = tablerow1.getChildCount();
//		int row2_children_count = tablerow2.getChildCount();
//		int table_width = icon_table.getWidth();
//		int table_height = icon_table.getHeight();
//		/*
//		 * if(table_width >= table_height){ System.out.println("width>height");
//		 * }
//		 */
//
//		int target_image_width = (table_width) * (row_count)
//				/ (row1_children_count + row2_children_count);
//		int target_image_height = (table_height) * (row_count)
//				/ (row1_children_count + row2_children_count);
//		for (int i = 0; i < row1_children_count; i++) {
//			tablerow1.getChildAt(i).getLayoutParams().width = target_image_height;
//			// tablerow1.getChildAt(i).getLayoutParams().height = table_height;
//		}
//
//		for (int i = 0; i < row2_children_count; i++) {
//			tablerow2.getChildAt(i).getLayoutParams().width = target_image_width;
//			// tablerow1.getChildAt(i).getLayoutParams().height = table_height;
//		}
//	}
//
//	// logo should be a square with sides half the screen
//	public void resizeLogo() {
//
//		View logo = (View) findViewById(R.id.logo_homepage);
//		System.out.println("resizing logo");
//		System.out.println("before" + logo.getHeight());
//		Display display = getWindowManager().getDefaultDisplay();
//		int screen_height = display.getHeight();
//		logo.getLayoutParams().height = screen_height / 4;
//		logo.getLayoutParams().width = screen_height / 4;
//		System.out.println("after" + screen_height / 2);
//
//	}
//
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
//
//	/* Called whenever we call invalidateOptionsMenu() */
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
		 * for this planet Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
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

//	// the following is for the original homepage. No longer relevant because
//	// homepage is same as viewentriesactivity
//	/*
//	 * public boolean onCameraIconClick(View v) { Intent i = new Intent(this,
//	 * TakePhotoActivity.class); startActivity(i); return true; }
//	 * 
//	 * public boolean onSearchIconClick(View v) { Intent i = new Intent(this,
//	 * SearchActivity.class); startActivity(i); return true; }
//	 * 
//	 * public boolean onSeeLogIconClick(View v) { Intent i = new Intent(this,
//	 * ViewEntriesActivity.class); startActivity(i); return true; }
//	 * 
//	 * public boolean onAddEntryIconClick(View v) { Intent i = new Intent(this,
//	 * AddEntryActivity.class); startActivity(i); return true; }
//	 */
//
	public void setLayoutContent() {
		// set the content for the layout
		RelativeLayout content = (RelativeLayout) findViewById(R.id.content_homepage);
		content.removeAllViews();
		getLayoutInflater().inflate(R.layout.login_signup, content);
	}

	public boolean onSignUpClick(View v) {
		Intent i = new Intent(this, SignUpActivity.class);
		startActivity(i);
		return true;
	}
	
	//get current email in email field
	public String getEmail() {
		return (((EditText) findViewById(R.id.login_email)).getText().toString());
	}
	
	//get current password in password field
		public String getPassword() {
			return (((EditText) findViewById(R.id.login_password)).getText().toString());
		}

	public boolean onSignInClick(View v) {
		
		//TODO: get by email task should check as well
		String userKey = null; //TODO: get the user key and store it
		new GetUserKeyByEmailTask().execute(null, getEmail(), getPassword());
		
		
		if(userKey != null) {
			SharedPreferences prefs = this.getSharedPreferences(
				      MyPREFERENCES, Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("userKey", userKey);
			Intent i = new Intent(this, ViewTripsActivity.class);
			startActivity(i);
		}
		
		else {
			Toast.makeText(this, "username or password incorrect", Toast.LENGTH_LONG).show();
		}
		
		//if not working just uncomment this:
//		Intent i = new Intent(this, ViewTripsActivity.class);
//		startActivity(i);
		return true;
	}
}
