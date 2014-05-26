package com.travellog;
/*
 * sliding drawer menu
 * 
 */

import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.view.GravityCompat;
public class DrawerActivity extends FragmentActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mMenuTitles;
 //   protected LinearLayout fullLayout;
    protected FrameLayout actContent;
    
    	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			setTitle("");
			
			//sets to portrait
			 int orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
		     setRequestedOrientation(orientation);

			
			    mTitle = mDrawerTitle = getTitle();
		        mMenuTitles = getResources().getStringArray(R.array.menu_options);
		        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		        mDrawerList = (ListView) findViewById(R.id.left_drawer);

		        // set a custom shadow that overlays the main content when the drawer opens
		        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		        // set up the drawer's list view with items and click listener
		        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
		                R.layout.drawer_list_item, mMenuTitles));
		        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		        // enable ActionBar app icon to behave as action to toggle nav drawer
		        getActionBar().setDisplayHomeAsUpEnabled(true);
		        getActionBar().setHomeButtonEnabled(true);

		        // ActionBarDrawerToggle ties together the the proper interactions
		        // between the sliding drawer and the action bar app icon
		        mDrawerToggle = new ActionBarDrawerToggle(
		                this,                  /* host Activity */
		                mDrawerLayout,         /* DrawerLayout object */
		                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
		                R.string.drawer_open,  /* "open drawer" description for accessibility */
		                R.string.drawer_close  /* "close drawer" description for accessibility */
		                ) {
		            public void onDrawerClosed(View view) {
		               // getActionBar().setTitle(mTitle);
		                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
		            }

		            public void onDrawerOpened(View drawerView) {
		               // getActionBar().setTitle(mDrawerTitle);
		                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
		            }
		        };
		        mDrawerLayout.setDrawerListener(mDrawerToggle);

		        if (savedInstanceState == null) {
		            selectItem(0);
		        }
		    }

		    @Override
		    public boolean onCreateOptionsMenu(Menu menu) {
		        MenuInflater inflater = getMenuInflater();
		        inflater.inflate(R.menu.main, menu);
		        return super.onCreateOptionsMenu(menu);
		    }

		    /* Called whenever we call invalidateOptionsMenu() */
		  /*  @Override
		    public boolean onPrepareOptionsMenu(Menu menu) {
		        // If the nav drawer is open, hide action items related to the content view
		        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		       // menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		        return super.onPrepareOptionsMenu(menu);
		    }*/

		    @Override
		    public boolean onOptionsItemSelected(MenuItem item) {
		         // The action bar home/up action should open or close the drawer.
		         // ActionBarDrawerToggle will take care of this.
		        if (mDrawerToggle.onOptionsItemSelected(item)) {
		            return true;
		        }	     
		            return super.onOptionsItemSelected(item);
		    }

		    /* The click listner for ListView in the navigation drawer */
		    class DrawerItemClickListener implements ListView.OnItemClickListener {
		        @Override
		        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		            selectItem(position);
				        mDrawerList.setItemChecked(position, true);         
				        mDrawerLayout.closeDrawer(mDrawerList);
				        if ( position == 0 ) {
				            Intent intent = new Intent(DrawerActivity.this, MainActivity.class);
				            startActivity(intent);
				            mDrawerLayout.closeDrawers();
				            }
				        if ( position == 1 ) {
				        	Intent intent = new Intent(DrawerActivity.this, ViewTripsActivity.class);
				            startActivity(intent);
				            mDrawerLayout.closeDrawers();
				            }
				        if ( position == 2 ) {
				        	Intent intent = new Intent(DrawerActivity.this, TakePhotoActivity.class);
				            startActivity(intent);
				            mDrawerLayout.closeDrawers();
					     }
				        
				        if ( position == 3 ) {
				        	Intent intent = new Intent(DrawerActivity.this, SearchActivity.class);
				            startActivity(intent);
				            mDrawerLayout.closeDrawers();
					     }
		        }
		    }

		    private void selectItem(int position) {
		      //the following is sample code for sliding drawers
		    	// update the main content by replacing fragments
		        Fragment fragment = new MenuFragment();
		        Bundle args = new Bundle();
		        args.putInt(MenuFragment.ARG_MENU_NUMBER, position);
		        fragment.setArguments(args);

		        FragmentManager fragmentManager = getFragmentManager();
		        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

		        // update selected item and title, then close the drawer
		        mDrawerList.setItemChecked(position, true);
		       // setTitle(mMenuTitles[position]);
		        mDrawerLayout.closeDrawer(mDrawerList);
		        
		    }
		    
		    

		    @Override
		    public void setTitle(CharSequence title) {
		        mTitle = title;
		        getActionBar().setTitle(mTitle);
		    }
		    

		    /**
		     * When using the ActionBarDrawerToggle, you must call it during
		     * onPostCreate() and onConfigurationChanged()...
		     */

		    @Override
		    protected void onPostCreate(Bundle savedInstanceState) {
		        super.onPostCreate(savedInstanceState);
		        // Sync the toggle state after onRestoreInstanceState has occurred.
		        mDrawerToggle.syncState();
		    }
		    
		    public boolean onCameraIconClick(View v) {
		    	Intent i = new Intent(this, TakePhotoActivity.class);
		    	startActivity(i);
		    	return true;
		    }

		    //don't allow landscape view (at least for now)
		    @Override
		    public void onConfigurationChanged(Configuration newConfig) {
		        super.onConfigurationChanged(newConfig);
		        // Pass any configuration change to the drawer toggls
		        mDrawerToggle.onConfigurationChanged(newConfig);
		        int orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
		        setRequestedOrientation(orientation);
		    }

		    public static class MenuFragment extends Fragment {
		        public static final String ARG_MENU_NUMBER = "menu_number";

		        public MenuFragment() {
		            // Empty constructor required for fragment subclasses
		        }

		  
		    }
		    
		    
		    
		 
		}
