package com.example.controllers.fragments;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.auxiliar.Blur;
import com.example.launcher.R;
import com.example.moduleApps.controllers.AppsGrid;
import android.graphics.drawable.BitmapDrawable;

public class SmartFragment extends Fragment {
	private View mView;
	private Handler handler = new Handler();
	private TextView timeHour;
	private TextView timeMin;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.controllers_fragments_smart,
				container, false);

		mView.getViewTreeObserver().addOnPreDrawListener(
				new ViewTreeObserver.OnPreDrawListener() {
					@Override
					public boolean onPreDraw() {
						Bitmap bmp = ((BitmapDrawable) getActivity()
								.getWallpaper()).getBitmap();

						/*
						 * Blur.blur(getActivity(),
						 * Bitmap.createScaledBitmap(bmp, mView.getWidth(),
						 * mView.getHeight(), true), mView);
						 */

						/*
						 * Bitmap overlay = Blur.fastblur(Bitmap
						 * .createScaledBitmap(bmp, mView.getWidth(),
						 * mView.getHeight(), true), 25);
						 * mView.setBackground(new
						 * BitmapDrawable(getResources(), overlay));
						 */
						System.gc();
						return true;
					}
				});

		loadClock();
		loadGridRecommendedApps();
		loadGridNextApps();
		return mView;
	}

	private void loadGridRecommendedApps() {
		LinearLayout layout = (LinearLayout) mView.findViewById(R.id.recommen);

		AppsGrid appsGrid = new AppsGrid(getActivity(), AppsGrid.GRID,
				AppsGrid.APPS_GRID_RECOMENDED, 12, 30000);
		layout.addView(appsGrid.getGridView());
	}

	private void loadGridNextApps() {
		LinearLayout layout = (LinearLayout) mView.findViewById(R.id.nextApps);

		AppsGrid appsGrid = new AppsGrid(getActivity(), AppsGrid.GRID,
				AppsGrid.APPS_GRID_NEXT, 2, 30000);
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