package com.example.controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.launcher.R;
import com.example.moduleApps.controllers.AppsGrid;

public class AppsFragment extends Fragment {
	private View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.controllers_apps_fragment, container,
				false);

		loadGridMostUsedApps();
		loadGridMostLessApps();
		
		return mView;
	}

	private void loadGridMostUsedApps() {
		LinearLayout layout = (LinearLayout) mView.findViewById(R.id.mostused);

		// create and add grid
		// 10min = 1800000ms
		AppsGrid appsGrid = new AppsGrid(getActivity(),
				AppsGrid.APPS_GRID_MOST_OPENS, 8, 600000);
		layout.addView(appsGrid.getGridView());
	}
	
	private void loadGridMostLessApps() {
		LinearLayout layout = (LinearLayout) mView.findViewById(R.id.lessused);

		// create and add grid
		// 10min = 1800000ms
		AppsGrid appsGrid = new AppsGrid(getActivity(),
				AppsGrid.APPS_GRID_LESS_OPENS, 8, 600000);
		layout.addView(appsGrid.getGridView());
	}
}
