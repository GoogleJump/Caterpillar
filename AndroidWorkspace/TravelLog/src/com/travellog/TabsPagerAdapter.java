package com.travellog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * TabsPagerAdapter
 * adapter for tabs that are found in ViewPhotosActivity
 **/
public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// return view by trip
			return new MapFragment();
		case 1:
			// return view by date
			return new TripsFragment();


		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 2;
	}

	
	public static class TripsFragment extends Fragment {
		public TripsFragment() {

		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			//load entries from database
			
			// Inflate the layout for this fragment
			return inflater.inflate(R.layout.activity_view_entries, container,
					false);
		}
	}
	
	
	public static class MapFragment extends Fragment {
		public MapFragment() {

		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			//load entries from database
			
			// Inflate the layout for this fragment
			return inflater.inflate(R.layout.activity_view_entries, container,
					false);
		}
	}
}
