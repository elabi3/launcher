package com.example.controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.launcher.R;
import com.example.moduleApps.controllers.AppsGrid;

public class MinimalistFragment extends Fragment {
	private View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.controllers_minimalist_fragment,
				container, false);
		loadGridRecommendedApps();
		return mView;
	}

	private void loadGridRecommendedApps() {
		LinearLayout layout = (LinearLayout) mView.findViewById(R.id.recommen);

		// create and add grid
		// 3min = 180000ms
		AppsGrid appsGrid = new AppsGrid(getActivity(),
				AppsGrid.APPS_GRID_WEEK_DAY_TIME, 8, 180000);
		layout.addView(appsGrid.getGridView());
	}

	/*
	 * private Runnable runnable = new Runnable() { public void run() { Calendar
	 * calendar = Calendar.getInstance(); SimpleDateFormat dateFormatHour = new
	 * SimpleDateFormat("HH"); SimpleDateFormat dateFormatMin = new
	 * SimpleDateFormat("mm"); SimpleDateFormat dateFormatSec = new
	 * SimpleDateFormat("ss");
	 * 
	 * // Revisar para poner seg�n el localized SimpleDateFormat dateFormatDate
	 * = new SimpleDateFormat( "dd:MMMM:yyyy");
	 * 
	 * final String strDateHour = dateFormatHour .format(calendar.getTime());
	 * final String strDateMin = dateFormatMin.format(calendar.getTime()); final
	 * String strDateSec = dateFormatSec.format(calendar.getTime()); final
	 * String strDate = dateFormatDate.format(calendar.getTime());
	 * 
	 * timeHour.setText(strDateHour); timeMin.setText(strDateMin);
	 * 
	 * handler.postDelayed(this, 1000); } };
	 */
}