package com.spaces.controllers.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spaces.launcher.R;
import com.spaces.moduleApps.controllers.AppsGrid;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SmartFragment extends Fragment {
	private View mView;
	private Handler handler = new Handler();

    private TextView title;
    private TextView subTitle;
    private AppsGrid appsRecommended;
	private AppsGrid appsNexts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
        appsRecommended = new AppsGrid(getActivity(), AppsGrid.GRID,
				AppsGrid.APPS_GRID_RECOMENDED, 12, 30000);

		appsNexts = new AppsGrid(getActivity(), AppsGrid.GRID,
				AppsGrid.APPS_GRID_NEXT, 2, 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.controllers_fragments_smart,
				container, false);

		loadMessage();

		// Layouts
		LinearLayout layout = (LinearLayout) mView.findViewById(R.id.recommen);
		layout.addView(appsRecommended.getGridView());

		layout = (LinearLayout) mView.findViewById(R.id.nextApps);
		layout.addView(appsNexts.getGridView());

		return mView;
	}

    public void loadMessage() {
        title = (TextView) mView.findViewById(R.id.title);
        subTitle = (TextView) mView.findViewById(R.id.subtitle);

        handler = new Handler();
        handler.postDelayed(runnable, 1000);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormatHour = new SimpleDateFormat("HH");
            int hour = Integer.parseInt(dateFormatHour
                    .format(calendar.getTime()));

            if (hour >= 5 && hour < 12) {
                title.setText(R.string.smart_morning);
            } else if (hour >= 12 && hour < 20 ) {
                title.setText(R.string.smart_afternoon);
            } else {
                title.setText(R.string.smart_night);
            }

			/* and here comes the "trick" */
            handler.postDelayed(this, 1000);
        }
    };
}