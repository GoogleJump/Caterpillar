package com.travellog;

import java.io.File;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.travellog.DrawerActivity.DrawerItemClickListener;

/**
 * ViewPhotosActivity 
 * View all photos from an entry in a GridView
 * Select a photo to open a photoFragment - shows the photo along with title and location
 * Edit a selected photo
 * TODO: 
 * get photos from database based on an inputed entry id
 * update edited photos in database
 * connect add photo button
 *
 */

public class ViewPhotosActivity extends DrawerActivity {

	/*
	 * DrawerActivity fields
	 */
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mMenuTitles;
	RelativeLayout content;

	/*
	 * ViewPhotosActivity fields
	 */

	private ImageAdapter myTaskAdapter; // image adapter that uses photoList to
										// load the gridview with bitmaps
	private AsyncTaskLoadFiles myAsyncTaskLoadFiles; // async task that will
														// load photos from
														// database (TODO)
	ImageAdapter myImageAdapter;
	static GridView gridview; // view that holds all the photos
	protected static Fragment viewPhotoFragment; // fragment that views the
													// selected photo
	protected static Fragment editPhotoFragment; // fragment that edits the
													// selected photo
	private static ArrayList<Photo> photoList; // list of photos
	private static int currentSelectedPhoto; // keeps track of the photo the
												// user is currently viewing or
												// editing

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getLayoutContent(); // replaces the content view from activity main with
							// the content view for ViewPhotosActivity

		// initialize the two fragments that will be used in this activity -
		// viewing and editing photos
		viewPhotoFragment = new PhotoFragment();
		editPhotoFragment = new EditPhotoFragment();

		// get extras from previous activity
		// TODO: use the extras to put/get the id of the entry so we can load
		// photos of that entry
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			// edit = extras.getBoolean("id"); //get the entry of which to load
			// photos from
		}

		/* view photo activity specific stuff */
		gridview = (GridView) findViewById(R.id.gridview);
		gridview.getLayoutParams().height = MainActivity.SCREEN_HEIGHT;
		gridview.setColumnWidth(MainActivity.SCREEN_HEIGHT/6); //3 pictures per row
		myImageAdapter = new ImageAdapter(this);
		gridview.setAdapter(myImageAdapter);

		myAsyncTaskLoadFiles = new AsyncTaskLoadFiles(myImageAdapter);
		myAsyncTaskLoadFiles.execute();

		// when the user clicks one of the images in the gridview, open the view
		// photo fragment
		gridview.setOnItemClickListener(myOnItemClickListener);

		// when the user clicks anywhere on the gridview, close any open
		// fragments
		gridview.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				getFragmentManager().beginTransaction()
						.remove(viewPhotoFragment).commit();
				getFragmentManager().beginTransaction()
						.remove(editPhotoFragment).commit();
				return false;
			}
		});

		// add photo button - top of view photos page
		Button buttonAddPhoto = (Button) findViewById(R.id.add_photo_viewphotos_btn);
		// add another photo to this activity
		buttonAddPhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// Cancel the previous running task, if exist.
				myAsyncTaskLoadFiles.cancel(true);

				// TODO open up add photo/take photo page

			}
		});

		/* initialize sliding drawer activity fields */
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
	OnItemClickListener myOnItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			currentSelectedPhoto = position;
			getFragmentManager().beginTransaction().remove(viewPhotoFragment)
					.commit();
			getFragmentManager().beginTransaction().remove(editPhotoFragment)
					.commit();
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_homepage, viewPhotoFragment).commit();

		}
	};

	// set the content in this activity
	public void getLayoutContent() {
		content = (RelativeLayout) findViewById(R.id.content_homepage);
		content.removeAllViews();
		getLayoutInflater().inflate(R.layout.activity_view_photos, content);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* for sliding drawer */
	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/* for sliding drawer */
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

	// fragment viewing an individual photo:
	public static class PhotoFragment extends Fragment {
		ImageView photoView; // image view for the current photo being viewed
		View v; // view for this fragment
		Photo selectedPhoto; // current photo that is being viewed

		public PhotoFragment() {

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			// Inflate the layout for this fragment
			v = inflater.inflate(R.layout.view_photo, container, false);
			LayoutParams params = (LayoutParams) v.getLayoutParams();
			params.setMargins(20, 20, 20, 20);
			v.setLayoutParams(params);
			v.setClickable(true); // so view underneath can't be clicked through
									// this view
			// get the current photo and image view:
			photoView = (ImageView) v.findViewById(R.id.photo_view_imgview);
			selectedPhoto = photoList.get(currentSelectedPhoto);
			setPhotoInfoAsCurrent(); // load the bitmap, title, and location of
										// photo into view

			// edit current photo button listener - opens the editPhotoFragment
			// and closes this one
			Button editButton = (Button) v.findViewById(R.id.edit_photo_btn);
			editButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					getActivity().getFragmentManager().beginTransaction()
							.replace(R.id.content_homepage, editPhotoFragment)
							.commit();
					getActivity().getFragmentManager().beginTransaction()
							.remove(PhotoFragment.this).commit();
				}
			});

			Button exitBtn = (Button) v.findViewById(R.id.exit_photo_btn);
			exitBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					getFragmentManager().beginTransaction()
					.remove(viewPhotoFragment).commit();
			getFragmentManager().beginTransaction()
					.remove(editPhotoFragment).commit();
				}
			});
					
			// current photo image view listener - clicking on the photo/view should
			// go to the next one in the gallery
			v.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					System.out.println("next photo");
					if (currentSelectedPhoto == photoList.size() - 1) {
						currentSelectedPhoto = 0;
					} else {
						currentSelectedPhoto++;					
						}
					selectedPhoto = photoList.get(currentSelectedPhoto);
					setPhotoInfoAsCurrent();
				}

			});

			return v;
		}

		public void setPhotoInfoAsCurrent() {

			Bitmap selectedImage = selectedPhoto.getImage();
			photoView.setImageBitmap(photoList.get(currentSelectedPhoto)
					.getImage());
			if (selectedImage.getHeight() > selectedImage.getWidth()) {
				photoView.getLayoutParams().height = (int) (.8 * MainActivity.SCREEN_HEIGHT);
			} else {
				photoView.getLayoutParams().width = (int) (.8 * MainActivity.SCREEN_WIDTH);
			}

			// check for title/location and set visible/invisible if they
			// exist/don't exist
			if (selectedPhoto.getTitle().equals("")) {
				setVisibility(false, R.id.photo_title_label);
				setVisibility(false, R.id.photo_title_textview);
			} else {
				setVisibility(true, R.id.photo_title_label);
				setVisibility(true, R.id.photo_title_textview);
				((TextView) v.findViewById(R.id.photo_title_textview))
						.setText(selectedPhoto.getTitle());

			}

			if (selectedPhoto.getLocation().equals("")) {
				setVisibility(false, R.id.photo_location_label);
				setVisibility(false, R.id.photo_location_textview);
			} else {
				setVisibility(true, R.id.photo_location_label);
				setVisibility(true, R.id.photo_location_textview);
				((TextView) v.findViewById(R.id.photo_title_textview))
						.setText(selectedPhoto.getLocation());
			}
		}

		// helper function that sets visibility of given resource
		private void setVisibility(boolean visible, int resource) {
			if (visible) {
				v.findViewById(resource).setVisibility(v.VISIBLE);
			} else {
				v.findViewById(resource).setVisibility(v.GONE);
			}
		}

	}

	// fragment for viewing an individual photo:
	public static class EditPhotoFragment extends Fragment {
		ImageView photoView; // image view for the current photo being viewed
		View view; // view for this fragment
		Photo selectedPhoto; // current photo that is being viewed

		public EditPhotoFragment() {

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// load entries from database

			// Inflate the layout for this fragment
			view = inflater.inflate(R.layout.edit_photo, container, false);
			LayoutParams params = (LayoutParams) view.getLayoutParams();
			params.setMargins(20, 20, 20, 20);
			view.setLayoutParams(params);
			view.setClickable(true); // so view underneath can't be clicked
										// through this view
			// get the current photo and image view:
			photoView = (ImageView) view.findViewById(R.id.photo_edit_imgview);
			selectedPhoto = photoList.get(currentSelectedPhoto);
			setPhotoInfoAsCurrent(); // load the bitmap, title, and location of
										// photo into view

			// submit button listener - done editing photo so get the text from
			// the EditText views and set for current photo
			Button submitButton = (Button) view
					.findViewById(R.id.submit_edit_photo_btn);
			submitButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// change photo title/location
					EditText titleEdit = ((EditText) view
							.findViewById(R.id.photo_title_edittext));
					EditText locationEdit = ((EditText) view
							.findViewById(R.id.photo_location_edittext));
					selectedPhoto.setTitle(titleEdit.getText().toString());
					selectedPhoto
							.setLocation(locationEdit.getText().toString());

					// end this fragment and bring up the view photo fragment
					getActivity().getFragmentManager().beginTransaction()
							.replace(R.id.content_homepage, viewPhotoFragment)
							.commit();
					getActivity().getFragmentManager().beginTransaction()
							.remove(EditPhotoFragment.this).commit();

					// TODO update database
				}
			});

			return view;
		}

		public void setPhotoInfoAsCurrent() {
			Bitmap selectedImage = selectedPhoto.getImage();
			photoView.setImageBitmap(photoList.get(currentSelectedPhoto)
					.getImage());
			if (selectedImage.getHeight() > selectedImage.getWidth()) {
				photoView.getLayoutParams().height = (int) (.8 * MainActivity.SCREEN_HEIGHT);
			} else {
				photoView.getLayoutParams().width = (int) (.8 * MainActivity.SCREEN_WIDTH);
			}
		}
	}

	// taken from:
	// http://android-er.blogspot.com/2013/10/gridview-example-load-images-to.html
	//original loaded from external storage, adjusted to load from arraylist of photos, 
	// TODO: adjust further to get images from database and put into photoList
	public class AsyncTaskLoadFiles extends AsyncTask<Void, String, Void> {

		public AsyncTaskLoadFiles(ImageAdapter adapter) {
			myTaskAdapter = adapter;
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

	public class ImageAdapter extends BaseAdapter {

		private Context mContext;

		public ImageAdapter(Context c) {
			mContext = c;
			// test images:
			photoList = new ArrayList<Photo>();
			
			//add sample images to photoList
			for (int i = 0; i < 10; i++) {
				Photo samplePhoto = new Photo();
				samplePhoto.setImage((BitmapFactory.decodeResource(
						getResources(), R.drawable.eiffel_tower_samplepic)));
				photoList.add(samplePhoto);

			}
		}

		void add(Photo photo) {
			photoList.add(photo);
		}

		void clear() {
			// itemList.clear();
			photoList.clear();
		}

		/*
		 * void remove(int index){ photoList.remove(index); }
		 */

		@Override
		public int getCount() {
			return photoList.size();
		}

		@Override
		public Object getItem(int position) {
			return photoList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView == null) { // if it's not recycled, initialize some
				// attributes
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(8, 8, 8, 8);
			} else {
				imageView = (ImageView) convertView;
			}

			Bitmap img = photoList.get(position).getImage();
			img = img.createScaledBitmap(img, 220, 220, true);

			// imageView.setImageBitmap(bm);
			imageView.setImageBitmap(img);
			return imageView;
		}
	}


}
