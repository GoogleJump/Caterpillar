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
import android.widget.Toast;

import com.travellog.DrawerActivity.DrawerItemClickListener;

public class ViewPhotosActivity extends DrawerActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mMenuTitles;
	RelativeLayout content;

	File targetDirector;
	ImageAdapter myTaskAdapter;
	AsyncTaskLoadFiles myAsyncTaskLoadFiles;
	static GridView gridview;
	Fragment viewPhotoFragment;
	static ArrayList<Photo> photoList;
	static int currentSelectedPhoto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getLayoutContent();
		viewPhotoFragment = new PhotoFragment();
		

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			// edit = extras.getBoolean("id"); //get the entry of which to load
			// photos from
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

		gridview = (GridView) findViewById(R.id.gridview);
		gridview.getLayoutParams().height = MainActivity.SCREEN_HEIGHT;
		myImageAdapter = new ImageAdapter(this);
		gridview.setAdapter(myImageAdapter);

		myAsyncTaskLoadFiles = new AsyncTaskLoadFiles(myImageAdapter);
		myAsyncTaskLoadFiles.execute();

		gridview.setOnItemClickListener(myOnItemClickListener);
		
		//close fragment
		gridview.setOnTouchListener(new OnTouchListener() {


			@Override
			public boolean onTouch(View v, MotionEvent event) {
				System.out.println("removing");
				getFragmentManager().beginTransaction().remove(viewPhotoFragment).commit();
            return false;
			}
        });
			

		Button buttonAddPhoto = (Button) findViewById(R.id.add_photo_viewphotos_btn);
		buttonAddPhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// Cancel the previous running task, if exist.
				myAsyncTaskLoadFiles.cancel(true);

				// TODO open up add photo/take photo page

			}
		});

	}

	OnItemClickListener myOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			currentSelectedPhoto = position;
			getFragmentManager().beginTransaction().remove(viewPhotoFragment).commit();
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_homepage, viewPhotoFragment).commit();
			
					
		}
	};

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

	// viewing an individual photo:
	public static class PhotoFragment extends Fragment {
		ImageView photoView;
		View v;
		
		public PhotoFragment() {
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// load entries from database

			// Inflate the layout for this fragment
			v = inflater.inflate(R.layout.view_photo, container, false);
			LayoutParams params = (LayoutParams) v.getLayoutParams();
			params.setMargins(20, 20, 20, 20);
			v.setLayoutParams(params);
			v.setClickable(true); //so view underneath can't be clicked
			photoView = (ImageView) v.findViewById(R.id.photo_view_imgview);
			setImageAsCurrent();
			return v;
		}
		
		public void setImageAsCurrent() {
			Photo selectedPhoto = photoList.get(currentSelectedPhoto);
			Bitmap selectedImage = selectedPhoto.getImage();
			photoView.setImageBitmap(photoList.get(currentSelectedPhoto).getImage());
			if(selectedImage.getHeight() > selectedImage.getWidth()) {
			photoView.getLayoutParams().height = (int) (.8*MainActivity.SCREEN_HEIGHT);
			}
			else {
				photoView.getLayoutParams().width = (int) (.8*MainActivity.SCREEN_WIDTH);
			}
			
			//check for title/location and set visible/invisible if they exist/don't exist
			if(selectedPhoto.getTitle().equals("")) {
				setVisibility(false, R.id.photo_title_label);
				setVisibility(false, R.id.photo_title_textview);
			}
			else {
				setVisibility(true, R.id.photo_title_label);
				setVisibility(true, R.id.photo_title_textview);
			}
			
			if(selectedPhoto.getLocation().equals("")) {
				setVisibility(false, R.id.photo_location_label);
				setVisibility(false, R.id.photo_location_textview);
			}
			else {
				setVisibility(true, R.id.photo_location_label);
				setVisibility(true, R.id.photo_location_textview);
			}
		}
		
		private void setVisibility(boolean visible, int resource) {
			if(visible) {
				v.findViewById(resource).setVisibility(v.VISIBLE);
			}
			else {
				v.findViewById(resource).setVisibility(v.GONE);
			}
		}
		
		public boolean onGetNextPhotoClick(View v) {
			//TODO - connect this to clicking photo
			currentSelectedPhoto++;
			setImageAsCurrent();
			return true;
		}
		
		
	}

	// taken from:
	// http://android-er.blogspot.com/2013/10/gridview-example-load-images-to.html
	// loads image files --TODO load from database
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
		// ArrayList<String> itemList = new ArrayList<String>(); //example -
		// loading from url
		
		

		public ImageAdapter(Context c) {
			mContext = c;
			// test images:
			 photoList = new ArrayList<Photo>(); 
			for (int i = 0; i < 10; i++) {
				Photo samplePhoto = new Photo();
				samplePhoto.setImage((BitmapFactory.decodeResource(getResources(),
						R.drawable.eiffel_tower_samplepic)));
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
		 * void remove(int index){ itemList.remove(index); }
		 */

		@Override
		public int getCount() {
			// return itemList.size();
			return photoList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			// return itemList.get(position);
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

			/*
			 * Bitmap bm = decodeSampledBitmapFromUri(itemList.get(position),
			 * 220, 220);
			 */
			Bitmap img = photoList.get(position).getImage();
			img = img.createScaledBitmap(img, 220, 220, true);

			// imageView.setImageBitmap(bm);
			imageView.setImageBitmap(img);
			return imageView;
		}
	}

	ImageAdapter myImageAdapter;
}
