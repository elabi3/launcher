package com.example.controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.launcher.R;
import com.example.moduleApps.controllers.AppsGrid;

public class MinimalistFragment extends Fragment {
	private View mView;
	private Handler handler = new Handler();
	private TextView timeHour;
	private TextView timeMin;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.controllers_minimalist_fragment,
				container, false);
		loadClock();
		loadGridRecommendedApps();
		return mView;
	}

	private void loadGridRecommendedApps() {
		LinearLayout layout = (LinearLayout) mView.findViewById(R.id.recommen);

		// 3min = 180000ms
		AppsGrid appsGrid = new AppsGrid(getActivity(), AppsGrid.GRID_COMPACT,
				AppsGrid.APPS_GRID_WEEK_DAY_TIME, 10, 180000);
		layout.addView(appsGrid.getGridView());
	}

	public void loadClock() {
		timeHour = (TextView) mView.findViewById(R.id.timeHour);
		timeMin = (TextView) mView.findViewById(R.id.timeMin);

		handler = new Handler();
		handler.postDelayed(runnable, 1000);
	}

	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			/* do what you need to do */
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat dateFormatHour = new SimpleDateFormat("HH");
			SimpleDateFormat dateFormatMin = new SimpleDateFormat("mm");
			SimpleDateFormat dateFormatSec = new SimpleDateFormat("ss");

			// Revisar para poner seg�n el localized
			SimpleDateFormat dateFormatDate = new SimpleDateFormat(
					"dd:MMMM:yyyy");

			final String strDateHour = dateFormatHour
					.format(calendar.getTime());
			final String strDateMin = dateFormatMin.format(calendar.getTime());
			final String strDateSec = dateFormatSec.format(calendar.getTime());
			final String strDate = dateFormatDate.format(calendar.getTime());

			timeHour.setText(strDateHour);
			timeMin.setText(strDateMin);

			/* and here comes the "trick" */
			handler.postDelayed(this, 1000);
		}
	};
}