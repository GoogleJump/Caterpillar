package com.travellog;

import java.io.IOException;
import java.sql.Driver;
import java.util.Date;
import java.util.Locale;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import com.travellog.userendpoint.Userendpoint;
import com.travellog.userendpoint.model.Email;
import com.travellog.userendpoint.model.User;
import com.travellog.noteendpoint.Noteendpoint;
import com.travellog.noteendpoint.model.Note;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
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
import android.os.AsyncTask;
import android.content.Context;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;

/**
 * The Sign Up Activity.
 * 
 * sign in/sign up buttons TODO check if sign in was successful and store the
 * user id somewhere
 */
public class SignUpActivity extends DrawerActivity {

	static int SCREEN_WIDTH;
	static int SCREEN_HEIGHT;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mPlanetTitles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("");

		// Intent i = new Intent(this, RegisterActivity.class);
		// startActivity(i);

		// TODO: if the person is already logged in, start next activity
		setLayoutContent();
		Display display = getWindowManager().getDefaultDisplay();
		SCREEN_WIDTH = display.getWidth();
		SCREEN_HEIGHT = display.getHeight();
		// resizeIcons();
		// resizeLogo();

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

	// gets whatever text is currently in the first name field
	public String getFirstName() {
		return ((EditText) findViewById(R.id.signup_firstname_edittext))
				.getText().toString();
	}

	// gets whatever text is currently in the last name field
	public String getLastName() {
		return ((EditText) findViewById(R.id.signup_lastname_edittext))
				.getText().toString();
	}

	// gets whatever text is currently in the email field
	public String getEmail() {
		return ((EditText) findViewById(R.id.signup_email_edittext)).getText()
				.toString();
	}

	// gets whatever text is currently in the password field
	public String getPassword() {
		return ((EditText) findViewById(R.id.signup_password_edittext)).getText()
				.toString();
	}
	
	// gets whatever text is currently in the username field
	public String getUsername() {
		return ((EditText) findViewById(R.id.signup_username_edittext)).getText()
				.toString();
	}

	// logo should be a square with sides half the screen
	public void resizeLogo() {

		View logo = (View) findViewById(R.id.logo_homepage);
		System.out.println("resizing logo");
		System.out.println("before" + logo.getHeight());
		Display display = getWindowManager().getDefaultDisplay();
		int screen_height = display.getHeight();
		logo.getLayoutParams().height = screen_height / 4;
		logo.getLayoutParams().width = screen_height / 4;
		System.out.println("after" + screen_height / 2);

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

	public void setLayoutContent() {
		// set the content for the layout
		RelativeLayout content = (RelativeLayout) findViewById(R.id.content_homepage);
		content.removeAllViews();
		getLayoutInflater().inflate(R.layout.activity_signup, content);
	}

	public boolean onSignUpClick(View v) {
		new AddNewUserTask().execute(null, getFirstName(), getLastName(), getUsername(), 
				getPassword(), getEmail());
		Intent i = new Intent(this, ViewTripsActivity.class);
		startActivity(i);
		return true;
	}

	public String[] getFields() {
		// { null, first name, last name, username, password, email }
		String[] user = new String[5];
		user[0] = getFirstName();
		user[1] = getLastName();
		user[2] = getUsername();
		user[3] = getPassword();
		user[4] = getEmail();
		return user;
	}

	/*
	 * public class SampleEndpointsTask extends AsyncTask<Context, Integer,
	 * Long> { protected Long doInBackground(Context... contexts) {
	 * 
	 * Noteendpoint.Builder endpointBuilder = new Noteendpoint.Builder(
	 * AndroidHttp.newCompatibleTransport(), new JacksonFactory(), new
	 * HttpRequestInitializer() { public void initialize(HttpRequest
	 * httpRequest) { } }); Noteendpoint endpoint =
	 * CloudEndpointUtils.updateBuilder( endpointBuilder).build(); try {
	 * System.out.println("trycatch"); Note note = new
	 * Note().setDescription("Note Description"); String noteID = new
	 * Date().toString(); note.setId(noteID);
	 * 
	 * note.setEmailAddress("E-Mail Address"); Note result =
	 * endpoint.insertNote(note).execute(); System.out.println("stored note"); }
	 * catch (IOException e) { e.printStackTrace(); } return (long) 0; } }
	 * 
	 * public class EndpointsTask extends AsyncTask<Context, Integer, Long> {
	 * protected Long doInBackground(Context... contexts) {
	 * 
	 * System.out.println("task starting"); Userendpoint.Builder endpointBuilder
	 * = new Userendpoint.Builder( AndroidHttp.newCompatibleTransport(), new
	 * JacksonFactory(), new HttpRequestInitializer() { public void
	 * initialize(HttpRequest httpRequest) {
	 * System.out.println("initializing http"); //
	 * httpRequest.setConnectTimeout(5*20000); } });
	 * 
	 * 
	 * System.out.println("about to do things"); Userendpoint endpoint =
	 * CloudEndpointUtils.updateBuilder( endpointBuilder).build();
	 * 
	 * System.out.println("built user endpoint starting"); try {
	 * System.out.println("try block"); Date d = new Date(); User user = new
	 * User(); //User user = new User(); System.out.println("new user"); //Long
	 * UserID = System.currentTimeMillis(); Email email = new Email();
	 * email.setEmail("emailll@yespleasework.plz"); user.setEmail(email);
	 * user.setFirstName("Chuck"); user.setLastName("Norris");
	 * user.setPassword("password123"); //Integer userID = Integer.valueOf(new
	 * Date().getSeconds());
	 * 
	 * // user.setDateCreated(new DateTime(new Date()));
	 * System.out.println("about to insert user"); User result =
	 * endpoint.insertUser(user).execute(); System.out.println("success! :D"); }
	 * catch (IOException e) { e.printStackTrace(); } return (long) 0; } }
	 */

}
